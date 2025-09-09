package com.aliyun.alink.linksdk.connectsdk;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.connectsdk.tools.ThreadPool;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.google.gson.Gson;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ApiHelper {
    private static final String CLASS_IOTAPICLIENT = "com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient";
    private static final String CLASS_MTOP = "mtopsdk.mtop.domain.MtopRequest";
    private static final String TAG = "ApiHelper";
    private static boolean debug = false;
    private static volatile ApiHelper mInstance;
    private boolean isApiClient;

    public static class MappingClass {
        public boolean isList;
        public Class mappingClass;

        public MappingClass(boolean z2, Class cls) {
            this.isList = z2;
            this.mappingClass = cls;
        }
    }

    private ApiHelper() {
        boolean zIsClassAvailable = isClassAvailable(CLASS_IOTAPICLIENT);
        boolean zIsClassAvailable2 = isClassAvailable("mtopsdk.mtop.domain.MtopRequest");
        if (zIsClassAvailable && zIsClassAvailable2) {
            throw new RuntimeException("both apiclient and mtop exist");
        }
        if (zIsClassAvailable) {
            this.isApiClient = true;
        } else {
            if (!zIsClassAvailable2) {
                throw new RuntimeException("no way to send api request");
            }
            this.isApiClient = false;
        }
    }

    private IoTRequest buildApiRequest(@NonNull BaseApiRequest baseApiRequest, ApiCallBack apiCallBack) {
        Map<String, Object> mapObjectToMap = baseApiRequest.objectToMap();
        log("buildApiRequest params : " + new Gson().toJson(mapObjectToMap));
        if (mapObjectToMap == null) {
            onFail(apiCallBack, -1, "empty parameter");
            return null;
        }
        IoTRequestBuilder authType = new IoTRequestBuilder().setApiVersion(getString(mapObjectToMap, "API_VERSION")).setPath(getString(mapObjectToMap, "API_PATH")).setScheme(Scheme.HTTPS).setAuthType(AlinkConstants.KEY_IOT_AUTH);
        if ("http".equalsIgnoreCase(getString(mapObjectToMap, "API_SCHEME"))) {
            authType.setScheme(Scheme.HTTP);
        }
        String string = getString(mapObjectToMap, "API_AUTH_TYPE");
        if (!TextUtils.isEmpty(string)) {
            authType.setAuthType(string);
        }
        String string2 = getString(mapObjectToMap, "API_HOST");
        if (!TextUtils.isEmpty(string2)) {
            authType.setHost(string2);
        }
        removeApiRequestParams(mapObjectToMap);
        if (!mapObjectToMap.isEmpty()) {
            authType.setParams(mapObjectToMap);
        }
        return authType.build();
    }

    public static ApiHelper getInstance() {
        if (mInstance == null) {
            synchronized (ApiHelper.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new ApiHelper();
                    }
                } finally {
                }
            }
        }
        return mInstance;
    }

    public static MappingClass getMappingClazz(ApiCallBack apiCallBack) {
        Type type = null;
        boolean z2 = false;
        if (apiCallBack != null) {
            try {
                type = ((ParameterizedType) apiCallBack.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                if (((Class) ((ParameterizedType) type).getRawType()) == List.class) {
                    try {
                        type = ((ParameterizedType) type).getActualTypeArguments()[0];
                    } catch (Exception unused) {
                    }
                    z2 = true;
                }
            } catch (Exception unused2) {
            }
        }
        return new MappingClass(z2, (Class) type);
    }

    private String getString(@NonNull Map map, @NonNull String str) {
        Object obj = map.get(str);
        if (obj != null) {
            return String.valueOf(obj);
        }
        return null;
    }

    private static boolean isClassAvailable(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isDebug() {
        return debug;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void log(String str) {
        if (debug) {
            Log.d(TAG, str);
        }
    }

    public static Map<String, Object> objectToMap(Object obj) {
        try {
            return (Map) JSON.parseObject(new Gson().toJson(obj), Map.class);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFail(final ApiCallBack apiCallBack, final int i2, final String str) {
        if (apiCallBack == null) {
            return;
        }
        ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.aliyun.alink.linksdk.connectsdk.ApiHelper.3
            @Override // java.lang.Runnable
            public void run() {
                apiCallBack.onFail(i2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSuccess(final ApiCallBack apiCallBack, final Object obj) {
        if (apiCallBack == null) {
            return;
        }
        ThreadPool.MainThreadHandler.getInstance().post(new Runnable() { // from class: com.aliyun.alink.linksdk.connectsdk.ApiHelper.2
            @Override // java.lang.Runnable
            public void run() {
                apiCallBack.onSuccess(obj);
            }
        });
    }

    private void removeApiRequestParams(Map map) {
        if (map == null) {
            return;
        }
        map.remove("API_VERSION");
        map.remove("API_PATH");
        map.remove("API_SCHEME");
        map.remove("API_AUTH_TYPE");
        map.remove("API_HOST");
        map.remove("REQUEST_METHOD");
        map.remove("MTOP_API_NAME");
        map.remove("MTOP_VERSION");
        map.remove("MTOP_NEED_ECODE");
        map.remove("MTOP_NEED_SESSION");
    }

    private void sendApiRequest(@NonNull BaseApiRequest baseApiRequest, final ApiCallBack apiCallBack) {
        try {
            IoTRequest ioTRequestBuildApiRequest = buildApiRequest(baseApiRequest, apiCallBack);
            log("ioTRequest: " + new Gson().toJson(ioTRequestBuildApiRequest));
            final MappingClass mappingClazz = getMappingClazz(apiCallBack);
            new IoTAPIClientFactory().getClient().send(ioTRequestBuildApiRequest, new IoTCallback() { // from class: com.aliyun.alink.linksdk.connectsdk.ApiHelper.1
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest, Exception exc) {
                    ApiHelper.this.onFail(apiCallBack, 0, exc.getLocalizedMessage());
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                    if (apiCallBack == null) {
                        return;
                    }
                    if (ioTResponse == null || ioTResponse.getCode() != 200) {
                        ApiHelper.this.onFail(apiCallBack, ioTResponse == null ? 0 : ioTResponse.getCode(), ioTResponse == null ? "" : ioTResponse.getLocalizedMsg());
                        return;
                    }
                    if (ioTResponse.getData() == null) {
                        ApiHelper.this.onSuccess(apiCallBack, null);
                        return;
                    }
                    Object data = ioTResponse.getData();
                    try {
                        String string = ioTResponse.getData().toString();
                        MappingClass mappingClass = mappingClazz;
                        data = mappingClass.isList ? JSON.parseArray(string, mappingClass.mappingClass) : JSON.parseObject(string, mappingClass.mappingClass);
                    } catch (Exception unused) {
                    }
                    ApiHelper.log("response data : " + new Gson().toJson(data));
                    ApiHelper.this.onSuccess(apiCallBack, data);
                }
            });
        } catch (Exception e2) {
            onFail(apiCallBack, -1, e2.getLocalizedMessage());
        }
    }

    private void sendMtopequest(@NonNull BaseApiRequest baseApiRequest, ApiCallBack apiCallBack) {
    }

    public static void setDebug(boolean z2) {
        debug = z2;
    }

    public boolean isApiClient() {
        return this.isApiClient;
    }

    public void send(@NonNull BaseApiRequest baseApiRequest, ApiCallBack apiCallBack) {
        log("BaseApiRequest: " + new Gson().toJson(baseApiRequest));
        if (this.isApiClient) {
            sendApiRequest(baseApiRequest, apiCallBack);
        } else {
            sendMtopequest(baseApiRequest, apiCallBack);
        }
    }
}
