package androidx.preference;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class MultiSelectListPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    private static final String SAVE_STATE_CHANGED = "MultiSelectListPreferenceDialogFragmentCompat.changed";
    private static final String SAVE_STATE_ENTRIES = "MultiSelectListPreferenceDialogFragmentCompat.entries";
    private static final String SAVE_STATE_ENTRY_VALUES = "MultiSelectListPreferenceDialogFragmentCompat.entryValues";
    private static final String SAVE_STATE_VALUES = "MultiSelectListPreferenceDialogFragmentCompat.values";

    /* renamed from: a, reason: collision with root package name */
    Set f5656a = new HashSet();

    /* renamed from: b, reason: collision with root package name */
    boolean f5657b;

    /* renamed from: c, reason: collision with root package name */
    CharSequence[] f5658c;

    /* renamed from: d, reason: collision with root package name */
    CharSequence[] f5659d;

    private MultiSelectListPreference getListPreference() {
        return (MultiSelectListPreference) getPreference();
    }

    @NonNull
    public static MultiSelectListPreferenceDialogFragmentCompat newInstance(String str) {
        MultiSelectListPreferenceDialogFragmentCompat multiSelectListPreferenceDialogFragmentCompat = new MultiSelectListPreferenceDialogFragmentCompat();
        Bundle bundle = new Bundle(1);
        bundle.putString(KsProperty.Key, str);
        multiSelectListPreferenceDialogFragmentCompat.setArguments(bundle);
        return multiSelectListPreferenceDialogFragmentCompat;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    protected void e(AlertDialog.Builder builder) {
        super.e(builder);
        int length = this.f5659d.length;
        boolean[] zArr = new boolean[length];
        for (int i2 = 0; i2 < length; i2++) {
            zArr[i2] = this.f5656a.contains(this.f5659d[i2].toString());
        }
        builder.setMultiChoiceItems(this.f5658c, zArr, new DialogInterface.OnMultiChoiceClickListener() { // from class: androidx.preference.MultiSelectListPreferenceDialogFragmentCompat.1
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public void onClick(DialogInterface dialogInterface, int i3, boolean z2) {
                if (z2) {
                    MultiSelectListPreferenceDialogFragmentCompat multiSelectListPreferenceDialogFragmentCompat = MultiSelectListPreferenceDialogFragmentCompat.this;
                    multiSelectListPreferenceDialogFragmentCompat.f5657b = multiSelectListPreferenceDialogFragmentCompat.f5656a.add(multiSelectListPreferenceDialogFragmentCompat.f5659d[i3].toString()) | multiSelectListPreferenceDialogFragmentCompat.f5657b;
                } else {
                    MultiSelectListPreferenceDialogFragmentCompat multiSelectListPreferenceDialogFragmentCompat2 = MultiSelectListPreferenceDialogFragmentCompat.this;
                    multiSelectListPreferenceDialogFragmentCompat2.f5657b = multiSelectListPreferenceDialogFragmentCompat2.f5656a.remove(multiSelectListPreferenceDialogFragmentCompat2.f5659d[i3].toString()) | multiSelectListPreferenceDialogFragmentCompat2.f5657b;
                }
            }
        });
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.f5656a.clear();
            this.f5656a.addAll(bundle.getStringArrayList(SAVE_STATE_VALUES));
            this.f5657b = bundle.getBoolean(SAVE_STATE_CHANGED, false);
            this.f5658c = bundle.getCharSequenceArray(SAVE_STATE_ENTRIES);
            this.f5659d = bundle.getCharSequenceArray(SAVE_STATE_ENTRY_VALUES);
            return;
        }
        MultiSelectListPreference listPreference = getListPreference();
        if (listPreference.getEntries() == null || listPreference.getEntryValues() == null) {
            throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
        }
        this.f5656a.clear();
        this.f5656a.addAll(listPreference.getValues());
        this.f5657b = false;
        this.f5658c = listPreference.getEntries();
        this.f5659d = listPreference.getEntryValues();
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public void onDialogClosed(boolean z2) {
        if (z2 && this.f5657b) {
            MultiSelectListPreference listPreference = getListPreference();
            if (listPreference.callChangeListener(this.f5656a)) {
                listPreference.setValues(this.f5656a);
            }
        }
        this.f5657b = false;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putStringArrayList(SAVE_STATE_VALUES, new ArrayList<>(this.f5656a));
        bundle.putBoolean(SAVE_STATE_CHANGED, this.f5657b);
        bundle.putCharSequenceArray(SAVE_STATE_ENTRIES, this.f5658c);
        bundle.putCharSequenceArray(SAVE_STATE_ENTRY_VALUES, this.f5659d);
    }
}
