package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alipay.sdk.m.u.i;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ShowFirstParty
@KeepForSdk
/* loaded from: classes3.dex */
public abstract class FastJsonResponse {

    @ShowFirstParty
    public interface FieldConverter<I, O> {
        int zaa();

        int zab();

        @Nullable
        Object zac(@NonNull Object obj);

        @NonNull
        Object zad(@NonNull Object obj);
    }

    protected static final Object j(Field field, Object obj) {
        return field.zak != null ? field.zaf(obj) : obj;
    }

    private final void zaE(Field field, @Nullable Object obj) {
        int i2 = field.f12882c;
        Object objZae = field.zae(obj);
        String str = field.f12884e;
        switch (i2) {
            case 0:
                if (objZae != null) {
                    e(field, str, ((Integer) objZae).intValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 1:
                m(field, str, (BigInteger) objZae);
                return;
            case 2:
                if (objZae != null) {
                    f(field, str, ((Long) objZae).longValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 3:
            default:
                throw new IllegalStateException("Unsupported type for conversion: " + i2);
            case 4:
                if (objZae != null) {
                    p(field, str, ((Double) objZae).doubleValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 5:
                k(field, str, (BigDecimal) objZae);
                return;
            case 6:
                if (objZae != null) {
                    c(field, str, ((Boolean) objZae).booleanValue());
                    return;
                } else {
                    zaG(str);
                    return;
                }
            case 7:
                g(field, str, (String) objZae);
                return;
            case 8:
            case 9:
                if (objZae != null) {
                    d(field, str, (byte[]) objZae);
                    return;
                } else {
                    zaG(str);
                    return;
                }
        }
    }

    private static final void zaF(StringBuilder sb, Field field, Object obj) {
        int i2 = field.f12880a;
        if (i2 == 11) {
            Class cls = field.f12886g;
            Preconditions.checkNotNull(cls);
            sb.append(((FastJsonResponse) cls.cast(obj)).toString());
        } else {
            if (i2 != 7) {
                sb.append(obj);
                return;
            }
            sb.append("\"");
            sb.append(JsonUtils.escapeString((String) obj));
            sb.append("\"");
        }
    }

    private static final void zaG(String str) {
        if (Log.isLoggable("FastJsonResponse", 6)) {
            Log.e("FastJsonResponse", "Output field (" + str + ") has a null value, but expected a primitive");
        }
    }

    protected Object a(Field field) {
        String str = field.f12884e;
        if (field.f12886g == null) {
            return getValueObject(str);
        }
        Preconditions.checkState(getValueObject(str) == null, "Concrete field shouldn't be value object: %s", field.f12884e);
        try {
            return getClass().getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET + Character.toUpperCase(str.charAt(0)) + str.substring(1), null).invoke(this, null);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@NonNull Field field, @NonNull String str, @Nullable ArrayList<T> arrayList) {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeInternal(@NonNull Field field, @NonNull String str, @NonNull T t2) {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    protected boolean b(Field field) {
        if (field.f12882c != 11) {
            return isPrimitiveFieldSet(field.f12884e);
        }
        if (field.f12883d) {
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected void c(Field field, String str, boolean z2) {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    protected void d(Field field, String str, byte[] bArr) {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    protected void e(Field field, String str, int i2) {
        throw new UnsupportedOperationException("Integer not supported");
    }

    protected void f(Field field, String str, long j2) {
        throw new UnsupportedOperationException("Long not supported");
    }

    protected void g(Field field, String str, String str2) {
        throw new UnsupportedOperationException("String not supported");
    }

    @NonNull
    @KeepForSdk
    public abstract Map<String, Field<?, ?>> getFieldMappings();

    protected abstract Object getValueObject(String str);

    protected void h(Field field, String str, Map map) {
        throw new UnsupportedOperationException("String map not supported");
    }

    protected void i(Field field, String str, ArrayList arrayList) {
        throw new UnsupportedOperationException("String list not supported");
    }

    protected abstract boolean isPrimitiveFieldSet(String str);

    protected void k(Field field, String str, BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    protected void l(Field field, String str, ArrayList arrayList) {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    protected void m(Field field, String str, BigInteger bigInteger) {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    protected void n(Field field, String str, ArrayList arrayList) {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    protected void o(Field field, String str, ArrayList arrayList) {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    protected void p(Field field, String str, double d2) {
        throw new UnsupportedOperationException("Double not supported");
    }

    protected void q(Field field, String str, ArrayList arrayList) {
        throw new UnsupportedOperationException("Double list not supported");
    }

    protected void r(Field field, String str, float f2) {
        throw new UnsupportedOperationException("Float not supported");
    }

    protected void s(Field field, String str, ArrayList arrayList) {
        throw new UnsupportedOperationException("Float list not supported");
    }

    protected void t(Field field, String str, ArrayList arrayList) {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    @NonNull
    @KeepForSdk
    public String toString() {
        Map<String, Field<?, ?>> fieldMappings = getFieldMappings();
        StringBuilder sb = new StringBuilder(100);
        for (String str : fieldMappings.keySet()) {
            Field<?, ?> field = fieldMappings.get(str);
            if (b(field)) {
                Object objJ = j(field, a(field));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"");
                sb.append(str);
                sb.append("\":");
                if (objJ != null) {
                    switch (field.f12882c) {
                        case 8:
                            sb.append("\"");
                            sb.append(Base64Utils.encode((byte[]) objJ));
                            sb.append("\"");
                            break;
                        case 9:
                            sb.append("\"");
                            sb.append(Base64Utils.encodeUrlSafe((byte[]) objJ));
                            sb.append("\"");
                            break;
                        case 10:
                            MapUtils.writeStringMapToJson(sb, (HashMap) objJ);
                            break;
                        default:
                            if (field.f12881b) {
                                ArrayList arrayList = (ArrayList) objJ;
                                sb.append("[");
                                int size = arrayList.size();
                                for (int i2 = 0; i2 < size; i2++) {
                                    if (i2 > 0) {
                                        sb.append(",");
                                    }
                                    Object obj = arrayList.get(i2);
                                    if (obj != null) {
                                        zaF(sb, field, obj);
                                    }
                                }
                                sb.append("]");
                                break;
                            } else {
                                zaF(sb, field, objJ);
                                break;
                            }
                    }
                } else {
                    sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
                }
            }
        }
        if (sb.length() > 0) {
            sb.append(i.f9804d);
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }

    protected void u(Field field, String str, ArrayList arrayList) {
        throw new UnsupportedOperationException("Long list not supported");
    }

    public final void zaA(@NonNull Field field, @Nullable String str) {
        if (field.zak != null) {
            zaE(field, str);
        } else {
            g(field, field.f12884e, str);
        }
    }

    public final void zaB(@NonNull Field field, @Nullable Map map) {
        if (field.zak != null) {
            zaE(field, map);
        } else {
            h(field, field.f12884e, map);
        }
    }

    public final void zaC(@NonNull Field field, @Nullable ArrayList arrayList) {
        if (field.zak != null) {
            zaE(field, arrayList);
        } else {
            i(field, field.f12884e, arrayList);
        }
    }

    public final void zaa(@NonNull Field field, @Nullable BigDecimal bigDecimal) {
        if (field.zak != null) {
            zaE(field, bigDecimal);
        } else {
            k(field, field.f12884e, bigDecimal);
        }
    }

    public final void zac(@NonNull Field field, @Nullable ArrayList arrayList) {
        if (field.zak != null) {
            zaE(field, arrayList);
        } else {
            l(field, field.f12884e, arrayList);
        }
    }

    public final void zae(@NonNull Field field, @Nullable BigInteger bigInteger) {
        if (field.zak != null) {
            zaE(field, bigInteger);
        } else {
            m(field, field.f12884e, bigInteger);
        }
    }

    public final void zag(@NonNull Field field, @Nullable ArrayList arrayList) {
        if (field.zak != null) {
            zaE(field, arrayList);
        } else {
            n(field, field.f12884e, arrayList);
        }
    }

    public final void zai(@NonNull Field field, boolean z2) {
        if (field.zak != null) {
            zaE(field, Boolean.valueOf(z2));
        } else {
            c(field, field.f12884e, z2);
        }
    }

    public final void zaj(@NonNull Field field, @Nullable ArrayList arrayList) {
        if (field.zak != null) {
            zaE(field, arrayList);
        } else {
            o(field, field.f12884e, arrayList);
        }
    }

    public final void zal(@NonNull Field field, @Nullable byte[] bArr) {
        if (field.zak != null) {
            zaE(field, bArr);
        } else {
            d(field, field.f12884e, bArr);
        }
    }

    public final void zam(@NonNull Field field, double d2) {
        if (field.zak != null) {
            zaE(field, Double.valueOf(d2));
        } else {
            p(field, field.f12884e, d2);
        }
    }

    public final void zao(@NonNull Field field, @Nullable ArrayList arrayList) {
        if (field.zak != null) {
            zaE(field, arrayList);
        } else {
            q(field, field.f12884e, arrayList);
        }
    }

    public final void zaq(@NonNull Field field, float f2) {
        if (field.zak != null) {
            zaE(field, Float.valueOf(f2));
        } else {
            r(field, field.f12884e, f2);
        }
    }

    public final void zas(@NonNull Field field, @Nullable ArrayList arrayList) {
        if (field.zak != null) {
            zaE(field, arrayList);
        } else {
            s(field, field.f12884e, arrayList);
        }
    }

    public final void zau(@NonNull Field field, int i2) {
        if (field.zak != null) {
            zaE(field, Integer.valueOf(i2));
        } else {
            e(field, field.f12884e, i2);
        }
    }

    public final void zav(@NonNull Field field, @Nullable ArrayList arrayList) {
        if (field.zak != null) {
            zaE(field, arrayList);
        } else {
            t(field, field.f12884e, arrayList);
        }
    }

    public final void zax(@NonNull Field field, long j2) {
        if (field.zak != null) {
            zaE(field, Long.valueOf(j2));
        } else {
            f(field, field.f12884e, j2);
        }
    }

    public final void zay(@NonNull Field field, @Nullable ArrayList arrayList) {
        if (field.zak != null) {
            zaE(field, arrayList);
        } else {
            u(field, field.f12884e, arrayList);
        }
    }

    @SafeParcelable.Class(creator = "FieldCreator")
    @ShowFirstParty
    @KeepForSdk
    public static class Field<I, O> extends AbstractSafeParcelable {
        public static final zaj CREATOR = new zaj();

        /* renamed from: a, reason: collision with root package name */
        protected final int f12880a;

        /* renamed from: b, reason: collision with root package name */
        protected final boolean f12881b;

        /* renamed from: c, reason: collision with root package name */
        protected final int f12882c;

        /* renamed from: d, reason: collision with root package name */
        protected final boolean f12883d;

        /* renamed from: e, reason: collision with root package name */
        protected final String f12884e;

        /* renamed from: f, reason: collision with root package name */
        protected final int f12885f;

        /* renamed from: g, reason: collision with root package name */
        protected final Class f12886g;

        /* renamed from: h, reason: collision with root package name */
        protected final String f12887h;

        @SafeParcelable.VersionField(getter = "getVersionCode", id = 1)
        private final int zai;
        private zan zaj;

        @Nullable
        @SafeParcelable.Field(getter = "getWrappedConverter", id = 9, type = "com.google.android.gms.common.server.converter.ConverterWrapper")
        private final FieldConverter zak;

        Field(int i2, int i3, boolean z2, int i4, boolean z3, String str, int i5, String str2, com.google.android.gms.common.server.converter.zaa zaaVar) {
            this.zai = i2;
            this.f12880a = i3;
            this.f12881b = z2;
            this.f12882c = i4;
            this.f12883d = z3;
            this.f12884e = str;
            this.f12885f = i5;
            if (str2 == null) {
                this.f12886g = null;
                this.f12887h = null;
            } else {
                this.f12886g = SafeParcelResponse.class;
                this.f12887h = str2;
            }
            if (zaaVar == null) {
                this.zak = null;
            } else {
                this.zak = zaaVar.zab();
            }
        }

        @NonNull
        @KeepForSdk
        public static Field<byte[], byte[]> forBase64(@NonNull String str, int i2) {
            return new Field<>(8, false, 8, false, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Boolean, Boolean> forBoolean(@NonNull String str, int i2) {
            return new Field<>(6, false, 6, false, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(@NonNull String str, int i2, @NonNull Class<T> cls) {
            return new Field<>(11, false, 11, false, str, i2, cls, null);
        }

        @NonNull
        @KeepForSdk
        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(@NonNull String str, int i2, @NonNull Class<T> cls) {
            return new Field<>(11, true, 11, true, str, i2, cls, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Double, Double> forDouble(@NonNull String str, int i2) {
            return new Field<>(4, false, 4, false, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Float, Float> forFloat(@NonNull String str, int i2) {
            return new Field<>(3, false, 3, false, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Integer, Integer> forInteger(@NonNull String str, int i2) {
            return new Field<>(0, false, 0, false, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<Long, Long> forLong(@NonNull String str, int i2) {
            return new Field<>(2, false, 2, false, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<String, String> forString(@NonNull String str, int i2) {
            return new Field<>(7, false, 7, false, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(@NonNull String str, int i2) {
            return new Field<>(10, false, 10, false, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field<ArrayList<String>, ArrayList<String>> forStrings(@NonNull String str, int i2) {
            return new Field<>(7, true, 7, true, str, i2, null, null);
        }

        @NonNull
        @KeepForSdk
        public static Field withConverter(@NonNull String str, int i2, @NonNull FieldConverter<?, ?> fieldConverter, boolean z2) {
            fieldConverter.zaa();
            fieldConverter.zab();
            return new Field(7, z2, 0, false, str, i2, null, fieldConverter);
        }

        final com.google.android.gms.common.server.converter.zaa e() {
            FieldConverter fieldConverter = this.zak;
            if (fieldConverter == null) {
                return null;
            }
            return com.google.android.gms.common.server.converter.zaa.zaa(fieldConverter);
        }

        final String g() {
            String str = this.f12887h;
            if (str == null) {
                return null;
            }
            return str;
        }

        @KeepForSdk
        public int getSafeParcelableFieldId() {
            return this.f12885f;
        }

        @NonNull
        public final String toString() {
            Objects.ToStringHelper toStringHelperAdd = Objects.toStringHelper(this).add("versionCode", Integer.valueOf(this.zai)).add("typeIn", Integer.valueOf(this.f12880a)).add("typeInArray", Boolean.valueOf(this.f12881b)).add("typeOut", Integer.valueOf(this.f12882c)).add("typeOutArray", Boolean.valueOf(this.f12883d)).add("outputFieldName", this.f12884e).add("safeParcelFieldId", Integer.valueOf(this.f12885f)).add("concreteTypeName", g());
            Class cls = this.f12886g;
            if (cls != null) {
                toStringHelperAdd.add("concreteType.class", cls.getCanonicalName());
            }
            FieldConverter fieldConverter = this.zak;
            if (fieldConverter != null) {
                toStringHelperAdd.add("converterName", fieldConverter.getClass().getCanonicalName());
            }
            return toStringHelperAdd.toString();
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(@NonNull Parcel parcel, int i2) {
            int i3 = this.zai;
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, i3);
            SafeParcelWriter.writeInt(parcel, 2, this.f12880a);
            SafeParcelWriter.writeBoolean(parcel, 3, this.f12881b);
            SafeParcelWriter.writeInt(parcel, 4, this.f12882c);
            SafeParcelWriter.writeBoolean(parcel, 5, this.f12883d);
            SafeParcelWriter.writeString(parcel, 6, this.f12884e, false);
            SafeParcelWriter.writeInt(parcel, 7, getSafeParcelableFieldId());
            SafeParcelWriter.writeString(parcel, 8, g(), false);
            SafeParcelWriter.writeParcelable(parcel, 9, e(), i2, false);
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }

        @NonNull
        public final Field zab() {
            return new Field(this.zai, this.f12880a, this.f12881b, this.f12882c, this.f12883d, this.f12884e, this.f12885f, this.f12887h, e());
        }

        @NonNull
        public final FastJsonResponse zad() throws IllegalAccessException, InstantiationException {
            Preconditions.checkNotNull(this.f12886g);
            Class cls = this.f12886g;
            if (cls != SafeParcelResponse.class) {
                return (FastJsonResponse) cls.newInstance();
            }
            Preconditions.checkNotNull(this.f12887h);
            Preconditions.checkNotNull(this.zaj, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.zaj, this.f12887h);
        }

        @NonNull
        public final Object zae(@Nullable Object obj) {
            Preconditions.checkNotNull(this.zak);
            return Preconditions.checkNotNull(this.zak.zac(obj));
        }

        @NonNull
        public final Object zaf(@NonNull Object obj) {
            Preconditions.checkNotNull(this.zak);
            return this.zak.zad(obj);
        }

        @NonNull
        public final Map zah() {
            Preconditions.checkNotNull(this.f12887h);
            Preconditions.checkNotNull(this.zaj);
            return (Map) Preconditions.checkNotNull(this.zaj.zab(this.f12887h));
        }

        public final void zai(zan zanVar) {
            this.zaj = zanVar;
        }

        public final boolean zaj() {
            return this.zak != null;
        }

        protected Field(int i2, boolean z2, int i3, boolean z3, String str, int i4, Class cls, FieldConverter fieldConverter) {
            this.zai = 1;
            this.f12880a = i2;
            this.f12881b = z2;
            this.f12882c = i3;
            this.f12883d = z3;
            this.f12884e = str;
            this.f12885f = i4;
            this.f12886g = cls;
            if (cls == null) {
                this.f12887h = null;
            } else {
                this.f12887h = cls.getCanonicalName();
            }
            this.zak = fieldConverter;
        }
    }
}
