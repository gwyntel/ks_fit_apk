package com.alibaba.sdk.android.oss.internal;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.AbortMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.MultipartUploadRequest;
import com.alibaba.sdk.android.oss.network.ExecutionContext;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public class MultipartUploadTask extends BaseMultipartUploadTask<MultipartUploadRequest, CompleteMultipartUploadResult> implements Callable<CompleteMultipartUploadResult> {
    public MultipartUploadTask(InternalRequestOperation internalRequestOperation, MultipartUploadRequest multipartUploadRequest, OSSCompletedCallback<MultipartUploadRequest, CompleteMultipartUploadResult> oSSCompletedCallback, ExecutionContext executionContext) {
        super(internalRequestOperation, multipartUploadRequest, oSSCompletedCallback, executionContext);
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    protected void abortThisUpload() throws ExecutionException, InterruptedException {
        if (this.mUploadId != null) {
            this.mApiOperation.abortMultipartUpload(new AbortMultipartUploadRequest(this.mRequest.getBucketName(), this.mRequest.getObjectKey(), this.mUploadId), null).waitUntilFinished();
        }
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    protected CompleteMultipartUploadResult doMultipartUpload() throws ExecutionException, ServiceException, ClientException, InterruptedException, IOException {
        checkCancel();
        File file = new File(this.mRequest.getUploadFilePath());
        this.mUploadFile = file;
        long length = file.length();
        this.mFileLength = length;
        if (length == 0) {
            throw new ClientException("file length must not be 0");
        }
        int[] iArr = new int[2];
        checkPartSize(iArr);
        int i2 = iArr[0];
        final int i3 = iArr[1];
        final int iMin = i2;
        int i4 = 0;
        for (final int i5 = 0; i5 < i3; i5++) {
            checkException();
            if (this.mPoolExecutor != null) {
                if (i5 == i3 - 1) {
                    iMin = (int) Math.min(iMin, this.mFileLength - i4);
                }
                i4 += iMin;
                this.mPoolExecutor.execute(new Runnable() { // from class: com.alibaba.sdk.android.oss.internal.MultipartUploadTask.1
                    @Override // java.lang.Runnable
                    public void run() throws Throwable {
                        MultipartUploadTask.this.uploadPart(i5, iMin, i3);
                    }
                });
            }
        }
        if (checkWaitCondition(i3)) {
            synchronized (this.mLock) {
                this.mLock.wait();
            }
        }
        if (this.mUploadException != null) {
            abortThisUpload();
        }
        checkException();
        CompleteMultipartUploadResult completeMultipartUploadResult = completeMultipartUploadResult();
        releasePool();
        return completeMultipartUploadResult;
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    protected void initMultipartUploadId() throws ServiceException, ClientException {
        String uploadId = ((InitiateMultipartUploadResult) this.mApiOperation.initMultipartUpload(new InitiateMultipartUploadRequest(this.mRequest.getBucketName(), this.mRequest.getObjectKey(), this.mRequest.getMetadata()), null).getResult()).getUploadId();
        this.mUploadId = uploadId;
        this.mRequest.setUploadId(uploadId);
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    protected void preUploadPart(int i2, int i3, int i4) throws Exception {
        checkException();
    }

    @Override // com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask
    protected void processException(Exception exc) {
        synchronized (this.mLock) {
            try {
                this.mPartExceptionCount++;
                if (this.mUploadException == null) {
                    this.mUploadException = exc;
                    this.mLock.notify();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
