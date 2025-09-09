package datasource.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class AddModelClient {
    public int modelId;
    public List<Integer> statusOpcodes;

    public int getModelId() {
        return this.modelId;
    }

    public List<Integer> getStatusOpcodes() {
        return this.statusOpcodes;
    }

    public void setModelId(int i2) {
        this.modelId = i2;
    }

    public void setStatusOpcodes(List<Integer> list) {
        this.statusOpcodes = list;
    }
}
