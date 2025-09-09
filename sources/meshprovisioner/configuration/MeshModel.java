package meshprovisioner.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.fastjson.annotation.JSONField;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public abstract class MeshModel implements Parcelable {
    public static final int DEFAULT_MODEL_ID = -1;
    public int credentialFlag;
    public List<Integer> mBoundAppKeyIndexes;
    public Map<Integer, String> mBoundAppKeys;
    public int mModelId;

    @JSONField(deserialize = false, serialize = false)
    public List<byte[]> mSubscriptionAddress;
    public int publicationResolution;
    public int publicationSteps;
    public byte[] publishAddress;
    public byte[] publishAppKeyIndex;
    public int publishPeriod;
    public int publishRetransmitCount;
    public int publishRetransmitIntervalSteps;
    public int publishTtl;

    public MeshModel() {
        this.mBoundAppKeyIndexes = new ArrayList();
        this.mBoundAppKeys = new LinkedHashMap();
        this.publishTtl = 255;
        this.publishPeriod = 0;
        this.publishRetransmitCount = 1;
        this.publishRetransmitIntervalSteps = 1;
        this.mSubscriptionAddress = new ArrayList();
        this.publicationSteps = 0;
        this.publicationResolution = 0;
        this.mModelId = -1;
    }

    private boolean checkIfAlreadySubscribed(byte[] bArr) {
        Iterator<byte[]> it = this.mSubscriptionAddress.iterator();
        while (it.hasNext()) {
            if (Arrays.equals(it.next(), bArr)) {
                return true;
            }
        }
        return false;
    }

    private int getIndex(byte[] bArr) {
        Iterator<byte[]> it = this.mSubscriptionAddress.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (Arrays.equals(it.next(), bArr)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private void sortAppKeys(HashMap<Integer, String> map) {
        ArrayList<Integer> arrayList = new ArrayList(map.keySet());
        Collections.sort(arrayList);
        for (Integer num : arrayList) {
            num.intValue();
            this.mBoundAppKeys.put(num, map.get(num));
        }
    }

    public String getBoundAppKey(int i2) {
        return this.mBoundAppKeys.get(Integer.valueOf(i2));
    }

    public List<Integer> getBoundAppKeyIndexes() {
        return this.mBoundAppKeyIndexes;
    }

    public Map<Integer, String> getBoundAppkeys() {
        return this.mBoundAppKeys;
    }

    public int getCredentialFlag() {
        return this.credentialFlag;
    }

    public abstract int getModelId();

    public abstract String getModelName();

    public int getPublicationResolution() {
        return this.publicationResolution;
    }

    public int getPublicationSteps() {
        return this.publicationSteps;
    }

    public byte[] getPublishAddress() {
        return this.publishAddress;
    }

    public byte[] getPublishAppKeyIndex() {
        return this.publishAppKeyIndex;
    }

    public Integer getPublishAppKeyIndexInt() {
        byte[] bArr = this.publishAppKeyIndex;
        if (bArr != null) {
            return Integer.valueOf(ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort());
        }
        return null;
    }

    public int getPublishRetransmitCount() {
        return this.publishRetransmitCount;
    }

    public int getPublishRetransmitIntervalSteps() {
        return this.publishRetransmitIntervalSteps;
    }

    public int getPublishTtl() {
        return this.publishTtl & 255;
    }

    @JSONField(serialize = false)
    public List<byte[]> getSubscriptionAddresses() {
        return this.mSubscriptionAddress;
    }

    public final void parcelMeshModel(Parcel parcel, int i2) {
        parcel.writeInt(this.mModelId);
        parcel.writeList(this.mBoundAppKeyIndexes);
        parcel.writeMap(this.mBoundAppKeys);
        parcel.writeByteArray(this.publishAddress);
        parcel.writeByteArray(this.publishAppKeyIndex);
        parcel.writeInt(this.credentialFlag);
        parcel.writeInt(this.publishTtl);
        parcel.writeInt(this.publishPeriod);
        parcel.writeInt(this.publishRetransmitIntervalSteps);
        parcel.writeList(this.mSubscriptionAddress);
    }

    public void removeBoundAppKey(int i2, String str) {
        if (this.mBoundAppKeyIndexes.contains(Integer.valueOf(i2))) {
            this.mBoundAppKeyIndexes.remove(this.mBoundAppKeyIndexes.indexOf(Integer.valueOf(i2)));
        }
        this.mBoundAppKeys.remove(Integer.valueOf(i2));
    }

    public void removeSubscriptionAddress(byte[] bArr) {
        int index;
        if (bArr == null || (index = getIndex(bArr)) <= -1) {
            return;
        }
        this.mSubscriptionAddress.remove(index);
    }

    public void setBoundAppKey(int i2, String str) {
        if (!this.mBoundAppKeyIndexes.contains(Integer.valueOf(i2))) {
            this.mBoundAppKeyIndexes.add(Integer.valueOf(i2));
        }
        this.mBoundAppKeys.put(Integer.valueOf(i2), str);
    }

    public void setCredentialFlag(int i2) {
        this.credentialFlag = i2;
    }

    public void setModelId(int i2) {
        this.mModelId = i2;
    }

    public void setPublicationResolution(int i2) {
        this.publicationResolution = i2;
    }

    public void setPublicationStatus(ConfigModelPublicationStatus configModelPublicationStatus) {
        this.publishAddress = configModelPublicationStatus.getPublishAddress();
        this.publishAppKeyIndex = configModelPublicationStatus.getPublicationAppKeyIndex();
        this.credentialFlag = configModelPublicationStatus.getCredentialFlag();
        this.publishTtl = configModelPublicationStatus.getPublishTtl();
        int publishPeriod = configModelPublicationStatus.getPublishPeriod();
        this.publishPeriod = publishPeriod;
        this.publicationSteps = publishPeriod >> 6;
        this.publicationResolution = publishPeriod & 3;
        this.publishRetransmitCount = configModelPublicationStatus.getPublishRetransmitCount();
        this.publishRetransmitIntervalSteps = configModelPublicationStatus.getPublishRetransmitIntervalSteps();
    }

    public void setPublicationSteps(int i2) {
        this.publicationSteps = i2;
    }

    public void setPublishAddress(byte[] bArr) {
        this.publishAddress = bArr;
    }

    public void setPublishAppKeyIndex(byte[] bArr) {
        this.publishAppKeyIndex = bArr;
    }

    public void setPublishPeriod(int i2) {
        this.publishPeriod = i2;
    }

    public void setPublishRetransmitCount(int i2) {
        this.publishRetransmitCount = i2;
    }

    public void setPublishRetransmitIntervalSteps(int i2) {
        this.publishRetransmitIntervalSteps = i2;
    }

    public void setPublishTtl(int i2) {
        this.publishTtl = i2;
    }

    public void setSubscriptionAddress(List<byte[]> list) {
        this.mSubscriptionAddress = list;
    }

    public void setPublicationStatus(byte[] bArr) {
        if (bArr == null || checkIfAlreadySubscribed(bArr)) {
            return;
        }
        this.mSubscriptionAddress.add(bArr);
    }

    public MeshModel(int i2) {
        this.mBoundAppKeyIndexes = new ArrayList();
        this.mBoundAppKeys = new LinkedHashMap();
        this.publishTtl = 255;
        this.publishPeriod = 0;
        this.publishRetransmitCount = 1;
        this.publishRetransmitIntervalSteps = 1;
        this.mSubscriptionAddress = new ArrayList();
        this.publicationSteps = 0;
        this.publicationResolution = 0;
        this.mModelId = i2;
    }

    public MeshModel(Parcel parcel) {
        this.mBoundAppKeyIndexes = new ArrayList();
        this.mBoundAppKeys = new LinkedHashMap();
        this.publishTtl = 255;
        this.publishPeriod = 0;
        this.publishRetransmitCount = 1;
        this.publishRetransmitIntervalSteps = 1;
        this.mSubscriptionAddress = new ArrayList();
        this.publicationSteps = 0;
        this.publicationResolution = 0;
        int i2 = parcel.readInt();
        if (i2 >= -32768 && i2 <= 32767) {
            this.mModelId = (short) i2;
        } else {
            this.mModelId = i2;
        }
        parcel.readList(this.mBoundAppKeyIndexes, Integer.class.getClassLoader());
        sortAppKeys(parcel.readHashMap(String.class.getClassLoader()));
        this.publishAddress = parcel.createByteArray();
        this.publishAppKeyIndex = parcel.createByteArray();
        this.credentialFlag = parcel.readInt();
        this.publishTtl = parcel.readInt();
        int i3 = parcel.readInt();
        this.publishPeriod = i3;
        this.publicationSteps = i3 >> 6;
        this.publicationResolution = i3 & 3;
        this.publishRetransmitIntervalSteps = parcel.readInt();
        parcel.readList(this.mSubscriptionAddress, byte[].class.getClassLoader());
    }
}
