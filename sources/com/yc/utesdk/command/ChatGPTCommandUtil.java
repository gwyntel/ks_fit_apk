package com.yc.utesdk.command;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.AIAgentMemoInfo;
import com.yc.utesdk.bean.AIAgentType;
import com.yc.utesdk.bean.AITranslateLan;
import com.yc.utesdk.bean.AITranslatePlay;
import com.yc.utesdk.bean.ChatGptAnswerContent;
import com.yc.utesdk.bean.ChatGptMemoInfo;
import com.yc.utesdk.bean.ChatGptStatus;
import com.yc.utesdk.bean.ChatGptVoiceContent;
import com.yc.utesdk.bean.GptVoiceInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.listener.AIAgentMemoSyncListener;
import com.yc.utesdk.listener.ChatGptMemoSyncListener;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ChatGPTCommandUtil {
    private static ChatGPTCommandUtil Instance = null;
    private static final String TAG = "ChatGPTCommandUtil--";
    private int cmdHead;
    private int flag;
    private AIAgentMemoSyncListener mAIAgentMemoSyncListener;
    private ChatGptMemoSyncListener mChatGptMemoSyncListener;
    private GptVoiceInfo mGptVoiceInfo;
    private Handler mHandler;
    private byte[] mAllSendData = null;
    private boolean isSendDataFD = false;
    private boolean isSendFinish = true;
    private int mAppCrc = 0;
    private byte[] mGptVoiceOpusData = new byte[0];
    private List<byte[]> subChatGptMemoInfoList = null;
    private List<byte[]> subAIAgentMemoInfoList = null;
    private int totalContactsDataLength = 100;
    private int currentContactsDataLength = 0;
    private int totalAIAgentDataLength = 100;
    private int currentAIAgentDataLength = 0;
    public int NOSectionOnline = 0;
    public int NOSectionAIAgent = 0;
    private final int SEND_DATA_SEG_MSG = 1;
    private final int SEND_DATA_SEG_AB04 = 2;
    private final int SEND_DATA_SEG_AB08 = 3;
    private final int head01EF = 495;

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ChatGPTCommandUtil chatGPTCommandUtil;
            int i2;
            ChatGPTCommandUtil chatGPTCommandUtil2;
            int i3;
            int i4 = message.what;
            if (i4 == 1) {
                if (!DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                    ChatGPTCommandUtil.this.utenint(message.arg1);
                    return;
                }
                LogUtils.i("设备忙，等20ms");
                Message message2 = new Message();
                message2.what = 1;
                message2.arg1 = message.arg1;
                ChatGPTCommandUtil.this.mHandler.sendMessageDelayed(message2, 20L);
                return;
            }
            if (i4 == 2) {
                int i5 = message.arg1;
                if (i5 == 1) {
                    ChatGPTCommandUtil chatGPTCommandUtil3 = ChatGPTCommandUtil.this;
                    chatGPTCommandUtil3.NOSectionOnline = 0;
                    chatGPTCommandUtil3.utenif(0);
                    return;
                }
                if (i5 == 2) {
                    chatGPTCommandUtil = ChatGPTCommandUtil.this;
                    i2 = chatGPTCommandUtil.NOSectionOnline + 1;
                    chatGPTCommandUtil.NOSectionOnline = i2;
                } else {
                    if (i5 != 3) {
                        return;
                    }
                    chatGPTCommandUtil = ChatGPTCommandUtil.this;
                    i2 = chatGPTCommandUtil.NOSectionOnline;
                }
                chatGPTCommandUtil.utenif(i2);
                return;
            }
            if (i4 != 3) {
                return;
            }
            int i6 = message.arg1;
            if (i6 == 1) {
                ChatGPTCommandUtil chatGPTCommandUtil4 = ChatGPTCommandUtil.this;
                chatGPTCommandUtil4.NOSectionAIAgent = 0;
                chatGPTCommandUtil4.utendo(0);
                return;
            }
            if (i6 == 2) {
                chatGPTCommandUtil2 = ChatGPTCommandUtil.this;
                i3 = chatGPTCommandUtil2.NOSectionAIAgent + 1;
                chatGPTCommandUtil2.NOSectionAIAgent = i3;
            } else {
                if (i6 != 3) {
                    return;
                }
                chatGPTCommandUtil2 = ChatGPTCommandUtil.this;
                i3 = chatGPTCommandUtil2.NOSectionAIAgent;
            }
            chatGPTCommandUtil2.utendo(i3);
        }
    }

    public ChatGPTCommandUtil() {
        utendo();
    }

    public static ChatGPTCommandUtil getInstance() {
        if (Instance == null) {
            Instance = new ChatGPTCommandUtil();
        }
        return Instance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v34, types: [com.yc.utesdk.bean.GptVoiceInfo] */
    /* JADX WARN: Type inference failed for: r0v36, types: [com.yc.utesdk.bean.AITranslateLan] */
    /* JADX WARN: Type inference failed for: r0v38, types: [com.yc.utesdk.bean.AITranslatePlay] */
    public void doChatGptData01EF(byte[] bArr) {
        int i2;
        int i3;
        UteListenerManager uteListenerManager;
        int i4;
        ChatGptStatus chatGptStatus;
        int i5 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
        switch (i5) {
            case 43777:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onChatGPTStatus(true, 1);
                break;
            case 43778:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i6 = bArr[4] & 255;
                if (i6 != 253) {
                    i2 = i6;
                    utennew(i2);
                    break;
                } else {
                    LogUtils.i("setChatGptVoiceContent 完成 bleCrc =" + (bArr[5] & 255));
                    UteListenerManager.getInstance().onChatGPTStatus(true, 2);
                    break;
                }
            case 43779:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                i2 = bArr[4] & 255;
                if (i2 == 253) {
                    LogUtils.i("ChatGptAnswerContent 完成 bleCrc =" + (bArr[5] & 255));
                    UteListenerManager.getInstance().onChatGPTStatus(true, 3);
                    break;
                }
                utennew(i2);
                break;
            case 43780:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i7 = bArr[5] & 255;
                Message message = new Message();
                message.what = 2;
                message.arg1 = i7;
                this.mHandler.sendMessage(message);
                break;
            case 43781:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                this.currentContactsDataLength += SPUtil.getInstance().getMaxCommunicationLength() - 5;
                utennew();
                i3 = bArr[4] & 255;
                if (i3 == 253) {
                }
                utennew(i3);
                break;
            case 43782:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onChatGPTStatus(true, 5);
                break;
            case 43783:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                i2 = bArr[4] & 255;
                if (i2 == 253) {
                    LogUtils.i("SET_AI_AGENT_TYPE 完成 bleCrc =" + (bArr[5] & 255));
                    UteListenerManager.getInstance().onChatGPTStatus(true, 6);
                    break;
                }
                utennew(i2);
                break;
            case 43784:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i8 = bArr[5] & 255;
                Message message2 = new Message();
                message2.what = 3;
                message2.arg1 = i8;
                this.mHandler.sendMessage(message2);
                break;
            case 43785:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                this.currentAIAgentDataLength += SPUtil.getInstance().getMaxCommunicationLength() - 5;
                utenint();
                i3 = bArr[4] & 255;
                if (i3 == 253) {
                }
                utennew(i3);
                break;
            case 43786:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                i2 = bArr[4] & 255;
                if (i2 == 253) {
                    LogUtils.i("SET_LARGE_MODE_TYPE 完成 bleCrc =" + (bArr[5] & 255));
                    UteListenerManager.getInstance().onChatGPTStatus(true, 8);
                    break;
                }
                utennew(i2);
                break;
            default:
                switch (i5) {
                    case 44033:
                        ChatGptStatus chatGptStatus2 = new ChatGptStatus();
                        chatGptStatus2.setStatus(bArr[5] & 255);
                        uteListenerManager = UteListenerManager.getInstance();
                        i4 = 101;
                        chatGptStatus = chatGptStatus2;
                        break;
                    case 44034:
                        int i9 = ((bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[5] & 255);
                        if (i9 != 65021) {
                            int length = bArr.length - 6;
                            byte[] bArr2 = new byte[length];
                            System.arraycopy(bArr, 6, bArr2, 0, length);
                            if (i9 != 0) {
                                this.mGptVoiceOpusData = ByteDataUtil.getInstance().copyTwoArrays(this.mGptVoiceOpusData, bArr2);
                                break;
                            } else {
                                this.mGptVoiceOpusData = bArr2;
                                break;
                            }
                        } else {
                            byte b2 = bArr[6];
                            LogUtils.i("mGptVoiceOpusData =" + GBUtils.getInstance().bytes2HexString(this.mGptVoiceOpusData));
                            this.mGptVoiceInfo = utendo(this.mGptVoiceOpusData);
                            uteListenerManager = UteListenerManager.getInstance();
                            i4 = 102;
                            chatGptStatus = this.mGptVoiceInfo;
                            break;
                        }
                    case 44035:
                        AIAgentType aIAgentType = new AIAgentType();
                        int i10 = bArr[4] & 255;
                        int i11 = bArr[5] & 255;
                        aIAgentType.setType(i11);
                        if (i10 == 2 && i11 == 0) {
                            aIAgentType.setLargeMode(bArr[6] & 255);
                        }
                        UteListenerManager.getInstance().onChatGPTNotify(103, aIAgentType);
                        break;
                    case 44036:
                        ?? aITranslateLan = new AITranslateLan();
                        aITranslateLan.setFromLan(bArr[5] & 255);
                        aITranslateLan.setToLan(bArr[6] & 255);
                        uteListenerManager = UteListenerManager.getInstance();
                        i4 = 104;
                        chatGptStatus = aITranslateLan;
                        break;
                    case 44037:
                        ?? aITranslatePlay = new AITranslatePlay();
                        aITranslatePlay.setStatus(bArr[5] & 255);
                        uteListenerManager = UteListenerManager.getInstance();
                        i4 = 150;
                        chatGptStatus = aITranslatePlay;
                        break;
                }
                uteListenerManager.onChatGPTNotify(i4, chatGptStatus);
                break;
        }
    }

    public void setAIAgentType(List<Integer> list) {
        byte[] bArrUtenfor = utenfor(list);
        if (bArrUtenfor == null) {
            UteListenerManager.getInstance().onChatGPTStatus(false, 6);
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_AI_AGENT_TYPE);
        LogUtils.e("setAIAgentType = " + new GBUtils().bytes2HexString(bArrUtenfor));
        this.mAllSendData = bArrUtenfor;
        this.cmdHead = 495;
        this.flag = 43783;
        this.mAppCrc = 0;
        utenint(0);
    }

    public void setChatGptAnswerContent(ChatGptAnswerContent chatGptAnswerContent) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(221);
        byte[] bArrUtendo = utendo(chatGptAnswerContent.getContent());
        if (bArrUtendo == null) {
            UteListenerManager.getInstance().onChatGPTStatus(false, 3);
            return;
        }
        LogUtils.e("setChatGptVoiceContent = " + new GBUtils().bytes2HexString(bArrUtendo));
        this.mAllSendData = bArrUtendo;
        this.cmdHead = 495;
        this.flag = 43779;
        this.mAppCrc = 0;
        utenint(0);
    }

    public void setChatGptLanguageEnvironment(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_CHAT_GPT_LANGUAGE_ENVIRONMENT);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -17, -85, 6, 1, (byte) i2});
    }

    public void setChatGptStatus(ChatGptStatus chatGptStatus) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(219);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -17, -85, 1, 1, (byte) chatGptStatus.getStatus()});
    }

    public void setChatGptVoiceContent(ChatGptVoiceContent chatGptVoiceContent) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(220);
        byte[] bArrUtendo = utendo(chatGptVoiceContent.getContent());
        if (bArrUtendo == null) {
            UteListenerManager.getInstance().onChatGPTStatus(false, 2);
            return;
        }
        LogUtils.e("setChatGptVoiceContent = " + new GBUtils().bytes2HexString(bArrUtendo));
        this.mAllSendData = bArrUtendo;
        this.cmdHead = 495;
        this.flag = 43778;
        this.mAppCrc = 0;
        utenint(0);
    }

    public void setLargeModeType(List<Integer> list) {
        byte[] bArrUtenfor = utenfor(list);
        if (bArrUtenfor == null) {
            UteListenerManager.getInstance().onChatGPTStatus(false, 8);
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_LARGE_MODE_TYPE);
        LogUtils.e("setLargeModeType = " + new GBUtils().bytes2HexString(bArrUtenfor));
        this.mAllSendData = bArrUtenfor;
        this.cmdHead = 495;
        this.flag = 43786;
        this.mAppCrc = 0;
        utenint(0);
    }

    public void syncAIAgentMemoToDevice(List<AIAgentMemoInfo> list, AIAgentMemoSyncListener aIAgentMemoSyncListener) {
        this.mAIAgentMemoSyncListener = aIAgentMemoSyncListener;
        this.subAIAgentMemoInfoList = utendo(list);
        AIAgentMemoSyncListener aIAgentMemoSyncListener2 = this.mAIAgentMemoSyncListener;
        if (aIAgentMemoSyncListener2 != null) {
            aIAgentMemoSyncListener2.onAIAgentMemoSyncState(1);
        }
        utenif();
    }

    public void syncChatGptMemoToDevice(List<ChatGptMemoInfo> list, ChatGptMemoSyncListener chatGptMemoSyncListener) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(222);
        this.mChatGptMemoSyncListener = chatGptMemoSyncListener;
        this.subChatGptMemoInfoList = utenif(list);
        ChatGptMemoSyncListener chatGptMemoSyncListener2 = this.mChatGptMemoSyncListener;
        if (chatGptMemoSyncListener2 != null) {
            chatGptMemoSyncListener2.onChatGptMemoSyncState(1);
        }
        utenfor();
    }

    public final synchronized void utenint(int i2) {
        try {
            byte[] bArr = this.mAllSendData;
            if (bArr == null) {
                return;
            }
            int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
            int i3 = maxCommunicationLength - 5;
            int length = bArr.length;
            int i4 = length / i3;
            int i5 = length % i3;
            LogUtils.i(TAG, "max=" + i3 + ",length=" + length + ",sendCount=" + i4 + ",lastCount=" + i5 + ",section=" + i2 + ",isSendDataFD=" + this.isSendDataFD + ",ThreadId=" + Thread.currentThread().getId());
            int i6 = 0;
            if (i2 < i4) {
                byte[] bArr2 = new byte[maxCommunicationLength];
                int i7 = this.cmdHead;
                bArr2[0] = (byte) ((i7 >> 8) & 255);
                bArr2[1] = (byte) (i7 & 255);
                int i8 = this.flag;
                bArr2[2] = (byte) ((i8 >> 8) & 255);
                bArr2[3] = (byte) (i8 & 255);
                bArr2[4] = (byte) i2;
                System.arraycopy(bArr, i2 * i3, bArr2, 5, i3);
                while (i6 < maxCommunicationLength) {
                    this.mAppCrc = bArr2[i6] ^ this.mAppCrc;
                    i6++;
                }
                WriteCommandToBLE.getInstance().writeCharaBle5(bArr2);
                if (i5 == 0) {
                    this.isSendDataFD = true;
                }
            } else if (this.isSendDataFD) {
                utenfor(this.mAppCrc);
                this.isSendDataFD = false;
            } else if (i5 != 0) {
                int i9 = i5 + 5;
                byte[] bArr3 = new byte[i9];
                int i10 = this.cmdHead;
                bArr3[0] = (byte) ((i10 >> 8) & 255);
                bArr3[1] = (byte) (i10 & 255);
                int i11 = this.flag;
                bArr3[2] = (byte) ((i11 >> 8) & 255);
                bArr3[3] = (byte) (i11 & 255);
                bArr3[4] = (byte) i2;
                System.arraycopy(bArr, i3 * i2, bArr3, 5, i5);
                while (i6 < i9) {
                    this.mAppCrc = bArr3[i6] ^ this.mAppCrc;
                    i6++;
                }
                WriteCommandToBLE.getInstance().writeCharaBle5(bArr3);
                this.isSendDataFD = true;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void utennew(int i2) {
        Message message = new Message();
        message.what = 1;
        message.arg1 = i2 + 1;
        this.mHandler.sendMessageDelayed(message, 0L);
    }

    public final byte[] utenfor(List list) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        sb.append(String.format("%02X", Integer.valueOf(size)));
        for (int i2 = 0; i2 < size; i2++) {
            Integer num = (Integer) list.get(i2);
            num.intValue();
            sb.append(String.format("%02X", num));
        }
        return GBUtils.getInstance().hexStringToBytes(sb.toString());
    }

    public final List utenif(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            this.totalContactsDataLength = 0;
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < list.size(); i2++) {
                ChatGptMemoInfo chatGptMemoInfo = (ChatGptMemoInfo) list.get(i2);
                int timestamp = chatGptMemoInfo.getTimestamp();
                String questionContent = chatGptMemoInfo.getQuestionContent();
                String answerContent = chatGptMemoInfo.getAnswerContent();
                if (!TextUtils.isEmpty(questionContent) && !TextUtils.isEmpty(answerContent)) {
                    sb.append("04");
                    sb.append(String.format("%08X", Integer.valueOf(timestamp)));
                    if (questionContent.length() > 127) {
                        questionContent = questionContent.substring(0, 127);
                    }
                    String strString2unicode = GBUtils.getInstance().string2unicode(questionContent);
                    sb.append(String.format("%02X", Integer.valueOf(strString2unicode.length() / 2)));
                    sb.append(strString2unicode);
                    if (answerContent.length() > 127) {
                        answerContent = answerContent.substring(0, 127);
                    }
                    String strString2unicode2 = GBUtils.getInstance().string2unicode(answerContent);
                    sb.append(String.format("%02X", Integer.valueOf(strString2unicode2.length() / 2)));
                    sb.append(strString2unicode2);
                    LogUtils.i("sb.length()  =" + sb.length());
                    if (sb.length() > 2970 || i2 == list.size() - 1) {
                        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(sb.toString());
                        arrayList.add(bArrHexStringToBytes);
                        this.totalContactsDataLength += bArrHexStringToBytes.length;
                        StringBuilder sb2 = new StringBuilder();
                        LogUtils.i("segData.length =" + bArrHexStringToBytes.length);
                        sb = sb2;
                    }
                }
            }
            this.currentContactsDataLength = 0;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                LogUtils.i("subList.length =" + ((byte[]) arrayList.get(i3)).length);
            }
        }
        return arrayList;
    }

    public final void utenint() {
        if (this.mAIAgentMemoSyncListener != null) {
            this.mAIAgentMemoSyncListener.onAIAgentMemoSyncProgress(Math.min((this.currentAIAgentDataLength * 100) / this.totalAIAgentDataLength, 100));
        }
    }

    public final void utennew() {
        if (this.mChatGptMemoSyncListener != null) {
            this.mChatGptMemoSyncListener.onChatGptMemoSyncProgress(Math.min((this.currentContactsDataLength * 100) / this.totalContactsDataLength, 100));
        }
    }

    public final GptVoiceInfo utendo(byte[] bArr) {
        GptVoiceInfo gptVoiceInfo = new GptVoiceInfo();
        if (bArr != null && bArr.length > 4) {
            int i2 = ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255);
            int length = bArr.length - 4;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 4, bArr2, 0, length);
            gptVoiceInfo.setDataLength(i2);
            gptVoiceInfo.setVoiceOpusData(bArr2);
        }
        return gptVoiceInfo;
    }

    public final void utenfor() {
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -17, -85, 4, 1, 0});
    }

    public final void utenif() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SYNC_AI_AGENT_MEMO_TO_DEVICE);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -17, -85, 8, 1, 0});
    }

    public final List utendo(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            this.totalAIAgentDataLength = 0;
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < list.size(); i2++) {
                AIAgentMemoInfo aIAgentMemoInfo = (AIAgentMemoInfo) list.get(i2);
                int timestamp = aIAgentMemoInfo.getTimestamp();
                String questionContent = aIAgentMemoInfo.getQuestionContent();
                String answerContent = aIAgentMemoInfo.getAnswerContent();
                if (!TextUtils.isEmpty(questionContent) && !TextUtils.isEmpty(answerContent)) {
                    sb.append("04");
                    sb.append(String.format("%08X", Integer.valueOf(timestamp)));
                    if (questionContent.length() > 127) {
                        questionContent = questionContent.substring(0, 127);
                    }
                    String strString2unicode = GBUtils.getInstance().string2unicode(questionContent);
                    sb.append(String.format("%02X", Integer.valueOf(strString2unicode.length() / 2)));
                    sb.append(strString2unicode);
                    if (answerContent.length() > 127) {
                        answerContent = answerContent.substring(0, 127);
                    }
                    String strString2unicode2 = GBUtils.getInstance().string2unicode(answerContent);
                    sb.append(String.format("%02X", Integer.valueOf(strString2unicode2.length() / 2)));
                    sb.append(strString2unicode2);
                    LogUtils.i("sb.length()  =" + sb.length());
                    if (sb.length() > 2970 || i2 == list.size() - 1) {
                        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(sb.toString());
                        arrayList.add(bArrHexStringToBytes);
                        this.totalAIAgentDataLength += bArrHexStringToBytes.length;
                        StringBuilder sb2 = new StringBuilder();
                        LogUtils.i("segData.length =" + bArrHexStringToBytes.length);
                        sb = sb2;
                    }
                }
            }
            this.currentAIAgentDataLength = 0;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                LogUtils.i("subList.length =" + ((byte[]) arrayList.get(i3)).length);
            }
        }
        return arrayList;
    }

    public final void utenfor(int i2) {
        int i3 = this.cmdHead;
        int i4 = this.flag;
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{(byte) ((i3 >> 8) & 255), (byte) (i3 & 255), (byte) ((i4 >> 8) & 255), (byte) (i4 & 255), -3, (byte) i2});
        this.mAppCrc = 0;
    }

    public final void utenif(int i2) {
        LogUtils.i(TAG, "备忘录 发送，总共有 " + this.subChatGptMemoInfoList.size() + " 条，现在发送第 " + i2 + " 条");
        if (i2 >= this.subChatGptMemoInfoList.size()) {
            ChatGptMemoSyncListener chatGptMemoSyncListener = this.mChatGptMemoSyncListener;
            if (chatGptMemoSyncListener != null) {
                chatGptMemoSyncListener.onChatGptMemoSyncState(2);
            }
            UteListenerManager.getInstance().onChatGPTStatus(true, 1);
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(222);
        byte[] bArr = this.subChatGptMemoInfoList.get(i2);
        this.mAllSendData = ByteDataUtil.getInstance().copyTwoArrays(new byte[]{(byte) ((bArr.length >> 8) & 255), (byte) (bArr.length & 255)}, bArr);
        this.cmdHead = 495;
        this.flag = 43781;
        this.mAppCrc = 0;
        utenint(0);
    }

    public final byte[] utendo(String str) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            if (str.length() > 128) {
                str = str.substring(0, 128);
            }
            String strString2unicode = GBUtils.getInstance().string2unicode(str);
            sb.append(String.format("%04X", Integer.valueOf(strString2unicode.length() / 2)));
            sb.append(strString2unicode);
        }
        return GBUtils.getInstance().hexStringToBytes(sb.toString());
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utendo(int i2) {
        LogUtils.i(TAG, "智能体备忘录 发送，总共有 " + this.subAIAgentMemoInfoList.size() + " 条，现在发送第 " + i2 + " 条");
        if (i2 >= this.subAIAgentMemoInfoList.size()) {
            AIAgentMemoSyncListener aIAgentMemoSyncListener = this.mAIAgentMemoSyncListener;
            if (aIAgentMemoSyncListener != null) {
                aIAgentMemoSyncListener.onAIAgentMemoSyncState(2);
                return;
            }
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SYNC_AI_AGENT_MEMO_TO_DEVICE);
        byte[] bArr = this.subAIAgentMemoInfoList.get(i2);
        this.mAllSendData = ByteDataUtil.getInstance().copyTwoArrays(new byte[]{(byte) ((bArr.length >> 8) & 255), (byte) (bArr.length & 255)}, bArr);
        this.cmdHead = 495;
        this.flag = 43785;
        this.mAppCrc = 0;
        utenint(0);
    }
}
