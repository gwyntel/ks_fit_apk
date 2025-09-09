package com.heytap.mcssdk.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import anetwork.channel.util.RequestConstant;
import com.heytap.mcssdk.PushService;
import com.heytap.mcssdk.constant.McsEventConstant;
import com.heytap.mcssdk.constant.PushConstant;
import com.heytap.mcssdk.statis.McsStatisticUtils;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.constant.ConfigConstant;
import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.mode.NotificationSortMessage;
import com.heytap.msp.push.notification.ISortListener;
import com.heytap.msp.push.notification.PushNotification;
import com.heytap.msp.push.statis.StatisticUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class PushNotificationSortManager {
    private int canDelete;
    private int highSize;
    private int normalSize;
    private int notDelete;
    private int keepNumber = 3;
    private List<NotificationSortMessage> canDeleteList = new ArrayList();
    private List<String> deleteList = new ArrayList();

    private static class SingletonHolder {
        private static final PushNotificationSortManager INSTANCE = new PushNotificationSortManager();

        private SingletonHolder() {
        }
    }

    private void callbackListener(ISortListener iSortListener, boolean z2, PushNotification.Builder builder) {
        if (iSortListener != null) {
            iSortListener.buildCompleted(z2, builder, this.deleteList);
        }
    }

    private DataMessage createDataMessage(Context context, NotificationSortMessage notificationSortMessage) throws JSONException {
        DataMessage dataMessage = new DataMessage(context.getPackageName(), notificationSortMessage.getMessageId());
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(PushConstant.NotificationSort.IS_MCS, RequestConstant.FALSE);
            String statisticData = notificationSortMessage.getStatisticData();
            if (!TextUtils.isEmpty(statisticData)) {
                jSONObject.put(PushConstant.NotificationSort.CLIENT_STATISTIC_DATA, statisticData);
            }
            dataMessage.setStatisticsExtra(jSONObject.toString());
        } catch (JSONException unused) {
        }
        return dataMessage;
    }

    private boolean dealCurrentMessage(NotificationManager notificationManager, Context context, PushNotification.Builder builder, NotificationSortMessage notificationSortMessage) throws JSONException {
        boolean zJudgeShowCurrentMessage = true;
        if (this.canDelete + this.notDelete < this.keepNumber) {
            if (notificationSortMessage.getAutoDelete() == -1) {
                notificationSortMessage.setGroup(PushConstant.Notification.DEFAULT_NOTIFICATION_NOT_DELETE_GROUP_KEY + context.getPackageName());
            } else {
                notificationSortMessage.setGroup(PushConstant.Notification.DEFAULT_NOTIFICATION_CAN_DELETE_GROUP_KEY + context.getPackageName());
            }
        } else if (notificationSortMessage.getAutoDelete() == -1) {
            notificationSortMessage.setGroup(PushConstant.Notification.DEFAULT_NOTIFICATION_NOT_DELETE_GROUP_KEY + context.getPackageName());
            int i2 = this.keepNumber - this.notDelete;
            if (i2 > 0) {
                deleteLowestMessage(context, notificationManager, i2 - 1);
            } else {
                Notification notificationCreateDefaultGroupNotification = NotificationHelper.createDefaultGroupNotification(context, notificationSortMessage.getGroup(), builder);
                if (notificationCreateDefaultGroupNotification != null) {
                    notificationManager.notify(4096, notificationCreateDefaultGroupNotification);
                }
            }
        } else {
            zJudgeShowCurrentMessage = judgeShowCurrentMessage(context, notificationManager, notificationSortMessage);
        }
        if (zJudgeShowCurrentMessage) {
            doMessageConfig(builder, notificationSortMessage);
        } else {
            McsStatisticUtils.statisticEvent(context, McsEventConstant.EventId.EVENT_ID_PUSH_NO_SHOW_BY_FOLD, createDataMessage(context, notificationSortMessage));
        }
        return zJudgeShowCurrentMessage;
    }

    private void dealShowedNotificationList(NotificationManager notificationManager, Context context) {
        initParams(NotificationHelper.getActiveNotifications(notificationManager, context.getPackageName()));
    }

    private void deleteLowestMessage(Context context, NotificationManager notificationManager, int i2) throws JSONException {
        keepMessage(this.canDeleteList, i2);
        sendCommandOrStatic(context, notificationManager, this.canDeleteList);
    }

    private void deleteNotification(Context context, NotificationManager notificationManager, JSONArray jSONArray, List<NotificationSortMessage> list, List<DataMessage> list2) throws JSONException {
        for (NotificationSortMessage notificationSortMessage : list) {
            if (notificationSortMessage.isMcs()) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(ConfigConstant.NotificationSort.EXTRA_MESSAGE_ID, notificationSortMessage.getMessageId());
                    jSONObject.put(ConfigConstant.NotificationSort.EXTRA_NOTIFY_ID, notificationSortMessage.getNotifyId());
                    jSONArray.put(jSONObject);
                } catch (JSONException unused) {
                }
            } else {
                list2.add(createDataMessage(context, notificationSortMessage));
                this.deleteList.add(notificationSortMessage.getMessageId());
            }
            notificationManager.cancel(notificationSortMessage.getNotifyId());
        }
    }

    private void doMessageConfig(PushNotification.Builder builder, NotificationSortMessage notificationSortMessage) {
        Bundle bundle = new Bundle();
        bundle.putInt(ConfigConstant.NotificationSort.EXTRA_AUTO_DELETE, notificationSortMessage.getAutoDelete());
        bundle.putInt(ConfigConstant.NotificationSort.EXTRA_IMPORTANT_LEVEL, notificationSortMessage.getImportantLevel());
        bundle.putString(ConfigConstant.NotificationSort.EXTRA_MESSAGE_ID, notificationSortMessage.getMessageId());
        bundle.putLong(ConfigConstant.NotificationSort.EXTRA_POST_TIME, System.currentTimeMillis());
        bundle.putBoolean(ConfigConstant.NotificationSort.EXTRA_IS_MCS, false);
        bundle.putString(ConfigConstant.NotificationSort.EXTRA_STATISTIC_DATA, notificationSortMessage.getStatisticData());
        builder.addExtras(bundle);
        builder.setGroup(notificationSortMessage.getGroup());
    }

    private boolean doTask(PushNotification.Builder builder, int i2, int i3, String str, String str2) {
        Context context = PushService.getInstance().getContext();
        if (builder == null || context == null) {
            return false;
        }
        NotificationManager notificationManager = NotificationHelper.getNotificationManager(context);
        NotificationSortMessage notificationSortMessage = new NotificationSortMessage(str, i3, i2, false, System.currentTimeMillis(), str2);
        if (!judgeMessageNeedDoAntiDeleteAndAntiFolderLogic(context, notificationManager, notificationSortMessage, builder)) {
            return true;
        }
        dealShowedNotificationList(notificationManager, context);
        return dealCurrentMessage(notificationManager, context, builder, notificationSortMessage);
    }

    public static PushNotificationSortManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void initCanDeleteAndNotDelete(int i2) {
        if (i2 == -1) {
            this.notDelete++;
        } else if (i2 == 1) {
            this.canDelete++;
        }
    }

    private void initHighAndNormalSize(int i2) {
        if (i2 == 7) {
            this.highSize++;
        } else if (i2 == 5) {
            this.normalSize++;
        }
    }

    private void initList(NotificationSortMessage notificationSortMessage) {
        if (notificationSortMessage.getAutoDelete() != 1) {
            return;
        }
        if (this.canDeleteList.size() != 0) {
            for (int size = this.canDeleteList.size() - 1; size >= 0; size--) {
                NotificationSortMessage notificationSortMessage2 = this.canDeleteList.get(size);
                if (notificationSortMessage.getImportantLevel() >= notificationSortMessage2.getImportantLevel() && notificationSortMessage.getPostTime() >= notificationSortMessage2.getPostTime()) {
                    this.canDeleteList.add(size + 1, notificationSortMessage2);
                    return;
                }
            }
        }
        this.canDeleteList.add(0, notificationSortMessage);
    }

    private void initParams(StatusBarNotification[] statusBarNotificationArr) {
        resetParams();
        if (statusBarNotificationArr == null || statusBarNotificationArr.length == 0) {
            return;
        }
        for (StatusBarNotification statusBarNotification : statusBarNotificationArr) {
            Bundle bundle = statusBarNotification.getNotification().extras;
            boolean z2 = bundle.getBoolean(ConfigConstant.NotificationSort.EXTRA_IS_MCS, true);
            long j2 = bundle.getLong(ConfigConstant.NotificationSort.EXTRA_POST_TIME, statusBarNotification.getPostTime());
            String string = bundle.getString(ConfigConstant.NotificationSort.EXTRA_MESSAGE_ID, "");
            int i2 = bundle.getInt(ConfigConstant.NotificationSort.EXTRA_AUTO_DELETE, 1);
            int i3 = bundle.getInt(ConfigConstant.NotificationSort.EXTRA_IMPORTANT_LEVEL, 7);
            NotificationSortMessage notificationSortMessage = new NotificationSortMessage(string, i3, i2, z2, j2, statusBarNotification.getId(), bundle.getString(ConfigConstant.NotificationSort.EXTRA_STATISTIC_DATA));
            initCanDeleteAndNotDelete(i2);
            initHighAndNormalSize(i3);
            initList(notificationSortMessage);
        }
    }

    private boolean judgeShowCurrentMessage(Context context, NotificationManager notificationManager, NotificationSortMessage notificationSortMessage) throws JSONException {
        int i2 = this.notDelete;
        int i3 = this.keepNumber;
        boolean z2 = false;
        if (i2 >= i3) {
            return false;
        }
        int i4 = i3 - i2;
        if (notificationSortMessage.getImportantLevel() == 7 || (notificationSortMessage.getImportantLevel() != 5 ? this.highSize + this.normalSize < i4 : this.highSize < i4)) {
            z2 = true;
        }
        if (z2) {
            deleteLowestMessage(context, notificationManager, i4 - 1);
        }
        return z2;
    }

    private int keepMessage(List<NotificationSortMessage> list, int i2) {
        int size = list == null ? 0 : list.size();
        if (i2 <= 0 || size == 0) {
            return i2;
        }
        if (size < i2) {
            int i3 = i2 - size;
            list.clear();
            return i3;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            list.remove((size - 1) - i4);
        }
        return 0;
    }

    private void resetParams() {
        this.canDelete = 0;
        this.notDelete = 0;
        this.highSize = 0;
        this.normalSize = 0;
        this.canDeleteList.clear();
        this.deleteList.clear();
    }

    private void sendCommandOrStatic(Context context, NotificationManager notificationManager, List<NotificationSortMessage> list) throws JSONException {
        if (list == null || list.size() == 0) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        ArrayList arrayList = new ArrayList();
        deleteNotification(context, notificationManager, jSONArray, list, arrayList);
        if (jSONArray.length() != 0) {
            try {
                jSONObject.put(PushConstant.NotificationSort.SORT_ARRAY, jSONArray);
                HeytapPushManager.cancelNotification(jSONObject);
            } catch (JSONException unused) {
            }
        }
        if (arrayList.size() != 0) {
            HashMap map = new HashMap();
            map.put(McsEventConstant.EventId.EVENT_ID_PUSH_DELETE_BY_FOLD, arrayList);
            StatisticUtils.statisticEvent(context, map);
        }
    }

    public boolean judgeMessageNeedDoAntiDeleteAndAntiFolderLogic(Context context, NotificationManager notificationManager, NotificationSortMessage notificationSortMessage, PushNotification.Builder builder) {
        int i2;
        if (notificationSortMessage.getAutoDelete() == 0 || (i2 = Build.VERSION.SDK_INT) < 24 || i2 >= 30) {
            return false;
        }
        if (!NotificationHelper.isExistNotificationsByPkgAndId(notificationManager, context.getPackageName(), 4096)) {
            return true;
        }
        notificationSortMessage.setGroup(PushConstant.Notification.DEFAULT_NOTIFICATION_NOT_DELETE_GROUP_KEY + context.getPackageName());
        doMessageConfig(builder, notificationSortMessage);
        return false;
    }

    public void startBuild(PushNotification.Builder builder, ISortListener iSortListener) {
        if (builder == null) {
            return;
        }
        callbackListener(iSortListener, doTask(builder, builder.getAutoDelete(), builder.getImportantLevel(), builder.getMessageId(), builder.getStatisticData()), builder);
    }
}
