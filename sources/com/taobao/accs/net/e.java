package com.taobao.accs.net;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.taobao.accs.client.GlobalConfig;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import java.util.Calendar;

/* loaded from: classes4.dex */
class e extends g {

    /* renamed from: c, reason: collision with root package name */
    private PendingIntent f20214c;

    /* renamed from: d, reason: collision with root package name */
    private AlarmManager f20215d;

    e(Context context) {
        super(context);
    }

    @Override // com.taobao.accs.net.g
    protected void a(int i2) {
        if (GlobalConfig.isAlarmHeartbeatEnable()) {
            if (this.f20215d == null) {
                this.f20215d = (AlarmManager) this.f20219a.getSystemService(NotificationCompat.CATEGORY_ALARM);
            }
            if (this.f20215d == null) {
                ALog.e("AlarmHeartBeatMgr", "setInner null", new Object[0]);
                return;
            }
            if (this.f20214c == null) {
                Intent intent = new Intent();
                intent.setPackage(this.f20219a.getPackageName());
                intent.addFlags(32);
                intent.setAction(Constants.ACTION_COMMAND);
                intent.putExtra("command", 201);
                this.f20214c = PendingIntent.getBroadcast(this.f20219a, 0, intent, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(13, i2);
            this.f20215d.set(0, calendar.getTimeInMillis(), this.f20214c);
        }
    }
}
