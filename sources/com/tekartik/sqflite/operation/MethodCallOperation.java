package com.tekartik.sqflite.operation;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/* loaded from: classes4.dex */
public class MethodCallOperation extends BaseOperation {

    /* renamed from: a, reason: collision with root package name */
    final MethodCall f20523a;
    public final Result result;

    class Result implements OperationResult {

        /* renamed from: a, reason: collision with root package name */
        final MethodChannel.Result f20524a;

        Result(MethodChannel.Result result) {
            this.f20524a = result;
        }

        @Override // com.tekartik.sqflite.operation.OperationResult
        public void error(String str, String str2, Object obj) {
            this.f20524a.error(str, str2, obj);
        }

        @Override // com.tekartik.sqflite.operation.OperationResult
        public void success(Object obj) {
            this.f20524a.success(obj);
        }
    }

    public MethodCallOperation(MethodCall methodCall, MethodChannel.Result result) {
        this.f20523a = methodCall;
        this.result = new Result(result);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public <T> T getArgument(String str) {
        return (T) this.f20523a.argument(str);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public String getMethod() {
        return this.f20523a.method;
    }

    @Override // com.tekartik.sqflite.operation.BaseOperation
    public OperationResult getOperationResult() {
        return this.result;
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public boolean hasArgument(String str) {
        return this.f20523a.hasArgument(str);
    }
}
