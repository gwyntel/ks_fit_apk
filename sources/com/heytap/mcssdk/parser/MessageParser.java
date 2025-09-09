package com.heytap.mcssdk.parser;

import android.content.Context;
import android.content.Intent;
import com.heytap.mcssdk.PushService;
import com.heytap.mcssdk.utils.CryptoUtil;
import com.heytap.mcssdk.utils.LogUtil;
import com.heytap.msp.push.mode.BaseMode;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class MessageParser implements Parser {
    public static List<BaseMode> getMessageList(Context context, Intent intent) throws NumberFormatException {
        int i2;
        BaseMode baseMode;
        if (intent == null) {
            return null;
        }
        try {
            i2 = Integer.parseInt(CryptoUtil.sdkDecrypt(intent.getStringExtra("type")));
        } catch (Exception e2) {
            LogUtil.e("MessageParser--getMessageByIntent--Exception:" + e2.getMessage());
            i2 = 4096;
        }
        LogUtil.d("MessageParser--getMessageByIntent--type:" + i2);
        ArrayList arrayList = new ArrayList();
        for (Parser parser : PushService.getInstance().getParsers()) {
            if (parser != null && (baseMode = parser.parse(context, i2, intent)) != null) {
                arrayList.add(baseMode);
            }
        }
        return arrayList;
    }
}
