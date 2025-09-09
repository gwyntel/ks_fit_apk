package es.antonborri.home_widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import com.facebook.share.internal.ShareConstants;
import com.umeng.analytics.pro.f;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\t\u001a\u00020\n\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Les/antonborri/home_widget/HomeWidgetLaunchIntent;", "", "()V", "HOME_WIDGET_LAUNCH_ACTION", "", "getHOME_WIDGET_LAUNCH_ACTION", "()Ljava/lang/String;", "setHOME_WIDGET_LAUNCH_ACTION", "(Ljava/lang/String;)V", "getActivity", "Landroid/app/PendingIntent;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/app/Activity;", f.X, "Landroid/content/Context;", "activityClass", "Ljava/lang/Class;", ShareConstants.MEDIA_URI, "Landroid/net/Uri;", "type", "home_widget_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class HomeWidgetLaunchIntent {

    @NotNull
    public static final HomeWidgetLaunchIntent INSTANCE = new HomeWidgetLaunchIntent();

    @NotNull
    private static String HOME_WIDGET_LAUNCH_ACTION = "es.antonborri.home_widget.action.LAUNCH";

    private HomeWidgetLaunchIntent() {
    }

    public static /* synthetic */ PendingIntent getActivity$default(HomeWidgetLaunchIntent homeWidgetLaunchIntent, Context context, Class cls, Uri uri, String str, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            uri = null;
        }
        if ((i2 & 8) != 0) {
            str = null;
        }
        return homeWidgetLaunchIntent.getActivity(context, cls, uri, str);
    }

    @NotNull
    public final <T extends Activity> PendingIntent getActivity(@NotNull Context context, @NotNull Class<T> activityClass, @Nullable Uri uri, @Nullable String type) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(activityClass, "activityClass");
        Intent intent = new Intent(context, (Class<?>) activityClass);
        intent.setData(uri);
        intent.setType(type);
        intent.setAction(HOME_WIDGET_LAUNCH_ACTION);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 201326592);
        Intrinsics.checkNotNullExpressionValue(activity, "getActivity(...)");
        return activity;
    }

    @NotNull
    public final String getHOME_WIDGET_LAUNCH_ACTION() {
        return HOME_WIDGET_LAUNCH_ACTION;
    }

    public final void setHOME_WIDGET_LAUNCH_ACTION(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        HOME_WIDGET_LAUNCH_ACTION = str;
    }
}
