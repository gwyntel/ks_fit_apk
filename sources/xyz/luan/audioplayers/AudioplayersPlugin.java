package xyz.luan.audioplayers;

import android.content.Context;
import android.media.AudioManager;
import androidx.core.app.NotificationCompat;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.umeng.analytics.pro.f;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.player.SoundPoolManager;
import xyz.luan.audioplayers.player.WrappedPlayer;
import xyz.luan.audioplayers.source.BytesSource;
import xyz.luan.audioplayers.source.UrlSource;

@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0014\u001a\u00020\u0006J\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0010H\u0002J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u000e\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u0011J\u000e\u0010!\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u0011J,\u0010\"\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u00112\b\u0010#\u001a\u0004\u0018\u00010\u00102\b\u0010$\u001a\u0004\u0018\u00010\u00102\b\u0010%\u001a\u0004\u0018\u00010&J$\u0010'\u001a\u00020\u001a2\b\u0010#\u001a\u0004\u0018\u00010\u00102\b\u0010$\u001a\u0004\u0018\u00010\u00102\b\u0010%\u001a\u0004\u0018\u00010&J\u000e\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u0010J\u0016\u0010*\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u00112\u0006\u0010)\u001a\u00020\u0010J\u0016\u0010+\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u00112\u0006\u0010,\u001a\u00020-J\u000e\u0010.\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u0011J\u0018\u0010/\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u00100\u001a\u00020\u001a2\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u001a2\u0006\u00101\u001a\u000202H\u0016JT\u00104\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2:\u00105\u001a6\u0012\u0013\u0012\u00110\u001c¢\u0006\f\b7\u0012\b\b8\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u001e¢\u0006\f\b7\u0012\b\b8\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u001a06j\u0002`9H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lxyz/luan/audioplayers/AudioplayersPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "()V", "binaryMessenger", "Lio/flutter/plugin/common/BinaryMessenger;", f.X, "Landroid/content/Context;", "defaultAudioContext", "Lxyz/luan/audioplayers/AudioContextAndroid;", "globalEvents", "Lxyz/luan/audioplayers/EventHandler;", "globalMethods", "Lio/flutter/plugin/common/MethodChannel;", "methods", "players", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lxyz/luan/audioplayers/player/WrappedPlayer;", "soundPoolManager", "Lxyz/luan/audioplayers/player/SoundPoolManager;", "getApplicationContext", "getAudioManager", "Landroid/media/AudioManager;", "getPlayer", "playerId", "globalMethodHandler", "", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "response", "Lio/flutter/plugin/common/MethodChannel$Result;", "handleComplete", "player", "handleDuration", "handleError", "errorCode", "errorMessage", "errorDetails", "", "handleGlobalError", "handleGlobalLog", "message", "handleLog", "handlePrepared", "isPrepared", "", "handleSeekComplete", "methodHandler", "onAttachedToEngine", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "safeCall", "handler", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "Lxyz/luan/audioplayers/FlutterHandler;", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAudioplayersPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AudioplayersPlugin.kt\nxyz/luan/audioplayers/AudioplayersPlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 AudioplayersPlugin.kt\nxyz/luan/audioplayers/AudioplayersPluginKt\n*L\n1#1,307:1\n1851#2,2:308\n1851#2,2:310\n257#3,2:312\n257#3,2:314\n*S KotlinDebug\n*F\n+ 1 AudioplayersPlugin.kt\nxyz/luan/audioplayers/AudioplayersPlugin\n*L\n44#1:308,2\n65#1:310,2\n167#1:312,2\n172#1:314,2\n*E\n"})
/* loaded from: classes5.dex */
public final class AudioplayersPlugin implements FlutterPlugin {
    private BinaryMessenger binaryMessenger;
    private Context context;
    private EventHandler globalEvents;
    private MethodChannel globalMethods;
    private MethodChannel methods;
    private SoundPoolManager soundPoolManager;

    @NotNull
    private final ConcurrentHashMap<String, WrappedPlayer> players = new ConcurrentHashMap<>();

    @NotNull
    private AudioContextAndroid defaultAudioContext = new AudioContextAndroid();

    private final WrappedPlayer getPlayer(String playerId) {
        WrappedPlayer wrappedPlayer = this.players.get(playerId);
        if (wrappedPlayer != null) {
            return wrappedPlayer;
        }
        throw new IllegalStateException("Player has not yet been created or has already been disposed.".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void globalMethodHandler(MethodCall call, MethodChannel.Result response) {
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -1630329231:
                    if (str.equals("emitLog")) {
                        String str2 = (String) call.argument("message");
                        if (str2 == null) {
                            throw new IllegalStateException("message is required".toString());
                        }
                        handleGlobalLog(str2);
                        response.success(1);
                        return;
                    }
                    break;
                case 3237136:
                    if (str.equals("init")) {
                        Collection<WrappedPlayer> collectionValues = this.players.values();
                        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
                        Iterator<T> it = collectionValues.iterator();
                        while (it.hasNext()) {
                            ((WrappedPlayer) it.next()).dispose();
                        }
                        this.players.clear();
                        response.success(1);
                        return;
                    }
                    break;
                case 910310901:
                    if (str.equals("emitError")) {
                        String str3 = (String) call.argument("code");
                        if (str3 == null) {
                            throw new IllegalStateException("code is required".toString());
                        }
                        String str4 = (String) call.argument("message");
                        if (str4 == null) {
                            throw new IllegalStateException("message is required".toString());
                        }
                        handleGlobalError(str3, str4, null);
                        response.success(1);
                        return;
                    }
                    break;
                case 1902436987:
                    if (str.equals("setAudioContext")) {
                        AudioManager audioManager = getAudioManager();
                        audioManager.setMode(this.defaultAudioContext.getAudioMode());
                        audioManager.setSpeakerphoneOn(this.defaultAudioContext.isSpeakerphoneOn());
                        this.defaultAudioContext = AudioplayersPluginKt.audioContext(call);
                        response.success(1);
                        return;
                    }
                    break;
            }
        }
        response.notImplemented();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void methodHandler(MethodCall call, MethodChannel.Result response) {
        String str = (String) call.argument("playerId");
        if (str == null) {
            return;
        }
        PlayerMode playerModeValueOf = null;
        SoundPoolManager soundPoolManager = null;
        ReleaseMode releaseModeValueOf = null;
        if (Intrinsics.areEqual(call.method, "create")) {
            BinaryMessenger binaryMessenger = this.binaryMessenger;
            if (binaryMessenger == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binaryMessenger");
                binaryMessenger = null;
            }
            EventHandler eventHandler = new EventHandler(new EventChannel(binaryMessenger, "xyz.luan/audioplayers/events/" + str));
            ConcurrentHashMap<String, WrappedPlayer> concurrentHashMap = this.players;
            AudioContextAndroid audioContextAndroidCopy$default = AudioContextAndroid.copy$default(this.defaultAudioContext, false, false, 0, 0, 0, 0, 63, null);
            SoundPoolManager soundPoolManager2 = this.soundPoolManager;
            if (soundPoolManager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("soundPoolManager");
            } else {
                soundPoolManager = soundPoolManager2;
            }
            concurrentHashMap.put(str, new WrappedPlayer(this, eventHandler, audioContextAndroidCopy$default, soundPoolManager));
            response.success(1);
            return;
        }
        WrappedPlayer player = getPlayer(str);
        try {
            String str2 = call.method;
            if (str2 != null) {
                switch (str2.hashCode()) {
                    case -1757019252:
                        if (!str2.equals("getCurrentPosition")) {
                            break;
                        } else {
                            response.success(player.getCurrentPosition());
                            return;
                        }
                    case -1722943962:
                        if (!str2.equals("setPlayerMode")) {
                            break;
                        } else {
                            String str3 = (String) call.argument("playerMode");
                            if (str3 != null) {
                                Intrinsics.checkNotNull(str3);
                                playerModeValueOf = PlayerMode.valueOf(AudioplayersPluginKt.toConstantCase((String) CollectionsKt.last(StringsKt.split$default((CharSequence) str3, new char[]{'.'}, false, 0, 6, (Object) null))));
                            }
                            if (playerModeValueOf == null) {
                                throw new IllegalStateException("playerMode is required".toString());
                            }
                            player.setPlayerMode(playerModeValueOf);
                            response.success(1);
                            return;
                        }
                    case -1660487654:
                        if (!str2.equals("setBalance")) {
                            break;
                        } else {
                            Double d2 = (Double) call.argument("balance");
                            if (d2 == null) {
                                throw new IllegalStateException("balance is required".toString());
                            }
                            player.setBalance((float) d2.doubleValue());
                            response.success(1);
                            return;
                        }
                    case -1630329231:
                        if (!str2.equals("emitLog")) {
                            break;
                        } else {
                            String str4 = (String) call.argument("message");
                            if (str4 == null) {
                                throw new IllegalStateException("message is required".toString());
                            }
                            player.handleLog(str4);
                            response.success(1);
                            return;
                        }
                    case -934426579:
                        if (!str2.equals("resume")) {
                            break;
                        } else {
                            player.play();
                            response.success(1);
                            return;
                        }
                    case -402284771:
                        if (!str2.equals("setPlaybackRate")) {
                            break;
                        } else {
                            Double d3 = (Double) call.argument("playbackRate");
                            if (d3 == null) {
                                throw new IllegalStateException("playbackRate is required".toString());
                            }
                            player.setRate((float) d3.doubleValue());
                            response.success(1);
                            return;
                        }
                    case -159032046:
                        if (!str2.equals("setSourceUrl")) {
                            break;
                        } else {
                            String str5 = (String) call.argument("url");
                            if (str5 == null) {
                                throw new IllegalStateException("url is required".toString());
                            }
                            Boolean bool = (Boolean) call.argument("isLocal");
                            if (bool == null) {
                                bool = Boolean.FALSE;
                            }
                            try {
                                player.setSource(new UrlSource(str5, bool.booleanValue()));
                                response.success(1);
                                return;
                            } catch (FileNotFoundException e2) {
                                response.error("AndroidAudioError", "Failed to set source. For troubleshooting, see: https://github.com/bluefireteam/audioplayers/blob/main/troubleshooting.md", e2);
                                return;
                            }
                        }
                    case 3526264:
                        if (!str2.equals("seek")) {
                            break;
                        } else {
                            Integer num = (Integer) call.argument(RequestParameters.POSITION);
                            if (num == null) {
                                throw new IllegalStateException("position is required".toString());
                            }
                            player.seek(num.intValue());
                            response.success(1);
                            return;
                        }
                    case 3540994:
                        if (!str2.equals("stop")) {
                            break;
                        } else {
                            player.stop();
                            response.success(1);
                            return;
                        }
                    case 85887754:
                        if (!str2.equals("getDuration")) {
                            break;
                        } else {
                            response.success(player.getDuration());
                            return;
                        }
                    case 106440182:
                        if (!str2.equals("pause")) {
                            break;
                        } else {
                            player.pause();
                            response.success(1);
                            return;
                        }
                    case 670514716:
                        if (!str2.equals("setVolume")) {
                            break;
                        } else {
                            Double d4 = (Double) call.argument("volume");
                            if (d4 == null) {
                                throw new IllegalStateException("volume is required".toString());
                            }
                            player.setVolume((float) d4.doubleValue());
                            response.success(1);
                            return;
                        }
                    case 910310901:
                        if (!str2.equals("emitError")) {
                            break;
                        } else {
                            String str6 = (String) call.argument("code");
                            if (str6 == null) {
                                throw new IllegalStateException("code is required".toString());
                            }
                            String str7 = (String) call.argument("message");
                            if (str7 == null) {
                                throw new IllegalStateException("message is required".toString());
                            }
                            player.handleError(str6, str7, null);
                            response.success(1);
                            return;
                        }
                    case 1090594823:
                        if (!str2.equals("release")) {
                            break;
                        } else {
                            player.release();
                            response.success(1);
                            return;
                        }
                    case 1671767583:
                        if (!str2.equals("dispose")) {
                            break;
                        } else {
                            player.dispose();
                            this.players.remove(str);
                            response.success(1);
                            return;
                        }
                    case 1771699022:
                        if (!str2.equals("setSourceBytes")) {
                            break;
                        } else {
                            byte[] bArr = (byte[]) call.argument("bytes");
                            if (bArr == null) {
                                throw new IllegalStateException("bytes are required".toString());
                            }
                            player.setSource(new BytesSource(bArr));
                            response.success(1);
                            return;
                        }
                    case 1902436987:
                        if (!str2.equals("setAudioContext")) {
                            break;
                        } else {
                            player.updateAudioContext(AudioplayersPluginKt.audioContext(call));
                            response.success(1);
                            return;
                        }
                    case 2096116872:
                        if (!str2.equals("setReleaseMode")) {
                            break;
                        } else {
                            String str8 = (String) call.argument("releaseMode");
                            if (str8 != null) {
                                Intrinsics.checkNotNull(str8);
                                releaseModeValueOf = ReleaseMode.valueOf(AudioplayersPluginKt.toConstantCase((String) CollectionsKt.last(StringsKt.split$default((CharSequence) str8, new char[]{'.'}, false, 0, 6, (Object) null))));
                            }
                            if (releaseModeValueOf == null) {
                                throw new IllegalStateException("releaseMode is required".toString());
                            }
                            player.setReleaseMode(releaseModeValueOf);
                            response.success(1);
                            return;
                        }
                }
            }
            response.notImplemented();
        } catch (Exception e3) {
            response.error("AndroidAudioError", e3.getMessage(), e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAttachedToEngine$lambda$0(AudioplayersPlugin this$0, MethodCall call, MethodChannel.Result response) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        this$0.safeCall(call, response, new AudioplayersPlugin$onAttachedToEngine$1$1(this$0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAttachedToEngine$lambda$1(AudioplayersPlugin this$0, MethodCall call, MethodChannel.Result response) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        this$0.safeCall(call, response, new AudioplayersPlugin$onAttachedToEngine$2$1(this$0));
    }

    private final void safeCall(MethodCall call, MethodChannel.Result response, Function2<? super MethodCall, ? super MethodChannel.Result, Unit> handler) {
        try {
            handler.mo1invoke(call, response);
        } catch (Throwable th) {
            response.error("Unexpected AndroidAudioError", th.getMessage(), th);
        }
    }

    @NotNull
    public final Context getApplicationContext() {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(f.X);
            context = null;
        }
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        return applicationContext;
    }

    @NotNull
    public final AudioManager getAudioManager() {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(f.X);
            context = null;
        }
        Object systemService = context.getApplicationContext().getSystemService("audio");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.AudioManager");
        return (AudioManager) systemService;
    }

    public final void handleComplete(@NotNull WrappedPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
        EventHandler.success$default(player.getEventHandler(), "audio.onComplete", null, 2, null);
    }

    public final void handleDuration(@NotNull WrappedPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
        EventHandler eventHandler = player.getEventHandler();
        Integer duration = player.getDuration();
        eventHandler.success("audio.onDuration", MapsKt.hashMapOf(TuplesKt.to("value", Integer.valueOf(duration != null ? duration.intValue() : 0))));
    }

    public final void handleError(@NotNull WrappedPlayer player, @Nullable String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
        Intrinsics.checkNotNullParameter(player, "player");
        player.getEventHandler().error(errorCode, errorMessage, errorDetails);
    }

    public final void handleGlobalError(@Nullable String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
        EventHandler eventHandler = this.globalEvents;
        if (eventHandler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("globalEvents");
            eventHandler = null;
        }
        eventHandler.error(errorCode, errorMessage, errorDetails);
    }

    public final void handleGlobalLog(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        EventHandler eventHandler = this.globalEvents;
        if (eventHandler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("globalEvents");
            eventHandler = null;
        }
        eventHandler.success("audio.onLog", MapsKt.hashMapOf(TuplesKt.to("value", message)));
    }

    public final void handleLog(@NotNull WrappedPlayer player, @NotNull String message) {
        Intrinsics.checkNotNullParameter(player, "player");
        Intrinsics.checkNotNullParameter(message, "message");
        player.getEventHandler().success("audio.onLog", MapsKt.hashMapOf(TuplesKt.to("value", message)));
    }

    public final void handlePrepared(@NotNull WrappedPlayer player, boolean isPrepared) {
        Intrinsics.checkNotNullParameter(player, "player");
        player.getEventHandler().success("audio.onPrepared", MapsKt.hashMapOf(TuplesKt.to("value", Boolean.valueOf(isPrepared))));
    }

    public final void handleSeekComplete(@NotNull WrappedPlayer player) {
        Intrinsics.checkNotNullParameter(player, "player");
        EventHandler.success$default(player.getEventHandler(), "audio.onSeekComplete", null, 2, null);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Context applicationContext = binding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.context = applicationContext;
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        this.binaryMessenger = binaryMessenger;
        this.soundPoolManager = new SoundPoolManager(this);
        MethodChannel methodChannel = new MethodChannel(binding.getBinaryMessenger(), "xyz.luan/audioplayers");
        this.methods = methodChannel;
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() { // from class: xyz.luan.audioplayers.a
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public final void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                AudioplayersPlugin.onAttachedToEngine$lambda$0(this.f26919a, methodCall, result);
            }
        });
        MethodChannel methodChannel2 = new MethodChannel(binding.getBinaryMessenger(), "xyz.luan/audioplayers.global");
        this.globalMethods = methodChannel2;
        methodChannel2.setMethodCallHandler(new MethodChannel.MethodCallHandler() { // from class: xyz.luan.audioplayers.b
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public final void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                AudioplayersPlugin.onAttachedToEngine$lambda$1(this.f26920a, methodCall, result);
            }
        });
        this.globalEvents = new EventHandler(new EventChannel(binding.getBinaryMessenger(), "xyz.luan/audioplayers.global/events"));
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Collection<WrappedPlayer> collectionValues = this.players.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            ((WrappedPlayer) it.next()).dispose();
        }
        this.players.clear();
        SoundPoolManager soundPoolManager = this.soundPoolManager;
        EventHandler eventHandler = null;
        if (soundPoolManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundPoolManager");
            soundPoolManager = null;
        }
        soundPoolManager.dispose();
        EventHandler eventHandler2 = this.globalEvents;
        if (eventHandler2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("globalEvents");
        } else {
            eventHandler = eventHandler2;
        }
        eventHandler.dispose();
    }
}
