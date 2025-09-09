package androidx.health.platform.client.proto;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.health.platform.client.proto.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
final class RopeByteString extends ByteString {
    static final int[] minLengthByDepth = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903, Integer.MAX_VALUE};
    private static final long serialVersionUID = 1;
    private final ByteString left;
    private final int leftLength;
    private final ByteString right;
    private final int totalLength;
    private final int treeDepth;

    private static class Balancer {
        private final ArrayDeque<ByteString> prefixesStack;

        private Balancer() {
            this.prefixesStack = new ArrayDeque<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ByteString balance(ByteString byteString, ByteString byteString2) {
            doBalance(byteString);
            doBalance(byteString2);
            ByteString byteStringPop = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty()) {
                byteStringPop = new RopeByteString(this.prefixesStack.pop(), byteStringPop);
            }
            return byteStringPop;
        }

        private void doBalance(ByteString byteString) {
            if (byteString.isBalanced()) {
                insert(byteString);
                return;
            }
            if (byteString instanceof RopeByteString) {
                RopeByteString ropeByteString = (RopeByteString) byteString;
                doBalance(ropeByteString.left);
                doBalance(ropeByteString.right);
            } else {
                throw new IllegalArgumentException("Has a new type of ByteString been created? Found " + byteString.getClass());
            }
        }

        private int getDepthBinForLength(int i2) {
            int iBinarySearch = Arrays.binarySearch(RopeByteString.minLengthByDepth, i2);
            return iBinarySearch < 0 ? (-(iBinarySearch + 1)) - 1 : iBinarySearch;
        }

        private void insert(ByteString byteString) {
            int depthBinForLength = getDepthBinForLength(byteString.size());
            int iMinLength = RopeByteString.minLength(depthBinForLength + 1);
            if (this.prefixesStack.isEmpty() || this.prefixesStack.peek().size() >= iMinLength) {
                this.prefixesStack.push(byteString);
                return;
            }
            int iMinLength2 = RopeByteString.minLength(depthBinForLength);
            ByteString byteStringPop = this.prefixesStack.pop();
            while (true) {
                if (this.prefixesStack.isEmpty() || this.prefixesStack.peek().size() >= iMinLength2) {
                    break;
                } else {
                    byteStringPop = new RopeByteString(this.prefixesStack.pop(), byteStringPop);
                }
            }
            RopeByteString ropeByteString = new RopeByteString(byteStringPop, byteString);
            while (!this.prefixesStack.isEmpty()) {
                if (this.prefixesStack.peek().size() >= RopeByteString.minLength(getDepthBinForLength(ropeByteString.size()) + 1)) {
                    break;
                } else {
                    ropeByteString = new RopeByteString(this.prefixesStack.pop(), ropeByteString);
                }
            }
            this.prefixesStack.push(ropeByteString);
        }
    }

    private static final class PieceIterator implements Iterator<ByteString.LeafByteString> {
        private final ArrayDeque<RopeByteString> breadCrumbs;
        private ByteString.LeafByteString next;

        private ByteString.LeafByteString getLeafByLeft(ByteString byteString) {
            while (byteString instanceof RopeByteString) {
                RopeByteString ropeByteString = (RopeByteString) byteString;
                this.breadCrumbs.push(ropeByteString);
                byteString = ropeByteString.left;
            }
            return (ByteString.LeafByteString) byteString;
        }

        private ByteString.LeafByteString getNextNonEmptyLeaf() {
            ByteString.LeafByteString leafByLeft;
            do {
                ArrayDeque<RopeByteString> arrayDeque = this.breadCrumbs;
                if (arrayDeque == null || arrayDeque.isEmpty()) {
                    return null;
                }
                leafByLeft = getLeafByLeft(this.breadCrumbs.pop().right);
            } while (leafByLeft.isEmpty());
            return leafByLeft;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private PieceIterator(ByteString byteString) {
            if (!(byteString instanceof RopeByteString)) {
                this.breadCrumbs = null;
                this.next = (ByteString.LeafByteString) byteString;
                return;
            }
            RopeByteString ropeByteString = (RopeByteString) byteString;
            ArrayDeque<RopeByteString> arrayDeque = new ArrayDeque<>(ropeByteString.getTreeDepth());
            this.breadCrumbs = arrayDeque;
            arrayDeque.push(ropeByteString);
            this.next = getLeafByLeft(ropeByteString.left);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public ByteString.LeafByteString next() {
            ByteString.LeafByteString leafByteString = this.next;
            if (leafByteString == null) {
                throw new NoSuchElementException();
            }
            this.next = getNextNonEmptyLeaf();
            return leafByteString;
        }
    }

    static ByteString concatenate(ByteString byteString, ByteString byteString2) {
        if (byteString2.size() == 0) {
            return byteString;
        }
        if (byteString.size() == 0) {
            return byteString2;
        }
        int size = byteString.size() + byteString2.size();
        if (size < 128) {
            return concatenateBytes(byteString, byteString2);
        }
        if (byteString instanceof RopeByteString) {
            RopeByteString ropeByteString = (RopeByteString) byteString;
            if (ropeByteString.right.size() + byteString2.size() < 128) {
                return new RopeByteString(ropeByteString.left, concatenateBytes(ropeByteString.right, byteString2));
            }
            if (ropeByteString.left.getTreeDepth() > ropeByteString.right.getTreeDepth() && ropeByteString.getTreeDepth() > byteString2.getTreeDepth()) {
                return new RopeByteString(ropeByteString.left, new RopeByteString(ropeByteString.right, byteString2));
            }
        }
        return size >= minLength(Math.max(byteString.getTreeDepth(), byteString2.getTreeDepth()) + 1) ? new RopeByteString(byteString, byteString2) : new Balancer().balance(byteString, byteString2);
    }

    private static ByteString concatenateBytes(ByteString byteString, ByteString byteString2) {
        int size = byteString.size();
        int size2 = byteString2.size();
        byte[] bArr = new byte[size + size2];
        byteString.copyTo(bArr, 0, 0, size);
        byteString2.copyTo(bArr, 0, size, size2);
        return ByteString.wrap(bArr);
    }

    private boolean equalsFragments(ByteString byteString) {
        PieceIterator pieceIterator = new PieceIterator(this);
        ByteString.LeafByteString next = pieceIterator.next();
        PieceIterator pieceIterator2 = new PieceIterator(byteString);
        ByteString.LeafByteString next2 = pieceIterator2.next();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int size = next.size() - i2;
            int size2 = next2.size() - i3;
            int iMin = Math.min(size, size2);
            if (!(i2 == 0 ? next.equalsRange(next2, i3, iMin) : next2.equalsRange(next, i2, iMin))) {
                return false;
            }
            i4 += iMin;
            int i5 = this.totalLength;
            if (i4 >= i5) {
                if (i4 == i5) {
                    return true;
                }
                throw new IllegalStateException();
            }
            if (iMin == size) {
                i2 = 0;
                next = pieceIterator.next();
            } else {
                i2 += iMin;
                next = next;
            }
            if (iMin == size2) {
                next2 = pieceIterator2.next();
                i3 = 0;
            } else {
                i3 += iMin;
            }
        }
    }

    static int minLength(int i2) {
        int[] iArr = minLengthByDepth;
        if (i2 >= iArr.length) {
            return Integer.MAX_VALUE;
        }
        return iArr[i2];
    }

    static RopeByteString newInstanceForTest(ByteString byteString, ByteString byteString2) {
        return new RopeByteString(byteString, byteString2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        ArrayList arrayList = new ArrayList();
        PieceIterator pieceIterator = new PieceIterator(this);
        while (pieceIterator.hasNext()) {
            arrayList.add(pieceIterator.next().asReadOnlyByteBuffer());
        }
        return arrayList;
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public byte byteAt(int i2) {
        ByteString.checkIndex(i2, this.totalLength);
        return internalByteAt(i2);
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public void copyTo(ByteBuffer byteBuffer) {
        this.left.copyTo(byteBuffer);
        this.right.copyTo(byteBuffer);
    }

    @Override // androidx.health.platform.client.proto.ByteString
    protected void copyToInternal(byte[] bArr, int i2, int i3, int i4) {
        int i5 = i2 + i4;
        int i6 = this.leftLength;
        if (i5 <= i6) {
            this.left.copyToInternal(bArr, i2, i3, i4);
        } else {
            if (i2 >= i6) {
                this.right.copyToInternal(bArr, i2 - i6, i3, i4);
                return;
            }
            int i7 = i6 - i2;
            this.left.copyToInternal(bArr, i2, i3, i7);
            this.right.copyToInternal(bArr, 0, i3 + i7, i4 - i7);
        }
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ByteString)) {
            return false;
        }
        ByteString byteString = (ByteString) obj;
        if (this.totalLength != byteString.size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        int iPeekCachedHashCode = peekCachedHashCode();
        int iPeekCachedHashCode2 = byteString.peekCachedHashCode();
        if (iPeekCachedHashCode == 0 || iPeekCachedHashCode2 == 0 || iPeekCachedHashCode == iPeekCachedHashCode2) {
            return equalsFragments(byteString);
        }
        return false;
    }

    @Override // androidx.health.platform.client.proto.ByteString
    protected int getTreeDepth() {
        return this.treeDepth;
    }

    @Override // androidx.health.platform.client.proto.ByteString
    byte internalByteAt(int i2) {
        int i3 = this.leftLength;
        return i2 < i3 ? this.left.internalByteAt(i2) : this.right.internalByteAt(i2 - i3);
    }

    @Override // androidx.health.platform.client.proto.ByteString
    protected boolean isBalanced() {
        return this.totalLength >= minLength(this.treeDepth);
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public boolean isValidUtf8() {
        int iPartialIsValidUtf8 = this.left.partialIsValidUtf8(0, 0, this.leftLength);
        ByteString byteString = this.right;
        return byteString.partialIsValidUtf8(iPartialIsValidUtf8, 0, byteString.size()) == 0;
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public CodedInputStream newCodedInput() {
        return CodedInputStream.a(asReadOnlyByteBufferList(), true);
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public InputStream newInput() {
        return new RopeInputStream();
    }

    @Override // androidx.health.platform.client.proto.ByteString
    protected int partialHash(int i2, int i3, int i4) {
        int i5 = i3 + i4;
        int i6 = this.leftLength;
        if (i5 <= i6) {
            return this.left.partialHash(i2, i3, i4);
        }
        if (i3 >= i6) {
            return this.right.partialHash(i2, i3 - i6, i4);
        }
        int i7 = i6 - i3;
        return this.right.partialHash(this.left.partialHash(i2, i3, i7), 0, i4 - i7);
    }

    @Override // androidx.health.platform.client.proto.ByteString
    protected int partialIsValidUtf8(int i2, int i3, int i4) {
        int i5 = i3 + i4;
        int i6 = this.leftLength;
        if (i5 <= i6) {
            return this.left.partialIsValidUtf8(i2, i3, i4);
        }
        if (i3 >= i6) {
            return this.right.partialIsValidUtf8(i2, i3 - i6, i4);
        }
        int i7 = i6 - i3;
        return this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(i2, i3, i7), 0, i4 - i7);
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public int size() {
        return this.totalLength;
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public ByteString substring(int i2, int i3) {
        int iCheckRange = ByteString.checkRange(i2, i3, this.totalLength);
        if (iCheckRange == 0) {
            return ByteString.EMPTY;
        }
        if (iCheckRange == this.totalLength) {
            return this;
        }
        int i4 = this.leftLength;
        return i3 <= i4 ? this.left.substring(i2, i3) : i2 >= i4 ? this.right.substring(i2 - i4, i3 - i4) : new RopeByteString(this.left.substring(i2), this.right.substring(0, i3 - this.leftLength));
    }

    @Override // androidx.health.platform.client.proto.ByteString
    protected String toStringInternal(Charset charset) {
        return new String(toByteArray(), charset);
    }

    Object writeReplace() {
        return ByteString.wrap(toByteArray());
    }

    @Override // androidx.health.platform.client.proto.ByteString
    public void writeTo(OutputStream outputStream) throws IOException {
        this.left.writeTo(outputStream);
        this.right.writeTo(outputStream);
    }

    @Override // androidx.health.platform.client.proto.ByteString
    void writeToInternal(OutputStream outputStream, int i2, int i3) throws IOException {
        int i4 = i2 + i3;
        int i5 = this.leftLength;
        if (i4 <= i5) {
            this.left.writeToInternal(outputStream, i2, i3);
        } else {
            if (i2 >= i5) {
                this.right.writeToInternal(outputStream, i2 - i5, i3);
                return;
            }
            int i6 = i5 - i2;
            this.left.writeToInternal(outputStream, i2, i6);
            this.right.writeToInternal(outputStream, 0, i3 - i6);
        }
    }

    @Override // androidx.health.platform.client.proto.ByteString
    void writeToReverse(ByteOutput byteOutput) throws IOException {
        this.right.writeToReverse(byteOutput);
        this.left.writeToReverse(byteOutput);
    }

    private RopeByteString(ByteString byteString, ByteString byteString2) {
        this.left = byteString;
        this.right = byteString2;
        int size = byteString.size();
        this.leftLength = size;
        this.totalLength = size + byteString2.size();
        this.treeDepth = Math.max(byteString.getTreeDepth(), byteString2.getTreeDepth()) + 1;
    }

    @Override // androidx.health.platform.client.proto.ByteString, java.lang.Iterable
    /* renamed from: iterator */
    public Iterator<Byte> iterator2() {
        return new ByteString.AbstractByteIterator() { // from class: androidx.health.platform.client.proto.RopeByteString.1

            /* renamed from: a, reason: collision with root package name */
            final PieceIterator f4479a;

            /* renamed from: b, reason: collision with root package name */
            ByteString.ByteIterator f4480b = nextPiece();

            {
                this.f4479a = new PieceIterator(RopeByteString.this);
            }

            /* JADX WARN: Type inference failed for: r0v5, types: [androidx.health.platform.client.proto.ByteString$ByteIterator] */
            private ByteString.ByteIterator nextPiece() {
                if (this.f4479a.hasNext()) {
                    return this.f4479a.next().iterator2();
                }
                return null;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.f4480b != null;
            }

            @Override // androidx.health.platform.client.proto.ByteString.ByteIterator
            public byte nextByte() {
                ByteString.ByteIterator byteIterator = this.f4480b;
                if (byteIterator == null) {
                    throw new NoSuchElementException();
                }
                byte bNextByte = byteIterator.nextByte();
                if (!this.f4480b.hasNext()) {
                    this.f4480b = nextPiece();
                }
                return bNextByte;
            }
        };
    }

    @Override // androidx.health.platform.client.proto.ByteString
    void writeTo(ByteOutput byteOutput) throws IOException {
        this.left.writeTo(byteOutput);
        this.right.writeTo(byteOutput);
    }

    private class RopeInputStream extends InputStream {
        private ByteString.LeafByteString currentPiece;
        private int currentPieceIndex;
        private int currentPieceOffsetInRope;
        private int currentPieceSize;
        private int mark;
        private PieceIterator pieceIterator;

        public RopeInputStream() {
            initialize();
        }

        private void advanceIfCurrentPieceFullyRead() {
            if (this.currentPiece != null) {
                int i2 = this.currentPieceIndex;
                int i3 = this.currentPieceSize;
                if (i2 == i3) {
                    this.currentPieceOffsetInRope += i3;
                    this.currentPieceIndex = 0;
                    if (!this.pieceIterator.hasNext()) {
                        this.currentPiece = null;
                        this.currentPieceSize = 0;
                    } else {
                        ByteString.LeafByteString next = this.pieceIterator.next();
                        this.currentPiece = next;
                        this.currentPieceSize = next.size();
                    }
                }
            }
        }

        private int availableInternal() {
            return RopeByteString.this.size() - (this.currentPieceOffsetInRope + this.currentPieceIndex);
        }

        private void initialize() {
            PieceIterator pieceIterator = new PieceIterator(RopeByteString.this);
            this.pieceIterator = pieceIterator;
            ByteString.LeafByteString next = pieceIterator.next();
            this.currentPiece = next;
            this.currentPieceSize = next.size();
            this.currentPieceIndex = 0;
            this.currentPieceOffsetInRope = 0;
        }

        private int readSkipInternal(byte[] bArr, int i2, int i3) {
            int i4 = i3;
            while (i4 > 0) {
                advanceIfCurrentPieceFullyRead();
                if (this.currentPiece == null) {
                    break;
                }
                int iMin = Math.min(this.currentPieceSize - this.currentPieceIndex, i4);
                if (bArr != null) {
                    this.currentPiece.copyTo(bArr, this.currentPieceIndex, i2, iMin);
                    i2 += iMin;
                }
                this.currentPieceIndex += iMin;
                i4 -= iMin;
            }
            return i3 - i4;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return availableInternal();
        }

        @Override // java.io.InputStream
        public void mark(int i2) {
            this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return true;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) {
            bArr.getClass();
            if (i2 < 0 || i3 < 0 || i3 > bArr.length - i2) {
                throw new IndexOutOfBoundsException();
            }
            int skipInternal = readSkipInternal(bArr, i2, i3);
            if (skipInternal != 0) {
                return skipInternal;
            }
            if (i3 > 0 || availableInternal() == 0) {
                return -1;
            }
            return skipInternal;
        }

        @Override // java.io.InputStream
        public synchronized void reset() {
            initialize();
            readSkipInternal(null, 0, this.mark);
        }

        @Override // java.io.InputStream
        public long skip(long j2) {
            if (j2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (j2 > 2147483647L) {
                j2 = 2147483647L;
            }
            return readSkipInternal(null, 0, (int) j2);
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            advanceIfCurrentPieceFullyRead();
            ByteString.LeafByteString leafByteString = this.currentPiece;
            if (leafByteString == null) {
                return -1;
            }
            int i2 = this.currentPieceIndex;
            this.currentPieceIndex = i2 + 1;
            return leafByteString.byteAt(i2) & 255;
        }
    }
}
