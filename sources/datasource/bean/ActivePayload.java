package datasource.bean;

/* loaded from: classes4.dex */
public class ActivePayload {
    public int groupAddr;
    public String opcode;
    public String parameters;
    public int retryCount;

    public int getGroupAddr() {
        return this.groupAddr;
    }

    public String getOpcode() {
        return this.opcode;
    }

    public String getParameters() {
        return this.parameters;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setGroupAddr(int i2) {
        this.groupAddr = i2;
    }

    public void setOpcode(String str) {
        this.opcode = str;
    }

    public void setParameters(String str) {
        this.parameters = str;
    }

    public void setRetryCount(int i2) {
        this.retryCount = i2;
    }
}
