package androidx.media3.container;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.media3.common.Format;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Metadata;
import androidx.media3.common.o;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import com.google.common.primitives.Floats;

@UnstableApi
/* loaded from: classes.dex */
public final class Mp4LocationData implements Metadata.Entry {
    public static final Parcelable.Creator<Mp4LocationData> CREATOR = new Parcelable.Creator<Mp4LocationData>() { // from class: androidx.media3.container.Mp4LocationData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Mp4LocationData createFromParcel(Parcel parcel) {
            return new Mp4LocationData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Mp4LocationData[] newArray(int i2) {
            return new Mp4LocationData[i2];
        }
    };
    public final float latitude;
    public final float longitude;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Mp4LocationData.class != obj.getClass()) {
            return false;
        }
        Mp4LocationData mp4LocationData = (Mp4LocationData) obj;
        return this.latitude == mp4LocationData.latitude && this.longitude == mp4LocationData.longitude;
    }

    @Override // androidx.media3.common.Metadata.Entry
    public /* synthetic */ byte[] getWrappedMetadataBytes() {
        return o.a(this);
    }

    @Override // androidx.media3.common.Metadata.Entry
    public /* synthetic */ Format getWrappedMetadataFormat() {
        return o.b(this);
    }

    public int hashCode() {
        return ((527 + Floats.hashCode(this.latitude)) * 31) + Floats.hashCode(this.longitude);
    }

    @Override // androidx.media3.common.Metadata.Entry
    public /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        o.c(this, builder);
    }

    public String toString() {
        return "xyz: latitude=" + this.latitude + ", longitude=" + this.longitude;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeFloat(this.latitude);
        parcel.writeFloat(this.longitude);
    }

    public Mp4LocationData(@FloatRange(from = -90.0d, to = 90.0d) float f2, @FloatRange(from = -180.0d, to = 180.0d) float f3) {
        Assertions.checkArgument(f2 >= -90.0f && f2 <= 90.0f && f3 >= -180.0f && f3 <= 180.0f, "Invalid latitude or longitude");
        this.latitude = f2;
        this.longitude = f3;
    }

    private Mp4LocationData(Parcel parcel) {
        this.latitude = parcel.readFloat();
        this.longitude = parcel.readFloat();
    }
}
