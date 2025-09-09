package androidx.preference;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.kingsmith.miot.KsProperty;

/* loaded from: classes2.dex */
public class EditTextPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    private static final String SAVE_STATE_TEXT = "EditTextPreferenceDialogFragment.text";
    private static final int SHOW_REQUEST_TIMEOUT = 1000;
    private EditText mEditText;
    private CharSequence mText;
    private final Runnable mShowSoftInputRunnable = new Runnable() { // from class: androidx.preference.EditTextPreferenceDialogFragmentCompat.1
        @Override // java.lang.Runnable
        public void run() {
            EditTextPreferenceDialogFragmentCompat.this.g();
        }
    };
    private long mShowRequestTime = -1;

    private EditTextPreference getEditTextPreference() {
        return (EditTextPreference) getPreference();
    }

    private boolean hasPendingShowSoftInputRequest() {
        long j2 = this.mShowRequestTime;
        return j2 != -1 && j2 + 1000 > SystemClock.currentThreadTimeMillis();
    }

    @NonNull
    public static EditTextPreferenceDialogFragmentCompat newInstance(String str) {
        EditTextPreferenceDialogFragmentCompat editTextPreferenceDialogFragmentCompat = new EditTextPreferenceDialogFragmentCompat();
        Bundle bundle = new Bundle(1);
        bundle.putString(KsProperty.Key, str);
        editTextPreferenceDialogFragmentCompat.setArguments(bundle);
        return editTextPreferenceDialogFragmentCompat;
    }

    private void setPendingShowSoftInputRequest(boolean z2) {
        this.mShowRequestTime = z2 ? SystemClock.currentThreadTimeMillis() : -1L;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    protected boolean b() {
        return true;
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    protected void c(View view) {
        super.c(view);
        EditText editText = (EditText) view.findViewById(android.R.id.edit);
        this.mEditText = editText;
        if (editText == null) {
            throw new IllegalStateException("Dialog view must contain an EditText with id @android:id/edit");
        }
        editText.requestFocus();
        this.mEditText.setText(this.mText);
        EditText editText2 = this.mEditText;
        editText2.setSelection(editText2.getText().length());
        if (getEditTextPreference().D() != null) {
            getEditTextPreference().D().onBindEditText(this.mEditText);
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    protected void f() {
        setPendingShowSoftInputRequest(true);
        g();
    }

    void g() {
        if (hasPendingShowSoftInputRequest()) {
            EditText editText = this.mEditText;
            if (editText == null || !editText.isFocused()) {
                setPendingShowSoftInputRequest(false);
            } else if (((InputMethodManager) this.mEditText.getContext().getSystemService("input_method")).showSoftInput(this.mEditText, 0)) {
                setPendingShowSoftInputRequest(false);
            } else {
                this.mEditText.removeCallbacks(this.mShowSoftInputRunnable);
                this.mEditText.postDelayed(this.mShowSoftInputRunnable, 50L);
            }
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.mText = getEditTextPreference().getText();
        } else {
            this.mText = bundle.getCharSequence(SAVE_STATE_TEXT);
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat
    public void onDialogClosed(boolean z2) {
        if (z2) {
            String string = this.mEditText.getText().toString();
            EditTextPreference editTextPreference = getEditTextPreference();
            if (editTextPreference.callChangeListener(string)) {
                editTextPreference.setText(string);
            }
        }
    }

    @Override // androidx.preference.PreferenceDialogFragmentCompat, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putCharSequence(SAVE_STATE_TEXT, this.mText);
    }
}
