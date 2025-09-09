package com.tekartik.sqflite.operation;

import androidx.annotation.Nullable;
import com.tekartik.sqflite.SqlCommand;

/* loaded from: classes4.dex */
public interface Operation extends OperationResult {
    <T> T getArgument(String str);

    boolean getContinueOnError();

    Boolean getInTransactionChange();

    String getMethod();

    boolean getNoResult();

    SqlCommand getSqlCommand();

    @Nullable
    Integer getTransactionId();

    boolean hasArgument(String str);

    boolean hasNullTransactionId();
}
