package androidx.activity;

import android.view.View;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a\u0013\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003\u001a\u0019\u0010\u0004\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u0007¨\u0006\b"}, d2 = {"findViewTreeFullyDrawnReporterOwner", "Landroidx/activity/FullyDrawnReporterOwner;", "Landroid/view/View;", TmpConstant.PROPERTY_IDENTIFIER_GET, "setViewTreeFullyDrawnReporterOwner", "", "fullyDrawnReporterOwner", TmpConstant.PROPERTY_IDENTIFIER_SET, "activity_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@JvmName(name = "ViewTreeFullyDrawnReporterOwner")
/* loaded from: classes.dex */
public final class ViewTreeFullyDrawnReporterOwner {
    @JvmName(name = TmpConstant.PROPERTY_IDENTIFIER_GET)
    @Nullable
    public static final FullyDrawnReporterOwner get(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return (FullyDrawnReporterOwner) SequencesKt.firstOrNull(SequencesKt.mapNotNull(SequencesKt.generateSequence(view, new Function1<View, View>() { // from class: androidx.activity.ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$1
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final View invoke(@NotNull View it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Object parent = it.getParent();
                if (parent instanceof View) {
                    return (View) parent;
                }
                return null;
            }
        }), new Function1<View, FullyDrawnReporterOwner>() { // from class: androidx.activity.ViewTreeFullyDrawnReporterOwner$findViewTreeFullyDrawnReporterOwner$2
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final FullyDrawnReporterOwner invoke(@NotNull View it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Object tag = it.getTag(R.id.report_drawn);
                if (tag instanceof FullyDrawnReporterOwner) {
                    return (FullyDrawnReporterOwner) tag;
                }
                return null;
            }
        }));
    }

    @JvmName(name = TmpConstant.PROPERTY_IDENTIFIER_SET)
    public static final void set(@NotNull View view, @NotNull FullyDrawnReporterOwner fullyDrawnReporterOwner) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(fullyDrawnReporterOwner, "fullyDrawnReporterOwner");
        view.setTag(R.id.report_drawn, fullyDrawnReporterOwner);
    }
}
