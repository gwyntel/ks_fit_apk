package com.tekartik.sqflite.operation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.tekartik.sqflite.Constant;
import com.tekartik.sqflite.SqlCommand;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseReadOperation implements Operation {
    private Boolean getBoolean(String str) {
        Object argument = getArgument(str);
        if (argument instanceof Boolean) {
            return (Boolean) argument;
        }
        return null;
    }

    private String getSql() {
        return (String) getArgument(Constant.PARAM_SQL);
    }

    private List<Object> getSqlArguments() {
        return (List) getArgument(Constant.PARAM_SQL_ARGUMENTS);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public boolean getContinueOnError() {
        return Boolean.TRUE.equals(getArgument(Constant.PARAM_CONTINUE_OR_ERROR));
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public Boolean getInTransactionChange() {
        return getBoolean(Constant.PARAM_IN_TRANSACTION_CHANGE);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public boolean getNoResult() {
        return Boolean.TRUE.equals(getArgument(Constant.PARAM_NO_RESULT));
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public SqlCommand getSqlCommand() {
        return new SqlCommand(getSql(), getSqlArguments());
    }

    @Override // com.tekartik.sqflite.operation.Operation
    @Nullable
    public Integer getTransactionId() {
        return (Integer) getArgument(Constant.PARAM_TRANSACTION_ID);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public boolean hasNullTransactionId() {
        return hasArgument(Constant.PARAM_TRANSACTION_ID) && getTransactionId() == null;
    }

    @NonNull
    public String toString() {
        return getMethod() + " " + getSql() + " " + getSqlArguments();
    }
}
