package com.xiaomi.push.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.push.ja;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class as {

    /* renamed from: a, reason: collision with root package name */
    private static final boolean f24467a = Log.isLoggable("NCHelper", 3);

    @TargetApi(26)
    private static void a(aw awVar, NotificationChannel notificationChannel, String str) {
        int i2;
        char c2;
        int iA;
        Context contextM744a = awVar.m744a();
        String id = notificationChannel.getId();
        String strA = aw.a(id, awVar.m745a());
        boolean z2 = f24467a;
        if (z2) {
            a("appChannelId:" + id + " oldChannelId:" + strA);
        }
        if (!com.xiaomi.push.j.m550a(contextM744a) || TextUtils.equals(id, strA)) {
            NotificationChannel notificationChannelM743a = awVar.m743a(id);
            if (z2) {
                a("elseLogic getNotificationChannel:" + notificationChannelM743a);
            }
            if (notificationChannelM743a == null) {
                awVar.a(notificationChannel);
            }
            i2 = 0;
            c2 = 0;
        } else {
            NotificationManager notificationManager = (NotificationManager) contextM744a.getSystemService("notification");
            NotificationChannel notificationChannel2 = notificationManager.getNotificationChannel(strA);
            NotificationChannel notificationChannelM743a2 = awVar.m743a(id);
            if (z2) {
                a("xmsfChannel:" + notificationChannel2);
                a("appChannel:" + notificationChannelM743a2);
            }
            if (notificationChannel2 != null) {
                NotificationChannel notificationChannelA = a(id, notificationChannel2);
                if (z2) {
                    a("copyXmsf copyXmsfChannel:" + notificationChannelA);
                }
                if (notificationChannelM743a2 != null) {
                    iA = a(notificationChannelM743a2);
                    awVar.a(notificationChannelA, iA == 0);
                    c2 = 3;
                } else {
                    iA = a(notificationChannel2);
                    a(contextM744a, awVar, notificationChannelA, iA, notificationChannel2.getId());
                    c2 = 4;
                }
                b(contextM744a, id);
                notificationManager.deleteNotificationChannel(strA);
            } else if (notificationChannelM743a2 == null) {
                if (z2) {
                    a("appHack createNotificationChannel:" + notificationChannel);
                }
                awVar.a(notificationChannel);
                c2 = 1;
                iA = 0;
            } else if (m735a(contextM744a, id) || !a(notificationChannel, notificationChannelM743a2)) {
                iA = 0;
                c2 = 0;
            } else {
                if (z2) {
                    a("appHack updateNotificationChannel:" + notificationChannel);
                }
                iA = a(notificationChannelM743a2);
                awVar.a(notificationChannel, iA == 0);
                c2 = 2;
            }
            i2 = iA;
        }
        f.a(awVar.m744a(), awVar.m745a(), id, notificationChannel.getImportance(), str, c2 == 1 || c2 == 4 || c2 == 3, i2);
    }

    private static void b(Context context, String str) {
        if (f24467a) {
            a("recordCopiedChannel:" + str);
        }
        a(context).edit().putBoolean(str, true).apply();
    }

    private static void c(Context context, String str) {
        try {
            aw awVarA = aw.a(context, str);
            Set<String> setKeySet = a(context).getAll().keySet();
            ArrayList arrayList = new ArrayList();
            for (String str2 : setKeySet) {
                if (awVarA.m748a(str2)) {
                    arrayList.add(str2);
                    if (f24467a) {
                        a("delete channel copy record:" + str2);
                    }
                }
            }
            a(context, arrayList);
        } catch (Exception unused) {
        }
    }

    @TargetApi(26)
    private static boolean a(NotificationChannel notificationChannel, NotificationChannel notificationChannel2) {
        boolean z2;
        if (notificationChannel == null || notificationChannel2 == null) {
            return false;
        }
        boolean z3 = true;
        if (TextUtils.equals(notificationChannel.getName(), notificationChannel2.getName())) {
            z2 = false;
        } else {
            if (f24467a) {
                a("appHack channelConfigLowerCompare:getName");
            }
            z2 = true;
        }
        if (!TextUtils.equals(notificationChannel.getDescription(), notificationChannel2.getDescription())) {
            if (f24467a) {
                a("appHack channelConfigLowerCompare:getDescription");
            }
            z2 = true;
        }
        if (notificationChannel.getImportance() != notificationChannel2.getImportance()) {
            notificationChannel.setImportance(Math.min(notificationChannel.getImportance(), notificationChannel2.getImportance()));
            if (f24467a) {
                a("appHack channelConfigLowerCompare:getImportance  " + notificationChannel.getImportance() + " " + notificationChannel2.getImportance());
            }
            z2 = true;
        }
        if (notificationChannel.shouldVibrate() != notificationChannel2.shouldVibrate()) {
            notificationChannel.enableVibration(false);
            if (f24467a) {
                a("appHack channelConfigLowerCompare:enableVibration");
            }
            z2 = true;
        }
        if (notificationChannel.shouldShowLights() != notificationChannel2.shouldShowLights()) {
            notificationChannel.enableLights(false);
            if (f24467a) {
                a("appHack channelConfigLowerCompare:enableLights");
            }
            z2 = true;
        }
        if ((notificationChannel.getSound() != null) != (notificationChannel2.getSound() != null)) {
            notificationChannel.setSound(null, null);
            if (f24467a) {
                a("appHack channelConfigLowerCompare:setSound");
            }
        } else {
            z3 = z2;
        }
        if (f24467a) {
            a("appHack channelConfigLowerCompare:isDifferent:" + z3);
        }
        return z3;
    }

    private static int a(NotificationChannel notificationChannel) {
        int iIntValue = 0;
        try {
            iIntValue = ((Integer) com.xiaomi.push.bk.b((Object) notificationChannel, "getUserLockedFields", new Object[0])).intValue();
            if (f24467a) {
                a("isUserLockedChannel:" + iIntValue + " " + notificationChannel);
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m92a("NCHelper", "is user locked error" + e2);
        }
        return iIntValue;
    }

    @TargetApi(26)
    private static NotificationChannel a(String str, NotificationChannel notificationChannel) {
        androidx.media3.common.util.j.a();
        NotificationChannel notificationChannelA = androidx.browser.trusted.g.a(str, notificationChannel.getName(), notificationChannel.getImportance());
        notificationChannelA.setDescription(notificationChannel.getDescription());
        notificationChannelA.enableVibration(notificationChannel.shouldVibrate());
        notificationChannelA.enableLights(notificationChannel.shouldShowLights());
        notificationChannelA.setSound(notificationChannel.getSound(), notificationChannel.getAudioAttributes());
        notificationChannelA.setLockscreenVisibility(notificationChannel.getLockscreenVisibility());
        return notificationChannelA;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static boolean m735a(Context context, String str) {
        if (f24467a) {
            a("checkCopeidChannel:newFullChannelId:" + str + "  " + a(context).getBoolean(str, false));
        }
        return a(context).getBoolean(str, false);
    }

    private static void a(Context context, List<String> list) {
        if (f24467a) {
            a("deleteCopiedChannelRecord:" + list);
        }
        if (list.isEmpty()) {
            return;
        }
        SharedPreferences.Editor editorEdit = a(context).edit();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            editorEdit.remove(it.next());
        }
        editorEdit.apply();
    }

    private static SharedPreferences a(Context context) {
        return context.getSharedPreferences("mipush_channel_copy_sp", 0);
    }

    @TargetApi(26)
    public static String a(aw awVar, String str, CharSequence charSequence, String str2, int i2, int i3, String str3, String str4) {
        String strM746a = awVar.m746a(str);
        boolean z2 = f24467a;
        if (z2) {
            a("createChannel: appChannelId:" + strM746a + " serverChannelId:" + str + " serverChannelName:" + ((Object) charSequence) + " serverChannelDesc:" + str2 + " serverChannelNotifyType:" + i2 + " serverChannelName:" + ((Object) charSequence) + " serverChannelImportance:" + i3 + " channelSoundStr:" + str3 + " channelPermissions:" + str4);
        }
        NotificationChannel notificationChannelA = androidx.browser.trusted.g.a(strM746a, charSequence, i3);
        notificationChannelA.setDescription(str2);
        notificationChannelA.enableVibration((i2 & 2) != 0);
        notificationChannelA.enableLights((i2 & 4) != 0);
        if ((i2 & 1) == 0) {
            notificationChannelA.setSound(null, null);
        } else if (!TextUtils.isEmpty(str3)) {
            if (str3.startsWith("android.resource://" + awVar.m745a())) {
                notificationChannelA.setSound(Uri.parse(str3), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            }
        }
        if (z2) {
            a("create channel:" + notificationChannelA);
        }
        a(awVar, notificationChannelA, str4);
        return strM746a;
    }

    private static void a(String str) {
        com.xiaomi.channel.commonutils.logger.b.m92a("NCHelper", str);
    }

    public static void a(Context context, String str) {
        if (!com.xiaomi.push.j.m550a(context) || TextUtils.isEmpty(str)) {
            return;
        }
        c(context, str);
        f.a(context, str);
    }

    static void a(ja jaVar) {
        Map<String, String> map;
        if (jaVar == null || (map = jaVar.f656a) == null || !map.containsKey("REMOVE_CHANNEL_MARK")) {
            return;
        }
        jaVar.f652a = 0;
        jaVar.f656a.remove("channel_id");
        jaVar.f656a.remove("channel_importance");
        jaVar.f656a.remove("channel_name");
        jaVar.f656a.remove("channel_description");
        jaVar.f656a.remove("channel_perm");
        com.xiaomi.channel.commonutils.logger.b.m91a("delete channel info by:" + jaVar.f656a.get("REMOVE_CHANNEL_MARK"));
        jaVar.f656a.remove("REMOVE_CHANNEL_MARK");
    }

    @SuppressLint({"WrongConstant"})
    @TargetApi(26)
    static void a(Context context, aw awVar, NotificationChannel notificationChannel, int i2, String str) {
        if (i2 > 0) {
            int iA = com.xiaomi.push.g.a(context) >= 2 ? f.a(context.getPackageName(), str) : 0;
            NotificationChannel notificationChannelA = a(notificationChannel.getId(), notificationChannel);
            if ((i2 & 32) != 0) {
                if (notificationChannel.getSound() != null) {
                    notificationChannelA.setSound(null, null);
                } else {
                    notificationChannelA.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, Notification.AUDIO_ATTRIBUTES_DEFAULT);
                }
            }
            if ((i2 & 16) != 0) {
                if (notificationChannel.shouldVibrate()) {
                    notificationChannelA.enableVibration(false);
                } else {
                    notificationChannelA.enableVibration(true);
                }
            }
            if ((i2 & 8) != 0) {
                if (notificationChannel.shouldShowLights()) {
                    notificationChannelA.enableLights(false);
                } else {
                    notificationChannelA.enableLights(true);
                }
            }
            if ((i2 & 4) != 0) {
                int importance = notificationChannel.getImportance() - 1;
                if (importance <= 0) {
                    importance = 2;
                }
                notificationChannelA.setImportance(importance);
            }
            if ((i2 & 2) != 0) {
                notificationChannelA.setLockscreenVisibility(notificationChannel.getLockscreenVisibility() - 1);
            }
            awVar.a(notificationChannelA);
            awVar.a(notificationChannel, true);
            f.a(awVar.m745a(), notificationChannel.getId(), iA, 0);
            return;
        }
        awVar.a(notificationChannel);
    }
}
