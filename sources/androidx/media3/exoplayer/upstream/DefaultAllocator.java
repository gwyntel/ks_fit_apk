package androidx.media3.exoplayer.upstream;

import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.upstream.Allocator;
import java.util.Arrays;

@UnstableApi
/* loaded from: classes2.dex */
public final class DefaultAllocator implements Allocator {
    private static final int AVAILABLE_EXTRA_CAPACITY = 100;
    private int allocatedCount;
    private Allocation[] availableAllocations;
    private int availableCount;
    private final int individualAllocationSize;

    @Nullable
    private final byte[] initialAllocationBlock;
    private int targetBufferSize;
    private final boolean trimOnReset;

    public DefaultAllocator(boolean z2, int i2) {
        this(z2, i2, 0);
    }

    @Override // androidx.media3.exoplayer.upstream.Allocator
    public synchronized Allocation allocate() {
        Allocation allocation;
        try {
            this.allocatedCount++;
            int i2 = this.availableCount;
            if (i2 > 0) {
                Allocation[] allocationArr = this.availableAllocations;
                int i3 = i2 - 1;
                this.availableCount = i3;
                allocation = (Allocation) Assertions.checkNotNull(allocationArr[i3]);
                this.availableAllocations[this.availableCount] = null;
            } else {
                allocation = new Allocation(new byte[this.individualAllocationSize], 0);
                int i4 = this.allocatedCount;
                Allocation[] allocationArr2 = this.availableAllocations;
                if (i4 > allocationArr2.length) {
                    this.availableAllocations = (Allocation[]) Arrays.copyOf(allocationArr2, allocationArr2.length * 2);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return allocation;
    }

    @Override // androidx.media3.exoplayer.upstream.Allocator
    public int getIndividualAllocationLength() {
        return this.individualAllocationSize;
    }

    @Override // androidx.media3.exoplayer.upstream.Allocator
    public synchronized int getTotalBytesAllocated() {
        return this.allocatedCount * this.individualAllocationSize;
    }

    @Override // androidx.media3.exoplayer.upstream.Allocator
    public synchronized void release(Allocation allocation) {
        Allocation[] allocationArr = this.availableAllocations;
        int i2 = this.availableCount;
        this.availableCount = i2 + 1;
        allocationArr[i2] = allocation;
        this.allocatedCount--;
        notifyAll();
    }

    public synchronized void reset() {
        if (this.trimOnReset) {
            setTargetBufferSize(0);
        }
    }

    public synchronized void setTargetBufferSize(int i2) {
        boolean z2 = i2 < this.targetBufferSize;
        this.targetBufferSize = i2;
        if (z2) {
            trim();
        }
    }

    @Override // androidx.media3.exoplayer.upstream.Allocator
    public synchronized void trim() {
        try {
            int i2 = 0;
            int iMax = Math.max(0, Util.ceilDivide(this.targetBufferSize, this.individualAllocationSize) - this.allocatedCount);
            int i3 = this.availableCount;
            if (iMax >= i3) {
                return;
            }
            if (this.initialAllocationBlock != null) {
                int i4 = i3 - 1;
                while (i2 <= i4) {
                    Allocation allocation = (Allocation) Assertions.checkNotNull(this.availableAllocations[i2]);
                    if (allocation.data == this.initialAllocationBlock) {
                        i2++;
                    } else {
                        Allocation allocation2 = (Allocation) Assertions.checkNotNull(this.availableAllocations[i4]);
                        if (allocation2.data != this.initialAllocationBlock) {
                            i4--;
                        } else {
                            Allocation[] allocationArr = this.availableAllocations;
                            allocationArr[i2] = allocation2;
                            allocationArr[i4] = allocation;
                            i4--;
                            i2++;
                        }
                    }
                }
                iMax = Math.max(iMax, i2);
                if (iMax >= this.availableCount) {
                    return;
                }
            }
            Arrays.fill(this.availableAllocations, iMax, this.availableCount, (Object) null);
            this.availableCount = iMax;
        } catch (Throwable th) {
            throw th;
        }
    }

    public DefaultAllocator(boolean z2, int i2, int i3) {
        Assertions.checkArgument(i2 > 0);
        Assertions.checkArgument(i3 >= 0);
        this.trimOnReset = z2;
        this.individualAllocationSize = i2;
        this.availableCount = i3;
        this.availableAllocations = new Allocation[i3 + 100];
        if (i3 <= 0) {
            this.initialAllocationBlock = null;
            return;
        }
        this.initialAllocationBlock = new byte[i3 * i2];
        for (int i4 = 0; i4 < i3; i4++) {
            this.availableAllocations[i4] = new Allocation(this.initialAllocationBlock, i4 * i2);
        }
    }

    @Override // androidx.media3.exoplayer.upstream.Allocator
    public synchronized void release(@Nullable Allocator.AllocationNode allocationNode) {
        while (allocationNode != null) {
            try {
                Allocation[] allocationArr = this.availableAllocations;
                int i2 = this.availableCount;
                this.availableCount = i2 + 1;
                allocationArr[i2] = allocationNode.getAllocation();
                this.allocatedCount--;
                allocationNode = allocationNode.next();
            } catch (Throwable th) {
                throw th;
            }
        }
        notifyAll();
    }
}
