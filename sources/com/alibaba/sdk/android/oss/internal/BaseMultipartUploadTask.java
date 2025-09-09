package com.alibaba.sdk.android.oss.internal;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.utils.BinaryUtil;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.MultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PartETag;
import com.alibaba.sdk.android.oss.model.UploadPartRequest;
import com.alibaba.sdk.android.oss.model.UploadPartResult;
import com.alibaba.sdk.android.oss.network.ExecutionContext;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class BaseMultipartUploadTask<Request extends MultipartUploadRequest, Result extends CompleteMultipartUploadResult> implements Callable<Result> {
    protected final int CPU_SIZE;
    protected final int KEEP_ALIVE_TIME;
    protected final int MAX_CORE_POOL_SIZE;
    protected final int MAX_IMUM_POOL_SIZE;
    protected final int MAX_QUEUE_SIZE;
    protected InternalRequestOperation mApiOperation;
    protected boolean mCheckCRC64;
    protected OSSCompletedCallback<Request, Result> mCompletedCallback;
    protected ExecutionContext mContext;
    protected long mFileLength;
    protected boolean mIsCancel;
    protected Object mLock;
    protected List<PartETag> mPartETags;
    protected int mPartExceptionCount;
    protected ThreadPoolExecutor mPoolExecutor;
    protected OSSProgressCallback<Request> mProgressCallback;
    protected Request mRequest;
    protected int mRunPartTaskCount;
    protected Exception mUploadException;
    protected File mUploadFile;
    protected String mUploadId;
    protected long mUploadedLength;

    public BaseMultipartUploadTask(InternalRequestOperation internalRequestOperation, Request request, OSSCompletedCallback<Request, Result> oSSCompletedCallback, ExecutionContext executionContext) {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors() * 2;
        this.CPU_SIZE = iAvailableProcessors;
        int i2 = iAvailableProcessors < 5 ? iAvailableProcessors : 5;
        this.MAX_CORE_POOL_SIZE = i2;
        this.MAX_IMUM_POOL_SIZE = iAvailableProcessors;
        this.KEEP_ALIVE_TIME = 3000;
        this.MAX_QUEUE_SIZE = 5000;
        this.mPoolExecutor = new ThreadPoolExecutor(i2, iAvailableProcessors, 3000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(5000), new ThreadFactory() { // from class: com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "oss-android-multipart-thread");
            }
        });
        this.mPartETags = new ArrayList();
        this.mLock = new Object();
        this.mUploadedLength = 0L;
        this.mCheckCRC64 = false;
        this.mApiOperation = internalRequestOperation;
        this.mRequest = request;
        this.mProgressCallback = request.getProgressCallback();
        this.mCompletedCallback = oSSCompletedCallback;
        this.mContext = executionContext;
        this.mCheckCRC64 = request.getCRC64() == OSSRequest.CRC64Config.YES;
    }

    protected abstract void abortThisUpload();

    protected void checkCancel() throws ClientException {
        if (this.mContext.getCancellationHandler().isCancelled()) {
            IOException iOException = new IOException("multipart cancel");
            throw new ClientException(iOException.getMessage(), iOException);
        }
    }

    protected void checkException() throws ServiceException, ClientException, IOException {
        if (this.mUploadException != null) {
            releasePool();
            Exception exc = this.mUploadException;
            if (exc instanceof IOException) {
                throw ((IOException) exc);
            }
            if (exc instanceof ServiceException) {
                throw ((ServiceException) exc);
            }
            if (!(exc instanceof ClientException)) {
                throw new ClientException(this.mUploadException.getMessage(), this.mUploadException);
            }
            throw ((ClientException) exc);
        }
    }

    protected void checkPartSize(int[] iArr) {
        long partSize = this.mRequest.getPartSize();
        long j2 = this.mFileLength;
        int i2 = (int) (j2 / partSize);
        if (j2 % partSize != 0) {
            i2++;
        }
        if (i2 == 1) {
            partSize = j2;
        } else if (i2 > 5000) {
            partSize = j2 / 5000;
            i2 = 5000;
        }
        iArr[0] = (int) partSize;
        iArr[1] = i2;
    }

    protected boolean checkWaitCondition(int i2) {
        return this.mPartETags.size() != i2;
    }

    protected CompleteMultipartUploadResult completeMultipartUploadResult() throws ServiceException, ClientException {
        CompleteMultipartUploadResult completeMultipartUploadResultSyncCompleteMultipartUpload;
        if (this.mPartETags.size() > 0) {
            Collections.sort(this.mPartETags, new Comparator<PartETag>() { // from class: com.alibaba.sdk.android.oss.internal.BaseMultipartUploadTask.2
                @Override // java.util.Comparator
                public int compare(PartETag partETag, PartETag partETag2) {
                    if (partETag.getPartNumber() < partETag2.getPartNumber()) {
                        return -1;
                    }
                    return partETag.getPartNumber() > partETag2.getPartNumber() ? 1 : 0;
                }
            });
            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(this.mRequest.getBucketName(), this.mRequest.getObjectKey(), this.mUploadId, this.mPartETags);
            completeMultipartUploadRequest.setMetadata(this.mRequest.getMetadata());
            if (this.mRequest.getCallbackParam() != null) {
                completeMultipartUploadRequest.setCallbackParam(this.mRequest.getCallbackParam());
            }
            if (this.mRequest.getCallbackVars() != null) {
                completeMultipartUploadRequest.setCallbackVars(this.mRequest.getCallbackVars());
            }
            completeMultipartUploadRequest.setCRC64(this.mRequest.getCRC64());
            completeMultipartUploadResultSyncCompleteMultipartUpload = this.mApiOperation.syncCompleteMultipartUpload(completeMultipartUploadRequest);
        } else {
            completeMultipartUploadResultSyncCompleteMultipartUpload = null;
        }
        this.mUploadedLength = 0L;
        return completeMultipartUploadResultSyncCompleteMultipartUpload;
    }

    protected abstract Result doMultipartUpload() throws ServiceException, ClientException, InterruptedException, IOException;

    protected abstract void initMultipartUploadId() throws ServiceException, ClientException, IOException;

    protected void notifyMultipartThread() {
        this.mLock.notify();
        this.mPartExceptionCount = 0;
    }

    protected void onProgressCallback(Request request, long j2, long j3) {
        OSSProgressCallback<Request> oSSProgressCallback = this.mProgressCallback;
        if (oSSProgressCallback != null) {
            oSSProgressCallback.onProgress(request, j2, j3);
        }
    }

    protected void preUploadPart(int i2, int i3, int i4) throws Exception {
    }

    protected abstract void processException(Exception exc);

    protected void releasePool() {
        ThreadPoolExecutor threadPoolExecutor = this.mPoolExecutor;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.getQueue().clear();
            this.mPoolExecutor.shutdown();
        }
    }

    protected void uploadPart(int i2, int i3, int i4) throws Throwable {
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                try {
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Exception e2) {
                e = e2;
            }
            if (this.mContext.getCancellationHandler().isCancelled()) {
                this.mPoolExecutor.getQueue().clear();
                return;
            }
            synchronized (this.mLock) {
                this.mRunPartTaskCount++;
            }
            preUploadPart(i2, i3, i4);
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(this.mUploadFile, "r");
            try {
                UploadPartRequest uploadPartRequest = new UploadPartRequest(this.mRequest.getBucketName(), this.mRequest.getObjectKey(), this.mUploadId, i2 + 1);
                long partSize = i2 * this.mRequest.getPartSize();
                byte[] bArr = new byte[i3];
                randomAccessFile2.seek(partSize);
                randomAccessFile2.readFully(bArr, 0, i3);
                uploadPartRequest.setPartContent(bArr);
                uploadPartRequest.setMd5Digest(BinaryUtil.calculateBase64Md5(bArr));
                uploadPartRequest.setCRC64(this.mRequest.getCRC64());
                UploadPartResult uploadPartResultSyncUploadPart = this.mApiOperation.syncUploadPart(uploadPartRequest);
                synchronized (this.mLock) {
                    try {
                        PartETag partETag = new PartETag(uploadPartRequest.getPartNumber(), uploadPartResultSyncUploadPart.getETag());
                        long j2 = i3;
                        partETag.setPartSize(j2);
                        if (this.mCheckCRC64) {
                            partETag.setCRC64(uploadPartResultSyncUploadPart.getClientCRC().longValue());
                        }
                        this.mPartETags.add(partETag);
                        this.mUploadedLength += j2;
                        uploadPartFinish(partETag);
                        if (!this.mContext.getCancellationHandler().isCancelled()) {
                            if (this.mPartETags.size() == i4 - this.mPartExceptionCount) {
                                notifyMultipartThread();
                            }
                            onProgressCallback(this.mRequest, this.mUploadedLength, this.mFileLength);
                        } else if (this.mPartETags.size() == this.mRunPartTaskCount - this.mPartExceptionCount) {
                            IOException iOException = new IOException("multipart cancel");
                            throw new ClientException(iOException.getMessage(), iOException);
                        }
                    } finally {
                    }
                }
                randomAccessFile2.close();
            } catch (Exception e3) {
                e = e3;
                randomAccessFile = randomAccessFile2;
                processException(e);
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (Throwable th2) {
                th = th2;
                randomAccessFile = randomAccessFile2;
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException e4) {
                        OSSLog.logThrowable2Local(e4);
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            OSSLog.logThrowable2Local(e5);
        }
    }

    protected void uploadPartFinish(PartETag partETag) throws Exception {
    }

    @Override // java.util.concurrent.Callable
    public Result call() throws Exception {
        try {
            initMultipartUploadId();
            Result result = (Result) doMultipartUpload();
            OSSCompletedCallback<Request, Result> oSSCompletedCallback = this.mCompletedCallback;
            if (oSSCompletedCallback != null) {
                oSSCompletedCallback.onSuccess(this.mRequest, result);
            }
            return result;
        } catch (ServiceException e2) {
            OSSCompletedCallback<Request, Result> oSSCompletedCallback2 = this.mCompletedCallback;
            if (oSSCompletedCallback2 != null) {
                oSSCompletedCallback2.onFailure(this.mRequest, null, e2);
            }
            throw e2;
        } catch (Exception e3) {
            ClientException clientException = new ClientException(e3.toString(), e3);
            OSSCompletedCallback<Request, Result> oSSCompletedCallback3 = this.mCompletedCallback;
            if (oSSCompletedCallback3 == null) {
                throw clientException;
            }
            oSSCompletedCallback3.onFailure(this.mRequest, clientException, null);
            throw clientException;
        }
    }
}
