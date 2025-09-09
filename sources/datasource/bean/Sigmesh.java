package datasource.bean;

/* loaded from: classes4.dex */
public class Sigmesh {
    public Action action;
    public boolean compareParameters;
    public String devId;
    public Device device;
    public String uuid;

    public static class Action {
        public String opcode;
        public String parameters;
        public String statusOpcode;

        public String getOpcode() {
            return this.opcode;
        }

        public String getParameters() {
            return this.parameters;
        }

        public String getStatusOpcode() {
            return this.statusOpcode;
        }

        public void setOpcode(String str) {
            this.opcode = str;
        }

        public void setParameters(String str) {
            this.parameters = str;
        }

        public void setStatusOpcode(String str) {
            this.statusOpcode = str;
        }
    }

    public static class Device {
        public int appKeyIndex;
        public int destAddr;
        public String deviceKey;
        public int netKeyIndex;
        public int ttl;

        public int getAppKeyIndex() {
            return this.appKeyIndex;
        }

        public int getDestAddr() {
            return this.destAddr;
        }

        public String getDeviceKey() {
            return this.deviceKey;
        }

        public int getNetKeyIndex() {
            return this.netKeyIndex;
        }

        public int getTtl() {
            return this.ttl;
        }

        public void setAppKeyIndex(int i2) {
            this.appKeyIndex = i2;
        }

        public void setDestAddr(int i2) {
            this.destAddr = i2;
        }

        public void setDeviceKey(String str) {
            this.deviceKey = str;
        }

        public void setNetKeyIndex(int i2) {
            this.netKeyIndex = i2;
        }

        public void setTtl(int i2) {
            this.ttl = i2;
        }
    }

    public Action getAction() {
        return this.action;
    }

    public String getDevId() {
        return this.devId;
    }

    public Device getDevice() {
        return this.device;
    }

    public String getUuid() {
        return this.uuid;
    }

    public boolean isCompareParameters() {
        return this.compareParameters;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setCompareParameters(boolean z2) {
        this.compareParameters = z2;
    }

    public void setDevId(String str) {
        this.devId = str;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }
}
