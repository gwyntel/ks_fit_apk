package com.aliyun.alink.linksdk.alcs.coap;

import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;

/* loaded from: classes2.dex */
public final class AlcsCoAPConstant {
    public static final int COAP_RECV_RESP_TIMEOUT = 1;
    public static final int COAP_REQUEST_SUCCESS = 0;
    public static final String COAP_SECURE_TCP_URI_SCHEME = "coaps+tcp";
    public static final String COAP_SECURE_URI_SCHEME = "coaps";
    public static final String COAP_TCP_URI_SCHEME = "coap+tcp";
    public static final String COAP_URI_SCHEME = "coap";
    public static final int DEFAULT_COAP_PORT = 5683;
    public static final int DEFAULT_COAP_SECURE_PORT = 5684;
    private static final String TAG = "AlcsCoAPConstant";
    public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    public static final int VERSION = 1;

    public enum Code {
        GET(1),
        POST(2),
        PUT(3),
        DELETE(4);

        public final int value;

        Code(int i2) {
            this.value = i2;
        }

        public static Code valueOf(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            int codeClass = AlcsCoAPConstant.getCodeClass(i2);
            int codeDetail = AlcsCoAPConstant.getCodeDetail(i2);
            if (codeClass > 0) {
                ALog.e(AlcsCoAPConstant.TAG, "Not a CoAP request code: %s", AlcsCoAPConstant.formatCode(codeClass, codeDetail));
                return null;
            }
            if (codeDetail == 1) {
                return GET;
            }
            if (codeDetail == 2) {
                return POST;
            }
            if (codeDetail == 3) {
                return PUT;
            }
            if (codeDetail == 4) {
                return DELETE;
            }
            throw new MessageFormatException(String.format("Unknown CoAP request code: %s", AlcsCoAPConstant.formatCode(codeClass, codeDetail)));
        }
    }

    public enum CodeClass {
        REQUEST(0),
        SUCCESS_RESPONSE(2),
        ERROR_RESPONSE(4),
        SERVER_ERROR_RESPONSE(5),
        SIGNAL(7);

        public final int value;

        CodeClass(int i2) {
            this.value = i2;
        }

        public static CodeClass valueOf(int i2) {
            if (i2 == 0) {
                return REQUEST;
            }
            if (i2 == 2) {
                return SUCCESS_RESPONSE;
            }
            if (i2 == 7) {
                return SIGNAL;
            }
            if (i2 == 4) {
                return ERROR_RESPONSE;
            }
            if (i2 == 5) {
                return SERVER_ERROR_RESPONSE;
            }
            throw new MessageFormatException(String.format("Unknown CoAP class code: %d", Integer.valueOf(i2)));
        }
    }

    public static final class MessageFormat {
        public static final int CODE_BITS = 8;
        public static final int EMPTY_CODE = 0;
        public static final int LENGTH_NIBBLE_BITS = 4;
        public static final int MESSAGE_ID_BITS = 16;
        public static final int OPTION_DELTA_BITS = 4;
        public static final int OPTION_LENGTH_BITS = 4;
        public static final byte PAYLOAD_MARKER = -1;
        public static final int REQUEST_CODE_LOWER_BOUND = 1;
        public static final int REQUEST_CODE_UPPER_BOUND = 31;
        public static final int RESPONSE_CODE_LOWER_BOUND = 64;
        public static final int RESPONSE_CODE_UPPER_BOUND = 191;
        public static final int TOKEN_LENGTH_BITS = 4;
        public static final int TYPE_BITS = 2;
        public static final int VERSION = 1;
        public static final int VERSION_BITS = 2;

        private MessageFormat() {
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field '_UNKNOWN_SUCCESS_CODE' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class ResponseCode {
        private static final /* synthetic */ ResponseCode[] $VALUES;
        public static final ResponseCode BAD_GATEWAY;
        public static final ResponseCode BAD_OPTION;
        public static final ResponseCode BAD_REQUEST;
        public static final ResponseCode CHANGED;
        public static final ResponseCode CONTENT;
        public static final ResponseCode CONTINUE;
        public static final ResponseCode CREATED;
        public static final ResponseCode DELETED;
        public static final ResponseCode FORBIDDEN;
        public static final ResponseCode GATEWAY_TIMEOUT;
        public static final ResponseCode INTERNAL_SERVER_ERROR;
        public static final ResponseCode METHOD_NOT_ALLOWED;
        public static final ResponseCode NOT_ACCEPTABLE;
        public static final ResponseCode NOT_FOUND;
        public static final ResponseCode NOT_IMPLEMENTED;
        public static final ResponseCode PRECONDITION_FAILED;
        public static final ResponseCode PROXY_NOT_SUPPORTED;
        public static final ResponseCode REQUEST_ENTITY_INCOMPLETE;
        public static final ResponseCode REQUEST_ENTITY_TOO_LARGE;
        public static final ResponseCode SERVICE_UNAVAILABLE;
        public static final ResponseCode UNAUTHORIZED;
        public static final ResponseCode UNSUPPORTED_CONTENT_FORMAT;
        public static final ResponseCode VALID;
        public static final ResponseCode _UNKNOWN_SUCCESS_CODE;
        public final int codeClass;
        public final int codeDetail;
        public final int value;

        static {
            CodeClass codeClass = CodeClass.SUCCESS_RESPONSE;
            ResponseCode responseCode = new ResponseCode("_UNKNOWN_SUCCESS_CODE", 0, codeClass, 0);
            _UNKNOWN_SUCCESS_CODE = responseCode;
            ResponseCode responseCode2 = new ResponseCode(DebugCoroutineInfoImplKt.CREATED, 1, codeClass, 1);
            CREATED = responseCode2;
            ResponseCode responseCode3 = new ResponseCode("DELETED", 2, codeClass, 2);
            DELETED = responseCode3;
            ResponseCode responseCode4 = new ResponseCode("VALID", 3, codeClass, 3);
            VALID = responseCode4;
            ResponseCode responseCode5 = new ResponseCode("CHANGED", 4, codeClass, 4);
            CHANGED = responseCode5;
            ResponseCode responseCode6 = new ResponseCode("CONTENT", 5, codeClass, 5);
            CONTENT = responseCode6;
            ResponseCode responseCode7 = new ResponseCode("CONTINUE", 6, codeClass, 31);
            CONTINUE = responseCode7;
            CodeClass codeClass2 = CodeClass.ERROR_RESPONSE;
            ResponseCode responseCode8 = new ResponseCode("BAD_REQUEST", 7, codeClass2, 0);
            BAD_REQUEST = responseCode8;
            ResponseCode responseCode9 = new ResponseCode("UNAUTHORIZED", 8, codeClass2, 1);
            UNAUTHORIZED = responseCode9;
            ResponseCode responseCode10 = new ResponseCode("BAD_OPTION", 9, codeClass2, 2);
            BAD_OPTION = responseCode10;
            ResponseCode responseCode11 = new ResponseCode("FORBIDDEN", 10, codeClass2, 3);
            FORBIDDEN = responseCode11;
            ResponseCode responseCode12 = new ResponseCode("NOT_FOUND", 11, codeClass2, 4);
            NOT_FOUND = responseCode12;
            ResponseCode responseCode13 = new ResponseCode("METHOD_NOT_ALLOWED", 12, codeClass2, 5);
            METHOD_NOT_ALLOWED = responseCode13;
            ResponseCode responseCode14 = new ResponseCode("NOT_ACCEPTABLE", 13, codeClass2, 6);
            NOT_ACCEPTABLE = responseCode14;
            ResponseCode responseCode15 = new ResponseCode("REQUEST_ENTITY_INCOMPLETE", 14, codeClass2, 8);
            REQUEST_ENTITY_INCOMPLETE = responseCode15;
            ResponseCode responseCode16 = new ResponseCode("PRECONDITION_FAILED", 15, codeClass2, 12);
            PRECONDITION_FAILED = responseCode16;
            ResponseCode responseCode17 = new ResponseCode("REQUEST_ENTITY_TOO_LARGE", 16, codeClass2, 13);
            REQUEST_ENTITY_TOO_LARGE = responseCode17;
            ResponseCode responseCode18 = new ResponseCode("UNSUPPORTED_CONTENT_FORMAT", 17, codeClass2, 15);
            UNSUPPORTED_CONTENT_FORMAT = responseCode18;
            CodeClass codeClass3 = CodeClass.SERVER_ERROR_RESPONSE;
            ResponseCode responseCode19 = new ResponseCode("INTERNAL_SERVER_ERROR", 18, codeClass3, 0);
            INTERNAL_SERVER_ERROR = responseCode19;
            ResponseCode responseCode20 = new ResponseCode("NOT_IMPLEMENTED", 19, codeClass3, 1);
            NOT_IMPLEMENTED = responseCode20;
            ResponseCode responseCode21 = new ResponseCode("BAD_GATEWAY", 20, codeClass3, 2);
            BAD_GATEWAY = responseCode21;
            ResponseCode responseCode22 = new ResponseCode("SERVICE_UNAVAILABLE", 21, codeClass3, 3);
            SERVICE_UNAVAILABLE = responseCode22;
            ResponseCode responseCode23 = new ResponseCode("GATEWAY_TIMEOUT", 22, codeClass3, 4);
            GATEWAY_TIMEOUT = responseCode23;
            ResponseCode responseCode24 = new ResponseCode("PROXY_NOT_SUPPORTED", 23, codeClass3, 5);
            PROXY_NOT_SUPPORTED = responseCode24;
            $VALUES = new ResponseCode[]{responseCode, responseCode2, responseCode3, responseCode4, responseCode5, responseCode6, responseCode7, responseCode8, responseCode9, responseCode10, responseCode11, responseCode12, responseCode13, responseCode14, responseCode15, responseCode16, responseCode17, responseCode18, responseCode19, responseCode20, responseCode21, responseCode22, responseCode23, responseCode24};
        }

        private ResponseCode(String str, int i2, CodeClass codeClass, int i3) {
            int i4 = codeClass.value;
            this.codeClass = i4;
            this.codeDetail = i3;
            this.value = (i4 << 5) | i3;
        }

        public static boolean isClientError(ResponseCode responseCode) {
            if (responseCode != null) {
                return responseCode.codeClass == CodeClass.ERROR_RESPONSE.value;
            }
            throw new NullPointerException("ResponseCode must not be null!");
        }

        public static boolean isServerError(ResponseCode responseCode) {
            if (responseCode != null) {
                return responseCode.codeClass == CodeClass.SERVER_ERROR_RESPONSE.value;
            }
            throw new NullPointerException("ResponseCode must not be null!");
        }

        public static boolean isSuccess(ResponseCode responseCode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (responseCode != null) {
                return responseCode.codeClass == CodeClass.SUCCESS_RESPONSE.value;
            }
            ALog.e(AlcsCoAPConstant.TAG, "ResponseCode must not be null!");
            return false;
        }

        public static ResponseCode valueOf(String str) {
            return (ResponseCode) Enum.valueOf(ResponseCode.class, str);
        }

        private static ResponseCode valueOfClientErrorCode(int i2) {
            if (i2 == 8) {
                return REQUEST_ENTITY_INCOMPLETE;
            }
            if (i2 == 15) {
                return UNSUPPORTED_CONTENT_FORMAT;
            }
            if (i2 == 12) {
                return PRECONDITION_FAILED;
            }
            if (i2 == 13) {
                return REQUEST_ENTITY_TOO_LARGE;
            }
            switch (i2) {
                case 0:
                    return BAD_REQUEST;
                case 1:
                    return UNAUTHORIZED;
                case 2:
                    return BAD_OPTION;
                case 3:
                    return FORBIDDEN;
                case 4:
                    return NOT_FOUND;
                case 5:
                    return METHOD_NOT_ALLOWED;
                case 6:
                    return NOT_ACCEPTABLE;
                default:
                    return BAD_REQUEST;
            }
        }

        private static ResponseCode valueOfServerErrorCode(int i2) {
            return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? INTERNAL_SERVER_ERROR : PROXY_NOT_SUPPORTED : GATEWAY_TIMEOUT : SERVICE_UNAVAILABLE : BAD_GATEWAY : NOT_IMPLEMENTED : INTERNAL_SERVER_ERROR;
        }

        private static ResponseCode valueOfSuccessCode(int i2) {
            return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 31 ? _UNKNOWN_SUCCESS_CODE : CONTINUE : CONTENT : CHANGED : VALID : DELETED : CREATED;
        }

        public static ResponseCode[] values() {
            return (ResponseCode[]) $VALUES.clone();
        }

        @Override // java.lang.Enum
        public String toString() {
            return AlcsCoAPConstant.formatCode(this.codeClass, this.codeDetail);
        }

        public static ResponseCode valueOf(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            int codeClass = AlcsCoAPConstant.getCodeClass(i2);
            int codeDetail = AlcsCoAPConstant.getCodeDetail(i2);
            if (codeClass == 2) {
                return valueOfSuccessCode(codeDetail);
            }
            if (codeClass == 4) {
                return valueOfClientErrorCode(codeDetail);
            }
            if (codeClass == 5) {
                return valueOfServerErrorCode(codeDetail);
            }
            ALog.e(AlcsCoAPConstant.TAG, "Not a CoAP response code:" + AlcsCoAPConstant.formatCode(codeClass, codeDetail));
            return valueOfServerErrorCode(codeDetail);
        }
    }

    public enum Type {
        CON(0),
        NON(1),
        ACK(2),
        RST(3);

        public final int value;

        Type(int i2) {
            this.value = i2;
        }

        public static Type valueOf(int i2) {
            if (i2 == 0) {
                return CON;
            }
            if (i2 == 1) {
                return NON;
            }
            if (i2 == 2) {
                return ACK;
            }
            if (i2 == 3) {
                return RST;
            }
            throw new IllegalArgumentException("Unknown CoAP type " + i2);
        }
    }

    private AlcsCoAPConstant() {
    }

    public static String formatCode(int i2) {
        return formatCode(getCodeClass(i2), getCodeDetail(i2));
    }

    public static int getCodeClass(int i2) {
        return (i2 & 224) >> 5;
    }

    public static int getCodeDetail(int i2) {
        return i2 & 31;
    }

    public static int getDefaultPort(String str) {
        if ("coap".equalsIgnoreCase(str)) {
            return 5683;
        }
        if ("coaps".equalsIgnoreCase(str)) {
            return 5684;
        }
        if (COAP_TCP_URI_SCHEME.equalsIgnoreCase(str)) {
            return 5683;
        }
        return COAP_SECURE_TCP_URI_SCHEME.equalsIgnoreCase(str) ? 5684 : 0;
    }

    public static boolean isEmptyMessage(int i2) {
        return i2 == 0;
    }

    public static boolean isRequest(int i2) {
        return i2 >= 1 && i2 <= 31;
    }

    public static boolean isResponse(int i2) {
        return i2 >= 64 && i2 <= 191;
    }

    public static boolean isSecureScheme(String str) {
        return "coaps".equalsIgnoreCase(str) || COAP_SECURE_TCP_URI_SCHEME.equalsIgnoreCase(str);
    }

    public static boolean isSupportedScheme(String str) {
        return "coap".equalsIgnoreCase(str) || COAP_TCP_URI_SCHEME.equalsIgnoreCase(str) || "coaps".equalsIgnoreCase(str) || COAP_SECURE_TCP_URI_SCHEME.equalsIgnoreCase(str);
    }

    public static boolean isTcpScheme(String str) {
        return COAP_TCP_URI_SCHEME.equalsIgnoreCase(str) || COAP_SECURE_TCP_URI_SCHEME.equalsIgnoreCase(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatCode(int i2, int i3) {
        return String.format("%d.%02d", Integer.valueOf(i2), Integer.valueOf(i3));
    }
}
