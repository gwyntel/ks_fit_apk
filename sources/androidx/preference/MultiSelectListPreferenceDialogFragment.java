package androidx.preference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Deprecated
/* loaded from: classes2.dex */
public class MultiSelectListPreferenceDialogFragment extends PreferenceDialogFragment {
    private static final String SAVE_STATE_CHANGED = "MultiSelectListPreferenceDialogFragment.changed";
    private static final String SAVE_STATE_ENTRIES = "MultiSelectListPreferenceDialogFragment.entries";
    private static final String SAVE_STATE_ENTRY_VALUES = "MultiSelectListPreferenceDialogFragment.entryValues";
    private static final String SAVE_STATE_VALUES = "MultiSelectListPreferenceDialogFragment.values";

    /* renamed from: a, reason: collision with root package name */
    Set f5651a = new HashSet();

    /* renamed from: b, reason: collision with root package name */
    boolean f5652b;

    /* renamed from: c, reason: collision with root package name */
    CharSequence[] f5653c;

    /* renamed from: d, reason: collision with root package name */
    CharSequence[] f5654d;

    @Deprecated
    public MultiSelectListPreferenceDialogFragment() {
    }

    private MultiSelectListPreference getListPreference() {
        return (MultiSelectListPreference) getPreference();
    }

    @NonNull
    @Deprecated
    public static MultiSelectListPreferenceDialogFragment newInstance(String str) {
        MultiSelectListPreferenceDialogFragment multiSelectListPreferenceDialogFragment = new MultiSelectListPreferenceDialogFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(KsProperty.Key, str);
        multiSelectListPreferenceDialogFragment.setArguments(bundle);
        return multiSelectListPreferenceDialogFragment;
    }

    @Override // androidx.preference.PreferenceDialogFragment
    protected void d(AlertDialog.Builder builder) {
        super.d(builder);
        int length = this.f5654d.length;
        boolean[] zArr = new boolean[length];
        for (int i2 = 0; i2 < length; i2++) {
            zArr[i2] = this.f5651a.contains(this.f5654d[i2].toString());
        }
        builder.setMultiChoiceItems(this.f5653c, zArr, new DialogInterface.OnMultiChoiceClickListener() { // from class: androidx.preference.MultiSelectListPreferenceDialogFragment.1
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public void onClick(DialogInterface dialogInterface, int i3, boolean z2) {
                if (z2) {
                    MultiSelectListPreferenceDialogFragment multiSelectListPreferenceDialogFragment = MultiSelectListPreferenceDialogFragment.this;
                    multiSelectListPreferenceDialogFragment.f5652b = multiSelectListPreferenceDialogFragment.f5651a.add(multiSelectListPreferenceDialogFragment.f5654d[i3].toString()) | multiSelectListPreferenceDialogFragment.f5652b;
                } else {
                    MultiSelectListPreferenceDialogFragment multiSelectListPreferenceDialogFragment2 = MultiSelectListPreferenceDialogFragment.this;
                    multiSelectListPreferenceDialogFragment2.f5652b = multiSelectListPreferenceDialogFragment2.f5651a.remove(multiSelectListPreferenceDialogFragment2.f5654d[i3].toString()) | multiSelectListPreferenceDialogFragment2.f5652b;
                }
            }
        });
    }

    @Override // androidx.preference.PreferenceDialogFragment, android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.f5651a.clear();
            this.f5651a.addAll(bundle.getStringArrayList(SAVE_STATE_VALUES));
            this.f5652b = bundle.getBoolean(SAVE_STATE_CHANGED, false);
            this.f5653c = bundle.getCharSequenceArray(SAVE_STATE_ENTRIES);
            this.f5654d = bundle.getCharSequenceArray(SAVE_STATE_ENTRY_VALUES);
            return;
        }
        MultiSelectListPreference listPreference = getListPreference();
        if (listPreference.getEntries() == null || listPreference.getEntryValues() == null) {
            throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
        }
        this.f5651a.clear();
        this.f5651a.addAll(listPreference.getValues());
        this.f5652b = false;
        this.f5653c = listPreference.getEntries();
        this.f5654d = listPreference.getEntryValues();
    }

    @Override // androidx.preference.PreferenceDialogFragment
    @Deprecated
    public void onDialogClosed(boolean z2) {
        MultiSelectListPreference listPreference = getListPreference();
        if (z2 && this.f5652b) {
            Set<String> set = this.f5651a;
            if (listPreference.callChangeListener(set)) {
                listPreference.setValues(set);
            }
        }
        this.f5652b = false;
    }

    @Override // androidx.preference.PreferenceDialogFragment, android.app.DialogFragment, android.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putStringArrayList(SAVE_STATE_VALUES, new ArrayList<>(this.f5651a));
        bundle.putBoolean(SAVE_STATE_CHANGED, this.f5652b);
        bundle.putCharSequenceArray(SAVE_STATE_ENTRIES, this.f5653c);
        bundle.putCharSequenceArray(SAVE_STATE_ENTRY_VALUES, this.f5654d);
    }
}
