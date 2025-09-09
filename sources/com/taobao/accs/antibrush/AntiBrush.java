package com.taobao.accs.antibrush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.data.g;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import java.util.concurrent.ScheduledFuture;

/* loaded from: classes4.dex */
public class AntiBrush {
    private static final String ANTI_ATTACK_ACTION = "mtopsdk.mtop.antiattack.checkcode.validate.activity_action";
    private static final String ANTI_ATTACK_RESULT_ACTION = "mtopsdk.extra.antiattack.result.notify.action";
    public static final int STATUS_BRUSH = 419;
    private static final String TAG = "AntiBrush";
    private static String mHost = null;
    private static volatile boolean mIsInCheckCodeActivity = false;
    private static ScheduledFuture<?> mTimeoutTask;
    private BroadcastReceiver mAntiAttackReceiver = null;
    private Context mContext;

    public static class AntiReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                try {
                    String stringExtra = intent.getStringExtra("Result");
                    ALog.e(AntiBrush.TAG, "anti onReceive result: " + stringExtra, new Object[0]);
                    AntiBrush.onResult(GlobalClientInfo.getContext(), stringExtra.equalsIgnoreCase("success"));
                } catch (Exception e2) {
                    ALog.e(AntiBrush.TAG, "anti onReceive", e2, new Object[0]);
                    AntiBrush.onResult(GlobalClientInfo.getContext(), false);
                }
            } catch (Throwable th) {
                AntiBrush.onResult(GlobalClientInfo.getContext(), false);
                throw th;
            }
        }
    }

    public AntiBrush(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private void handleantiBrush(String str) {
        if (mIsInCheckCodeActivity) {
            ALog.e(TAG, "handleantiBrush return", "mIsInCheckCodeActivity", Boolean.valueOf(mIsInCheckCodeActivity));
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction(ANTI_ATTACK_ACTION);
            intent.setPackage(this.mContext.getPackageName());
            intent.setFlags(268435456);
            intent.putExtra("Location", str);
            ALog.e(TAG, "handleAntiBrush:", new Object[0]);
            this.mContext.startActivity(intent);
            mIsInCheckCodeActivity = true;
            if (this.mAntiAttackReceiver == null) {
                this.mAntiAttackReceiver = new AntiReceiver();
            }
            this.mContext.registerReceiver(this.mAntiAttackReceiver, new IntentFilter(ANTI_ATTACK_RESULT_ACTION));
        } catch (Throwable th) {
            ALog.e(TAG, "handleantiBrush", th, new Object[0]);
        }
    }

    public static void onResult(Context context, boolean z2) {
        mIsInCheckCodeActivity = false;
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(context.getPackageName());
        intent.putExtra("command", 104);
        intent.putExtra(Constants.KEY_ANTI_BRUSH_RET, z2);
        g.a().b(context, intent);
        ScheduledFuture<?> scheduledFuture = mTimeoutTask;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            mTimeoutTask = null;
        }
        String str = mHost;
        if (str != null) {
            UtilityImpl.b(context, b.a(str));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean checkAntiBrush(java.net.URL r8, java.util.Map<java.lang.Integer, java.lang.String> r9) {
        /*
            r7 = this;
            java.lang.String r0 = "AntiBrush"
            r1 = 0
            if (r9 == 0) goto L87
            boolean r2 = com.taobao.accs.utl.UtilityImpl.a()     // Catch: java.lang.Throwable -> L6a
            if (r2 == 0) goto L87
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r2 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_STATUS     // Catch: java.lang.Throwable -> L6a
            int r2 = r2.ordinal()     // Catch: java.lang.Throwable -> L6a
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Throwable -> L6a
            java.lang.Object r2 = r9.get(r2)     // Catch: java.lang.Throwable -> L6a
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> L6a
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Throwable -> L6a
            if (r3 == 0) goto L23
            r2 = r1
            goto L2b
        L23:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Throwable -> L6a
            int r2 = r2.intValue()     // Catch: java.lang.Throwable -> L6a
        L2b:
            r3 = 419(0x1a3, float:5.87E-43)
            if (r2 != r3) goto L87
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r2 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_LOCATION     // Catch: java.lang.Throwable -> L6a
            int r2 = r2.ordinal()     // Catch: java.lang.Throwable -> L6a
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Throwable -> L6a
            java.lang.Object r9 = r9.get(r2)     // Catch: java.lang.Throwable -> L6a
            java.lang.String r9 = (java.lang.String) r9     // Catch: java.lang.Throwable -> L6a
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch: java.lang.Throwable -> L6a
            if (r2 != 0) goto L87
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6a
            r2.<init>()     // Catch: java.lang.Throwable -> L6a
            java.lang.String r3 = "start anti bursh location:"
            r2.append(r3)     // Catch: java.lang.Throwable -> L6a
            r2.append(r9)     // Catch: java.lang.Throwable -> L6a
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L6a
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L6a
            com.taobao.accs.utl.ALog.e(r0, r2, r3)     // Catch: java.lang.Throwable -> L6a
            r7.handleantiBrush(r9)     // Catch: java.lang.Throwable -> L6a
            java.util.concurrent.ScheduledFuture<?> r9 = com.taobao.accs.antibrush.AntiBrush.mTimeoutTask     // Catch: java.lang.Throwable -> L6a
            r2 = 0
            r3 = 1
            if (r9 == 0) goto L6d
            r9.cancel(r3)     // Catch: java.lang.Throwable -> L6a
            com.taobao.accs.antibrush.AntiBrush.mTimeoutTask = r2     // Catch: java.lang.Throwable -> L6a
            goto L6d
        L6a:
            r8 = move-exception
            r3 = r1
            goto Laa
        L6d:
            com.taobao.accs.antibrush.a r9 = new com.taobao.accs.antibrush.a     // Catch: java.lang.Throwable -> L6a
            r9.<init>(r7)     // Catch: java.lang.Throwable -> L6a
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.Throwable -> L6a
            r5 = 60000(0xea60, double:2.9644E-319)
            java.util.concurrent.ScheduledFuture r9 = com.taobao.accs.common.ThreadPoolExecutorFactory.schedule(r9, r5, r4)     // Catch: java.lang.Throwable -> L6a
            com.taobao.accs.antibrush.AntiBrush.mTimeoutTask = r9     // Catch: java.lang.Throwable -> L6a
            if (r8 != 0) goto L80
            goto L84
        L80:
            java.lang.String r2 = r8.getHost()     // Catch: java.lang.Throwable -> L6a
        L84:
            com.taobao.accs.antibrush.AntiBrush.mHost = r2     // Catch: java.lang.Throwable -> L6a
            goto L88
        L87:
            r3 = r1
        L88:
            java.lang.String r8 = com.taobao.accs.client.GlobalClientInfo.f20067c     // Catch: java.lang.Throwable -> La9
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch: java.lang.Throwable -> La9
            if (r8 != 0) goto Lb1
            java.lang.String r8 = com.taobao.accs.antibrush.AntiBrush.mHost     // Catch: java.lang.Throwable -> La9
            java.lang.String r8 = com.taobao.accs.antibrush.b.a(r8)     // Catch: java.lang.Throwable -> La9
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch: java.lang.Throwable -> La9
            if (r8 == 0) goto Lb1
            java.lang.String r8 = "cookie invalid, clear"
            java.lang.Object[] r9 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> La9
            com.taobao.accs.utl.ALog.e(r0, r8, r9)     // Catch: java.lang.Throwable -> La9
            android.content.Context r8 = r7.mContext     // Catch: java.lang.Throwable -> La9
            com.taobao.accs.utl.UtilityImpl.n(r8)     // Catch: java.lang.Throwable -> La9
            goto Lb1
        La9:
            r8 = move-exception
        Laa:
            java.lang.String r9 = "checkAntiBrush error"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.e(r0, r9, r8, r1)
        Lb1:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.antibrush.AntiBrush.checkAntiBrush(java.net.URL, java.util.Map):boolean");
    }
}
