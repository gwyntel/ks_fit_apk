package androidx.media3.exoplayer.rtsp;

/* loaded from: classes.dex */
final class RtspDescribeResponse {
    public final RtspHeaders headers;
    public final SessionDescription sessionDescription;
    public final int status;

    public RtspDescribeResponse(RtspHeaders rtspHeaders, int i2, SessionDescription sessionDescription) {
        this.headers = rtspHeaders;
        this.status = i2;
        this.sessionDescription = sessionDescription;
    }
}
