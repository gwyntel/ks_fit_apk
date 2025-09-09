package com.heytap.mcssdk.parser;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.heytap.mcssdk.constant.IntentConstant;
import com.heytap.mcssdk.constant.McsEventConstant;
import com.heytap.mcssdk.statis.McsStatisticUtils;
import com.heytap.mcssdk.utils.CryptoUtil;
import com.heytap.mcssdk.utils.LogUtil;
import com.heytap.msp.push.mode.BaseMode;
import com.heytap.msp.push.mode.DataMessage;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DataMessageParser extends MessageParser {
    public String getMsgCommand(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new JSONObject(str).optString(IntentConstant.MSG_COMMAND);
        } catch (JSONException e2) {
            LogUtil.d(e2.getMessage());
            return "";
        }
    }

    @Override // com.heytap.mcssdk.parser.Parser
    public BaseMode parse(Context context, int i2, Intent intent) throws NumberFormatException {
        if (4103 != i2 && 4098 != i2 && 4108 != i2) {
            return null;
        }
        BaseMode messageByIntent = parseMessageByIntent(intent, i2);
        McsStatisticUtils.statisticEvent(context, McsEventConstant.EventId.EVENT_ID_PUSH_TRANSMIT, (DataMessage) messageByIntent);
        return messageByIntent;
    }

    public BaseMode parseMessageByIntent(Intent intent, int i2) throws NumberFormatException {
        try {
            DataMessage dataMessage = new DataMessage();
            dataMessage.setMessageID(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.MESSAGE_ID)));
            dataMessage.setTaskID(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.TASK_ID)));
            dataMessage.setGlobalId(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.GLOBAL_ID)));
            dataMessage.setAppPackage(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.APP_PACKAGE)));
            dataMessage.setTitle(CryptoUtil.sdkDecrypt(intent.getStringExtra("title")));
            dataMessage.setContent(CryptoUtil.sdkDecrypt(intent.getStringExtra("content")));
            dataMessage.setDescription(CryptoUtil.sdkDecrypt(intent.getStringExtra("description")));
            String strSdkDecrypt = CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.NOTIFY_ID));
            int i3 = 0;
            dataMessage.setNotifyID(TextUtils.isEmpty(strSdkDecrypt) ? 0 : Integer.parseInt(strSdkDecrypt));
            dataMessage.setMiniProgramPkg(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.MINI_PROGRAM_PKG)));
            dataMessage.setMessageType(i2);
            dataMessage.setEventId(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.EVENT_ID)));
            dataMessage.setStatisticsExtra(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.STATISTICS_EXTRA)));
            String strSdkDecrypt2 = CryptoUtil.sdkDecrypt(intent.getStringExtra("data_extra"));
            dataMessage.setDataExtra(strSdkDecrypt2);
            String msgCommand = getMsgCommand(strSdkDecrypt2);
            if (!TextUtils.isEmpty(msgCommand)) {
                i3 = Integer.parseInt(msgCommand);
            }
            dataMessage.setMsgCommand(i3);
            dataMessage.setBalanceTime(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.BALANCE_TIME)));
            dataMessage.setStartDate(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.START_DATE)));
            dataMessage.setEndDate(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.END_DATE)));
            dataMessage.setTimeRanges(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.TIME_RANGES)));
            dataMessage.setRule(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.RULE)));
            dataMessage.setForcedDelivery(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.FORCED_DELIVERY)));
            dataMessage.setDistinctContent(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.DISTINCT_CONTENT)));
            dataMessage.setAppId(CryptoUtil.sdkDecrypt(intent.getStringExtra(IntentConstant.APP_ID)));
            return dataMessage;
        } catch (Exception e2) {
            LogUtil.d("OnHandleIntent--" + e2.getMessage());
            return null;
        }
    }
}
