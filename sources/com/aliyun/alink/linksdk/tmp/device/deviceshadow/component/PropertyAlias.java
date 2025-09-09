package com.aliyun.alink.linksdk.tmp.device.deviceshadow.component;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DeviceShadowMgr;
import com.aliyun.alink.linksdk.tmp.device.deviceshadow.DiskLruHelper;
import com.aliyun.alink.linksdk.tmp.device.payload.discovery.GetTslResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.request.GateWayRequest;
import com.aliyun.alink.linksdk.tmp.device.request.GateWayResponse;
import com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener;
import com.aliyun.alink.linksdk.tmp.device.request.propertyalias.QueryPropertyAliasRequest;
import com.aliyun.alink.linksdk.tmp.device.request.propertyalias.UpdatePropertyAliasRequest;
import com.aliyun.alink.linksdk.tmp.listener.IProcessListener;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class PropertyAlias {
    private static final String DEVICESHADOW_PROPERTYALIAS_KEY = "property_alias_key";
    private static final String TAG = TmpConstant.TAG + PropertyAlias.class.getSimpleName();
    protected DiskLruHelper mDiskLruHelper;

    public static class PropertyAliasData {
        public String iotId;
        public String propertyAliasName;
        public String propertyAliasValue;

        public PropertyAliasData() {
        }

        public String toString() {
            return "PropertyAliasData{iotId='" + this.iotId + "', propertyAliasName='" + this.propertyAliasName + "', propertyAliasValue='" + this.propertyAliasValue + "'}";
        }

        public PropertyAliasData(String str, String str2, String str3) {
            this.iotId = str;
            this.propertyAliasName = str2;
            this.propertyAliasValue = str3;
        }
    }

    public PropertyAlias(DiskLruHelper diskLruHelper) {
        this.mDiskLruHelper = diskLruHelper;
    }

    public static List<PropertyAliasData> create(String str, List<AliasNotifyData> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            AliasNotifyData aliasNotifyData = list.get(i2);
            if (aliasNotifyData != null) {
                arrayList.add(new PropertyAliasData(str, aliasNotifyData.name, aliasNotifyData.identifier));
            }
        }
        return arrayList;
    }

    public String changeTslWithAlias(String str, String str2, List<PropertyAliasData> list) {
        JSONObject object = JSON.parseObject(str2);
        return (object != null && changeTslWithAlias(str, object, list)) ? object.toString() : str2;
    }

    protected void deleteAllUnUploadPropertyAlias() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "deleteAllUnUploadPropertyAlias");
        this.mDiskLruHelper.deleteValue(DeviceShadowMgr.getCacheKey(DEVICESHADOW_PROPERTYALIAS_KEY, null));
    }

    protected void deleteUnUploadPropertyAlias(String str, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str4 = TAG;
        ALog.d(str4, "deleteUnUploadPropertyAlias iotId:" + str + " propertyAliasName:" + str2 + " propertyAliasValue:" + str3);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            ALog.e(str4, "deleteUnUploadPropertyAlias iotId or  propertyAliasName or propertyAliasValue empty");
            return;
        }
        List<PropertyAliasData> unUploadPropertyAlias = getUnUploadPropertyAlias();
        if (unUploadPropertyAlias == null || unUploadPropertyAlias.isEmpty()) {
            ALog.w(str4, "deleteUnUploadPropertyAlias aliasDataList empty");
            return;
        }
        Iterator<PropertyAliasData> it = unUploadPropertyAlias.iterator();
        while (it.hasNext()) {
            PropertyAliasData next = it.next();
            if (str.equalsIgnoreCase(next.iotId) && str2.equalsIgnoreCase(next.propertyAliasName)) {
                it.remove();
            }
        }
        if (unUploadPropertyAlias.isEmpty()) {
            deleteAllUnUploadPropertyAlias();
        } else {
            saveUnUploadPropertyAlias(unUploadPropertyAlias);
        }
    }

    protected List<PropertyAliasData> getUnUploadPropertyAlias() {
        return null;
    }

    public void refreshPropertyAlias(final String str, final GetTslResponsePayload getTslResponsePayload, final IProcessListener iProcessListener) {
        String str2 = TAG;
        ALog.d(str2, "refreshPropertyAlias iotId:" + str);
        if (iProcessListener != null && !TextUtils.isEmpty(str) && getTslResponsePayload != null && getTslResponsePayload.data != null) {
            GateWayRequest queryPropertyAliasRequest = new QueryPropertyAliasRequest(str);
            queryPropertyAliasRequest.sendRequest(queryPropertyAliasRequest, new IGateWayRequestListener<QueryPropertyAliasRequest, QueryPropertyAliasRequest.QueryPropertyAliasResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.component.PropertyAlias.1
                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                public void onFail(QueryPropertyAliasRequest queryPropertyAliasRequest2, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e(PropertyAlias.TAG, "refreshPropertyAlias onFail iotId:" + str + " error:" + aError);
                    iProcessListener.onFail(null);
                }

                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                public void onSuccess(QueryPropertyAliasRequest queryPropertyAliasRequest2, QueryPropertyAliasRequest.QueryPropertyAliasResponse queryPropertyAliasResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    T t2;
                    ALog.d(PropertyAlias.TAG, "refreshPropertyAlias onSuccess iotId:" + str + " result:" + queryPropertyAliasResponse);
                    if (queryPropertyAliasResponse == null || (t2 = queryPropertyAliasResponse.data) == 0 || ((List) t2).isEmpty()) {
                        ALog.w(PropertyAlias.TAG, "refreshPropertyAlias onSuccess result empty");
                        iProcessListener.onSuccess(null);
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (int i2 = 0; i2 < ((List) queryPropertyAliasResponse.data).size(); i2++) {
                        QueryPropertyAliasRequest.QueryPropertyAliasData queryPropertyAliasData = (QueryPropertyAliasRequest.QueryPropertyAliasData) ((List) queryPropertyAliasResponse.data).get(i2);
                        if (queryPropertyAliasData != null) {
                            arrayList.add(new PropertyAliasData(str, queryPropertyAliasData.aliasId, queryPropertyAliasData.aliasValue));
                        }
                    }
                    getTslResponsePayload.data = PropertyAlias.this.changeTslWithAlias(str, getTslResponsePayload.data.toString(), arrayList);
                    iProcessListener.onSuccess(null);
                }
            });
            return;
        }
        ALog.d(str2, "refreshPropertyAlias error iotId:" + str + " payload:" + getTslResponsePayload + " processListener:" + iProcessListener);
    }

    protected void saveUnUploadPropertyAlias(List<PropertyAliasData> list) {
    }

    public void setPropertyAlias(String str, String str2, String str3) {
        ALog.d(TAG, "setPropertyAlias iotId:" + str + " propertyAliasName:" + str2 + " propertyAliasValue:" + str3);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PropertyAliasData(str, str2, str3));
        changeTslWithAlias(str, arrayList);
        List<PropertyAliasData> unUploadPropertyAlias = getUnUploadPropertyAlias();
        if (unUploadPropertyAlias == null) {
            unUploadPropertyAlias = new ArrayList<>();
        }
        Iterator<PropertyAliasData> it = unUploadPropertyAlias.iterator();
        while (it.hasNext()) {
            PropertyAliasData next = it.next();
            if (next != null && next.propertyAliasName.equalsIgnoreCase(str2) && next.iotId.equalsIgnoreCase(str)) {
                it.remove();
            }
        }
        unUploadPropertyAlias.add(new PropertyAliasData(str, str2, str3));
        saveUnUploadPropertyAlias(unUploadPropertyAlias);
        uploadAlias(arrayList);
    }

    protected void uploadAlias(List<PropertyAliasData> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (list == null || list.isEmpty()) {
            ALog.e(TAG, "uploadAlias aliasDataList empty");
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            final PropertyAliasData propertyAliasData = list.get(i2);
            GateWayRequest updatePropertyAliasRequest = new UpdatePropertyAliasRequest(propertyAliasData.iotId, propertyAliasData.propertyAliasName, propertyAliasData.propertyAliasValue);
            updatePropertyAliasRequest.sendRequest(updatePropertyAliasRequest, new IGateWayRequestListener<UpdatePropertyAliasRequest, GateWayResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.component.PropertyAlias.3
                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                public void onFail(UpdatePropertyAliasRequest updatePropertyAliasRequest2, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e(PropertyAlias.TAG, "uploadAlias onFail updatePropertyAliasRequest:" + updatePropertyAliasRequest2 + " error:" + aError);
                }

                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                public void onSuccess(UpdatePropertyAliasRequest updatePropertyAliasRequest2, GateWayResponse gateWayResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.d(PropertyAlias.TAG, "uploadAlias onSuccess updatePropertyAliasRequest:" + updatePropertyAliasRequest2 + " result:" + gateWayResponse);
                    PropertyAlias propertyAlias = PropertyAlias.this;
                    PropertyAliasData propertyAliasData2 = propertyAliasData;
                    propertyAlias.deleteUnUploadPropertyAlias(propertyAliasData2.iotId, propertyAliasData2.propertyAliasName, propertyAliasData2.propertyAliasValue);
                }
            });
        }
    }

    public void uploadCachedPropertyAlias() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "uploadCachedPropertyAlias");
        uploadAlias(getUnUploadPropertyAlias());
    }

    public boolean changeTslWithAlias(String str, JSONObject jSONObject, List<PropertyAliasData> list) {
        boolean z2 = false;
        if (!TextUtils.isEmpty(str) && jSONObject != null && list != null && !list.isEmpty()) {
            String string = jSONObject.getString("identifier");
            String string2 = jSONObject.getString("name");
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                for (PropertyAliasData propertyAliasData : list) {
                    if (propertyAliasData != null && string.equalsIgnoreCase(propertyAliasData.propertyAliasName) && str.equalsIgnoreCase(propertyAliasData.iotId)) {
                        jSONObject.put("name", propertyAliasData.propertyAliasValue);
                        z2 = true;
                    }
                }
            }
            Set<String> setKeySet = jSONObject.keySet();
            if (setKeySet != null && !setKeySet.isEmpty()) {
                for (String str2 : setKeySet) {
                    Object obj = jSONObject.get(str2);
                    if (obj != null) {
                        if (obj instanceof JSONObject) {
                            if (changeTslWithAlias(str, (JSONObject) obj, list)) {
                                jSONObject.put(str2, obj);
                                z2 = true;
                            }
                        } else if ((obj instanceof JSONArray) && changeTslWithAlias(str, (JSONArray) obj, list)) {
                            jSONObject.put(str2, obj);
                            z2 = true;
                        }
                    }
                }
            }
        }
        return z2;
    }

    public void refreshPropertyAlias(final String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "refreshPropertyAlias iotId:" + str);
        GateWayRequest queryPropertyAliasRequest = new QueryPropertyAliasRequest(str);
        queryPropertyAliasRequest.sendRequest(queryPropertyAliasRequest, new IGateWayRequestListener<QueryPropertyAliasRequest, QueryPropertyAliasRequest.QueryPropertyAliasResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.deviceshadow.component.PropertyAlias.2
            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onFail(QueryPropertyAliasRequest queryPropertyAliasRequest2, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(PropertyAlias.TAG, "refreshPropertyAlias onFail iotId:" + str + " error:" + aError);
            }

            @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
            public void onSuccess(QueryPropertyAliasRequest queryPropertyAliasRequest2, QueryPropertyAliasRequest.QueryPropertyAliasResponse queryPropertyAliasResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                T t2;
                ALog.d(PropertyAlias.TAG, "refreshPropertyAlias onSuccess iotId:" + str + " result:" + queryPropertyAliasResponse);
                if (queryPropertyAliasResponse == null || (t2 = queryPropertyAliasResponse.data) == 0 || ((List) t2).isEmpty()) {
                    ALog.e(PropertyAlias.TAG, "refreshPropertyAlias onSuccess result empty");
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < ((List) queryPropertyAliasResponse.data).size(); i2++) {
                    QueryPropertyAliasRequest.QueryPropertyAliasData queryPropertyAliasData = (QueryPropertyAliasRequest.QueryPropertyAliasData) ((List) queryPropertyAliasResponse.data).get(i2);
                    if (queryPropertyAliasData != null) {
                        arrayList.add(new PropertyAliasData(str, queryPropertyAliasData.aliasId, queryPropertyAliasData.aliasValue));
                    }
                }
                PropertyAlias.this.changeTslWithAlias(str, arrayList);
            }
        });
    }

    public boolean changeTslWithAlias(String str, JSONArray jSONArray, List<PropertyAliasData> list) {
        boolean z2 = false;
        if (!TextUtils.isEmpty(str) && jSONArray != null && list != null && !list.isEmpty()) {
            for (int size = jSONArray.size() - 1; size >= 0; size--) {
                Object obj = jSONArray.get(size);
                if (obj instanceof JSONObject) {
                    if (changeTslWithAlias(str, (JSONObject) obj, list)) {
                        jSONArray.add(size, obj);
                        jSONArray.remove(size);
                        z2 = true;
                    }
                } else if ((obj instanceof JSONArray) && changeTslWithAlias(str, (JSONObject) obj, list)) {
                    jSONArray.add(size, obj);
                    jSONArray.remove(size);
                    z2 = true;
                }
            }
        }
        return z2;
    }

    public void changeTslWithAlias(String str, List<PropertyAliasData> list) {
        JSONObject jSONObject;
        String str2 = TAG;
        ALog.d(str2, "changeTslWithAlias aliasDataList:" + list + " iotId:" + str);
        if (!TextUtils.isEmpty(str) && list != null && !list.isEmpty()) {
            try {
                JSONObject object = JSON.parseObject(DeviceShadowMgr.getInstance().getTsl(str));
                if (object == null || (jSONObject = object.getJSONObject("data")) == null) {
                    return;
                }
                DeviceShadowMgr.getInstance().setTsl(str, changeTslWithAlias(str, jSONObject.toString(), list));
                return;
            } catch (Exception e2) {
                ALog.e(TAG, "changeTslWithAlias e:" + e2.toString());
                return;
            }
        }
        ALog.e(str2, "changeTslWithAlias iotId or aliasDataList empty");
    }
}
