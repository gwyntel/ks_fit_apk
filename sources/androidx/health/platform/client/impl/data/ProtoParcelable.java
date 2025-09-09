package androidx.health.platform.client.impl.data;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.exifinterface.media.ExifInterface;
import androidx.health.platform.client.proto.MessageLite;
import com.taobao.accs.common.Constants;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b'\u0018\u0000 \u0015*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\rH\u0016R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u0016"}, d2 = {"Landroidx/health/platform/client/impl/data/ProtoParcelable;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/health/platform/client/proto/MessageLite;", "Landroidx/health/platform/client/impl/data/ProtoData;", "Landroid/os/Parcelable;", "()V", "bytes", "", "getBytes", "()[B", "bytes$delegate", "Lkotlin/Lazy;", "describeContents", "", "shouldStoreInPlace", "", "writeToParcel", "", "dest", "Landroid/os/Parcel;", Constants.KEY_FLAGS, "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public abstract class ProtoParcelable<T extends MessageLite> extends ProtoData<T> implements Parcelable {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    /* renamed from: bytes$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy bytes = LazyKt.lazy(new Function0<byte[]>(this) { // from class: androidx.health.platform.client.impl.data.ProtoParcelable$bytes$2
        final /* synthetic */ ProtoParcelable<T> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        @Override // kotlin.jvm.functions.Function0
        public final byte[] invoke() {
            return this.this$0.getProto().toByteArray();
        }
    });

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J=\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u000e\b\u0001\u0010\u0005\u0018\u0001*\u0006\u0012\u0002\b\u00030\u00062\u0014\b\u0004\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u0002H\u00050\bH\u0080\bø\u0001\u0000¢\u0006\u0002\b\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000b"}, d2 = {"Landroidx/health/platform/client/impl/data/ProtoParcelable$Companion;", "", "()V", "newCreator", "Landroid/os/Parcelable$Creator;", "U", "Landroidx/health/platform/client/impl/data/ProtoParcelable;", "parser", "Lkotlin/Function1;", "", "newCreator$connect_client_release", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public final /* synthetic */ <U extends ProtoParcelable<?>> Parcelable.Creator<U> newCreator$connect_client_release(final Function1<? super byte[], ? extends U> parser) {
            Intrinsics.checkNotNullParameter(parser, "parser");
            Intrinsics.needClassReification();
            return (Parcelable.Creator<U>) new Parcelable.Creator<U>() { // from class: androidx.health.platform.client.impl.data.ProtoParcelable$Companion$newCreator$1
                /* JADX WARN: Incorrect return type in method signature: (Landroid/os/Parcel;)TU; */
                @Override // android.os.Parcelable.Creator
                @SuppressLint({"NewApi"})
                @Nullable
                public ProtoParcelable createFromParcel(@NotNull Parcel source) {
                    Intrinsics.checkNotNullParameter(source, "source");
                    int i2 = source.readInt();
                    if (i2 == 0) {
                        byte[] bArrCreateByteArray = source.createByteArray();
                        if (bArrCreateByteArray == null) {
                            return null;
                        }
                        return (ProtoParcelable) parser.invoke(bArrCreateByteArray);
                    }
                    if (i2 == 1) {
                        SharedMemory27Impl sharedMemory27Impl = SharedMemory27Impl.INSTANCE;
                        Intrinsics.needClassReification();
                        final Function1 function1 = parser;
                        return (ProtoParcelable) sharedMemory27Impl.parseParcelUsingSharedMemory(source, new Function1<byte[], U>() { // from class: androidx.health.platform.client.impl.data.ProtoParcelable$Companion$newCreator$1$createFromParcel$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(1);
                            }

                            /* JADX WARN: Incorrect return type in method signature: ([B)TU; */
                            @Override // kotlin.jvm.functions.Function1
                            @NotNull
                            public final ProtoParcelable invoke(@NotNull byte[] it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return (ProtoParcelable) function1.invoke(it);
                            }
                        });
                    }
                    throw new IllegalArgumentException("Unknown storage: " + i2);
                }

                /* JADX WARN: Incorrect return type in method signature: (I)[TU; */
                @Override // android.os.Parcelable.Creator
                @NotNull
                public ProtoParcelable[] newArray(int size) {
                    Intrinsics.reifiedOperationMarker(0, "U?");
                    return new ProtoParcelable[size];
                }
            };
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final byte[] getBytes() {
        Object value = this.bytes.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-bytes>(...)");
        return (byte[]) value;
    }

    private final boolean shouldStoreInPlace() {
        return getBytes().length <= 16384;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return !shouldStoreInPlace() ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        if (shouldStoreInPlace()) {
            dest.writeInt(0);
            dest.writeByteArray(getBytes());
        } else {
            dest.writeInt(1);
            SharedMemory27Impl.INSTANCE.writeToParcelUsingSharedMemory("ProtoParcelable", getBytes(), dest, flags);
        }
    }
}
