package com.fluttercandies.flutter_image_compress.exif;

import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0006¨\u0006\u000b"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/exif/Exif;", "", "()V", "getFromExifInterface", "", "byteArray", "", "getRotationDegrees", "file", "Ljava/io/File;", "_bytes", "flutter_image_compress_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class Exif {

    @NotNull
    public static final Exif INSTANCE = new Exif();

    private Exif() {
    }

    private final int getFromExifInterface(byte[] byteArray) {
        return new ExifInterface(new ByteArrayInputStream(byteArray)).getRotationDegrees();
    }

    public final int getRotationDegrees(@NotNull byte[] _bytes) {
        Intrinsics.checkNotNullParameter(_bytes, "_bytes");
        try {
            return getFromExifInterface(_bytes);
        } catch (Exception unused) {
            return 0;
        }
    }

    public final int getRotationDegrees(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            return new ExifInterface(file.getAbsolutePath()).getRotationDegrees();
        } catch (Exception unused) {
            return 0;
        }
    }
}
