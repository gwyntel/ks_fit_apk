package io.flutter.embedding.android;

import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import io.flutter.Log;
import io.flutter.embedding.engine.systemchannels.KeyEventChannel;
import io.flutter.embedding.engine.systemchannels.KeyboardChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.editing.InputConnectionAdaptor;
import java.util.HashSet;
import java.util.Map;

/* loaded from: classes4.dex */
public class KeyboardManager implements InputConnectionAdaptor.KeyboardDelegate, KeyboardChannel.KeyboardMethodHandler {
    private static final String TAG = "KeyboardManager";
    private final HashSet<KeyEvent> redispatchedEvents = new HashSet<>();
    protected final Responder[] responders;
    private final ViewDelegate viewDelegate;

    public static class CharacterCombiner {
        private int combiningCharacter = 0;

        Character applyCombiningCharacterToBaseCharacter(int i2) {
            char c2 = (char) i2;
            if ((Integer.MIN_VALUE & i2) != 0) {
                int i3 = i2 & Integer.MAX_VALUE;
                int i4 = this.combiningCharacter;
                if (i4 != 0) {
                    this.combiningCharacter = KeyCharacterMap.getDeadChar(i4, i3);
                } else {
                    this.combiningCharacter = i3;
                }
            } else {
                int i5 = this.combiningCharacter;
                if (i5 != 0) {
                    int deadChar = KeyCharacterMap.getDeadChar(i5, i2);
                    if (deadChar > 0) {
                        c2 = (char) deadChar;
                    }
                    this.combiningCharacter = 0;
                }
            }
            return Character.valueOf(c2);
        }
    }

    private class PerEventCallbackBuilder {
        boolean isEventHandled = false;
        final KeyEvent keyEvent;
        int unrepliedCount;

        private class Callback implements Responder.OnKeyEventHandledCallback {
            boolean isCalled;

            private Callback() {
                this.isCalled = false;
            }

            @Override // io.flutter.embedding.android.KeyboardManager.Responder.OnKeyEventHandledCallback
            public void onKeyEventHandled(boolean z2) {
                if (this.isCalled) {
                    throw new IllegalStateException("The onKeyEventHandledCallback should be called exactly once.");
                }
                this.isCalled = true;
                PerEventCallbackBuilder perEventCallbackBuilder = PerEventCallbackBuilder.this;
                int i2 = perEventCallbackBuilder.unrepliedCount - 1;
                perEventCallbackBuilder.unrepliedCount = i2;
                boolean z3 = z2 | perEventCallbackBuilder.isEventHandled;
                perEventCallbackBuilder.isEventHandled = z3;
                if (i2 != 0 || z3) {
                    return;
                }
                KeyboardManager.this.onUnhandled(perEventCallbackBuilder.keyEvent);
            }
        }

        PerEventCallbackBuilder(@NonNull KeyEvent keyEvent) {
            this.unrepliedCount = KeyboardManager.this.responders.length;
            this.keyEvent = keyEvent;
        }

        public Responder.OnKeyEventHandledCallback buildCallback() {
            return new Callback();
        }
    }

    public interface Responder {

        public interface OnKeyEventHandledCallback {
            void onKeyEventHandled(boolean z2);
        }

        void handleEvent(@NonNull KeyEvent keyEvent, @NonNull OnKeyEventHandledCallback onKeyEventHandledCallback);
    }

    public interface ViewDelegate {
        BinaryMessenger getBinaryMessenger();

        boolean onTextInputKeyEvent(@NonNull KeyEvent keyEvent);

        void redispatch(@NonNull KeyEvent keyEvent);
    }

    public KeyboardManager(@NonNull ViewDelegate viewDelegate) {
        this.viewDelegate = viewDelegate;
        this.responders = new Responder[]{new KeyEmbedderResponder(viewDelegate.getBinaryMessenger()), new KeyChannelResponder(new KeyEventChannel(viewDelegate.getBinaryMessenger()))};
        new KeyboardChannel(viewDelegate.getBinaryMessenger()).setKeyboardMethodHandler(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUnhandled(@NonNull KeyEvent keyEvent) {
        ViewDelegate viewDelegate = this.viewDelegate;
        if (viewDelegate == null || viewDelegate.onTextInputKeyEvent(keyEvent)) {
            return;
        }
        this.redispatchedEvents.add(keyEvent);
        this.viewDelegate.redispatch(keyEvent);
        if (this.redispatchedEvents.remove(keyEvent)) {
            Log.w(TAG, "A redispatched key event was consumed before reaching KeyboardManager");
        }
    }

    public void destroy() {
        int size = this.redispatchedEvents.size();
        if (size > 0) {
            Log.w(TAG, "A KeyboardManager was destroyed with " + String.valueOf(size) + " unhandled redispatch event(s).");
        }
    }

    @Override // io.flutter.embedding.engine.systemchannels.KeyboardChannel.KeyboardMethodHandler
    public Map<Long, Long> getKeyboardState() {
        return ((KeyEmbedderResponder) this.responders[0]).getPressedState();
    }

    @Override // io.flutter.plugin.editing.InputConnectionAdaptor.KeyboardDelegate
    public boolean handleEvent(@NonNull KeyEvent keyEvent) {
        if (this.redispatchedEvents.remove(keyEvent)) {
            return false;
        }
        if (this.responders.length <= 0) {
            onUnhandled(keyEvent);
            return true;
        }
        PerEventCallbackBuilder perEventCallbackBuilder = new PerEventCallbackBuilder(keyEvent);
        for (Responder responder : this.responders) {
            responder.handleEvent(keyEvent, perEventCallbackBuilder.buildCallback());
        }
        return true;
    }
}
