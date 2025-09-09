package com.cicada.player.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@NativeUsed
/* loaded from: classes3.dex */
public class ContentDataSource {
    private static final int EINVAL = 22;
    private static final int EIO = 5;
    private static final int ENOENT = 2;
    private static final int SEEK_CUR = 1;
    private static final int SEEK_END = 2;
    private static final int SEEK_SET = 0;
    private static final int SEEK_SIZE = 65536;
    private static final String TAG = "ContentDataSource";
    private static Context sContext;
    private String mUri = null;
    private InputStream mStream = null;
    private int mStreamSize = -1;
    private long mOffset = 0;

    public static void setContext(Context context) {
        if (context == null || sContext != null) {
            return;
        }
        sContext = context.getApplicationContext();
    }

    private long skip(long j2) throws IOException {
        try {
            return this.mStream.skip(j2);
        } catch (IOException unused) {
            long j3 = (int) (this.mOffset + j2);
            close();
            open(0);
            return this.mStream.skip(j3);
        }
    }

    public void close() throws IOException {
        InputStream inputStream = this.mStream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public int open(int i2) throws FileNotFoundException {
        Context context;
        if (TextUtils.isEmpty(this.mUri) || (context = sContext) == null) {
            return -22;
        }
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(Uri.parse(this.mUri));
            this.mStream = inputStreamOpenInputStream;
            if (inputStreamOpenInputStream == null) {
                return -22;
            }
            try {
                this.mOffset = 0L;
                this.mStreamSize = inputStreamOpenInputStream.available();
                return 0;
            } catch (IOException unused) {
                return -5;
            }
        } catch (FileNotFoundException unused2) {
            return -2;
        }
    }

    public int read(byte[] bArr) throws IOException {
        InputStream inputStream = this.mStream;
        if (inputStream == null) {
            return -22;
        }
        try {
            int i2 = inputStream.read(bArr);
            this.mOffset += i2;
            return i2;
        } catch (IOException unused) {
            return -5;
        }
    }

    public long seek(long j2, int i2) {
        InputStream inputStream = this.mStream;
        if (inputStream == null) {
            return -22L;
        }
        if (i2 == 65536) {
            int i3 = this.mStreamSize;
            if (i3 <= 0) {
                return -22L;
            }
            return i3;
        }
        if (i2 == 2) {
            try {
                j2 = inputStream.available();
            } catch (IOException unused) {
                return -5L;
            }
        } else if (i2 == 0) {
            j2 -= this.mOffset;
        } else if (i2 != 1) {
            return -22L;
        }
        try {
            long jSkip = this.mOffset + skip(j2);
            this.mOffset = jSkip;
            return jSkip;
        } catch (IOException unused2) {
            return -5L;
        }
    }

    public void setUri(String str) {
        this.mUri = str;
    }
}
