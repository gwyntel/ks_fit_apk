package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.security.SecurityGuardService;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.util.OpenAccountUIUtils;
import com.alibaba.sdk.android.openaccount.util.JSONUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

/* loaded from: classes2.dex */
public class InputBoxWithHistory extends AbsInputBoxWrapper {
    private static final String KEY_INPUT_HISTORY = "openaccount_input_history";
    public static final int MAX_LOGINID_HISTORY;
    public static String historySavedKey;
    protected TextView historyButton;
    protected List<String> inputHistory;
    protected ListView inputHistoryView;
    protected ArrayAdapter<String> inputHistoryViewAdapter;

    @Autowired
    private SecurityGuardService securityGuardService;
    protected boolean showHistory;

    static {
        int i2 = OpenAccountUIConfigs.AccountPasswordLoginFlow.historyAccountNum;
        if (i2 > 10) {
            i2 = 10;
        }
        MAX_LOGINID_HISTORY = i2;
        historySavedKey = KEY_INPUT_HISTORY;
    }

    public InputBoxWithHistory(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ResourceUtils.getRStyleableIntArray(context, "inputHistory"));
        int i2 = typedArrayObtainStyledAttributes.getInt(ResourceUtils.getRStyleable(context, "inputHistory_ali_sdk_openaccount_attrs_inputType"), 0);
        if (i2 != 0) {
            this.inputBoxWithClear.getEditText().setInputType(i2);
        }
        this.inputBoxWithClear.getEditText().setHint(typedArrayObtainStyledAttributes.getString(ResourceUtils.getRStyleable(context, "inputHistory_ali_sdk_openaccount_attrs_hint")));
        int integer = typedArrayObtainStyledAttributes.getInteger(ResourceUtils.getRStyleable(context, "inputHistory_ali_sdk_openaccount_attrs_input_maxLength"), 0);
        if (integer > 0) {
            this.inputBoxWithClear.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(integer)});
        }
        String string = typedArrayObtainStyledAttributes.getString(ResourceUtils.getRStyleable(context, "inputHistory_ali_sdk_openaccount_attrs_storeKey"));
        if (string != null) {
            historySavedKey = string;
        }
        typedArrayObtainStyledAttributes.recycle();
        this.historyButton = (TextView) findViewById("open_history");
        loadSavedInputHistory();
        List<String> list = this.inputHistory;
        if (list == null || list.size() == 0) {
            this.historyButton.setVisibility(8);
        }
        List<String> list2 = this.inputHistory;
        if (list2 != null && list2.size() > 0) {
            getEditText().setText(this.inputHistory.get(0));
        }
        this.historyButton.setTypeface(OpenAccountUIUtils.getDefaultFont());
        this.historyButton.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithHistory.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                InputBoxWithHistory.this.showInputHistory(view, !r0.showHistory);
            }
        });
        this.inputBoxWithClear.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithHistory.2
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z2) {
                if (z2) {
                    boolean z3 = view instanceof EditText;
                }
            }
        });
    }

    private void loadSavedInputHistory() {
        this.inputHistory = new ArrayList(MAX_LOGINID_HISTORY);
        try {
            String valueFromDynamicDataStore = this.securityGuardService.getValueFromDynamicDataStore(historySavedKey);
            if (valueFromDynamicDataStore == null || valueFromDynamicDataStore.length() <= 0) {
                return;
            }
            JSONArray jSONArray = new JSONArray(valueFromDynamicDataStore);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                if (!TextUtils.isEmpty(jSONArray.optString(i2))) {
                    this.inputHistory.add(jSONArray.getString(i2));
                }
            }
        } catch (Exception e2) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "loadSavedInputHistory error:" + e2.getMessage(), e2);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.AbsInputBoxWrapper
    public EditText getEditText() {
        return this.inputBoxWithClear.getEditText();
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_input_box_with_history";
    }

    public boolean saveInputHistory(String str) {
        boolean z2;
        if (str == null || str.length() == 0) {
            return false;
        }
        List<String> list = this.inputHistory;
        if (list == null) {
            this.inputHistory = new ArrayList(MAX_LOGINID_HISTORY);
            z2 = false;
        } else {
            Iterator<String> it = list.iterator();
            z2 = false;
            while (it.hasNext()) {
                if (str.equals(it.next())) {
                    it.remove();
                    z2 = true;
                }
            }
            if (this.inputHistory.size() >= MAX_LOGINID_HISTORY) {
                for (int size = this.inputHistory.size() - 1; size >= MAX_LOGINID_HISTORY - 1; size--) {
                    this.inputHistory.remove(size);
                }
            }
        }
        this.inputHistory.add(0, str);
        this.securityGuardService.putValueInDynamicDataStore(historySavedKey, JSONUtils.toJsonArray(this.inputHistory).toString());
        this.inputHistoryViewAdapter.notifyDataSetChanged();
        return z2;
    }

    public void setHistoryView(ListView listView) {
        this.inputHistoryView = listView;
        listView.setVisibility(8);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(listView.getContext(), ResourceUtils.getRLayout(listView.getContext(), "ali_sdk_openaccount_input_history_item"), this.inputHistory) { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithHistory.3
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int i2, View view, ViewGroup viewGroup) {
                View view2 = super.getView(i2, view, viewGroup);
                if (view2 instanceof TextView) {
                    ((TextView) view2).setTransformationMethod(SensitiveTransformationMethod.INSTANCE);
                }
                return view2;
            }
        };
        this.inputHistoryViewAdapter = arrayAdapter;
        this.inputHistoryView.setAdapter((ListAdapter) arrayAdapter);
        this.inputHistoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithHistory.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
                List<String> list = InputBoxWithHistory.this.inputHistory;
                if (list == null || list.size() <= i2) {
                    return;
                }
                InputBoxWithHistory.this.inputBoxWithClear.getEditText().setText(InputBoxWithHistory.this.inputHistory.get(i2));
                InputBoxWithHistory.this.showInputHistory(view, false);
            }
        });
    }

    public void showInputHistory(View view, boolean z2) {
        if (this.showHistory == z2) {
            return;
        }
        this.showHistory = z2;
        if (z2) {
            this.historyButton.setText(ResourceUtils.getString(view.getContext(), "ali_sdk_openaccount_dynamic_icon_arrow_up"));
            this.inputHistoryView.setVisibility(0);
        } else {
            this.historyButton.setText(ResourceUtils.getString(view.getContext(), "ali_sdk_openaccount_dynamic_icon_arrow_down"));
            this.inputHistoryView.setVisibility(8);
        }
    }

    public void updateHistoryView(String str) {
        if (this.inputHistoryView == null) {
            return;
        }
        showInputHistory(this, true);
        final ArrayList arrayList = new ArrayList();
        for (String str2 : this.inputHistory) {
            if (!TextUtils.isEmpty(str2) && str2.startsWith(str)) {
                arrayList.add(str2);
            } else if (TextUtils.isEmpty(str2)) {
                arrayList.add(str2);
            }
        }
        if (arrayList.size() == 0) {
            showInputHistory(this, false);
            return;
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.inputHistoryView.getContext(), ResourceUtils.getRLayout(this.inputHistoryView.getContext(), "ali_sdk_openaccount_input_history_item"), arrayList) { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithHistory.5
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int i2, View view, ViewGroup viewGroup) {
                View view2 = super.getView(i2, view, viewGroup);
                if (view2 instanceof TextView) {
                    ((TextView) view2).setTransformationMethod(SensitiveTransformationMethod.INSTANCE);
                }
                return view2;
            }
        };
        this.inputHistoryViewAdapter = arrayAdapter;
        this.inputHistoryView.setAdapter((ListAdapter) arrayAdapter);
        this.inputHistoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithHistory.6
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
                List list = arrayList;
                if (list == null || list.size() <= i2) {
                    return;
                }
                InputBoxWithHistory.this.inputBoxWithClear.getEditText().setText((CharSequence) arrayList.get(i2));
                InputBoxWithHistory.this.showInputHistory(view, false);
            }
        });
    }
}
