package com.tekartik.sqflite.operation;

/* loaded from: classes4.dex */
public abstract class BaseOperation extends BaseReadOperation {
    @Override // com.tekartik.sqflite.operation.OperationResult
    public void error(String str, String str2, Object obj) {
        getOperationResult().error(str, str2, obj);
    }

    protected abstract OperationResult getOperationResult();

    @Override // com.tekartik.sqflite.operation.OperationResult
    public void success(Object obj) {
        getOperationResult().success(obj);
    }
}
