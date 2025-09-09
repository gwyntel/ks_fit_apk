package com.alibaba.sdk.android.openaccount.message;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes2.dex */
public class MessageUtils {
    private static final Message defaultMessageNotFoundMessage;
    private static final Message defaultUnknownErrorMessage;
    private static Message messageNotFoundMessage;
    private static Message unknownErrorMessage;
    private static Map<Integer, Message> cachedMessageMetas = new HashMap();
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Object defaultMessageLoadLock = new Object();

    static {
        Message message = new Message();
        defaultMessageNotFoundMessage = message;
        message.code = 10;
        message.message = "未在消息文件中找到 id 为 {0} 的消息";
        message.action = "请检查所依赖的 SDK 项目，或若是手动拷贝 SDK 至当前开发应用所在项目，请检查是否漏拷文件 res/values 下文件";
        message.type = ExifInterface.LONGITUDE_EAST;
        Message message2 = new Message();
        defaultUnknownErrorMessage = message2;
        message2.code = 11;
        message2.message = "检索消息时发生如下错误 {0}";
        message2.action = "请检查所依赖的 SDK 项目，或若是手动拷贝 SDK 至当前开发应用所在项目，请检查是否漏拷文件 res/values 下文件";
        message2.type = ExifInterface.LONGITUDE_EAST;
    }

    public static Message createMessage(int i2, Object... objArr) {
        try {
            lock.readLock().lock();
            Message messageLoadMessage = cachedMessageMetas.get(Integer.valueOf(i2));
            if (messageLoadMessage == null) {
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    messageLoadMessage = loadMessage(i2);
                    if (messageLoadMessage != null) {
                        cachedMessageMetas.put(Integer.valueOf(i2), messageLoadMessage);
                    }
                    lock.readLock().lock();
                    lock.writeLock().unlock();
                } catch (Throwable th) {
                    lock.writeLock().unlock();
                    throw th;
                }
            }
            try {
                if (messageLoadMessage == null) {
                    return createMessageNotFoundMessage(i2);
                }
                if (objArr.length == 0) {
                    return messageLoadMessage;
                }
                Message message = (Message) messageLoadMessage.clone();
                message.message = MessageFormat.format(messageLoadMessage.message, objArr);
                return message;
            } finally {
                lock.readLock().unlock();
            }
        } catch (Exception e2) {
            AliSDKLogger.printStackTraceAndMore(e2);
            return createUnknownErrorMessage(e2.getMessage());
        }
    }

    private static Message createMessageNotFoundMessage(int i2) {
        if (messageNotFoundMessage == null) {
            synchronized (defaultMessageLoadLock) {
                try {
                    if (messageNotFoundMessage == null) {
                        Message messageLoadMessage = loadMessage(10);
                        messageNotFoundMessage = messageLoadMessage;
                        if (messageLoadMessage == null) {
                            messageNotFoundMessage = defaultMessageNotFoundMessage;
                        }
                    }
                } finally {
                }
            }
        }
        try {
            Message message = (Message) messageNotFoundMessage.clone();
            message.message = MessageFormat.format(message.message, String.valueOf(i2));
            return message;
        } catch (CloneNotSupportedException unused) {
            return messageNotFoundMessage;
        }
    }

    private static Message createUnknownErrorMessage(String str) {
        if (unknownErrorMessage == null) {
            synchronized (defaultMessageLoadLock) {
                try {
                    if (unknownErrorMessage == null) {
                        Message messageLoadMessage = loadMessage(11);
                        unknownErrorMessage = messageLoadMessage;
                        if (messageLoadMessage == null) {
                            unknownErrorMessage = defaultUnknownErrorMessage;
                        }
                    }
                } finally {
                }
            }
        }
        try {
            Message message = (Message) unknownErrorMessage.clone();
            message.message = MessageFormat.format(message.message, str);
            return message;
        } catch (CloneNotSupportedException unused) {
            return unknownErrorMessage;
        }
    }

    public static String getMessageContent(int i2, Object... objArr) {
        try {
            lock.readLock().lock();
            Message messageLoadMessage = cachedMessageMetas.get(Integer.valueOf(i2));
            if (messageLoadMessage == null) {
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    messageLoadMessage = loadMessage(i2);
                    if (messageLoadMessage != null) {
                        cachedMessageMetas.put(Integer.valueOf(i2), messageLoadMessage);
                    }
                    lock.readLock().lock();
                    lock.writeLock().unlock();
                } catch (Throwable th) {
                    lock.writeLock().unlock();
                    throw th;
                }
            }
            try {
                return messageLoadMessage == null ? createMessageNotFoundMessage(i2).message : MessageFormat.format(messageLoadMessage.message, objArr);
            } finally {
                lock.readLock().unlock();
            }
        } catch (Exception e2) {
            AliSDKLogger.printStackTraceAndMore(e2);
            return createUnknownErrorMessage(e2.getMessage()).message;
        }
    }

    private static Message loadMessage(int i2) {
        try {
            int stringIdentifier = ResourceUtils.getStringIdentifier(OpenAccountSDK.getAndroidContext(), "alisdk_openaccount_message_" + i2 + "_message");
            if (stringIdentifier == 0) {
                return null;
            }
            Message message = new Message();
            message.code = i2;
            message.message = OpenAccountSDK.getAndroidContext().getResources().getString(stringIdentifier);
            int stringIdentifier2 = ResourceUtils.getStringIdentifier(OpenAccountSDK.getAndroidContext(), "alisdk_openaccount_message_" + i2 + "_action");
            if (stringIdentifier2 != 0) {
                message.action = OpenAccountSDK.getAndroidContext().getResources().getString(stringIdentifier2);
            } else {
                message.action = "";
            }
            int stringIdentifier3 = ResourceUtils.getStringIdentifier(OpenAccountSDK.getAndroidContext(), "alisdk_openaccount_message_" + i2 + "_type");
            if (stringIdentifier3 != 0) {
                message.type = OpenAccountSDK.getAndroidContext().getResources().getString(stringIdentifier3);
            } else {
                message.type = "I";
            }
            return message;
        } catch (Exception e2) {
            AliSDKLogger.e("kernel", "Fail to get message of the code " + i2 + ", the error message is " + e2.getMessage());
            return null;
        }
    }
}
