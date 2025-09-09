package retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import retrofit.CallAdapter;

/* loaded from: classes5.dex */
final class ExecutorCallAdapterFactory implements CallAdapter.Factory {
    private final Executor callbackExecutor;

    static final class ExecutorCallback<T> implements Callback<T> {
        private final Executor callbackExecutor;
        private final Callback<T> delegate;

        ExecutorCallback(Executor executor, Callback callback) {
            this.callbackExecutor = executor;
            this.delegate = callback;
        }

        @Override // retrofit.Callback
        public void onFailure(final Throwable th) {
            this.callbackExecutor.execute(new Runnable() { // from class: retrofit.ExecutorCallAdapterFactory.ExecutorCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    ExecutorCallback.this.delegate.onFailure(th);
                }
            });
        }

        @Override // retrofit.Callback
        public void onResponse(final Response<T> response, final Retrofit retrofit3) {
            this.callbackExecutor.execute(new Runnable() { // from class: retrofit.ExecutorCallAdapterFactory.ExecutorCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    ExecutorCallback.this.delegate.onResponse(response, retrofit3);
                }
            });
        }
    }

    static final class ExecutorCallbackCall<T> implements Call<T> {
        private final Executor callbackExecutor;
        private final Call<T> delegate;

        ExecutorCallbackCall(Executor executor, Call call) {
            this.callbackExecutor = executor;
            this.delegate = call;
        }

        @Override // retrofit.Call
        public void cancel() {
            this.delegate.cancel();
        }

        @Override // retrofit.Call
        public void enqueue(Callback<T> callback) {
            this.delegate.enqueue(new ExecutorCallback(this.callbackExecutor, callback));
        }

        @Override // retrofit.Call
        public Response<T> execute() throws IOException {
            return this.delegate.execute();
        }

        @Override // retrofit.Call
        public Call<T> clone() {
            return new ExecutorCallbackCall(this.callbackExecutor, this.delegate.clone());
        }
    }

    ExecutorCallAdapterFactory(Executor executor) {
        this.callbackExecutor = executor;
    }

    @Override // retrofit.CallAdapter.Factory
    public CallAdapter<Call<?>> get(Type type, Annotation[] annotationArr, Retrofit retrofit3) {
        if (Utils.getRawType(type) != Call.class) {
            return null;
        }
        final Type typeC = Utils.c(type);
        return new CallAdapter<Call<?>>() { // from class: retrofit.ExecutorCallAdapterFactory.1
            @Override // retrofit.CallAdapter
            public Type responseType() {
                return typeC;
            }

            @Override // retrofit.CallAdapter
            public <R> Call<?> adapt(Call<R> call) {
                return new ExecutorCallbackCall(ExecutorCallAdapterFactory.this.callbackExecutor, call);
            }
        };
    }
}
