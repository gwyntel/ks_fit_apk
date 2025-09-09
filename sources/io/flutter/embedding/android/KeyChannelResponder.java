package io.flutter.embedding.android;

import android.view.KeyEvent;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.KeyboardManager;
import io.flutter.embedding.engine.systemchannels.KeyEventChannel;

/* loaded from: classes4.dex */
public class KeyChannelResponder implements KeyboardManager.Responder {
    private static final String TAG = "KeyChannelResponder";

    @NonNull
    private final KeyboardManager.CharacterCombiner characterCombiner = new KeyboardManager.CharacterCombiner();

    @NonNull
    private final KeyEventChannel keyEventChannel;

    public KeyChannelResponder(@NonNull KeyEventChannel keyEventChannel) {
        this.keyEventChannel = keyEventChannel;
    }

    @Override // io.flutter.embedding.android.KeyboardManager.Responder
    public void handleEvent(@NonNull KeyEvent keyEvent, @NonNull final KeyboardManager.Responder.OnKeyEventHandledCallback onKeyEventHandledCallback) {
        int action = keyEvent.getAction();
        if (action == 0 || action == 1) {
            this.keyEventChannel.sendFlutterKeyEvent(new KeyEventChannel.FlutterKeyEvent(keyEvent, this.characterCombiner.applyCombiningCharacterToBaseCharacter(keyEvent.getUnicodeChar())), action != 0, new KeyEventChannel.EventResponseHandler() { // from class: io.flutter.embedding.android.q
                @Override // io.flutter.embedding.engine.systemchannels.KeyEventChannel.EventResponseHandler
                public final void onFrameworkResponse(boolean z2) {
                    onKeyEventHandledCallback.onKeyEventHandled(z2);
                }
            });
        } else {
            onKeyEventHandledCallback.onKeyEventHandled(false);
        }
    }
}
