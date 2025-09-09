package androidx.core.view.contentcapture;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureSession;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewStructureCompat;
import androidx.core.view.autofill.AutofillIdCompat;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class ContentCaptureSessionCompat {
    private static final String KEY_VIEW_TREE_APPEARED = "TREAT_AS_VIEW_TREE_APPEARED";
    private static final String KEY_VIEW_TREE_APPEARING = "TREAT_AS_VIEW_TREE_APPEARING";
    private final View mView;
    private final Object mWrappedObj;

    @RequiresApi(23)
    private static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        static Bundle a(ViewStructure viewStructure) {
            return viewStructure.getExtras();
        }
    }

    @RequiresApi(29)
    private static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static AutofillId a(ContentCaptureSession contentCaptureSession, AutofillId autofillId, long j2) {
            return contentCaptureSession.newAutofillId(autofillId, j2);
        }

        @DoNotInline
        static ViewStructure b(ContentCaptureSession contentCaptureSession, View view) {
            return contentCaptureSession.newViewStructure(view);
        }

        @DoNotInline
        static ViewStructure c(ContentCaptureSession contentCaptureSession, AutofillId autofillId, long j2) {
            return contentCaptureSession.newVirtualViewStructure(autofillId, j2);
        }

        @DoNotInline
        static void d(ContentCaptureSession contentCaptureSession, ViewStructure viewStructure) {
            contentCaptureSession.notifyViewAppeared(viewStructure);
        }

        @DoNotInline
        static void e(ContentCaptureSession contentCaptureSession, AutofillId autofillId, long[] jArr) {
            contentCaptureSession.notifyViewsDisappeared(autofillId, jArr);
        }

        @DoNotInline
        public static void notifyViewTextChanged(ContentCaptureSession contentCaptureSession, AutofillId autofillId, CharSequence charSequence) {
            contentCaptureSession.notifyViewTextChanged(autofillId, charSequence);
        }
    }

    @RequiresApi(34)
    private static class Api34Impl {
        private Api34Impl() {
        }

        @DoNotInline
        static void a(ContentCaptureSession contentCaptureSession, List<ViewStructure> list) {
            contentCaptureSession.notifyViewsAppeared(list);
        }
    }

    @RequiresApi(29)
    private ContentCaptureSessionCompat(@NonNull ContentCaptureSession contentCaptureSession, @NonNull View view) {
        this.mWrappedObj = contentCaptureSession;
        this.mView = view;
    }

    @NonNull
    @RequiresApi(29)
    public static ContentCaptureSessionCompat toContentCaptureSessionCompat(@NonNull ContentCaptureSession contentCaptureSession, @NonNull View view) {
        return new ContentCaptureSessionCompat(contentCaptureSession, view);
    }

    @Nullable
    public AutofillId newAutofillId(long j2) {
        if (Build.VERSION.SDK_INT < 29) {
            return null;
        }
        ContentCaptureSession contentCaptureSessionA = a.a(this.mWrappedObj);
        AutofillIdCompat autofillId = ViewCompat.getAutofillId(this.mView);
        Objects.requireNonNull(autofillId);
        return Api29Impl.a(contentCaptureSessionA, autofillId.toAutofillId(), j2);
    }

    @Nullable
    public ViewStructureCompat newVirtualViewStructure(@NonNull AutofillId autofillId, long j2) {
        if (Build.VERSION.SDK_INT >= 29) {
            return ViewStructureCompat.toViewStructureCompat(Api29Impl.c(a.a(this.mWrappedObj), autofillId, j2));
        }
        return null;
    }

    public void notifyViewTextChanged(@NonNull AutofillId autofillId, @Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 29) {
            Api29Impl.notifyViewTextChanged(a.a(this.mWrappedObj), autofillId, charSequence);
        }
    }

    public void notifyViewsAppeared(@NonNull List<ViewStructure> list) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 34) {
            Api34Impl.a(a.a(this.mWrappedObj), list);
            return;
        }
        if (i2 >= 29) {
            ViewStructure viewStructureB = Api29Impl.b(a.a(this.mWrappedObj), this.mView);
            Api23Impl.a(viewStructureB).putBoolean(KEY_VIEW_TREE_APPEARING, true);
            Api29Impl.d(a.a(this.mWrappedObj), viewStructureB);
            for (int i3 = 0; i3 < list.size(); i3++) {
                Api29Impl.d(a.a(this.mWrappedObj), list.get(i3));
            }
            ViewStructure viewStructureB2 = Api29Impl.b(a.a(this.mWrappedObj), this.mView);
            Api23Impl.a(viewStructureB2).putBoolean(KEY_VIEW_TREE_APPEARED, true);
            Api29Impl.d(a.a(this.mWrappedObj), viewStructureB2);
        }
    }

    public void notifyViewsDisappeared(@NonNull long[] jArr) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 34) {
            ContentCaptureSession contentCaptureSessionA = a.a(this.mWrappedObj);
            AutofillIdCompat autofillId = ViewCompat.getAutofillId(this.mView);
            Objects.requireNonNull(autofillId);
            Api29Impl.e(contentCaptureSessionA, autofillId.toAutofillId(), jArr);
            return;
        }
        if (i2 >= 29) {
            ViewStructure viewStructureB = Api29Impl.b(a.a(this.mWrappedObj), this.mView);
            Api23Impl.a(viewStructureB).putBoolean(KEY_VIEW_TREE_APPEARING, true);
            Api29Impl.d(a.a(this.mWrappedObj), viewStructureB);
            ContentCaptureSession contentCaptureSessionA2 = a.a(this.mWrappedObj);
            AutofillIdCompat autofillId2 = ViewCompat.getAutofillId(this.mView);
            Objects.requireNonNull(autofillId2);
            Api29Impl.e(contentCaptureSessionA2, autofillId2.toAutofillId(), jArr);
            ViewStructure viewStructureB2 = Api29Impl.b(a.a(this.mWrappedObj), this.mView);
            Api23Impl.a(viewStructureB2).putBoolean(KEY_VIEW_TREE_APPEARED, true);
            Api29Impl.d(a.a(this.mWrappedObj), viewStructureB2);
        }
    }

    @NonNull
    @RequiresApi(29)
    public ContentCaptureSession toContentCaptureSession() {
        return a.a(this.mWrappedObj);
    }
}
