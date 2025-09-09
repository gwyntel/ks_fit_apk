package retrofit;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.squareup.okhttp.ResponseBody;
import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import okio.Buffer;
import okio.BufferedSource;

/* loaded from: classes5.dex */
final class Utils {
    private Utils() {
    }

    static Object a(Object obj, String str) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(str);
    }

    static void b(Closeable closeable) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }

    static Type c(Type type) {
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
        }
        Type singleParameterUpperBound = getSingleParameterUpperBound((ParameterizedType) type);
        if (getRawType(singleParameterUpperBound) != Response.class) {
            return singleParameterUpperBound;
        }
        throw new IllegalArgumentException("Call<T> cannot use Response as its generic parameter. Specify the response body type only (e.g., Call<TweetResponse>).");
    }

    static boolean d(Annotation[] annotationArr, Class cls) {
        for (Annotation annotation : annotationArr) {
            if (cls.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }

    static RuntimeException e(Throwable th, Method method, String str, Object... objArr) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(String.format(str, objArr) + "\n    for method " + method.getDeclaringClass().getSimpleName() + "." + method.getName());
        illegalArgumentException.initCause(th);
        return illegalArgumentException;
    }

    static RuntimeException f(Method method, String str, Object... objArr) {
        return e(null, method, str, objArr);
    }

    static ResponseBody g(ResponseBody responseBody) throws IOException {
        if (responseBody == null) {
            return null;
        }
        BufferedSource bufferedSourceSource = responseBody.source();
        Buffer buffer = new Buffer();
        buffer.writeAll(bufferedSourceSource);
        bufferedSourceSource.close();
        return ResponseBody.create(responseBody.contentType(), responseBody.contentLength(), buffer);
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return (Class) rawType;
            }
            throw new IllegalArgumentException();
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance(getRawType(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? TmpConstant.GROUP_ROLE_UNKNOWN : type.getClass().getName()));
    }

    public static Type getSingleParameterUpperBound(ParameterizedType parameterizedType) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length == 1) {
            Type type = actualTypeArguments[0];
            return type instanceof WildcardType ? ((WildcardType) type).getUpperBounds()[0] : type;
        }
        throw new IllegalArgumentException("Expected one type argument but got: " + Arrays.toString(actualTypeArguments));
    }

    static void h(Class cls) {
        if (!cls.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (cls.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    public static boolean hasUnresolvableType(Type type) {
        if (type instanceof Class) {
            return false;
        }
        if (type instanceof ParameterizedType) {
            for (Type type2 : ((ParameterizedType) type).getActualTypeArguments()) {
                if (hasUnresolvableType(type2)) {
                    return true;
                }
            }
            return false;
        }
        if (type instanceof GenericArrayType) {
            return hasUnresolvableType(((GenericArrayType) type).getGenericComponentType());
        }
        if ((type instanceof TypeVariable) || (type instanceof WildcardType)) {
            return true;
        }
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? TmpConstant.GROUP_ROLE_UNKNOWN : type.getClass().getName()));
    }
}
