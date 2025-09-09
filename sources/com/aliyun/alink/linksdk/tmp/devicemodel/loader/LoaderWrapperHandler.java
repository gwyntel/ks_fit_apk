package com.aliyun.alink.linksdk.tmp.devicemodel.loader;

import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class LoaderWrapperHandler implements ILoaderHandler {
    protected static final String TAG = "[Tmp]SerWrapperListener";
    protected ILoaderHandler mListener;
    protected volatile int taskCount = 0;

    public LoaderWrapperHandler(ILoaderHandler iLoaderHandler) {
        this.mListener = iLoaderHandler;
    }

    public void decreaseTaskCount() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.taskCount--;
        LogCat.d(TAG, "decreaseTaskCount taskCount:" + this.taskCount);
    }

    public void increaseTaskCount() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.taskCount++;
        LogCat.d(TAG, "increaseTaskCount taskCount:" + this.taskCount);
    }

    public boolean isAllTaskFinished() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LogCat.d(TAG, "isAllTaskFinished taskCount:" + this.taskCount);
        return this.taskCount == 0;
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler
    public void onDeserialize(DeviceModel deviceModel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mListener == null) {
            LogCat.e(TAG, "onDeserialize listener error");
        } else if (isAllTaskFinished()) {
            this.mListener.onDeserialize(deviceModel);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler
    public void onDeserializeError(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ILoaderHandler iLoaderHandler = this.mListener;
        if (iLoaderHandler == null) {
            LogCat.e(TAG, "onDeserializeError listener error");
            return;
        }
        iLoaderHandler.onDeserializeError(str);
        this.mListener = null;
        LogCat.d(TAG, "onDeserializeError callback empty listener");
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler
    public void onSerialize(String str) {
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.ILoaderHandler
    public void onSerializeError(String str) {
    }
}
