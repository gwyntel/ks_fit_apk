package com.taobao.agoo;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class e implements BaseNotifyClickActivity.INotifyListener {
    @Override // com.taobao.agoo.BaseNotifyClickActivity.INotifyListener
    public String getMsgSource() {
        return "vivo";
    }

    @Override // com.taobao.agoo.BaseNotifyClickActivity.INotifyListener
    public String parseMsgFromIntent(Intent intent) {
        String stringExtra = null;
        if (intent == null) {
            ALog.e("DefaultVivoMsgParseImpl", "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            stringExtra = intent.getStringExtra(AgooConstants.MESSAGE_VIVO_PAYLOAD);
            ALog.i("DefaultVivoMsgParseImpl", "parseMsgFromIntent", "msg", stringExtra);
            return stringExtra;
        } catch (Throwable th) {
            ALog.e("DefaultVivoMsgParseImpl", "parseMsgFromIntent", th, new Object[0]);
            return stringExtra;
        }
    }
}
