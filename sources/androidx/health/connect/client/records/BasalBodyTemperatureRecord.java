package androidx.health.connect.client.records;

import androidx.health.connect.client.units.Temperature;
import androidx.health.connect.client.units.TemperatureKt;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.time.Instant;
import java.time.ZoneOffset;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0096\u0002J\b\u0010\u001d\u001a\u00020\tH\u0016R\u0017\u0010\b\u001a\u00020\t¢\u0006\u000e\n\u0000\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001f"}, d2 = {"Landroidx/health/connect/client/records/BasalBodyTemperatureRecord;", "Landroidx/health/connect/client/records/InstantaneousRecord;", "time", "Ljava/time/Instant;", "zoneOffset", "Ljava/time/ZoneOffset;", "temperature", "Landroidx/health/connect/client/units/Temperature;", "measurementLocation", "", TtmlNode.TAG_METADATA, "Landroidx/health/connect/client/records/metadata/Metadata;", "(Ljava/time/Instant;Ljava/time/ZoneOffset;Landroidx/health/connect/client/units/Temperature;ILandroidx/health/connect/client/records/metadata/Metadata;)V", "getMeasurementLocation$annotations", "()V", "getMeasurementLocation", "()I", "getMetadata", "()Landroidx/health/connect/client/records/metadata/Metadata;", "getTemperature", "()Landroidx/health/connect/client/units/Temperature;", "getTime", "()Ljava/time/Instant;", "getZoneOffset", "()Ljava/time/ZoneOffset;", "equals", "", "other", "", "hashCode", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BasalBodyTemperatureRecord implements InstantaneousRecord {
    private final int measurementLocation;

    @NotNull
    private final androidx.health.connect.client.records.metadata.Metadata metadata;

    @NotNull
    private final Temperature temperature;

    @NotNull
    private final Instant time;

    @Nullable
    private final ZoneOffset zoneOffset;

    @NotNull
    private static final Companion Companion = new Companion(null);

    @NotNull
    private static final Temperature MIN_TEMPERATURE = TemperatureKt.getCelsius(0.0d);

    @NotNull
    private static final Temperature MAX_TEMPERATURE = TemperatureKt.getCelsius(4.94E-322d);

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Landroidx/health/connect/client/records/BasalBodyTemperatureRecord$Companion;", "", "()V", "MAX_TEMPERATURE", "Landroidx/health/connect/client/units/Temperature;", "MIN_TEMPERATURE", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BasalBodyTemperatureRecord(@NotNull Instant time, @Nullable ZoneOffset zoneOffset, @NotNull Temperature temperature, int i2, @NotNull androidx.health.connect.client.records.metadata.Metadata metadata) {
        Intrinsics.checkNotNullParameter(time, "time");
        Intrinsics.checkNotNullParameter(temperature, "temperature");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.time = time;
        this.zoneOffset = zoneOffset;
        this.temperature = temperature;
        this.measurementLocation = i2;
        this.metadata = metadata;
        UtilsKt.requireNotLess(temperature, MIN_TEMPERATURE, "temperature");
        UtilsKt.requireNotMore(temperature, MAX_TEMPERATURE, "temperature");
    }

    public static /* synthetic */ void getMeasurementLocation$annotations() {
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BasalBodyTemperatureRecord)) {
            return false;
        }
        BasalBodyTemperatureRecord basalBodyTemperatureRecord = (BasalBodyTemperatureRecord) other;
        return Intrinsics.areEqual(this.temperature, basalBodyTemperatureRecord.temperature) && this.measurementLocation == basalBodyTemperatureRecord.measurementLocation && Intrinsics.areEqual(getTime(), basalBodyTemperatureRecord.getTime()) && Intrinsics.areEqual(getZoneOffset(), basalBodyTemperatureRecord.getZoneOffset()) && Intrinsics.areEqual(getMetadata(), basalBodyTemperatureRecord.getMetadata());
    }

    public final int getMeasurementLocation() {
        return this.measurementLocation;
    }

    @Override // androidx.health.connect.client.records.Record
    @NotNull
    public androidx.health.connect.client.records.metadata.Metadata getMetadata() {
        return this.metadata;
    }

    @NotNull
    public final Temperature getTemperature() {
        return this.temperature;
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
        int iHashCode = ((((this.temperature.hashCode() * 31) + this.measurementLocation) * 31) + getTime().hashCode()) * 31;
        ZoneOffset zoneOffset = getZoneOffset();
        return ((iHashCode + (zoneOffset != null ? zoneOffset.hashCode() : 0)) * 31) + getMetadata().hashCode();
    }

    public /* synthetic */ BasalBodyTemperatureRecord(Instant instant, ZoneOffset zoneOffset, Temperature temperature, int i2, androidx.health.connect.client.records.metadata.Metadata metadata, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(instant, zoneOffset, temperature, (i3 & 8) != 0 ? 0 : i2, (i3 & 16) != 0 ? androidx.health.connect.client.records.metadata.Metadata.EMPTY : metadata);
    }
}
