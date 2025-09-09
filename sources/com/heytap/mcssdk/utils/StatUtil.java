package com.heytap.mcssdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.heytap.mcssdk.PushService;
import com.heytap.mcssdk.constant.IntentConstant;
import com.heytap.mcssdk.constant.MessageConstant;
import com.heytap.msp.push.mode.MessageStat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class StatUtil {
    private static final String COUNT = "count";
    private static final int MCS_SUPPORT_VERSION = 1017;
    private static final String STAT_LIST = "list";
    private static final String TYPE = "type";

    private static boolean isSupportStatisticByMcs(Context context) throws PackageManager.NameNotFoundException {
        String mcsPackageName = PushService.getInstance().getMcsPackageName(context);
        return Utils.isExistPackage(context, mcsPackageName) && Utils.getVersionCode(context, mcsPackageName) >= 1017;
    }

    public static void statisticMessage(Context context, MessageStat messageStat) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(messageStat);
        statisticMessage(context, linkedList);
    }

    private static boolean statisticMessageByMcs(Context context, List<MessageStat> list) {
        try {
            Intent intent = new Intent();
            intent.setAction(PushService.getInstance().getReceiveSdkAction(context));
            intent.setPackage(PushService.getInstance().getMcsPackageName(context));
            intent.putExtra(IntentConstant.APP_PACKAGE, context.getPackageName());
            intent.putExtra("type", MessageConstant.CommandId.COMMAND_STATISTIC);
            intent.putExtra(COUNT, list.size());
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator<MessageStat> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toJsonObject());
            }
            intent.putStringArrayListExtra("list", arrayList);
            context.startService(intent);
            return true;
        } catch (Exception e2) {
            LogUtil.e("statisticMessage--Exception" + e2.getMessage());
            return false;
        }
    }

    public static boolean statisticMessage(Context context, List<MessageStat> list) {
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(list);
        LogUtil.d("isSupportStatisticByMcs:" + isSupportStatisticByMcs(context) + ",list size:" + linkedList.size());
        if (linkedList.size() <= 0 || !isSupportStatisticByMcs(context)) {
            return false;
        }
        return statisticMessageByMcs(context, linkedList);
    }
}
