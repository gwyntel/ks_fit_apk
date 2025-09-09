package com.kingsmith.aliiot.proto;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public final class Test {

    /* renamed from: com.kingsmith.aliiot.proto.Test$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class LoginRequest extends GeneratedMessageLite<LoginRequest, Builder> implements LoginRequestOrBuilder {
        private static final LoginRequest DEFAULT_INSTANCE;
        private static volatile Parser<LoginRequest> PARSER = null;
        public static final int PWD_FIELD_NUMBER = 2;
        public static final int USERNAME_FIELD_NUMBER = 1;
        private String username_ = "";
        private String pwd_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<LoginRequest, Builder> implements LoginRequestOrBuilder {
            public Builder clearPwd() {
                copyOnWrite();
                ((LoginRequest) this.instance).clearPwd();
                return this;
            }

            public Builder clearUsername() {
                copyOnWrite();
                ((LoginRequest) this.instance).clearUsername();
                return this;
            }

            @Override // com.kingsmith.aliiot.proto.Test.LoginRequestOrBuilder
            public String getPwd() {
                return ((LoginRequest) this.instance).getPwd();
            }

            @Override // com.kingsmith.aliiot.proto.Test.LoginRequestOrBuilder
            public ByteString getPwdBytes() {
                return ((LoginRequest) this.instance).getPwdBytes();
            }

            @Override // com.kingsmith.aliiot.proto.Test.LoginRequestOrBuilder
            public String getUsername() {
                return ((LoginRequest) this.instance).getUsername();
            }

            @Override // com.kingsmith.aliiot.proto.Test.LoginRequestOrBuilder
            public ByteString getUsernameBytes() {
                return ((LoginRequest) this.instance).getUsernameBytes();
            }

            public Builder setPwd(String str) {
                copyOnWrite();
                ((LoginRequest) this.instance).setPwd(str);
                return this;
            }

            public Builder setPwdBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((LoginRequest) this.instance).setPwdBytes(byteString);
                return this;
            }

            public Builder setUsername(String str) {
                copyOnWrite();
                ((LoginRequest) this.instance).setUsername(str);
                return this;
            }

            public Builder setUsernameBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((LoginRequest) this.instance).setUsernameBytes(byteString);
                return this;
            }

            private Builder() {
                super(LoginRequest.DEFAULT_INSTANCE);
            }
        }

        static {
            LoginRequest loginRequest = new LoginRequest();
            DEFAULT_INSTANCE = loginRequest;
            GeneratedMessageLite.registerDefaultInstance(LoginRequest.class, loginRequest);
        }

        private LoginRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPwd() {
            this.pwd_ = getDefaultInstance().getPwd();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUsername() {
            this.username_ = getDefaultInstance().getUsername();
        }

        public static LoginRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static LoginRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (LoginRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LoginRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<LoginRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPwd(String str) {
            str.getClass();
            this.pwd_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPwdBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.pwd_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUsername(String str) {
            str.getClass();
            this.username_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUsernameBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.username_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new LoginRequest();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ", new Object[]{"username_", "pwd_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<LoginRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (LoginRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override // com.kingsmith.aliiot.proto.Test.LoginRequestOrBuilder
        public String getPwd() {
            return this.pwd_;
        }

        @Override // com.kingsmith.aliiot.proto.Test.LoginRequestOrBuilder
        public ByteString getPwdBytes() {
            return ByteString.copyFromUtf8(this.pwd_);
        }

        @Override // com.kingsmith.aliiot.proto.Test.LoginRequestOrBuilder
        public String getUsername() {
            return this.username_;
        }

        @Override // com.kingsmith.aliiot.proto.Test.LoginRequestOrBuilder
        public ByteString getUsernameBytes() {
            return ByteString.copyFromUtf8(this.username_);
        }

        public static Builder newBuilder(LoginRequest loginRequest) {
            return DEFAULT_INSTANCE.createBuilder(loginRequest);
        }

        public static LoginRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LoginRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LoginRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static LoginRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static LoginRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static LoginRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static LoginRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static LoginRequest parseFrom(InputStream inputStream) throws IOException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LoginRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LoginRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static LoginRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LoginRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface LoginRequestOrBuilder extends MessageLiteOrBuilder {
        String getPwd();

        ByteString getPwdBytes();

        String getUsername();

        ByteString getUsernameBytes();
    }

    public static final class LoginResponse extends GeneratedMessageLite<LoginResponse, Builder> implements LoginResponseOrBuilder {
        public static final int CODE_FIELD_NUMBER = 1;
        private static final LoginResponse DEFAULT_INSTANCE;
        public static final int MSG_FIELD_NUMBER = 2;
        private static volatile Parser<LoginResponse> PARSER;
        private int code_;
        private String msg_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<LoginResponse, Builder> implements LoginResponseOrBuilder {
            public Builder clearCode() {
                copyOnWrite();
                ((LoginResponse) this.instance).clearCode();
                return this;
            }

            public Builder clearMsg() {
                copyOnWrite();
                ((LoginResponse) this.instance).clearMsg();
                return this;
            }

            @Override // com.kingsmith.aliiot.proto.Test.LoginResponseOrBuilder
            public int getCode() {
                return ((LoginResponse) this.instance).getCode();
            }

            @Override // com.kingsmith.aliiot.proto.Test.LoginResponseOrBuilder
            public String getMsg() {
                return ((LoginResponse) this.instance).getMsg();
            }

            @Override // com.kingsmith.aliiot.proto.Test.LoginResponseOrBuilder
            public ByteString getMsgBytes() {
                return ((LoginResponse) this.instance).getMsgBytes();
            }

            public Builder setCode(int i2) {
                copyOnWrite();
                ((LoginResponse) this.instance).setCode(i2);
                return this;
            }

            public Builder setMsg(String str) {
                copyOnWrite();
                ((LoginResponse) this.instance).setMsg(str);
                return this;
            }

            public Builder setMsgBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((LoginResponse) this.instance).setMsgBytes(byteString);
                return this;
            }

            private Builder() {
                super(LoginResponse.DEFAULT_INSTANCE);
            }
        }

        static {
            LoginResponse loginResponse = new LoginResponse();
            DEFAULT_INSTANCE = loginResponse;
            GeneratedMessageLite.registerDefaultInstance(LoginResponse.class, loginResponse);
        }

        private LoginResponse() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCode() {
            this.code_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMsg() {
            this.msg_ = getDefaultInstance().getMsg();
        }

        public static LoginResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static LoginResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (LoginResponse) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LoginResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<LoginResponse> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCode(int i2) {
            this.code_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMsg(String str) {
            str.getClass();
            this.msg_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMsgBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.msg_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new LoginResponse();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"code_", "msg_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<LoginResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (LoginResponse.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override // com.kingsmith.aliiot.proto.Test.LoginResponseOrBuilder
        public int getCode() {
            return this.code_;
        }

        @Override // com.kingsmith.aliiot.proto.Test.LoginResponseOrBuilder
        public String getMsg() {
            return this.msg_;
        }

        @Override // com.kingsmith.aliiot.proto.Test.LoginResponseOrBuilder
        public ByteString getMsgBytes() {
            return ByteString.copyFromUtf8(this.msg_);
        }

        public static Builder newBuilder(LoginResponse loginResponse) {
            return DEFAULT_INSTANCE.createBuilder(loginResponse);
        }

        public static LoginResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LoginResponse) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LoginResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static LoginResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static LoginResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static LoginResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static LoginResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static LoginResponse parseFrom(InputStream inputStream) throws IOException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static LoginResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static LoginResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static LoginResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (LoginResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface LoginResponseOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        String getMsg();

        ByteString getMsgBytes();
    }

    private Test() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
