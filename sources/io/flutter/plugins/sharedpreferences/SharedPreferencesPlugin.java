package io.flutter.plugins.sharedpreferences;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.core.PreferencesKt;
import androidx.health.connect.client.records.CervicalMucusRecord;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.kingsmith.miot.KsProperty;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002:\u00019B\u000f\b\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0005¢\u0006\u0002\u0010\u0006J \u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001e\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0082@¢\u0006\u0002\u0010\u0013J,\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00160\u00152\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001f\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0019J\u001f\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u001cJ\u001f\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u001fJ&\u0010 \u001a\b\u0012\u0004\u0012\u00020\r0\f2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J*\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00160\u00152\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fH\u0082@¢\u0006\u0002\u0010\"J\u001a\u0010#\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J \u0010$\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001c\u0010%\u001a\u0004\u0018\u00010\u00162\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030&H\u0082@¢\u0006\u0002\u0010'J\u0010\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\n2\u0006\u0010)\u001a\u00020*H\u0016J*\u0010,\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00162\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010-H\u0002J\u001a\u0010.\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030&\u0018\u00010-H\u0082@¢\u0006\u0002\u0010/J \u00100\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00182\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J \u00101\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J \u00102\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u001e2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J \u00103\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J&\u00104\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\r2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u00105\u001a\u00020\n2\u0006\u00106\u001a\u0002072\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0014\u00108\u001a\u0004\u0018\u00010\u00162\b\u0010\u0012\u001a\u0004\u0018\u00010\u0016H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugins/sharedpreferences/SharedPreferencesAsyncApi;", "listEncoder", "Lio/flutter/plugins/sharedpreferences/SharedPreferencesListEncoder;", "(Lio/flutter/plugins/sharedpreferences/SharedPreferencesListEncoder;)V", "()V", com.umeng.analytics.pro.f.X, "Landroid/content/Context;", CervicalMucusRecord.Appearance.CLEAR, "", "allowList", "", "", Constant.METHOD_OPTIONS, "Lio/flutter/plugins/sharedpreferences/SharedPreferencesPigeonOptions;", "dataStoreSetString", KsProperty.Key, "value", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "", "getBool", "", "(Ljava/lang/String;Lio/flutter/plugins/sharedpreferences/SharedPreferencesPigeonOptions;)Ljava/lang/Boolean;", "getDouble", "", "(Ljava/lang/String;Lio/flutter/plugins/sharedpreferences/SharedPreferencesPigeonOptions;)Ljava/lang/Double;", "getInt", "", "(Ljava/lang/String;Lio/flutter/plugins/sharedpreferences/SharedPreferencesPigeonOptions;)Ljava/lang/Long;", "getKeys", "getPrefs", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getString", "getStringList", "getValueByKey", "Landroidx/datastore/preferences/core/Preferences$Key;", "(Landroidx/datastore/preferences/core/Preferences$Key;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onAttachedToEngine", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "preferencesFilter", "", "readAllKeys", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setBool", "setDouble", "setInt", "setString", "setStringList", "setUp", "messenger", "Lio/flutter/plugin/common/BinaryMessenger;", "transformPref", "ListEncoder", "shared_preferences_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSharedPreferencesPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 4 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 5 SafeCollector.common.kt\nkotlinx/coroutines/flow/internal/SafeCollector_commonKt\n*L\n1#1,275:1\n800#2,11:276\n1855#2,2:287\n47#3:289\n49#3:293\n47#3:294\n49#3:298\n50#4:290\n55#4:292\n50#4:295\n55#4:297\n106#5:291\n106#5:296\n*S KotlinDebug\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin\n*L\n187#1:276,11\n204#1:287,2\n217#1:289\n217#1:293\n222#1:294\n222#1:298\n217#1:290\n217#1:292\n222#1:295\n222#1:297\n217#1:291\n222#1:296\n*E\n"})
/* loaded from: classes4.dex */
public final class SharedPreferencesPlugin implements FlutterPlugin, SharedPreferencesAsyncApi {
    private Context context;

    @NotNull
    private SharedPreferencesListEncoder listEncoder;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0016J\u0016\u0010\u0007\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016¨\u0006\t"}, d2 = {"Lio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$ListEncoder;", "Lio/flutter/plugins/sharedpreferences/SharedPreferencesListEncoder;", "()V", "decode", "", "", "listString", "encode", AlinkConstants.KEY_LIST, "shared_preferences_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nSharedPreferencesPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$ListEncoder\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,275:1\n800#2,11:276\n*S KotlinDebug\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$ListEncoder\n*L\n268#1:276,11\n*E\n"})
    public static final class ListEncoder implements SharedPreferencesListEncoder {
        @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesListEncoder
        @NotNull
        public List<String> decode(@NotNull String listString) throws ClassNotFoundException, IOException {
            Intrinsics.checkNotNullParameter(listString, "listString");
            try {
                Object object = new ObjectInputStream(new ByteArrayInputStream(Base64.decode(listString, 0))).readObject();
                Intrinsics.checkNotNull(object, "null cannot be cast to non-null type kotlin.collections.List<*>");
                ArrayList arrayList = new ArrayList();
                for (Object obj : (List) object) {
                    if (obj instanceof String) {
                        arrayList.add(obj);
                    }
                }
                return arrayList;
            } catch (RuntimeException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesListEncoder
        @NotNull
        public String encode(@NotNull List<String> list) throws IOException {
            Intrinsics.checkNotNullParameter(list, "list");
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(list);
                objectOutputStream.flush();
                String strEncodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
                Intrinsics.checkNotNullExpressionValue(strEncodeToString, "encodeToString(...)");
                return strEncodeToString;
            } catch (RuntimeException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Landroidx/datastore/preferences/core/Preferences;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$clear$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {113}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$clear$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Preferences>, Object> {
        final /* synthetic */ List<String> $allowList;
        int label;

        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "preferences", "Landroidx/datastore/preferences/core/MutablePreferences;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$clear$1$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        @SourceDebugExtension({"SMAP\nSharedPreferencesPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$clear$1$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,275:1\n1855#2,2:276\n*S KotlinDebug\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$clear$1$1\n*L\n115#1:276,2\n*E\n"})
        /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$clear$1$1, reason: invalid class name and collision with other inner class name */
        static final class C01971 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
            final /* synthetic */ List<String> $allowList;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01971(List<String> list, Continuation<? super C01971> continuation) {
                super(2, continuation);
                this.$allowList = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C01971 c01971 = new C01971(this.$allowList, continuation);
                c01971.L$0 = obj;
                return c01971;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
            public final Object mo1invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
                return ((C01971) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
                Unit unit;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                List<String> list = this.$allowList;
                if (list != null) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        mutablePreferences.remove(PreferencesKeys.booleanKey((String) it.next()));
                    }
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                if (unit == null) {
                    mutablePreferences.clear();
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(List<String> list, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$allowList = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SharedPreferencesPlugin.this.new AnonymousClass1(this.$allowList, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Context context = SharedPreferencesPlugin.this.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
                    context = null;
                }
                DataStore sharedPreferencesDataStore = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context);
                C01971 c01971 = new C01971(this.$allowList, null);
                this.label = 1;
                obj = PreferencesKt.edit(sharedPreferencesDataStore, c01971, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Preferences> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "preferences", "Landroidx/datastore/preferences/core/MutablePreferences;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$dataStoreSetString$2", f = "SharedPreferencesPlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$dataStoreSetString$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
        final /* synthetic */ Preferences.Key<String> $stringKey;
        final /* synthetic */ String $value;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Preferences.Key<String> key, String str, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$stringKey = key;
            this.$value = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$stringKey, this.$value, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((MutablePreferences) this.L$0).set(this.$stringKey, this.$value);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001*\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getAll$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {129}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getAll$1, reason: invalid class name and case insensitive filesystem */
    static final class C04751 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Map<String, ? extends Object>>, Object> {
        final /* synthetic */ List<String> $allowList;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04751(List<String> list, Continuation<? super C04751> continuation) {
            super(2, continuation);
            this.$allowList = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SharedPreferencesPlugin.this.new C04751(this.$allowList, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                SharedPreferencesPlugin sharedPreferencesPlugin = SharedPreferencesPlugin.this;
                List<String> list = this.$allowList;
                this.label = 1;
                obj = sharedPreferencesPlugin.getPrefs(list, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Map<String, ? extends Object>> continuation) {
            return ((C04751) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {153}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nSharedPreferencesPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getBool$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 4 SafeCollector.common.kt\nkotlinx/coroutines/flow/internal/SafeCollector_commonKt\n*L\n1#1,275:1\n47#2:276\n49#2:280\n50#3:277\n55#3:279\n106#4:278\n*S KotlinDebug\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getBool$1\n*L\n151#1:276\n151#1:280\n151#1:277\n151#1:279\n151#1:278\n*E\n"})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1, reason: invalid class name and case insensitive filesystem */
    static final class C04761 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.ObjectRef<Boolean> $value;
        Object L$0;
        int label;
        final /* synthetic */ SharedPreferencesPlugin this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04761(String str, SharedPreferencesPlugin sharedPreferencesPlugin, Ref.ObjectRef<Boolean> objectRef, Continuation<? super C04761> continuation) {
            super(2, continuation);
            this.$key = str;
            this.this$0 = sharedPreferencesPlugin;
            this.$value = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04761(this.$key, this.this$0, this.$value, continuation);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Ref.ObjectRef<Boolean> objectRef;
            T t2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                final Preferences.Key<Boolean> keyBooleanKey = PreferencesKeys.booleanKey(this.$key);
                Context context = this.this$0.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
                    context = null;
                }
                final Flow data = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context).getData();
                Flow<Boolean> flow = new Flow<Boolean>() { // from class: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1

                    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$lambda-1$$inlined$collect$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 1, mv = {1, 9, 0}, xi = 48)
                    @SourceDebugExtension({"SMAP\nCollect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Collect.kt\nkotlinx/coroutines/flow/FlowKt__CollectKt$collect$3\n+ 2 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 3 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 4 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getBool$1\n*L\n1#1,134:1\n53#2:135\n48#3:136\n151#4:137\n*E\n"})
                    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public static final class AnonymousClass2 implements FlowCollector<Preferences> {
                        final /* synthetic */ Preferences.Key $preferencesKey$inlined;
                        final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

                        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                        @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1$2", f = "SharedPreferencesPlugin.kt", i = {}, l = {136}, m = "emit", n = {}, s = {})
                        /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                        public static final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            @Nullable
                            public final Object invokeSuspend(@NotNull Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, Preferences.Key key) {
                            this.$this_unsafeFlow$inlined = flowCollector;
                            this.$preferencesKey$inlined = key;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        @org.jetbrains.annotations.Nullable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public java.lang.Object emit(androidx.datastore.preferences.core.Preferences r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) throws java.lang.Throwable {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1$2$1 r0 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1$2$1 r0 = new io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L31
                                if (r2 != r3) goto L29
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L47
                            L29:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L31:
                                kotlin.ResultKt.throwOnFailure(r6)
                                kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow$inlined
                                androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                                androidx.datastore.preferences.core.Preferences$Key r2 = r4.$preferencesKey$inlined
                                java.lang.Object r5 = r5.get(r2)
                                r0.label = r3
                                java.lang.Object r5 = r6.emit(r5, r0)
                                if (r5 != r1) goto L47
                                return r1
                            L47:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getBool$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    @Nullable
                    public Object collect(@NotNull FlowCollector<? super Boolean> flowCollector, @NotNull Continuation continuation) {
                        Object objCollect = data.collect(new AnonymousClass2(flowCollector, keyBooleanKey), continuation);
                        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                    }
                };
                Ref.ObjectRef<Boolean> objectRef2 = this.$value;
                this.L$0 = objectRef2;
                this.label = 1;
                Object objFirstOrNull = FlowKt.firstOrNull(flow, this);
                if (objFirstOrNull == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
                t2 = objFirstOrNull;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = (Ref.ObjectRef) this.L$0;
                ResultKt.throwOnFailure(obj);
                t2 = obj;
            }
            objectRef.element = t2;
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04761) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {167}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nSharedPreferencesPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getDouble$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 4 SafeCollector.common.kt\nkotlinx/coroutines/flow/internal/SafeCollector_commonKt\n*L\n1#1,275:1\n47#2:276\n49#2:280\n50#3:277\n55#3:279\n106#4:278\n*S KotlinDebug\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getDouble$1\n*L\n163#1:276\n163#1:280\n163#1:277\n163#1:279\n163#1:278\n*E\n"})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1, reason: invalid class name and case insensitive filesystem */
    static final class C04771 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.ObjectRef<Double> $value;
        Object L$0;
        int label;
        final /* synthetic */ SharedPreferencesPlugin this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04771(String str, SharedPreferencesPlugin sharedPreferencesPlugin, Ref.ObjectRef<Double> objectRef, Continuation<? super C04771> continuation) {
            super(2, continuation);
            this.$key = str;
            this.this$0 = sharedPreferencesPlugin;
            this.$value = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04771(this.$key, this.this$0, this.$value, continuation);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Ref.ObjectRef<Double> objectRef;
            T t2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                final Preferences.Key<String> keyStringKey = PreferencesKeys.stringKey(this.$key);
                Context context = this.this$0.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
                    context = null;
                }
                final Flow data = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context).getData();
                final SharedPreferencesPlugin sharedPreferencesPlugin = this.this$0;
                Flow<Double> flow = new Flow<Double>() { // from class: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1

                    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$lambda-1$$inlined$collect$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 1, mv = {1, 9, 0}, xi = 48)
                    @SourceDebugExtension({"SMAP\nCollect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Collect.kt\nkotlinx/coroutines/flow/FlowKt__CollectKt$collect$3\n+ 2 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 3 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 4 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getDouble$1\n*L\n1#1,134:1\n53#2:135\n48#3:136\n164#4:137\n*E\n"})
                    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public static final class AnonymousClass2 implements FlowCollector<Preferences> {
                        final /* synthetic */ Preferences.Key $preferencesKey$inlined;
                        final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;
                        final /* synthetic */ SharedPreferencesPlugin this$0;

                        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                        @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1$2", f = "SharedPreferencesPlugin.kt", i = {}, l = {136}, m = "emit", n = {}, s = {})
                        /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                        public static final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            @Nullable
                            public final Object invokeSuspend(@NotNull Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, SharedPreferencesPlugin sharedPreferencesPlugin, Preferences.Key key) {
                            this.$this_unsafeFlow$inlined = flowCollector;
                            this.this$0 = sharedPreferencesPlugin;
                            this.$preferencesKey$inlined = key;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        @org.jetbrains.annotations.Nullable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public java.lang.Object emit(androidx.datastore.preferences.core.Preferences r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r7) throws java.lang.Throwable {
                            /*
                                r5 = this;
                                boolean r0 = r7 instanceof io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1$2$1 r0 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1$2$1 r0 = new io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L31
                                if (r2 != r3) goto L29
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L4f
                            L29:
                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                r6.<init>(r7)
                                throw r6
                            L31:
                                kotlin.ResultKt.throwOnFailure(r7)
                                kotlinx.coroutines.flow.FlowCollector r7 = r5.$this_unsafeFlow$inlined
                                androidx.datastore.preferences.core.Preferences r6 = (androidx.datastore.preferences.core.Preferences) r6
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin r2 = r5.this$0
                                androidx.datastore.preferences.core.Preferences$Key r4 = r5.$preferencesKey$inlined
                                java.lang.Object r6 = r6.get(r4)
                                java.lang.Object r6 = io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin.access$transformPref(r2, r6)
                                java.lang.Double r6 = (java.lang.Double) r6
                                r0.label = r3
                                java.lang.Object r6 = r7.emit(r6, r0)
                                if (r6 != r1) goto L4f
                                return r1
                            L4f:
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            */
                            throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getDouble$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    @Nullable
                    public Object collect(@NotNull FlowCollector<? super Double> flowCollector, @NotNull Continuation continuation) {
                        Object objCollect = data.collect(new AnonymousClass2(flowCollector, sharedPreferencesPlugin, keyStringKey), continuation);
                        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                    }
                };
                Ref.ObjectRef<Double> objectRef2 = this.$value;
                this.L$0 = objectRef2;
                this.label = 1;
                Object objFirstOrNull = FlowKt.firstOrNull(flow, this);
                if (objFirstOrNull == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
                t2 = objFirstOrNull;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = (Ref.ObjectRef) this.L$0;
                ResultKt.throwOnFailure(obj);
                t2 = obj;
            }
            objectRef.element = t2;
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04771) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {140}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nSharedPreferencesPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getInt$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 4 SafeCollector.common.kt\nkotlinx/coroutines/flow/internal/SafeCollector_commonKt\n*L\n1#1,275:1\n47#2:276\n49#2:280\n50#3:277\n55#3:279\n106#4:278\n*S KotlinDebug\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getInt$1\n*L\n138#1:276\n138#1:280\n138#1:277\n138#1:279\n138#1:278\n*E\n"})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1, reason: invalid class name and case insensitive filesystem */
    static final class C04781 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.ObjectRef<Long> $value;
        Object L$0;
        int label;
        final /* synthetic */ SharedPreferencesPlugin this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04781(String str, SharedPreferencesPlugin sharedPreferencesPlugin, Ref.ObjectRef<Long> objectRef, Continuation<? super C04781> continuation) {
            super(2, continuation);
            this.$key = str;
            this.this$0 = sharedPreferencesPlugin;
            this.$value = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04781(this.$key, this.this$0, this.$value, continuation);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Ref.ObjectRef<Long> objectRef;
            T t2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                final Preferences.Key<Long> keyLongKey = PreferencesKeys.longKey(this.$key);
                Context context = this.this$0.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
                    context = null;
                }
                final Flow data = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context).getData();
                Flow<Long> flow = new Flow<Long>() { // from class: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1

                    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$lambda-1$$inlined$collect$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 1, mv = {1, 9, 0}, xi = 48)
                    @SourceDebugExtension({"SMAP\nCollect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Collect.kt\nkotlinx/coroutines/flow/FlowKt__CollectKt$collect$3\n+ 2 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 3 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 4 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getInt$1\n*L\n1#1,134:1\n53#2:135\n48#3:136\n138#4:137\n*E\n"})
                    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public static final class AnonymousClass2 implements FlowCollector<Preferences> {
                        final /* synthetic */ Preferences.Key $preferencesKey$inlined;
                        final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

                        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                        @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1$2", f = "SharedPreferencesPlugin.kt", i = {}, l = {136}, m = "emit", n = {}, s = {})
                        /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                        public static final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            @Nullable
                            public final Object invokeSuspend(@NotNull Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, Preferences.Key key) {
                            this.$this_unsafeFlow$inlined = flowCollector;
                            this.$preferencesKey$inlined = key;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        @org.jetbrains.annotations.Nullable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public java.lang.Object emit(androidx.datastore.preferences.core.Preferences r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) throws java.lang.Throwable {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1$2$1 r0 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1$2$1 r0 = new io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L31
                                if (r2 != r3) goto L29
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L47
                            L29:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L31:
                                kotlin.ResultKt.throwOnFailure(r6)
                                kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow$inlined
                                androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                                androidx.datastore.preferences.core.Preferences$Key r2 = r4.$preferencesKey$inlined
                                java.lang.Object r5 = r5.get(r2)
                                r0.label = r3
                                java.lang.Object r5 = r6.emit(r5, r0)
                                if (r5 != r1) goto L47
                                return r1
                            L47:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getInt$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    @Nullable
                    public Object collect(@NotNull FlowCollector<? super Long> flowCollector, @NotNull Continuation continuation) {
                        Object objCollect = data.collect(new AnonymousClass2(flowCollector, keyLongKey), continuation);
                        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                    }
                };
                Ref.ObjectRef<Long> objectRef2 = this.$value;
                this.L$0 = objectRef2;
                this.label = 1;
                Object objFirstOrNull = FlowKt.firstOrNull(flow, this);
                if (objFirstOrNull == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
                t2 = objFirstOrNull;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = (Ref.ObjectRef) this.L$0;
                ResultKt.throwOnFailure(obj);
                t2 = obj;
            }
            objectRef.element = t2;
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04781) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin", f = "SharedPreferencesPlugin.kt", i = {0, 0, 0, 1, 1, 1, 1}, l = {203, 205}, m = "getPrefs", n = {"this", "allowSet", "filteredMap", "this", "allowSet", "filteredMap", KsProperty.Key}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$4"})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getPrefs$1, reason: invalid class name and case insensitive filesystem */
    static final class C04791 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        C04791(Continuation<? super C04791> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SharedPreferencesPlugin.this.getPrefs(null, this);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {180}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nSharedPreferencesPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getString$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 4 SafeCollector.common.kt\nkotlinx/coroutines/flow/internal/SafeCollector_commonKt\n*L\n1#1,275:1\n47#2:276\n49#2:280\n50#3:277\n55#3:279\n106#4:278\n*S KotlinDebug\n*F\n+ 1 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getString$1\n*L\n178#1:276\n178#1:280\n178#1:277\n178#1:279\n178#1:278\n*E\n"})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1, reason: invalid class name and case insensitive filesystem */
    static final class C04801 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.ObjectRef<String> $value;
        Object L$0;
        int label;
        final /* synthetic */ SharedPreferencesPlugin this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04801(String str, SharedPreferencesPlugin sharedPreferencesPlugin, Ref.ObjectRef<String> objectRef, Continuation<? super C04801> continuation) {
            super(2, continuation);
            this.$key = str;
            this.this$0 = sharedPreferencesPlugin;
            this.$value = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04801(this.$key, this.this$0, this.$value, continuation);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Ref.ObjectRef<String> objectRef;
            T t2;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                final Preferences.Key<String> keyStringKey = PreferencesKeys.stringKey(this.$key);
                Context context = this.this$0.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
                    context = null;
                }
                final Flow data = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context).getData();
                Flow<String> flow = new Flow<String>() { // from class: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1

                    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$lambda-1$$inlined$collect$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 1, mv = {1, 9, 0}, xi = 48)
                    @SourceDebugExtension({"SMAP\nCollect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Collect.kt\nkotlinx/coroutines/flow/FlowKt__CollectKt$collect$3\n+ 2 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 3 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 4 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin$getString$1\n*L\n1#1,134:1\n53#2:135\n48#3:136\n178#4:137\n*E\n"})
                    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public static final class AnonymousClass2 implements FlowCollector<Preferences> {
                        final /* synthetic */ Preferences.Key $preferencesKey$inlined;
                        final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

                        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                        @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1$2", f = "SharedPreferencesPlugin.kt", i = {}, l = {136}, m = "emit", n = {}, s = {})
                        /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                        public static final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            @Nullable
                            public final Object invokeSuspend(@NotNull Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, Preferences.Key key) {
                            this.$this_unsafeFlow$inlined = flowCollector;
                            this.$preferencesKey$inlined = key;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        @org.jetbrains.annotations.Nullable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public java.lang.Object emit(androidx.datastore.preferences.core.Preferences r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) throws java.lang.Throwable {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1$2$1 r0 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1$2$1 r0 = new io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L31
                                if (r2 != r3) goto L29
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L47
                            L29:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L31:
                                kotlin.ResultKt.throwOnFailure(r6)
                                kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow$inlined
                                androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                                androidx.datastore.preferences.core.Preferences$Key r2 = r4.$preferencesKey$inlined
                                java.lang.Object r5 = r5.get(r2)
                                r0.label = r3
                                java.lang.Object r5 = r6.emit(r5, r0)
                                if (r5 != r1) goto L47
                                return r1
                            L47:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getString$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    @Nullable
                    public Object collect(@NotNull FlowCollector<? super String> flowCollector, @NotNull Continuation continuation) {
                        Object objCollect = data.collect(new AnonymousClass2(flowCollector, keyStringKey), continuation);
                        return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
                    }
                };
                Ref.ObjectRef<String> objectRef2 = this.$value;
                this.L$0 = objectRef2;
                this.label = 1;
                Object objFirstOrNull = FlowKt.firstOrNull(flow, this);
                if (objFirstOrNull == coroutine_suspended) {
                    return coroutine_suspended;
                }
                objectRef = objectRef2;
                t2 = objFirstOrNull;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                objectRef = (Ref.ObjectRef) this.L$0;
                ResultKt.throwOnFailure(obj);
                t2 = obj;
            }
            objectRef.element = t2;
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04801) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setBool$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {70}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setBool$1, reason: invalid class name and case insensitive filesystem */
    static final class C04811 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ boolean $value;
        int label;
        final /* synthetic */ SharedPreferencesPlugin this$0;

        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "preferences", "Landroidx/datastore/preferences/core/MutablePreferences;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setBool$1$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setBool$1$1, reason: invalid class name and collision with other inner class name */
        static final class C01981 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
            final /* synthetic */ Preferences.Key<Boolean> $boolKey;
            final /* synthetic */ boolean $value;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01981(Preferences.Key<Boolean> key, boolean z2, Continuation<? super C01981> continuation) {
                super(2, continuation);
                this.$boolKey = key;
                this.$value = z2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C01981 c01981 = new C01981(this.$boolKey, this.$value, continuation);
                c01981.L$0 = obj;
                return c01981;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
            public final Object mo1invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
                return ((C01981) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ((MutablePreferences) this.L$0).set(this.$boolKey, Boxing.boxBoolean(this.$value));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04811(String str, SharedPreferencesPlugin sharedPreferencesPlugin, boolean z2, Continuation<? super C04811> continuation) {
            super(2, continuation);
            this.$key = str;
            this.this$0 = sharedPreferencesPlugin;
            this.$value = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04811(this.$key, this.this$0, this.$value, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Preferences.Key<Boolean> keyBooleanKey = PreferencesKeys.booleanKey(this.$key);
                Context context = this.this$0.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
                    context = null;
                }
                DataStore sharedPreferencesDataStore = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context);
                C01981 c01981 = new C01981(keyBooleanKey, this.$value, null);
                this.label = 1;
                if (PreferencesKt.edit(sharedPreferencesDataStore, c01981, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04811) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setDouble$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {96}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setDouble$1, reason: invalid class name and case insensitive filesystem */
    static final class C04821 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ double $value;
        int label;
        final /* synthetic */ SharedPreferencesPlugin this$0;

        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "preferences", "Landroidx/datastore/preferences/core/MutablePreferences;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setDouble$1$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setDouble$1$1, reason: invalid class name and collision with other inner class name */
        static final class C01991 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
            final /* synthetic */ Preferences.Key<Double> $doubleKey;
            final /* synthetic */ double $value;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01991(Preferences.Key<Double> key, double d2, Continuation<? super C01991> continuation) {
                super(2, continuation);
                this.$doubleKey = key;
                this.$value = d2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C01991 c01991 = new C01991(this.$doubleKey, this.$value, continuation);
                c01991.L$0 = obj;
                return c01991;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
            public final Object mo1invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
                return ((C01991) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ((MutablePreferences) this.L$0).set(this.$doubleKey, Boxing.boxDouble(this.$value));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04821(String str, SharedPreferencesPlugin sharedPreferencesPlugin, double d2, Continuation<? super C04821> continuation) {
            super(2, continuation);
            this.$key = str;
            this.this$0 = sharedPreferencesPlugin;
            this.$value = d2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04821(this.$key, this.this$0, this.$value, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Preferences.Key<Double> keyDoubleKey = PreferencesKeys.doubleKey(this.$key);
                Context context = this.this$0.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
                    context = null;
                }
                DataStore sharedPreferencesDataStore = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context);
                C01991 c01991 = new C01991(keyDoubleKey, this.$value, null);
                this.label = 1;
                if (PreferencesKt.edit(sharedPreferencesDataStore, c01991, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04821) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setInt$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {88}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setInt$1, reason: invalid class name and case insensitive filesystem */
    static final class C04831 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ long $value;
        int label;
        final /* synthetic */ SharedPreferencesPlugin this$0;

        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "preferences", "Landroidx/datastore/preferences/core/MutablePreferences;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setInt$1$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setInt$1$1, reason: invalid class name and collision with other inner class name */
        static final class C02001 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
            final /* synthetic */ Preferences.Key<Long> $intKey;
            final /* synthetic */ long $value;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02001(Preferences.Key<Long> key, long j2, Continuation<? super C02001> continuation) {
                super(2, continuation);
                this.$intKey = key;
                this.$value = j2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C02001 c02001 = new C02001(this.$intKey, this.$value, continuation);
                c02001.L$0 = obj;
                return c02001;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
            public final Object mo1invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
                return ((C02001) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ((MutablePreferences) this.L$0).set(this.$intKey, Boxing.boxLong(this.$value));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04831(String str, SharedPreferencesPlugin sharedPreferencesPlugin, long j2, Continuation<? super C04831> continuation) {
            super(2, continuation);
            this.$key = str;
            this.this$0 = sharedPreferencesPlugin;
            this.$value = j2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C04831(this.$key, this.this$0, this.$value, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Preferences.Key<Long> keyLongKey = PreferencesKeys.longKey(this.$key);
                Context context = this.this$0.context;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
                    context = null;
                }
                DataStore sharedPreferencesDataStore = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context);
                C02001 c02001 = new C02001(keyLongKey, this.$value, null);
                this.label = 1;
                if (PreferencesKt.edit(sharedPreferencesDataStore, c02001, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04831) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setString$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {76}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setString$1, reason: invalid class name and case insensitive filesystem */
    static final class C04841 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ String $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04841(String str, String str2, Continuation<? super C04841> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SharedPreferencesPlugin.this.new C04841(this.$key, this.$value, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                SharedPreferencesPlugin sharedPreferencesPlugin = SharedPreferencesPlugin.this;
                String str = this.$key;
                String str2 = this.$value;
                this.label = 1;
                if (sharedPreferencesPlugin.dataStoreSetString(str, str2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04841) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setStringList$1", f = "SharedPreferencesPlugin.kt", i = {}, l = {107}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$setStringList$1, reason: invalid class name and case insensitive filesystem */
    static final class C04851 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ String $valueString;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04851(String str, String str2, Continuation<? super C04851> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$valueString = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SharedPreferencesPlugin.this.new C04851(this.$key, this.$valueString, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                SharedPreferencesPlugin sharedPreferencesPlugin = SharedPreferencesPlugin.this;
                String str = this.$key;
                String str2 = this.$valueString;
                this.label = 1;
                if (sharedPreferencesPlugin.dataStoreSetString(str, str2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public final Object mo1invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C04851) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public SharedPreferencesPlugin() {
        this.listEncoder = new ListEncoder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object dataStoreSetString(String str, String str2, Continuation<? super Unit> continuation) {
        Preferences.Key<String> keyStringKey = PreferencesKeys.stringKey(str);
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
            context = null;
        }
        Object objEdit = PreferencesKt.edit(SharedPreferencesPluginKt.getSharedPreferencesDataStore(context), new AnonymousClass2(keyStringKey, str2, null), continuation);
        return objEdit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEdit : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:30:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00a5 -> B:33:0x00a8). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getPrefs(java.util.List<java.lang.String> r9, kotlin.coroutines.Continuation<? super java.util.Map<java.lang.String, ? extends java.lang.Object>> r10) throws java.lang.Throwable {
        /*
            r8 = this;
            boolean r0 = r10 instanceof io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin.C04791
            if (r0 == 0) goto L13
            r0 = r10
            io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getPrefs$1 r0 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin.C04791) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getPrefs$1 r0 = new io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getPrefs$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L59
            if (r2 == r4) goto L49
            if (r2 != r3) goto L41
            java.lang.Object r9 = r0.L$4
            androidx.datastore.preferences.core.Preferences$Key r9 = (androidx.datastore.preferences.core.Preferences.Key) r9
            java.lang.Object r2 = r0.L$3
            java.util.Iterator r2 = (java.util.Iterator) r2
            java.lang.Object r4 = r0.L$2
            java.util.Map r4 = (java.util.Map) r4
            java.lang.Object r5 = r0.L$1
            java.util.Set r5 = (java.util.Set) r5
            java.lang.Object r6 = r0.L$0
            io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin r6 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin) r6
            kotlin.ResultKt.throwOnFailure(r10)
            goto La8
        L41:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L49:
            java.lang.Object r9 = r0.L$2
            java.util.Map r9 = (java.util.Map) r9
            java.lang.Object r2 = r0.L$1
            java.util.Set r2 = (java.util.Set) r2
            java.lang.Object r4 = r0.L$0
            io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin r4 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin) r4
            kotlin.ResultKt.throwOnFailure(r10)
            goto L7d
        L59:
            kotlin.ResultKt.throwOnFailure(r10)
            if (r9 == 0) goto L66
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            java.util.Set r9 = kotlin.collections.CollectionsKt.toSet(r9)
        L64:
            r2 = r9
            goto L68
        L66:
            r9 = 0
            goto L64
        L68:
            java.util.LinkedHashMap r9 = new java.util.LinkedHashMap
            r9.<init>()
            r0.L$0 = r8
            r0.L$1 = r2
            r0.L$2 = r9
            r0.label = r4
            java.lang.Object r10 = r8.readAllKeys(r0)
            if (r10 != r1) goto L7c
            return r1
        L7c:
            r4 = r8
        L7d:
            java.util.Set r10 = (java.util.Set) r10
            if (r10 == 0) goto Lc1
            java.util.Iterator r10 = r10.iterator()
            r5 = r2
            r6 = r4
            r4 = r9
            r2 = r10
        L89:
            boolean r9 = r2.hasNext()
            if (r9 == 0) goto Lc0
            java.lang.Object r9 = r2.next()
            androidx.datastore.preferences.core.Preferences$Key r9 = (androidx.datastore.preferences.core.Preferences.Key) r9
            r0.L$0 = r6
            r0.L$1 = r5
            r0.L$2 = r4
            r0.L$3 = r2
            r0.L$4 = r9
            r0.label = r3
            java.lang.Object r10 = r6.getValueByKey(r9, r0)
            if (r10 != r1) goto La8
            return r1
        La8:
            java.lang.String r7 = r9.toString()
            boolean r7 = r6.preferencesFilter(r7, r10, r5)
            if (r7 == 0) goto L89
            java.lang.Object r10 = r6.transformPref(r10)
            if (r10 == 0) goto L89
            java.lang.String r9 = r9.toString()
            r4.put(r9, r10)
            goto L89
        Lc0:
            r9 = r4
        Lc1:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin.getPrefs(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getValueByKey(final Preferences.Key<?> key, Continuation<Object> continuation) {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
            context = null;
        }
        final Flow data = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context).getData();
        return FlowKt.firstOrNull(new Flow<Object>() { // from class: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1

            @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$lambda-1$$inlined$collect$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 1, mv = {1, 9, 0}, xi = 48)
            @SourceDebugExtension({"SMAP\nCollect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Collect.kt\nkotlinx/coroutines/flow/FlowKt__CollectKt$collect$3\n+ 2 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 3 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 4 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin\n*L\n1#1,134:1\n53#2:135\n48#3:136\n222#4:137\n*E\n"})
            /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2 implements FlowCollector<Preferences> {
                final /* synthetic */ Preferences.Key $key$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1$2", f = "SharedPreferencesPlugin.kt", i = {}, l = {136}, m = "emit", n = {}, s = {})
                /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, Preferences.Key key) {
                    this.$this_unsafeFlow$inlined = flowCollector;
                    this.$key$inlined = key;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public java.lang.Object emit(androidx.datastore.preferences.core.Preferences r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) throws java.lang.Throwable {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1$2$1 r0 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1$2$1 r0 = new io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow$inlined
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        androidx.datastore.preferences.core.Preferences$Key r2 = r4.$key$inlined
                        java.lang.Object r5 = r5.get(r2)
                        r0.label = r3
                        java.lang.Object r5 = r6.emit(r5, r0)
                        if (r5 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$getValueByKey$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super Object> flowCollector, @NotNull Continuation continuation2) {
                Object objCollect = data.collect(new AnonymousClass2(flowCollector, key), continuation2);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }, continuation);
    }

    private final boolean preferencesFilter(String key, Object value, Set<String> allowList) {
        return allowList == null ? (value instanceof Boolean) || (value instanceof Long) || (value instanceof String) || (value instanceof Double) : allowList.contains(key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object readAllKeys(Continuation<? super Set<? extends Preferences.Key<?>>> continuation) {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(com.umeng.analytics.pro.f.X);
            context = null;
        }
        final Flow data = SharedPreferencesPluginKt.getSharedPreferencesDataStore(context).getData();
        return FlowKt.firstOrNull(new Flow<Set<? extends Preferences.Key<?>>>() { // from class: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1

            @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$lambda-1$$inlined$collect$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 1, mv = {1, 9, 0}, xi = 48)
            @SourceDebugExtension({"SMAP\nCollect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Collect.kt\nkotlinx/coroutines/flow/FlowKt__CollectKt$collect$3\n+ 2 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 3 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 4 SharedPreferencesPlugin.kt\nio/flutter/plugins/sharedpreferences/SharedPreferencesPlugin\n*L\n1#1,134:1\n53#2:135\n48#3:136\n217#4:137\n*E\n"})
            /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2 implements FlowCollector<Preferences> {
                final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                @DebugMetadata(c = "io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1$2", f = "SharedPreferencesPlugin.kt", i = {}, l = {136}, m = "emit", n = {}, s = {})
                /* renamed from: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow$inlined = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public java.lang.Object emit(androidx.datastore.preferences.core.Preferences r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) throws java.lang.Throwable {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1$2$1 r0 = (io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1$2$1 r0 = new io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow$inlined
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        java.util.Map r5 = r5.asMap()
                        java.util.Set r5 = r5.keySet()
                        r0.label = r3
                        java.lang.Object r5 = r6.emit(r5, r0)
                        if (r5 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin$readAllKeys$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super Set<? extends Preferences.Key<?>>> flowCollector, @NotNull Continuation continuation2) {
                Object objCollect = data.collect(new AnonymousClass2(flowCollector), continuation2);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        }, continuation);
    }

    private final void setUp(BinaryMessenger messenger, Context context) {
        this.context = context;
        try {
            SharedPreferencesAsyncApi.INSTANCE.setUp(messenger, this);
        } catch (Exception e2) {
            Log.e(SharedPreferencesPluginKt.TAG, "Received exception while setting up SharedPreferencesPlugin", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object transformPref(Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        String str = (String) value;
        if (!StringsKt.startsWith$default(str, SharedPreferencesPluginKt.LIST_PREFIX, false, 2, (Object) null)) {
            return value;
        }
        SharedPreferencesListEncoder sharedPreferencesListEncoder = this.listEncoder;
        String strSubstring = str.substring(40);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return sharedPreferencesListEncoder.decode(strSubstring);
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    public void clear(@Nullable List<String> allowList, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(options, "options");
        BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(allowList, null), 1, null);
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    @NotNull
    public Map<String, Object> getAll(@Nullable List<String> allowList, @NotNull SharedPreferencesPigeonOptions options) {
        Intrinsics.checkNotNullParameter(options, "options");
        return (Map) BuildersKt__BuildersKt.runBlocking$default(null, new C04751(allowList, null), 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    @Nullable
    public Boolean getBool(@NotNull String key, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        BuildersKt__BuildersKt.runBlocking$default(null, new C04761(key, this, objectRef, null), 1, null);
        return (Boolean) objectRef.element;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    @Nullable
    public Double getDouble(@NotNull String key, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        BuildersKt__BuildersKt.runBlocking$default(null, new C04771(key, this, objectRef, null), 1, null);
        return (Double) objectRef.element;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    @Nullable
    public Long getInt(@NotNull String key, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        BuildersKt__BuildersKt.runBlocking$default(null, new C04781(key, this, objectRef, null), 1, null);
        return (Long) objectRef.element;
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    @NotNull
    public List<String> getKeys(@Nullable List<String> allowList, @NotNull SharedPreferencesPigeonOptions options) {
        Intrinsics.checkNotNullParameter(options, "options");
        return CollectionsKt.toList(((Map) BuildersKt__BuildersKt.runBlocking$default(null, new SharedPreferencesPlugin$getKeys$prefs$1(this, allowList, null), 1, null)).keySet());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    @Nullable
    public String getString(@NotNull String key, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        BuildersKt__BuildersKt.runBlocking$default(null, new C04801(key, this, objectRef, null), 1, null);
        return (String) objectRef.element;
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    @Nullable
    public List<String> getStringList(@NotNull String key, @NotNull SharedPreferencesPigeonOptions options) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        List list = (List) transformPref(getString(key, options));
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof String) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        Context applicationContext = binding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        setUp(binaryMessenger, applicationContext);
        new LegacySharedPreferencesPlugin().onAttachedToEngine(binding);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        SharedPreferencesAsyncApi.Companion companion = SharedPreferencesAsyncApi.INSTANCE;
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        companion.setUp(binaryMessenger, null);
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    public void setBool(@NotNull String key, boolean value, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        BuildersKt__BuildersKt.runBlocking$default(null, new C04811(key, this, value, null), 1, null);
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    public void setDouble(@NotNull String key, double value, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        BuildersKt__BuildersKt.runBlocking$default(null, new C04821(key, this, value, null), 1, null);
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    public void setInt(@NotNull String key, long value, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        BuildersKt__BuildersKt.runBlocking$default(null, new C04831(key, this, value, null), 1, null);
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    public void setString(@NotNull String key, @NotNull String value, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(options, "options");
        BuildersKt__BuildersKt.runBlocking$default(null, new C04841(key, value, null), 1, null);
    }

    @Override // io.flutter.plugins.sharedpreferences.SharedPreferencesAsyncApi
    public void setStringList(@NotNull String key, @NotNull List<String> value, @NotNull SharedPreferencesPigeonOptions options) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(options, "options");
        BuildersKt__BuildersKt.runBlocking$default(null, new C04851(key, SharedPreferencesPluginKt.LIST_PREFIX + this.listEncoder.encode(value), null), 1, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @VisibleForTesting
    public SharedPreferencesPlugin(@NotNull SharedPreferencesListEncoder listEncoder) {
        this();
        Intrinsics.checkNotNullParameter(listEncoder, "listEncoder");
        this.listEncoder = listEncoder;
    }
}
