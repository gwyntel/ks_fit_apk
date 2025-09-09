package androidx.health.connect.client.datanotification;

import android.content.Intent;
import androidx.annotation.RestrictTo;
import androidx.health.connect.client.impl.converters.datatype.DataTypeConverterKt;
import androidx.health.connect.client.records.Record;
import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.utils.IntentExtKt;
import com.huawei.hms.support.api.entity.core.CommonCode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u001d\b\u0002\u0012\u0014\u0010\u0002\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u0003¢\u0006\u0002\u0010\u0006R\u001f\u0010\u0002\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Landroidx/health/connect/client/datanotification/DataNotification;", "", "dataTypes", "", "Lkotlin/reflect/KClass;", "Landroidx/health/connect/client/records/Record;", "(Ljava/util/Set;)V", "getDataTypes", "()Ljava/util/Set;", "Companion", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public final class DataNotification {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String EXTRA_DATA_TYPES = "com.google.android.healthdata.extra.DATA_TYPES";

    @NotNull
    private final Set<KClass<? extends Record>> dataTypes;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Landroidx/health/connect/client/datanotification/DataNotification$Companion;", "", "()V", "EXTRA_DATA_TYPES", "", "from", "Landroidx/health/connect/client/datanotification/DataNotification;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nDataNotification.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataNotification.kt\nandroidx/health/connect/client/datanotification/DataNotification$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,63:1\n1620#2,3:64\n*S KotlinDebug\n*F\n+ 1 DataNotification.kt\nandroidx/health/connect/client/datanotification/DataNotification$Companion\n*L\n58#1:64,3\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @Nullable
        public final DataNotification from(@NotNull Intent intent) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            List protoMessages = IntentExtKt.getProtoMessages(intent, DataNotification.EXTRA_DATA_TYPES, DataNotification$Companion$from$dataTypes$1.INSTANCE);
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (protoMessages == null) {
                return null;
            }
            HashSet hashSet = new HashSet();
            Iterator it = protoMessages.iterator();
            while (it.hasNext()) {
                hashSet.add(DataTypeConverterKt.toDataTypeKClass((DataProto.DataType) it.next()));
            }
            return new DataNotification(hashSet, defaultConstructorMarker);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ DataNotification(Set set, DefaultConstructorMarker defaultConstructorMarker) {
        this(set);
    }

    @JvmStatic
    @Nullable
    public static final DataNotification from(@NotNull Intent intent) {
        return INSTANCE.from(intent);
    }

    @NotNull
    public final Set<KClass<? extends Record>> getDataTypes() {
        return this.dataTypes;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private DataNotification(Set<? extends KClass<? extends Record>> set) {
        this.dataTypes = set;
    }
}
