package org.eclipse.paho.client.mqttv3;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.media3.common.C;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.SimpleDateFormat;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class AlarmTimer {
    private AlarmManager alarmManager;
    private AlarmReceiver alarmReceiver;
    private Context context;
    private static final String CLASS_NAME = "AlarmTimer";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private static String ALARM_TIMER_ACTION = null;
    private AlarmTask alarmTask = null;
    private PendingIntent pendingIntent = null;
    private int requestCode = 65450;

    class AlarmReceiver extends BroadcastReceiver {
        AlarmReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        @SuppressLint({"Wakelock"})
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null || !TextUtils.equals(AlarmTimer.ALARM_TIMER_ACTION, intent.getAction())) {
                return;
            }
            AlarmTimer.log.fine(AlarmTimer.CLASS_NAME, "onReceive", AlarmTimer.ALARM_TIMER_ACTION + " wake up at: " + AlarmTimer.getCurrentTime(System.currentTimeMillis()));
            if (AlarmTimer.this.alarmTask != null) {
                AlarmTimer.this.alarmTask.onReceive(context, intent);
            }
        }
    }

    public interface AlarmTask {
        void onReceive(Context context, Intent intent);
    }

    public AlarmTimer(Context context, String str) {
        this.alarmReceiver = null;
        this.alarmManager = null;
        this.context = null;
        this.context = context.getApplicationContext();
        this.alarmReceiver = new AlarmReceiver();
        this.alarmManager = (AlarmManager) this.context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (TextUtils.isEmpty(str)) {
            ALARM_TIMER_ACTION = this.context.getPackageName() + OpenAccountUIConstants.UNDER_LINE + hashCode();
        } else {
            ALARM_TIMER_ACTION = str + OpenAccountUIConstants.UNDER_LINE + hashCode();
        }
        Context context2 = this.context;
        if (context2 != null) {
            context2.registerReceiver(this.alarmReceiver, new IntentFilter(ALARM_TIMER_ACTION));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getCurrentTime(long j2) {
        return new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss).format(Long.valueOf(j2));
    }

    public void destroy() {
        AlarmManager alarmManager;
        if (this.alarmTask != null) {
            this.alarmTask = null;
        }
        PendingIntent pendingIntent = this.pendingIntent;
        if (pendingIntent != null && (alarmManager = this.alarmManager) != null) {
            alarmManager.cancel(pendingIntent);
        }
        try {
            this.context.unregisterReceiver(this.alarmReceiver);
        } catch (IllegalArgumentException unused) {
        }
    }

    public void schedule(AlarmTask alarmTask, int i2, long j2) {
        if (alarmTask == null || i2 < 1000) {
            log.warning(CLASS_NAME, "schedule", "invalid params");
            return;
        }
        this.alarmTask = alarmTask;
        this.pendingIntent = PendingIntent.getBroadcast(this.context, this.requestCode, new Intent(ALARM_TIMER_ACTION), C.BUFFER_FLAG_FIRST_SAMPLE);
        long jCurrentTimeMillis = System.currentTimeMillis() + i2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss);
        log.fine(CLASS_NAME, "schedule", ALARM_TIMER_ACTION + " time=" + simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ", nextStart=" + simpleDateFormat.format(Long.valueOf(jCurrentTimeMillis)) + ", nextEnd=" + simpleDateFormat.format(Long.valueOf(System.currentTimeMillis() + j2)) + ", pid=" + Process.myPid() + ", tid=" + Process.myTid() + ", SDK_INT=" + Build.VERSION.SDK_INT);
        this.alarmManager.setWindow(0, jCurrentTimeMillis, j2, this.pendingIntent);
    }
}
