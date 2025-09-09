package com.aliyun.iot.aep.sdk.page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.openaccount.ui.widget.SiderBar;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.framework.R;
import com.aliyun.iot.aep.sdk.framework.config.GlobalConfig;
import com.aliyun.iot.aep.sdk.page.LocateHandler;
import com.aliyun.iot.aep.sdk.threadpool.ThreadPool;
import com.aliyun.iot.link.ui.component.LinkToast;
import com.aliyun.iot.link.ui.component.LoadingCompact;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class CountryListActivity extends AppCompatActivity implements View.OnClickListener, LocateHandler.OnLocationListener {
    public static final String TAG = "CountryListActivity";

    /* renamed from: a, reason: collision with root package name */
    private BaseAdapter f11926a;

    /* renamed from: b, reason: collision with root package name */
    private ListView f11927b;

    /* renamed from: c, reason: collision with root package name */
    private TextView f11928c;

    /* renamed from: d, reason: collision with root package name */
    private CountrySiderBar f11929d;

    /* renamed from: e, reason: collision with root package name */
    private e f11930e;

    /* renamed from: g, reason: collision with root package name */
    private IoTSmart.Country f11932g;

    /* renamed from: i, reason: collision with root package name */
    private WindowManager f11934i;

    /* renamed from: j, reason: collision with root package name */
    private View f11935j;

    /* renamed from: k, reason: collision with root package name */
    private TextView f11936k;

    /* renamed from: l, reason: collision with root package name */
    private LocateTask f11937l;

    /* renamed from: m, reason: collision with root package name */
    private Button f11938m;

    /* renamed from: n, reason: collision with root package name */
    private ImageView f11939n;

    /* renamed from: o, reason: collision with root package name */
    private HashMap<String, Integer> f11940o;

    /* renamed from: p, reason: collision with root package name */
    private String[] f11941p;

    /* renamed from: f, reason: collision with root package name */
    private ArrayList<IoTSmart.Country> f11931f = new ArrayList<>();

    /* renamed from: h, reason: collision with root package name */
    private boolean f11933h = false;

    /* renamed from: q, reason: collision with root package name */
    private Handler f11942q = new Handler();

    /* renamed from: r, reason: collision with root package name */
    private Handler f11943r = new Handler();

    class a implements AdapterView.OnItemClickListener {
        a() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
            if (i2 <= 0) {
                return;
            }
            IoTSmart.Country country = (IoTSmart.Country) CountryListActivity.this.f11927b.getAdapter().getItem(i2);
            if (CountryListActivity.this.f11939n != null && !Objects.equals(CountryListActivity.this.f11932g, country)) {
                CountryListActivity.this.f11939n.setVisibility(8);
            }
            CountryListActivity.this.f11932g = country;
            CountryListActivity.this.f11926a.notifyDataSetChanged();
        }
    }

    static class b {
        private static boolean a() {
            try {
                return IoTSmart.getLanguage().equalsIgnoreCase("zh-CN");
            } catch (Exception unused) {
                return false;
            }
        }

        static String b(@NonNull IoTSmart.Country country) {
            try {
                return a() ? country.pinyin : country.areaName;
            } catch (NullPointerException unused) {
                return "";
            }
        }

        static String a(@NonNull IoTSmart.Country country) {
            try {
                return a() ? country.pinyin.substring(0, 1) : country.areaName.substring(0, 1);
            } catch (IndexOutOfBoundsException | NullPointerException unused) {
                return "";
            }
        }
    }

    class c implements SiderBar.OnTouchingLetterChangedListener {
        private c() {
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.widget.SiderBar.OnTouchingLetterChangedListener
        public void onTouchingLetterChanged(String str) {
            Integer num;
            if (CountryListActivity.this.f11940o.get(str) == null || (num = (Integer) CountryListActivity.this.f11940o.get(str)) == null) {
                return;
            }
            CountryListActivity.this.f11927b.setSelection(num.intValue() + 1);
            CountryListActivity.this.f11928c.setText(CountryListActivity.this.f11941p[num.intValue()]);
            CountryListActivity.this.f11928c.setVisibility(0);
            CountryListActivity.this.f11943r.removeCallbacks(CountryListActivity.this.f11930e);
            CountryListActivity.this.f11943r.postDelayed(CountryListActivity.this.f11930e, 700L);
        }
    }

    class d extends BaseAdapter {

        /* renamed from: b, reason: collision with root package name */
        private LayoutInflater f11959b;

        /* renamed from: c, reason: collision with root package name */
        private List<IoTSmart.Country> f11960c = new ArrayList();

        class a {

            /* renamed from: a, reason: collision with root package name */
            TextView f11961a;

            /* renamed from: b, reason: collision with root package name */
            TextView f11962b;

            /* renamed from: c, reason: collision with root package name */
            ImageView f11963c;

            /* renamed from: d, reason: collision with root package name */
            View f11964d;

            /* renamed from: e, reason: collision with root package name */
            RelativeLayout f11965e;

            private a() {
            }
        }

        d(Context context, List<IoTSmart.Country> list) {
            this.f11959b = LayoutInflater.from(context);
            if (list != null) {
                this.f11960c.addAll(list);
            }
            a();
        }

        void a() {
            if (CountryListActivity.this.f11940o == null) {
                CountryListActivity.this.f11940o = new HashMap();
            }
            CountryListActivity.this.f11940o.clear();
            CountryListActivity.this.f11941p = new String[this.f11960c.size()];
            for (int i2 = 0; i2 < this.f11960c.size(); i2++) {
                if (this.f11960c.get(i2) != null) {
                    String strA = b.a(this.f11960c.get(i2));
                    int i3 = i2 - 1;
                    if (!(i3 >= 0 ? b.a(this.f11960c.get(i3)) : " ").equals(strA)) {
                        CountryListActivity.this.f11940o.put(strA, Integer.valueOf(i2));
                        CountryListActivity.this.f11941p[i2] = strA;
                    }
                }
            }
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.f11960c.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i2) {
            return this.f11960c.get(i2);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        @Override // android.widget.Adapter
        public View getView(int i2, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = this.f11959b.inflate(R.layout.sdk_framework_list_item, (ViewGroup) null);
                aVar = new a();
                aVar.f11961a = (TextView) view.findViewById(R.id.alpha);
                aVar.f11962b = (TextView) view.findViewById(R.id.name);
                aVar.f11963c = (ImageView) view.findViewById(R.id.code);
                aVar.f11964d = view.findViewById(R.id.view_item_top_line);
                aVar.f11965e = (RelativeLayout) view.findViewById(R.id.rl_item);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            aVar.f11965e.setBackgroundColor(-1);
            aVar.f11961a.setBackgroundColor(Color.parseColor("#FFF6F6F6"));
            aVar.f11961a.setTextColor(Color.parseColor("#FF999999"));
            aVar.f11962b.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            aVar.f11964d.setBackgroundColor(Color.parseColor("#FFF5F5F5"));
            IoTSmart.Country country = this.f11960c.get(i2);
            if (country == null) {
                return view;
            }
            aVar.f11962b.setText(country.areaName);
            if (CountryListActivity.this.f11932g == null || !country.domainAbbreviation.equals(CountryListActivity.this.f11932g.domainAbbreviation)) {
                aVar.f11963c.setVisibility(8);
            } else {
                aVar.f11963c.setVisibility(0);
            }
            String strA = b.a(country);
            int i3 = i2 - 1;
            if ((i3 >= 0 ? b.a(this.f11960c.get(i3)) : " ").equals(strA)) {
                aVar.f11961a.setVisibility(8);
                aVar.f11964d.setVisibility(0);
            } else {
                aVar.f11961a.setVisibility(0);
                aVar.f11961a.setText(strA);
                aVar.f11964d.setVisibility(8);
            }
            return view;
        }

        @Override // android.widget.BaseAdapter
        public void notifyDataSetChanged() {
            a();
            super.notifyDataSetChanged();
        }
    }

    class e implements Runnable {
        private e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            CountryListActivity.this.f11928c.setVisibility(8);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btn_select_country) {
            this.f11938m.setEnabled(false);
            if (this.f11932g != null) {
                if (GlobalConfig.getInstance().getCountrySelectCallBack() != null) {
                    GlobalConfig.getInstance().getCountrySelectCallBack().onCountrySelect(this.f11932g);
                }
                this.f11942q.postDelayed(new Runnable() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.6
                    @Override // java.lang.Runnable
                    public void run() {
                        CountryListActivity.this.finish();
                    }
                }, 500L);
            }
            this.f11942q.postDelayed(new Runnable() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.7
                @Override // java.lang.Runnable
                public void run() {
                    CountryListActivity.this.f11938m.setEnabled(true);
                }
            }, 300L);
        }
    }

    @Override // com.aliyun.iot.aep.sdk.page.LocateHandler.OnLocationListener
    public void onContinuedLocate(String str) {
        this.f11936k.setText(str);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        setContentView(R.layout.sdk_framework_country_list);
        OATitleBar oATitleBar = (OATitleBar) findViewById(R.id.country_title);
        this.f11938m = (Button) findViewById(R.id.btn_select_country);
        this.f11927b = (ListView) findViewById(R.id.country_list);
        this.f11929d = (CountrySiderBar) findViewById(R.id.countryLetterListView);
        oATitleBar.setType(0);
        oATitleBar.setTitle(getString(R.string.region_header_title));
        this.f11938m.setVisibility(0);
        this.f11938m.setOnClickListener(this);
        oATitleBar.setBackgroundColor(-1);
        this.f11927b.setBackgroundColor(-1);
        oATitleBar.setBackClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CountryListActivity.this.finish();
            }
        });
        this.f11932g = IoTSmart.getCountry();
        LoadingCompact.showLoading(this);
        IoTSmart.getCountryList(new IoTSmart.ICountryListGetCallBack() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.2
            @Override // com.aliyun.iot.aep.sdk.IoTSmart.ICountryListGetCallBack
            public void onFail(String str, int i2, final String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(CountryListActivity.TAG, str + i2 + str2);
                ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        LoadingCompact.showLoading(CountryListActivity.this);
                        LinkToast.makeText(CountryListActivity.this, str2);
                    }
                });
            }

            @Override // com.aliyun.iot.aep.sdk.IoTSmart.ICountryListGetCallBack
            public void onSucess(final List<IoTSmart.Country> list) {
                ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        LoadingCompact.dismissLoading(CountryListActivity.this);
                        List list2 = list;
                        if (list2 != null) {
                            String jSONString = JSON.toJSONString(list2);
                            int i2 = 0;
                            while (i2 <= jSONString.length() / 600) {
                                int i3 = i2 * 600;
                                i2++;
                                int length = i2 * 600;
                                if (length > jSONString.length()) {
                                    length = jSONString.length();
                                }
                                ALog.d(CountryListActivity.TAG, jSONString.substring(i3, length));
                            }
                            CountryListActivity.this.a((List<IoTSmart.Country>) list);
                        }
                    }
                });
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        TextView textView;
        WindowManager windowManager = this.f11934i;
        if (windowManager != null && (textView = this.f11928c) != null) {
            windowManager.removeViewImmediate(textView);
        }
        Handler handler = this.f11943r;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        LocateTask locateTask = this.f11937l;
        if (locateTask != null) {
            locateTask.stopLocation();
            this.f11937l = null;
        }
        LocationUtil.cancelLocating();
        super.onDestroy();
        this.f11942q.removeCallbacksAndMessages(null);
    }

    @Override // com.aliyun.iot.aep.sdk.page.LocateHandler.OnLocationListener
    @SuppressLint({"SetTextI18n"})
    public void onFailLocate() {
        View view = this.f11935j;
        if (view != null) {
            this.f11927b.removeHeaderView(view);
        }
        View viewInflate = View.inflate(this, R.layout.sdk_framework_header_view_location_fail, null);
        this.f11935j = viewInflate;
        LineTextView lineTextView = (LineTextView) viewInflate.findViewById(R.id.tv_location_again);
        lineTextView.setText(getString(R.string.location_failed) + getString(R.string.location_failed_again));
        ((LinearLayout) this.f11935j.findViewById(R.id.ll_header_fail_back)).setBackgroundColor(getResources().getColor(android.R.color.white));
        ((TextView) this.f11935j.findViewById(R.id.tv_location_fail)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
        lineTextView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.f11935j.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (ContextCompat.checkSelfPermission(CountryListActivity.this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    ActivityCompat.requestPermissions(CountryListActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 1);
                }
                if (!LocationUtil.isLocationEnabled(CountryListActivity.this)) {
                    LocationUtil.remindStartLocateService(CountryListActivity.this);
                }
                CountryListActivity.this.c();
            }
        });
        this.f11927b.addHeaderView(this.f11935j);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        LoadingCompact.dismissLoading(this);
    }

    @Override // com.aliyun.iot.aep.sdk.page.LocateHandler.OnLocationListener
    public void onSuccessLocate(final IoTSmart.Country country) {
        View view = this.f11935j;
        if (view != null) {
            this.f11927b.removeHeaderView(view);
        }
        View viewInflate = View.inflate(this, R.layout.sdk_framework_header_view, null);
        this.f11935j = viewInflate;
        TextView textView = (TextView) viewInflate.findViewById(R.id.ilop_pagestart_default_country_name);
        this.f11939n = (ImageView) this.f11935j.findViewById(R.id.code);
        textView.setText(country.areaName);
        this.f11935j.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CountryListActivity.this.f11939n.setVisibility(0);
                CountryListActivity.this.f11932g = country;
                if (CountryListActivity.this.f11926a != null) {
                    CountryListActivity.this.f11926a.notifyDataSetChanged();
                }
            }
        });
        this.f11927b.addHeaderView(this.f11935j);
    }

    private void b() {
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.sdk_framework_overlay, (ViewGroup) null);
        this.f11928c = textView;
        textView.setVisibility(4);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2, 24, -3);
        WindowManager windowManager = (WindowManager) getSystemService("window");
        this.f11934i = windowManager;
        if (windowManager != null) {
            windowManager.addView(this.f11928c, layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        View view = this.f11935j;
        if (view != null) {
            this.f11927b.removeHeaderView(view);
        }
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(-1, -2);
        View viewInflate = View.inflate(this, R.layout.sdk_framework_header_view, null);
        this.f11935j = viewInflate;
        TextView textView = (TextView) viewInflate.findViewById(R.id.ilop_pagestart_default_country_name);
        this.f11936k = textView;
        textView.setTextColor(Color.parseColor("#FF333333"));
        this.f11936k.setText(R.string.locating);
        this.f11935j.setLayoutParams(layoutParams);
        this.f11927b.addHeaderView(this.f11935j);
        LocationUtil.requestLocation(this);
        ArrayList<IoTSmart.Country> arrayList = this.f11931f;
        if (arrayList != null) {
            LocateTask locateTask = new LocateTask(this, arrayList, this);
            this.f11937l = locateTask;
            locateTask.startLocation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List<IoTSmart.Country> list) {
        if (list == null) {
            return;
        }
        this.f11931f.addAll(list);
        Collections.sort(this.f11931f, new Comparator<IoTSmart.Country>() { // from class: com.aliyun.iot.aep.sdk.page.CountryListActivity.3
            @Override // java.util.Comparator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(IoTSmart.Country country, IoTSmart.Country country2) {
                return b.b(country).compareTo(b.b(country2));
            }
        });
        this.f11929d.setInterval(0.0f);
        this.f11929d.setOnTouchingLetterChangedListener(new c());
        this.f11940o = new HashMap<>();
        this.f11930e = new e();
        b();
        d dVar = new d(this, this.f11931f);
        this.f11926a = dVar;
        this.f11927b.setAdapter((ListAdapter) dVar);
        this.f11927b.setOnItemClickListener(new a());
        a();
    }

    private void a() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 1);
        }
        if (!LocationUtil.isLocationEnabled(this)) {
            LocationUtil.remindStartLocateService(this);
        }
        c();
    }
}
