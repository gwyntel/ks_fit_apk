package androidx.appcompat.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
class ActionBarBackgroundDrawable extends Drawable {

    /* renamed from: a, reason: collision with root package name */
    final ActionBarContainer f2185a;

    @RequiresApi(21)
    private static class Api21Impl {
        private Api21Impl() {
        }

        public static void getOutline(Drawable drawable, Outline outline) {
            drawable.getOutline(outline);
        }
    }

    public ActionBarBackgroundDrawable(ActionBarContainer actionBarContainer) {
        this.f2185a = actionBarContainer;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        ActionBarContainer actionBarContainer = this.f2185a;
        if (actionBarContainer.f2189d) {
            Drawable drawable = actionBarContainer.f2188c;
            if (drawable != null) {
                drawable.draw(canvas);
                return;
            }
            return;
        }
        Drawable drawable2 = actionBarContainer.f2186a;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        ActionBarContainer actionBarContainer2 = this.f2185a;
        Drawable drawable3 = actionBarContainer2.f2187b;
        if (drawable3 == null || !actionBarContainer2.f2190e) {
            return;
        }
        drawable3.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void getOutline(@NonNull Outline outline) {
        ActionBarContainer actionBarContainer = this.f2185a;
        if (actionBarContainer.f2189d) {
            if (actionBarContainer.f2188c != null) {
                Api21Impl.getOutline(actionBarContainer.f2186a, outline);
            }
        } else {
            Drawable drawable = actionBarContainer.f2186a;
            if (drawable != null) {
                Api21Impl.getOutline(drawable, outline);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }
}
