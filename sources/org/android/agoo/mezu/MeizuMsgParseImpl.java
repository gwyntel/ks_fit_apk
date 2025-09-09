package org.android.agoo.mezu;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes5.dex */
public class MeizuMsgParseImpl implements BaseNotifyClickActivity.INotifyListener {
    @Override // com.taobao.agoo.BaseNotifyClickActivity.INotifyListener
    public String getMsgSource() {
        return "meizu";
    }

    @Override // com.taobao.agoo.BaseNotifyClickActivity.INotifyListener
    public String parseMsgFromIntent(Intent intent) {
        String stringExtra = null;
        if (intent == null) {
            ALog.e(MeizuRegister.TAG, "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            stringExtra = intent.getStringExtra(AgooConstants.MESSAGE_MEIZU_PAYLOAD);
            ALog.i(MeizuRegister.TAG, "parseMsgFromIntent", "msg", stringExtra);
            return stringExtra;
        } catch (Throwable th) {
            ALog.e(MeizuRegister.TAG, "parseMsgFromIntent", th, new Object[0]);
            return stringExtra;
        }
    }
}
