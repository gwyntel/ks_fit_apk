package com.tekartik.sqflite.operation;

import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class BatchOperation extends BaseOperation {

    /* renamed from: a, reason: collision with root package name */
    final Map f20515a;

    /* renamed from: b, reason: collision with root package name */
    final BatchOperationResult f20516b = new BatchOperationResult();

    /* renamed from: c, reason: collision with root package name */
    final boolean f20517c;

    public class BatchOperationResult implements OperationResult {

        /* renamed from: a, reason: collision with root package name */
        Object f20518a;

        /* renamed from: b, reason: collision with root package name */
        String f20519b;

        /* renamed from: c, reason: collision with root package name */
        String f20520c;

        /* renamed from: d, reason: collision with root package name */
        Object f20521d;

        public BatchOperationResult() {
        }

        @Override // com.tekartik.sqflite.operation.OperationResult
        public void error(String str, String str2, Object obj) {
            this.f20519b = str;
            this.f20520c = str2;
            this.f20521d = obj;
        }

        @Override // com.tekartik.sqflite.operation.OperationResult
        public void success(Object obj) {
            this.f20518a = obj;
        }
    }

    public BatchOperation(Map<String, Object> map, boolean z2) {
        this.f20515a = map;
        this.f20517c = z2;
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public <T> T getArgument(String str) {
        return (T) this.f20515a.get(str);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public String getMethod() {
        return (String) this.f20515a.get("method");
    }

    @Override // com.tekartik.sqflite.operation.BaseReadOperation, com.tekartik.sqflite.operation.Operation
    public boolean getNoResult() {
        return this.f20517c;
    }

    public Map<String, Object> getOperationError() {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map2.put("code", this.f20516b.f20519b);
        map2.put("message", this.f20516b.f20520c);
        map2.put("data", this.f20516b.f20521d);
        map.put("error", map2);
        return map;
    }

    @Override // com.tekartik.sqflite.operation.BaseOperation
    public OperationResult getOperationResult() {
        return this.f20516b;
    }

    public Map<String, Object> getOperationSuccessResult() {
        HashMap map = new HashMap();
        map.put("result", this.f20516b.f20518a);
        return map;
    }

    public void handleError(MethodChannel.Result result) {
        BatchOperationResult batchOperationResult = this.f20516b;
        result.error(batchOperationResult.f20519b, batchOperationResult.f20520c, batchOperationResult.f20521d);
    }

    public void handleErrorContinue(List<Map<String, Object>> list) {
        if (getNoResult()) {
            return;
        }
        list.add(getOperationError());
    }

    public void handleSuccess(List<Map<String, Object>> list) {
        if (getNoResult()) {
            return;
        }
        list.add(getOperationSuccessResult());
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public boolean hasArgument(String str) {
        return this.f20515a.containsKey(str);
    }
}
