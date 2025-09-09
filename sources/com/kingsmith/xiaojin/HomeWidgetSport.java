package com.kingsmith.xiaojin;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import com.alipay.sdk.m.s.a;
import com.kingsmith.xiaojin.language.Cn;
import com.kingsmith.xiaojin.language.De;
import com.kingsmith.xiaojin.language.En;
import com.kingsmith.xiaojin.language.Es;
import com.kingsmith.xiaojin.language.Fi;
import com.kingsmith.xiaojin.language.Fr;
import com.kingsmith.xiaojin.language.It;
import com.kingsmith.xiaojin.language.Ja;
import com.kingsmith.xiaojin.language.Ko;
import com.kingsmith.xiaojin.language.Nb;
import com.kingsmith.xiaojin.language.Pl;
import com.kingsmith.xiaojin.language.Ru;
import com.kingsmith.xiaojin.language.Sv;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.f;
import es.antonborri.home_widget.HomeWidgetLaunchIntent;
import es.antonborri.home_widget.HomeWidgetProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lcom/kingsmith/xiaojin/HomeWidgetSport;", "Les/antonborri/home_widget/HomeWidgetProvider;", "()V", "onUpdate", "", f.X, "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "widgetData", "Landroid/content/SharedPreferences;", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHomeWidgetSport.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeWidgetSport.kt\ncom/kingsmith/xiaojin/HomeWidgetSport\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,232:1\n13330#2,2:233\n*S KotlinDebug\n*F\n+ 1 HomeWidgetSport.kt\ncom/kingsmith/xiaojin/HomeWidgetSport\n*L\n23#1:233,2\n*E\n"})
/* loaded from: classes4.dex */
public final class HomeWidgetSport extends HomeWidgetProvider {
    @Override // es.antonborri.home_widget.HomeWidgetProvider
    public void onUpdate(@NotNull Context context, @NotNull AppWidgetManager appWidgetManager, @NotNull int[] appWidgetIds, @NotNull SharedPreferences widgetData) throws JSONException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
        Intrinsics.checkNotNullParameter(appWidgetIds, "appWidgetIds");
        Intrinsics.checkNotNullParameter(widgetData, "widgetData");
        for (int i2 : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.home_widget_sport);
            PendingIntent activity = HomeWidgetLaunchIntent.INSTANCE.getActivity(context, MainActivity.class, null, "sport");
            String string = widgetData.getString("Locale", null);
            String string2 = widgetData.getString("todaySport", null);
            Log.e("HomeWidgetProvider", "Sport widgetDataLocale " + string + " widgetData " + string2);
            String str = "0";
            if (string2 != null) {
                JSONObject jSONObject = new JSONObject(string2);
                String string3 = jSONObject.getString("todaySportDuration");
                String string4 = jSONObject.getString("todaySportCalorie");
                int i3 = R.id.sport_cal;
                if (string4 == null) {
                    string4 = "0";
                } else {
                    Intrinsics.checkNotNull(string4);
                }
                remoteViews.setTextViewText(i3, string4);
                int i4 = R.id.sport_time;
                if (string3 != null) {
                    Intrinsics.checkNotNull(string3);
                    str = string3;
                }
                remoteViews.setTextViewText(i4, str);
            } else {
                remoteViews.setTextViewText(R.id.sport_cal, "0");
                remoteViews.setTextViewText(R.id.sport_time, "0");
            }
            if (string != null) {
                String string5 = new JSONObject(string).getString("LanguageCode");
                String language = LanguageUtil.INSTANCE.getSystemLocal().getLanguage();
                Intrinsics.checkNotNullExpressionValue(language, "getLanguage(...)");
                Log.e("HomeWidgetProvider", "sysLanguage " + language + " languageCode " + string5);
                if (Intrinsics.areEqual(string5, "en") || (Intrinsics.areEqual(language, "en") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, En.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, En.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, En.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "zh") || (Intrinsics.areEqual(language, "zh") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Cn.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Cn.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Cn.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, bc.aF) || (Intrinsics.areEqual(language, bc.aF) && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Pl.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Pl.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Pl.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "fi") || (Intrinsics.areEqual(language, "fi") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Fi.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Fi.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Fi.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, a.f9715r) || (Intrinsics.areEqual(language, a.f9715r) && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Sv.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Sv.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Sv.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "es") || (Intrinsics.areEqual(language, "es") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Es.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Es.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Es.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "ru") || (Intrinsics.areEqual(language, "ru") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Ru.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Ru.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Ru.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "ja") || (Intrinsics.areEqual(language, "ja") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Ja.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Ja.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Ja.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "nb") || (Intrinsics.areEqual(language, "nb") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Nb.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Nb.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Nb.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "de") || (Intrinsics.areEqual(language, "de") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, De.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, De.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, De.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, AdvanceSetting.NETWORK_TYPE) || (Intrinsics.areEqual(language, AdvanceSetting.NETWORK_TYPE) && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, It.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, It.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, It.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "fr") || (Intrinsics.areEqual(language, "fr") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Fr.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Fr.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Fr.locale_plan_target_consume);
                } else if (Intrinsics.areEqual(string5, "ko") || (Intrinsics.areEqual(language, "ko") && Intrinsics.areEqual(string5, "sys"))) {
                    remoteViews.setTextViewText(R.id.local_title_today, Ko.local_title_today);
                    remoteViews.setTextViewText(R.id.locale_today_time, Ko.locale_plan_target_duration);
                    remoteViews.setTextViewText(R.id.locale_today_cal, Ko.locale_plan_target_consume);
                }
            }
            remoteViews.setOnClickPendingIntent(R.id.widget_container, activity);
            appWidgetManager.updateAppWidget(i2, remoteViews);
        }
    }
}
