package com.alibaba.sdk.android.openaccount.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.ui.model.CountryComparator;
import com.alibaba.sdk.android.openaccount.ui.model.CountrySort;
import com.alibaba.sdk.android.openaccount.ui.model.GetCountryNameSort;
import com.alibaba.sdk.android.openaccount.ui.util.LocaleUtils;
import com.alibaba.sdk.android.openaccount.ui.widget.CountrySortAdapter;
import com.alibaba.sdk.android.openaccount.ui.widget.SiderBar;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MobileCountrySelectorActivity extends ActivityTemplate {
    public static final int COUNTRY_CODE_REQUEST = 12;
    protected static final String COUNTRY_NUM_LIST_KEY = "countryNumList";
    protected static final String MAX_COUNTRY_VERSION_KEY = "maxCountryVersion";
    protected static final String PREF_FILE_NAME = "openaccount_country_list";
    protected GetCountryNameSort countryChangeUtil;
    protected CountryComparator countryComparator;
    protected TextView mCountryDialog;
    protected List<CountrySort> mCountryList = null;
    protected CountrySortAdapter mCountryListAdapter = null;
    protected ListView mCountryListView;
    protected EditText mSearchBox;
    protected SiderBar mSiderBar;

    protected class GetCountryTask extends TaskWithDialog<Void, Void, List<CountrySort>> {
        public GetCountryTask(Activity activity) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doWhenException(Throwable th) {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0066  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.util.List<com.alibaba.sdk.android.openaccount.ui.model.CountrySort> asyncExecute(java.lang.Void... r8) {
            /*
                r7 = this;
                com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity r8 = com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity.this
                java.lang.String r0 = "openaccount_country_list"
                r1 = 0
                android.content.SharedPreferences r8 = r8.getSharedPreferences(r0, r1)
                java.lang.String r0 = "countryNumList"
                r2 = 0
                java.lang.String r0 = r8.getString(r0, r2)
                boolean r3 = android.text.TextUtils.isEmpty(r0)
                java.lang.String r4 = "oa_ui"
                if (r3 == 0) goto L1b
            L18:
                r8 = r2
                r3 = r8
                goto L2e
            L1b:
                java.lang.String r3 = "maxCountryVersion"
                java.lang.String r8 = r8.getString(r3, r2)
                org.json.JSONArray r3 = new org.json.JSONArray     // Catch: java.lang.Exception -> L27
                r3.<init>(r0)     // Catch: java.lang.Exception -> L27
                goto L2e
            L27:
                r8 = move-exception
                java.lang.String r0 = "fail to parse local cached country list, will use server side"
                com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.e(r4, r0, r8)
                goto L18
            L2e:
                java.util.HashMap r0 = new java.util.HashMap
                r0.<init>()
                java.lang.String r5 = "queryString"
                java.lang.String r6 = "q=test&rows=100"
                r0.put(r5, r6)
                java.lang.String r5 = "version"
                r0.put(r5, r8)
                java.lang.String r8 = "searchCountryRequest"
                java.lang.String r5 = "searchcountry"
                com.alibaba.sdk.android.openaccount.rpc.model.RpcResponse r8 = com.alibaba.sdk.android.openaccount.util.RpcUtils.pureInvokeWithRiskControlInfo(r8, r0, r5)
                if (r8 == 0) goto L66
                org.json.JSONArray r0 = r8.arrayData
                if (r0 == 0) goto L66
                int r8 = r8.code     // Catch: java.lang.Exception -> L5e
                r5 = 1
                if (r8 != r5) goto L66
                if (r0 == 0) goto L67
                com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity r8 = com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity.this     // Catch: java.lang.Exception -> L5c
                java.util.List r2 = r8.readArray(r0)     // Catch: java.lang.Exception -> L5c
                r1 = r5
                goto L67
            L5c:
                r8 = move-exception
                goto L60
            L5e:
                r8 = move-exception
                r0 = r2
            L60:
                java.lang.String r5 = "fail to parse the server side response"
                com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.e(r4, r5, r8)
                goto L67
            L66:
                r0 = r2
            L67:
                if (r2 != 0) goto L78
                if (r3 == 0) goto L78
                com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity r8 = com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity.this     // Catch: java.lang.Exception -> L72
                java.util.List r2 = r8.readArray(r3)     // Catch: java.lang.Exception -> L72
                goto L78
            L72:
                r8 = move-exception
                java.lang.String r3 = "fail to parse the local country list"
                com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.e(r4, r3, r8)
            L78:
                if (r1 == 0) goto L84
                com.alibaba.sdk.android.openaccount.executor.ExecutorService r8 = r7.executorService
                com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity$GetCountryTask$1 r1 = new com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity$GetCountryTask$1
                r1.<init>()
                r8.postTask(r1)
            L84:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity.GetCountryTask.asyncExecute(java.lang.Void[]):java.util.List");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(List<CountrySort> list) {
            if (list == null || list.size() <= 0) {
                return;
            }
            MobileCountrySelectorActivity mobileCountrySelectorActivity = MobileCountrySelectorActivity.this;
            List<CountrySort> hot5 = mobileCountrySelectorActivity.getHot5(mobileCountrySelectorActivity.mCountryList);
            MobileCountrySelectorActivity mobileCountrySelectorActivity2 = MobileCountrySelectorActivity.this;
            Collections.sort(mobileCountrySelectorActivity2.mCountryList, mobileCountrySelectorActivity2.countryComparator);
            list.addAll(0, hot5);
            MobileCountrySelectorActivity.this.mCountryListAdapter = new CountrySortAdapter(MobileCountrySelectorActivity.this, list);
            MobileCountrySelectorActivity mobileCountrySelectorActivity3 = MobileCountrySelectorActivity.this;
            mobileCountrySelectorActivity3.mCountryListView.setAdapter((ListAdapter) mobileCountrySelectorActivity3.mCountryListAdapter);
        }
    }

    protected AsyncTask<Void, Void, List<CountrySort>> getCountryList() {
        return new GetCountryTask(this).execute(new Void[0]);
    }

    protected List<CountrySort> getHot5(List<CountrySort> list) {
        ArrayList arrayList = new ArrayList();
        if (list.size() >= 5) {
            for (int i2 = 0; i2 < 5; i2++) {
                arrayList.add(CountrySort.getCountryHot(list.get(i2)));
            }
        }
        return arrayList;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_mobile_country_selector";
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSearchBox = (EditText) findViewById("search_box");
        this.mCountryListView = (ListView) findViewById("country_list");
        this.mCountryDialog = (TextView) findViewById("country_dialog");
        SiderBar siderBar = (SiderBar) findViewById("country_sidebar");
        this.mSiderBar = siderBar;
        siderBar.setTextView(this.mCountryDialog);
        this.countryComparator = new CountryComparator();
        this.countryChangeUtil = new GetCountryNameSort();
        this.mCountryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
                Intent intent = new Intent();
                intent.putExtra(MLApplicationSetting.BundleKeyConstants.AppInfo.COUNTRY_CODE, ((CountrySort) MobileCountrySelectorActivity.this.mCountryListAdapter.getItem(i2)).code);
                MobileCountrySelectorActivity.this.setResult(-1, intent);
                MobileCountrySelectorActivity.this.finish();
            }
        });
        this.mSearchBox.addTextChangedListener(new TextWatcher() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                MobileCountrySelectorActivity mobileCountrySelectorActivity = MobileCountrySelectorActivity.this;
                if (mobileCountrySelectorActivity.mCountryList == null) {
                    return;
                }
                String string = mobileCountrySelectorActivity.mSearchBox.getText().toString();
                if (string.length() > 0) {
                    MobileCountrySelectorActivity mobileCountrySelectorActivity2 = MobileCountrySelectorActivity.this;
                    MobileCountrySelectorActivity.this.mCountryListAdapter.updateListView((ArrayList) mobileCountrySelectorActivity2.countryChangeUtil.search(string, mobileCountrySelectorActivity2.mCountryList));
                } else {
                    MobileCountrySelectorActivity mobileCountrySelectorActivity3 = MobileCountrySelectorActivity.this;
                    mobileCountrySelectorActivity3.mCountryListAdapter.updateListView(mobileCountrySelectorActivity3.mCountryList);
                }
                MobileCountrySelectorActivity.this.mCountryListView.setSelection(0);
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        });
        this.mSiderBar.setOnTouchingLetterChangedListener(new SiderBar.OnTouchingLetterChangedListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.MobileCountrySelectorActivity.3
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.SiderBar.OnTouchingLetterChangedListener
            public void onTouchingLetterChanged(String str) {
                int positionForSection;
                CountrySortAdapter countrySortAdapter = MobileCountrySelectorActivity.this.mCountryListAdapter;
                if (countrySortAdapter == null || (positionForSection = countrySortAdapter.getPositionForSection(str.charAt(0))) == -1) {
                    return;
                }
                MobileCountrySelectorActivity.this.mCountryListView.setSelection(positionForSection);
            }
        });
        getCountryList();
        useCustomAttrs(this, this.attrs);
    }

    protected List<CountrySort> readArray(JSONArray jSONArray) throws JSONException {
        try {
            int length = jSONArray.length();
            if (length <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < length; i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                if (jSONObject != null) {
                    CountrySort countrySort = new CountrySort();
                    countrySort.name = jSONObject.optString("areaName");
                    countrySort.code = jSONObject.optString("code");
                    countrySort.englishName = jSONObject.optString("areaEnglishName");
                    countrySort.tranditionalName = jSONObject.optString("areaChineseTranditionalName");
                    countrySort.pinyin = jSONObject.optString("pinyin");
                    countrySort.sortWeightKey = jSONObject.optString("sortWeightKey");
                    countrySort.checkKey = jSONObject.optString("checkKey");
                    countrySort.domain = jSONObject.optString("domainAbbreviation");
                    countrySort.version = jSONObject.optInt("version");
                    String currentLocale = LocaleUtils.getCurrentLocale();
                    if (LocaleUtils.isZHLocale(currentLocale)) {
                        String sortLetterBySortKey = this.countryChangeUtil.getSortLetterBySortKey(countrySort.pinyin);
                        if (sortLetterBySortKey == null) {
                            sortLetterBySortKey = this.countryChangeUtil.getSortLetter(countrySort.name);
                        }
                        countrySort.sortLetters = sortLetterBySortKey;
                        if (LocaleUtils.isUseTraditionChinese(currentLocale)) {
                            countrySort.displayName = countrySort.tranditionalName;
                        } else {
                            countrySort.displayName = countrySort.name;
                        }
                    } else {
                        String sortLetterBySortKey2 = this.countryChangeUtil.getSortLetterBySortKey(countrySort.englishName);
                        if (sortLetterBySortKey2 == null) {
                            sortLetterBySortKey2 = this.countryChangeUtil.getSortLetter(countrySort.name);
                        }
                        countrySort.sortLetters = sortLetterBySortKey2;
                        countrySort.displayName = countrySort.englishName;
                    }
                    arrayList.add(countrySort);
                }
            }
            this.mCountryList = arrayList;
            return arrayList;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
