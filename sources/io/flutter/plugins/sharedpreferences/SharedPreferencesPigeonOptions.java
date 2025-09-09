package io.flutter.plugins.sharedpreferences;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000fJ\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lio/flutter/plugins/sharedpreferences/SharedPreferencesPigeonOptions;", "", "fileKey", "", "(Ljava/lang/String;)V", "getFileKey", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toList", "", "toString", "Companion", "shared_preferences_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class SharedPreferencesPigeonOptions {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final String fileKey;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006¨\u0006\u0007"}, d2 = {"Lio/flutter/plugins/sharedpreferences/SharedPreferencesPigeonOptions$Companion;", "", "()V", "fromList", "Lio/flutter/plugins/sharedpreferences/SharedPreferencesPigeonOptions;", AlinkConstants.KEY_LIST, "", "shared_preferences_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final SharedPreferencesPigeonOptions fromList(@NotNull List<? extends Object> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            return new SharedPreferencesPigeonOptions((String) list.get(0));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SharedPreferencesPigeonOptions() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ SharedPreferencesPigeonOptions copy$default(SharedPreferencesPigeonOptions sharedPreferencesPigeonOptions, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = sharedPreferencesPigeonOptions.fileKey;
        }
        return sharedPreferencesPigeonOptions.copy(str);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getFileKey() {
        return this.fileKey;
    }

    @NotNull
    public final SharedPreferencesPigeonOptions copy(@Nullable String fileKey) {
        return new SharedPreferencesPigeonOptions(fileKey);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof SharedPreferencesPigeonOptions) && Intrinsics.areEqual(this.fileKey, ((SharedPreferencesPigeonOptions) other).fileKey);
    }

    @Nullable
    public final String getFileKey() {
        return this.fileKey;
    }

    public int hashCode() {
        String str = this.fileKey;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    @NotNull
    public final List<Object> toList() {
        return CollectionsKt.listOf(this.fileKey);
    }

    @NotNull
    public String toString() {
        return "SharedPreferencesPigeonOptions(fileKey=" + this.fileKey + ")";
    }

    public SharedPreferencesPigeonOptions(@Nullable String str) {
        this.fileKey = str;
    }

    public /* synthetic */ SharedPreferencesPigeonOptions(String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : str);
    }
}
