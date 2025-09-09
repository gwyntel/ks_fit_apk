package com.taobao.agoo;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageHelper;

/* loaded from: classes4.dex */
public class f implements BaseNotifyClickActivity.INotifyListener {
    @Override // com.taobao.agoo.BaseNotifyClickActivity.INotifyListener
    public String getMsgSource() {
        return "xiaomi";
    }

    @Override // com.taobao.agoo.BaseNotifyClickActivity.INotifyListener
    public String parseMsgFromIntent(Intent intent) {
        try {
            String str = PushMessageHelper.MESSAGE_TYPE;
            String str2 = (String) PushMessageHelper.class.getField("KEY_MESSAGE").get(null);
            if (intent.getSerializableExtra(str2) != null) {
                return (String) MiPushMessage.class.getMethod("getContent", null).invoke(MiPushMessage.class.cast(intent.getSerializableExtra(str2)), null);
            }
            return null;
        } catch (Throwable th) {
            ALog.e("DefaultXiaomiMsgParseImpl", "parseMsgFromIntent", th, new Object[0]);
            return null;
        }
    }
}
