package xiaojin.kingsmith.com.flutter_health_kit;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class FlutterHealthKitPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener, MethodChannel.Result, ActivityAware {
    public static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1111;
    public static final String Tag = "FlutterHealthKitPlugin";

    /* renamed from: b, reason: collision with root package name */
    static Activity f26890b;
    public static MethodChannel.Result mResult;

    /* renamed from: a, reason: collision with root package name */
    FitnessOptions f26891a = FitnessOptions.builder().addDataType(DataType.TYPE_STEP_COUNT_DELTA, 1).addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, 1).build();

    private void checkAuthorization(MethodChannel.Result result) throws JSONException {
        try {
            mResult = result;
            JSONObject jSONObject = new JSONObject();
            if (GoogleSignIn.hasPermissions(getGoogleAccount(), this.f26891a)) {
                jSONObject.put("code", 0);
                jSONObject.put("msg", "hasPermissions");
            } else {
                jSONObject.put("code", -1);
                jSONObject.put("msg", "!hasPermissions");
            }
            mResult.success(jSONObject.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void checkGoogleService(MethodChannel.Result result) throws JSONException {
        mResult = result;
        JSONObject jSONObject = new JSONObject();
        try {
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(f26890b) == 0) {
                jSONObject.put("code", 0);
                jSONObject.put("msg", "Google service  was successful!");
            } else {
                jSONObject.put("code", -1);
                jSONObject.put("msg", "Google service  was not exit!");
            }
            mResult.success(jSONObject.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void deleteData(MethodChannel.Result result, MethodCall methodCall) throws JSONException {
        String str;
        String string;
        JSONObject jSONObject;
        mResult = result;
        String string2 = "0";
        try {
            jSONObject = new JSONObject(methodCall.arguments.toString());
            string = jSONObject.getString("startTime");
        } catch (JSONException e2) {
            e = e2;
            str = "0";
        }
        try {
            string2 = jSONObject.getString(AUserTrack.UTKEY_END_TIME);
            Log.e("deleteData", string + ",,," + string2);
        } catch (JSONException e3) {
            e = e3;
            str = string2;
            string2 = string;
            e.printStackTrace();
            String str2 = str;
            string = string2;
            string2 = str2;
            DataDeleteRequest dataDeleteRequestBuild = new DataDeleteRequest.Builder().setTimeInterval(Long.parseLong(string), Long.parseLong(string2), TimeUnit.MILLISECONDS).addDataType(DataType.TYPE_STEP_COUNT_DELTA).build();
            Activity activity = f26890b;
            Fitness.getHistoryClient(activity, GoogleSignIn.getAccountForExtension(activity, this.f26891a)).deleteData(dataDeleteRequestBuild).addOnSuccessListener(new OnSuccessListener<Void>() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.2
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public void onSuccess(Void r3) throws JSONException {
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("code", 0);
                        jSONObject2.put("msg", "deleteData was successful!");
                        FlutterHealthKitPlugin.mResult.success(jSONObject2.toString());
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.1
                @Override // com.google.android.gms.tasks.OnFailureListener
                public void onFailure(@NonNull Exception exc) throws JSONException {
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("code", -1);
                        jSONObject2.put("msg", "deleteData was onFailure!" + exc.getMessage());
                        FlutterHealthKitPlugin.mResult.success(jSONObject2.toString());
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
            });
        }
        DataDeleteRequest dataDeleteRequestBuild2 = new DataDeleteRequest.Builder().setTimeInterval(Long.parseLong(string), Long.parseLong(string2), TimeUnit.MILLISECONDS).addDataType(DataType.TYPE_STEP_COUNT_DELTA).build();
        Activity activity2 = f26890b;
        Fitness.getHistoryClient(activity2, GoogleSignIn.getAccountForExtension(activity2, this.f26891a)).deleteData(dataDeleteRequestBuild2).addOnSuccessListener(new OnSuccessListener<Void>() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.2
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public void onSuccess(Void r3) throws JSONException {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", 0);
                    jSONObject2.put("msg", "deleteData was successful!");
                    FlutterHealthKitPlugin.mResult.success(jSONObject2.toString());
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.1
            @Override // com.google.android.gms.tasks.OnFailureListener
            public void onFailure(@NonNull Exception exc) throws JSONException {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", -1);
                    jSONObject2.put("msg", "deleteData was onFailure!" + exc.getMessage());
                    FlutterHealthKitPlugin.mResult.success(jSONObject2.toString());
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        });
    }

    private GoogleSignInAccount getGoogleAccount() {
        return GoogleSignIn.getAccountForExtension(f26890b, this.f26891a);
    }

    private Task<Void> insertData(MethodChannel.Result result, MethodCall methodCall) throws JSONException, NumberFormatException {
        String str;
        String str2;
        String string;
        mResult = result;
        String string2 = "0";
        String string3 = "";
        try {
            JSONObject jSONObject = new JSONObject(methodCall.arguments.toString());
            string3 = jSONObject.getString("steps");
            string = jSONObject.getString("startTime");
            try {
                string2 = jSONObject.getString(AUserTrack.UTKEY_END_TIME);
                Log.e("insertData", string3 + ",,,," + string + ",,," + string2);
                str2 = string3;
            } catch (Exception e2) {
                e = e2;
                str = string2;
                string2 = string;
                e.printStackTrace();
                str2 = string3;
                String str3 = str;
                string = string2;
                string2 = str3;
                DataSet dataSetInsertFitnessData = insertFitnessData(str2, Long.parseLong(string), Long.parseLong(string2));
                Activity activity = f26890b;
                return Fitness.getHistoryClient(activity, GoogleSignIn.getAccountForExtension(activity, this.f26891a)).insertData(dataSetInsertFitnessData).addOnSuccessListener(new OnSuccessListener<Void>() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.4
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public void onSuccess(Void r1) {
                        try {
                            FlutterHealthKitPlugin.this.readData();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.3
                    @Override // com.google.android.gms.tasks.OnFailureListener
                    public void onFailure(@NonNull Exception exc) throws JSONException {
                        try {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("code", -1);
                            jSONObject2.put("msg", "insertFitnessDataFail" + exc.getMessage());
                            FlutterHealthKitPlugin.mResult.success(jSONObject2.toString());
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e3) {
            e = e3;
            str = "0";
        }
        DataSet dataSetInsertFitnessData2 = insertFitnessData(str2, Long.parseLong(string), Long.parseLong(string2));
        Activity activity2 = f26890b;
        return Fitness.getHistoryClient(activity2, GoogleSignIn.getAccountForExtension(activity2, this.f26891a)).insertData(dataSetInsertFitnessData2).addOnSuccessListener(new OnSuccessListener<Void>() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.4
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public void onSuccess(Void r1) {
                try {
                    FlutterHealthKitPlugin.this.readData();
                } catch (Exception e32) {
                    e32.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.3
            @Override // com.google.android.gms.tasks.OnFailureListener
            public void onFailure(@NonNull Exception exc) throws JSONException {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", -1);
                    jSONObject2.put("msg", "insertFitnessDataFail" + exc.getMessage());
                    FlutterHealthKitPlugin.mResult.success(jSONObject2.toString());
                } catch (Exception e32) {
                    e32.printStackTrace();
                }
            }
        });
    }

    private DataSet insertFitnessData(String str, long j2, long j3) throws NumberFormatException {
        DataSource dataSourceBuild = new DataSource.Builder().setAppPackageName(f26890b).setDataType(DataType.TYPE_STEP_COUNT_DELTA).setStreamName(" - step count").setType(0).build();
        return DataSet.builder(dataSourceBuild).add(DataPoint.builder(dataSourceBuild).setField(Field.FIELD_STEPS, Integer.parseInt(str)).setTimeInterval(j2, j3, TimeUnit.MILLISECONDS).build()).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readData() {
        Fitness.getHistoryClient(f26890b, getGoogleAccount()).readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA).addOnSuccessListener(new OnSuccessListener<DataSet>() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.6
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public void onSuccess(DataSet dataSet) throws JSONException {
                try {
                    int iAsInt = dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 0);
                    jSONObject.put("msg", "read Data was Success");
                    jSONObject.put("totalStep", iAsInt);
                    FlutterHealthKitPlugin.mResult.success(jSONObject.toString());
                    Log.e("readData", "Total steps:." + iAsInt);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.5
            @Override // com.google.android.gms.tasks.OnFailureListener
            public void onFailure(@NonNull Exception exc) {
                Log.e("readData", "Total steps Exception:." + exc.getMessage());
            }
        });
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        f26890b = registrar.activity();
        new MethodChannel(registrar.messenger(), "flutter_health_kit").setMethodCallHandler(new FlutterHealthKitPlugin());
        registrar.addActivityResultListener(new FlutterHealthKitPlugin());
    }

    private void requestAuthorization(MethodChannel.Result result) throws JSONException {
        try {
            mResult = result;
            JSONObject jSONObject = new JSONObject();
            if (GoogleSignIn.hasPermissions(getGoogleAccount(), this.f26891a)) {
                jSONObject.put("code", 0);
                jSONObject.put("msg", "success");
                mResult.success(jSONObject.toString());
            } else {
                Log.e("GooglehasPermissions", RequestConstant.FALSE);
                jSONObject.put("code", -1);
                jSONObject.put("msg", "Google not has Permissions ");
                Activity activity = f26890b;
                GoogleSignIn.requestPermissions(activity, 1111, GoogleSignIn.getLastSignedInAccount(activity), this.f26891a);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private Task<Void> updateData(MethodChannel.Result result) {
        mResult = result;
        DataSet dataSetUpdateFitnessData = updateFitnessData();
        DataPoint dataPoint = dataSetUpdateFitnessData.getDataPoints().get(0);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        DataUpdateRequest dataUpdateRequestBuild = new DataUpdateRequest.Builder().setDataSet(dataSetUpdateFitnessData).setTimeInterval(dataPoint.getStartTime(timeUnit), dataSetUpdateFitnessData.getDataPoints().get(0).getEndTime(timeUnit), timeUnit).build();
        Activity activity = f26890b;
        return Fitness.getHistoryClient(activity, GoogleSignIn.getAccountForExtension(activity, this.f26891a)).updateData(dataUpdateRequestBuild).addOnSuccessListener(new OnSuccessListener<Void>() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.8
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public void onSuccess(Void r3) throws JSONException {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 0);
                    jSONObject.put("msg", "Data updateData was successful!");
                    FlutterHealthKitPlugin.mResult.success(jSONObject.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: xiaojin.kingsmith.com.flutter_health_kit.FlutterHealthKitPlugin.7
            @Override // com.google.android.gms.tasks.OnFailureListener
            public void onFailure(@NonNull Exception exc) throws JSONException {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", -1);
                    jSONObject.put("msg", "Data updateData was OnFailureListener!" + exc.getMessage());
                    FlutterHealthKitPlugin.mResult.success(jSONObject.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private DataSet updateFitnessData() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date());
        long timeInMillis = calendar.getTimeInMillis();
        calendar.add(12, -50);
        long timeInMillis2 = calendar.getTimeInMillis();
        DataSource dataSourceBuild = new DataSource.Builder().setAppPackageName(f26890b).setDataType(DataType.TYPE_STEP_COUNT_DELTA).setStreamName("$TAG - step count").setType(0).build();
        return DataSet.builder(dataSourceBuild).add(DataPoint.builder(dataSourceBuild).setField(Field.FIELD_STEPS, 50).setTimeInterval(timeInMillis2, timeInMillis, TimeUnit.MILLISECONDS).build()).build();
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void error(String str, @Nullable String str2, @Nullable Object obj) {
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void notImplemented() {
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) throws JSONException {
        if (i3 != -1 || i2 != 1111) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            if (GoogleSignIn.hasPermissions(getGoogleAccount(), this.f26891a)) {
                jSONObject.put("code", 0);
                jSONObject.put("msg", "hasPermissions");
            } else {
                jSONObject.put("code", -1);
                jSONObject.put("msg", "!hasPermissions");
            }
            mResult.success(jSONObject.toString());
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        f26890b = activityPluginBinding.getActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_health_kit").setMethodCallHandler(new FlutterHealthKitPlugin());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) throws JSONException, NumberFormatException {
        if (methodCall.method.equals("requestAuthGoogleFit")) {
            requestAuthorization(result);
            return;
        }
        if (methodCall.method.equals("requestGoogleFitHealthAdd")) {
            insertData(result, methodCall);
            return;
        }
        if (methodCall.method.equals("requestGoogleFitHealthUpdate")) {
            updateData(result);
            return;
        }
        if (methodCall.method.equals("requestGoogleFitHealthDelete")) {
            deleteData(result, methodCall);
        } else if (methodCall.method.equals("checkConnectGoogleFit")) {
            checkAuthorization(result);
        } else if (methodCall.method.equals("googleService")) {
            checkGoogleService(result);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
    }

    @Override // io.flutter.plugin.common.MethodChannel.Result
    public void success(@Nullable Object obj) {
    }
}
