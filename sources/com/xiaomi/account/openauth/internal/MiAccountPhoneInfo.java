package com.xiaomi.account.openauth.internal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.xiaomi.account.auth.PhoneInfo;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.account.utils.Base64Coder;
import com.xiaomi.phonenum.PhoneNumKeeper;
import com.xiaomi.phonenum.PhoneNumKeeperFactory;
import com.xiaomi.phonenum.bean.Error;
import com.xiaomi.phonenum.bean.PhoneNum;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class MiAccountPhoneInfo implements PhoneInfo {
    private String TAG = "MiAccountPhoneInfo";
    private Context mContext;
    private PhoneNumKeeper mPhoneNumKeeper;
    private long timeOutMs;

    public MiAccountPhoneInfo(Context context, long j2) {
        this.mContext = context;
        this.timeOutMs = j2;
        PhoneNumKeeper phoneNumKeeperCreatePhoneNumKeeper = new PhoneNumKeeperFactory().createPhoneNumKeeper(this.mContext, "");
        this.mPhoneNumKeeper = phoneNumKeeperCreatePhoneNumKeeper;
        phoneNumKeeperCreatePhoneNumKeeper.setUp(new PhoneNumKeeper.SetupFinishedListener() { // from class: com.xiaomi.account.openauth.internal.MiAccountPhoneInfo.1
            public void onSetupFinished(Error error) {
            }
        });
    }

    @Override // com.xiaomi.account.auth.PhoneInfo
    public Bundle blokingGetPhoneInfo(int i2) {
        Bundle bundle = new Bundle();
        try {
            PhoneNum phoneNum = (PhoneNum) this.mPhoneNumKeeper.obtainPhoneNum(i2).get(this.timeOutMs, TimeUnit.MILLISECONDS);
            if (phoneNum == null || phoneNum.errorCode != 0) {
                Log.i(this.TAG, "blokingGetPhoneInfo " + phoneNum);
            } else {
                bundle.putString(AuthorizeActivityBase.KEY_ACTIVATORTOKEN, "activatorToken=" + phoneNum.token);
                bundle.putString(AuthorizeActivityBase.KEY_HASH, "hash=" + phoneNum.numberHash);
                bundle.putString(AuthorizeActivityBase.KEY_OPERATOR, "operator=" + Base64Coder.encodeString(phoneNum.copywriter));
                bundle.putString(AuthorizeActivityBase.KEY_OPERATORLINK, "operatorLink=" + phoneNum.operatorLink);
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        } catch (ExecutionException e3) {
            e3.printStackTrace();
        } catch (TimeoutException e4) {
            e4.printStackTrace();
        }
        return bundle;
    }
}
