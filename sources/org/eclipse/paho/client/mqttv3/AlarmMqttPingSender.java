package org.eclipse.paho.client.mqttv3;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;
import androidx.core.app.NotificationCompat;
import androidx.media3.common.C;
import com.kingsmith.miot.KsProperty;
import com.xiaomi.mipush.sdk.Constants;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class AlarmMqttPingSender implements MqttPingSender {
    private static final String CLASS_NAME = "AlarmMqttPingSender";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private AlarmReceiver alarmReceiver;
    private ClientComms clientComms;
    private Context context;
    private AtomicBoolean hasStarted = new AtomicBoolean(false);
    private PendingIntent pendingIntent = null;
    private String ALARM_ACTION = "hb_";
    private String ALARM_ACTION_PRE = "hb_";
    private AtomicBoolean noWakeLocak = new AtomicBoolean(true);
    private AtomicInteger requestCodeAI = new AtomicInteger(65484);

    class AlarmReceiver extends BroadcastReceiver {
        private static final String methodName = "PingTask.onReceive";
        private final String wakeLockTag;
        private PowerManager.WakeLock wakelock;

        AlarmReceiver() {
            this.wakeLockTag = AlarmMqttPingSender.this.ALARM_ACTION + AlarmMqttPingSender.this.clientComms.getClient().getClientId();
        }

        @Override // android.content.BroadcastReceiver
        @SuppressLint({"Wakelock"})
        public void onReceive(Context context, Intent intent) {
            PowerManager.WakeLock wakeLock;
            if (intent == null || intent.getAction() == null || !intent.getAction().equals(AlarmMqttPingSender.this.ALARM_ACTION)) {
                return;
            }
            AlarmMqttPingSender.log.fine(AlarmMqttPingSender.CLASS_NAME, methodName, "660 " + AlarmMqttPingSender.this.ALARM_ACTION + " Sending Ping at:" + AlarmMqttPingSender.getCurrentTime(System.currentTimeMillis()));
            if (!AlarmMqttPingSender.this.noWakeLocak.get()) {
                PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) context.getSystemService(KsProperty.Power)).newWakeLock(1, this.wakeLockTag);
                this.wakelock = wakeLockNewWakeLock;
                wakeLockNewWakeLock.acquire();
            }
            if (!AlarmMqttPingSender.this.hasStarted.get()) {
                AlarmMqttPingSender.log.fine("AlarmMqttPingSender-AR", "onReceive", "alarm stopped, return.");
                return;
            }
            AlarmMqttPingSender alarmMqttPingSender = AlarmMqttPingSender.this;
            alarmMqttPingSender.schedule(alarmMqttPingSender.clientComms.getKeepAlive());
            MqttToken mqttTokenCheckForActivity = AlarmMqttPingSender.this.clientComms.checkForActivity(new IMqttActionListener() { // from class: org.eclipse.paho.client.mqttv3.AlarmMqttPingSender.AlarmReceiver.1
                @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    AlarmMqttPingSender.log.fine("AlarmMqttPingSender-AR", "onSuccess", "Release lock(" + AlarmReceiver.this.wakeLockTag + "):" + AlarmMqttPingSender.getCurrentTime(System.currentTimeMillis()));
                    if (AlarmMqttPingSender.this.noWakeLocak.get() || AlarmReceiver.this.wakelock == null) {
                        return;
                    }
                    AlarmReceiver.this.wakelock.release();
                }

                @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onSuccess(IMqttToken iMqttToken) {
                    AlarmMqttPingSender.log.fine("AlarmMqttPingSender-AR", "onSuccess", "Release lock(" + AlarmReceiver.this.wakeLockTag + "):" + AlarmMqttPingSender.getCurrentTime(System.currentTimeMillis()));
                    if (AlarmMqttPingSender.this.noWakeLocak.get() || AlarmReceiver.this.wakelock == null) {
                        return;
                    }
                    AlarmReceiver.this.wakelock.release();
                }
            });
            if (AlarmMqttPingSender.this.noWakeLocak.get() || (wakeLock = this.wakelock) == null || mqttTokenCheckForActivity != null || !wakeLock.isHeld()) {
                return;
            }
            this.wakelock.release();
        }
    }

    public AlarmMqttPingSender(Context context) {
        this.context = null;
        this.context = context;
        if (context == null) {
            throw new RuntimeException("context cannot be null.");
        }
        this.ALARM_ACTION_PRE += this.context.getPackageName() + hashCode();
        log.setResourceName(this.ALARM_ACTION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getCurrentTime(long j2) {
        return new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss).format(Long.valueOf(j2));
    }

    public Context getContext() {
        return this.context;
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttPingSender
    public void init(ClientComms clientComms) {
        this.clientComms = clientComms;
        this.alarmReceiver = new AlarmReceiver();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttPingSender
    public void schedule(long j2) {
        AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager == null) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() + j2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss);
        log.fine(CLASS_NAME, "schedule", this.ALARM_ACTION + " time=" + simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ", next=" + simpleDateFormat.format(Long.valueOf(jCurrentTimeMillis)) + ", nextMax=" + simpleDateFormat.format(Long.valueOf(System.currentTimeMillis() + (((j2 * 3) / 2) - 5000))) + ", pid=" + Process.myPid() + ", tid=" + Process.myTid() + ", SDK_INT=" + Build.VERSION.SDK_INT);
        alarmManager.setExact(0, jCurrentTimeMillis, this.pendingIntent);
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttPingSender
    public void start() {
        log.fine(CLASS_NAME, "start", "659-cancel", new Object[]{this.clientComms.getClient().getClientId()});
        this.ALARM_ACTION = this.ALARM_ACTION_PRE + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this.requestCodeAI.get();
        Context context = this.context;
        if (context != null) {
            context.registerReceiver(this.alarmReceiver, new IntentFilter(this.ALARM_ACTION));
        }
        this.pendingIntent = PendingIntent.getBroadcast(this.context, this.requestCodeAI.getAndIncrement(), new Intent(this.ALARM_ACTION), C.BUFFER_FLAG_FIRST_SAMPLE);
        schedule(this.clientComms.getKeepAlive());
        this.hasStarted.set(true);
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttPingSender
    public void stop() {
        AlarmManager alarmManager;
        log.fine(CLASS_NAME, "stop", "661", null);
        if (this.hasStarted.get()) {
            if (this.pendingIntent != null && (alarmManager = (AlarmManager) this.context.getSystemService(NotificationCompat.CATEGORY_ALARM)) != null) {
                alarmManager.cancel(this.pendingIntent);
            }
            this.hasStarted.set(false);
            try {
                this.context.unregisterReceiver(this.alarmReceiver);
            } catch (IllegalArgumentException unused) {
            }
        }
    }
}
