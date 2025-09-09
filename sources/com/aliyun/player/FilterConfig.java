package com.aliyun.player;

import com.tekartik.sqflite.Constant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class FilterConfig {
    private JSONArray filters = new JSONArray();

    public static class Filter {
        JSONObject filter;

        public Filter(String str) throws JSONException {
            JSONObject jSONObject = new JSONObject();
            this.filter = jSONObject;
            try {
                jSONObject.put("target", str);
            } catch (JSONException unused) {
            }
        }

        JSONObject getFilter() {
            return this.filter;
        }

        public void setOptions(FilterOptions filterOptions) throws JSONException {
            if (filterOptions == null || filterOptions.getOptions().length() <= 0) {
                return;
            }
            try {
                this.filter.put(Constant.METHOD_OPTIONS, filterOptions.getOptions());
            } catch (JSONException unused) {
            }
        }
    }

    public static class FilterOptions {
        JSONObject options = new JSONObject();

        JSONObject getOptions() {
            return this.options;
        }

        public void setOption(String str, Object obj) throws JSONException {
            try {
                this.options.put(str, obj);
            } catch (JSONException unused) {
            }
        }

        public String toString() {
            return this.options.toString();
        }
    }

    public void addFilter(Filter filter) {
        if (filter == null || filter.getFilter() == null) {
            return;
        }
        this.filters.put(filter.getFilter());
    }

    public String toString() {
        return this.filters.toString();
    }
}
