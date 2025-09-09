package androidx.core.database.sqlite;

import android.database.sqlite.SQLiteCursor;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public final class SQLiteCursorCompat {

    @RequiresApi(28)
    static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static void a(SQLiteCursor sQLiteCursor, boolean z2) {
            sQLiteCursor.setFillWindowForwardOnly(z2);
        }
    }

    private SQLiteCursorCompat() {
    }

    public static void setFillWindowForwardOnly(@NonNull SQLiteCursor sQLiteCursor, boolean z2) {
        if (Build.VERSION.SDK_INT >= 28) {
            Api28Impl.a(sQLiteCursor, z2);
        }
    }
}
