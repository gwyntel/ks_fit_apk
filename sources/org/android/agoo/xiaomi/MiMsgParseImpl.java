package org.android.agoo.xiaomi;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageHelper;

/* loaded from: classes5.dex */
public class MiMsgParseImpl implements BaseNotifyClickActivity.INotifyListener {
    @Override // com.taobao.agoo.BaseNotifyClickActivity.INotifyListener
    public String getMsgSource() {
        return "xiaomi";
    }

    @Override // com.taobao.agoo.BaseNotifyClickActivity.INotifyListener
    public String parseMsgFromIntent(Intent intent) {
        MiPushMessage miPushMessage;
        try {
            miPushMessage = (MiPushMessage) intent.getSerializableExtra(PushMessageHelper.KEY_MESSAGE);
        } catch (Throwable unused) {
        }
        String content = miPushMessage != null ? miPushMessage.getContent() : null;
        ALog.i(MiPushRegistar.TAG, "parseMsgFromIntent", "msg", content);
        return content;
    }

    public String toString() {
        return "INotifyListener: " + getMsgSource();
    }
}
