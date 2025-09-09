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

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lcom/kingsmith/xiaojin/HomeMultiple;", "Les/antonborri/home_widget/HomeWidgetProvider;", "()V", "onUpdate", "", f.X, "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "widgetData", "Landroid/content/SharedPreferences;", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHomeMultiple.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeMultiple.kt\ncom/kingsmith/xiaojin/HomeMultiple\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,317:1\n13330#2,2:318\n*S KotlinDebug\n*F\n+ 1 HomeMultiple.kt\ncom/kingsmith/xiaojin/HomeMultiple\n*L\n25#1:318,2\n*E\n"})
/* loaded from: classes4.dex */
public final class HomeMultiple extends HomeWidgetProvider {
    @Override // es.antonborri.home_widget.HomeWidgetProvider
    public void onUpdate(@NotNull Context context, @NotNull AppWidgetManager appWidgetManager, @NotNull int[] appWidgetIds, @NotNull SharedPreferences widgetData) throws JSONException {
        int i2;
        String string;
        Context context2 = context;
        int[] appWidgetIds2 = appWidgetIds;
        SharedPreferences widgetData2 = widgetData;
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
        Intrinsics.checkNotNullParameter(appWidgetIds2, "appWidgetIds");
        Intrinsics.checkNotNullParameter(widgetData2, "widgetData");
        int length = appWidgetIds2.length;
        int i3 = 0;
        while (i3 < length) {
            int i4 = appWidgetIds2[i3];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.home_widget_multiple);
            PendingIntent activity = HomeWidgetLaunchIntent.INSTANCE.getActivity(context2, MainActivity.class, null, "mine");
            String string2 = widgetData2.getString("appUnit", null);
            String string3 = widgetData2.getString("Locale", null);
            String string4 = widgetData2.getString("todaySport", null);
            Log.e("HomeWidgetProvider", "Multiple widgetDataLocale " + string3 + " widgetData " + string4 + " appUnit " + string2);
            if (string4 != null) {
                JSONObject jSONObject = new JSONObject(string4);
                String string5 = jSONObject.getString("todaySportDuration");
                String string6 = jSONObject.getString("todaySportCalorie");
                string = jSONObject.getString("todaySportDistance");
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                String string7 = jSONObject.getString("todaySportSteps");
                String string8 = jSONObject.getString("AppLanguageCode");
                StringBuilder sb = new StringBuilder();
                i2 = length;
                sb.append("SportMultiple languageCode ");
                sb.append(string8);
                sb.append("  dur ");
                sb.append(string5);
                sb.append(" cal ");
                sb.append(string6);
                sb.append(" dist ");
                sb.append(string);
                sb.append(" step ");
                sb.append(string7);
                Log.e("HomeWidgetProvider", sb.toString());
                remoteViews.setOnClickPendingIntent(R.id.widget_container, activity);
                int i5 = R.id.multiply_cal;
                if (string6 == null) {
                    string6 = "0";
                } else {
                    Intrinsics.checkNotNull(string6);
                }
                remoteViews.setTextViewText(i5, string6);
                int i6 = R.id.multiply_minute;
                if (string5 == null) {
                    string5 = "0";
                } else {
                    Intrinsics.checkNotNull(string5);
                }
                remoteViews.setTextViewText(i6, string5);
                remoteViews.setTextViewText(R.id.multiply_distance, string);
                int i7 = R.id.multiply_step;
                if (string7 == null) {
                    string7 = "0";
                } else {
                    Intrinsics.checkNotNull(string7);
                }
                remoteViews.setTextViewText(i7, string7);
            } else {
                i2 = length;
                remoteViews.setTextViewText(R.id.multiply_cal, "0");
                remoteViews.setTextViewText(R.id.multiply_minute, "0");
                remoteViews.setTextViewText(R.id.multiply_distance, "0");
                remoteViews.setTextViewText(R.id.multiply_step, "0");
                string = "0";
            }
            if (string2 != null) {
                if (Intrinsics.areEqual(string2, "1")) {
                    if (Double.parseDouble(string) > 0.0d) {
                        double d2 = Double.parseDouble(string) * 0.62d;
                        int i8 = R.id.multiply_distance;
                        CharSequence charSequenceValueOf = String.valueOf(d2);
                        remoteViews.setTextViewText(i8, charSequenceValueOf != null ? charSequenceValueOf : "0");
                    }
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist_unit, "(mi)");
                } else {
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist_unit, "(km)");
                }
            }
            if (string3 != null) {
                String string9 = new JSONObject(string3).getString("LanguageCode");
                String language = LanguageUtil.INSTANCE.getSystemLocal().getLanguage();
                Intrinsics.checkNotNullExpressionValue(language, "getLanguage(...)");
                Log.e("HomeWidgetProvider", "sysLanguage " + language + " languageCode " + string9);
                if (Intrinsics.areEqual(string9, "en") || (Intrinsics.areEqual(language, "en") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, En.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, En.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, En.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, En.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "zh") || (Intrinsics.areEqual(language, "zh") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Cn.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Cn.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Cn.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Cn.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, bc.aF) || (Intrinsics.areEqual(language, bc.aF) && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Pl.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Pl.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Pl.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Pl.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "fi") || (Intrinsics.areEqual(language, "fi") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Fi.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Fi.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Fi.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Fi.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, a.f9715r) || (Intrinsics.areEqual(language, a.f9715r) && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Sv.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Sv.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Sv.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Sv.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "es") || (Intrinsics.areEqual(language, "es") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Es.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Es.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Es.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Es.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "ru") || (Intrinsics.areEqual(language, "ru") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Ru.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Ru.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Ru.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Ru.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "ja") || (Intrinsics.areEqual(language, "ja") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Ja.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Ja.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Ja.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Ja.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "nb") || (Intrinsics.areEqual(language, "nb") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Nb.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Nb.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Nb.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Nb.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "de") || (Intrinsics.areEqual(language, "de") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, De.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, De.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, De.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, De.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, AdvanceSetting.NETWORK_TYPE) || (Intrinsics.areEqual(language, AdvanceSetting.NETWORK_TYPE) && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, It.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, It.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, It.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, It.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "fr") || (Intrinsics.areEqual(language, "fr") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Fr.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Fr.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Fr.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Fr.locale_multiple_today_step);
                } else if (Intrinsics.areEqual(string9, "ko") || (Intrinsics.areEqual(language, "ko") && Intrinsics.areEqual(string9, "sys"))) {
                    remoteViews.setTextViewText(R.id.locale_multiple_min, Ko.locale_multiple_min);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_consume, Ko.locale_multiple_today_consume);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_dist, Ko.locale_multiple_today_dist);
                    remoteViews.setTextViewText(R.id.locale_multiple_today_step, Ko.locale_multiple_today_step);
                }
            }
            appWidgetManager.updateAppWidget(i4, remoteViews);
            i3++;
            context2 = context;
            appWidgetIds2 = appWidgetIds;
            widgetData2 = widgetData;
            length = i2;
        }
    }
}
