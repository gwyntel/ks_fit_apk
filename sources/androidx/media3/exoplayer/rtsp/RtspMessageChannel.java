package androidx.media3.exoplayer.rtsp;

import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.Nullable;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.exoplayer.upstream.Loader;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
final class RtspMessageChannel implements Closeable {
    public static final Charset CHARSET = Charsets.UTF_8;
    public static final int DEFAULT_RTSP_PORT = 554;
    private static final String TAG = "RtspMessageChannel";
    private volatile boolean closed;
    private final MessageListener messageListener;
    private Sender sender;
    private Socket socket;
    private final Loader receiverLoader = new Loader("ExoPlayer:RtspMessageChannel:ReceiverLoader");
    private final Map<Integer, InterleavedBinaryDataListener> interleavedBinaryDataListeners = Collections.synchronizedMap(new HashMap());

    public interface InterleavedBinaryDataListener {
        void onInterleavedBinaryDataReceived(byte[] bArr);
    }

    private final class LoaderCallbackImpl implements Loader.Callback<Receiver> {
        private LoaderCallbackImpl() {
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public void onLoadCanceled(Receiver receiver, long j2, long j3, boolean z2) {
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public void onLoadCompleted(Receiver receiver, long j2, long j3) {
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(Receiver receiver, long j2, long j3, IOException iOException, int i2) {
            if (!RtspMessageChannel.this.closed) {
                RtspMessageChannel.this.messageListener.onReceivingFailed(iOException);
            }
            return Loader.DONT_RETRY;
        }
    }

    public interface MessageListener {
        void onReceivingFailed(Exception exc);

        void onRtspMessageReceived(List<String> list);

        void onSendingFailed(List<String> list, Exception exc);
    }

    private static final class MessageParser {
        private static final int STATE_READING_BODY = 3;
        private static final int STATE_READING_FIRST_LINE = 1;
        private static final int STATE_READING_HEADER = 2;
        private long messageBodyLength;
        private final List<String> messageLines = new ArrayList();
        private int state = 1;

        private ImmutableList<String> addMessageBody(byte[] bArr) {
            Assertions.checkState(this.state == 3);
            if (bArr.length <= 0 || bArr[bArr.length - 1] != 10) {
                throw new IllegalArgumentException("Message body is empty or does not end with a LF.");
            }
            this.messageLines.add((bArr.length <= 1 || bArr[bArr.length + (-2)] != 13) ? new String(bArr, 0, bArr.length - 1, RtspMessageChannel.CHARSET) : new String(bArr, 0, bArr.length - 2, RtspMessageChannel.CHARSET));
            ImmutableList<String> immutableListCopyOf = ImmutableList.copyOf((Collection) this.messageLines);
            reset();
            return immutableListCopyOf;
        }

        @Nullable
        private ImmutableList<String> addMessageLine(byte[] bArr) throws ParserException {
            Assertions.checkArgument(bArr.length >= 2 && bArr[bArr.length - 2] == 13 && bArr[bArr.length - 1] == 10);
            String str = new String(bArr, 0, bArr.length - 2, RtspMessageChannel.CHARSET);
            this.messageLines.add(str);
            int i2 = this.state;
            if (i2 == 1) {
                if (!RtspMessageUtil.isRtspStartLine(str)) {
                    return null;
                }
                this.state = 2;
                return null;
            }
            if (i2 != 2) {
                throw new IllegalStateException();
            }
            long contentLengthHeader = RtspMessageUtil.parseContentLengthHeader(str);
            if (contentLengthHeader != -1) {
                this.messageBodyLength = contentLengthHeader;
            }
            if (!str.isEmpty()) {
                return null;
            }
            if (this.messageBodyLength > 0) {
                this.state = 3;
                return null;
            }
            ImmutableList<String> immutableListCopyOf = ImmutableList.copyOf((Collection) this.messageLines);
            reset();
            return immutableListCopyOf;
        }

        private static byte[] parseNextLine(byte b2, DataInputStream dataInputStream) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = {b2, dataInputStream.readByte()};
            byteArrayOutputStream.write(bArr);
            while (true) {
                if (bArr[0] == 13 && bArr[1] == 10) {
                    return byteArrayOutputStream.toByteArray();
                }
                bArr[0] = bArr[1];
                byte b3 = dataInputStream.readByte();
                bArr[1] = b3;
                byteArrayOutputStream.write(b3);
            }
        }

        private void reset() {
            this.messageLines.clear();
            this.state = 1;
            this.messageBodyLength = 0L;
        }

        public ImmutableList<String> parseNext(byte b2, DataInputStream dataInputStream) throws IOException {
            ImmutableList<String> immutableListAddMessageLine = addMessageLine(parseNextLine(b2, dataInputStream));
            while (immutableListAddMessageLine == null) {
                if (this.state == 3) {
                    long j2 = this.messageBodyLength;
                    if (j2 <= 0) {
                        throw new IllegalStateException("Expects a greater than zero Content-Length.");
                    }
                    int iCheckedCast = Ints.checkedCast(j2);
                    Assertions.checkState(iCheckedCast != -1);
                    byte[] bArr = new byte[iCheckedCast];
                    dataInputStream.readFully(bArr, 0, iCheckedCast);
                    immutableListAddMessageLine = addMessageBody(bArr);
                } else {
                    immutableListAddMessageLine = addMessageLine(parseNextLine(dataInputStream.readByte(), dataInputStream));
                }
            }
            return immutableListAddMessageLine;
        }
    }

    private final class Receiver implements Loader.Loadable {
        private static final byte INTERLEAVED_MESSAGE_MARKER = 36;
        private final DataInputStream dataInputStream;
        private volatile boolean loadCanceled;
        private final MessageParser messageParser = new MessageParser();

        public Receiver(InputStream inputStream) {
            this.dataInputStream = new DataInputStream(inputStream);
        }

        private void handleInterleavedBinaryData() throws IOException {
            int unsignedByte = this.dataInputStream.readUnsignedByte();
            int unsignedShort = this.dataInputStream.readUnsignedShort();
            byte[] bArr = new byte[unsignedShort];
            this.dataInputStream.readFully(bArr, 0, unsignedShort);
            InterleavedBinaryDataListener interleavedBinaryDataListener = (InterleavedBinaryDataListener) RtspMessageChannel.this.interleavedBinaryDataListeners.get(Integer.valueOf(unsignedByte));
            if (interleavedBinaryDataListener == null || RtspMessageChannel.this.closed) {
                return;
            }
            interleavedBinaryDataListener.onInterleavedBinaryDataReceived(bArr);
        }

        private void handleRtspMessage(byte b2) throws IOException {
            if (RtspMessageChannel.this.closed) {
                return;
            }
            RtspMessageChannel.this.messageListener.onRtspMessageReceived(this.messageParser.parseNext(b2, this.dataInputStream));
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
        public void cancelLoad() {
            this.loadCanceled = true;
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
        public void load() throws IOException {
            while (!this.loadCanceled) {
                byte b2 = this.dataInputStream.readByte();
                if (b2 == 36) {
                    handleInterleavedBinaryData();
                } else {
                    handleRtspMessage(b2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class Sender implements Closeable {
        private final OutputStream outputStream;
        private final HandlerThread senderThread;
        private final Handler senderThreadHandler;

        public Sender(OutputStream outputStream) {
            this.outputStream = outputStream;
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:RtspMessageChannel:Sender");
            this.senderThread = handlerThread;
            handlerThread.start();
            this.senderThreadHandler = new Handler(handlerThread.getLooper());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$send$0(byte[] bArr, List list) throws IOException {
            try {
                this.outputStream.write(bArr);
            } catch (Exception e2) {
                if (RtspMessageChannel.this.closed) {
                    return;
                }
                RtspMessageChannel.this.messageListener.onSendingFailed(list, e2);
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws InterruptedException {
            Handler handler = this.senderThreadHandler;
            final HandlerThread handlerThread = this.senderThread;
            Objects.requireNonNull(handlerThread);
            handler.post(new Runnable() { // from class: androidx.media3.exoplayer.rtsp.j
                @Override // java.lang.Runnable
                public final void run() {
                    handlerThread.quit();
                }
            });
            try {
                this.senderThread.join();
            } catch (InterruptedException unused) {
                this.senderThread.interrupt();
            }
        }

        public void send(final List<String> list) {
            final byte[] bArrConvertMessageToByteArray = RtspMessageUtil.convertMessageToByteArray(list);
            this.senderThreadHandler.post(new Runnable() { // from class: androidx.media3.exoplayer.rtsp.k
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    this.f5379a.lambda$send$0(bArrConvertMessageToByteArray, list);
                }
            });
        }
    }

    public RtspMessageChannel(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        try {
            Sender sender = this.sender;
            if (sender != null) {
                sender.close();
            }
            this.receiverLoader.release();
            Socket socket = this.socket;
            if (socket != null) {
                socket.close();
            }
            this.closed = true;
        } catch (Throwable th) {
            this.closed = true;
            throw th;
        }
    }

    public void open(Socket socket) throws IOException {
        this.socket = socket;
        this.sender = new Sender(socket.getOutputStream());
        this.receiverLoader.startLoading(new Receiver(socket.getInputStream()), new LoaderCallbackImpl(), 0);
    }

    public void registerInterleavedBinaryDataListener(int i2, InterleavedBinaryDataListener interleavedBinaryDataListener) {
        this.interleavedBinaryDataListeners.put(Integer.valueOf(i2), interleavedBinaryDataListener);
    }

    public void send(List<String> list) {
        Assertions.checkStateNotNull(this.sender);
        this.sender.send(list);
    }
}
