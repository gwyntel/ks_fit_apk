package org.android.agoo.control;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Base64;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.yc.utesdk.utils.close.AESUtils;
import javax.crypto.spec.SecretKeySpec;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.Config;
import org.android.agoo.common.MsgDO;
import org.android.agoo.message.MessageService;
import org.android.agoo.service.SendMessage;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AgooFactory {
    private static final String DEAL_MESSAGE = "accs.msgRecevie";
    private static final String TAG = "AgooFactory";
    private static Context mContext;
    protected NotifManager notifyManager = null;
    private MessageService messageService = null;

    static class a implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        private Intent f26512a;

        /* renamed from: b, reason: collision with root package name */
        private String f26513b;

        /* renamed from: c, reason: collision with root package name */
        private SendMessage f26514c;

        /* renamed from: d, reason: collision with root package name */
        private ServiceConnection f26515d = this;

        public a(String str, Intent intent) {
            this.f26513b = str;
            this.f26512a = intent;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ALog.d(AgooFactory.TAG, "MessageConnection conneted:" + componentName, new Object[0]);
            this.f26514c = SendMessage.Stub.asInterface(iBinder);
            ALog.d(AgooFactory.TAG, "onConnected current tid:" + Thread.currentThread().getId(), new Object[0]);
            ALog.d(AgooFactory.TAG, "MessageConnection sent:" + this.f26512a, new Object[0]);
            if (this.f26514c != null) {
                ThreadPoolExecutorFactory.execute(new f(this));
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            ALog.d(AgooFactory.TAG, "MessageConnection disConnected", new Object[0]);
        }
    }

    class b implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private String f26517b;

        /* renamed from: c, reason: collision with root package name */
        private Intent f26518c;

        public b(String str, Intent intent) {
            this.f26517b = str;
            this.f26518c = intent;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                ALog.d(AgooFactory.TAG, "running tid:" + Thread.currentThread().getId() + ",pack=" + this.f26517b, new Object[0]);
                AgooFactory.mContext.sendBroadcast(this.f26518c);
                StringBuilder sb = new StringBuilder();
                sb.append("SendMessageRunnable for accs,pack=");
                sb.append(this.f26517b);
                ALog.d(AgooFactory.TAG, sb.toString(), new Object[0]);
                try {
                    this.f26518c.setPackage(this.f26517b);
                    this.f26518c.setAction(AgooConstants.INTENT_FROM_AGOO_MESSAGE);
                    com.taobao.accs.a.a.a(AgooFactory.mContext, this.f26518c);
                } catch (Throwable unused) {
                }
                Intent intent = new Intent(AgooConstants.BINDER_MSGRECEIVER_ACTION);
                intent.setPackage(this.f26517b);
                ALog.d(AgooFactory.TAG, "this message pack:" + this.f26517b, new Object[0]);
                ALog.d(AgooFactory.TAG, "start to service...", new Object[0]);
                boolean zBindService = AgooFactory.mContext.bindService(intent, new a(this.f26518c.getStringExtra("id"), this.f26518c), 17);
                ALog.d(AgooFactory.TAG, "start service ret:" + zBindService, new Object[0]);
                if (zBindService) {
                    return;
                }
                ALog.d(AgooFactory.TAG, "SendMessageRunnable is error", new Object[0]);
            } catch (Throwable th) {
                ALog.e(AgooFactory.TAG, "SendMessageRunnable is error,e=" + th.toString(), new Object[0]);
            }
        }
    }

    private static final boolean checkPackage(Context context, String str) {
        return context.getPackageManager().getApplicationInfo(str, 0) != null;
    }

    private static Bundle getFlag(long j2, MsgDO msgDO) {
        Bundle bundle = new Bundle();
        try {
            char[] charArray = Long.toBinaryString(j2).toCharArray();
            if (charArray != null && 8 <= charArray.length) {
                if (8 <= charArray.length) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(Integer.parseInt("" + charArray[1] + charArray[2] + charArray[3] + charArray[4], 2));
                    bundle.putString(AgooConstants.MESSAGE_ENCRYPTED, sb.toString());
                    if (charArray[6] == '1') {
                        bundle.putString(AgooConstants.MESSAGE_REPORT, "1");
                        msgDO.reportStr = "1";
                    }
                    if (charArray[7] == '1') {
                        bundle.putString(AgooConstants.MESSAGE_NOTIFICATION, "1");
                    }
                }
                if (9 <= charArray.length && charArray[8] == '1') {
                    bundle.putString(AgooConstants.MESSAGE_HAS_TEST, "1");
                }
                if (10 <= charArray.length && charArray[9] == '1') {
                    bundle.putString(AgooConstants.MESSAGE_DUPLICATE, "1");
                }
                if (11 <= charArray.length && charArray[10] == '1') {
                    bundle.putInt(AgooConstants.MESSAGE_POPUP, 1);
                }
            }
        } catch (Throwable unused) {
        }
        return bundle;
    }

    public static String parseEncryptedMsg(String str) {
        byte[] bArrA;
        String str2 = null;
        try {
            String strA = Config.a(mContext);
            String strB = Config.b(mContext);
            String strJ = UtilityImpl.j(mContext);
            if (TextUtils.isEmpty(strB)) {
                ALog.e(TAG, "getAppsign secret null", new Object[0]);
                bArrA = null;
            } else {
                bArrA = org.android.agoo.common.a.a(strB.getBytes("utf-8"), (strA + strJ).getBytes("utf-8"));
            }
            if (bArrA == null || bArrA.length <= 0) {
                ALog.e(TAG, "aesDecrypt key is null!", new Object[0]);
            } else {
                str2 = new String(org.android.agoo.common.a.a(Base64.decode(str, 8), new SecretKeySpec(org.android.agoo.common.a.a(bArrA), AESUtils.AES), org.android.agoo.common.a.a(strA.getBytes("utf-8"))), "utf-8");
            }
        } catch (Throwable th) {
            ALog.e(TAG, "parseEncryptedMsg failure: ", th, new Object[0]);
        }
        return TextUtils.isEmpty(str2) ? parseEncryptedMsgByOldDid(str) : str2;
    }

    private static String parseEncryptedMsgByOldDid(String str) {
        byte[] bArrA;
        try {
            String strD = UtilityImpl.d(Constants.SP_FILE_NAME, mContext);
            if (TextUtils.isEmpty(strD) || TextUtils.equals(strD, UtilityImpl.j(mContext))) {
                return null;
            }
            String strA = Config.a(mContext);
            String strB = Config.b(mContext);
            if (TextUtils.isEmpty(strB)) {
                ALog.e(TAG, "oldDid getAppsign secret null", new Object[0]);
                bArrA = null;
            } else {
                bArrA = org.android.agoo.common.a.a(strB.getBytes("utf-8"), (strA + strD).getBytes("utf-8"));
            }
            if (bArrA != null && bArrA.length > 0) {
                return new String(org.android.agoo.common.a.a(Base64.decode(str, 8), new SecretKeySpec(org.android.agoo.common.a.a(bArrA), AESUtils.AES), org.android.agoo.common.a.a(strA.getBytes("utf-8"))), "utf-8");
            }
            ALog.e(TAG, "oldDid aesDecrypt key is null!", new Object[0]);
            return null;
        } catch (Throwable th) {
            ALog.e(TAG, "oldDid parseEncryptedMsg failure: ", th, new Object[0]);
            return null;
        }
    }

    private void sendMsgByBindService(String str, Intent intent) {
        try {
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.d(TAG, "onHandleMessage current tid:" + Thread.currentThread().getId(), new Object[0]);
            }
            ThreadPoolExecutorFactory.execute(new b(str, intent));
        } catch (Throwable th) {
            ALog.e(TAG, "sendMsgByBindService error >>", th, new Object[0]);
        }
    }

    private void sendMsgToBussiness(Context context, String str, Bundle bundle, boolean z2, String str2, TaoBaseService.ExtraInfo extraInfo) {
        Intent intent = new Intent();
        intent.setAction(AgooConstants.INTENT_FROM_AGOO_MESSAGE);
        intent.setPackage(str);
        intent.putExtras(bundle);
        intent.putExtra("type", "common-push");
        intent.putExtra(AgooConstants.MESSAGE_SOURCE, str2);
        intent.addFlags(32);
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable(AgooConstants.MESSAGE_ACCS_EXTRA, extraInfo);
            intent.putExtra(AgooConstants.MESSAGE_AGOO_BUNDLE, bundle2);
        } catch (Throwable th) {
            ALog.e(TAG, "sendMsgToBussiness", th, new Object[0]);
        }
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.i(TAG, "sendMsgToBussiness intent:" + bundle.toString() + ",utdid=" + com.taobao.accs.utl.j.b(context) + ",pack=" + str + ",agooFlag=" + z2, new Object[0]);
        }
        if (z2) {
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, DEAL_MESSAGE, com.taobao.accs.utl.j.b(context), "agooMsg", AgooConstants.ACK_PACK_ERROR);
            sendMsgByBindService(str, intent);
        } else {
            intent.setClassName(str, com.taobao.accs.client.a.b());
            com.taobao.accs.a.a.a(context, intent);
        }
    }

    public void init(Context context, NotifManager notifManager, MessageService messageService) {
        mContext = context.getApplicationContext();
        this.notifyManager = notifManager;
        if (notifManager == null) {
            this.notifyManager = new NotifManager();
        }
        this.notifyManager.init(mContext);
        this.messageService = messageService;
        if (messageService == null) {
            this.messageService = new MessageService();
        }
        this.messageService.a(mContext);
    }

    public Bundle msgReceiverPreHandler(byte[] bArr, String str, TaoBaseService.ExtraInfo extraInfo, boolean z2) {
        JSONArray jSONArray;
        String str2;
        boolean z3;
        Bundle bundle;
        String str3;
        String str4;
        Bundle bundle2;
        StringBuilder sb;
        String str5;
        StringBuilder sb2;
        int i2;
        int i3;
        String str6 = ",";
        String str7 = "ext";
        if (bArr != null) {
            try {
                if (bArr.length > 0) {
                    String str8 = new String(bArr, "utf-8");
                    if (ALog.isPrintLog(ALog.Level.I)) {
                        ALog.i(TAG, "msgRecevie,message--->[" + str8 + "],utdid=" + com.taobao.accs.utl.j.b(mContext), new Object[0]);
                    }
                    if (TextUtils.isEmpty(str8)) {
                        UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, DEAL_MESSAGE, com.taobao.accs.utl.j.b(mContext), "message==null");
                        ALog.i(TAG, "handleMessage message==null,utdid=" + com.taobao.accs.utl.j.b(mContext), new Object[0]);
                        return null;
                    }
                    JSONArray jSONArray2 = new JSONArray(str8);
                    int length = jSONArray2.length();
                    StringBuilder sb3 = new StringBuilder();
                    StringBuilder sb4 = new StringBuilder();
                    Bundle bundle3 = null;
                    String str9 = null;
                    int i4 = 0;
                    while (i4 < length) {
                        Bundle bundle4 = new Bundle();
                        JSONObject jSONObject = jSONArray2.getJSONObject(i4);
                        if (jSONObject == null) {
                            jSONArray = jSONArray2;
                            i2 = i4;
                            sb = sb4;
                            i3 = length;
                            str5 = str8;
                            str3 = str6;
                            str2 = str7;
                            bundle2 = bundle4;
                            sb2 = sb3;
                        } else {
                            MsgDO msgDO = new MsgDO();
                            jSONArray = jSONArray2;
                            String string = jSONObject.getString("p");
                            String str10 = str9;
                            String string2 = jSONObject.getString("i");
                            String str11 = str8;
                            String string3 = jSONObject.getString("b");
                            int i5 = i4;
                            StringBuilder sb5 = sb4;
                            long j2 = jSONObject.getLong("f");
                            String string4 = !jSONObject.isNull(str7) ? jSONObject.getString(str7) : str10;
                            str2 = str7;
                            int i6 = length - 1;
                            msgDO.msgIds = string2;
                            msgDO.extData = string4;
                            msgDO.removePacks = string;
                            msgDO.messageSource = str;
                            if (TextUtils.isEmpty(string3)) {
                                msgDO.errorCode = AgooConstants.ACK_BODY_NULL;
                                this.notifyManager.handlerACKMessage(msgDO, extraInfo);
                            } else if (TextUtils.isEmpty(string)) {
                                msgDO.errorCode = AgooConstants.ACK_PACK_NULL;
                                this.notifyManager.handlerACKMessage(msgDO, extraInfo);
                            } else if (j2 == -1) {
                                msgDO.errorCode = AgooConstants.ACK_FLAG_NULL;
                                this.notifyManager.handlerACKMessage(msgDO, extraInfo);
                            } else {
                                int i7 = length;
                                if (checkPackage(mContext, string)) {
                                    Bundle flag = getFlag(j2, msgDO);
                                    String string5 = flag.getString(AgooConstants.MESSAGE_ENCRYPTED);
                                    StringBuilder sb6 = sb3;
                                    if (!mContext.getPackageName().equals(string)) {
                                        z3 = true;
                                        bundle = bundle4;
                                    } else if (TextUtils.equals(string5, Integer.toString(4))) {
                                        bundle = bundle4;
                                        z3 = false;
                                    } else {
                                        ALog.e(TAG, "msgRecevie msg encrypted flag not exist, cannot prase!!!", new Object[0]);
                                        UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, DEAL_MESSAGE, com.taobao.accs.utl.j.b(mContext), "encrypted!=4", AgooConstants.ACK_PACK_ERROR);
                                        msgDO.errorCode = AgooConstants.REPORT_NOT_ENCRYPT;
                                        this.notifyManager.handlerACKMessage(msgDO, extraInfo);
                                        str4 = string4;
                                        sb = sb5;
                                        str3 = str6;
                                        i2 = i5;
                                        sb2 = sb6;
                                    }
                                    bundle.putAll(flag);
                                    try {
                                        String string6 = jSONObject.getString("t");
                                        if (!TextUtils.isEmpty(string6)) {
                                            bundle.putString("time", string6);
                                        }
                                    } catch (Throwable unused) {
                                        if (ALog.isPrintLog(ALog.Level.I)) {
                                            ALog.i(TAG, "agoo msg has no time", new Object[0]);
                                        }
                                    }
                                    str3 = str6;
                                    bundle.putLong(AgooConstants.MESSAGE_TRACE, System.currentTimeMillis());
                                    bundle.putString("id", string2);
                                    bundle.putString("body", string3);
                                    bundle.putString("source", string);
                                    bundle.putString(AgooConstants.MESSAGE_FROM_APPKEY, Config.a(mContext));
                                    bundle.putString(AgooConstants.MESSAGE_EXT, string4);
                                    bundle.putString(AgooConstants.MESSAGE_ORI, str11);
                                    if (z2) {
                                        str4 = string4;
                                        bundle2 = bundle;
                                        i2 = i5;
                                        sb = sb5;
                                        sb2 = sb6;
                                        i3 = i7;
                                        str5 = str11;
                                        sendMsgToBussiness(mContext, string, bundle2, z3, str, extraInfo);
                                    } else {
                                        str4 = string4;
                                        bundle2 = bundle;
                                        sb = sb5;
                                        str5 = str11;
                                        sb2 = sb6;
                                        i2 = i5;
                                        i3 = i7;
                                        bundle2.putString("type", "common-push");
                                        bundle2.putString(AgooConstants.MESSAGE_SOURCE, str);
                                    }
                                    str9 = str4;
                                } else {
                                    ALog.d(TAG, "msgRecevie checkpackage is del,pack=" + string, new Object[0]);
                                    UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, DEAL_MESSAGE, com.taobao.accs.utl.j.b(mContext), "deletePack", string);
                                    sb5.append(string);
                                    sb3.append(string2);
                                    if (i5 < i6) {
                                        sb5.append(str6);
                                        sb3.append(str6);
                                    }
                                    i2 = i5;
                                    str4 = string4;
                                    sb2 = sb3;
                                    sb = sb5;
                                    str3 = str6;
                                }
                                str5 = str11;
                                bundle2 = bundle4;
                                i3 = i7;
                                str9 = str4;
                            }
                            str4 = string4;
                            sb2 = sb3;
                            i3 = length;
                            str3 = str6;
                            str5 = str11;
                            bundle2 = bundle4;
                            sb = sb5;
                            i2 = i5;
                            str9 = str4;
                        }
                        i4 = i2 + 1;
                        sb4 = sb;
                        bundle3 = bundle2;
                        sb3 = sb2;
                        str6 = str3;
                        length = i3;
                        str8 = str5;
                        str7 = str2;
                        jSONArray2 = jSONArray;
                    }
                    StringBuilder sb7 = sb4;
                    StringBuilder sb8 = sb3;
                    if (sb7.length() > 0) {
                        MsgDO msgDO2 = new MsgDO();
                        msgDO2.msgIds = sb8.toString();
                        msgDO2.removePacks = sb7.toString();
                        msgDO2.errorCode = AgooConstants.ACK_REMOVE_PACKAGE;
                        msgDO2.messageSource = str;
                        this.notifyManager.handlerACKMessage(msgDO2, extraInfo);
                    }
                    return bundle3;
                }
            } catch (Throwable th) {
                if (!ALog.isPrintLog(ALog.Level.E)) {
                    return null;
                }
                ALog.e(TAG, "msgRecevie is error,e=" + th, new Object[0]);
                return null;
            }
        }
        UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, DEAL_MESSAGE, com.taobao.accs.utl.j.b(mContext), "data==null");
        ALog.i(TAG, "handleMessage data==null,utdid=" + com.taobao.accs.utl.j.b(mContext), new Object[0]);
        return null;
    }

    public void msgRecevie(byte[] bArr, String str) {
        msgRecevie(bArr, str, null);
    }

    public void reportCacheMsg() {
        try {
            ThreadPoolExecutorFactory.execute(new c(this));
        } catch (Throwable th) {
            ALog.e(TAG, "reportCacheMsg fail:" + th.toString(), new Object[0]);
        }
    }

    public void saveMsg(byte[] bArr) {
        saveMsg(bArr, null);
    }

    public void updateMsg(byte[] bArr, boolean z2) {
        ThreadPoolExecutorFactory.execute(new d(this, bArr, z2));
    }

    public void updateMsgStatus(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.i(TAG, "updateNotifyMsg begin,messageId=" + str + ",status=" + str2 + ",reportTimes=" + Config.f(mContext), new Object[0]);
                }
                if (TextUtils.equals(str2, MessageService.MSG_ACCS_NOTIFY_CLICK)) {
                    this.messageService.a(str, "2");
                } else if (TextUtils.equals(str2, MessageService.MSG_ACCS_NOTIFY_DISMISS)) {
                    this.messageService.a(str, "3");
                }
            }
        } catch (Throwable th) {
            ALog.e(TAG, "updateNotifyMsg e=" + th.toString(), new Object[0]);
        }
    }

    public void updateNotifyMsg(String str, String str2) {
        ThreadPoolExecutorFactory.execute(new e(this, str, str2));
    }

    public void msgRecevie(byte[] bArr, String str, TaoBaseService.ExtraInfo extraInfo) {
        try {
            if (ALog.isPrintLog(ALog.Level.I)) {
                ALog.i(TAG, "into--[AgooFactory,msgRecevie]:messageSource=" + str, new Object[0]);
            }
            ThreadPoolExecutorFactory.execute(new org.android.agoo.control.b(this, bArr, str, extraInfo));
        } catch (Throwable th) {
            ALog.e(TAG, "serviceImpl init task fail:" + th.toString(), new Object[0]);
        }
    }

    public void saveMsg(byte[] bArr, String str) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        ThreadPoolExecutorFactory.execute(new org.android.agoo.control.a(this, bArr, str));
    }
}
