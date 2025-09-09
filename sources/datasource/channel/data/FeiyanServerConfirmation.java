package datasource.channel.data;

import com.aliyun.alink.linksdk.connectsdk.BaseApiResponse;
import datasource.bean.ServerConfirmation;

/* loaded from: classes4.dex */
public class FeiyanServerConfirmation extends BaseApiResponse {
    public String serverConfirmation;

    public ServerConfirmation convert2ServerConfirmation() {
        ServerConfirmation serverConfirmation = new ServerConfirmation();
        serverConfirmation.setServerConfirmation(getServerConfirmation());
        return serverConfirmation;
    }

    public String getServerConfirmation() {
        return this.serverConfirmation;
    }

    public void setServerConfirmation(String str) {
        this.serverConfirmation = str;
    }
}
