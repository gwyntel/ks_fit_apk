package io.flutter.plugins.googlesignin;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugins.googlesignin.Messages;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class Messages {

    public static class FlutterError extends RuntimeException {
        public final String code;
        public final Object details;

        public FlutterError(@NonNull String str, @Nullable String str2, @Nullable Object obj) {
            super(str2);
            this.code = str;
            this.details = obj;
        }
    }

    public interface GoogleSignInApi {

        /* renamed from: io.flutter.plugins.googlesignin.Messages$GoogleSignInApi$-CC, reason: invalid class name */
        public abstract /* synthetic */ class CC {
            public static MessageCodec a() {
                return GoogleSignInApiCodec.INSTANCE;
            }

            public static /* synthetic */ void b(GoogleSignInApi googleSignInApi, Object obj, BasicMessageChannel.Reply reply) {
                ArrayList<Object> arrayList = new ArrayList<>();
                try {
                    googleSignInApi.init((InitParams) ((ArrayList) obj).get(0));
                    arrayList.add(0, null);
                } catch (Throwable th) {
                    arrayList = Messages.wrapError(th);
                }
                reply.reply(arrayList);
            }

            public static /* synthetic */ void c(GoogleSignInApi googleSignInApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                googleSignInApi.signInSilently(new Result<UserData>() { // from class: io.flutter.plugins.googlesignin.Messages.GoogleSignInApi.1
                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void success(UserData userData) {
                        arrayList.add(0, userData);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void d(GoogleSignInApi googleSignInApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                googleSignInApi.signIn(new Result<UserData>() { // from class: io.flutter.plugins.googlesignin.Messages.GoogleSignInApi.2
                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void success(UserData userData) {
                        arrayList.add(0, userData);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void e(GoogleSignInApi googleSignInApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = (ArrayList) obj;
                googleSignInApi.getAccessToken((String) arrayList2.get(0), (Boolean) arrayList2.get(1), new Result<String>() { // from class: io.flutter.plugins.googlesignin.Messages.GoogleSignInApi.3
                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void success(String str) {
                        arrayList.add(0, str);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void f(GoogleSignInApi googleSignInApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                googleSignInApi.signOut(new Result<Void>() { // from class: io.flutter.plugins.googlesignin.Messages.GoogleSignInApi.4
                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void success(Void r3) {
                        arrayList.add(0, null);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void g(GoogleSignInApi googleSignInApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                googleSignInApi.disconnect(new Result<Void>() { // from class: io.flutter.plugins.googlesignin.Messages.GoogleSignInApi.5
                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void success(Void r3) {
                        arrayList.add(0, null);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void h(GoogleSignInApi googleSignInApi, Object obj, BasicMessageChannel.Reply reply) {
                ArrayList<Object> arrayList = new ArrayList<>();
                try {
                    arrayList.add(0, googleSignInApi.isSignedIn());
                } catch (Throwable th) {
                    arrayList = Messages.wrapError(th);
                }
                reply.reply(arrayList);
            }

            public static /* synthetic */ void i(GoogleSignInApi googleSignInApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                googleSignInApi.clearAuthCache((String) ((ArrayList) obj).get(0), new Result<Void>() { // from class: io.flutter.plugins.googlesignin.Messages.GoogleSignInApi.6
                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void success(Void r3) {
                        arrayList.add(0, null);
                        reply.reply(arrayList);
                    }
                });
            }

            public static /* synthetic */ void j(GoogleSignInApi googleSignInApi, Object obj, final BasicMessageChannel.Reply reply) {
                final ArrayList arrayList = new ArrayList();
                googleSignInApi.requestScopes((List) ((ArrayList) obj).get(0), new Result<Boolean>() { // from class: io.flutter.plugins.googlesignin.Messages.GoogleSignInApi.7
                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void error(Throwable th) {
                        reply.reply(Messages.wrapError(th));
                    }

                    @Override // io.flutter.plugins.googlesignin.Messages.Result
                    public void success(Boolean bool) {
                        arrayList.add(0, bool);
                        reply.reply(arrayList);
                    }
                });
            }

            public static void k(BinaryMessenger binaryMessenger, final GoogleSignInApi googleSignInApi) {
                BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.init", a());
                if (googleSignInApi != null) {
                    basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.j
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.b(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.signInSilently", a());
                if (googleSignInApi != null) {
                    basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.k
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.c(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel2.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.signIn", a());
                if (googleSignInApi != null) {
                    basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.l
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.d(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel3.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.getAccessToken", a());
                if (googleSignInApi != null) {
                    basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.m
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.e(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel4.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel5 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.signOut", a());
                if (googleSignInApi != null) {
                    basicMessageChannel5.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.n
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.f(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel5.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel6 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.disconnect", a());
                if (googleSignInApi != null) {
                    basicMessageChannel6.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.o
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.g(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel6.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel7 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.isSignedIn", a());
                if (googleSignInApi != null) {
                    basicMessageChannel7.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.p
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.h(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel7.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel8 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.clearAuthCache", a());
                if (googleSignInApi != null) {
                    basicMessageChannel8.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.q
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.i(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel8.setMessageHandler(null);
                }
                BasicMessageChannel basicMessageChannel9 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.GoogleSignInApi.requestScopes", a());
                if (googleSignInApi != null) {
                    basicMessageChannel9.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: io.flutter.plugins.googlesignin.s
                        @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                        public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                            Messages.GoogleSignInApi.CC.j(googleSignInApi, obj, reply);
                        }
                    });
                } else {
                    basicMessageChannel9.setMessageHandler(null);
                }
            }
        }

        void clearAuthCache(@NonNull String str, @NonNull Result<Void> result);

        void disconnect(@NonNull Result<Void> result);

        void getAccessToken(@NonNull String str, @NonNull Boolean bool, @NonNull Result<String> result);

        void init(@NonNull InitParams initParams);

        @NonNull
        Boolean isSignedIn();

        void requestScopes(@NonNull List<String> list, @NonNull Result<Boolean> result);

        void signIn(@NonNull Result<UserData> result);

        void signInSilently(@NonNull Result<UserData> result);

        void signOut(@NonNull Result<Void> result);
    }

    private static class GoogleSignInApiCodec extends StandardMessageCodec {
        public static final GoogleSignInApiCodec INSTANCE = new GoogleSignInApiCodec();

        private GoogleSignInApiCodec() {
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected Object readValueOfType(byte b2, @NonNull ByteBuffer byteBuffer) {
            return b2 != Byte.MIN_VALUE ? b2 != -127 ? super.readValueOfType(b2, byteBuffer) : UserData.fromList((ArrayList) readValue(byteBuffer)) : InitParams.fromList((ArrayList) readValue(byteBuffer));
        }

        @Override // io.flutter.plugin.common.StandardMessageCodec
        protected void writeValue(@NonNull ByteArrayOutputStream byteArrayOutputStream, Object obj) {
            if (obj instanceof InitParams) {
                byteArrayOutputStream.write(128);
                writeValue(byteArrayOutputStream, ((InitParams) obj).toList());
            } else if (!(obj instanceof UserData)) {
                super.writeValue(byteArrayOutputStream, obj);
            } else {
                byteArrayOutputStream.write(129);
                writeValue(byteArrayOutputStream, ((UserData) obj).toList());
            }
        }
    }

    public static final class InitParams {

        @Nullable
        private String clientId;

        @NonNull
        private Boolean forceCodeForRefreshToken;

        @Nullable
        private String hostedDomain;

        @NonNull
        private List<String> scopes;

        @Nullable
        private String serverClientId;

        @NonNull
        private SignInType signInType;

        public static final class Builder {

            @Nullable
            private String clientId;

            @Nullable
            private Boolean forceCodeForRefreshToken;

            @Nullable
            private String hostedDomain;

            @Nullable
            private List<String> scopes;

            @Nullable
            private String serverClientId;

            @Nullable
            private SignInType signInType;

            @NonNull
            public InitParams build() {
                InitParams initParams = new InitParams();
                initParams.setScopes(this.scopes);
                initParams.setSignInType(this.signInType);
                initParams.setHostedDomain(this.hostedDomain);
                initParams.setClientId(this.clientId);
                initParams.setServerClientId(this.serverClientId);
                initParams.setForceCodeForRefreshToken(this.forceCodeForRefreshToken);
                return initParams;
            }

            @NonNull
            public Builder setClientId(@Nullable String str) {
                this.clientId = str;
                return this;
            }

            @NonNull
            public Builder setForceCodeForRefreshToken(@NonNull Boolean bool) {
                this.forceCodeForRefreshToken = bool;
                return this;
            }

            @NonNull
            public Builder setHostedDomain(@Nullable String str) {
                this.hostedDomain = str;
                return this;
            }

            @NonNull
            public Builder setScopes(@NonNull List<String> list) {
                this.scopes = list;
                return this;
            }

            @NonNull
            public Builder setServerClientId(@Nullable String str) {
                this.serverClientId = str;
                return this;
            }

            @NonNull
            public Builder setSignInType(@NonNull SignInType signInType) {
                this.signInType = signInType;
                return this;
            }
        }

        InitParams() {
        }

        @NonNull
        static InitParams fromList(@NonNull ArrayList<Object> arrayList) {
            InitParams initParams = new InitParams();
            initParams.setScopes((List) arrayList.get(0));
            Object obj = arrayList.get(1);
            initParams.setSignInType(obj == null ? null : SignInType.values()[((Integer) obj).intValue()]);
            initParams.setHostedDomain((String) arrayList.get(2));
            initParams.setClientId((String) arrayList.get(3));
            initParams.setServerClientId((String) arrayList.get(4));
            initParams.setForceCodeForRefreshToken((Boolean) arrayList.get(5));
            return initParams;
        }

        @Nullable
        public String getClientId() {
            return this.clientId;
        }

        @NonNull
        public Boolean getForceCodeForRefreshToken() {
            return this.forceCodeForRefreshToken;
        }

        @Nullable
        public String getHostedDomain() {
            return this.hostedDomain;
        }

        @NonNull
        public List<String> getScopes() {
            return this.scopes;
        }

        @Nullable
        public String getServerClientId() {
            return this.serverClientId;
        }

        @NonNull
        public SignInType getSignInType() {
            return this.signInType;
        }

        public void setClientId(@Nullable String str) {
            this.clientId = str;
        }

        public void setForceCodeForRefreshToken(@NonNull Boolean bool) {
            if (bool == null) {
                throw new IllegalStateException("Nonnull field \"forceCodeForRefreshToken\" is null.");
            }
            this.forceCodeForRefreshToken = bool;
        }

        public void setHostedDomain(@Nullable String str) {
            this.hostedDomain = str;
        }

        public void setScopes(@NonNull List<String> list) {
            if (list == null) {
                throw new IllegalStateException("Nonnull field \"scopes\" is null.");
            }
            this.scopes = list;
        }

        public void setServerClientId(@Nullable String str) {
            this.serverClientId = str;
        }

        public void setSignInType(@NonNull SignInType signInType) {
            if (signInType == null) {
                throw new IllegalStateException("Nonnull field \"signInType\" is null.");
            }
            this.signInType = signInType;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(6);
            arrayList.add(this.scopes);
            SignInType signInType = this.signInType;
            arrayList.add(signInType == null ? null : Integer.valueOf(signInType.index));
            arrayList.add(this.hostedDomain);
            arrayList.add(this.clientId);
            arrayList.add(this.serverClientId);
            arrayList.add(this.forceCodeForRefreshToken);
            return arrayList;
        }
    }

    public interface Result<T> {
        void error(@NonNull Throwable th);

        void success(T t2);
    }

    public enum SignInType {
        STANDARD(0),
        GAMES(1);

        final int index;

        SignInType(int i2) {
            this.index = i2;
        }
    }

    public static final class UserData {

        @Nullable
        private String displayName;

        @NonNull
        private String email;

        @NonNull
        private String id;

        @Nullable
        private String idToken;

        @Nullable
        private String photoUrl;

        @Nullable
        private String serverAuthCode;

        public static final class Builder {

            @Nullable
            private String displayName;

            @Nullable
            private String email;

            @Nullable
            private String id;

            @Nullable
            private String idToken;

            @Nullable
            private String photoUrl;

            @Nullable
            private String serverAuthCode;

            @NonNull
            public UserData build() {
                UserData userData = new UserData();
                userData.setDisplayName(this.displayName);
                userData.setEmail(this.email);
                userData.setId(this.id);
                userData.setPhotoUrl(this.photoUrl);
                userData.setIdToken(this.idToken);
                userData.setServerAuthCode(this.serverAuthCode);
                return userData;
            }

            @NonNull
            public Builder setDisplayName(@Nullable String str) {
                this.displayName = str;
                return this;
            }

            @NonNull
            public Builder setEmail(@NonNull String str) {
                this.email = str;
                return this;
            }

            @NonNull
            public Builder setId(@NonNull String str) {
                this.id = str;
                return this;
            }

            @NonNull
            public Builder setIdToken(@Nullable String str) {
                this.idToken = str;
                return this;
            }

            @NonNull
            public Builder setPhotoUrl(@Nullable String str) {
                this.photoUrl = str;
                return this;
            }

            @NonNull
            public Builder setServerAuthCode(@Nullable String str) {
                this.serverAuthCode = str;
                return this;
            }
        }

        UserData() {
        }

        @NonNull
        static UserData fromList(@NonNull ArrayList<Object> arrayList) {
            UserData userData = new UserData();
            userData.setDisplayName((String) arrayList.get(0));
            userData.setEmail((String) arrayList.get(1));
            userData.setId((String) arrayList.get(2));
            userData.setPhotoUrl((String) arrayList.get(3));
            userData.setIdToken((String) arrayList.get(4));
            userData.setServerAuthCode((String) arrayList.get(5));
            return userData;
        }

        @Nullable
        public String getDisplayName() {
            return this.displayName;
        }

        @NonNull
        public String getEmail() {
            return this.email;
        }

        @NonNull
        public String getId() {
            return this.id;
        }

        @Nullable
        public String getIdToken() {
            return this.idToken;
        }

        @Nullable
        public String getPhotoUrl() {
            return this.photoUrl;
        }

        @Nullable
        public String getServerAuthCode() {
            return this.serverAuthCode;
        }

        public void setDisplayName(@Nullable String str) {
            this.displayName = str;
        }

        public void setEmail(@NonNull String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"email\" is null.");
            }
            this.email = str;
        }

        public void setId(@NonNull String str) {
            if (str == null) {
                throw new IllegalStateException("Nonnull field \"id\" is null.");
            }
            this.id = str;
        }

        public void setIdToken(@Nullable String str) {
            this.idToken = str;
        }

        public void setPhotoUrl(@Nullable String str) {
            this.photoUrl = str;
        }

        public void setServerAuthCode(@Nullable String str) {
            this.serverAuthCode = str;
        }

        @NonNull
        ArrayList<Object> toList() {
            ArrayList<Object> arrayList = new ArrayList<>(6);
            arrayList.add(this.displayName);
            arrayList.add(this.email);
            arrayList.add(this.id);
            arrayList.add(this.photoUrl);
            arrayList.add(this.idToken);
            arrayList.add(this.serverAuthCode);
            return arrayList;
        }
    }

    @NonNull
    protected static ArrayList<Object> wrapError(@NonNull Throwable th) {
        ArrayList<Object> arrayList = new ArrayList<>(3);
        if (th instanceof FlutterError) {
            FlutterError flutterError = (FlutterError) th;
            arrayList.add(flutterError.code);
            arrayList.add(flutterError.getMessage());
            arrayList.add(flutterError.details);
        } else {
            arrayList.add(th.toString());
            arrayList.add(th.getClass().getSimpleName());
            arrayList.add("Cause: " + th.getCause() + ", Stacktrace: " + Log.getStackTraceString(th));
        }
        return arrayList;
    }
}
