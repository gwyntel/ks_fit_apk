package com.heytap.mcssdk.statis;

import android.content.Context;
import com.heytap.mcssdk.utils.StatUtil;
import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.mode.MessageStat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class McsStatisticUtils {
    public static boolean statisticEvent(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MessageStat(context.getPackageName(), str));
        return StatUtil.statisticMessage(context, arrayList);
    }

    public static boolean statisticEvent(Context context, String str, DataMessage dataMessage) {
        MessageStat messageStat;
        ArrayList arrayList = new ArrayList();
        String packageName = context.getPackageName();
        if (dataMessage == null) {
            messageStat = new MessageStat(packageName, str);
        } else {
            messageStat = new MessageStat(dataMessage.getMessageType(), packageName, dataMessage.getGlobalId(), dataMessage.getTaskID(), str, null, dataMessage.getStatisticsExtra(), dataMessage.getDataExtra());
        }
        arrayList.add(messageStat);
        return StatUtil.statisticMessage(context, arrayList);
    }

    public static boolean statisticEvent(Context context, List<String> list) {
        ArrayList arrayList = new ArrayList();
        String packageName = context.getPackageName();
        if (list != null && list.size() != 0) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new MessageStat(packageName, it.next()));
            }
        }
        return StatUtil.statisticMessage(context, arrayList);
    }
}
