package com.taobao.agoo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.k;
import com.taobao.agoo.BaseNotifyClickActivity;
import java.util.Iterator;
import java.util.Set;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.MsgDO;
import org.android.agoo.control.AgooFactory;
import org.android.agoo.control.NotifManager;
import org.android.agoo.message.MessageService;

/* loaded from: classes4.dex */
public abstract class BaseNotifyClick {
    private static final String TAG = "accs.BaseNotifyClick";
    private AgooFactory agooFactory;
    private Context context;
    private String msgSource;
    private NotifManager notifyManager;

    private void buildMessage(Intent intent) {
        ThreadPoolExecutorFactory.execute(new a(this, intent));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String parseMsgByThirdPush(Intent intent) {
        String msgFromIntent;
        Set<BaseNotifyClickActivity.INotifyListener> set = BaseNotifyClickActivity.notifyListeners;
        if (set != null && set.size() > 0) {
            Iterator<BaseNotifyClickActivity.INotifyListener> it = BaseNotifyClickActivity.notifyListeners.iterator();
            msgFromIntent = null;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                BaseNotifyClickActivity.INotifyListener next = it.next();
                String msgFromIntent2 = next.parseMsgFromIntent(intent);
                if (!TextUtils.isEmpty(msgFromIntent2)) {
                    this.msgSource = next.getMsgSource();
                    msgFromIntent = msgFromIntent2;
                    break;
                }
                msgFromIntent = msgFromIntent2;
            }
        } else {
            ALog.e(TAG, "no impl, try use default impl to parse intent!", new Object[0]);
            BaseNotifyClickActivity.INotifyListener bVar = new b();
            msgFromIntent = bVar.parseMsgFromIntent(intent);
            if (TextUtils.isEmpty(msgFromIntent)) {
                bVar = new f();
                msgFromIntent = bVar.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(msgFromIntent)) {
                bVar = new d();
                msgFromIntent = bVar.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(msgFromIntent)) {
                bVar = new e();
                msgFromIntent = bVar.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(msgFromIntent)) {
                bVar = new c();
                msgFromIntent = bVar.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(msgFromIntent)) {
                k.a("accs", "error", "parse 3push error", 0.0d);
            } else {
                this.msgSource = bVar.getMsgSource();
                k.a("accs", "error", "parse 3push default " + this.msgSource, 0.0d);
            }
        }
        ALog.i(TAG, "parseMsgByThirdPush", "result", msgFromIntent, "msgSource", this.msgSource);
        return msgFromIntent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportClickNotifyMsg(Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("id");
            String stringExtra2 = intent.getStringExtra(AgooConstants.MESSAGE_SOURCE);
            String stringExtra3 = intent.getStringExtra(AgooConstants.MESSAGE_REPORT);
            String stringExtra4 = intent.getStringExtra(AgooConstants.MESSAGE_EXT);
            MsgDO msgDO = new MsgDO();
            msgDO.msgIds = stringExtra;
            msgDO.extData = stringExtra4;
            msgDO.messageSource = stringExtra2;
            msgDO.reportStr = stringExtra3;
            msgDO.msgStatus = MessageService.MSG_ACCS_NOTIFY_CLICK;
            ALog.i(TAG, "reportClickNotifyMsg messageId:" + stringExtra + " source:" + stringExtra2 + " reportStr:" + stringExtra3 + " status:" + msgDO.msgStatus, new Object[0]);
            this.notifyManager.report(msgDO, null);
        } catch (Exception e2) {
            ALog.e(TAG, "reportClickNotifyMsg exception: " + e2, new Object[0]);
        }
    }

    public void onCreate(Context context, Intent intent) {
        ALog.i(TAG, "onCreate", new Object[0]);
        this.context = context;
        buildMessage(intent);
    }

    public abstract void onMessage(Intent intent);

    public void onNewIntent(Intent intent) {
        ALog.i(TAG, "onNewIntent", new Object[0]);
        buildMessage(intent);
    }
}
