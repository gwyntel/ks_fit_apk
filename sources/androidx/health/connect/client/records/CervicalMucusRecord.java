package androidx.health.connect.client.records;

import androidx.annotation.RestrictTo;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0005\u001d\u001e\u001f !B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0096\u0002J\b\u0010\u001c\u001a\u00020\u0007H\u0016R\u0017\u0010\u0006\u001a\u00020\u0007¢\u0006\u000e\n\u0000\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\b\u001a\u00020\u0007¢\u0006\u000e\n\u0000\u0012\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\""}, d2 = {"Landroidx/health/connect/client/records/CervicalMucusRecord;", "Landroidx/health/connect/client/records/InstantaneousRecord;", "time", "Ljava/time/Instant;", "zoneOffset", "Ljava/time/ZoneOffset;", "appearance", "", "sensation", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;IILandroidx/health/connect/client/records/metadata/Metadata;)V", "getAppearance$annotations", "()V", "getAppearance", "()I", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getSensation$annotations", "getSensation", "getTime", "()Ljava/time/Instant;", "getZoneOffset", "()Ljava/time/ZoneOffset;", "equals", "", "other", "", "hashCode", "Appearance", "Appearances", "Companion", "Sensation", "Sensations", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CervicalMucusRecord implements InstantaneousRecord {
    public static final int APPEARANCE_CREAMY = 3;
    public static final int APPEARANCE_DRY = 1;
    public static final int APPEARANCE_EGG_WHITE = 5;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<Integer, String> APPEARANCE_INT_TO_STRING_MAP;
    public static final int APPEARANCE_STICKY = 2;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<String, Integer> APPEARANCE_STRING_TO_INT_MAP;
    public static final int APPEARANCE_UNKNOWN = 0;
    public static final int APPEARANCE_UNUSUAL = 6;
    public static final int APPEARANCE_WATERY = 4;
    public static final int SENSATION_HEAVY = 3;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<Integer, String> SENSATION_INT_TO_STRING_MAP;
    public static final int SENSATION_LIGHT = 1;
    public static final int SENSATION_MEDIUM = 2;

    @JvmField
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NotNull
    public static final Map<String, Integer> SENSATION_STRING_TO_INT_MAP;
    public static final int SENSATION_UNKNOWN = 0;
    private final int appearance;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;
    private final int sensation;

    @NotNull
    private final Instant time;

    @Nullable
    private final ZoneOffset zoneOffset;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/records/CervicalMucusRecord$Appearance;", "", "()V", "CLEAR", "", "CREAMY", "DRY", "STICKY", "UNUSUAL", "WATERY", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Appearance {

        @NotNull
        public static final String CLEAR = "clear";

        @NotNull
        public static final String CREAMY = "creamy";

        @NotNull
        public static final String DRY = "dry";

        @NotNull
        public static final Appearance INSTANCE = new Appearance();

        @NotNull
        public static final String STICKY = "sticky";

        @NotNull
        public static final String UNUSUAL = "unusual";

        @NotNull
        public static final String WATERY = "watery";

        private Appearance() {
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/CervicalMucusRecord$Appearances;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface Appearances {
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Landroidx/health/connect/client/records/CervicalMucusRecord$Sensation;", "", "()V", "HEAVY", "", "LIGHT", "MEDIUM", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Sensation {

        @NotNull
        public static final String HEAVY = "heavy";

        @NotNull
        public static final Sensation INSTANCE = new Sensation();

        @NotNull
        public static final String LIGHT = "light";

        @NotNull
        public static final String MEDIUM = "medium";

        private Sensation() {
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/CervicalMucusRecord$Sensations;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface Sensations {
    }

    static {
        Map<String, Integer> mapMapOf = MapsKt.mapOf(TuplesKt.to(Appearance.CLEAR, 5), TuplesKt.to(Appearance.CREAMY, 3), TuplesKt.to(Appearance.DRY, 1), TuplesKt.to(Appearance.STICKY, 2), TuplesKt.to(Appearance.WATERY, 4), TuplesKt.to(Appearance.UNUSUAL, 6));
        APPEARANCE_STRING_TO_INT_MAP = mapMapOf;
        APPEARANCE_INT_TO_STRING_MAP = UtilsKt.reverse(mapMapOf);
        Map<String, Integer> mapMapOf2 = MapsKt.mapOf(TuplesKt.to(Sensation.LIGHT, 1), TuplesKt.to("medium", 2), TuplesKt.to(Sensation.HEAVY, 3));
        SENSATION_STRING_TO_INT_MAP = mapMapOf2;
        SENSATION_INT_TO_STRING_MAP = UtilsKt.reverse(mapMapOf2);
    }

    public CervicalMucusRecord(@NotNull Instant time, @Nullable ZoneOffset zoneOffset, int i2, int i3, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(time, "time");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.time = time;
        this.zoneOffset = zoneOffset;
        this.appearance = i2;
        this.sensation = i3;
        this.metadata = metadata;
    }

    public static /* synthetic */ void getAppearance$annotations() {
    }

    public static /* synthetic */ void getSensation$annotations() {
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(CervicalMucusRecord.class, other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type androidx.health.connect.client.records.CervicalMucusRecord");
        CervicalMucusRecord cervicalMucusRecord = (CervicalMucusRecord) other;
        return Intrinsics.areEqual(getTime(), cervicalMucusRecord.getTime()) && Intrinsics.areEqual(getZoneOffset(), cervicalMucusRecord.getZoneOffset()) && this.appearance == cervicalMucusRecord.appearance && this.sensation == cervicalMucusRecord.sensation && Intrinsics.areEqual(getMetadata(), cervicalMucusRecord.getMetadata());
    }

    public final int getAppearance() {
        return this.appearance;
    }

    @Override // androidx.health.connect.client.records.Record
    @NotNull
    public androidx.health.connect.client.records.metadata.Metadata getMetadata() {
        return this.metadata;
    }

    public final int getSensation() {
        return this.sensation;
    }

    @Override // androidx.health.connect.client.records.InstantaneousRecord
    @NotNull
    public Instant getTime() {
        return this.time;
    }

    @Override // androidx.health.connect.client.records.InstantaneousRecord
    @Nullable
    public ZoneOffset getZoneOffset() {
        return this.zoneOffset;
    }

    public int hashCode() {
        int iHashCode = getTime().hashCode() * 31;
        ZoneOffset zoneOffset = getZoneOffset();
        return ((((((iHashCode + (zoneOffset != null ? zoneOffset.hashCode() : 0)) * 31) + this.appearance) * 31) + this.sensation) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ CervicalMucusRecord(Instant instant, ZoneOffset zoneOffset, int i2, int i3, androidx.health.connect.client.records.metadata.Metadata metadata, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, (i4 & 4) != 0 ? 0 : i2, (i4 & 8) != 0 ? 0 : i3, (i4 & 16) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
