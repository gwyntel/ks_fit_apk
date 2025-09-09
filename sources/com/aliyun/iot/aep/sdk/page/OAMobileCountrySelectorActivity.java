package com.aliyun.iot.aep.sdk.page;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.model.CountrySort;
import com.alibaba.sdk.android.openaccount.ui.widget.SiderBar;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.framework.R;
import com.aliyun.iot.aep.sdk.threadpool.ThreadPool;
import com.aliyun.iot.link.ui.component.LinkToast;
import com.aliyun.iot.link.ui.component.LoadingCompact;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes3.dex */
public class OAMobileCountrySelectorActivity extends AppCompatActivity {

    /* renamed from: a, reason: collision with root package name */
    protected CountrySiderBar f11986a;

    /* renamed from: b, reason: collision with root package name */
    protected GetCountryNameSort f11987b;

    /* renamed from: c, reason: collision with root package name */
    private ListView f11988c;

    /* renamed from: d, reason: collision with root package name */
    private CountryCodeAdapter f11989d;

    /* renamed from: e, reason: collision with root package name */
    private a f11990e = new a();

    /* renamed from: f, reason: collision with root package name */
    private SharedPreferences f11991f;

    class a implements Comparator<CountrySort> {
        a() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(CountrySort countrySort, CountrySort countrySort2) {
            if (countrySort.sortLetters.equals("@") || countrySort2.sortLetters.equals(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                return -1;
            }
            if (countrySort.sortLetters.equals(MqttTopic.MULTI_LEVEL_WILDCARD) || countrySort2.sortLetters.equals("@")) {
                return 1;
            }
            return countrySort.sortLetters.compareTo(countrySort2.sortLetters);
        }
    }

    public static void start(Activity activity) {
        activity.startActivityForResult(new Intent(activity, (Class<?>) OAMobileCountrySelectorActivity.class), RequestCode.MOBILE_COUNTRY_SELECTOR_REQUEST);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(@Nullable Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        setContentView(R.layout.sdk_framework_ali_sdk_openaccount_mobile_country_selector2);
        this.f11991f = getSharedPreferences("ilop_sp", 0);
        this.f11988c = (ListView) findViewById(R.id.country_list);
        this.f11986a = (CountrySiderBar) findViewById(R.id.country_sidebar);
        this.f11987b = new GetCountryNameSort();
        this.f11988c.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.aliyun.iot.aep.sdk.page.OAMobileCountrySelectorActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
                CountrySort countrySort = (CountrySort) OAMobileCountrySelectorActivity.this.f11989d.getItem(i2);
                OAMobileCountrySelectorActivity.this.f11991f.edit().putString("phoneCode", countrySort.code).apply();
                Intent intent = new Intent();
                intent.putExtra(MLApplicationSetting.BundleKeyConstants.AppInfo.COUNTRY_CODE, countrySort.code);
                OAMobileCountrySelectorActivity.this.setResult(-1, intent);
                OAMobileCountrySelectorActivity.this.finish();
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main);
        OATitleBar oATitleBar = (OATitleBar) findViewById(R.id.oat_title);
        oATitleBar.setType(0);
        oATitleBar.setTitle(getString(R.string.account_select_country_code));
        linearLayout.setBackgroundColor(-1);
        oATitleBar.setBackClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.aep.sdk.page.OAMobileCountrySelectorActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OAMobileCountrySelectorActivity.this.finish();
            }
        });
        this.f11986a.setInterval(0.0f);
        this.f11986a.setOnTouchingLetterChangedListener(new SiderBar.OnTouchingLetterChangedListener() { // from class: com.aliyun.iot.aep.sdk.page.OAMobileCountrySelectorActivity.3
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.SiderBar.OnTouchingLetterChangedListener
            public void onTouchingLetterChanged(String str) {
                int positionForSection;
                if (OAMobileCountrySelectorActivity.this.f11989d == null || (positionForSection = OAMobileCountrySelectorActivity.this.f11989d.getPositionForSection(str.charAt(0))) == -1) {
                    return;
                }
                OAMobileCountrySelectorActivity.this.f11988c.setSelection(positionForSection);
            }
        });
        LoadingCompact.showLoading(this);
        IoTSmart.getCountryList(new IoTSmart.ICountryListGetCallBack() { // from class: com.aliyun.iot.aep.sdk.page.OAMobileCountrySelectorActivity.4
            @Override // com.aliyun.iot.aep.sdk.IoTSmart.ICountryListGetCallBack
            public void onFail(String str, int i2, final String str2) {
                ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.aliyun.iot.aep.sdk.page.OAMobileCountrySelectorActivity.4.2
                    @Override // java.lang.Runnable
                    public void run() {
                        LoadingCompact.dismissLoading(OAMobileCountrySelectorActivity.this);
                        LinkToast.makeText(OAMobileCountrySelectorActivity.this, str2);
                    }
                });
            }

            @Override // com.aliyun.iot.aep.sdk.IoTSmart.ICountryListGetCallBack
            public void onSucess(final List<IoTSmart.Country> list) {
                ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.aliyun.iot.aep.sdk.page.OAMobileCountrySelectorActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LoadingCompact.dismissLoading(OAMobileCountrySelectorActivity.this);
                        List list2 = list;
                        if (list2 == null || list2.isEmpty()) {
                            return;
                        }
                        OAMobileCountrySelectorActivity.this.b((List<IoTSmart.Country>) list);
                    }
                });
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        LoadingCompact.dismissLoading(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(List<IoTSmart.Country> list) {
        boolean zEqualsIgnoreCase;
        ArrayList arrayList = new ArrayList();
        try {
            zEqualsIgnoreCase = IoTSmart.getLanguage().equalsIgnoreCase("zh-CN");
        } catch (Exception unused) {
            zEqualsIgnoreCase = false;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            IoTSmart.Country country = list.get(i2);
            CountrySort countrySort = new CountrySort();
            countrySort.code = country.code;
            if (zEqualsIgnoreCase) {
                String sortLetterBySortKey = this.f11987b.getSortLetterBySortKey(country.pinyin);
                if (sortLetterBySortKey == null) {
                    sortLetterBySortKey = this.f11987b.getSortLetter(country.areaName);
                }
                countrySort.sortLetters = sortLetterBySortKey;
                countrySort.displayName = country.areaName;
            } else {
                countrySort.sortLetters = this.f11987b.getSortLetterBySortKey(country.areaName);
                countrySort.displayName = country.areaName;
            }
            countrySort.englishName = country.isoCode;
            arrayList.add(countrySort);
        }
        List<CountrySort> listA = a(arrayList);
        Collections.sort(arrayList, this.f11990e);
        arrayList.addAll(0, listA);
        CountryCodeAdapter countryCodeAdapter = new CountryCodeAdapter(this, arrayList);
        this.f11989d = countryCodeAdapter;
        this.f11988c.setAdapter((ListAdapter) countryCodeAdapter);
    }

    protected List<CountrySort> a(List<CountrySort> list) {
        ArrayList arrayList = new ArrayList();
        try {
            CountrySort countrySortA = a("CHN", list);
            Objects.requireNonNull(countrySortA);
            arrayList.add(CountrySort.getCountryHot(countrySortA));
            CountrySort countrySortA2 = a("FRA", list);
            Objects.requireNonNull(countrySortA2);
            arrayList.add(CountrySort.getCountryHot(countrySortA2));
            CountrySort countrySortA3 = a("DEU", list);
            Objects.requireNonNull(countrySortA3);
            arrayList.add(CountrySort.getCountryHot(countrySortA3));
            CountrySort countrySortA4 = a("JPN", list);
            Objects.requireNonNull(countrySortA4);
            arrayList.add(CountrySort.getCountryHot(countrySortA4));
            CountrySort countrySortA5 = a("KOR", list);
            Objects.requireNonNull(countrySortA5);
            arrayList.add(CountrySort.getCountryHot(countrySortA5));
            CountrySort countrySortA6 = a("RUS", list);
            Objects.requireNonNull(countrySortA6);
            arrayList.add(CountrySort.getCountryHot(countrySortA6));
            CountrySort countrySortA7 = a("ESP", list);
            Objects.requireNonNull(countrySortA7);
            arrayList.add(CountrySort.getCountryHot(countrySortA7));
            CountrySort countrySortA8 = a("GBR", list);
            Objects.requireNonNull(countrySortA8);
            arrayList.add(CountrySort.getCountryHot(countrySortA8));
        } catch (Exception unused) {
        }
        return arrayList;
    }

    private CountrySort a(String str, List<CountrySort> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (list.get(i2).englishName.equals(str)) {
                return list.get(i2);
            }
        }
        return null;
    }
}
