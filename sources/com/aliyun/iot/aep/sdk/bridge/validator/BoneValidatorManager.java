package com.aliyun.iot.aep.sdk.bridge.validator;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class BoneValidatorManager {

    /* renamed from: a, reason: collision with root package name */
    private static List<BoneValidator> f11698a = new LinkedList();

    static class a implements BoneValidator {

        /* renamed from: a, reason: collision with root package name */
        private int f11699a = 0;

        /* renamed from: b, reason: collision with root package name */
        private int f11700b;

        /* renamed from: c, reason: collision with root package name */
        private List<BoneValidator> f11701c;

        a(List<BoneValidator> list) {
            this.f11700b = list.size();
            this.f11701c = new ArrayList(list);
        }

        static /* synthetic */ int a(a aVar) {
            int i2 = aVar.f11699a;
            aVar.f11699a = i2 + 1;
            return i2;
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.validator.BoneValidator
        public void validate(JSContext jSContext, BoneCall boneCall, BoneValidateListener boneValidateListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (jSContext == null) {
                throw new IllegalArgumentException("context can not be null");
            }
            if (jSContext.getCurrentActivity() == null) {
                ALog.d("DefaultBoneValidator", "ignore call after destroy");
                return;
            }
            if (TextUtils.isEmpty(jSContext.getCurrentUrl())) {
                throw new IllegalArgumentException("jsContext.getCurrentUrl can not be empty");
            }
            if (boneCall == null) {
                throw new IllegalArgumentException("call can not be null");
            }
            if (TextUtils.isEmpty(boneCall.serviceId)) {
                throw new IllegalArgumentException("call.serviceId can not be empty");
            }
            if (TextUtils.isEmpty(boneCall.methodName)) {
                throw new IllegalArgumentException("call.methodName can not be empty");
            }
            if (boneCall.mode == null) {
                throw new IllegalArgumentException("call.mode can not be null");
            }
            if (boneValidateListener == null) {
                throw new IllegalArgumentException("listener can not be null");
            }
            a(jSContext, boneCall, boneValidateListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final JSContext jSContext, final BoneCall boneCall, final BoneValidateListener boneValidateListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            int i2 = this.f11699a;
            if (i2 == this.f11700b) {
                try {
                    boneValidateListener.onAuthorized();
                    return;
                } catch (Exception e2) {
                    ALog.e("BoneValidatorManager", "exception happens when call listener.onAuthorized()", e2);
                    e2.printStackTrace();
                    return;
                }
            }
            this.f11701c.get(i2).validate(jSContext, boneCall, new BoneValidateListener() { // from class: com.aliyun.iot.aep.sdk.bridge.validator.BoneValidatorManager.a.1
                @Override // com.aliyun.iot.aep.sdk.bridge.validator.BoneValidateListener
                public void onAuthorized() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    a.a(a.this);
                    a.this.a(jSContext, boneCall, boneValidateListener);
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.validator.BoneValidateListener
                public void onPermissionDie(String str, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    try {
                        boneValidateListener.onPermissionDie(str, str2, str3);
                    } catch (Exception e3) {
                        ALog.e("BoneValidatorManager", "exception happens when call listener.onAuthorized()", e3);
                        e3.printStackTrace();
                    }
                }
            });
        }
    }

    public static synchronized void add(BoneValidator boneValidator) {
        if (boneValidator == null) {
            throw new IllegalArgumentException("validator can not be null");
        }
        ALog.d("BoneValidatorManager", "add " + boneValidator);
        if (f11698a.contains(boneValidator)) {
            return;
        }
        ALog.d("BoneValidatorManager", "add " + boneValidator);
        f11698a.add(boneValidator);
    }

    public static synchronized BoneValidator getDefaultValidator() {
        ALog.d("BoneValidatorManager", "getDefaultValidator is called");
        return new a(f11698a);
    }

    public static synchronized void remove(BoneValidator boneValidator) {
        if (boneValidator == null) {
            throw new IllegalArgumentException("validator can not be null");
        }
        f11698a.remove(boneValidator);
    }
}
