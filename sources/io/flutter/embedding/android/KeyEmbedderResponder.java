package io.flutter.embedding.android;

import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.Log;
import io.flutter.embedding.android.KeyData;
import io.flutter.embedding.android.KeyboardManager;
import io.flutter.embedding.android.KeyboardMap;
import io.flutter.plugin.common.BinaryMessenger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class KeyEmbedderResponder implements KeyboardManager.Responder {
    private static final String TAG = "KeyEmbedderResponder";

    @NonNull
    private final BinaryMessenger messenger;

    @NonNull
    private final HashMap<Long, Long> pressingRecords = new HashMap<>();

    @NonNull
    private final HashMap<Long, KeyboardMap.TogglingGoal> togglingGoals = new HashMap<>();

    @NonNull
    private final KeyboardManager.CharacterCombiner characterCombiner = new KeyboardManager.CharacterCombiner();

    /* renamed from: io.flutter.embedding.android.KeyEmbedderResponder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$flutter$embedding$android$KeyData$Type;

        static {
            int[] iArr = new int[KeyData.Type.values().length];
            $SwitchMap$io$flutter$embedding$android$KeyData$Type = iArr;
            try {
                iArr[KeyData.Type.kDown.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$flutter$embedding$android$KeyData$Type[KeyData.Type.kUp.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$flutter$embedding$android$KeyData$Type[KeyData.Type.kRepeat.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public KeyEmbedderResponder(BinaryMessenger binaryMessenger) {
        this.messenger = binaryMessenger;
        for (KeyboardMap.TogglingGoal togglingGoal : KeyboardMap.getTogglingGoals()) {
            this.togglingGoals.put(Long.valueOf(togglingGoal.logicalKey), togglingGoal);
        }
    }

    private static KeyData.Type getEventType(KeyEvent keyEvent) {
        boolean z2 = keyEvent.getRepeatCount() > 0;
        int action = keyEvent.getAction();
        if (action == 0) {
            return z2 ? KeyData.Type.kRepeat : KeyData.Type.kDown;
        }
        if (action == 1) {
            return KeyData.Type.kUp;
        }
        throw new AssertionError("Unexpected event type");
    }

    private Long getLogicalKey(@NonNull KeyEvent keyEvent) {
        Long l2 = KeyboardMap.keyCodeToLogical.get(Long.valueOf(keyEvent.getKeyCode()));
        return l2 != null ? l2 : Long.valueOf(keyOfPlane(keyEvent.getKeyCode(), KeyboardMap.kAndroidPlane));
    }

    private Long getPhysicalKey(@NonNull KeyEvent keyEvent) {
        long scanCode = keyEvent.getScanCode();
        if (scanCode == 0) {
            return Long.valueOf(keyOfPlane(keyEvent.getKeyCode(), KeyboardMap.kAndroidPlane));
        }
        Long l2 = KeyboardMap.scanCodeToPhysical.get(Long.valueOf(scanCode));
        return l2 != null ? l2 : Long.valueOf(keyOfPlane(keyEvent.getScanCode(), KeyboardMap.kAndroidPlane));
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x014c A[LOOP:2: B:68:0x0146->B:70:0x014c, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean handleEventImpl(@androidx.annotation.NonNull android.view.KeyEvent r18, @androidx.annotation.NonNull io.flutter.embedding.android.KeyboardManager.Responder.OnKeyEventHandledCallback r19) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.embedding.android.KeyEmbedderResponder.handleEventImpl(android.view.KeyEvent, io.flutter.embedding.android.KeyboardManager$Responder$OnKeyEventHandledCallback):boolean");
    }

    private static long keyOfPlane(long j2, long j3) {
        return (j2 & 4294967295L) | j3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendKeyEvent$2(KeyboardManager.Responder.OnKeyEventHandledCallback onKeyEventHandledCallback, ByteBuffer byteBuffer) {
        Boolean boolValueOf = Boolean.FALSE;
        if (byteBuffer != null) {
            byteBuffer.rewind();
            if (byteBuffer.capacity() != 0) {
                boolValueOf = Boolean.valueOf(byteBuffer.get() != 0);
            }
        } else {
            Log.w(TAG, "A null reply was received when sending a key event to the framework.");
        }
        onKeyEventHandledCallback.onKeyEventHandled(boolValueOf.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$synchronizePressingKey$0(KeyboardMap.KeyPair keyPair, long j2, KeyEvent keyEvent) {
        synthesizeEvent(false, Long.valueOf(keyPair.logicalKey), Long.valueOf(j2), keyEvent.getEventTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$synchronizePressingKey$1(KeyboardMap.KeyPair keyPair, KeyEvent keyEvent) {
        synthesizeEvent(false, Long.valueOf(keyPair.logicalKey), Long.valueOf(keyPair.physicalKey), keyEvent.getEventTime());
    }

    private void sendKeyEvent(KeyData keyData, final KeyboardManager.Responder.OnKeyEventHandledCallback onKeyEventHandledCallback) {
        this.messenger.send(KeyData.CHANNEL, keyData.toBytes(), onKeyEventHandledCallback == null ? null : new BinaryMessenger.BinaryReply() { // from class: io.flutter.embedding.android.r
            @Override // io.flutter.plugin.common.BinaryMessenger.BinaryReply
            public final void reply(ByteBuffer byteBuffer) {
                KeyEmbedderResponder.lambda$sendKeyEvent$2(onKeyEventHandledCallback, byteBuffer);
            }
        });
    }

    private void synthesizeEvent(boolean z2, Long l2, Long l3, long j2) {
        KeyData keyData = new KeyData();
        keyData.timestamp = j2;
        keyData.type = z2 ? KeyData.Type.kDown : KeyData.Type.kUp;
        keyData.logicalKey = l2.longValue();
        keyData.physicalKey = l3.longValue();
        keyData.character = null;
        keyData.synthesized = true;
        keyData.deviceType = KeyData.DeviceType.kKeyboard;
        if (l3.longValue() != 0 && l2.longValue() != 0) {
            if (!z2) {
                l2 = null;
            }
            updatePressingState(l3, l2);
        }
        sendKeyEvent(keyData, null);
    }

    public Map<Long, Long> getPressedState() {
        return Collections.unmodifiableMap(this.pressingRecords);
    }

    @Override // io.flutter.embedding.android.KeyboardManager.Responder
    public void handleEvent(@NonNull KeyEvent keyEvent, @NonNull KeyboardManager.Responder.OnKeyEventHandledCallback onKeyEventHandledCallback) {
        if (handleEventImpl(keyEvent, onKeyEventHandledCallback)) {
            return;
        }
        synthesizeEvent(true, 0L, 0L, 0L);
        onKeyEventHandledCallback.onKeyEventHandled(true);
    }

    void synchronizePressingKey(KeyboardMap.PressingGoal pressingGoal, boolean z2, long j2, final long j3, final KeyEvent keyEvent, ArrayList<Runnable> arrayList) {
        KeyboardMap.KeyPair[] keyPairArr = pressingGoal.keys;
        boolean[] zArr = new boolean[keyPairArr.length];
        Boolean[] boolArr = new Boolean[keyPairArr.length];
        boolean z3 = false;
        int i2 = 0;
        while (true) {
            KeyboardMap.KeyPair[] keyPairArr2 = pressingGoal.keys;
            boolean z4 = true;
            if (i2 >= keyPairArr2.length) {
                break;
            }
            final KeyboardMap.KeyPair keyPair = keyPairArr2[i2];
            boolean zContainsKey = this.pressingRecords.containsKey(Long.valueOf(keyPair.physicalKey));
            zArr[i2] = zContainsKey;
            if (keyPair.logicalKey == j2) {
                int i3 = AnonymousClass1.$SwitchMap$io$flutter$embedding$android$KeyData$Type[getEventType(keyEvent).ordinal()];
                if (i3 != 1) {
                    if (i3 == 2) {
                        boolArr[i2] = Boolean.valueOf(zArr[i2]);
                    } else if (i3 == 3) {
                        if (!z2) {
                            arrayList.add(new Runnable() { // from class: io.flutter.embedding.android.t
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f25046a.lambda$synchronizePressingKey$1(keyPair, keyEvent);
                                }
                            });
                        }
                        boolArr[i2] = Boolean.valueOf(zArr[i2]);
                    }
                    i2++;
                } else {
                    boolArr[i2] = Boolean.FALSE;
                    if (!z2) {
                        arrayList.add(new Runnable() { // from class: io.flutter.embedding.android.s
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f25042a.lambda$synchronizePressingKey$0(keyPair, j3, keyEvent);
                            }
                        });
                    }
                }
            } else if (!z3 && !zContainsKey) {
                z4 = false;
            }
            z3 = z4;
            i2++;
        }
        if (z2) {
            for (int i4 = 0; i4 < pressingGoal.keys.length; i4++) {
                if (boolArr[i4] == null) {
                    if (z3) {
                        boolArr[i4] = Boolean.valueOf(zArr[i4]);
                    } else {
                        boolArr[i4] = Boolean.TRUE;
                        z3 = true;
                    }
                }
            }
            if (!z3) {
                boolArr[0] = Boolean.TRUE;
            }
        } else {
            for (int i5 = 0; i5 < pressingGoal.keys.length; i5++) {
                if (boolArr[i5] == null) {
                    boolArr[i5] = Boolean.FALSE;
                }
            }
        }
        for (int i6 = 0; i6 < pressingGoal.keys.length; i6++) {
            if (zArr[i6] != boolArr[i6].booleanValue()) {
                KeyboardMap.KeyPair keyPair2 = pressingGoal.keys[i6];
                synthesizeEvent(boolArr[i6].booleanValue(), Long.valueOf(keyPair2.logicalKey), Long.valueOf(keyPair2.physicalKey), keyEvent.getEventTime());
            }
        }
    }

    void synchronizeTogglingKey(KeyboardMap.TogglingGoal togglingGoal, boolean z2, long j2, KeyEvent keyEvent) {
        if (togglingGoal.logicalKey == j2 || togglingGoal.enabled == z2) {
            return;
        }
        boolean zContainsKey = this.pressingRecords.containsKey(Long.valueOf(togglingGoal.physicalKey));
        boolean z3 = !zContainsKey;
        if (z3) {
            togglingGoal.enabled = !togglingGoal.enabled;
        }
        synthesizeEvent(z3, Long.valueOf(togglingGoal.logicalKey), Long.valueOf(togglingGoal.physicalKey), keyEvent.getEventTime());
        if (!z3) {
            togglingGoal.enabled = !togglingGoal.enabled;
        }
        synthesizeEvent(zContainsKey, Long.valueOf(togglingGoal.logicalKey), Long.valueOf(togglingGoal.physicalKey), keyEvent.getEventTime());
    }

    void updatePressingState(@NonNull Long l2, @Nullable Long l3) {
        if (l3 != null) {
            if (this.pressingRecords.put(l2, l3) != null) {
                throw new AssertionError("The key was not empty");
            }
        } else if (this.pressingRecords.remove(l2) == null) {
            throw new AssertionError("The key was empty");
        }
    }
}
