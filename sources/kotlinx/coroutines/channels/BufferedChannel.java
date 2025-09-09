package kotlinx.coroutines.channels;

import androidx.concurrent.futures.a;
import androidx.exifinterface.media.ExifInterface;
import androidx.webkit.ProxyConfig;
import com.google.common.primitives.Longs;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.tekartik.sqflite.Constant;
import com.umeng.analytics.pro.bc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmName;
import kotlin.jvm.Volatile;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.time.DurationKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectClause1Impl;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectClause2Impl;
import kotlinx.coroutines.selects.SelectImplementation;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.TrySelectDetailedResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mozilla.javascript.ES6Iterator;

@Metadata(d1 = {"\u0000¾\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\bN\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0004ø\u0001ù\u0001B3\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\"\b\u0002\u0010\b\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0007¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00028\u0000H\u0082@ø\u0001\u0000¢\u0006\u0004\b\f\u0010\rJ9\u0010\u0013\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00020\u0011H\u0082@ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014J)\u0010\u0016\u001a\u00020\u0006*\u00020\u00152\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u0016\u0010\u0017J%\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00028\u00002\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0018H\u0002¢\u0006\u0004\b\u001a\u0010\u001bJZ\u0010 \u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00152\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00060\u001d2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u001dH\u0082\b¢\u0006\u0004\b \u0010!JG\u0010%\u001a\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\"2\u0006\u0010$\u001a\u00020#H\u0002¢\u0006\u0004\b%\u0010&JG\u0010'\u001a\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\"2\u0006\u0010$\u001a\u00020#H\u0002¢\u0006\u0004\b'\u0010&J\u0017\u0010)\u001a\u00020#2\u0006\u0010(\u001a\u00020\u0011H\u0003¢\u0006\u0004\b)\u0010*J\u0017\u0010,\u001a\u00020#2\u0006\u0010+\u001a\u00020\u0011H\u0002¢\u0006\u0004\b,\u0010*J\u001b\u0010-\u001a\u00020#*\u00020\"2\u0006\u0010\u000b\u001a\u00028\u0000H\u0002¢\u0006\u0004\b-\u0010.J1\u00100\u001a\u00028\u00002\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\u0011H\u0082@ø\u0001\u0000¢\u0006\u0004\b0\u00101J)\u00102\u001a\u00020\u0006*\u00020\u00152\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u0003H\u0002¢\u0006\u0004\b2\u0010\u0017J\u001d\u00103\u001a\u00020\u00062\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H\u0002¢\u0006\u0004\b3\u00104J@\u00107\u001a\b\u0012\u0004\u0012\u00028\u0000052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\u0011H\u0082@ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000ø\u0001\u0000¢\u0006\u0004\b6\u00101J&\u00108\u001a\u00020\u00062\u0012\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000050\u0018H\u0002ø\u0001\u0000¢\u0006\u0004\b8\u00104Jù\u0001\u0010B\u001a\u00028\u0001\"\u0004\b\u0001\u001092\b\u0010\u001c\u001a\u0004\u0018\u00010\"2!\u0010<\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00028\u00010\u00052Q\u0010@\u001aM\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u000e¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(>\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(?\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(/\u0012\u0004\u0012\u00028\u00010=2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00010\u001d2S\b\u0002\u0010A\u001aM\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u000e¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(>\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(?\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(/\u0012\u0004\u0012\u00028\u00010=H\u0082\b¢\u0006\u0004\bB\u0010CJg\u0010D\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00152!\u0010<\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u001dH\u0082\b¢\u0006\u0004\bD\u0010EJ9\u0010F\u001a\u0004\u0018\u00010\"2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0004\bF\u0010GJ9\u0010H\u001a\u0004\u0018\u00010\"2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0004\bH\u0010GJ)\u0010I\u001a\u00020#*\u00020\"2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u0003H\u0002¢\u0006\u0004\bI\u0010JJ\u000f\u0010K\u001a\u00020\u0006H\u0002¢\u0006\u0004\bK\u0010LJ-\u0010N\u001a\u00020#2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010M\u001a\u00020\u0011H\u0002¢\u0006\u0004\bN\u0010OJ-\u0010P\u001a\u00020#2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010M\u001a\u00020\u0011H\u0002¢\u0006\u0004\bP\u0010OJ\u0019\u0010R\u001a\u00020\u00062\b\b\u0002\u0010Q\u001a\u00020\u0011H\u0002¢\u0006\u0004\bR\u0010SJ#\u0010V\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00028\u00002\n\u0010U\u001a\u0006\u0012\u0002\b\u00030TH\u0002¢\u0006\u0004\bV\u0010WJ%\u0010Z\u001a\u0004\u0018\u00010\"2\b\u0010X\u001a\u0004\u0018\u00010\"2\b\u0010Y\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0004\bZ\u0010[J%\u0010\\\u001a\u00020\u00062\n\u0010U\u001a\u0006\u0012\u0002\b\u00030T2\b\u0010X\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0004\b\\\u0010]J\u001b\u0010^\u001a\u00020\u00062\n\u0010U\u001a\u0006\u0012\u0002\b\u00030TH\u0002¢\u0006\u0004\b^\u0010_J%\u0010`\u001a\u0004\u0018\u00010\"2\b\u0010X\u001a\u0004\u0018\u00010\"2\b\u0010Y\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0004\b`\u0010[J%\u0010a\u001a\u0004\u0018\u00010\"2\b\u0010X\u001a\u0004\u0018\u00010\"2\b\u0010Y\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0004\ba\u0010[J%\u0010b\u001a\u0004\u0018\u00010\"2\b\u0010X\u001a\u0004\u0018\u00010\"2\b\u0010Y\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0004\bb\u0010[J\u000f\u0010c\u001a\u00020\u0006H\u0002¢\u0006\u0004\bc\u0010LJ\u000f\u0010d\u001a\u00020\u0006H\u0002¢\u0006\u0004\bd\u0010LJ\u000f\u0010e\u001a\u00020\u0006H\u0002¢\u0006\u0004\be\u0010LJ\u000f\u0010f\u001a\u00020\u0006H\u0002¢\u0006\u0004\bf\u0010LJ\u000f\u0010g\u001a\u00020\u0006H\u0002¢\u0006\u0004\bg\u0010LJ\u001d\u0010i\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010h\u001a\u00020\u0011H\u0002¢\u0006\u0004\bi\u0010jJ\u0017\u0010k\u001a\u00020\u00062\u0006\u0010h\u001a\u00020\u0011H\u0002¢\u0006\u0004\bk\u0010SJ\u0015\u0010l\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0002¢\u0006\u0004\bl\u0010mJ\u001d\u0010o\u001a\u00020\u00112\f\u0010n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0002¢\u0006\u0004\bo\u0010pJ\u001d\u0010q\u001a\u00020\u00062\f\u0010n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0002¢\u0006\u0004\bq\u0010rJ%\u0010t\u001a\u00020\u00062\f\u0010n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010s\u001a\u00020\u0011H\u0002¢\u0006\u0004\bt\u0010uJ\u0013\u0010v\u001a\u00020\u0006*\u00020\u0015H\u0002¢\u0006\u0004\bv\u0010wJ\u0013\u0010x\u001a\u00020\u0006*\u00020\u0015H\u0002¢\u0006\u0004\bx\u0010wJ\u001b\u0010z\u001a\u00020\u0006*\u00020\u00152\u0006\u0010y\u001a\u00020#H\u0002¢\u0006\u0004\bz\u0010{J\u001f\u0010~\u001a\u00020#2\u0006\u0010|\u001a\u00020\u00112\u0006\u0010}\u001a\u00020#H\u0002¢\u0006\u0004\b~\u0010\u007fJ0\u0010\u0081\u0001\u001a\u00020#2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u0010\u001a\u00020\u00032\u0007\u0010\u0080\u0001\u001a\u00020\u0011H\u0002¢\u0006\u0005\b\u0081\u0001\u0010OJ2\u0010\u0084\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000e2\u0007\u0010\u0082\u0001\u001a\u00020\u00112\r\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0002¢\u0006\u0006\b\u0084\u0001\u0010\u0085\u0001J2\u0010\u0086\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000e2\u0007\u0010\u0082\u0001\u001a\u00020\u00112\r\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0002¢\u0006\u0006\b\u0086\u0001\u0010\u0085\u0001J;\u0010\u0088\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000e2\u0007\u0010\u0082\u0001\u001a\u00020\u00112\r\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0007\u0010\u0087\u0001\u001a\u00020\u0011H\u0002¢\u0006\u0006\b\u0088\u0001\u0010\u0089\u0001J*\u0010\u008a\u0001\u001a\u00020\u00062\u0007\u0010\u0082\u0001\u001a\u00020\u00112\r\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0002¢\u0006\u0006\b\u008a\u0001\u0010\u008b\u0001J\u001a\u0010\u008d\u0001\u001a\u00020\u00062\u0007\u0010\u008c\u0001\u001a\u00020\u0011H\u0002¢\u0006\u0005\b\u008d\u0001\u0010SJ\u001a\u0010\u008e\u0001\u001a\u00020\u00062\u0007\u0010\u008c\u0001\u001a\u00020\u0011H\u0002¢\u0006\u0005\b\u008e\u0001\u0010SJ\u001d\u0010\u008f\u0001\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0005\b\u008f\u0001\u0010\rJ)\u0010\u0092\u0001\u001a\b\u0012\u0004\u0012\u00020\u0006052\u0006\u0010\u000b\u001a\u00028\u0000H\u0016ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000¢\u0006\u0006\b\u0090\u0001\u0010\u0091\u0001J\u001d\u0010\u0094\u0001\u001a\u00020#2\u0006\u0010\u000b\u001a\u00028\u0000H\u0090@ø\u0001\u0000¢\u0006\u0005\b\u0093\u0001\u0010\rJ\u0011\u0010)\u001a\u00020#H\u0010¢\u0006\u0006\b\u0095\u0001\u0010\u0096\u0001J\u000f\u0010?\u001a\u00020\u0006H\u0014¢\u0006\u0004\b?\u0010LJ\u0011\u0010\u0097\u0001\u001a\u00020\u0006H\u0014¢\u0006\u0005\b\u0097\u0001\u0010LJ\u0016\u0010\u0098\u0001\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0006\b\u0098\u0001\u0010\u0099\u0001J%\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u00028\u000005H\u0096@ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000ø\u0001\u0000¢\u0006\u0006\b\u009a\u0001\u0010\u0099\u0001J!\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u00028\u000005H\u0016ø\u0001\u0001ø\u0001\u0002ø\u0001\u0000¢\u0006\u0006\b\u009c\u0001\u0010\u009d\u0001J\u0018\u0010M\u001a\u00020\u00062\u0007\u0010\u009f\u0001\u001a\u00020\u0011H\u0004¢\u0006\u0004\bM\u0010SJ\u001a\u0010¡\u0001\u001a\u00020\u00062\u0007\u0010\u0080\u0001\u001a\u00020\u0011H\u0000¢\u0006\u0005\b \u0001\u0010SJ'\u0010¢\u0001\u001a\u00020\u00062\n\u0010U\u001a\u0006\u0012\u0002\b\u00030T2\b\u0010\u000b\u001a\u0004\u0018\u00010\"H\u0014¢\u0006\u0005\b¢\u0001\u0010]J\u001a\u0010¤\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000£\u0001H\u0096\u0002¢\u0006\u0006\b¤\u0001\u0010¥\u0001J\u0011\u0010¦\u0001\u001a\u00020\u0006H\u0014¢\u0006\u0005\b¦\u0001\u0010LJ\u001e\u0010©\u0001\u001a\u00020#2\n\u0010¨\u0001\u001a\u0005\u0018\u00010§\u0001H\u0016¢\u0006\u0006\b©\u0001\u0010ª\u0001J\u001c\u0010«\u0001\u001a\u00020#2\n\u0010¨\u0001\u001a\u0005\u0018\u00010§\u0001¢\u0006\u0006\b«\u0001\u0010ª\u0001J\u000f\u0010«\u0001\u001a\u00020\u0006¢\u0006\u0005\b«\u0001\u0010LJ#\u0010«\u0001\u001a\u00020\u00062\u0011\u0010¨\u0001\u001a\f\u0018\u00010¬\u0001j\u0005\u0018\u0001`\u00ad\u0001¢\u0006\u0006\b«\u0001\u0010®\u0001J\u001e\u0010°\u0001\u001a\u00020#2\n\u0010¨\u0001\u001a\u0005\u0018\u00010§\u0001H\u0010¢\u0006\u0006\b¯\u0001\u0010ª\u0001J'\u0010±\u0001\u001a\u00020#2\n\u0010¨\u0001\u001a\u0005\u0018\u00010§\u00012\u0007\u0010«\u0001\u001a\u00020#H\u0014¢\u0006\u0006\b±\u0001\u0010²\u0001J:\u0010´\u0001\u001a\u00020\u00062&\u0010³\u0001\u001a!\u0012\u0017\u0012\u0015\u0018\u00010§\u0001¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(¨\u0001\u0012\u0004\u0012\u00020\u00060\u0005H\u0016¢\u0006\u0006\b´\u0001\u0010µ\u0001J\u0012\u0010·\u0001\u001a\u00020#H\u0000¢\u0006\u0006\b¶\u0001\u0010\u0096\u0001J\u0013\u0010¹\u0001\u001a\u00030¸\u0001H\u0016¢\u0006\u0006\b¹\u0001\u0010º\u0001J\u0013\u0010¼\u0001\u001a\u00030¸\u0001H\u0000¢\u0006\u0006\b»\u0001\u0010º\u0001J\u000f\u0010½\u0001\u001a\u00020\u0006¢\u0006\u0005\b½\u0001\u0010LR\u0015\u0010\u0004\u001a\u00020\u00038\u0002X\u0082\u0004¢\u0006\u0007\n\u0005\b\u0004\u0010¾\u0001R/\u0010\b\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u00078\u0000X\u0081\u0004¢\u0006\u0007\n\u0005\b\b\u0010¿\u0001R\u0083\u0001\u0010Ã\u0001\u001ag\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030T¢\u0006\f\b:\u0012\b\b;\u0012\u0004\b\b(U\u0012\u0016\u0012\u0014\u0018\u00010\"¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(À\u0001\u0012\u0016\u0012\u0014\u0018\u00010\"¢\u0006\r\b:\u0012\t\b;\u0012\u0005\b\b(Á\u0001\u0012\u0011\u0012\u000f\u0012\u0005\u0012\u00030§\u0001\u0012\u0004\u0012\u00020\u00060\u0005\u0018\u00010=j\u0005\u0018\u0001`Â\u00018\u0002X\u0082\u0004¢\u0006\u000f\n\u0006\bÃ\u0001\u0010Ä\u0001\u0012\u0005\bÅ\u0001\u0010LR\u0017\u0010È\u0001\u001a\u00020\u00118BX\u0082\u0004¢\u0006\b\u001a\u0006\bÆ\u0001\u0010Ç\u0001R\u0017\u0010É\u0001\u001a\u00020#8BX\u0082\u0004¢\u0006\b\u001a\u0006\bÉ\u0001\u0010\u0096\u0001R1\u0010Î\u0001\u001a\u0015\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00000Ê\u00018VX\u0096\u0004¢\u0006\u000f\u0012\u0005\bÍ\u0001\u0010L\u001a\u0006\bË\u0001\u0010Ì\u0001R%\u0010Ó\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000Ï\u00018VX\u0096\u0004¢\u0006\u000f\u0012\u0005\bÒ\u0001\u0010L\u001a\u0006\bÐ\u0001\u0010Ñ\u0001R.\u0010Ö\u0001\u001a\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000050Ï\u00018VX\u0096\u0004ø\u0001\u0000¢\u0006\u000f\u0012\u0005\bÕ\u0001\u0010L\u001a\u0006\bÔ\u0001\u0010Ñ\u0001R'\u0010Ù\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00018\u00000Ï\u00018VX\u0096\u0004¢\u0006\u000f\u0012\u0005\bØ\u0001\u0010L\u001a\u0006\b×\u0001\u0010Ñ\u0001R\u0018\u0010Ü\u0001\u001a\u00030§\u00018BX\u0082\u0004¢\u0006\b\u001a\u0006\bÚ\u0001\u0010Û\u0001R\u001e\u0010Ý\u0001\u001a\u00020#8VX\u0097\u0004¢\u0006\u000f\u0012\u0005\bÞ\u0001\u0010L\u001a\u0006\bÝ\u0001\u0010\u0096\u0001R\u001a\u0010ß\u0001\u001a\u00020#*\u00020\u00118BX\u0082\u0004¢\u0006\u0007\u001a\u0005\bß\u0001\u0010*R\u001c\u0010}\u001a\u00020#8VX\u0097\u0004¢\u0006\u000e\u0012\u0005\bà\u0001\u0010L\u001a\u0005\b}\u0010\u0096\u0001R\u001a\u0010á\u0001\u001a\u00020#*\u00020\u00118BX\u0082\u0004¢\u0006\u0007\u001a\u0005\bá\u0001\u0010*R\u001e\u0010â\u0001\u001a\u00020#8VX\u0097\u0004¢\u0006\u000f\u0012\u0005\bã\u0001\u0010L\u001a\u0006\bâ\u0001\u0010\u0096\u0001R\u0016\u0010s\u001a\u00020\u00118@X\u0080\u0004¢\u0006\b\u001a\u0006\bä\u0001\u0010Ç\u0001R\u0017\u0010æ\u0001\u001a\u00020\u00118@X\u0080\u0004¢\u0006\b\u001a\u0006\bå\u0001\u0010Ç\u0001R\u001a\u0010è\u0001\u001a\u0005\u0018\u00010§\u00018DX\u0084\u0004¢\u0006\b\u001a\u0006\bç\u0001\u0010Û\u0001R\u0018\u0010ê\u0001\u001a\u00030§\u00018DX\u0084\u0004¢\u0006\b\u001a\u0006\bé\u0001\u0010Û\u0001R\u0017\u0010ì\u0001\u001a\u00020#8TX\u0094\u0004¢\u0006\b\u001a\u0006\bë\u0001\u0010\u0096\u0001R\u0015\u0010î\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010\"0í\u00018\u0002X\u0082\u0004R\r\u0010ð\u0001\u001a\u00030ï\u00018\u0002X\u0082\u0004R\u0019\u0010ñ\u0001\u001a\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000e0í\u00018\u0002X\u0082\u0004R\u0015\u0010ò\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010\"0í\u00018\u0002X\u0082\u0004R\r\u0010ó\u0001\u001a\u00030ï\u00018\u0002X\u0082\u0004R\u0019\u0010ô\u0001\u001a\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000e0í\u00018\u0002X\u0082\u0004R\r\u0010õ\u0001\u001a\u00030ï\u00018\u0002X\u0082\u0004R\u0019\u0010ö\u0001\u001a\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000e0í\u00018\u0002X\u0082\u0004R\r\u0010÷\u0001\u001a\u00030ï\u00018\u0002X\u0082\u0004\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006ú\u0001"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/Channel;", "", "capacity", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(ILkotlin/jvm/functions/Function1;)V", "element", "onClosedSend", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ChannelSegment;", "segment", FirebaseAnalytics.Param.INDEX, "", "s", "sendOnNoWaiterSuspend", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/Waiter;", "prepareSenderForSuspension", "(Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V", "Lkotlinx/coroutines/CancellableContinuation;", "cont", "onClosedSendOnNoWaiterSuspend", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "waiter", "Lkotlin/Function0;", "onRendezvousOrBuffered", "onClosed", "sendImplOnNoWaiter", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLkotlinx/coroutines/Waiter;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "", "", "closed", "updateCellSend", "(Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLjava/lang/Object;Z)I", "updateCellSendSlow", "curSendersAndCloseStatus", "shouldSendSuspend", "(J)Z", "curSenders", "bufferOrRendezvousSend", "tryResumeReceiver", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "r", "receiveOnNoWaiterSuspend", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "prepareReceiverForSuspension", "onClosedReceiveOnNoWaiterSuspend", "(Lkotlinx/coroutines/CancellableContinuation;)V", "Lkotlinx/coroutines/channels/ChannelResult;", "receiveCatchingOnNoWaiterSuspend-GKJJFZk", "receiveCatchingOnNoWaiterSuspend", "onClosedReceiveCatchingOnNoWaiterSuspend", "R", "Lkotlin/ParameterName;", "name", "onElementRetrieved", "Lkotlin/Function3;", "segm", "i", "onSuspend", "onNoWaiterSuspend", "receiveImpl", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "receiveImplOnNoWaiter", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlinx/coroutines/Waiter;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)V", "updateCellReceive", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLjava/lang/Object;)Ljava/lang/Object;", "updateCellReceiveSlow", "tryResumeSender", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/ChannelSegment;I)Z", "expandBuffer", "()V", "b", "updateCellExpandBuffer", "(Lkotlinx/coroutines/channels/ChannelSegment;IJ)Z", "updateCellExpandBufferSlow", "nAttempts", "incCompletedExpandBufferAttempts", "(J)V", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "onClosedSelectOnSend", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)V", "ignoredParam", "selectResult", "processResultSelectSend", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "registerSelectForReceive", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;)V", "onClosedSelectOnReceive", "(Lkotlinx/coroutines/selects/SelectInstance;)V", "processResultSelectReceive", "processResultSelectReceiveOrNull", "processResultSelectReceiveCatching", "invokeCloseHandler", "markClosed", "markCancelled", "markCancellationStarted", "completeCloseOrCancel", "sendersCur", "completeClose", "(J)Lkotlinx/coroutines/channels/ChannelSegment;", "completeCancel", "closeLinkedList", "()Lkotlinx/coroutines/channels/ChannelSegment;", "lastSegment", "markAllEmptyCellsAsClosed", "(Lkotlinx/coroutines/channels/ChannelSegment;)J", "removeUnprocessedElements", "(Lkotlinx/coroutines/channels/ChannelSegment;)V", "sendersCounter", "cancelSuspendedReceiveRequests", "(Lkotlinx/coroutines/channels/ChannelSegment;J)V", "resumeReceiverOnClosedChannel", "(Lkotlinx/coroutines/Waiter;)V", "resumeSenderOnCancelledChannel", "receiver", "resumeWaiterOnClosedChannel", "(Lkotlinx/coroutines/Waiter;Z)V", "sendersAndCloseStatusCur", "isClosedForReceive", "isClosed", "(JZ)Z", "globalIndex", "isCellNonEmpty", "id", "startFrom", "findSegmentSend", "(JLkotlinx/coroutines/channels/ChannelSegment;)Lkotlinx/coroutines/channels/ChannelSegment;", "findSegmentReceive", "currentBufferEndCounter", "findSegmentBufferEnd", "(JLkotlinx/coroutines/channels/ChannelSegment;J)Lkotlinx/coroutines/channels/ChannelSegment;", "moveSegmentBufferEndToSpecifiedOrLast", "(JLkotlinx/coroutines/channels/ChannelSegment;)V", "value", "updateSendersCounterIfLower", "updateReceiversCounterIfLower", "send", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "trySend", "sendBroadcast$kotlinx_coroutines_core", "sendBroadcast", "shouldSendSuspend$kotlinx_coroutines_core", "()Z", "h", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching-JP2dKIU", "receiveCatching", "tryReceive-PtdJZtk", "()Ljava/lang/Object;", "tryReceive", "globalCellIndex", "waitExpandBufferCompletion$kotlinx_coroutines_core", "waitExpandBufferCompletion", "l", "Lkotlinx/coroutines/channels/ChannelIterator;", "iterator", "()Lkotlinx/coroutines/channels/ChannelIterator;", "g", "", "cause", "close", "(Ljava/lang/Throwable;)Z", Constant.PARAM_CANCEL, "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "cancelImpl$kotlinx_coroutines_core", "cancelImpl", "a", "(Ljava/lang/Throwable;Z)Z", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "hasElements$kotlinx_coroutines_core", "hasElements", "", "toString", "()Ljava/lang/String;", "toStringDebug$kotlinx_coroutines_core", "toStringDebug", "checkSegmentStructureInvariants", "I", "Lkotlin/jvm/functions/Function1;", RemoteMessageConst.MessageBody.PARAM, "internalResult", "Lkotlinx/coroutines/selects/OnCancellationConstructor;", "onUndeliveredElementReceiveCancellationConstructor", "Lkotlin/jvm/functions/Function3;", "getOnUndeliveredElementReceiveCancellationConstructor$annotations", "getBufferEndCounter", "()J", "bufferEndCounter", "isRendezvousOrUnlimited", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend$annotations", "onSend", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive$annotations", "onReceive", "getOnReceiveCatching", "getOnReceiveCatching$annotations", "onReceiveCatching", "getOnReceiveOrNull", "getOnReceiveOrNull$annotations", "onReceiveOrNull", "getReceiveException", "()Ljava/lang/Throwable;", "receiveException", "isClosedForSend", "isClosedForSend$annotations", "isClosedForSend0", "isClosedForReceive$annotations", "isClosedForReceive0", "isEmpty", "isEmpty$annotations", "getSendersCounter$kotlinx_coroutines_core", "getReceiversCounter$kotlinx_coroutines_core", "receiversCounter", bc.aL, "closeCause", "d", "sendException", "f", "isConflatedDropOldest", "Lkotlinx/atomicfu/AtomicRef;", "_closeCause", "Lkotlinx/atomicfu/AtomicLong;", "bufferEnd", "bufferEndSegment", "closeHandler", "completedExpandBuffersAndPauseFlag", "receiveSegment", "receivers", "sendSegment", "sendersAndCloseStatus", "BufferedChannelIterator", "SendBroadcast", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0})
@SourceDebugExtension({"SMAP\nBufferedChannel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannelKt\n+ 4 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n+ 5 DispatchedTask.kt\nkotlinx/coroutines/DispatchedTaskKt\n+ 6 StackTraceRecovery.kt\nkotlinx/coroutines/internal/StackTraceRecoveryKt\n+ 7 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel$sendImpl$1\n+ 8 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel$receiveImpl$1\n+ 9 InlineList.kt\nkotlinx/coroutines/internal/InlineList\n+ 10 ConcurrentLinkedList.kt\nkotlinx/coroutines/internal/ConcurrentLinkedListKt\n+ 11 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,3055:1\n273#1,6:3058\n280#1,68:3065\n374#1,18:3156\n244#1:3174\n269#1,10:3175\n280#1,48:3186\n395#1:3234\n334#1,14:3235\n399#1,3:3250\n244#1:3263\n269#1,10:3264\n280#1,68:3275\n244#1:3353\n269#1,10:3354\n280#1,68:3365\n244#1:3437\n269#1,10:3438\n280#1,68:3449\n886#1,52:3519\n964#1,8:3575\n858#1:3583\n882#1,33:3584\n974#1:3617\n916#1,14:3618\n935#1,3:3633\n979#1,6:3636\n886#1,52:3650\n964#1,8:3706\n858#1:3714\n882#1,33:3715\n974#1:3748\n916#1,14:3749\n935#1,3:3764\n979#1,6:3767\n858#1:3782\n882#1,48:3783\n935#1,3:3832\n858#1:3835\n882#1,48:3836\n935#1,3:3885\n244#1:3897\n269#1,10:3898\n280#1,68:3909\n858#1:3978\n882#1,48:3979\n935#1,3:4028\n1#2:3056\n3038#3:3057\n3038#3:3064\n3038#3:3185\n3038#3:3274\n3038#3:3364\n3038#3:3436\n3038#3:3448\n3038#3:3518\n3038#3:3781\n3038#3:3888\n3038#3:3889\n3052#3:3890\n3052#3:3891\n3051#3:3892\n3051#3:3893\n3051#3:3894\n3052#3:3895\n3051#3:3896\n3038#3:3908\n3039#3:4031\n3038#3:4032\n3038#3:4033\n3038#3:4034\n3039#3:4035\n3038#3:4036\n3039#3:4059\n3038#3:4060\n3038#3:4061\n3039#3:4062\n3038#3:4112\n3039#3:4113\n3039#3:4114\n3039#3:4132\n3039#3:4133\n314#4,9:3133\n323#4,2:3150\n332#4,4:3152\n336#4,8:3253\n314#4,9:3344\n323#4,2:3434\n332#4,4:3571\n336#4,8:3642\n332#4,4:3702\n336#4,8:3773\n220#5:3142\n221#5:3145\n220#5:3146\n221#5:3149\n61#6,2:3143\n61#6,2:3147\n61#6,2:3261\n269#7:3249\n269#7:3343\n269#7:3433\n269#7:3517\n269#7:3977\n882#8:3632\n882#8:3763\n882#8:3831\n882#8:3884\n882#8:4027\n37#9,11:4037\n37#9,11:4048\n72#10,3:4063\n46#10,8:4066\n72#10,3:4074\n46#10,8:4077\n46#10,8:4085\n72#10,3:4093\n46#10,8:4096\n46#10,8:4104\n766#11:4115\n857#11,2:4116\n2310#11,14:4118\n766#11:4134\n857#11,2:4135\n2310#11,14:4137\n766#11:4151\n857#11,2:4152\n2310#11,14:4154\n*S KotlinDebug\n*F\n+ 1 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel\n*L\n113#1:3058,6\n113#1:3065,68\n154#1:3156,18\n154#1:3174\n154#1:3175,10\n154#1:3186,48\n154#1:3234\n154#1:3235,14\n154#1:3250,3\n194#1:3263\n194#1:3264,10\n194#1:3275,68\n225#1:3353\n225#1:3354,10\n225#1:3365,68\n391#1:3437\n391#1:3438,10\n391#1:3449,68\n667#1:3519,52\n696#1:3575,8\n696#1:3583\n696#1:3584,33\n696#1:3617\n696#1:3618,14\n696#1:3633,3\n696#1:3636,6\n732#1:3650,52\n748#1:3706,8\n748#1:3714\n748#1:3715,33\n748#1:3748\n748#1:3749,14\n748#1:3764,3\n748#1:3767,6\n781#1:3782\n781#1:3783,48\n781#1:3832,3\n971#1:3835\n971#1:3836,48\n971#1:3885,3\n1464#1:3897\n1464#1:3898,10\n1464#1:3909,68\n1512#1:3978\n1512#1:3979,48\n1512#1:4028,3\n70#1:3057\n113#1:3064\n154#1:3185\n194#1:3274\n225#1:3364\n278#1:3436\n391#1:3448\n606#1:3518\n771#1:3781\n1007#1:3888\n1056#1:3889\n1374#1:3890\n1376#1:3891\n1406#1:3892\n1416#1:3893\n1425#1:3894\n1426#1:3895\n1433#1:3896\n1464#1:3908\n1865#1:4031\n1867#1:4032\n1869#1:4033\n1882#1:4034\n1893#1:4035\n1894#1:4036\n2196#1:4059\n2209#1:4060\n2219#1:4061\n2222#1:4062\n2539#1:4112\n2541#1:4113\n2566#1:4114\n2628#1:4132\n2629#1:4133\n134#1:3133,9\n134#1:3150,2\n153#1:3152,4\n153#1:3253,8\n221#1:3344,9\n221#1:3434,2\n695#1:3571,4\n695#1:3642,8\n746#1:3702,4\n746#1:3773,8\n138#1:3142\n138#1:3145\n141#1:3146\n141#1:3149\n138#1:3143,2\n141#1:3147,2\n183#1:3261,2\n154#1:3249\n194#1:3343\n225#1:3433\n391#1:3517\n1464#1:3977\n696#1:3632\n748#1:3763\n781#1:3831\n971#1:3884\n1512#1:4027\n2098#1:4037,11\n2153#1:4048,11\n2361#1:4063,3\n2361#1:4066,8\n2416#1:4074,3\n2416#1:4077,8\n2435#1:4085,8\n2465#1:4093,3\n2465#1:4096,8\n2526#1:4104,8\n2575#1:4115\n2575#1:4116,2\n2576#1:4118,14\n2640#1:4134\n2640#1:4135,2\n2641#1:4137,14\n2681#1:4151\n2681#1:4152,2\n2682#1:4154,14\n*E\n"})
/* loaded from: classes5.dex */
public class BufferedChannel<E> implements Channel<E> {

    @Volatile
    @Nullable
    private volatile Object _closeCause;

    @Volatile
    private volatile long bufferEnd;

    @Volatile
    @Nullable
    private volatile Object bufferEndSegment;
    private final int capacity;

    @Volatile
    @Nullable
    private volatile Object closeHandler;

    @Volatile
    private volatile long completedExpandBuffersAndPauseFlag;

    @JvmField
    @Nullable
    public final Function1<E, Unit> onUndeliveredElement;

    @Nullable
    private final Function3<SelectInstance<?>, Object, Object, Function1<Throwable, Unit>> onUndeliveredElementReceiveCancellationConstructor;

    @Volatile
    @Nullable
    private volatile Object receiveSegment;

    @Volatile
    private volatile long receivers;

    @Volatile
    @Nullable
    private volatile Object sendSegment;

    @Volatile
    private volatile long sendersAndCloseStatus;

    @NotNull
    private static final AtomicLongFieldUpdater sendersAndCloseStatus$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "sendersAndCloseStatus");

    @NotNull
    private static final AtomicLongFieldUpdater receivers$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "receivers");

    @NotNull
    private static final AtomicLongFieldUpdater bufferEnd$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "bufferEnd");

    @NotNull
    private static final AtomicLongFieldUpdater completedExpandBuffersAndPauseFlag$FU = AtomicLongFieldUpdater.newUpdater(BufferedChannel.class, "completedExpandBuffersAndPauseFlag");

    @NotNull
    private static final AtomicReferenceFieldUpdater sendSegment$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "sendSegment");

    @NotNull
    private static final AtomicReferenceFieldUpdater receiveSegment$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "receiveSegment");

    @NotNull
    private static final AtomicReferenceFieldUpdater bufferEndSegment$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "bufferEndSegment");

    @NotNull
    private static final AtomicReferenceFieldUpdater _closeCause$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "_closeCause");

    @NotNull
    private static final AtomicReferenceFieldUpdater closeHandler$FU = AtomicReferenceFieldUpdater.newUpdater(BufferedChannel.class, Object.class, "closeHandler");

    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0011\u0010\t\u001a\u00020\u0006H\u0096Bø\u0001\u0000¢\u0006\u0002\u0010\nJ/\u0010\u000b\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0012J\u001c\u0010\u0013\u001a\u00020\u00142\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0016\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0006H\u0002J\b\u0010\u0019\u001a\u00020\u0014H\u0002J\u0013\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\u0006\u0010\u001d\u001a\u00020\u0014R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;", "Lkotlinx/coroutines/channels/ChannelIterator;", "Lkotlinx/coroutines/Waiter;", "(Lkotlinx/coroutines/channels/BufferedChannel;)V", "continuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "", "receiveResult", "", "hasNext", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasNextOnNoWaiterSuspend", "segment", "Lkotlinx/coroutines/channels/ChannelSegment;", FirebaseAnalytics.Param.INDEX, "", "r", "", "(Lkotlinx/coroutines/channels/ChannelSegment;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invokeOnCancellation", "", "Lkotlinx/coroutines/internal/Segment;", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", "onClosedHasNext", "onClosedHasNextNoWaiterSuspend", "tryResumeHasNext", "element", "(Ljava/lang/Object;)Z", "tryResumeHasNextOnClosedChannel", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nBufferedChannel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator\n+ 2 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel\n+ 3 CancellableContinuation.kt\nkotlinx/coroutines/CancellableContinuationKt\n+ 4 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel$receiveImpl$1\n+ 5 StackTraceRecovery.kt\nkotlinx/coroutines/internal/StackTraceRecoveryKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,3055:1\n886#2,52:3056\n964#2,8:3112\n858#2:3120\n882#2,33:3121\n974#2:3154\n916#2,14:3155\n935#2,3:3170\n979#2,6:3173\n332#3,4:3108\n336#3,8:3179\n882#4:3169\n61#5,2:3187\n61#5,2:3190\n1#6:3189\n*S KotlinDebug\n*F\n+ 1 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator\n*L\n1590#1:3056,52\n1627#1:3112,8\n1627#1:3120\n1627#1:3121,33\n1627#1:3154\n1627#1:3155,14\n1627#1:3170,3\n1627#1:3173,6\n1625#1:3108,4\n1625#1:3179,8\n1627#1:3169\n1663#1:3187,2\n1708#1:3190,2\n*E\n"})
    private final class BufferedChannelIterator implements ChannelIterator<E>, Waiter {

        @Nullable
        private CancellableContinuationImpl<? super Boolean> continuation;

        @Nullable
        private Object receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;

        public BufferedChannelIterator() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object hasNextOnNoWaiterSuspend(ChannelSegment<E> channelSegment, int i2, long j2, Continuation<? super Boolean> continuation) {
            Boolean boolBoxBoolean;
            BufferedChannel bufferedChannel = BufferedChannel.this;
            CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
            try {
                this.continuation = orCreateCancellableContinuation;
                Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i2, j2, this);
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                    bufferedChannel.prepareReceiverForSuspension(this, channelSegment, i2);
                } else {
                    Function1<Throwable, Unit> function1BindCancellationFun = null;
                    if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                        if (j2 < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                            channelSegment.cleanPrev();
                        }
                        ChannelSegment channelSegment2 = (ChannelSegment) BufferedChannel.receiveSegment$FU.get(bufferedChannel);
                        while (true) {
                            if (bufferedChannel.isClosedForReceive()) {
                                onClosedHasNextNoWaiterSuspend();
                                break;
                            }
                            long andIncrement = BufferedChannel.receivers$FU.getAndIncrement(bufferedChannel);
                            int i3 = BufferedChannelKt.SEGMENT_SIZE;
                            long j3 = andIncrement / i3;
                            int i4 = (int) (andIncrement % i3);
                            if (channelSegment2.id != j3) {
                                ChannelSegment channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j3, channelSegment2);
                                if (channelSegmentFindSegmentReceive != null) {
                                    channelSegment2 = channelSegmentFindSegmentReceive;
                                }
                            }
                            Object objUpdateCellReceive2 = bufferedChannel.updateCellReceive(channelSegment2, i4, andIncrement, this);
                            if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND) {
                                bufferedChannel.prepareReceiverForSuspension(this, channelSegment2, i4);
                                break;
                            }
                            if (objUpdateCellReceive2 == BufferedChannelKt.FAILED) {
                                if (andIncrement < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                                    channelSegment2.cleanPrev();
                                }
                            } else {
                                if (objUpdateCellReceive2 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                    throw new IllegalStateException("unexpected".toString());
                                }
                                channelSegment2.cleanPrev();
                                this.receiveResult = objUpdateCellReceive2;
                                this.continuation = null;
                                boolBoxBoolean = Boxing.boxBoolean(true);
                                Function1<E, Unit> function1 = bufferedChannel.onUndeliveredElement;
                                if (function1 != null) {
                                    function1BindCancellationFun = OnUndeliveredElementKt.bindCancellationFun(function1, objUpdateCellReceive2, orCreateCancellableContinuation.getContext());
                                }
                            }
                        }
                    } else {
                        channelSegment.cleanPrev();
                        this.receiveResult = objUpdateCellReceive;
                        this.continuation = null;
                        boolBoxBoolean = Boxing.boxBoolean(true);
                        Function1<E, Unit> function12 = bufferedChannel.onUndeliveredElement;
                        if (function12 != null) {
                            function1BindCancellationFun = OnUndeliveredElementKt.bindCancellationFun(function12, objUpdateCellReceive, orCreateCancellableContinuation.getContext());
                        }
                    }
                    orCreateCancellableContinuation.resume(boolBoxBoolean, function1BindCancellationFun);
                }
                Object result = orCreateCancellableContinuation.getResult();
                if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(continuation);
                }
                return result;
            } catch (Throwable th) {
                orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
                throw th;
            }
        }

        private final boolean onClosedHasNext() throws Throwable {
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable thC = BufferedChannel.this.c();
            if (thC == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(thC);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void onClosedHasNextNoWaiterSuspend() {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable thC = BufferedChannel.this.c();
            if (thC == null) {
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m837constructorimpl(Boolean.FALSE));
            } else {
                Result.Companion companion2 = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m837constructorimpl(ResultKt.createFailure(thC)));
            }
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        @Nullable
        public Object hasNext(@NotNull Continuation<? super Boolean> continuation) {
            ChannelSegment<E> channelSegment;
            BufferedChannel bufferedChannel = BufferedChannel.this;
            ChannelSegment<E> channelSegment2 = (ChannelSegment) BufferedChannel.receiveSegment$FU.get(bufferedChannel);
            while (!bufferedChannel.isClosedForReceive()) {
                long andIncrement = BufferedChannel.receivers$FU.getAndIncrement(bufferedChannel);
                int i2 = BufferedChannelKt.SEGMENT_SIZE;
                long j2 = andIncrement / i2;
                int i3 = (int) (andIncrement % i2);
                if (channelSegment2.id != j2) {
                    ChannelSegment<E> channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j2, channelSegment2);
                    if (channelSegmentFindSegmentReceive == null) {
                        continue;
                    } else {
                        channelSegment = channelSegmentFindSegmentReceive;
                    }
                } else {
                    channelSegment = channelSegment2;
                }
                Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i3, andIncrement, null);
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                    throw new IllegalStateException("unreachable".toString());
                }
                if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                    if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                        return hasNextOnNoWaiterSuspend(channelSegment, i3, andIncrement, continuation);
                    }
                    channelSegment.cleanPrev();
                    this.receiveResult = objUpdateCellReceive;
                    return Boxing.boxBoolean(true);
                }
                if (andIncrement < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
                channelSegment2 = channelSegment;
            }
            return Boxing.boxBoolean(onClosedHasNext());
        }

        @Override // kotlinx.coroutines.Waiter
        public void invokeOnCancellation(@NotNull Segment<?> segment, int index) {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            if (cancellableContinuationImpl != null) {
                cancellableContinuationImpl.invokeOnCancellation(segment, index);
            }
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        @JvmName(name = ES6Iterator.NEXT_METHOD)
        public /* synthetic */ Object next(Continuation continuation) {
            return ChannelIterator.DefaultImpls.next(this, continuation);
        }

        public final boolean tryResumeHasNext(E element) {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = element;
            Boolean bool = Boolean.TRUE;
            Function1<E, Unit> function1 = BufferedChannel.this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl, bool, function1 != null ? OnUndeliveredElementKt.bindCancellationFun(function1, element, cancellableContinuationImpl.getContext()) : null);
        }

        public final void tryResumeHasNextOnClosedChannel() {
            CancellableContinuationImpl<? super Boolean> cancellableContinuationImpl = this.continuation;
            Intrinsics.checkNotNull(cancellableContinuationImpl);
            this.continuation = null;
            this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
            Throwable thC = BufferedChannel.this.c();
            if (thC == null) {
                Result.Companion companion = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m837constructorimpl(Boolean.FALSE));
            } else {
                Result.Companion companion2 = Result.INSTANCE;
                cancellableContinuationImpl.resumeWith(Result.m837constructorimpl(ResultKt.createFailure(thC)));
            }
        }

        @Override // kotlinx.coroutines.channels.ChannelIterator
        public E next() throws Throwable {
            E e2 = (E) this.receiveResult;
            if (e2 == BufferedChannelKt.NO_RECEIVE_RESULT) {
                throw new IllegalStateException("`hasNext()` has not been invoked".toString());
            }
            this.receiveResult = BufferedChannelKt.NO_RECEIVE_RESULT;
            if (e2 != BufferedChannelKt.getCHANNEL_CLOSED()) {
                return e2;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(BufferedChannel.this.getReceiveException());
        }
    }

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u001d\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\f\u001a\u00020\rH\u0096\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/channels/BufferedChannel$SendBroadcast;", "Lkotlinx/coroutines/Waiter;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/CancellableContinuation;)V", "getCont", "()Lkotlinx/coroutines/CancellableContinuation;", "invokeOnCancellation", "", "segment", "Lkotlinx/coroutines/internal/Segment;", FirebaseAnalytics.Param.INDEX, "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class SendBroadcast implements Waiter {
        private final /* synthetic */ CancellableContinuationImpl<Boolean> $$delegate_0;

        @NotNull
        private final CancellableContinuation<Boolean> cont;

        /* JADX WARN: Multi-variable type inference failed */
        public SendBroadcast(@NotNull CancellableContinuation<? super Boolean> cancellableContinuation) {
            this.cont = cancellableContinuation;
            Intrinsics.checkNotNull(cancellableContinuation, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuationImpl<kotlin.Boolean>");
            this.$$delegate_0 = (CancellableContinuationImpl) cancellableContinuation;
        }

        @NotNull
        public final CancellableContinuation<Boolean> getCont() {
            return this.cont;
        }

        @Override // kotlinx.coroutines.Waiter
        public void invokeOnCancellation(@NotNull Segment<?> segment, int index) {
            this.$$delegate_0.invokeOnCancellation(segment, index);
        }
    }

    @Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\n¢\u0006\u0002\b\n"}, d2 = {"<anonymous>", "", "R", ExifInterface.LONGITUDE_EAST, "<anonymous parameter 0>", "Lkotlinx/coroutines/channels/ChannelSegment;", "<anonymous parameter 1>", "", "<anonymous parameter 2>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nBufferedChannel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BufferedChannel.kt\nkotlinx/coroutines/channels/BufferedChannel$receiveImpl$1\n*L\n1#1,3055:1\n*E\n"})
    /* renamed from: kotlinx.coroutines.channels.BufferedChannel$receiveImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends Lambda implements Function3 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(3);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke((ChannelSegment<Object>) obj, ((Number) obj2).intValue(), ((Number) obj3).longValue());
        }

        @NotNull
        public final Void invoke(@NotNull ChannelSegment<Object> channelSegment, int i2, long j2) {
            throw new IllegalStateException("unexpected".toString());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [kotlin.jvm.functions.Function1<? super E, kotlin.Unit>, kotlin.jvm.functions.Function1<E, kotlin.Unit>] */
    public BufferedChannel(int i2, @Nullable Function1<? super E, Unit> function1) {
        this.capacity = i2;
        this.onUndeliveredElement = function1;
        if (i2 < 0) {
            throw new IllegalArgumentException(("Invalid channel capacity: " + i2 + ", should be >=0").toString());
        }
        this.bufferEnd = BufferedChannelKt.initialBufferEnd(i2);
        this.completedExpandBuffersAndPauseFlag = getBufferEndCounter();
        ChannelSegment channelSegment = new ChannelSegment(0L, null, this, 3);
        this.sendSegment = channelSegment;
        this.receiveSegment = channelSegment;
        if (isRendezvousOrUnlimited()) {
            channelSegment = BufferedChannelKt.NULL_SEGMENT;
            Intrinsics.checkNotNull(channelSegment, "null cannot be cast to non-null type kotlinx.coroutines.channels.ChannelSegment<E of kotlinx.coroutines.channels.BufferedChannel>");
        }
        this.bufferEndSegment = channelSegment;
        this.onUndeliveredElementReceiveCancellationConstructor = function1 != 0 ? new Function3<SelectInstance<?>, Object, Object, Function1<? super Throwable, ? extends Unit>>(this) { // from class: kotlinx.coroutines.channels.BufferedChannel$onUndeliveredElementReceiveCancellationConstructor$1$1
            final /* synthetic */ BufferedChannel<E> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function3
            @NotNull
            public final Function1<Throwable, Unit> invoke(@NotNull final SelectInstance<?> selectInstance, @Nullable Object obj, @Nullable final Object obj2) {
                final BufferedChannel<E> bufferedChannel = this.this$0;
                return new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.channels.BufferedChannel$onUndeliveredElementReceiveCancellationConstructor$1$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull Throwable th) {
                        if (obj2 != BufferedChannelKt.getCHANNEL_CLOSED()) {
                            OnUndeliveredElementKt.callUndeliveredElement(bufferedChannel.onUndeliveredElement, obj2, selectInstance.getContext());
                        }
                    }
                };
            }
        } : null;
        this._closeCause = BufferedChannelKt.NO_CLOSE_CAUSE;
    }

    private final boolean bufferOrRendezvousSend(long curSenders) {
        return curSenders < getBufferEndCounter() || curSenders < getReceiversCounter$kotlinx_coroutines_core() + ((long) this.capacity);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void cancelSuspendedReceiveRequests(ChannelSegment<E> lastSegment, long sendersCounter) {
        Object objM2371constructorimpl$default = InlineList.m2371constructorimpl$default(null, 1, null);
        loop0: while (lastSegment != null) {
            for (int i2 = BufferedChannelKt.SEGMENT_SIZE - 1; -1 < i2; i2--) {
                if ((lastSegment.id * BufferedChannelKt.SEGMENT_SIZE) + i2 < sendersCounter) {
                    break loop0;
                }
                while (true) {
                    Object state$kotlinx_coroutines_core = lastSegment.getState$kotlinx_coroutines_core(i2);
                    if (state$kotlinx_coroutines_core != null && state$kotlinx_coroutines_core != BufferedChannelKt.IN_BUFFER) {
                        if (!(state$kotlinx_coroutines_core instanceof WaiterEB)) {
                            if (!(state$kotlinx_coroutines_core instanceof Waiter)) {
                                break;
                            }
                            if (lastSegment.casState$kotlinx_coroutines_core(i2, state$kotlinx_coroutines_core, BufferedChannelKt.getCHANNEL_CLOSED())) {
                                objM2371constructorimpl$default = InlineList.m2376plusFjFbRPM(objM2371constructorimpl$default, state$kotlinx_coroutines_core);
                                lastSegment.onCancelledRequest(i2, true);
                                break;
                            }
                        } else {
                            if (lastSegment.casState$kotlinx_coroutines_core(i2, state$kotlinx_coroutines_core, BufferedChannelKt.getCHANNEL_CLOSED())) {
                                objM2371constructorimpl$default = InlineList.m2376plusFjFbRPM(objM2371constructorimpl$default, ((WaiterEB) state$kotlinx_coroutines_core).waiter);
                                lastSegment.onCancelledRequest(i2, true);
                                break;
                            }
                        }
                    } else {
                        if (lastSegment.casState$kotlinx_coroutines_core(i2, state$kotlinx_coroutines_core, BufferedChannelKt.getCHANNEL_CLOSED())) {
                            lastSegment.onSlotCleaned();
                            break;
                        }
                    }
                }
            }
            lastSegment = (ChannelSegment) lastSegment.getPrev();
        }
        if (objM2371constructorimpl$default != null) {
            if (!(objM2371constructorimpl$default instanceof ArrayList)) {
                resumeReceiverOnClosedChannel((Waiter) objM2371constructorimpl$default);
                return;
            }
            Intrinsics.checkNotNull(objM2371constructorimpl$default, "null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            ArrayList arrayList = (ArrayList) objM2371constructorimpl$default;
            for (int size = arrayList.size() - 1; -1 < size; size--) {
                resumeReceiverOnClosedChannel((Waiter) arrayList.get(size));
            }
        }
    }

    private final ChannelSegment<E> closeLinkedList() {
        Object obj = bufferEndSegment$FU.get(this);
        ChannelSegment channelSegment = (ChannelSegment) sendSegment$FU.get(this);
        if (channelSegment.id > ((ChannelSegment) obj).id) {
            obj = channelSegment;
        }
        ChannelSegment channelSegment2 = (ChannelSegment) receiveSegment$FU.get(this);
        if (channelSegment2.id > ((ChannelSegment) obj).id) {
            obj = channelSegment2;
        }
        return (ChannelSegment) ConcurrentLinkedListKt.close((ConcurrentLinkedListNode) obj);
    }

    private final void completeCancel(long sendersCur) {
        removeUnprocessedElements(completeClose(sendersCur));
    }

    private final ChannelSegment<E> completeClose(long sendersCur) {
        ChannelSegment<E> channelSegmentCloseLinkedList = closeLinkedList();
        if (f()) {
            long jMarkAllEmptyCellsAsClosed = markAllEmptyCellsAsClosed(channelSegmentCloseLinkedList);
            if (jMarkAllEmptyCellsAsClosed != -1) {
                b(jMarkAllEmptyCellsAsClosed);
            }
        }
        cancelSuspendedReceiveRequests(channelSegmentCloseLinkedList, sendersCur);
        return channelSegmentCloseLinkedList;
    }

    private final void completeCloseOrCancel() {
        isClosedForSend();
    }

    static /* synthetic */ void e(BufferedChannel bufferedChannel, long j2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incCompletedExpandBufferAttempts");
        }
        if ((i2 & 1) != 0) {
            j2 = 1;
        }
        bufferedChannel.incCompletedExpandBufferAttempts(j2);
    }

    private final void expandBuffer() {
        if (isRendezvousOrUnlimited()) {
            return;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) bufferEndSegment$FU.get(this);
        while (true) {
            long andIncrement = bufferEnd$FU.getAndIncrement(this);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = andIncrement / i2;
            if (getSendersCounter$kotlinx_coroutines_core() <= andIncrement) {
                if (channelSegment.id < j2 && channelSegment.getNext() != 0) {
                    moveSegmentBufferEndToSpecifiedOrLast(j2, channelSegment);
                }
                e(this, 0L, 1, null);
                return;
            }
            if (channelSegment.id != j2) {
                ChannelSegment<E> channelSegmentFindSegmentBufferEnd = findSegmentBufferEnd(j2, channelSegment, andIncrement);
                if (channelSegmentFindSegmentBufferEnd == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentBufferEnd;
                }
            }
            if (updateCellExpandBuffer(channelSegment, (int) (andIncrement % i2), andIncrement)) {
                e(this, 0L, 1, null);
                return;
            }
            e(this, 0L, 1, null);
        }
    }

    private final ChannelSegment<E> findSegmentBufferEnd(long id, ChannelSegment<E> startFrom, long currentBufferEndCounter) {
        Object objFindSegmentInternal;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = bufferEndSegment$FU;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, function2);
            if (!SegmentOrClosed.m2388isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2386getSegmentimpl = SegmentOrClosed.m2386getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicReferenceFieldUpdater.get(this);
                    if (segment.id >= segmentM2386getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2386getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                        break;
                    }
                    if (a.a(atomicReferenceFieldUpdater, this, segment, segmentM2386getSegmentimpl)) {
                        if (segment.decPointers$kotlinx_coroutines_core()) {
                            segment.remove();
                        }
                    } else if (segmentM2386getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                        segmentM2386getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m2388isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            moveSegmentBufferEndToSpecifiedOrLast(id, startFrom);
            e(this, 0L, 1, null);
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m2386getSegmentimpl(objFindSegmentInternal);
        long j2 = channelSegment.id;
        if (j2 <= id) {
            return channelSegment;
        }
        int i2 = BufferedChannelKt.SEGMENT_SIZE;
        if (bufferEnd$FU.compareAndSet(this, currentBufferEndCounter + 1, i2 * j2)) {
            incCompletedExpandBufferAttempts((channelSegment.id * i2) - currentBufferEndCounter);
            return null;
        }
        e(this, 0L, 1, null);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChannelSegment<E> findSegmentReceive(long id, ChannelSegment<E> startFrom) {
        Object objFindSegmentInternal;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = receiveSegment$FU;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, function2);
            if (!SegmentOrClosed.m2388isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2386getSegmentimpl = SegmentOrClosed.m2386getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicReferenceFieldUpdater.get(this);
                    if (segment.id >= segmentM2386getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2386getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                        break;
                    }
                    if (a.a(atomicReferenceFieldUpdater, this, segment, segmentM2386getSegmentimpl)) {
                        if (segment.decPointers$kotlinx_coroutines_core()) {
                            segment.remove();
                        }
                    } else if (segmentM2386getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                        segmentM2386getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m2388isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            if (startFrom.id * BufferedChannelKt.SEGMENT_SIZE >= getSendersCounter$kotlinx_coroutines_core()) {
                return null;
            }
            startFrom.cleanPrev();
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m2386getSegmentimpl(objFindSegmentInternal);
        if (!isRendezvousOrUnlimited() && id <= getBufferEndCounter() / BufferedChannelKt.SEGMENT_SIZE) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = bufferEndSegment$FU;
            while (true) {
                Segment segment2 = (Segment) atomicReferenceFieldUpdater2.get(this);
                if (segment2.id >= channelSegment.id || !channelSegment.tryIncPointers$kotlinx_coroutines_core()) {
                    break;
                }
                if (a.a(atomicReferenceFieldUpdater2, this, segment2, channelSegment)) {
                    if (segment2.decPointers$kotlinx_coroutines_core()) {
                        segment2.remove();
                    }
                } else if (channelSegment.decPointers$kotlinx_coroutines_core()) {
                    channelSegment.remove();
                }
            }
        }
        long j2 = channelSegment.id;
        if (j2 <= id) {
            return channelSegment;
        }
        int i2 = BufferedChannelKt.SEGMENT_SIZE;
        updateReceiversCounterIfLower(j2 * i2);
        if (channelSegment.id * i2 >= getSendersCounter$kotlinx_coroutines_core()) {
            return null;
        }
        channelSegment.cleanPrev();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ChannelSegment<E> findSegmentSend(long id, ChannelSegment<E> startFrom) {
        Object objFindSegmentInternal;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = sendSegment$FU;
        Function2 function2 = (Function2) BufferedChannelKt.createSegmentFunction();
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(startFrom, id, function2);
            if (!SegmentOrClosed.m2388isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2386getSegmentimpl = SegmentOrClosed.m2386getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicReferenceFieldUpdater.get(this);
                    if (segment.id >= segmentM2386getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2386getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                        break;
                    }
                    if (a.a(atomicReferenceFieldUpdater, this, segment, segmentM2386getSegmentimpl)) {
                        if (segment.decPointers$kotlinx_coroutines_core()) {
                            segment.remove();
                        }
                    } else if (segmentM2386getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                        segmentM2386getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        if (SegmentOrClosed.m2388isClosedimpl(objFindSegmentInternal)) {
            completeCloseOrCancel();
            if (startFrom.id * BufferedChannelKt.SEGMENT_SIZE >= getReceiversCounter$kotlinx_coroutines_core()) {
                return null;
            }
            startFrom.cleanPrev();
            return null;
        }
        ChannelSegment<E> channelSegment = (ChannelSegment) SegmentOrClosed.m2386getSegmentimpl(objFindSegmentInternal);
        long j2 = channelSegment.id;
        if (j2 <= id) {
            return channelSegment;
        }
        int i2 = BufferedChannelKt.SEGMENT_SIZE;
        updateSendersCounterIfLower(j2 * i2);
        if (channelSegment.id * i2 >= getReceiversCounter$kotlinx_coroutines_core()) {
            return null;
        }
        channelSegment.cleanPrev();
        return null;
    }

    private final Object getAndUpdate$atomicfu(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, ? extends Object> function1, Object obj) {
        Object obj2;
        do {
            obj2 = atomicReferenceFieldUpdater.get(obj);
        } while (!a.a(atomicReferenceFieldUpdater, obj, obj2, function1.invoke(obj2)));
        return obj2;
    }

    private final long getBufferEndCounter() {
        return bufferEnd$FU.get(this);
    }

    public static /* synthetic */ void getOnReceive$annotations() {
    }

    public static /* synthetic */ void getOnReceiveCatching$annotations() {
    }

    public static /* synthetic */ void getOnReceiveOrNull$annotations() {
    }

    public static /* synthetic */ void getOnSend$annotations() {
    }

    private static /* synthetic */ void getOnUndeliveredElementReceiveCancellationConstructor$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Throwable getReceiveException() {
        Throwable thC = c();
        return thC == null ? new ClosedReceiveChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : thC;
    }

    private final void incCompletedExpandBufferAttempts(long nAttempts) {
        if ((completedExpandBuffersAndPauseFlag$FU.addAndGet(this, nAttempts) & Longs.MAX_POWER_OF_TWO) != 0) {
            while ((completedExpandBuffersAndPauseFlag$FU.get(this) & Longs.MAX_POWER_OF_TWO) != 0) {
            }
        }
    }

    private final void invokeCloseHandler() {
        Object obj;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = closeHandler$FU;
        do {
            obj = atomicReferenceFieldUpdater.get(this);
        } while (!a.a(atomicReferenceFieldUpdater, this, obj, obj == null ? BufferedChannelKt.CLOSE_HANDLER_CLOSED : BufferedChannelKt.CLOSE_HANDLER_INVOKED));
        if (obj == null) {
            return;
        }
        ((Function1) obj).invoke(c());
    }

    private final boolean isCellNonEmpty(ChannelSegment<E> segment, int index, long globalIndex) {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
            if (state$kotlinx_coroutines_core != null && state$kotlinx_coroutines_core != BufferedChannelKt.IN_BUFFER) {
                if (state$kotlinx_coroutines_core == BufferedChannelKt.BUFFERED) {
                    return true;
                }
                if (state$kotlinx_coroutines_core == BufferedChannelKt.INTERRUPTED_SEND || state$kotlinx_coroutines_core == BufferedChannelKt.getCHANNEL_CLOSED() || state$kotlinx_coroutines_core == BufferedChannelKt.DONE_RCV || state$kotlinx_coroutines_core == BufferedChannelKt.POISONED) {
                    return false;
                }
                if (state$kotlinx_coroutines_core == BufferedChannelKt.RESUMING_BY_EB) {
                    return true;
                }
                return state$kotlinx_coroutines_core != BufferedChannelKt.RESUMING_BY_RCV && globalIndex == getReceiversCounter$kotlinx_coroutines_core();
            }
        } while (!segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.POISONED));
        expandBuffer();
        return false;
    }

    private final boolean isClosed(long sendersAndCloseStatusCur, boolean isClosedForReceive) {
        int i2 = (int) (sendersAndCloseStatusCur >> 60);
        if (i2 == 0 || i2 == 1) {
            return false;
        }
        if (i2 == 2) {
            completeClose(sendersAndCloseStatusCur & 1152921504606846975L);
            if (isClosedForReceive && hasElements$kotlinx_coroutines_core()) {
                return false;
            }
        } else {
            if (i2 != 3) {
                throw new IllegalStateException(("unexpected close status: " + i2).toString());
            }
            completeCancel(sendersAndCloseStatusCur & 1152921504606846975L);
        }
        return true;
    }

    @ExperimentalCoroutinesApi
    public static /* synthetic */ void isClosedForReceive$annotations() {
    }

    private final boolean isClosedForReceive0(long j2) {
        return isClosed(j2, true);
    }

    @ExperimentalCoroutinesApi
    public static /* synthetic */ void isClosedForSend$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isClosedForSend0(long j2) {
        return isClosed(j2, false);
    }

    @ExperimentalCoroutinesApi
    public static /* synthetic */ void isEmpty$annotations() {
    }

    private final boolean isRendezvousOrUnlimited() {
        long bufferEndCounter = getBufferEndCounter();
        return bufferEndCounter == 0 || bufferEndCounter == Long.MAX_VALUE;
    }

    static /* synthetic */ Object j(BufferedChannel bufferedChannel, Continuation continuation) throws Throwable {
        ChannelSegment<E> channelSegment = (ChannelSegment) receiveSegment$FU.get(bufferedChannel);
        while (!bufferedChannel.isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(bufferedChannel);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = andIncrement / i2;
            int i3 = (int) (andIncrement % i2);
            if (channelSegment.id != j2) {
                ChannelSegment<E> channelSegmentFindSegmentReceive = bufferedChannel.findSegmentReceive(j2, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            }
            Object objUpdateCellReceive = bufferedChannel.updateCellReceive(channelSegment, i3, andIncrement, null);
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                throw new IllegalStateException("unexpected".toString());
            }
            if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    return bufferedChannel.receiveOnNoWaiterSuspend(channelSegment, i3, andIncrement, continuation);
                }
                channelSegment.cleanPrev();
                return objUpdateCellReceive;
            }
            if (andIncrement < bufferedChannel.getSendersCounter$kotlinx_coroutines_core()) {
                channelSegment.cleanPrev();
            }
        }
        throw StackTraceRecoveryKt.recoverStackTrace(bufferedChannel.getReceiveException());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ java.lang.Object k(kotlinx.coroutines.channels.BufferedChannel r13, kotlin.coroutines.Continuation r14) throws java.lang.Throwable {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            if (r0 == 0) goto L14
            r0 = r14
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = (kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r6 = r0
            goto L1a
        L14:
            kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1 r0 = new kotlinx.coroutines.channels.BufferedChannel$receiveCatching$1
            r0.<init>(r13, r14)
            goto L12
        L1a:
            java.lang.Object r14 = r6.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            r2 = 1
            if (r1 == 0) goto L3a
            if (r1 != r2) goto L32
            kotlin.ResultKt.throwOnFailure(r14)
            kotlinx.coroutines.channels.ChannelResult r14 = (kotlinx.coroutines.channels.ChannelResult) r14
            java.lang.Object r13 = r14.getHolder()
            goto Lb2
        L32:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L3a:
            kotlin.ResultKt.throwOnFailure(r14)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r14 = access$getReceiveSegment$FU$p()
            java.lang.Object r14 = r14.get(r13)
            kotlinx.coroutines.channels.ChannelSegment r14 = (kotlinx.coroutines.channels.ChannelSegment) r14
        L47:
            boolean r1 = r13.isClosedForReceive()
            if (r1 == 0) goto L58
            kotlinx.coroutines.channels.ChannelResult$Companion r14 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Throwable r13 = r13.c()
            java.lang.Object r13 = r14.m2348closedJP2dKIU(r13)
            goto Lb2
        L58:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r1 = access$getReceivers$FU$p()
            long r4 = r1.getAndIncrement(r13)
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r7 = (long) r1
            long r7 = r4 / r7
            long r9 = (long) r1
            long r9 = r4 % r9
            int r3 = (int) r9
            long r9 = r14.id
            int r1 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r1 == 0) goto L77
            kotlinx.coroutines.channels.ChannelSegment r1 = access$findSegmentReceive(r13, r7, r14)
            if (r1 != 0) goto L76
            goto L47
        L76:
            r14 = r1
        L77:
            r12 = 0
            r7 = r13
            r8 = r14
            r9 = r3
            r10 = r4
            java.lang.Object r1 = access$updateCellReceive(r7, r8, r9, r10, r12)
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.channels.BufferedChannelKt.access$getSUSPEND$p()
            if (r1 == r7) goto Lb3
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.channels.BufferedChannelKt.access$getFAILED$p()
            if (r1 != r7) goto L98
            long r7 = r13.getSendersCounter$kotlinx_coroutines_core()
            int r1 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r1 >= 0) goto L47
            r14.cleanPrev()
            goto L47
        L98:
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.channels.BufferedChannelKt.access$getSUSPEND_NO_WAITER$p()
            if (r1 != r7) goto La9
            r6.label = r2
            r1 = r13
            r2 = r14
            java.lang.Object r13 = r1.m2328receiveCatchingOnNoWaiterSuspendGKJJFZk(r2, r3, r4, r6)
            if (r13 != r0) goto Lb2
            return r0
        La9:
            r14.cleanPrev()
            kotlinx.coroutines.channels.ChannelResult$Companion r13 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Object r13 = r13.m2350successJP2dKIU(r1)
        Lb2:
            return r13
        Lb3:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "unexpected"
            java.lang.String r14 = r14.toString()
            r13.<init>(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.k(kotlinx.coroutines.channels.BufferedChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final void loop$atomicfu(AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Unit> function1, Object obj) {
        while (true) {
            function1.invoke(Long.valueOf(atomicLongFieldUpdater.get(obj)));
        }
    }

    static /* synthetic */ Object m(BufferedChannel bufferedChannel, Object obj, Continuation continuation) {
        ChannelSegment<E> channelSegment = (ChannelSegment) sendSegment$FU.get(bufferedChannel);
        while (true) {
            long andIncrement = sendersAndCloseStatus$FU.getAndIncrement(bufferedChannel);
            long j2 = 1152921504606846975L & andIncrement;
            boolean zIsClosedForSend0 = bufferedChannel.isClosedForSend0(andIncrement);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j3 = j2 / i2;
            int i3 = (int) (j2 % i2);
            if (channelSegment.id != j3) {
                ChannelSegment<E> channelSegmentFindSegmentSend = bufferedChannel.findSegmentSend(j3, channelSegment);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (zIsClosedForSend0) {
                    Object objOnClosedSend = bufferedChannel.onClosedSend(obj, continuation);
                    if (objOnClosedSend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objOnClosedSend;
                    }
                }
            }
            int iUpdateCellSend = bufferedChannel.updateCellSend(channelSegment, i3, obj, j2, null, zIsClosedForSend0);
            if (iUpdateCellSend == 0) {
                channelSegment.cleanPrev();
                break;
            }
            if (iUpdateCellSend == 1) {
                break;
            }
            if (iUpdateCellSend != 2) {
                if (iUpdateCellSend == 3) {
                    Object objSendOnNoWaiterSuspend = bufferedChannel.sendOnNoWaiterSuspend(channelSegment, i3, obj, j2, continuation);
                    if (objSendOnNoWaiterSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objSendOnNoWaiterSuspend;
                    }
                } else if (iUpdateCellSend == 4) {
                    if (j2 < bufferedChannel.getReceiversCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                    Object objOnClosedSend2 = bufferedChannel.onClosedSend(obj, continuation);
                    if (objOnClosedSend2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        return objOnClosedSend2;
                    }
                } else if (iUpdateCellSend == 5) {
                    channelSegment.cleanPrev();
                }
            } else if (zIsClosedForSend0) {
                channelSegment.onSlotCleaned();
                Object objOnClosedSend3 = bufferedChannel.onClosedSend(obj, continuation);
                if (objOnClosedSend3 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    return objOnClosedSend3;
                }
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003c, code lost:
    
        r8 = (kotlinx.coroutines.channels.ChannelSegment) r8.getPrev();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final long markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment<E> r8) {
        /*
            r7 = this;
        L0:
            int r0 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            int r0 = r0 + (-1)
        L4:
            r1 = -1
            r3 = -1
            if (r3 >= r0) goto L3c
            long r3 = r8.id
            int r5 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r5 = (long) r5
            long r3 = r3 * r5
            long r5 = (long) r0
            long r3 = r3 + r5
            long r5 = r7.getReceiversCounter$kotlinx_coroutines_core()
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 >= 0) goto L1a
            return r1
        L1a:
            java.lang.Object r1 = r8.getState$kotlinx_coroutines_core(r0)
            if (r1 == 0) goto L2c
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getIN_BUFFER$p()
            if (r1 != r2) goto L27
            goto L2c
        L27:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r1 != r2) goto L39
            return r3
        L2c:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r1 = r8.casState$kotlinx_coroutines_core(r0, r1, r2)
            if (r1 == 0) goto L1a
            r8.onSlotCleaned()
        L39:
            int r0 = r0 + (-1)
            goto L4
        L3c:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r8 = r8.getPrev()
            kotlinx.coroutines.channels.ChannelSegment r8 = (kotlinx.coroutines.channels.ChannelSegment) r8
            if (r8 != 0) goto L0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.markAllEmptyCellsAsClosed(kotlinx.coroutines.channels.ChannelSegment):long");
    }

    private final void markCancellationStarted() {
        long j2;
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        do {
            j2 = atomicLongFieldUpdater.get(this);
            if (((int) (j2 >> 60)) != 0) {
                return;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(this, j2, BufferedChannelKt.constructSendersAndCloseStatus(1152921504606846975L & j2, 1)));
    }

    private final void markCancelled() {
        long j2;
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        do {
            j2 = atomicLongFieldUpdater.get(this);
        } while (!atomicLongFieldUpdater.compareAndSet(this, j2, BufferedChannelKt.constructSendersAndCloseStatus(1152921504606846975L & j2, 3)));
    }

    private final void markClosed() {
        long j2;
        long jConstructSendersAndCloseStatus;
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        do {
            j2 = atomicLongFieldUpdater.get(this);
            int i2 = (int) (j2 >> 60);
            if (i2 == 0) {
                jConstructSendersAndCloseStatus = BufferedChannelKt.constructSendersAndCloseStatus(j2 & 1152921504606846975L, 2);
            } else if (i2 != 1) {
                return;
            } else {
                jConstructSendersAndCloseStatus = BufferedChannelKt.constructSendersAndCloseStatus(j2 & 1152921504606846975L, 3);
            }
        } while (!atomicLongFieldUpdater.compareAndSet(this, j2, jConstructSendersAndCloseStatus));
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0011, code lost:
    
        continue;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void moveSegmentBufferEndToSpecifiedOrLast(long r5, kotlinx.coroutines.channels.ChannelSegment<E> r7) {
        /*
            r4 = this;
        L0:
            long r0 = r7.id
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto L11
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r0 = r7.getNext()
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
            if (r0 != 0) goto Lf
            goto L11
        Lf:
            r7 = r0
            goto L0
        L11:
            boolean r5 = r7.isRemoved()
            if (r5 == 0) goto L22
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r5 = r7.getNext()
            kotlinx.coroutines.channels.ChannelSegment r5 = (kotlinx.coroutines.channels.ChannelSegment) r5
            if (r5 != 0) goto L20
            goto L22
        L20:
            r7 = r5
            goto L11
        L22:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r5 = kotlinx.coroutines.channels.BufferedChannel.bufferEndSegment$FU
        L24:
            java.lang.Object r6 = r5.get(r4)
            kotlinx.coroutines.internal.Segment r6 = (kotlinx.coroutines.internal.Segment) r6
            long r0 = r6.id
            long r2 = r7.id
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L33
            goto L49
        L33:
            boolean r0 = r7.tryIncPointers$kotlinx_coroutines_core()
            if (r0 != 0) goto L3a
            goto L11
        L3a:
            boolean r0 = androidx.concurrent.futures.a.a(r5, r4, r6, r7)
            if (r0 == 0) goto L4a
            boolean r5 = r6.decPointers$kotlinx_coroutines_core()
            if (r5 == 0) goto L49
            r6.remove()
        L49:
            return
        L4a:
            boolean r6 = r7.decPointers$kotlinx_coroutines_core()
            if (r6 == 0) goto L24
            r7.remove()
            goto L24
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.moveSegmentBufferEndToSpecifiedOrLast(long, kotlinx.coroutines.channels.ChannelSegment):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00aa, code lost:
    
        r0 = kotlin.Result.INSTANCE;
        r9.resumeWith(kotlin.Result.m837constructorimpl(kotlin.coroutines.jvm.internal.Boxing.boxBoolean(true)));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ java.lang.Object n(kotlinx.coroutines.channels.BufferedChannel r18, java.lang.Object r19, kotlin.coroutines.Continuation r20) {
        /*
            r8 = r18
            kotlinx.coroutines.CancellableContinuationImpl r9 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r20)
            r10 = 1
            r9.<init>(r0, r10)
            r9.initCancellability()
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r0 = r8.onUndeliveredElement
            if (r0 != 0) goto Lcc
            kotlinx.coroutines.channels.BufferedChannel$SendBroadcast r11 = new kotlinx.coroutines.channels.BufferedChannel$SendBroadcast
            r11.<init>(r9)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = access$getSendSegment$FU$p()
            java.lang.Object r0 = r0.get(r8)
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
        L22:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r1 = access$getSendersAndCloseStatus$FU$p()
            long r1 = r1.getAndIncrement(r8)
            r3 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r12 = r1 & r3
            boolean r14 = access$isClosedForSend0(r8, r1)
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r2 = (long) r1
            long r2 = r12 / r2
            long r4 = (long) r1
            long r4 = r12 % r4
            int r15 = (int) r4
            long r4 = r0.id
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            r16 = 0
            if (r1 == 0) goto L5f
            kotlinx.coroutines.channels.ChannelSegment r1 = access$findSegmentSend(r8, r2, r0)
            if (r1 != 0) goto L5d
            if (r14 == 0) goto L22
        L4e:
            kotlin.Result$Companion r0 = kotlin.Result.INSTANCE
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r16)
            java.lang.Object r0 = kotlin.Result.m837constructorimpl(r0)
            r9.resumeWith(r0)
            goto Lbe
        L5d:
            r7 = r1
            goto L60
        L5f:
            r7 = r0
        L60:
            r0 = r18
            r1 = r7
            r2 = r15
            r3 = r19
            r4 = r12
            r6 = r11
            r17 = r7
            r7 = r14
            int r0 = access$updateCellSend(r0, r1, r2, r3, r4, r6, r7)
            if (r0 == 0) goto Lb8
            if (r0 == r10) goto Laa
            r1 = 2
            if (r0 == r1) goto L9e
            r1 = 3
            if (r0 == r1) goto L92
            r1 = 4
            if (r0 == r1) goto L86
            r1 = 5
            if (r0 == r1) goto L80
            goto L83
        L80:
            r17.cleanPrev()
        L83:
            r0 = r17
            goto L22
        L86:
            long r0 = r18.getReceiversCounter$kotlinx_coroutines_core()
            int r0 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r0 >= 0) goto L4e
            r17.cleanPrev()
            goto L4e
        L92:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "unexpected"
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L9e:
            if (r14 == 0) goto La4
            r17.onSlotCleaned()
            goto L4e
        La4:
            r0 = r17
            access$prepareSenderForSuspension(r8, r11, r0, r15)
            goto Lbe
        Laa:
            kotlin.Result$Companion r0 = kotlin.Result.INSTANCE
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r10)
            java.lang.Object r0 = kotlin.Result.m837constructorimpl(r0)
            r9.resumeWith(r0)
            goto Lbe
        Lb8:
            r0 = r17
            r0.cleanPrev()
            goto Laa
        Lbe:
            java.lang.Object r0 = r9.getResult()
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r1) goto Lcb
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r20)
        Lcb:
            return r0
        Lcc:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "the `onUndeliveredElement` feature is unsupported for `sendBroadcast(e)`"
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.n(kotlinx.coroutines.channels.BufferedChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedReceiveCatchingOnNoWaiterSuspend(CancellableContinuation<? super ChannelResult<? extends E>> cont) {
        Result.Companion companion = Result.INSTANCE;
        cont.resumeWith(Result.m837constructorimpl(ChannelResult.m2335boximpl(ChannelResult.INSTANCE.m2348closedJP2dKIU(c()))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedReceiveOnNoWaiterSuspend(CancellableContinuation<? super E> cont) {
        Result.Companion companion = Result.INSTANCE;
        cont.resumeWith(Result.m837constructorimpl(ResultKt.createFailure(getReceiveException())));
    }

    private final void onClosedSelectOnReceive(SelectInstance<?> select) {
        select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
    }

    private final void onClosedSelectOnSend(E element, SelectInstance<?> select) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, element, select.getContext());
        }
        select.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object onClosedSend(E e2, Continuation<? super Unit> continuation) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 == null || (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, e2, null, 2, null)) == null) {
            Throwable thD = d();
            Result.Companion companion = Result.INSTANCE;
            cancellableContinuationImpl.resumeWith(Result.m837constructorimpl(ResultKt.createFailure(thD)));
        } else {
            ExceptionsKt.addSuppressed(undeliveredElementExceptionCallUndeliveredElementCatchingException$default, d());
            Result.Companion companion2 = Result.INSTANCE;
            cancellableContinuationImpl.resumeWith(Result.m837constructorimpl(ResultKt.createFailure(undeliveredElementExceptionCallUndeliveredElementCatchingException$default)));
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClosedSendOnNoWaiterSuspend(E element, CancellableContinuation<? super Unit> cont) {
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null) {
            OnUndeliveredElementKt.callUndeliveredElement(function1, element, cont.getContext());
        }
        Throwable thD = d();
        Result.Companion companion = Result.INSTANCE;
        cont.resumeWith(Result.m837constructorimpl(ResultKt.createFailure(thD)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prepareReceiverForSuspension(Waiter waiter, ChannelSegment<E> channelSegment, int i2) {
        i();
        waiter.invokeOnCancellation(channelSegment, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void prepareSenderForSuspension(Waiter waiter, ChannelSegment<E> channelSegment, int i2) {
        waiter.invokeOnCancellation(channelSegment, i2 + BufferedChannelKt.SEGMENT_SIZE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceive(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return selectResult;
        }
        throw getReceiveException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceiveCatching(Object ignoredParam, Object selectResult) {
        return ChannelResult.m2335boximpl(selectResult == BufferedChannelKt.getCHANNEL_CLOSED() ? ChannelResult.INSTANCE.m2348closedJP2dKIU(c()) : ChannelResult.INSTANCE.m2350successJP2dKIU(selectResult));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectReceiveOrNull(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return selectResult;
        }
        if (c() == null) {
            return null;
        }
        throw getReceiveException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object processResultSelectSend(Object ignoredParam, Object selectResult) throws Throwable {
        if (selectResult != BufferedChannelKt.getCHANNEL_CLOSED()) {
            return this;
        }
        throw d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: receiveCatchingOnNoWaiterSuspend-GKJJFZk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m2328receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment<E> r11, int r12, long r13, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ChannelResult<? extends E>> r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.m2328receiveCatchingOnNoWaiterSuspendGKJJFZk(kotlinx.coroutines.channels.ChannelSegment, int, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final <R> R receiveImpl(Object waiter, Function1<? super E, ? extends R> onElementRetrieved, Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> onSuspend, Function0<? extends R> onClosed, Function3<? super ChannelSegment<E>, ? super Integer, ? super Long, ? extends R> onNoWaiterSuspend) {
        ChannelSegment channelSegment = (ChannelSegment) receiveSegment$FU.get(this);
        while (!isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(this);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = andIncrement / i2;
            int i3 = (int) (andIncrement % i2);
            if (channelSegment.id != j2) {
                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j2, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            }
            Symbol symbol = (Object) updateCellReceive(channelSegment, i3, andIncrement, waiter);
            if (symbol == BufferedChannelKt.SUSPEND) {
                Waiter waiter2 = waiter instanceof Waiter ? (Waiter) waiter : null;
                if (waiter2 != null) {
                    prepareReceiverForSuspension(waiter2, channelSegment, i3);
                }
                return onSuspend.invoke(channelSegment, Integer.valueOf(i3), Long.valueOf(andIncrement));
            }
            if (symbol != BufferedChannelKt.FAILED) {
                if (symbol == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    return onNoWaiterSuspend.invoke(channelSegment, Integer.valueOf(i3), Long.valueOf(andIncrement));
                }
                channelSegment.cleanPrev();
                return onElementRetrieved.invoke(symbol);
            }
            if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                channelSegment.cleanPrev();
            }
        }
        return onClosed.invoke();
    }

    private final void receiveImplOnNoWaiter(ChannelSegment<E> segment, int index, long r2, Waiter waiter, Function1<? super E, Unit> onElementRetrieved, Function0<Unit> onClosed) {
        Symbol symbol = (Object) updateCellReceive(segment, index, r2, waiter);
        if (symbol == BufferedChannelKt.SUSPEND) {
            prepareReceiverForSuspension(waiter, segment, index);
            return;
        }
        if (symbol != BufferedChannelKt.FAILED) {
            segment.cleanPrev();
            onElementRetrieved.invoke(symbol);
            return;
        }
        if (r2 < getSendersCounter$kotlinx_coroutines_core()) {
            segment.cleanPrev();
        }
        ChannelSegment channelSegment = (ChannelSegment) receiveSegment$FU.get(this);
        while (!isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(this);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = andIncrement / i2;
            int i3 = (int) (andIncrement % i2);
            if (channelSegment.id != j2) {
                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j2, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            }
            Symbol symbol2 = (Object) updateCellReceive(channelSegment, i3, andIncrement, waiter);
            if (symbol2 == BufferedChannelKt.SUSPEND) {
                if (!(waiter instanceof Waiter)) {
                    waiter = null;
                }
                if (waiter != null) {
                    prepareReceiverForSuspension(waiter, channelSegment, i3);
                }
                Unit unit = Unit.INSTANCE;
                return;
            }
            if (symbol2 != BufferedChannelKt.FAILED) {
                if (symbol2 == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    throw new IllegalStateException("unexpected".toString());
                }
                channelSegment.cleanPrev();
                onElementRetrieved.invoke(symbol2);
                return;
            }
            if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                channelSegment.cleanPrev();
            }
        }
        onClosed.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object receiveOnNoWaiterSuspend(ChannelSegment<E> channelSegment, int i2, long j2, Continuation<? super E> continuation) {
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        try {
            Object objUpdateCellReceive = updateCellReceive(channelSegment, i2, j2, orCreateCancellableContinuation);
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                prepareReceiverForSuspension(orCreateCancellableContinuation, channelSegment, i2);
            } else {
                Function1<Throwable, Unit> function1BindCancellationFun = null;
                function1BindCancellationFun = null;
                if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                    if (j2 < getSendersCounter$kotlinx_coroutines_core()) {
                        channelSegment.cleanPrev();
                    }
                    ChannelSegment channelSegment2 = (ChannelSegment) receiveSegment$FU.get(this);
                    while (true) {
                        if (isClosedForReceive()) {
                            onClosedReceiveOnNoWaiterSuspend(orCreateCancellableContinuation);
                            break;
                        }
                        long andIncrement = receivers$FU.getAndIncrement(this);
                        int i3 = BufferedChannelKt.SEGMENT_SIZE;
                        long j3 = andIncrement / i3;
                        int i4 = (int) (andIncrement % i3);
                        if (channelSegment2.id != j3) {
                            ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j3, channelSegment2);
                            if (channelSegmentFindSegmentReceive != null) {
                                channelSegment2 = channelSegmentFindSegmentReceive;
                            }
                        }
                        objUpdateCellReceive = updateCellReceive(channelSegment2, i4, andIncrement, orCreateCancellableContinuation);
                        if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                            CancellableContinuationImpl cancellableContinuationImpl = orCreateCancellableContinuation instanceof Waiter ? orCreateCancellableContinuation : null;
                            if (cancellableContinuationImpl != null) {
                                prepareReceiverForSuspension(cancellableContinuationImpl, channelSegment2, i4);
                            }
                        } else if (objUpdateCellReceive == BufferedChannelKt.FAILED) {
                            if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                                channelSegment2.cleanPrev();
                            }
                        } else {
                            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                                throw new IllegalStateException("unexpected".toString());
                            }
                            channelSegment2.cleanPrev();
                            Function1<E, Unit> function1 = this.onUndeliveredElement;
                            if (function1 != null) {
                                function1BindCancellationFun = OnUndeliveredElementKt.bindCancellationFun(function1, objUpdateCellReceive, orCreateCancellableContinuation.getContext());
                            }
                        }
                    }
                } else {
                    channelSegment.cleanPrev();
                    Function1<E, Unit> function12 = this.onUndeliveredElement;
                    if (function12 != null) {
                        function1BindCancellationFun = OnUndeliveredElementKt.bindCancellationFun(function12, objUpdateCellReceive, orCreateCancellableContinuation.getContext());
                    }
                }
                orCreateCancellableContinuation.resume(objUpdateCellReceive, function1BindCancellationFun);
            }
            Object result = orCreateCancellableContinuation.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(continuation);
            }
            return result;
        } catch (Throwable th) {
            orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void registerSelectForReceive(SelectInstance<?> select, Object ignoredParam) {
        ChannelSegment channelSegment = (ChannelSegment) receiveSegment$FU.get(this);
        while (!isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(this);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = andIncrement / i2;
            int i3 = (int) (andIncrement % i2);
            if (channelSegment.id != j2) {
                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j2, channelSegment);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            }
            Object objUpdateCellReceive = updateCellReceive(channelSegment, i3, andIncrement, select);
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                Waiter waiter = select instanceof Waiter ? (Waiter) select : null;
                if (waiter != null) {
                    prepareReceiverForSuspension(waiter, channelSegment, i3);
                    return;
                }
                return;
            }
            if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    throw new IllegalStateException("unexpected".toString());
                }
                channelSegment.cleanPrev();
                select.selectInRegistrationPhase(objUpdateCellReceive);
                return;
            }
            if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                channelSegment.cleanPrev();
            }
        }
        onClosedSelectOnReceive(select);
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b3, code lost:
    
        r12 = (kotlinx.coroutines.channels.ChannelSegment) r12.getPrev();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment<E> r12) {
        /*
            r11 = this;
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r0 = r11.onUndeliveredElement
            r1 = 0
            r2 = 1
            java.lang.Object r3 = kotlinx.coroutines.internal.InlineList.m2371constructorimpl$default(r1, r2, r1)
        L8:
            int r4 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            int r4 = r4 - r2
        Lb:
            r5 = -1
            if (r5 >= r4) goto Lb3
            long r6 = r12.id
            int r8 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r8 = (long) r8
            long r6 = r6 * r8
            long r8 = (long) r4
            long r6 = r6 + r8
        L16:
            java.lang.Object r8 = r12.getState$kotlinx_coroutines_core(r4)
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.channels.BufferedChannelKt.access$getDONE_RCV$p()
            if (r8 == r9) goto Lbb
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r8 != r9) goto L48
            long r9 = r11.getReceiversCounter$kotlinx_coroutines_core()
            int r9 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r9 < 0) goto Lbb
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r8 = r12.casState$kotlinx_coroutines_core(r4, r8, r9)
            if (r8 == 0) goto L16
            if (r0 == 0) goto L40
            java.lang.Object r5 = r12.getElement$kotlinx_coroutines_core(r4)
            kotlinx.coroutines.internal.UndeliveredElementException r1 = kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElementCatchingException(r0, r5, r1)
        L40:
            r12.cleanElement$kotlinx_coroutines_core(r4)
            r12.onSlotCleaned()
            goto Laf
        L48:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.channels.BufferedChannelKt.access$getIN_BUFFER$p()
            if (r8 == r9) goto La2
            if (r8 != 0) goto L51
            goto La2
        L51:
            boolean r9 = r8 instanceof kotlinx.coroutines.Waiter
            if (r9 != 0) goto L6e
            boolean r9 = r8 instanceof kotlinx.coroutines.channels.WaiterEB
            if (r9 == 0) goto L5a
            goto L6e
        L5a:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.channels.BufferedChannelKt.access$getRESUMING_BY_EB$p()
            if (r8 == r9) goto Lbb
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.channels.BufferedChannelKt.access$getRESUMING_BY_RCV$p()
            if (r8 != r9) goto L67
            goto Lbb
        L67:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.channels.BufferedChannelKt.access$getRESUMING_BY_EB$p()
            if (r8 == r9) goto L16
            goto Laf
        L6e:
            long r9 = r11.getReceiversCounter$kotlinx_coroutines_core()
            int r9 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r9 < 0) goto Lbb
            boolean r9 = r8 instanceof kotlinx.coroutines.channels.WaiterEB
            if (r9 == 0) goto L80
            r9 = r8
            kotlinx.coroutines.channels.WaiterEB r9 = (kotlinx.coroutines.channels.WaiterEB) r9
            kotlinx.coroutines.Waiter r9 = r9.waiter
            goto L83
        L80:
            r9 = r8
            kotlinx.coroutines.Waiter r9 = (kotlinx.coroutines.Waiter) r9
        L83:
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r8 = r12.casState$kotlinx_coroutines_core(r4, r8, r10)
            if (r8 == 0) goto L16
            if (r0 == 0) goto L97
            java.lang.Object r5 = r12.getElement$kotlinx_coroutines_core(r4)
            kotlinx.coroutines.internal.UndeliveredElementException r1 = kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElementCatchingException(r0, r5, r1)
        L97:
            java.lang.Object r3 = kotlinx.coroutines.internal.InlineList.m2376plusFjFbRPM(r3, r9)
            r12.cleanElement$kotlinx_coroutines_core(r4)
            r12.onSlotCleaned()
            goto Laf
        La2:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            boolean r8 = r12.casState$kotlinx_coroutines_core(r4, r8, r9)
            if (r8 == 0) goto L16
            r12.onSlotCleaned()
        Laf:
            int r4 = r4 + (-1)
            goto Lb
        Lb3:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r12 = r12.getPrev()
            kotlinx.coroutines.channels.ChannelSegment r12 = (kotlinx.coroutines.channels.ChannelSegment) r12
            if (r12 != 0) goto L8
        Lbb:
            if (r3 == 0) goto Le1
            boolean r12 = r3 instanceof java.util.ArrayList
            if (r12 != 0) goto Lc7
            kotlinx.coroutines.Waiter r3 = (kotlinx.coroutines.Waiter) r3
            r11.resumeSenderOnCancelledChannel(r3)
            goto Le1
        Lc7:
            java.lang.String r12 = "null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3, r12)
            java.util.ArrayList r3 = (java.util.ArrayList) r3
            int r12 = r3.size()
            int r12 = r12 - r2
        Ld3:
            if (r5 >= r12) goto Le1
            java.lang.Object r0 = r3.get(r12)
            kotlinx.coroutines.Waiter r0 = (kotlinx.coroutines.Waiter) r0
            r11.resumeSenderOnCancelledChannel(r0)
            int r12 = r12 + (-1)
            goto Ld3
        Le1:
            if (r1 != 0) goto Le4
            return
        Le4:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.removeUnprocessedElements(kotlinx.coroutines.channels.ChannelSegment):void");
    }

    private final void resumeReceiverOnClosedChannel(Waiter waiter) {
        resumeWaiterOnClosedChannel(waiter, true);
    }

    private final void resumeSenderOnCancelledChannel(Waiter waiter) {
        resumeWaiterOnClosedChannel(waiter, false);
    }

    private final void resumeWaiterOnClosedChannel(Waiter waiter, boolean z2) {
        if (waiter instanceof SendBroadcast) {
            CancellableContinuation<Boolean> cont = ((SendBroadcast) waiter).getCont();
            Result.Companion companion = Result.INSTANCE;
            cont.resumeWith(Result.m837constructorimpl(Boolean.FALSE));
            return;
        }
        if (waiter instanceof CancellableContinuation) {
            Continuation continuation = (Continuation) waiter;
            Result.Companion companion2 = Result.INSTANCE;
            continuation.resumeWith(Result.m837constructorimpl(ResultKt.createFailure(z2 ? getReceiveException() : d())));
        } else if (waiter instanceof ReceiveCatching) {
            CancellableContinuationImpl<ChannelResult<? extends E>> cancellableContinuationImpl = ((ReceiveCatching) waiter).cont;
            Result.Companion companion3 = Result.INSTANCE;
            cancellableContinuationImpl.resumeWith(Result.m837constructorimpl(ChannelResult.m2335boximpl(ChannelResult.INSTANCE.m2348closedJP2dKIU(c()))));
        } else if (waiter instanceof BufferedChannelIterator) {
            ((BufferedChannelIterator) waiter).tryResumeHasNextOnClosedChannel();
        } else {
            if (waiter instanceof SelectInstance) {
                ((SelectInstance) waiter).trySelect(this, BufferedChannelKt.getCHANNEL_CLOSED());
                return;
            }
            throw new IllegalStateException(("Unexpected waiter: " + waiter).toString());
        }
    }

    public static /* synthetic */ Object sendImpl$default(BufferedChannel bufferedChannel, Object obj, Object obj2, Function0 function0, Function2 function2, Function0 function02, Function4 function4, int i2, Object obj3) {
        ChannelSegment channelSegment;
        if (obj3 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendImpl");
        }
        Function4 function42 = (i2 & 32) != 0 ? new Function4() { // from class: kotlinx.coroutines.channels.BufferedChannel$sendImpl$1
            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Object invoke(Object obj4, Object obj5, Object obj6, Object obj7) {
                return invoke((ChannelSegment<int>) obj4, ((Number) obj5).intValue(), (int) obj6, ((Number) obj7).longValue());
            }

            @NotNull
            public final Void invoke(@NotNull ChannelSegment<E> channelSegment2, int i3, E e2, long j2) {
                throw new IllegalStateException("unexpected".toString());
            }
        } : function4;
        ChannelSegment channelSegment2 = (ChannelSegment) sendSegment$FU.get(bufferedChannel);
        while (true) {
            long andIncrement = sendersAndCloseStatus$FU.getAndIncrement(bufferedChannel);
            long j2 = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = bufferedChannel.isClosedForSend0(andIncrement);
            int i3 = BufferedChannelKt.SEGMENT_SIZE;
            long j3 = j2 / i3;
            int i4 = (int) (j2 % i3);
            if (channelSegment2.id != j3) {
                ChannelSegment channelSegmentFindSegmentSend = bufferedChannel.findSegmentSend(j3, channelSegment2);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (zIsClosedForSend0) {
                    return function02.invoke();
                }
            } else {
                channelSegment = channelSegment2;
            }
            int iUpdateCellSend = bufferedChannel.updateCellSend(channelSegment, i4, obj, j2, obj2, zIsClosedForSend0);
            if (iUpdateCellSend == 0) {
                channelSegment.cleanPrev();
                return function0.invoke();
            }
            if (iUpdateCellSend == 1) {
                return function0.invoke();
            }
            if (iUpdateCellSend == 2) {
                if (zIsClosedForSend0) {
                    channelSegment.onSlotCleaned();
                    return function02.invoke();
                }
                Waiter waiter = obj2 instanceof Waiter ? (Waiter) obj2 : null;
                if (waiter != null) {
                    bufferedChannel.prepareSenderForSuspension(waiter, channelSegment, i4);
                }
                return function2.mo1invoke(channelSegment, Integer.valueOf(i4));
            }
            if (iUpdateCellSend == 3) {
                return function42.invoke(channelSegment, Integer.valueOf(i4), obj, Long.valueOf(j2));
            }
            if (iUpdateCellSend == 4) {
                if (j2 < bufferedChannel.getReceiversCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
                return function02.invoke();
            }
            if (iUpdateCellSend == 5) {
                channelSegment.cleanPrev();
            }
            channelSegment2 = channelSegment;
        }
    }

    private final void sendImplOnNoWaiter(ChannelSegment<E> segment, int index, E element, long s2, Waiter waiter, Function0<Unit> onRendezvousOrBuffered, Function0<Unit> onClosed) {
        ChannelSegment channelSegment;
        Waiter waiter2 = waiter;
        int iUpdateCellSend = updateCellSend(segment, index, element, s2, waiter, false);
        if (iUpdateCellSend == 0) {
            segment.cleanPrev();
            onRendezvousOrBuffered.invoke();
            return;
        }
        if (iUpdateCellSend == 1) {
            onRendezvousOrBuffered.invoke();
            return;
        }
        if (iUpdateCellSend == 2) {
            prepareSenderForSuspension(waiter2, segment, index);
            return;
        }
        if (iUpdateCellSend == 4) {
            if (s2 < getReceiversCounter$kotlinx_coroutines_core()) {
                segment.cleanPrev();
            }
            onClosed.invoke();
            return;
        }
        if (iUpdateCellSend != 5) {
            throw new IllegalStateException("unexpected".toString());
        }
        segment.cleanPrev();
        ChannelSegment channelSegment2 = (ChannelSegment) sendSegment$FU.get(this);
        while (true) {
            long andIncrement = sendersAndCloseStatus$FU.getAndIncrement(this);
            long j2 = andIncrement & 1152921504606846975L;
            boolean zIsClosedForSend0 = isClosedForSend0(andIncrement);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j3 = j2 / i2;
            int i3 = (int) (j2 % i2);
            if (channelSegment2.id != j3) {
                ChannelSegment channelSegmentFindSegmentSend = findSegmentSend(j3, channelSegment2);
                if (channelSegmentFindSegmentSend != null) {
                    channelSegment = channelSegmentFindSegmentSend;
                } else if (zIsClosedForSend0) {
                    onClosed.invoke();
                    return;
                }
            } else {
                channelSegment = channelSegment2;
            }
            ChannelSegment channelSegment3 = channelSegment;
            int iUpdateCellSend2 = updateCellSend(channelSegment, i3, element, j2, waiter, zIsClosedForSend0);
            if (iUpdateCellSend2 == 0) {
                channelSegment3.cleanPrev();
                onRendezvousOrBuffered.invoke();
                return;
            }
            if (iUpdateCellSend2 == 1) {
                onRendezvousOrBuffered.invoke();
                return;
            }
            if (iUpdateCellSend2 == 2) {
                if (zIsClosedForSend0) {
                    channelSegment3.onSlotCleaned();
                    onClosed.invoke();
                    return;
                }
                if (!(waiter2 instanceof Waiter)) {
                    waiter2 = null;
                }
                if (waiter2 != null) {
                    prepareSenderForSuspension(waiter2, channelSegment3, i3);
                }
                Unit unit = Unit.INSTANCE;
                return;
            }
            if (iUpdateCellSend2 == 3) {
                throw new IllegalStateException("unexpected".toString());
            }
            if (iUpdateCellSend2 == 4) {
                if (j2 < getReceiversCounter$kotlinx_coroutines_core()) {
                    channelSegment3.cleanPrev();
                }
                onClosed.invoke();
                return;
            } else {
                if (iUpdateCellSend2 == 5) {
                    channelSegment3.cleanPrev();
                }
                channelSegment2 = channelSegment3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:62:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0125 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0126  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object sendOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment<E> r21, int r22, E r23, long r24, kotlin.coroutines.Continuation<? super kotlin.Unit> r26) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.sendOnNoWaiterSuspend(kotlinx.coroutines.channels.ChannelSegment, int, java.lang.Object, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final boolean shouldSendSuspend(long curSendersAndCloseStatus) {
        if (isClosedForSend0(curSendersAndCloseStatus)) {
            return false;
        }
        return !bufferOrRendezvousSend(curSendersAndCloseStatus & 1152921504606846975L);
    }

    private final boolean tryResumeReceiver(Object obj, E e2) {
        if (obj instanceof SelectInstance) {
            return ((SelectInstance) obj).trySelect(this, e2);
        }
        if (obj instanceof ReceiveCatching) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveCatching<E of kotlinx.coroutines.channels.BufferedChannel>");
            ReceiveCatching receiveCatching = (ReceiveCatching) obj;
            CancellableContinuationImpl<ChannelResult<? extends E>> cancellableContinuationImpl = receiveCatching.cont;
            ChannelResult channelResultM2335boximpl = ChannelResult.m2335boximpl(ChannelResult.INSTANCE.m2350successJP2dKIU(e2));
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuationImpl, channelResultM2335boximpl, function1 != null ? OnUndeliveredElementKt.bindCancellationFun(function1, e2, receiveCatching.cont.getContext()) : null);
        }
        if (obj instanceof BufferedChannelIterator) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator<E of kotlinx.coroutines.channels.BufferedChannel>");
            return ((BufferedChannelIterator) obj).tryResumeHasNext(e2);
        }
        if (obj instanceof CancellableContinuation) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<E of kotlinx.coroutines.channels.BufferedChannel>");
            CancellableContinuation cancellableContinuation = (CancellableContinuation) obj;
            Function1<E, Unit> function12 = this.onUndeliveredElement;
            return BufferedChannelKt.tryResume0(cancellableContinuation, e2, function12 != null ? OnUndeliveredElementKt.bindCancellationFun(function12, e2, cancellableContinuation.getContext()) : null);
        }
        throw new IllegalStateException(("Unexpected receiver type: " + obj).toString());
    }

    private final boolean tryResumeSender(Object obj, ChannelSegment<E> channelSegment, int i2) {
        if (obj instanceof CancellableContinuation) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<kotlin.Unit>");
            return BufferedChannelKt.a((CancellableContinuation) obj, Unit.INSTANCE, null, 2, null);
        }
        if (obj instanceof SelectInstance) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.selects.SelectImplementation<*>");
            TrySelectDetailedResult trySelectDetailedResultTrySelectDetailed = ((SelectImplementation) obj).trySelectDetailed(this, Unit.INSTANCE);
            if (trySelectDetailedResultTrySelectDetailed == TrySelectDetailedResult.REREGISTER) {
                channelSegment.cleanElement$kotlinx_coroutines_core(i2);
            }
            return trySelectDetailedResultTrySelectDetailed == TrySelectDetailedResult.SUCCESSFUL;
        }
        if (obj instanceof SendBroadcast) {
            return BufferedChannelKt.a(((SendBroadcast) obj).getCont(), Boolean.TRUE, null, 2, null);
        }
        throw new IllegalStateException(("Unexpected waiter: " + obj).toString());
    }

    private final void update$atomicfu(AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Long> function1, Object obj) {
        long j2;
        do {
            j2 = atomicLongFieldUpdater.get(obj);
        } while (!atomicLongFieldUpdater.compareAndSet(obj, j2, function1.invoke(Long.valueOf(j2)).longValue()));
    }

    private final boolean updateCellExpandBuffer(ChannelSegment<E> segment, int index, long b2) {
        Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
        if (!(state$kotlinx_coroutines_core instanceof Waiter) || b2 < receivers$FU.get(this) || !segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.RESUMING_BY_EB)) {
            return updateCellExpandBufferSlow(segment, index, b2);
        }
        if (tryResumeSender(state$kotlinx_coroutines_core, segment, index)) {
            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.BUFFERED);
            return true;
        }
        segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
        segment.onCancelledRequest(index, false);
        return false;
    }

    private final boolean updateCellExpandBufferSlow(ChannelSegment<E> segment, int index, long b2) {
        while (true) {
            Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
            if (state$kotlinx_coroutines_core instanceof Waiter) {
                if (b2 < receivers$FU.get(this)) {
                    if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, new WaiterEB((Waiter) state$kotlinx_coroutines_core))) {
                        return true;
                    }
                } else if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.RESUMING_BY_EB)) {
                    if (tryResumeSender(state$kotlinx_coroutines_core, segment, index)) {
                        segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.BUFFERED);
                        return true;
                    }
                    segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                    segment.onCancelledRequest(index, false);
                    return false;
                }
            } else {
                if (state$kotlinx_coroutines_core == BufferedChannelKt.INTERRUPTED_SEND) {
                    return false;
                }
                if (state$kotlinx_coroutines_core == null) {
                    if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.IN_BUFFER)) {
                        return true;
                    }
                } else {
                    if (state$kotlinx_coroutines_core == BufferedChannelKt.BUFFERED || state$kotlinx_coroutines_core == BufferedChannelKt.POISONED || state$kotlinx_coroutines_core == BufferedChannelKt.DONE_RCV || state$kotlinx_coroutines_core == BufferedChannelKt.INTERRUPTED_RCV || state$kotlinx_coroutines_core == BufferedChannelKt.getCHANNEL_CLOSED()) {
                        return true;
                    }
                    if (state$kotlinx_coroutines_core != BufferedChannelKt.RESUMING_BY_RCV) {
                        throw new IllegalStateException(("Unexpected cell state: " + state$kotlinx_coroutines_core).toString());
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object updateCellReceive(ChannelSegment<E> segment, int index, long r2, Object waiter) {
        Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
        if (state$kotlinx_coroutines_core == null) {
            if (r2 >= (sendersAndCloseStatus$FU.get(this) & 1152921504606846975L)) {
                if (waiter == null) {
                    return BufferedChannelKt.SUSPEND_NO_WAITER;
                }
                if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, waiter)) {
                    expandBuffer();
                    return BufferedChannelKt.SUSPEND;
                }
            }
        } else if (state$kotlinx_coroutines_core == BufferedChannelKt.BUFFERED && segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.DONE_RCV)) {
            expandBuffer();
            return segment.retrieveElement$kotlinx_coroutines_core(index);
        }
        return updateCellReceiveSlow(segment, index, r2, waiter);
    }

    private final Object updateCellReceiveSlow(ChannelSegment<E> segment, int index, long r2, Object waiter) {
        while (true) {
            Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
            if (state$kotlinx_coroutines_core == null || state$kotlinx_coroutines_core == BufferedChannelKt.IN_BUFFER) {
                if (r2 < (sendersAndCloseStatus$FU.get(this) & 1152921504606846975L)) {
                    if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.POISONED)) {
                        expandBuffer();
                        return BufferedChannelKt.FAILED;
                    }
                } else {
                    if (waiter == null) {
                        return BufferedChannelKt.SUSPEND_NO_WAITER;
                    }
                    if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, waiter)) {
                        expandBuffer();
                        return BufferedChannelKt.SUSPEND;
                    }
                }
            } else {
                if (state$kotlinx_coroutines_core != BufferedChannelKt.BUFFERED) {
                    if (state$kotlinx_coroutines_core != BufferedChannelKt.INTERRUPTED_SEND && state$kotlinx_coroutines_core != BufferedChannelKt.POISONED) {
                        if (state$kotlinx_coroutines_core == BufferedChannelKt.getCHANNEL_CLOSED()) {
                            expandBuffer();
                            return BufferedChannelKt.FAILED;
                        }
                        if (state$kotlinx_coroutines_core != BufferedChannelKt.RESUMING_BY_EB && segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.RESUMING_BY_RCV)) {
                            boolean z2 = state$kotlinx_coroutines_core instanceof WaiterEB;
                            if (z2) {
                                state$kotlinx_coroutines_core = ((WaiterEB) state$kotlinx_coroutines_core).waiter;
                            }
                            if (tryResumeSender(state$kotlinx_coroutines_core, segment, index)) {
                                segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                                expandBuffer();
                                return segment.retrieveElement$kotlinx_coroutines_core(index);
                            }
                            segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_SEND);
                            segment.onCancelledRequest(index, false);
                            if (z2) {
                                expandBuffer();
                            }
                            return BufferedChannelKt.FAILED;
                        }
                    }
                    return BufferedChannelKt.FAILED;
                }
                if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.DONE_RCV)) {
                    expandBuffer();
                    return segment.retrieveElement$kotlinx_coroutines_core(index);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int updateCellSend(ChannelSegment<E> segment, int index, E element, long s2, Object waiter, boolean closed) {
        segment.storeElement$kotlinx_coroutines_core(index, element);
        if (closed) {
            return updateCellSendSlow(segment, index, element, s2, waiter, closed);
        }
        Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
        if (state$kotlinx_coroutines_core == null) {
            if (bufferOrRendezvousSend(s2)) {
                if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else {
                if (waiter == null) {
                    return 3;
                }
                if (segment.casState$kotlinx_coroutines_core(index, null, waiter)) {
                    return 2;
                }
            }
        } else if (state$kotlinx_coroutines_core instanceof Waiter) {
            segment.cleanElement$kotlinx_coroutines_core(index);
            if (tryResumeReceiver(state$kotlinx_coroutines_core, element)) {
                segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                h();
                return 0;
            }
            if (segment.getAndSetState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                segment.onCancelledRequest(index, true);
            }
            return 5;
        }
        return updateCellSendSlow(segment, index, element, s2, waiter, closed);
    }

    private final int updateCellSendSlow(ChannelSegment<E> segment, int index, E element, long s2, Object waiter, boolean closed) {
        while (true) {
            Object state$kotlinx_coroutines_core = segment.getState$kotlinx_coroutines_core(index);
            if (state$kotlinx_coroutines_core == null) {
                if (!bufferOrRendezvousSend(s2) || closed) {
                    if (closed) {
                        if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.INTERRUPTED_SEND)) {
                            segment.onCancelledRequest(index, false);
                            return 4;
                        }
                    } else {
                        if (waiter == null) {
                            return 3;
                        }
                        if (segment.casState$kotlinx_coroutines_core(index, null, waiter)) {
                            return 2;
                        }
                    }
                } else if (segment.casState$kotlinx_coroutines_core(index, null, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            } else {
                if (state$kotlinx_coroutines_core != BufferedChannelKt.IN_BUFFER) {
                    if (state$kotlinx_coroutines_core == BufferedChannelKt.INTERRUPTED_RCV) {
                        segment.cleanElement$kotlinx_coroutines_core(index);
                        return 5;
                    }
                    if (state$kotlinx_coroutines_core == BufferedChannelKt.POISONED) {
                        segment.cleanElement$kotlinx_coroutines_core(index);
                        return 5;
                    }
                    if (state$kotlinx_coroutines_core == BufferedChannelKt.getCHANNEL_CLOSED()) {
                        segment.cleanElement$kotlinx_coroutines_core(index);
                        completeCloseOrCancel();
                        return 4;
                    }
                    segment.cleanElement$kotlinx_coroutines_core(index);
                    if (state$kotlinx_coroutines_core instanceof WaiterEB) {
                        state$kotlinx_coroutines_core = ((WaiterEB) state$kotlinx_coroutines_core).waiter;
                    }
                    if (tryResumeReceiver(state$kotlinx_coroutines_core, element)) {
                        segment.setState$kotlinx_coroutines_core(index, BufferedChannelKt.DONE_RCV);
                        h();
                        return 0;
                    }
                    if (segment.getAndSetState$kotlinx_coroutines_core(index, BufferedChannelKt.INTERRUPTED_RCV) != BufferedChannelKt.INTERRUPTED_RCV) {
                        segment.onCancelledRequest(index, true);
                    }
                    return 5;
                }
                if (segment.casState$kotlinx_coroutines_core(index, state$kotlinx_coroutines_core, BufferedChannelKt.BUFFERED)) {
                    return 1;
                }
            }
        }
    }

    private final void updateReceiversCounterIfLower(long value) {
        long j2;
        AtomicLongFieldUpdater atomicLongFieldUpdater = receivers$FU;
        do {
            j2 = atomicLongFieldUpdater.get(this);
            if (j2 >= value) {
                return;
            }
        } while (!receivers$FU.compareAndSet(this, j2, value));
    }

    private final void updateSendersCounterIfLower(long value) {
        long j2;
        long j3;
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        do {
            j2 = atomicLongFieldUpdater.get(this);
            j3 = 1152921504606846975L & j2;
            if (j3 >= value) {
                return;
            }
        } while (!sendersAndCloseStatus$FU.compareAndSet(this, j2, BufferedChannelKt.constructSendersAndCloseStatus(j3, (int) (j2 >> 60))));
    }

    protected boolean a(Throwable cause, boolean cancel) {
        if (cancel) {
            markCancellationStarted();
        }
        boolean zA = a.a(_closeCause$FU, this, BufferedChannelKt.NO_CLOSE_CAUSE, cause);
        if (cancel) {
            markCancelled();
        } else {
            markClosed();
        }
        completeCloseOrCancel();
        g();
        if (zA) {
            invokeCloseHandler();
        }
        return zA;
    }

    protected final void b(long globalCellIndex) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
        ChannelSegment<E> channelSegment = (ChannelSegment) receiveSegment$FU.get(this);
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = receivers$FU;
            long j2 = atomicLongFieldUpdater.get(this);
            if (globalCellIndex < Math.max(this.capacity + j2, getBufferEndCounter())) {
                return;
            }
            if (atomicLongFieldUpdater.compareAndSet(this, j2, j2 + 1)) {
                int i2 = BufferedChannelKt.SEGMENT_SIZE;
                long j3 = j2 / i2;
                int i3 = (int) (j2 % i2);
                if (channelSegment.id != j3) {
                    ChannelSegment<E> channelSegmentFindSegmentReceive = findSegmentReceive(j3, channelSegment);
                    if (channelSegmentFindSegmentReceive == null) {
                        continue;
                    } else {
                        channelSegment = channelSegmentFindSegmentReceive;
                    }
                }
                Object objUpdateCellReceive = updateCellReceive(channelSegment, i3, j2, null);
                if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                    channelSegment.cleanPrev();
                    Function1<E, Unit> function1 = this.onUndeliveredElement;
                    if (function1 != null && (undeliveredElementExceptionCallUndeliveredElementCatchingException$default = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, objUpdateCellReceive, null, 2, null)) != null) {
                        throw undeliveredElementExceptionCallUndeliveredElementCatchingException$default;
                    }
                } else if (j2 < getSendersCounter$kotlinx_coroutines_core()) {
                    channelSegment.cleanPrev();
                }
            }
        }
    }

    protected final Throwable c() {
        return (Throwable) _closeCause$FU.get(this);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final boolean cancel(@Nullable Throwable cause) {
        return cancelImpl$kotlinx_coroutines_core(cause);
    }

    public boolean cancelImpl$kotlinx_coroutines_core(@Nullable Throwable cause) {
        if (cause == null) {
            cause = new CancellationException("Channel was cancelled");
        }
        return a(cause, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void checkSegmentStructureInvariants() {
        if (isRendezvousOrUnlimited()) {
            if (bufferEndSegment$FU.get(this) != BufferedChannelKt.NULL_SEGMENT) {
                throw new IllegalStateException(("bufferEndSegment must be NULL_SEGMENT for rendezvous and unlimited channels; they do not manipulate it.\nChannel state: " + this).toString());
            }
        } else if (((ChannelSegment) receiveSegment$FU.get(this)).id > ((ChannelSegment) bufferEndSegment$FU.get(this)).id) {
            throw new IllegalStateException(("bufferEndSegment should not have lower id than receiveSegment.\nChannel state: " + this).toString());
        }
        List listListOf = CollectionsKt.listOf((Object[]) new ChannelSegment[]{receiveSegment$FU.get(this), sendSegment$FU.get(this), bufferEndSegment$FU.get(this)});
        ArrayList arrayList = new ArrayList();
        for (Object obj : listListOf) {
            if (((ChannelSegment) obj) != BufferedChannelKt.NULL_SEGMENT) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        Object next = it.next();
        if (it.hasNext()) {
            long j2 = ((ChannelSegment) next).id;
            do {
                Object next2 = it.next();
                long j3 = ((ChannelSegment) next2).id;
                if (j2 > j3) {
                    next = next2;
                    j2 = j3;
                }
            } while (it.hasNext());
        }
        ChannelSegment channelSegment = (ChannelSegment) next;
        if (channelSegment.getPrev() != 0) {
            throw new IllegalStateException(("All processed segments should be unreachable from the data structure, but the `prev` link of the leftmost segment is non-null.\nChannel state: " + this).toString());
        }
        while (channelSegment.getNext() != 0) {
            S next3 = channelSegment.getNext();
            Intrinsics.checkNotNull(next3);
            if (((ChannelSegment) next3).getPrev() != 0) {
                S next4 = channelSegment.getNext();
                Intrinsics.checkNotNull(next4);
                if (((ChannelSegment) next4).getPrev() != channelSegment) {
                    throw new IllegalStateException(("The `segment.next.prev === segment` invariant is violated.\nChannel state: " + this).toString());
                }
            }
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            int i3 = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                Object state$kotlinx_coroutines_core = channelSegment.getState$kotlinx_coroutines_core(i4);
                if (!Intrinsics.areEqual(state$kotlinx_coroutines_core, BufferedChannelKt.BUFFERED) && !(state$kotlinx_coroutines_core instanceof Waiter)) {
                    if (Intrinsics.areEqual(state$kotlinx_coroutines_core, BufferedChannelKt.INTERRUPTED_RCV) ? true : Intrinsics.areEqual(state$kotlinx_coroutines_core, BufferedChannelKt.INTERRUPTED_SEND) ? true : Intrinsics.areEqual(state$kotlinx_coroutines_core, BufferedChannelKt.getCHANNEL_CLOSED())) {
                        if (!(channelSegment.getElement$kotlinx_coroutines_core(i4) == null)) {
                            throw new IllegalStateException("Check failed.".toString());
                        }
                        i3++;
                    } else {
                        if (!(Intrinsics.areEqual(state$kotlinx_coroutines_core, BufferedChannelKt.POISONED) ? true : Intrinsics.areEqual(state$kotlinx_coroutines_core, BufferedChannelKt.DONE_RCV))) {
                            throw new IllegalStateException(("Unexpected segment cell state: " + state$kotlinx_coroutines_core + ".\nChannel state: " + this).toString());
                        }
                        if (!(channelSegment.getElement$kotlinx_coroutines_core(i4) == null)) {
                            throw new IllegalStateException("Check failed.".toString());
                        }
                    }
                }
            }
            if (i3 == BufferedChannelKt.SEGMENT_SIZE) {
                if (!(channelSegment == receiveSegment$FU.get(this) || channelSegment == sendSegment$FU.get(this) || channelSegment == bufferEndSegment$FU.get(this))) {
                    throw new IllegalStateException(("Logically removed segment is reachable.\nChannel state: " + this).toString());
                }
            }
            S next5 = channelSegment.getNext();
            Intrinsics.checkNotNull(next5);
            channelSegment = (ChannelSegment) next5;
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable cause) {
        return a(cause, false);
    }

    protected final Throwable d() {
        Throwable thC = c();
        return thC == null ? new ClosedSendChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE) : thC;
    }

    protected boolean f() {
        return false;
    }

    protected void g() {
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    public SelectClause1<E> getOnReceive() {
        BufferedChannel$onReceive$1 bufferedChannel$onReceive$1 = BufferedChannel$onReceive$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceive$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'select')] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = 'param')] kotlin.Any?, kotlin.Unit>{ kotlinx.coroutines.selects.SelectKt.RegistrationFunction }");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceive$1, 3);
        BufferedChannel$onReceive$2 bufferedChannel$onReceive$2 = BufferedChannel$onReceive$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceive$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'param')] kotlin.Any?, @[ParameterName(name = 'clauseResult')] kotlin.Any?, kotlin.Any?>{ kotlinx.coroutines.selects.SelectKt.ProcessResultFunction }");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceive$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    public SelectClause1<ChannelResult<E>> getOnReceiveCatching() {
        BufferedChannel$onReceiveCatching$1 bufferedChannel$onReceiveCatching$1 = BufferedChannel$onReceiveCatching$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveCatching$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'select')] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = 'param')] kotlin.Any?, kotlin.Unit>{ kotlinx.coroutines.selects.SelectKt.RegistrationFunction }");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveCatching$1, 3);
        BufferedChannel$onReceiveCatching$2 bufferedChannel$onReceiveCatching$2 = BufferedChannel$onReceiveCatching$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveCatching$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'param')] kotlin.Any?, @[ParameterName(name = 'clauseResult')] kotlin.Any?, kotlin.Any?>{ kotlinx.coroutines.selects.SelectKt.ProcessResultFunction }");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveCatching$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    public SelectClause1<E> getOnReceiveOrNull() {
        BufferedChannel$onReceiveOrNull$1 bufferedChannel$onReceiveOrNull$1 = BufferedChannel$onReceiveOrNull$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveOrNull$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'select')] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = 'param')] kotlin.Any?, kotlin.Unit>{ kotlinx.coroutines.selects.SelectKt.RegistrationFunction }");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveOrNull$1, 3);
        BufferedChannel$onReceiveOrNull$2 bufferedChannel$onReceiveOrNull$2 = BufferedChannel$onReceiveOrNull$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onReceiveOrNull$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'param')] kotlin.Any?, @[ParameterName(name = 'clauseResult')] kotlin.Any?, kotlin.Any?>{ kotlinx.coroutines.selects.SelectKt.ProcessResultFunction }");
        return new SelectClause1Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onReceiveOrNull$2, 3), this.onUndeliveredElementReceiveCancellationConstructor);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    public SelectClause2<E, BufferedChannel<E>> getOnSend() {
        BufferedChannel$onSend$1 bufferedChannel$onSend$1 = BufferedChannel$onSend$1.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onSend$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'select')] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = 'param')] kotlin.Any?, kotlin.Unit>{ kotlinx.coroutines.selects.SelectKt.RegistrationFunction }");
        Function3 function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onSend$1, 3);
        BufferedChannel$onSend$2 bufferedChannel$onSend$2 = BufferedChannel$onSend$2.INSTANCE;
        Intrinsics.checkNotNull(bufferedChannel$onSend$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = 'clauseObject')] kotlin.Any, @[ParameterName(name = 'param')] kotlin.Any?, @[ParameterName(name = 'clauseResult')] kotlin.Any?, kotlin.Any?>{ kotlinx.coroutines.selects.SelectKt.ProcessResultFunction }");
        return new SelectClause2Impl(this, function3, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(bufferedChannel$onSend$2, 3), null, 8, null);
    }

    public final long getReceiversCounter$kotlinx_coroutines_core() {
        return receivers$FU.get(this);
    }

    public final long getSendersCounter$kotlinx_coroutines_core() {
        return sendersAndCloseStatus$FU.get(this) & 1152921504606846975L;
    }

    protected void h() {
    }

    public final boolean hasElements$kotlinx_coroutines_core() {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = receiveSegment$FU;
            ChannelSegment<E> channelSegmentFindSegmentReceive = (ChannelSegment) atomicReferenceFieldUpdater.get(this);
            long receiversCounter$kotlinx_coroutines_core = getReceiversCounter$kotlinx_coroutines_core();
            if (getSendersCounter$kotlinx_coroutines_core() <= receiversCounter$kotlinx_coroutines_core) {
                return false;
            }
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j2 = receiversCounter$kotlinx_coroutines_core / i2;
            if (channelSegmentFindSegmentReceive.id == j2 || (channelSegmentFindSegmentReceive = findSegmentReceive(j2, channelSegmentFindSegmentReceive)) != null) {
                channelSegmentFindSegmentReceive.cleanPrev();
                if (isCellNonEmpty(channelSegmentFindSegmentReceive, (int) (receiversCounter$kotlinx_coroutines_core % i2), receiversCounter$kotlinx_coroutines_core)) {
                    return true;
                }
                receivers$FU.compareAndSet(this, receiversCounter$kotlinx_coroutines_core, receiversCounter$kotlinx_coroutines_core + 1);
            } else if (((ChannelSegment) atomicReferenceFieldUpdater.get(this)).id < j2) {
                return false;
            }
        }
    }

    protected void i() {
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public void invokeOnClose(@NotNull Function1<? super Throwable, Unit> handler) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = closeHandler$FU;
        if (a.a(atomicReferenceFieldUpdater, this, null, handler)) {
            return;
        }
        do {
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj != BufferedChannelKt.CLOSE_HANDLER_CLOSED) {
                if (obj == BufferedChannelKt.CLOSE_HANDLER_INVOKED) {
                    throw new IllegalStateException("Another handler was already registered and successfully invoked".toString());
                }
                throw new IllegalStateException(("Another handler is already registered: " + obj).toString());
            }
        } while (!a.a(closeHandler$FU, this, BufferedChannelKt.CLOSE_HANDLER_CLOSED, BufferedChannelKt.CLOSE_HANDLER_INVOKED));
        handler.invoke(c());
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isClosedForReceive() {
        return isClosedForReceive0(sendersAndCloseStatus$FU.get(this));
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean isClosedForSend() {
        return isClosedForSend0(sendersAndCloseStatus$FU.get(this));
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public boolean isEmpty() {
        if (isClosedForReceive() || hasElements$kotlinx_coroutines_core()) {
            return false;
        }
        return !isClosedForReceive();
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    public ChannelIterator<E> iterator() {
        return new BufferedChannelIterator();
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0086, code lost:
    
        r14.selectInRegistrationPhase(kotlin.Unit.INSTANCE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:?, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void l(kotlinx.coroutines.selects.SelectInstance r14, java.lang.Object r15) {
        /*
            r13 = this;
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = access$getSendSegment$FU$p()
            java.lang.Object r0 = r0.get(r13)
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
        La:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r1 = access$getSendersAndCloseStatus$FU$p()
            long r1 = r1.getAndIncrement(r13)
            r3 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r3 = r3 & r1
            boolean r1 = access$isClosedForSend0(r13, r1)
            int r2 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r5 = (long) r2
            long r5 = r3 / r5
            long r7 = (long) r2
            long r7 = r3 % r7
            int r2 = (int) r7
            long r7 = r0.id
            int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r7 == 0) goto L39
            kotlinx.coroutines.channels.ChannelSegment r5 = access$findSegmentSend(r13, r5, r0)
            if (r5 != 0) goto L38
            if (r1 == 0) goto La
        L33:
            r13.onClosedSelectOnSend(r15, r14)
            goto L90
        L38:
            r0 = r5
        L39:
            r5 = r13
            r6 = r0
            r7 = r2
            r8 = r15
            r9 = r3
            r11 = r14
            r12 = r1
            int r5 = access$updateCellSend(r5, r6, r7, r8, r9, r11, r12)
            if (r5 == 0) goto L8c
            r6 = 1
            if (r5 == r6) goto L86
            r6 = 2
            if (r5 == r6) goto L72
            r1 = 3
            if (r5 == r1) goto L66
            r1 = 4
            if (r5 == r1) goto L5a
            r1 = 5
            if (r5 == r1) goto L56
            goto La
        L56:
            r0.cleanPrev()
            goto La
        L5a:
            long r1 = r13.getReceiversCounter$kotlinx_coroutines_core()
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 >= 0) goto L33
            r0.cleanPrev()
            goto L33
        L66:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "unexpected"
            java.lang.String r15 = r15.toString()
            r14.<init>(r15)
            throw r14
        L72:
            if (r1 == 0) goto L78
            r0.onSlotCleaned()
            goto L33
        L78:
            boolean r15 = r14 instanceof kotlinx.coroutines.Waiter
            if (r15 == 0) goto L7f
            kotlinx.coroutines.Waiter r14 = (kotlinx.coroutines.Waiter) r14
            goto L80
        L7f:
            r14 = 0
        L80:
            if (r14 == 0) goto L90
            access$prepareSenderForSuspension(r13, r14, r0, r2)
            goto L90
        L86:
            kotlin.Unit r15 = kotlin.Unit.INSTANCE
            r14.selectInRegistrationPhase(r15)
            goto L90
        L8c:
            r0.cleanPrev()
            goto L86
        L90:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.l(kotlinx.coroutines.selects.SelectInstance, java.lang.Object):void");
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = {}))
    public boolean offer(E e2) {
        return Channel.DefaultImpls.offer(this, e2);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = {}))
    @Nullable
    public E poll() {
        return (E) Channel.DefaultImpls.poll(this);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Nullable
    public Object receive(@NotNull Continuation<? super E> continuation) {
        return j(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Nullable
    /* renamed from: receiveCatching-JP2dKIU, reason: not valid java name */
    public Object mo2329receiveCatchingJP2dKIU(@NotNull Continuation<? super ChannelResult<? extends E>> continuation) {
        return k(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    @LowPriorityInOverloadResolution
    @Nullable
    public Object receiveOrNull(@NotNull Continuation<? super E> continuation) {
        return Channel.DefaultImpls.receiveOrNull(this, continuation);
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Nullable
    public Object send(E e2, @NotNull Continuation<? super Unit> continuation) {
        return m(this, e2, continuation);
    }

    @Nullable
    public Object sendBroadcast$kotlinx_coroutines_core(E e2, @NotNull Continuation<? super Boolean> continuation) {
        return n(this, e2, continuation);
    }

    public boolean shouldSendSuspend$kotlinx_coroutines_core() {
        return shouldSendSuspend(sendersAndCloseStatus$FU.get(this));
    }

    /* JADX WARN: Code restructure failed: missing block: B:91:0x01dc, code lost:
    
        r3 = (kotlinx.coroutines.channels.ChannelSegment) r3.getNext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01e3, code lost:
    
        if (r3 != null) goto L98;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.toString():java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final String toStringDebug$kotlinx_coroutines_core() {
        String strValueOf;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("S=");
        sb2.append(getSendersCounter$kotlinx_coroutines_core());
        sb2.append(",R=");
        sb2.append(getReceiversCounter$kotlinx_coroutines_core());
        sb2.append(",B=");
        sb2.append(getBufferEndCounter());
        sb2.append(",B'=");
        sb2.append(completedExpandBuffersAndPauseFlag$FU.get(this));
        sb2.append(",C=");
        AtomicLongFieldUpdater atomicLongFieldUpdater = sendersAndCloseStatus$FU;
        sb2.append((int) (atomicLongFieldUpdater.get(this) >> 60));
        sb2.append(',');
        sb.append(sb2.toString());
        int i2 = (int) (atomicLongFieldUpdater.get(this) >> 60);
        if (i2 == 1) {
            sb.append("CANCELLATION_STARTED,");
        } else if (i2 == 2) {
            sb.append("CLOSED,");
        } else if (i2 == 3) {
            sb.append("CANCELLED,");
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("SEND_SEGM=");
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = sendSegment$FU;
        sb3.append(DebugStringsKt.getHexAddress(atomicReferenceFieldUpdater.get(this)));
        sb3.append(",RCV_SEGM=");
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = receiveSegment$FU;
        sb3.append(DebugStringsKt.getHexAddress(atomicReferenceFieldUpdater2.get(this)));
        sb.append(sb3.toString());
        if (!isRendezvousOrUnlimited()) {
            sb.append(",EB_SEGM=" + DebugStringsKt.getHexAddress(bufferEndSegment$FU.get(this)));
        }
        sb.append("  ");
        List listListOf = CollectionsKt.listOf((Object[]) new ChannelSegment[]{atomicReferenceFieldUpdater2.get(this), atomicReferenceFieldUpdater.get(this), bufferEndSegment$FU.get(this)});
        ArrayList arrayList = new ArrayList();
        for (Object obj : listListOf) {
            if (((ChannelSegment) obj) != BufferedChannelKt.NULL_SEGMENT) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        Object next = it.next();
        if (it.hasNext()) {
            long j2 = ((ChannelSegment) next).id;
            do {
                Object next2 = it.next();
                long j3 = ((ChannelSegment) next2).id;
                if (j2 > j3) {
                    next = next2;
                    j2 = j3;
                }
            } while (it.hasNext());
        }
        ChannelSegment channelSegment = (ChannelSegment) next;
        do {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(DebugStringsKt.getHexAddress(channelSegment));
            sb4.append("=[");
            sb4.append(channelSegment.isRemoved() ? ProxyConfig.MATCH_ALL_SCHEMES : "");
            sb4.append(channelSegment.id);
            sb4.append(",prev=");
            ChannelSegment channelSegment2 = (ChannelSegment) channelSegment.getPrev();
            sb4.append(channelSegment2 != null ? DebugStringsKt.getHexAddress(channelSegment2) : null);
            sb4.append(',');
            sb.append(sb4.toString());
            int i3 = BufferedChannelKt.SEGMENT_SIZE;
            for (int i4 = 0; i4 < i3; i4++) {
                Object state$kotlinx_coroutines_core = channelSegment.getState$kotlinx_coroutines_core(i4);
                Object element$kotlinx_coroutines_core = channelSegment.getElement$kotlinx_coroutines_core(i4);
                if (state$kotlinx_coroutines_core instanceof CancellableContinuation) {
                    strValueOf = "cont";
                } else if (state$kotlinx_coroutines_core instanceof SelectInstance) {
                    strValueOf = "select";
                } else if (state$kotlinx_coroutines_core instanceof ReceiveCatching) {
                    strValueOf = "receiveCatching";
                } else if (state$kotlinx_coroutines_core instanceof SendBroadcast) {
                    strValueOf = "send(broadcast)";
                } else if (state$kotlinx_coroutines_core instanceof WaiterEB) {
                    strValueOf = "EB(" + state$kotlinx_coroutines_core + ')';
                } else {
                    strValueOf = String.valueOf(state$kotlinx_coroutines_core);
                }
                sb.append('[' + i4 + "]=(" + strValueOf + ',' + element$kotlinx_coroutines_core + "),");
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append("next=");
            ChannelSegment channelSegment3 = (ChannelSegment) channelSegment.getNext();
            sb5.append(channelSegment3 != null ? DebugStringsKt.getHexAddress(channelSegment3) : null);
            sb5.append("]  ");
            sb.append(sb5.toString());
            channelSegment = (ChannelSegment) channelSegment.getNext();
        } while (channelSegment != null);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.channels.ReceiveChannel
    @NotNull
    /* renamed from: tryReceive-PtdJZtk, reason: not valid java name */
    public Object mo2330tryReceivePtdJZtk() {
        ChannelSegment channelSegment;
        long j2 = receivers$FU.get(this);
        long j3 = sendersAndCloseStatus$FU.get(this);
        if (isClosedForReceive0(j3)) {
            return ChannelResult.INSTANCE.m2348closedJP2dKIU(c());
        }
        if (j2 >= (j3 & 1152921504606846975L)) {
            return ChannelResult.INSTANCE.m2349failurePtdJZtk();
        }
        Object obj = BufferedChannelKt.INTERRUPTED_RCV;
        ChannelSegment channelSegment2 = (ChannelSegment) receiveSegment$FU.get(this);
        while (!isClosedForReceive()) {
            long andIncrement = receivers$FU.getAndIncrement(this);
            int i2 = BufferedChannelKt.SEGMENT_SIZE;
            long j4 = andIncrement / i2;
            int i3 = (int) (andIncrement % i2);
            if (channelSegment2.id != j4) {
                ChannelSegment channelSegmentFindSegmentReceive = findSegmentReceive(j4, channelSegment2);
                if (channelSegmentFindSegmentReceive == null) {
                    continue;
                } else {
                    channelSegment = channelSegmentFindSegmentReceive;
                }
            } else {
                channelSegment = channelSegment2;
            }
            Object objUpdateCellReceive = updateCellReceive(channelSegment, i3, andIncrement, obj);
            if (objUpdateCellReceive == BufferedChannelKt.SUSPEND) {
                Waiter waiter = obj instanceof Waiter ? (Waiter) obj : null;
                if (waiter != null) {
                    prepareReceiverForSuspension(waiter, channelSegment, i3);
                }
                waitExpandBufferCompletion$kotlinx_coroutines_core(andIncrement);
                channelSegment.onSlotCleaned();
                return ChannelResult.INSTANCE.m2349failurePtdJZtk();
            }
            if (objUpdateCellReceive != BufferedChannelKt.FAILED) {
                if (objUpdateCellReceive == BufferedChannelKt.SUSPEND_NO_WAITER) {
                    throw new IllegalStateException("unexpected".toString());
                }
                channelSegment.cleanPrev();
                return ChannelResult.INSTANCE.m2350successJP2dKIU(objUpdateCellReceive);
            }
            if (andIncrement < getSendersCounter$kotlinx_coroutines_core()) {
                channelSegment.cleanPrev();
            }
            channelSegment2 = channelSegment;
        }
        return ChannelResult.INSTANCE.m2348closedJP2dKIU(c());
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:?, code lost:
    
        return kotlinx.coroutines.channels.ChannelResult.INSTANCE.m2350successJP2dKIU(kotlin.Unit.INSTANCE);
     */
    @Override // kotlinx.coroutines.channels.SendChannel
    @org.jetbrains.annotations.NotNull
    /* renamed from: trySend-JP2dKIU */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object mo2326trySendJP2dKIU(E r15) {
        /*
            r14 = this;
            java.util.concurrent.atomic.AtomicLongFieldUpdater r0 = kotlinx.coroutines.channels.BufferedChannel.sendersAndCloseStatus$FU
            long r0 = r0.get(r14)
            boolean r0 = r14.shouldSendSuspend(r0)
            if (r0 == 0) goto L13
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Object r15 = r15.m2349failurePtdJZtk()
            return r15
        L13:
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.channels.BufferedChannelKt.access$getINTERRUPTED_SEND$p()
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = access$getSendSegment$FU$p()
            java.lang.Object r0 = r0.get(r14)
            kotlinx.coroutines.channels.ChannelSegment r0 = (kotlinx.coroutines.channels.ChannelSegment) r0
        L21:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r1 = access$getSendersAndCloseStatus$FU$p()
            long r1 = r1.getAndIncrement(r14)
            r3 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r9 = r1 & r3
            boolean r11 = access$isClosedForSend0(r14, r1)
            int r1 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            long r2 = (long) r1
            long r2 = r9 / r2
            long r4 = (long) r1
            long r4 = r9 % r4
            int r12 = (int) r4
            long r4 = r0.id
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r1 == 0) goto L59
            kotlinx.coroutines.channels.ChannelSegment r1 = access$findSegmentSend(r14, r2, r0)
            if (r1 != 0) goto L57
            if (r11 == 0) goto L21
        L4b:
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Throwable r0 = r14.d()
            java.lang.Object r15 = r15.m2348closedJP2dKIU(r0)
            goto Lbe
        L57:
            r13 = r1
            goto L5a
        L59:
            r13 = r0
        L5a:
            r0 = r14
            r1 = r13
            r2 = r12
            r3 = r15
            r4 = r9
            r6 = r8
            r7 = r11
            int r0 = access$updateCellSend(r0, r1, r2, r3, r4, r6, r7)
            if (r0 == 0) goto Lba
            r1 = 1
            if (r0 == r1) goto Lb1
            r1 = 2
            if (r0 == r1) goto L94
            r1 = 3
            if (r0 == r1) goto L88
            r1 = 4
            if (r0 == r1) goto L7c
            r1 = 5
            if (r0 == r1) goto L77
            goto L7a
        L77:
            r13.cleanPrev()
        L7a:
            r0 = r13
            goto L21
        L7c:
            long r0 = r14.getReceiversCounter$kotlinx_coroutines_core()
            int r15 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r15 >= 0) goto L4b
            r13.cleanPrev()
            goto L4b
        L88:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
            java.lang.String r0 = "unexpected"
            java.lang.String r0 = r0.toString()
            r15.<init>(r0)
            throw r15
        L94:
            if (r11 == 0) goto L9a
            r13.onSlotCleaned()
            goto L4b
        L9a:
            boolean r15 = r8 instanceof kotlinx.coroutines.Waiter
            if (r15 == 0) goto La1
            kotlinx.coroutines.Waiter r8 = (kotlinx.coroutines.Waiter) r8
            goto La2
        La1:
            r8 = 0
        La2:
            if (r8 == 0) goto La7
            access$prepareSenderForSuspension(r14, r8, r13, r12)
        La7:
            r13.onSlotCleaned()
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            java.lang.Object r15 = r15.m2349failurePtdJZtk()
            goto Lbe
        Lb1:
            kotlinx.coroutines.channels.ChannelResult$Companion r15 = kotlinx.coroutines.channels.ChannelResult.INSTANCE
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            java.lang.Object r15 = r15.m2350successJP2dKIU(r0)
            goto Lbe
        Lba:
            r13.cleanPrev()
            goto Lb1
        Lbe:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BufferedChannel.mo2326trySendJP2dKIU(java.lang.Object):java.lang.Object");
    }

    public final void waitExpandBufferCompletion$kotlinx_coroutines_core(long globalIndex) {
        long j2;
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        long j3;
        if (isRendezvousOrUnlimited()) {
            return;
        }
        while (getBufferEndCounter() <= globalIndex) {
        }
        int i2 = BufferedChannelKt.EXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS;
        for (int i3 = 0; i3 < i2; i3++) {
            long bufferEndCounter = getBufferEndCounter();
            if (bufferEndCounter == (DurationKt.MAX_MILLIS & completedExpandBuffersAndPauseFlag$FU.get(this)) && bufferEndCounter == getBufferEndCounter()) {
                return;
            }
        }
        AtomicLongFieldUpdater atomicLongFieldUpdater2 = completedExpandBuffersAndPauseFlag$FU;
        do {
            j2 = atomicLongFieldUpdater2.get(this);
        } while (!atomicLongFieldUpdater2.compareAndSet(this, j2, BufferedChannelKt.constructEBCompletedAndPauseFlag(j2 & DurationKt.MAX_MILLIS, true)));
        while (true) {
            long bufferEndCounter2 = getBufferEndCounter();
            atomicLongFieldUpdater = completedExpandBuffersAndPauseFlag$FU;
            long j4 = atomicLongFieldUpdater.get(this);
            long j5 = j4 & DurationKt.MAX_MILLIS;
            boolean z2 = (Longs.MAX_POWER_OF_TWO & j4) != 0;
            if (bufferEndCounter2 == j5 && bufferEndCounter2 == getBufferEndCounter()) {
                break;
            } else if (!z2) {
                atomicLongFieldUpdater.compareAndSet(this, j4, BufferedChannelKt.constructEBCompletedAndPauseFlag(j5, true));
            }
        }
        do {
            j3 = atomicLongFieldUpdater.get(this);
        } while (!atomicLongFieldUpdater.compareAndSet(this, j3, BufferedChannelKt.constructEBCompletedAndPauseFlag(j3 & DurationKt.MAX_MILLIS, false)));
    }

    private final void loop$atomicfu(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1, Object obj) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel() {
        cancelImpl$kotlinx_coroutines_core(null);
    }

    @Override // kotlinx.coroutines.channels.ReceiveChannel
    public final void cancel(@Nullable CancellationException cause) {
        cancelImpl$kotlinx_coroutines_core(cause);
    }

    public /* synthetic */ BufferedChannel(int i2, Function1 function1, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? null : function1);
    }
}
