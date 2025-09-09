package io.flutter.plugin.editing;

import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.flutter.embedding.engine.systemchannels.SpellCheckChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.localization.LocalizationPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/* loaded from: classes4.dex */
public class SpellCheckPlugin implements SpellCheckChannel.SpellCheckMethodHandler, SpellCheckerSession.SpellCheckerSessionListener {
    public static final String END_INDEX_KEY = "endIndex";
    private static final int MAX_SPELL_CHECK_SUGGESTIONS = 5;
    public static final String START_INDEX_KEY = "startIndex";
    public static final String SUGGESTIONS_KEY = "suggestions";
    private final SpellCheckChannel mSpellCheckChannel;
    private SpellCheckerSession mSpellCheckerSession;
    private final TextServicesManager mTextServicesManager;

    @VisibleForTesting
    MethodChannel.Result pendingResult;

    public SpellCheckPlugin(@NonNull TextServicesManager textServicesManager, @NonNull SpellCheckChannel spellCheckChannel) {
        this.mTextServicesManager = textServicesManager;
        this.mSpellCheckChannel = spellCheckChannel;
        spellCheckChannel.setSpellCheckMethodHandler(this);
    }

    public void destroy() {
        this.mSpellCheckChannel.setSpellCheckMethodHandler(null);
        SpellCheckerSession spellCheckerSession = this.mSpellCheckerSession;
        if (spellCheckerSession != null) {
            spellCheckerSession.close();
        }
    }

    @Override // io.flutter.embedding.engine.systemchannels.SpellCheckChannel.SpellCheckMethodHandler
    public void initiateSpellCheck(@NonNull String str, @NonNull String str2, @NonNull MethodChannel.Result result) {
        if (this.pendingResult != null) {
            result.error("error", "Previous spell check request still pending.", null);
        } else {
            this.pendingResult = result;
            performSpellCheck(str, str2);
        }
    }

    @Override // android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
        if (sentenceSuggestionsInfoArr.length == 0) {
            this.pendingResult.success(new ArrayList());
            this.pendingResult = null;
            return;
        }
        ArrayList arrayList = new ArrayList();
        SentenceSuggestionsInfo sentenceSuggestionsInfo = sentenceSuggestionsInfoArr[0];
        if (sentenceSuggestionsInfo == null) {
            this.pendingResult.success(new ArrayList());
            this.pendingResult = null;
            return;
        }
        for (int i2 = 0; i2 < sentenceSuggestionsInfo.getSuggestionsCount(); i2++) {
            SuggestionsInfo suggestionsInfoAt = sentenceSuggestionsInfo.getSuggestionsInfoAt(i2);
            int suggestionsCount = suggestionsInfoAt.getSuggestionsCount();
            if (suggestionsCount > 0) {
                HashMap map = new HashMap();
                int offsetAt = sentenceSuggestionsInfo.getOffsetAt(i2);
                int lengthAt = sentenceSuggestionsInfo.getLengthAt(i2) + offsetAt;
                map.put(START_INDEX_KEY, Integer.valueOf(offsetAt));
                map.put(END_INDEX_KEY, Integer.valueOf(lengthAt));
                ArrayList arrayList2 = new ArrayList();
                boolean z2 = false;
                for (int i3 = 0; i3 < suggestionsCount; i3++) {
                    String suggestionAt = suggestionsInfoAt.getSuggestionAt(i3);
                    if (!suggestionAt.equals("")) {
                        arrayList2.add(suggestionAt);
                        z2 = true;
                    }
                }
                if (z2) {
                    map.put("suggestions", arrayList2);
                    arrayList.add(map);
                }
            }
        }
        this.pendingResult.success(arrayList);
        this.pendingResult = null;
    }

    @Override // android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener
    public void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr) {
    }

    public void performSpellCheck(@NonNull String str, @NonNull String str2) {
        Locale localeLocaleFromString = LocalizationPlugin.localeFromString(str);
        if (this.mSpellCheckerSession == null) {
            this.mSpellCheckerSession = this.mTextServicesManager.newSpellCheckerSession(null, localeLocaleFromString, this, true);
        }
        this.mSpellCheckerSession.getSentenceSuggestions(new TextInfo[]{new TextInfo(str2)}, 5);
    }
}
