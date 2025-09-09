package androidx.media3.exoplayer.source;

import androidx.annotation.Nullable;
import androidx.media3.common.DataReader;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.CryptoInfo;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.source.SampleQueue;
import androidx.media3.exoplayer.upstream.Allocation;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.extractor.TrackOutput;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes2.dex */
class SampleDataQueue {
    private static final int INITIAL_SCRATCH_SIZE = 32;
    private final int allocationLength;
    private final Allocator allocator;
    private AllocationNode firstAllocationNode;
    private AllocationNode readAllocationNode;
    private final ParsableByteArray scratch;
    private long totalBytesWritten;
    private AllocationNode writeAllocationNode;

    private static final class AllocationNode implements Allocator.AllocationNode {

        @Nullable
        public Allocation allocation;
        public long endPosition;

        @Nullable
        public AllocationNode next;
        public long startPosition;

        public AllocationNode(long j2, int i2) {
            reset(j2, i2);
        }

        public AllocationNode clear() {
            this.allocation = null;
            AllocationNode allocationNode = this.next;
            this.next = null;
            return allocationNode;
        }

        @Override // androidx.media3.exoplayer.upstream.Allocator.AllocationNode
        public Allocation getAllocation() {
            return (Allocation) Assertions.checkNotNull(this.allocation);
        }

        public void initialize(Allocation allocation, AllocationNode allocationNode) {
            this.allocation = allocation;
            this.next = allocationNode;
        }

        @Override // androidx.media3.exoplayer.upstream.Allocator.AllocationNode
        @Nullable
        public Allocator.AllocationNode next() {
            AllocationNode allocationNode = this.next;
            if (allocationNode == null || allocationNode.allocation == null) {
                return null;
            }
            return allocationNode;
        }

        public void reset(long j2, int i2) {
            Assertions.checkState(this.allocation == null);
            this.startPosition = j2;
            this.endPosition = j2 + i2;
        }

        public int translateOffset(long j2) {
            return ((int) (j2 - this.startPosition)) + this.allocation.offset;
        }
    }

    public SampleDataQueue(Allocator allocator) {
        this.allocator = allocator;
        int individualAllocationLength = allocator.getIndividualAllocationLength();
        this.allocationLength = individualAllocationLength;
        this.scratch = new ParsableByteArray(32);
        AllocationNode allocationNode = new AllocationNode(0L, individualAllocationLength);
        this.firstAllocationNode = allocationNode;
        this.readAllocationNode = allocationNode;
        this.writeAllocationNode = allocationNode;
    }

    private void clearAllocationNodes(AllocationNode allocationNode) {
        if (allocationNode.allocation == null) {
            return;
        }
        this.allocator.release(allocationNode);
        allocationNode.clear();
    }

    private static AllocationNode getNodeContainingPosition(AllocationNode allocationNode, long j2) {
        while (j2 >= allocationNode.endPosition) {
            allocationNode = allocationNode.next;
        }
        return allocationNode;
    }

    private void postAppend(int i2) {
        long j2 = this.totalBytesWritten + i2;
        this.totalBytesWritten = j2;
        AllocationNode allocationNode = this.writeAllocationNode;
        if (j2 == allocationNode.endPosition) {
            this.writeAllocationNode = allocationNode.next;
        }
    }

    private int preAppend(int i2) {
        AllocationNode allocationNode = this.writeAllocationNode;
        if (allocationNode.allocation == null) {
            allocationNode.initialize(this.allocator.allocate(), new AllocationNode(this.writeAllocationNode.endPosition, this.allocationLength));
        }
        return Math.min(i2, (int) (this.writeAllocationNode.endPosition - this.totalBytesWritten));
    }

    private static AllocationNode readData(AllocationNode allocationNode, long j2, ByteBuffer byteBuffer, int i2) {
        AllocationNode nodeContainingPosition = getNodeContainingPosition(allocationNode, j2);
        while (i2 > 0) {
            int iMin = Math.min(i2, (int) (nodeContainingPosition.endPosition - j2));
            byteBuffer.put(nodeContainingPosition.allocation.data, nodeContainingPosition.translateOffset(j2), iMin);
            i2 -= iMin;
            j2 += iMin;
            if (j2 == nodeContainingPosition.endPosition) {
                nodeContainingPosition = nodeContainingPosition.next;
            }
        }
        return nodeContainingPosition;
    }

    private static AllocationNode readEncryptionData(AllocationNode allocationNode, DecoderInputBuffer decoderInputBuffer, SampleQueue.SampleExtrasHolder sampleExtrasHolder, ParsableByteArray parsableByteArray) {
        long j2 = sampleExtrasHolder.offset;
        int unsignedShort = 1;
        parsableByteArray.reset(1);
        AllocationNode data = readData(allocationNode, j2, parsableByteArray.getData(), 1);
        long j3 = j2 + 1;
        byte b2 = parsableByteArray.getData()[0];
        boolean z2 = (b2 & 128) != 0;
        int i2 = b2 & Byte.MAX_VALUE;
        CryptoInfo cryptoInfo = decoderInputBuffer.cryptoInfo;
        byte[] bArr = cryptoInfo.iv;
        if (bArr == null) {
            cryptoInfo.iv = new byte[16];
        } else {
            Arrays.fill(bArr, (byte) 0);
        }
        AllocationNode data2 = readData(data, j3, cryptoInfo.iv, i2);
        long j4 = j3 + i2;
        if (z2) {
            parsableByteArray.reset(2);
            data2 = readData(data2, j4, parsableByteArray.getData(), 2);
            j4 += 2;
            unsignedShort = parsableByteArray.readUnsignedShort();
        }
        int i3 = unsignedShort;
        int[] iArr = cryptoInfo.numBytesOfClearData;
        if (iArr == null || iArr.length < i3) {
            iArr = new int[i3];
        }
        int[] iArr2 = iArr;
        int[] iArr3 = cryptoInfo.numBytesOfEncryptedData;
        if (iArr3 == null || iArr3.length < i3) {
            iArr3 = new int[i3];
        }
        int[] iArr4 = iArr3;
        if (z2) {
            int i4 = i3 * 6;
            parsableByteArray.reset(i4);
            data2 = readData(data2, j4, parsableByteArray.getData(), i4);
            j4 += i4;
            parsableByteArray.setPosition(0);
            for (int i5 = 0; i5 < i3; i5++) {
                iArr2[i5] = parsableByteArray.readUnsignedShort();
                iArr4[i5] = parsableByteArray.readUnsignedIntToInt();
            }
        } else {
            iArr2[0] = 0;
            iArr4[0] = sampleExtrasHolder.size - ((int) (j4 - sampleExtrasHolder.offset));
        }
        TrackOutput.CryptoData cryptoData = (TrackOutput.CryptoData) Util.castNonNull(sampleExtrasHolder.cryptoData);
        cryptoInfo.set(i3, iArr2, iArr4, cryptoData.encryptionKey, cryptoInfo.iv, cryptoData.cryptoMode, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
        long j5 = sampleExtrasHolder.offset;
        int i6 = (int) (j4 - j5);
        sampleExtrasHolder.offset = j5 + i6;
        sampleExtrasHolder.size -= i6;
        return data2;
    }

    private static AllocationNode readSampleData(AllocationNode allocationNode, DecoderInputBuffer decoderInputBuffer, SampleQueue.SampleExtrasHolder sampleExtrasHolder, ParsableByteArray parsableByteArray) {
        if (decoderInputBuffer.isEncrypted()) {
            allocationNode = readEncryptionData(allocationNode, decoderInputBuffer, sampleExtrasHolder, parsableByteArray);
        }
        if (!decoderInputBuffer.hasSupplementalData()) {
            decoderInputBuffer.ensureSpaceForWrite(sampleExtrasHolder.size);
            return readData(allocationNode, sampleExtrasHolder.offset, decoderInputBuffer.data, sampleExtrasHolder.size);
        }
        parsableByteArray.reset(4);
        AllocationNode data = readData(allocationNode, sampleExtrasHolder.offset, parsableByteArray.getData(), 4);
        int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        sampleExtrasHolder.offset += 4;
        sampleExtrasHolder.size -= 4;
        decoderInputBuffer.ensureSpaceForWrite(unsignedIntToInt);
        AllocationNode data2 = readData(data, sampleExtrasHolder.offset, decoderInputBuffer.data, unsignedIntToInt);
        sampleExtrasHolder.offset += unsignedIntToInt;
        int i2 = sampleExtrasHolder.size - unsignedIntToInt;
        sampleExtrasHolder.size = i2;
        decoderInputBuffer.resetSupplementalData(i2);
        return readData(data2, sampleExtrasHolder.offset, decoderInputBuffer.supplementalData, sampleExtrasHolder.size);
    }

    public void discardDownstreamTo(long j2) {
        AllocationNode allocationNode;
        if (j2 == -1) {
            return;
        }
        while (true) {
            allocationNode = this.firstAllocationNode;
            if (j2 < allocationNode.endPosition) {
                break;
            }
            this.allocator.release(allocationNode.allocation);
            this.firstAllocationNode = this.firstAllocationNode.clear();
        }
        if (this.readAllocationNode.startPosition < allocationNode.startPosition) {
            this.readAllocationNode = allocationNode;
        }
    }

    public void discardUpstreamSampleBytes(long j2) {
        Assertions.checkArgument(j2 <= this.totalBytesWritten);
        this.totalBytesWritten = j2;
        if (j2 != 0) {
            AllocationNode allocationNode = this.firstAllocationNode;
            if (j2 != allocationNode.startPosition) {
                while (this.totalBytesWritten > allocationNode.endPosition) {
                    allocationNode = allocationNode.next;
                }
                AllocationNode allocationNode2 = (AllocationNode) Assertions.checkNotNull(allocationNode.next);
                clearAllocationNodes(allocationNode2);
                AllocationNode allocationNode3 = new AllocationNode(allocationNode.endPosition, this.allocationLength);
                allocationNode.next = allocationNode3;
                if (this.totalBytesWritten == allocationNode.endPosition) {
                    allocationNode = allocationNode3;
                }
                this.writeAllocationNode = allocationNode;
                if (this.readAllocationNode == allocationNode2) {
                    this.readAllocationNode = allocationNode3;
                    return;
                }
                return;
            }
        }
        clearAllocationNodes(this.firstAllocationNode);
        AllocationNode allocationNode4 = new AllocationNode(this.totalBytesWritten, this.allocationLength);
        this.firstAllocationNode = allocationNode4;
        this.readAllocationNode = allocationNode4;
        this.writeAllocationNode = allocationNode4;
    }

    public long getTotalBytesWritten() {
        return this.totalBytesWritten;
    }

    public void peekToBuffer(DecoderInputBuffer decoderInputBuffer, SampleQueue.SampleExtrasHolder sampleExtrasHolder) {
        readSampleData(this.readAllocationNode, decoderInputBuffer, sampleExtrasHolder, this.scratch);
    }

    public void readToBuffer(DecoderInputBuffer decoderInputBuffer, SampleQueue.SampleExtrasHolder sampleExtrasHolder) {
        this.readAllocationNode = readSampleData(this.readAllocationNode, decoderInputBuffer, sampleExtrasHolder, this.scratch);
    }

    public void reset() {
        clearAllocationNodes(this.firstAllocationNode);
        this.firstAllocationNode.reset(0L, this.allocationLength);
        AllocationNode allocationNode = this.firstAllocationNode;
        this.readAllocationNode = allocationNode;
        this.writeAllocationNode = allocationNode;
        this.totalBytesWritten = 0L;
        this.allocator.trim();
    }

    public void rewind() {
        this.readAllocationNode = this.firstAllocationNode;
    }

    public int sampleData(DataReader dataReader, int i2, boolean z2) throws IOException {
        int iPreAppend = preAppend(i2);
        AllocationNode allocationNode = this.writeAllocationNode;
        int i3 = dataReader.read(allocationNode.allocation.data, allocationNode.translateOffset(this.totalBytesWritten), iPreAppend);
        if (i3 != -1) {
            postAppend(i3);
            return i3;
        }
        if (z2) {
            return -1;
        }
        throw new EOFException();
    }

    private static AllocationNode readData(AllocationNode allocationNode, long j2, byte[] bArr, int i2) {
        AllocationNode nodeContainingPosition = getNodeContainingPosition(allocationNode, j2);
        int i3 = i2;
        while (i3 > 0) {
            int iMin = Math.min(i3, (int) (nodeContainingPosition.endPosition - j2));
            System.arraycopy(nodeContainingPosition.allocation.data, nodeContainingPosition.translateOffset(j2), bArr, i2 - i3, iMin);
            i3 -= iMin;
            j2 += iMin;
            if (j2 == nodeContainingPosition.endPosition) {
                nodeContainingPosition = nodeContainingPosition.next;
            }
        }
        return nodeContainingPosition;
    }

    public void sampleData(ParsableByteArray parsableByteArray, int i2) {
        while (i2 > 0) {
            int iPreAppend = preAppend(i2);
            AllocationNode allocationNode = this.writeAllocationNode;
            parsableByteArray.readBytes(allocationNode.allocation.data, allocationNode.translateOffset(this.totalBytesWritten), iPreAppend);
            i2 -= iPreAppend;
            postAppend(iPreAppend);
        }
    }
}
