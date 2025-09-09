package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

/* loaded from: classes2.dex */
public class SeekBarPreference extends Preference {
    private static final String TAG = "SeekBarPreference";

    /* renamed from: a, reason: collision with root package name */
    int f5694a;

    /* renamed from: b, reason: collision with root package name */
    int f5695b;

    /* renamed from: c, reason: collision with root package name */
    boolean f5696c;

    /* renamed from: d, reason: collision with root package name */
    SeekBar f5697d;

    /* renamed from: e, reason: collision with root package name */
    boolean f5698e;

    /* renamed from: f, reason: collision with root package name */
    boolean f5699f;
    private int mMax;
    private final SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;
    private int mSeekBarIncrement;
    private final View.OnKeyListener mSeekBarKeyListener;
    private TextView mSeekBarValueTextView;
    private boolean mShowSeekBarValue;

    public SeekBarPreference(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: androidx.preference.SeekBarPreference.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i4, boolean z2) {
                if (z2) {
                    SeekBarPreference seekBarPreference = SeekBarPreference.this;
                    if (seekBarPreference.f5699f || !seekBarPreference.f5696c) {
                        seekBarPreference.D(seekBar);
                        return;
                    }
                }
                SeekBarPreference seekBarPreference2 = SeekBarPreference.this;
                seekBarPreference2.E(i4 + seekBarPreference2.f5695b);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                SeekBarPreference.this.f5696c = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBarPreference.this.f5696c = false;
                int progress = seekBar.getProgress();
                SeekBarPreference seekBarPreference = SeekBarPreference.this;
                if (progress + seekBarPreference.f5695b != seekBarPreference.f5694a) {
                    seekBarPreference.D(seekBar);
                }
            }
        };
        this.mSeekBarKeyListener = new View.OnKeyListener() { // from class: androidx.preference.SeekBarPreference.2
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i4, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                SeekBarPreference seekBarPreference = SeekBarPreference.this;
                if ((!seekBarPreference.f5698e && (i4 == 21 || i4 == 22)) || i4 == 23 || i4 == 66) {
                    return false;
                }
                SeekBar seekBar = seekBarPreference.f5697d;
                if (seekBar != null) {
                    return seekBar.onKeyDown(i4, keyEvent);
                }
                Log.e(SeekBarPreference.TAG, "SeekBar view is null and hence cannot be adjusted.");
                return false;
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SeekBarPreference, i2, i3);
        this.f5695b = typedArrayObtainStyledAttributes.getInt(R.styleable.SeekBarPreference_min, 0);
        setMax(typedArrayObtainStyledAttributes.getInt(R.styleable.SeekBarPreference_android_max, 100));
        setSeekBarIncrement(typedArrayObtainStyledAttributes.getInt(R.styleable.SeekBarPreference_seekBarIncrement, 0));
        this.f5698e = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SeekBarPreference_adjustable, true);
        this.mShowSeekBarValue = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SeekBarPreference_showSeekBarValue, false);
        this.f5699f = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SeekBarPreference_updatesContinuously, false);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void setValueInternal(int i2, boolean z2) {
        int i3 = this.f5695b;
        if (i2 < i3) {
            i2 = i3;
        }
        int i4 = this.mMax;
        if (i2 > i4) {
            i2 = i4;
        }
        if (i2 != this.f5694a) {
            this.f5694a = i2;
            E(i2);
            x(i2);
            if (z2) {
                k();
            }
        }
    }

    void D(SeekBar seekBar) {
        int progress = this.f5695b + seekBar.getProgress();
        if (progress != this.f5694a) {
            if (callChangeListener(Integer.valueOf(progress))) {
                setValueInternal(progress, false);
            } else {
                seekBar.setProgress(this.f5694a - this.f5695b);
                E(this.f5694a);
            }
        }
    }

    void E(int i2) {
        TextView textView = this.mSeekBarValueTextView;
        if (textView != null) {
            textView.setText(String.valueOf(i2));
        }
    }

    public int getMax() {
        return this.mMax;
    }

    public int getMin() {
        return this.f5695b;
    }

    public final int getSeekBarIncrement() {
        return this.mSeekBarIncrement;
    }

    public boolean getShowSeekBarValue() {
        return this.mShowSeekBarValue;
    }

    public boolean getUpdatesContinuously() {
        return this.f5699f;
    }

    public int getValue() {
        return this.f5694a;
    }

    public boolean isAdjustable() {
        return this.f5698e;
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(@NonNull PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnKeyListener(this.mSeekBarKeyListener);
        this.f5697d = (SeekBar) preferenceViewHolder.findViewById(R.id.seekbar);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.seekbar_value);
        this.mSeekBarValueTextView = textView;
        if (this.mShowSeekBarValue) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
            this.mSeekBarValueTextView = null;
        }
        SeekBar seekBar = this.f5697d;
        if (seekBar == null) {
            Log.e(TAG, "SeekBar view is null in onBindViewHolder.");
            return;
        }
        seekBar.setOnSeekBarChangeListener(this.mSeekBarChangeListener);
        this.f5697d.setMax(this.mMax - this.f5695b);
        int i2 = this.mSeekBarIncrement;
        if (i2 != 0) {
            this.f5697d.setKeyProgressIncrement(i2);
        } else {
            this.mSeekBarIncrement = this.f5697d.getKeyProgressIncrement();
        }
        this.f5697d.setProgress(this.f5694a - this.f5695b);
        E(this.f5694a);
        this.f5697d.setEnabled(isEnabled());
    }

    @Override // androidx.preference.Preference
    protected Object p(TypedArray typedArray, int i2) {
        return Integer.valueOf(typedArray.getInt(i2, 0));
    }

    @Override // androidx.preference.Preference
    protected void r(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.r(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.r(savedState.getSuperState());
        this.f5694a = savedState.f5702a;
        this.f5695b = savedState.f5703b;
        this.mMax = savedState.f5704c;
        k();
    }

    @Override // androidx.preference.Preference
    protected Parcelable s() {
        Parcelable parcelableS = super.s();
        if (isPersistent()) {
            return parcelableS;
        }
        SavedState savedState = new SavedState(parcelableS);
        savedState.f5702a = this.f5694a;
        savedState.f5703b = this.f5695b;
        savedState.f5704c = this.mMax;
        return savedState;
    }

    public void setAdjustable(boolean z2) {
        this.f5698e = z2;
    }

    public final void setMax(int i2) {
        int i3 = this.f5695b;
        if (i2 < i3) {
            i2 = i3;
        }
        if (i2 != this.mMax) {
            this.mMax = i2;
            k();
        }
    }

    public void setMin(int i2) {
        int i3 = this.mMax;
        if (i2 > i3) {
            i2 = i3;
        }
        if (i2 != this.f5695b) {
            this.f5695b = i2;
            k();
        }
    }

    public final void setSeekBarIncrement(int i2) {
        if (i2 != this.mSeekBarIncrement) {
            this.mSeekBarIncrement = Math.min(this.mMax - this.f5695b, Math.abs(i2));
            k();
        }
    }

    public void setShowSeekBarValue(boolean z2) {
        this.mShowSeekBarValue = z2;
        k();
    }

    public void setUpdatesContinuously(boolean z2) {
        this.f5699f = z2;
    }

    public void setValue(int i2) {
        setValueInternal(i2, true);
    }

    @Override // androidx.preference.Preference
    protected void t(Object obj) {
        if (obj == null) {
            obj = 0;
        }
        setValue(i(((Integer) obj).intValue()));
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.preference.SeekBarPreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        };

        /* renamed from: a, reason: collision with root package name */
        int f5702a;

        /* renamed from: b, reason: collision with root package name */
        int f5703b;

        /* renamed from: c, reason: collision with root package name */
        int f5704c;

        SavedState(Parcel parcel) {
            super(parcel);
            this.f5702a = parcel.readInt();
            this.f5703b = parcel.readInt();
            this.f5704c = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f5702a);
            parcel.writeInt(this.f5703b);
            parcel.writeInt(this.f5704c);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public SeekBarPreference(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public SeekBarPreference(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarPreferenceStyle);
    }

    public SeekBarPreference(@NonNull Context context) {
        this(context, null);
    }
}
