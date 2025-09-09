package com.facebook.appevents.codeless;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.codeless.internal.UnityReflection;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ViewIndexer {
    private static final String APP_VERSION_PARAM = "app_version";
    private static final String PLATFORM_PARAM = "platform";
    private static final String REQUEST_TYPE = "request_type";
    private static final String SUCCESS = "success";
    private static final String TAG = "com.facebook.appevents.codeless.ViewIndexer";
    private static final String TREE_PARAM = "tree";
    private static ViewIndexer instance;
    private WeakReference<Activity> activityReference;
    private Timer indexingTimer;
    private String previousDigest = null;
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());

    private static class ScreenshotTaker implements Callable<String> {
        private WeakReference<View> rootView;

        ScreenshotTaker(View view) {
            this.rootView = new WeakReference<>(view);
        }

        @Override // java.util.concurrent.Callable
        public String call() {
            View view = this.rootView.get();
            if (view == null || view.getWidth() == 0 || view.getHeight() == 0) {
                return "";
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            view.draw(new Canvas(bitmapCreateBitmap));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
        }
    }

    public ViewIndexer(Activity activity) {
        this.activityReference = new WeakReference<>(activity);
        instance = this;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static GraphRequest buildAppIndexingRequest(String str, AccessToken accessToken, String str2, String str3) {
        if (str == null) {
            return null;
        }
        GraphRequest graphRequestNewPostRequest = GraphRequest.newPostRequest(accessToken, String.format(Locale.US, "%s/app_indexing", str2), null, null);
        Bundle parameters = graphRequestNewPostRequest.getParameters();
        if (parameters == null) {
            parameters = new Bundle();
        }
        parameters.putString(TREE_PARAM, str);
        parameters.putString("app_version", AppEventUtility.getAppVersion());
        parameters.putString("platform", "android");
        parameters.putString(REQUEST_TYPE, str3);
        if (str3.equals(Constants.APP_INDEXING)) {
            parameters.putString(Constants.DEVICE_SESSION_ID, CodelessManager.getCurrentDeviceSessionID());
        }
        graphRequestNewPostRequest.setParameters(parameters);
        graphRequestNewPostRequest.setCallback(new GraphRequest.Callback() { // from class: com.facebook.appevents.codeless.ViewIndexer.4
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                Logger.log(LoggingBehavior.APP_EVENTS, ViewIndexer.TAG, "App index sent to FB!");
            }
        });
        return graphRequestNewPostRequest;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendToServer(final String str) {
        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.codeless.ViewIndexer.3
            @Override // java.lang.Runnable
            public void run() {
                GraphRequest graphRequestBuildAppIndexingRequest;
                String strMd5hash = Utility.md5hash(str);
                AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
                if ((strMd5hash == null || !strMd5hash.equals(ViewIndexer.this.previousDigest)) && (graphRequestBuildAppIndexingRequest = ViewIndexer.buildAppIndexingRequest(str, currentAccessToken, FacebookSdk.getApplicationId(), Constants.APP_INDEXING)) != null) {
                    GraphResponse graphResponseExecuteAndWait = graphRequestBuildAppIndexingRequest.executeAndWait();
                    try {
                        JSONObject jSONObject = graphResponseExecuteAndWait.getJSONObject();
                        if (jSONObject == null) {
                            Log.e(ViewIndexer.TAG, "Error sending UI component tree to Facebook: " + graphResponseExecuteAndWait.getError());
                            return;
                        }
                        if ("true".equals(jSONObject.optString("success"))) {
                            Logger.log(LoggingBehavior.APP_EVENTS, ViewIndexer.TAG, "Successfully send UI component tree to server");
                            ViewIndexer.this.previousDigest = strMd5hash;
                        }
                        if (jSONObject.has(Constants.APP_INDEXING_ENABLED)) {
                            CodelessManager.updateAppIndexing(Boolean.valueOf(jSONObject.getBoolean(Constants.APP_INDEXING_ENABLED)));
                        }
                    } catch (JSONException e2) {
                        Log.e(ViewIndexer.TAG, "Error decoding server response.", e2);
                    }
                }
            }
        });
    }

    public static void sendToServerUnityInstance(String str) {
        ViewIndexer viewIndexer = instance;
        if (viewIndexer == null) {
            return;
        }
        viewIndexer.sendToServerUnity(str);
    }

    public void schedule() {
        final TimerTask timerTask = new TimerTask() { // from class: com.facebook.appevents.codeless.ViewIndexer.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() throws JSONException {
                try {
                    Activity activity = (Activity) ViewIndexer.this.activityReference.get();
                    if (activity == null) {
                        return;
                    }
                    String simpleName = activity.getClass().getSimpleName();
                    View rootView = activity.getWindow().getDecorView().getRootView();
                    if (CodelessManager.getIsAppIndexingEnabled()) {
                        if (InternalSettings.isUnityApp()) {
                            UnityReflection.captureViewHierarchy();
                            return;
                        }
                        FutureTask futureTask = new FutureTask(new ScreenshotTaker(rootView));
                        ViewIndexer.this.uiThreadHandler.post(futureTask);
                        String str = "";
                        try {
                            str = (String) futureTask.get(1L, TimeUnit.SECONDS);
                        } catch (Exception e2) {
                            Log.e(ViewIndexer.TAG, "Failed to take screenshot.", e2);
                        }
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put(ViewHierarchyConstants.SCREEN_NAME_KEY, simpleName);
                            jSONObject.put("screenshot", str);
                            JSONArray jSONArray = new JSONArray();
                            jSONArray.put(ViewHierarchy.getDictionaryOfView(rootView));
                            jSONObject.put(ViewHierarchyConstants.VIEW_KEY, jSONArray);
                        } catch (JSONException unused) {
                            Log.e(ViewIndexer.TAG, "Failed to create JSONObject");
                        }
                        ViewIndexer.this.sendToServer(jSONObject.toString());
                    }
                } catch (Exception e3) {
                    Log.e(ViewIndexer.TAG, "UI Component tree indexing failure!", e3);
                }
            }
        };
        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.codeless.ViewIndexer.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (ViewIndexer.this.indexingTimer != null) {
                        ViewIndexer.this.indexingTimer.cancel();
                    }
                    ViewIndexer.this.previousDigest = null;
                    ViewIndexer.this.indexingTimer = new Timer();
                    ViewIndexer.this.indexingTimer.scheduleAtFixedRate(timerTask, 0L, 1000L);
                } catch (Exception e2) {
                    Log.e(ViewIndexer.TAG, "Error scheduling indexing job", e2);
                }
            }
        });
    }

    @Deprecated
    public void sendToServerUnity(String str) {
        instance.sendToServer(str);
    }

    public void unschedule() {
        Timer timer;
        if (this.activityReference.get() == null || (timer = this.indexingTimer) == null) {
            return;
        }
        try {
            timer.cancel();
            this.indexingTimer = null;
        } catch (Exception e2) {
            Log.e(TAG, "Error unscheduling indexing job", e2);
        }
    }
}
