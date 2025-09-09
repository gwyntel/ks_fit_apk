package androidx.fragment.app;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class ListFragment extends Fragment {

    /* renamed from: a, reason: collision with root package name */
    ListAdapter f4279a;

    /* renamed from: b, reason: collision with root package name */
    ListView f4280b;

    /* renamed from: c, reason: collision with root package name */
    View f4281c;

    /* renamed from: d, reason: collision with root package name */
    TextView f4282d;

    /* renamed from: e, reason: collision with root package name */
    View f4283e;

    /* renamed from: f, reason: collision with root package name */
    View f4284f;

    /* renamed from: g, reason: collision with root package name */
    CharSequence f4285g;

    /* renamed from: h, reason: collision with root package name */
    boolean f4286h;
    private final Handler mHandler = new Handler();
    private final Runnable mRequestFocus = new Runnable() { // from class: androidx.fragment.app.ListFragment.1
        @Override // java.lang.Runnable
        public void run() {
            ListView listView = ListFragment.this.f4280b;
            listView.focusableViewAvailable(listView);
        }
    };
    private final AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() { // from class: androidx.fragment.app.ListFragment.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
            ListFragment.this.onListItemClick((ListView) adapterView, view, i2, j2);
        }
    };

    private void ensureList() {
        if (this.f4280b != null) {
            return;
        }
        View view = getView();
        if (view == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (view instanceof ListView) {
            this.f4280b = (ListView) view;
        } else {
            TextView textView = (TextView) view.findViewById(16711681);
            this.f4282d = textView;
            if (textView == null) {
                this.f4281c = view.findViewById(R.id.empty);
            } else {
                textView.setVisibility(8);
            }
            this.f4283e = view.findViewById(16711682);
            this.f4284f = view.findViewById(16711683);
            View viewFindViewById = view.findViewById(R.id.list);
            if (!(viewFindViewById instanceof ListView)) {
                if (viewFindViewById != null) {
                    throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
                }
                throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
            }
            ListView listView = (ListView) viewFindViewById;
            this.f4280b = listView;
            View view2 = this.f4281c;
            if (view2 != null) {
                listView.setEmptyView(view2);
            } else {
                CharSequence charSequence = this.f4285g;
                if (charSequence != null) {
                    this.f4282d.setText(charSequence);
                    this.f4280b.setEmptyView(this.f4282d);
                }
            }
        }
        this.f4286h = true;
        this.f4280b.setOnItemClickListener(this.mOnClickListener);
        ListAdapter listAdapter = this.f4279a;
        if (listAdapter != null) {
            this.f4279a = null;
            setListAdapter(listAdapter);
        } else if (this.f4283e != null) {
            setListShown(false, false);
        }
        this.mHandler.post(this.mRequestFocus);
    }

    @Nullable
    public ListAdapter getListAdapter() {
        return this.f4279a;
    }

    @NonNull
    public ListView getListView() {
        ensureList();
        return this.f4280b;
    }

    public long getSelectedItemId() {
        ensureList();
        return this.f4280b.getSelectedItemId();
    }

    public int getSelectedItemPosition() {
        ensureList();
        return this.f4280b.getSelectedItemPosition();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Context contextRequireContext = requireContext();
        FrameLayout frameLayout = new FrameLayout(contextRequireContext);
        LinearLayout linearLayout = new LinearLayout(contextRequireContext);
        linearLayout.setId(16711682);
        linearLayout.setOrientation(1);
        linearLayout.setVisibility(8);
        linearLayout.setGravity(17);
        linearLayout.addView(new ProgressBar(contextRequireContext, null, R.attr.progressBarStyleLarge), new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout2 = new FrameLayout(contextRequireContext);
        frameLayout2.setId(16711683);
        TextView textView = new TextView(contextRequireContext);
        textView.setId(16711681);
        textView.setGravity(17);
        frameLayout2.addView(textView, new FrameLayout.LayoutParams(-1, -1));
        ListView listView = new ListView(contextRequireContext);
        listView.setId(R.id.list);
        listView.setDrawSelectorOnTop(false);
        frameLayout2.addView(listView, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.addView(frameLayout2, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return frameLayout;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.f4280b = null;
        this.f4286h = false;
        this.f4284f = null;
        this.f4283e = null;
        this.f4281c = null;
        this.f4282d = null;
        super.onDestroyView();
    }

    public void onListItemClick(@NonNull ListView listView, @NonNull View view, int i2, long j2) {
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        ensureList();
    }

    @NonNull
    public final ListAdapter requireListAdapter() {
        ListAdapter listAdapter = getListAdapter();
        if (listAdapter != null) {
            return listAdapter;
        }
        throw new IllegalStateException("ListFragment " + this + " does not have a ListAdapter.");
    }

    public void setEmptyText(@Nullable CharSequence charSequence) {
        ensureList();
        TextView textView = this.f4282d;
        if (textView == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        textView.setText(charSequence);
        if (this.f4285g == null) {
            this.f4280b.setEmptyView(this.f4282d);
        }
        this.f4285g = charSequence;
    }

    public void setListAdapter(@Nullable ListAdapter listAdapter) {
        boolean z2 = this.f4279a != null;
        this.f4279a = listAdapter;
        ListView listView = this.f4280b;
        if (listView != null) {
            listView.setAdapter(listAdapter);
            if (this.f4286h || z2) {
                return;
            }
            setListShown(true, requireView().getWindowToken() != null);
        }
    }

    public void setListShown(boolean z2) {
        setListShown(z2, true);
    }

    public void setListShownNoAnimation(boolean z2) {
        setListShown(z2, false);
    }

    public void setSelection(int i2) {
        ensureList();
        this.f4280b.setSelection(i2);
    }

    private void setListShown(boolean z2, boolean z3) {
        ensureList();
        View view = this.f4283e;
        if (view == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (this.f4286h == z2) {
            return;
        }
        this.f4286h = z2;
        if (z2) {
            if (z3) {
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
                this.f4284f.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            } else {
                view.clearAnimation();
                this.f4284f.clearAnimation();
            }
            this.f4283e.setVisibility(8);
            this.f4284f.setVisibility(0);
            return;
        }
        if (z3) {
            view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            this.f4284f.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
        } else {
            view.clearAnimation();
            this.f4284f.clearAnimation();
        }
        this.f4283e.setVisibility(0);
        this.f4284f.setVisibility(8);
    }
}
