package com.kingsmith.miot;

import android.os.AsyncTask;
import android.util.Log;
import com.miot.api.MiotManager;
import com.miot.common.people.People;
import com.miot.common.people.PeopleFactory;
import com.miot.common.utils.Logger;
import com.xiaomi.account.auth.AuthorizeApi;
import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class XiaomiAccountGetPeopleInfoTask extends AsyncTask<Void, Void, People> {
    private static final String TAG = "XiaomiAccountTask";
    private String mAccessToken;
    private long mExpiresIn;
    private Handler mHandler;
    private String mMacAlgorithm;
    private String mMacKey;

    public interface Handler {
        void onFailed();

        void onSucceed(People people);
    }

    public XiaomiAccountGetPeopleInfoTask(String str, String str2, String str3, String str4, Handler handler) {
        this.mAccessToken = str;
        this.mExpiresIn = Long.valueOf(str2).longValue();
        this.mMacKey = str3;
        this.mMacAlgorithm = str4;
        this.mHandler = handler;
    }

    private People getPeopleInfo(String str, String str2, String str3) throws Throwable {
        try {
            String strDoHttpGet = AuthorizeApi.doHttpGet(XiaomiOAuthConstants.OPEN_API_PATH_PROFILE, MiotManager.getInstance().getAppConfig().getAppId().longValue(), str, str2, str3);
            try {
                JSONObject jSONObject = new JSONObject(strDoHttpGet);
                Log.e(TAG, strDoHttpGet);
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                if (jSONObjectOptJSONObject != null) {
                    Logger.d(TAG, "data: " + jSONObjectOptJSONObject.toString());
                    String strOptString = jSONObjectOptJSONObject.optString("userId");
                    String strOptString2 = jSONObjectOptJSONObject.optString("miliaoNick");
                    String strOptString3 = jSONObjectOptJSONObject.optString("miliaoIcon");
                    String strOptString4 = jSONObjectOptJSONObject.optString("miliaoIcon_75");
                    String strOptString5 = jSONObjectOptJSONObject.optString("miliaoIcon_90");
                    String strOptString6 = jSONObjectOptJSONObject.optString("miliaoIcon_120");
                    String strOptString7 = jSONObjectOptJSONObject.optString("miliaoIcon_320");
                    People peopleCreateOauthPeople = PeopleFactory.createOauthPeople(str, strOptString, this.mExpiresIn, str2, str3);
                    peopleCreateOauthPeople.setUserName(strOptString2);
                    peopleCreateOauthPeople.setIcon(strOptString3);
                    peopleCreateOauthPeople.setIcon75(strOptString4);
                    peopleCreateOauthPeople.setIcon90(strOptString5);
                    peopleCreateOauthPeople.setIcon120(strOptString6);
                    peopleCreateOauthPeople.setIcon320(strOptString7);
                    return peopleCreateOauthPeople;
                }
            } catch (JSONException unused) {
            }
        } catch (XMAuthericationException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public People doInBackground(Void... voidArr) {
        return getPeopleInfo(this.mAccessToken, this.mMacKey, this.mMacAlgorithm);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(People people) {
        if (people != null) {
            this.mHandler.onSucceed(people);
        } else {
            this.mHandler.onFailed();
        }
    }
}
