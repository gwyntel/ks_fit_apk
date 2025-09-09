package androidx.media3.exoplayer.rtsp;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.exoplayer.rtsp.RtpPacketReorderingQueue;
import java.util.Comparator;
import java.util.TreeSet;

/* loaded from: classes.dex */
final class RtpPacketReorderingQueue {
    private static final int QUEUE_SIZE_THRESHOLD_FOR_RESET = 5000;

    @GuardedBy("this")
    private int lastDequeuedSequenceNumber;

    @GuardedBy("this")
    private int lastReceivedSequenceNumber;

    @GuardedBy("this")
    private final TreeSet<RtpPacketContainer> packetQueue = new TreeSet<>(new Comparator() { // from class: androidx.media3.exoplayer.rtsp.c
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return RtpPacketReorderingQueue.lambda$new$0((RtpPacketReorderingQueue.RtpPacketContainer) obj, (RtpPacketReorderingQueue.RtpPacketContainer) obj2);
        }
    });

    @GuardedBy("this")
    private boolean started;

    /* JADX INFO: Access modifiers changed from: private */
    static final class RtpPacketContainer {
        public final RtpPacket packet;
        public final long receivedTimestampMs;

        public RtpPacketContainer(RtpPacket rtpPacket, long j2) {
            this.packet = rtpPacket;
            this.receivedTimestampMs = j2;
        }
    }

    public RtpPacketReorderingQueue() {
        reset();
    }

    private synchronized void addToQueue(RtpPacketContainer rtpPacketContainer) {
        this.lastReceivedSequenceNumber = rtpPacketContainer.packet.sequenceNumber;
        this.packetQueue.add(rtpPacketContainer);
    }

    private static int calculateSequenceNumberShift(int i2, int i3) {
        int iMin;
        int i4 = i2 - i3;
        return (Math.abs(i4) <= 1000 || (iMin = (Math.min(i2, i3) - Math.max(i2, i3)) + 65535) >= 1000) ? i4 : i2 < i3 ? iMin : -iMin;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$0(RtpPacketContainer rtpPacketContainer, RtpPacketContainer rtpPacketContainer2) {
        return calculateSequenceNumberShift(rtpPacketContainer.packet.sequenceNumber, rtpPacketContainer2.packet.sequenceNumber);
    }

    public synchronized boolean offer(RtpPacket rtpPacket, long j2) {
        if (this.packetQueue.size() >= 5000) {
            throw new IllegalStateException("Queue size limit of 5000 reached.");
        }
        int i2 = rtpPacket.sequenceNumber;
        if (!this.started) {
            reset();
            this.lastDequeuedSequenceNumber = RtpPacket.getPreviousSequenceNumber(i2);
            this.started = true;
            addToQueue(new RtpPacketContainer(rtpPacket, j2));
            return true;
        }
        if (Math.abs(calculateSequenceNumberShift(i2, RtpPacket.getNextSequenceNumber(this.lastReceivedSequenceNumber))) < 1000) {
            if (calculateSequenceNumberShift(i2, this.lastDequeuedSequenceNumber) <= 0) {
                return false;
            }
            addToQueue(new RtpPacketContainer(rtpPacket, j2));
            return true;
        }
        this.lastDequeuedSequenceNumber = RtpPacket.getPreviousSequenceNumber(i2);
        this.packetQueue.clear();
        addToQueue(new RtpPacketContainer(rtpPacket, j2));
        return true;
    }

    @Nullable
    public synchronized RtpPacket poll(long j2) {
        if (this.packetQueue.isEmpty()) {
            return null;
        }
        RtpPacketContainer rtpPacketContainerFirst = this.packetQueue.first();
        int i2 = rtpPacketContainerFirst.packet.sequenceNumber;
        if (i2 != RtpPacket.getNextSequenceNumber(this.lastDequeuedSequenceNumber) && j2 < rtpPacketContainerFirst.receivedTimestampMs) {
            return null;
        }
        this.packetQueue.pollFirst();
        this.lastDequeuedSequenceNumber = i2;
        return rtpPacketContainerFirst.packet;
    }

    public synchronized void reset() {
        this.packetQueue.clear();
        this.started = false;
        this.lastDequeuedSequenceNumber = -1;
        this.lastReceivedSequenceNumber = -1;
    }
}
