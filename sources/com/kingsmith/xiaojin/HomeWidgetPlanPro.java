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

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lcom/kingsmith/xiaojin/HomeWidgetPlanPro;", "Les/antonborri/home_widget/HomeWidgetProvider;", "()V", "onUpdate", "", f.X, "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "widgetData", "Landroid/content/SharedPreferences;", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHomeWidgetPlanPro.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeWidgetPlanPro.kt\ncom/kingsmith/xiaojin/HomeWidgetPlanPro\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,158:1\n13330#2,2:159\n*S KotlinDebug\n*F\n+ 1 HomeWidgetPlanPro.kt\ncom/kingsmith/xiaojin/HomeWidgetPlanPro\n*L\n21#1:159,2\n*E\n"})
/* loaded from: classes4.dex */
public final class HomeWidgetPlanPro extends HomeWidgetProvider {
    @Override // es.antonborri.home_widget.HomeWidgetProvider
    public void onUpdate(@NotNull Context context, @NotNull AppWidgetManager appWidgetManager, @NotNull int[] appWidgetIds, @NotNull SharedPreferences widgetData) throws JSONException, NumberFormatException {
        String str;
        String str2;
        String str3;
        Context context2 = context;
        int[] appWidgetIds2 = appWidgetIds;
        SharedPreferences widgetData2 = widgetData;
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
        Intrinsics.checkNotNullParameter(appWidgetIds2, "appWidgetIds");
        Intrinsics.checkNotNullParameter(widgetData2, "widgetData");
        int length = appWidgetIds2.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = appWidgetIds2[i2];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.home_widget_plan_pro);
            PendingIntent activity = HomeWidgetLaunchIntent.INSTANCE.getActivity(context2, MainActivity.class, null, "plan");
            Log.e("HomeWidgetProvider", " pendingIntent PlanPro " + activity);
            remoteViews.setOnClickPendingIntent(R.id.widget_container, activity);
            String string = widgetData2.getString("Locale", null);
            String string2 = widgetData2.getString("planTarget", null);
            Log.e("HomeWidgetProvider", "PlanPro widgetDataLocale " + string + " widgetData " + string2);
            String str4 = "0";
            if (string2 != null) {
                JSONObject jSONObject = new JSONObject(string2);
                String string3 = jSONObject.getString("planTargetDuration");
                String string4 = jSONObject.getString("planTargetTotalDuration");
                String string5 = jSONObject.getString("planTargetCalorie");
                String string6 = jSONObject.getString("planTargetTotalCalorie");
                Log.e("HomeWidgetProvider", "PlanTarget cal " + string5 + " dur " + string3 + " durTotal " + string4 + " calTotal " + string6);
                int i4 = R.id.plan_pro_time;
                if (string3 == null) {
                    str = "0";
                } else {
                    Intrinsics.checkNotNull(string3);
                    str = string3;
                }
                remoteViews.setTextViewText(i4, str);
                int i5 = R.id.plan_pro_time_target;
                if (string4 == null) {
                    str2 = "0";
                } else {
                    Intrinsics.checkNotNull(string4);
                    str2 = string4;
                }
                remoteViews.setTextViewText(i5, str2);
                int i6 = R.id.plan_pro_cal;
                if (string5 == null) {
                    str3 = "0";
                } else {
                    Intrinsics.checkNotNull(string5);
                    str3 = string5;
                }
                remoteViews.setTextViewText(i6, str3);
                int i7 = R.id.plan_pro_cal_target;
                if (string6 != null) {
                    Intrinsics.checkNotNull(string6);
                    str4 = string6;
                }
                remoteViews.setTextViewText(i7, str4);
                Intrinsics.checkNotNull(string5);
                double d2 = Double.parseDouble(string5);
                Intrinsics.checkNotNull(string6);
                double d3 = 100;
                double d4 = (d2 / Double.parseDouble(string6)) * d3;
                Log.e("HomeWidgetProvider", "calProgress " + d4);
                remoteViews.setProgressBar(R.id.plan_progress_cal, 100, (int) d4, false);
                Intrinsics.checkNotNull(string3);
                double d5 = Double.parseDouble(string3);
                Intrinsics.checkNotNull(string4);
                double d6 = (d5 / Double.parseDouble(string4)) * d3;
                Log.e("HomeWidgetProvider", "timeProgress " + d6);
                remoteViews.setProgressBar(R.id.plan_progress_time, 100, (int) d6, false);
            } else {
                remoteViews.setTextViewText(R.id.plan_pro_time, "0");
                remoteViews.setTextViewText(R.id.plan_pro_time_target, "0");
                remoteViews.setTextViewText(R.id.plan_pro_cal, "0");
                remoteViews.setTextViewText(R.id.plan_pro_cal_target, "0");
                remoteViews.setProgressBar(R.id.plan_progress_time, 100, 0, false);
                remoteViews.setProgressBar(R.id.plan_progress_cal, 100, 0, false);
            }
            if (string != null) {
                String string7 = new JSONObject(string).getString("LanguageCode");
                String language = LanguageUtil.INSTANCE.getSystemLocal().getLanguage();
                Intrinsics.checkNotNullExpressionValue(language, "getLanguage(...)");
                Log.e("HomeWidgetProvider", "sysLanguage " + language + " languageCode " + string7);
                if (Intrinsics.areEqual(string7, "en") || (Intrinsics.areEqual(language, "en") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, En.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, En.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, En.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, En.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "zh") || (Intrinsics.areEqual(language, "zh") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Cn.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Cn.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Cn.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Cn.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, bc.aF) || (Intrinsics.areEqual(language, bc.aF) && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Pl.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Pl.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Pl.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Pl.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "fi") || (Intrinsics.areEqual(language, "fi") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Fi.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Fi.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Fi.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Fi.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, a.f9715r) || (Intrinsics.areEqual(language, a.f9715r) && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Sv.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Sv.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Sv.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Sv.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "es") || (Intrinsics.areEqual(language, "es") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Es.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Es.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Es.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Es.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "ru") || (Intrinsics.areEqual(language, "ru") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Ru.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Ru.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Ru.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Ru.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "ja") || (Intrinsics.areEqual(language, "ja") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Ja.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Ja.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Ja.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Ja.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "nb") || (Intrinsics.areEqual(language, "nb") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Nb.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Nb.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Nb.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Nb.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "de") || (Intrinsics.areEqual(language, "de") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, De.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, De.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, De.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, De.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, AdvanceSetting.NETWORK_TYPE) || (Intrinsics.areEqual(language, AdvanceSetting.NETWORK_TYPE) && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, It.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, It.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, It.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, It.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "fr") || (Intrinsics.areEqual(language, "fr") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Fr.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Fr.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Fr.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Fr.locale_plan_target_duration);
                } else if (Intrinsics.areEqual(string7, "ko") || (Intrinsics.areEqual(language, "ko") && Intrinsics.areEqual(string7, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_plan_pro_train, Ko.locale_plan_pro_train);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_title, Ko.locale_plan_pro_title);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_consume, Ko.locale_plan_target_consume);
                    remoteViews.setTextViewText(R.id.locale_plan_pro_duration, Ko.locale_plan_target_duration);
                }
            }
            appWidgetManager.updateAppWidget(i3, remoteViews);
            i2++;
            context2 = context;
            appWidgetIds2 = appWidgetIds;
            widgetData2 = widgetData;
        }
    }
}
