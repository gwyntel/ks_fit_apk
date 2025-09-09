package com.aliyun.alink.linksdk.tmp.devicemodel.loader;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class SingleExtendSerializer extends DeviceModelSerializer {

    public static class DeserializeTask extends AsyncTask<String, String, String> {
        protected String mChildThingId;
        protected DeviceModel mDevcieModel;
        protected LoaderWrapperHandler mListener;
        protected DeviceModelSerializer mSerializer;
        protected String mTaskThingId;

        public static class Builder {
            DeserializeTask mTask = new DeserializeTask();

            Builder() {
            }

            public static Builder create() {
                return new Builder();
            }

            public Builder setChildThingId(String str) {
                this.mTask.mChildThingId = str;
                return this;
            }

            public Builder setCurTaskThingId(String str) {
                this.mTask.mTaskThingId = str;
                return this;
            }

            public Builder setDeviceModel(DeviceModel deviceModel) {
                this.mTask.mDevcieModel = deviceModel;
                return this;
            }

            public Builder setListener(LoaderWrapperHandler loaderWrapperHandler) {
                this.mTask.mListener = loaderWrapperHandler;
                return this;
            }

            public Builder setSerializer(DeviceModelSerializer deviceModelSerializer) {
                this.mTask.mSerializer = deviceModelSerializer;
                return this;
            }

            public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                LoaderWrapperHandler loaderWrapperHandler = this.mTask.mListener;
                if (loaderWrapperHandler != null) {
                    loaderWrapperHandler.increaseTaskCount();
                }
                this.mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            }
        }

        public DeserializeTask() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super.onPreExecute();
            LogCat.d("[Tmp]DeviceModelSerializer", "onPreExecute mTaskThingId:" + this.mTaskThingId + " mChildThingId:" + this.mChildThingId);
        }

        public void onTaskEnd() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            this.mListener.decreaseTaskCount();
            this.mListener.onDeserialize(this.mDevcieModel);
        }

        public void onTaskError(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            this.mListener.decreaseTaskCount();
            this.mListener.onDeserializeError(str);
        }

        public DeserializeTask(DeviceModelSerializer deviceModelSerializer, DeviceModel deviceModel, String str, String str2, LoaderWrapperHandler loaderWrapperHandler) {
            this.mSerializer = deviceModelSerializer;
            this.mDevcieModel = deviceModel;
            this.mListener = loaderWrapperHandler;
            this.mChildThingId = str2;
            this.mTaskThingId = str;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public String doInBackground(String... strArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            LogCat.d("[Tmp]DeviceModelSerializer", "doInBackground mTaskThingId:" + this.mTaskThingId + " mChildThingId:" + this.mChildThingId);
            if (TextUtils.isEmpty(this.mTaskThingId)) {
                return null;
            }
            String deviceModel = RootDeviceModelSerializer.getInstance().getDeviceModel(this.mTaskThingId);
            return TextUtils.isEmpty(deviceModel) ? DeviceModelSerializer.requestDeviceModel(this.mDevcieModel.getSchema(), this.mTaskThingId) : deviceModel;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            LogCat.d("[Tmp]DeviceModelSerializer", "onPostExecute mTaskThingId:" + this.mTaskThingId + " mChildThingId:" + this.mChildThingId);
            if (TextUtils.isEmpty(str)) {
                if (TextUtils.isEmpty(this.mTaskThingId)) {
                    onTaskEnd();
                    return;
                } else {
                    onTaskError("request thingid null");
                    return;
                }
            }
            DeviceModel deviceModelDeserializeInner = this.mSerializer.deserializeInner(str);
            if (deviceModelDeserializeInner == null) {
                LogCat.e("[Tmp]DeviceModelSerializer", "onPostExecute deserializeInner return null error s:" + str);
                onTaskError("deserialize null");
                return;
            }
            RootDeviceModelSerializer.getInstance().insertDeviceModel(this.mTaskThingId, str);
            DeviceModelSerializer.addChildModel(this.mDevcieModel, deviceModelDeserializeInner);
            if (deviceModelDeserializeInner.getExtend() != null && !deviceModelDeserializeInner.getExtend().isEmpty()) {
                for (int i2 = 0; i2 < deviceModelDeserializeInner.getExtend().size(); i2++) {
                    String str2 = deviceModelDeserializeInner.getExtend().get(i2);
                    if (!TextUtils.isEmpty(str2)) {
                        Builder.create().setSerializer(this.mSerializer).setDeviceModel(this.mDevcieModel).setCurTaskThingId(str2).setChildThingId(DeviceModelSerializer.expand(this.mChildThingId, str2)).setListener(this.mListener).start();
                    }
                }
            }
            onTaskEnd();
        }
    }

    public SingleExtendSerializer(String str) {
        super(str);
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.DeviceModelSerializer
    public boolean deserialize(String str, String str2, ILoaderHandler iLoaderHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DeviceModel deviceModelDeserializeInner = deserializeInner(str2);
        LoaderWrapperHandler loaderWrapperHandler = new LoaderWrapperHandler(iLoaderHandler);
        if (deviceModelDeserializeInner == null || deviceModelDeserializeInner.getExtend() == null || deviceModelDeserializeInner.getExtend().isEmpty()) {
            DeserializeTask.Builder.create().setSerializer(this).setDeviceModel(deviceModelDeserializeInner).setCurTaskThingId(null).setChildThingId(null).setListener(loaderWrapperHandler).start();
        } else {
            for (int i2 = 0; i2 < deviceModelDeserializeInner.getExtend().size(); i2++) {
                String str3 = deviceModelDeserializeInner.getExtend().get(i2);
                if (!TextUtils.isEmpty(str3)) {
                    DeserializeTask.Builder.create().setSerializer(this).setDeviceModel(deviceModelDeserializeInner).setCurTaskThingId(str3).setChildThingId(DeviceModelSerializer.expand(deviceModelDeserializeInner.getId(), str3)).setListener(loaderWrapperHandler).start();
                }
            }
        }
        return deviceModelDeserializeInner != null;
    }

    @Override // com.aliyun.alink.linksdk.tmp.devicemodel.loader.DeviceModelSerializer
    public String serialize(String str, DeviceModel deviceModel) {
        return serializeInner(deviceModel);
    }

    public SingleExtendSerializer() {
        this(RootDeviceModelSerializer.SINGLEEXTEND_DEVICEMODELSERIALIZER_ID);
    }
}
