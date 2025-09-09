package es.antonborri.home_widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.share.internal.ShareConstants;
import com.umeng.analytics.pro.f;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Les/antonborri/home_widget/HomeWidgetBackgroundIntent;", "", "()V", "HOME_WIDGET_BACKGROUND_ACTION", "", "getBroadcast", "Landroid/app/PendingIntent;", f.X, "Landroid/content/Context;", ShareConstants.MEDIA_URI, "Landroid/net/Uri;", "home_widget_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class HomeWidgetBackgroundIntent {

    @NotNull
    private static final String HOME_WIDGET_BACKGROUND_ACTION = "es.antonborri.home_widget.action.BACKGROUND";

    @NotNull
    public static final HomeWidgetBackgroundIntent INSTANCE = new HomeWidgetBackgroundIntent();

    private HomeWidgetBackgroundIntent() {
    }

    public static /* synthetic */ PendingIntent getBroadcast$default(HomeWidgetBackgroundIntent homeWidgetBackgroundIntent, Context context, Uri uri, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            uri = null;
        }
        return homeWidgetBackgroundIntent.getBroadcast(context, uri);
    }

    @NotNull
    public final PendingIntent getBroadcast(@NotNull Context context, @Nullable Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent(context, (Class<?>) HomeWidgetBackgroundReceiver.class);
        intent.setData(uri);
        intent.setAction(HOME_WIDGET_BACKGROUND_ACTION);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 201326592);
        Intrinsics.checkNotNullExpressionValue(broadcast, "getBroadcast(...)");
        return broadcast;
    }
}
