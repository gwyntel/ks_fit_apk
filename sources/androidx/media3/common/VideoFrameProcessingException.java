package androidx.media3.common;

import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public final class VideoFrameProcessingException extends Exception {
    public final long presentationTimeUs;

    public VideoFrameProcessingException(String str) {
        this(str, C.TIME_UNSET);
    }

    public static VideoFrameProcessingException from(Exception exc) {
        return from(exc, C.TIME_UNSET);
    }

    public VideoFrameProcessingException(String str, long j2) {
        super(str);
        this.presentationTimeUs = j2;
    }

    public static VideoFrameProcessingException from(Exception exc, long j2) {
        return exc instanceof VideoFrameProcessingException ? (VideoFrameProcessingException) exc : new VideoFrameProcessingException(exc, j2);
    }

    public VideoFrameProcessingException(String str, Throwable th) {
        this(str, th, C.TIME_UNSET);
    }

    public VideoFrameProcessingException(String str, Throwable th, long j2) {
        super(str, th);
        this.presentationTimeUs = j2;
    }

    public VideoFrameProcessingException(Throwable th) {
        this(th, C.TIME_UNSET);
    }

    public VideoFrameProcessingException(Throwable th, long j2) {
        super(th);
        this.presentationTimeUs = j2;
    }
}
