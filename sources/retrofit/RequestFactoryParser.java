package retrofit;

import anet.channel.request.Request;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit.RequestBuilderAction;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.HTTP;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.http.Url;

/* loaded from: classes5.dex */
final class RequestFactoryParser {
    private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    private static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);
    private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
    private MediaType contentType;
    private boolean hasBody;
    private Headers headers;
    private String httpMethod;
    private boolean isFormEncoded;
    private boolean isMultipart;
    private final Method method;
    private String relativeUrl;
    private Set<String> relativeUrlParamNames;
    private RequestBuilderAction[] requestBuilderActions;

    private RequestFactoryParser(Method method) {
        this.method = method;
    }

    static RequestFactory a(Method method, Type type, Retrofit retrofit3) {
        RequestFactoryParser requestFactoryParser = new RequestFactoryParser(method);
        requestFactoryParser.parseMethodAnnotations(type);
        requestFactoryParser.parseParameters(retrofit3);
        return requestFactoryParser.toRequestFactory(retrofit3.baseUrl());
    }

    static Set b(String str) {
        Matcher matcher = PARAM_URL_REGEX.matcher(str);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        while (matcher.find()) {
            linkedHashSet.add(matcher.group(1));
        }
        return linkedHashSet;
    }

    private RuntimeException parameterError(Throwable th, int i2, String str, Object... objArr) {
        return Utils.e(th, this.method, str + " (parameter #" + (i2 + 1) + ")", objArr);
    }

    private Headers parseHeaders(String[] strArr) {
        Headers.Builder builder = new Headers.Builder();
        for (String str : strArr) {
            int iIndexOf = str.indexOf(58);
            if (iIndexOf == -1 || iIndexOf == 0 || iIndexOf == str.length() - 1) {
                throw Utils.f(this.method, "@Headers value must be in the form \"Name: Value\". Found: \"%s\"", str);
            }
            String strSubstring = str.substring(0, iIndexOf);
            String strTrim = str.substring(iIndexOf + 1).trim();
            if ("Content-Type".equalsIgnoreCase(strSubstring)) {
                this.contentType = MediaType.parse(strTrim);
            } else {
                builder.add(strSubstring, strTrim);
            }
        }
        return builder.build();
    }

    private void parseHttpMethodAndPath(String str, String str2, boolean z2) {
        String str3 = this.httpMethod;
        if (str3 != null) {
            throw Utils.f(this.method, "Only one HTTP method is allowed. Found: %s and %s.", str3, str);
        }
        this.httpMethod = str;
        this.hasBody = z2;
        if (str2.isEmpty()) {
            return;
        }
        int iIndexOf = str2.indexOf(63);
        if (iIndexOf != -1 && iIndexOf < str2.length() - 1) {
            String strSubstring = str2.substring(iIndexOf + 1);
            if (PARAM_URL_REGEX.matcher(strSubstring).find()) {
                throw Utils.f(this.method, "URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", strSubstring);
            }
        }
        this.relativeUrl = str2;
        this.relativeUrlParamNames = b(str2);
    }

    private void parseMethodAnnotations(Type type) {
        for (Annotation annotation : this.method.getAnnotations()) {
            if (annotation instanceof DELETE) {
                parseHttpMethodAndPath("DELETE", ((DELETE) annotation).value(), false);
            } else if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof HEAD) {
                parseHttpMethodAndPath(Request.Method.HEAD, ((HEAD) annotation).value(), false);
                if (!Void.class.equals(type)) {
                    throw Utils.f(this.method, "HEAD method must use Void as response type.", new Object[0]);
                }
            } else if (annotation instanceof PATCH) {
                parseHttpMethodAndPath("PATCH", ((PATCH) annotation).value(), true);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            } else if (annotation instanceof PUT) {
                parseHttpMethodAndPath(Request.Method.PUT, ((PUT) annotation).value(), true);
            } else if (annotation instanceof HTTP) {
                HTTP http = (HTTP) annotation;
                parseHttpMethodAndPath(http.method(), http.path(), http.hasBody());
            } else if (annotation instanceof retrofit.http.Headers) {
                String[] strArrValue = ((retrofit.http.Headers) annotation).value();
                if (strArrValue.length == 0) {
                    throw Utils.f(this.method, "@Headers annotation is empty.", new Object[0]);
                }
                this.headers = parseHeaders(strArrValue);
            } else if (annotation instanceof Multipart) {
                if (this.isFormEncoded) {
                    throw Utils.f(this.method, "Only one encoding annotation is allowed.", new Object[0]);
                }
                this.isMultipart = true;
            } else if (!(annotation instanceof FormUrlEncoded)) {
                continue;
            } else {
                if (this.isMultipart) {
                    throw Utils.f(this.method, "Only one encoding annotation is allowed.", new Object[0]);
                }
                this.isFormEncoded = true;
            }
        }
        if (this.httpMethod == null) {
            throw Utils.f(this.method, "HTTP method annotation is required (e.g., @GET, @POST, etc.).", new Object[0]);
        }
        if (this.hasBody) {
            return;
        }
        if (this.isMultipart) {
            throw Utils.f(this.method, "Multipart can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
        }
        if (this.isFormEncoded) {
            throw Utils.f(this.method, "FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
        }
    }

    private void parseParameters(Retrofit retrofit3) {
        Type[] typeArr;
        int i2;
        RequestBuilderAction body;
        RequestBuilderAction fieldMap;
        RequestBuilderAction header;
        Type[] genericParameterTypes = this.method.getGenericParameterTypes();
        Annotation[][] parameterAnnotations = this.method.getParameterAnnotations();
        int length = parameterAnnotations.length;
        RequestBuilderAction[] requestBuilderActionArr = new RequestBuilderAction[length];
        int i3 = 0;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        while (i3 < length) {
            Type type = genericParameterTypes[i3];
            Annotation[] annotationArr = parameterAnnotations[i3];
            if (annotationArr != null) {
                int length2 = annotationArr.length;
                typeArr = genericParameterTypes;
                int i4 = 0;
                while (i4 < length2) {
                    int i5 = length2;
                    Annotation annotation = annotationArr[i4];
                    Annotation[][] annotationArr2 = parameterAnnotations;
                    int i6 = length;
                    if (annotation instanceof Url) {
                        if (z2) {
                            throw parameterError(i3, "Multiple @Url method annotations found.", new Object[0]);
                        }
                        if (z6) {
                            throw parameterError(i3, "@Path parameters may not be used with @Url.", new Object[0]);
                        }
                        if (z7) {
                            throw parameterError(i3, "A @Url parameter must not come after a @Query", new Object[0]);
                        }
                        if (type != String.class) {
                            throw parameterError(i3, "@Url must be String type.", new Object[0]);
                        }
                        if (this.relativeUrl != null) {
                            throw parameterError(i3, "@Url cannot be used with @%s URL", this.httpMethod);
                        }
                        body = new RequestBuilderAction.Url();
                        i2 = 0;
                        z2 = true;
                    } else if (annotation instanceof Path) {
                        if (z7) {
                            throw parameterError(i3, "A @Path parameter must not come after a @Query.", new Object[0]);
                        }
                        if (z2) {
                            throw parameterError(i3, "@Path parameters may not be used with @Url.", new Object[0]);
                        }
                        if (this.relativeUrl == null) {
                            throw parameterError(i3, "@Path can only be used with relative url on @%s", this.httpMethod);
                        }
                        Path path = (Path) annotation;
                        String strValue = path.value();
                        validatePathName(i3, strValue);
                        body = new RequestBuilderAction.Path(strValue, path.encoded());
                        i2 = 0;
                        z6 = true;
                    } else if (annotation instanceof Query) {
                        Query query = (Query) annotation;
                        body = new RequestBuilderAction.Query(query.value(), query.encoded());
                        i2 = 0;
                        z7 = true;
                    } else {
                        if (annotation instanceof QueryMap) {
                            if (!Map.class.isAssignableFrom(Utils.getRawType(type))) {
                                throw parameterError(i3, "@QueryMap parameter type must be Map.", new Object[0]);
                            }
                            header = new RequestBuilderAction.QueryMap(((QueryMap) annotation).encoded());
                        } else if (annotation instanceof Header) {
                            header = new RequestBuilderAction.Header(((Header) annotation).value());
                        } else {
                            if (annotation instanceof Field) {
                                if (!this.isFormEncoded) {
                                    throw parameterError(i3, "@Field parameters can only be used with form encoding.", new Object[0]);
                                }
                                Field field = (Field) annotation;
                                fieldMap = new RequestBuilderAction.Field(field.value(), field.encoded());
                            } else if (!(annotation instanceof FieldMap)) {
                                if (annotation instanceof Part) {
                                    if (!this.isMultipart) {
                                        throw parameterError(i3, "@Part parameters can only be used with multipart encoding.", new Object[0]);
                                    }
                                    Part part = (Part) annotation;
                                    try {
                                        body = new RequestBuilderAction.Part(Headers.of("Content-Disposition", "form-data; name=\"" + part.value() + "\"", "Content-Transfer-Encoding", part.encoding()), retrofit3.requestConverter(type, annotationArr));
                                    } catch (RuntimeException e2) {
                                        throw parameterError(e2, i3, "Unable to create @Part converter for %s", type);
                                    }
                                } else if (annotation instanceof PartMap) {
                                    if (!this.isMultipart) {
                                        throw parameterError(i3, "@PartMap parameters can only be used with multipart encoding.", new Object[0]);
                                    }
                                    if (!Map.class.isAssignableFrom(Utils.getRawType(type))) {
                                        throw parameterError(i3, "@PartMap parameter type must be Map.", new Object[0]);
                                    }
                                    body = new RequestBuilderAction.PartMap(retrofit3, ((PartMap) annotation).encoding(), annotationArr);
                                } else if (!(annotation instanceof Body)) {
                                    i2 = 0;
                                    body = null;
                                } else {
                                    if (this.isFormEncoded || this.isMultipart) {
                                        throw parameterError(i3, "@Body parameters cannot be used with form or multi-part encoding.", new Object[0]);
                                    }
                                    if (z3) {
                                        throw parameterError(i3, "Multiple @Body method annotations found.", new Object[0]);
                                    }
                                    try {
                                        body = new RequestBuilderAction.Body(retrofit3.requestConverter(type, annotationArr));
                                        i2 = 0;
                                        z3 = true;
                                    } catch (RuntimeException e3) {
                                        throw parameterError(e3, i3, "Unable to create @Body converter for %s", type);
                                    }
                                }
                                i2 = 0;
                                z5 = true;
                            } else {
                                if (!this.isFormEncoded) {
                                    throw parameterError(i3, "@FieldMap parameters can only be used with form encoding.", new Object[0]);
                                }
                                if (!Map.class.isAssignableFrom(Utils.getRawType(type))) {
                                    throw parameterError(i3, "@FieldMap parameter type must be Map.", new Object[0]);
                                }
                                fieldMap = new RequestBuilderAction.FieldMap(((FieldMap) annotation).encoded());
                            }
                            body = fieldMap;
                            i2 = 0;
                            z4 = true;
                        }
                        body = header;
                        i2 = 0;
                    }
                    if (body != null) {
                        if (requestBuilderActionArr[i3] != null) {
                            throw parameterError(i3, "Multiple Retrofit annotations found, only one allowed.", new Object[i2]);
                        }
                        requestBuilderActionArr[i3] = body;
                    }
                    i4++;
                    length2 = i5;
                    parameterAnnotations = annotationArr2;
                    length = i6;
                }
            } else {
                typeArr = genericParameterTypes;
            }
            Annotation[][] annotationArr3 = parameterAnnotations;
            int i7 = length;
            if (requestBuilderActionArr[i3] == null) {
                throw parameterError(i3, "No Retrofit annotation found.", new Object[0]);
            }
            i3++;
            genericParameterTypes = typeArr;
            parameterAnnotations = annotationArr3;
            length = i7;
        }
        if (this.relativeUrl == null && !z2) {
            throw Utils.f(this.method, "Missing either @%s URL or @Url parameter.", this.httpMethod);
        }
        boolean z8 = this.isFormEncoded;
        if (!z8 && !this.isMultipart && !this.hasBody && z3) {
            throw Utils.f(this.method, "Non-body HTTP method cannot contain @Body.", new Object[0]);
        }
        if (z8 && !z4) {
            throw Utils.f(this.method, "Form-encoded method must contain at least one @Field.", new Object[0]);
        }
        if (this.isMultipart && !z5) {
            throw Utils.f(this.method, "Multipart method must contain at least one @Part.", new Object[0]);
        }
        this.requestBuilderActions = requestBuilderActionArr;
    }

    private RequestFactory toRequestFactory(BaseUrl baseUrl) {
        return new RequestFactory(this.httpMethod, baseUrl, this.relativeUrl, this.headers, this.contentType, this.hasBody, this.isFormEncoded, this.isMultipart, this.requestBuilderActions);
    }

    private void validatePathName(int i2, String str) {
        if (!PARAM_NAME_REGEX.matcher(str).matches()) {
            throw parameterError(i2, "@Path parameter name must match %s. Found: %s", PARAM_URL_REGEX.pattern(), str);
        }
        if (!this.relativeUrlParamNames.contains(str)) {
            throw parameterError(i2, "URL \"%s\" does not contain \"{%s}\".", this.relativeUrl, str);
        }
    }

    private RuntimeException parameterError(int i2, String str, Object... objArr) {
        return Utils.f(this.method, str + " (parameter #" + (i2 + 1) + ")", objArr);
    }
}
