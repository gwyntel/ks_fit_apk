package io.flutter.embedding.engine.systemchannels;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.facebook.GraphRequest;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.editing.TextEditingDelta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class TextInputChannel {
    private static final String TAG = "TextInputChannel";

    @NonNull
    public final MethodChannel channel;

    @NonNull
    @VisibleForTesting
    final MethodChannel.MethodCallHandler parsingMethodHandler;

    @Nullable
    private TextInputMethodHandler textInputMethodHandler;

    public static class Configuration {

        @Nullable
        public final String actionLabel;
        public final boolean autocorrect;

        @Nullable
        public final Autofill autofill;

        @Nullable
        public final String[] contentCommitMimeTypes;
        public final boolean enableDeltaModel;
        public final boolean enableIMEPersonalizedLearning;
        public final boolean enableSuggestions;

        @Nullable
        public final Configuration[] fields;

        @Nullable
        public final Integer inputAction;

        @NonNull
        public final InputType inputType;
        public final boolean obscureText;

        @NonNull
        public final TextCapitalization textCapitalization;

        public static class Autofill {
            public final TextEditState editState;
            public final String hintText;
            public final String[] hints;
            public final String uniqueIdentifier;

            public Autofill(@NonNull String str, @NonNull String[] strArr, @Nullable String str2, @NonNull TextEditState textEditState) {
                this.uniqueIdentifier = str;
                this.hints = strArr;
                this.hintText = str2;
                this.editState = textEditState;
            }

            @NonNull
            public static Autofill fromJson(@NonNull JSONObject jSONObject) throws JSONException, NoSuchFieldException {
                String string = jSONObject.getString("uniqueIdentifier");
                JSONArray jSONArray = jSONObject.getJSONArray("hints");
                String string2 = jSONObject.isNull("hintText") ? null : jSONObject.getString("hintText");
                JSONObject jSONObject2 = jSONObject.getJSONObject("editingValue");
                String[] strArr = new String[jSONArray.length()];
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    strArr[i2] = translateAutofillHint(jSONArray.getString(i2));
                }
                return new Autofill(string, strArr, string2, TextEditState.fromJson(jSONObject2));
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:7:0x002d  */
            @androidx.annotation.NonNull
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            private static java.lang.String translateAutofillHint(@androidx.annotation.NonNull java.lang.String r17) {
                /*
                    Method dump skipped, instructions count: 802
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: io.flutter.embedding.engine.systemchannels.TextInputChannel.Configuration.Autofill.translateAutofillHint(java.lang.String):java.lang.String");
            }
        }

        public Configuration(boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, @NonNull TextCapitalization textCapitalization, @NonNull InputType inputType, @Nullable Integer num, @Nullable String str, @Nullable Autofill autofill, @Nullable String[] strArr, @Nullable Configuration[] configurationArr) {
            this.obscureText = z2;
            this.autocorrect = z3;
            this.enableSuggestions = z4;
            this.enableIMEPersonalizedLearning = z5;
            this.enableDeltaModel = z6;
            this.textCapitalization = textCapitalization;
            this.inputType = inputType;
            this.inputAction = num;
            this.actionLabel = str;
            this.autofill = autofill;
            this.contentCommitMimeTypes = strArr;
            this.fields = configurationArr;
        }

        @NonNull
        public static Configuration fromJson(@NonNull JSONObject jSONObject) throws JSONException, NoSuchFieldException {
            Configuration[] configurationArr;
            String string = jSONObject.getString("inputAction");
            if (string == null) {
                throw new JSONException("Configuration JSON missing 'inputAction' property.");
            }
            if (jSONObject.isNull(GraphRequest.FIELDS_PARAM)) {
                configurationArr = null;
            } else {
                JSONArray jSONArray = jSONObject.getJSONArray(GraphRequest.FIELDS_PARAM);
                int length = jSONArray.length();
                Configuration[] configurationArr2 = new Configuration[length];
                for (int i2 = 0; i2 < length; i2++) {
                    configurationArr2[i2] = fromJson(jSONArray.getJSONObject(i2));
                }
                configurationArr = configurationArr2;
            }
            Integer numInputActionFromTextInputAction = inputActionFromTextInputAction(string);
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray2 = jSONObject.isNull("contentCommitMimeTypes") ? null : jSONObject.getJSONArray("contentCommitMimeTypes");
            if (jSONArray2 != null) {
                for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                    arrayList.add(jSONArray2.optString(i3));
                }
            }
            return new Configuration(jSONObject.optBoolean("obscureText"), jSONObject.optBoolean("autocorrect", true), jSONObject.optBoolean("enableSuggestions"), jSONObject.optBoolean("enableIMEPersonalizedLearning"), jSONObject.optBoolean("enableDeltaModel"), TextCapitalization.fromValue(jSONObject.getString("textCapitalization")), InputType.fromJson(jSONObject.getJSONObject(RemoteMessageConst.INPUT_TYPE)), numInputActionFromTextInputAction, jSONObject.isNull("actionLabel") ? null : jSONObject.getString("actionLabel"), jSONObject.isNull("autofill") ? null : Autofill.fromJson(jSONObject.getJSONObject("autofill")), (String[]) arrayList.toArray(new String[arrayList.size()]), configurationArr);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:4:0x001b  */
        @androidx.annotation.NonNull
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static java.lang.Integer inputActionFromTextInputAction(@androidx.annotation.NonNull java.lang.String r12) {
            /*
                r0 = 7
                r1 = 6
                r2 = 5
                r3 = 4
                r4 = 3
                r5 = 2
                r12.hashCode()
                r6 = 1
                java.lang.Integer r7 = java.lang.Integer.valueOf(r6)
                r8 = 0
                java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
                r10 = -1
                int r11 = r12.hashCode()
                switch(r11) {
                    case -810971940: goto L76;
                    case -737377923: goto L6d;
                    case -737089298: goto L62;
                    case -737080013: goto L57;
                    case -736940669: goto L4c;
                    case 469250275: goto L41;
                    case 1241689507: goto L36;
                    case 1539450297: goto L2b;
                    case 2110497650: goto L1e;
                    default: goto L1b;
                }
            L1b:
                r6 = r10
                goto L80
            L1e:
                java.lang.String r6 = "TextInputAction.previous"
                boolean r12 = r12.equals(r6)
                if (r12 != 0) goto L27
                goto L1b
            L27:
                r6 = 8
                goto L80
            L2b:
                java.lang.String r6 = "TextInputAction.newline"
                boolean r12 = r12.equals(r6)
                if (r12 != 0) goto L34
                goto L1b
            L34:
                r6 = r0
                goto L80
            L36:
                java.lang.String r6 = "TextInputAction.go"
                boolean r12 = r12.equals(r6)
                if (r12 != 0) goto L3f
                goto L1b
            L3f:
                r6 = r1
                goto L80
            L41:
                java.lang.String r6 = "TextInputAction.search"
                boolean r12 = r12.equals(r6)
                if (r12 != 0) goto L4a
                goto L1b
            L4a:
                r6 = r2
                goto L80
            L4c:
                java.lang.String r6 = "TextInputAction.send"
                boolean r12 = r12.equals(r6)
                if (r12 != 0) goto L55
                goto L1b
            L55:
                r6 = r3
                goto L80
            L57:
                java.lang.String r6 = "TextInputAction.none"
                boolean r12 = r12.equals(r6)
                if (r12 != 0) goto L60
                goto L1b
            L60:
                r6 = r4
                goto L80
            L62:
                java.lang.String r6 = "TextInputAction.next"
                boolean r12 = r12.equals(r6)
                if (r12 != 0) goto L6b
                goto L1b
            L6b:
                r6 = r5
                goto L80
            L6d:
                java.lang.String r8 = "TextInputAction.done"
                boolean r12 = r12.equals(r8)
                if (r12 != 0) goto L80
                goto L1b
            L76:
                java.lang.String r6 = "TextInputAction.unspecified"
                boolean r12 = r12.equals(r6)
                if (r12 != 0) goto L7f
                goto L1b
            L7f:
                r6 = r8
            L80:
                switch(r6) {
                    case 0: goto La4;
                    case 1: goto L9f;
                    case 2: goto L9a;
                    case 3: goto L99;
                    case 4: goto L94;
                    case 5: goto L8f;
                    case 6: goto L8a;
                    case 7: goto L89;
                    case 8: goto L84;
                    default: goto L83;
                }
            L83:
                return r9
            L84:
                java.lang.Integer r12 = java.lang.Integer.valueOf(r0)
                return r12
            L89:
                return r7
            L8a:
                java.lang.Integer r12 = java.lang.Integer.valueOf(r5)
                return r12
            L8f:
                java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
                return r12
            L94:
                java.lang.Integer r12 = java.lang.Integer.valueOf(r3)
                return r12
            L99:
                return r7
            L9a:
                java.lang.Integer r12 = java.lang.Integer.valueOf(r2)
                return r12
            L9f:
                java.lang.Integer r12 = java.lang.Integer.valueOf(r1)
                return r12
            La4:
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: io.flutter.embedding.engine.systemchannels.TextInputChannel.Configuration.inputActionFromTextInputAction(java.lang.String):java.lang.Integer");
        }
    }

    public static class InputType {
        public final boolean isDecimal;
        public final boolean isSigned;

        @NonNull
        public final TextInputType type;

        public InputType(@NonNull TextInputType textInputType, boolean z2, boolean z3) {
            this.type = textInputType;
            this.isSigned = z2;
            this.isDecimal = z3;
        }

        @NonNull
        public static InputType fromJson(@NonNull JSONObject jSONObject) throws JSONException, NoSuchFieldException {
            return new InputType(TextInputType.fromValue(jSONObject.getString("name")), jSONObject.optBoolean("signed", false), jSONObject.optBoolean("decimal", false));
        }
    }

    public enum TextCapitalization {
        CHARACTERS("TextCapitalization.characters"),
        WORDS("TextCapitalization.words"),
        SENTENCES("TextCapitalization.sentences"),
        NONE("TextCapitalization.none");


        @NonNull
        private final String encodedName;

        TextCapitalization(@NonNull String str) {
            this.encodedName = str;
        }

        static TextCapitalization fromValue(@NonNull String str) throws NoSuchFieldException {
            for (TextCapitalization textCapitalization : values()) {
                if (textCapitalization.encodedName.equals(str)) {
                    return textCapitalization;
                }
            }
            throw new NoSuchFieldException("No such TextCapitalization: " + str);
        }
    }

    public static class TextEditState {
        public final int composingEnd;
        public final int composingStart;
        public final int selectionEnd;
        public final int selectionStart;

        @NonNull
        public final String text;

        public TextEditState(@NonNull String str, int i2, int i3, int i4, int i5) throws IndexOutOfBoundsException {
            if (!(i2 == -1 && i3 == -1) && (i2 < 0 || i3 < 0)) {
                throw new IndexOutOfBoundsException("invalid selection: (" + String.valueOf(i2) + ", " + String.valueOf(i3) + ")");
            }
            if (!(i4 == -1 && i5 == -1) && (i4 < 0 || i4 > i5)) {
                throw new IndexOutOfBoundsException("invalid composing range: (" + String.valueOf(i4) + ", " + String.valueOf(i5) + ")");
            }
            if (i5 > str.length()) {
                throw new IndexOutOfBoundsException("invalid composing start: " + String.valueOf(i4));
            }
            if (i2 > str.length()) {
                throw new IndexOutOfBoundsException("invalid selection start: " + String.valueOf(i2));
            }
            if (i3 > str.length()) {
                throw new IndexOutOfBoundsException("invalid selection end: " + String.valueOf(i3));
            }
            this.text = str;
            this.selectionStart = i2;
            this.selectionEnd = i3;
            this.composingStart = i4;
            this.composingEnd = i5;
        }

        @NonNull
        public static TextEditState fromJson(@NonNull JSONObject jSONObject) throws JSONException {
            return new TextEditState(jSONObject.getString("text"), jSONObject.getInt("selectionBase"), jSONObject.getInt("selectionExtent"), jSONObject.getInt("composingBase"), jSONObject.getInt("composingExtent"));
        }

        public boolean hasComposing() {
            int i2 = this.composingStart;
            return i2 >= 0 && this.composingEnd > i2;
        }

        public boolean hasSelection() {
            return this.selectionStart >= 0;
        }
    }

    public interface TextInputMethodHandler {
        void clearClient();

        void finishAutofillContext(boolean z2);

        void hide();

        void requestAutofill();

        void sendAppPrivateCommand(@NonNull String str, @NonNull Bundle bundle);

        void setClient(int i2, @NonNull Configuration configuration);

        void setEditableSizeAndTransform(double d2, double d3, @NonNull double[] dArr);

        void setEditingState(@NonNull TextEditState textEditState);

        void setPlatformViewClient(int i2, boolean z2);

        void show();
    }

    public enum TextInputType {
        TEXT("TextInputType.text"),
        DATETIME("TextInputType.datetime"),
        NAME("TextInputType.name"),
        POSTAL_ADDRESS("TextInputType.address"),
        NUMBER("TextInputType.number"),
        PHONE("TextInputType.phone"),
        MULTILINE("TextInputType.multiline"),
        EMAIL_ADDRESS("TextInputType.emailAddress"),
        URL("TextInputType.url"),
        VISIBLE_PASSWORD("TextInputType.visiblePassword"),
        NONE("TextInputType.none");


        @NonNull
        private final String encodedName;

        TextInputType(@NonNull String str) {
            this.encodedName = str;
        }

        static TextInputType fromValue(@NonNull String str) throws NoSuchFieldException {
            for (TextInputType textInputType : values()) {
                if (textInputType.encodedName.equals(str)) {
                    return textInputType;
                }
            }
            throw new NoSuchFieldException("No such TextInputType: " + str);
        }
    }

    public TextInputChannel(@NonNull DartExecutor dartExecutor) {
        MethodChannel.MethodCallHandler methodCallHandler = new MethodChannel.MethodCallHandler() { // from class: io.flutter.embedding.engine.systemchannels.TextInputChannel.1
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) throws JSONException {
                Object obj;
                Bundle bundle;
                if (TextInputChannel.this.textInputMethodHandler == null) {
                }
                String str = methodCall.method;
                obj = methodCall.arguments;
                Log.v(TextInputChannel.TAG, "Received '" + str + "' message.");
                str.hashCode();
                switch (str) {
                    case "TextInput.setPlatformViewClient":
                        try {
                            JSONObject jSONObject = (JSONObject) obj;
                            TextInputChannel.this.textInputMethodHandler.setPlatformViewClient(jSONObject.getInt("platformViewId"), jSONObject.optBoolean("usesVirtualDisplay", false));
                            result.success(null);
                            break;
                        } catch (JSONException e2) {
                            result.error("error", e2.getMessage(), null);
                            return;
                        }
                    case "TextInput.setEditingState":
                        try {
                            TextInputChannel.this.textInputMethodHandler.setEditingState(TextEditState.fromJson((JSONObject) obj));
                            result.success(null);
                            break;
                        } catch (JSONException e3) {
                            result.error("error", e3.getMessage(), null);
                            return;
                        }
                    case "TextInput.setClient":
                        try {
                            JSONArray jSONArray = (JSONArray) obj;
                            TextInputChannel.this.textInputMethodHandler.setClient(jSONArray.getInt(0), Configuration.fromJson(jSONArray.getJSONObject(1)));
                            result.success(null);
                            break;
                        } catch (NoSuchFieldException | JSONException e4) {
                            result.error("error", e4.getMessage(), null);
                            return;
                        }
                    case "TextInput.hide":
                        TextInputChannel.this.textInputMethodHandler.hide();
                        result.success(null);
                        break;
                    case "TextInput.show":
                        TextInputChannel.this.textInputMethodHandler.show();
                        result.success(null);
                        break;
                    case "TextInput.sendAppPrivateCommand":
                        try {
                            JSONObject jSONObject2 = (JSONObject) obj;
                            String string = jSONObject2.getString("action");
                            String string2 = jSONObject2.getString("data");
                            if (string2 == null || string2.isEmpty()) {
                                bundle = null;
                            } else {
                                bundle = new Bundle();
                                bundle.putString("data", string2);
                            }
                            TextInputChannel.this.textInputMethodHandler.sendAppPrivateCommand(string, bundle);
                            result.success(null);
                            break;
                        } catch (JSONException e5) {
                            result.error("error", e5.getMessage(), null);
                            return;
                        }
                    case "TextInput.setEditableSizeAndTransform":
                        try {
                            JSONObject jSONObject3 = (JSONObject) obj;
                            double d2 = jSONObject3.getDouble(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
                            double d3 = jSONObject3.getDouble(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
                            JSONArray jSONArray2 = jSONObject3.getJSONArray("transform");
                            double[] dArr = new double[16];
                            for (int i2 = 0; i2 < 16; i2++) {
                                dArr[i2] = jSONArray2.getDouble(i2);
                            }
                            TextInputChannel.this.textInputMethodHandler.setEditableSizeAndTransform(d2, d3, dArr);
                            result.success(null);
                            break;
                        } catch (JSONException e6) {
                            result.error("error", e6.getMessage(), null);
                            return;
                        }
                    case "TextInput.finishAutofillContext":
                        TextInputChannel.this.textInputMethodHandler.finishAutofillContext(((Boolean) obj).booleanValue());
                        result.success(null);
                        break;
                    case "TextInput.clearClient":
                        TextInputChannel.this.textInputMethodHandler.clearClient();
                        result.success(null);
                        break;
                    case "TextInput.requestAutofill":
                        TextInputChannel.this.textInputMethodHandler.requestAutofill();
                        result.success(null);
                        break;
                    default:
                        result.notImplemented();
                        break;
                }
            }
        };
        this.parsingMethodHandler = methodCallHandler;
        MethodChannel methodChannel = new MethodChannel(dartExecutor, "flutter/textinput", JSONMethodCodec.INSTANCE);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(methodCallHandler);
    }

    private static HashMap<Object, Object> createEditingDeltaJSON(ArrayList<TextEditingDelta> arrayList) {
        HashMap<Object, Object> map = new HashMap<>();
        JSONArray jSONArray = new JSONArray();
        Iterator<TextEditingDelta> it = arrayList.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toJSON());
        }
        map.put("deltas", jSONArray);
        return map;
    }

    private static HashMap<Object, Object> createEditingStateJSON(String str, int i2, int i3, int i4, int i5) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("text", str);
        map.put("selectionBase", Integer.valueOf(i2));
        map.put("selectionExtent", Integer.valueOf(i3));
        map.put("composingBase", Integer.valueOf(i4));
        map.put("composingExtent", Integer.valueOf(i5));
        return map;
    }

    public void commitContent(int i2, Map<String, Object> map) {
        Log.v(TAG, "Sending 'commitContent' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.commitContent", map));
    }

    public void done(int i2) {
        Log.v(TAG, "Sending 'done' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.done"));
    }

    public void go(int i2) {
        Log.v(TAG, "Sending 'go' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.go"));
    }

    public void newline(int i2) {
        Log.v(TAG, "Sending 'newline' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.newline"));
    }

    public void next(int i2) {
        Log.v(TAG, "Sending 'next' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.next"));
    }

    public void performPrivateCommand(int i2, @NonNull String str, @NonNull Bundle bundle) {
        HashMap map = new HashMap();
        map.put("action", str);
        if (bundle != null) {
            HashMap map2 = new HashMap();
            for (String str2 : bundle.keySet()) {
                Object obj = bundle.get(str2);
                if (obj instanceof byte[]) {
                    map2.put(str2, bundle.getByteArray(str2));
                } else if (obj instanceof Byte) {
                    map2.put(str2, Byte.valueOf(bundle.getByte(str2)));
                } else if (obj instanceof char[]) {
                    map2.put(str2, bundle.getCharArray(str2));
                } else if (obj instanceof Character) {
                    map2.put(str2, Character.valueOf(bundle.getChar(str2)));
                } else if (obj instanceof CharSequence[]) {
                    map2.put(str2, bundle.getCharSequenceArray(str2));
                } else if (obj instanceof CharSequence) {
                    map2.put(str2, bundle.getCharSequence(str2));
                } else if (obj instanceof float[]) {
                    map2.put(str2, bundle.getFloatArray(str2));
                } else if (obj instanceof Float) {
                    map2.put(str2, Float.valueOf(bundle.getFloat(str2)));
                }
            }
            map.put("data", map2);
        }
        this.channel.invokeMethod("TextInputClient.performPrivateCommand", Arrays.asList(Integer.valueOf(i2), map));
    }

    public void previous(int i2) {
        Log.v(TAG, "Sending 'previous' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.previous"));
    }

    public void requestExistingInputState() {
        this.channel.invokeMethod("TextInputClient.requestExistingInputState", null);
    }

    public void search(int i2) {
        Log.v(TAG, "Sending 'search' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.search"));
    }

    public void send(int i2) {
        Log.v(TAG, "Sending 'send' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.send"));
    }

    public void setTextInputMethodHandler(@Nullable TextInputMethodHandler textInputMethodHandler) {
        this.textInputMethodHandler = textInputMethodHandler;
    }

    public void unspecifiedAction(int i2) {
        Log.v(TAG, "Sending 'unspecified' message.");
        this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(Integer.valueOf(i2), "TextInputAction.unspecified"));
    }

    public void updateEditingState(int i2, @NonNull String str, int i3, int i4, int i5, int i6) {
        Log.v(TAG, "Sending message to update editing state: \nText: " + str + "\nSelection start: " + i3 + "\nSelection end: " + i4 + "\nComposing start: " + i5 + "\nComposing end: " + i6);
        this.channel.invokeMethod("TextInputClient.updateEditingState", Arrays.asList(Integer.valueOf(i2), createEditingStateJSON(str, i3, i4, i5, i6)));
    }

    public void updateEditingStateWithDeltas(int i2, @NonNull ArrayList<TextEditingDelta> arrayList) {
        Log.v(TAG, "Sending message to update editing state with deltas: \nNumber of deltas: " + arrayList.size());
        this.channel.invokeMethod("TextInputClient.updateEditingStateWithDeltas", Arrays.asList(Integer.valueOf(i2), createEditingDeltaJSON(arrayList)));
    }

    public void updateEditingStateWithTag(int i2, @NonNull HashMap<String, TextEditState> map) {
        Log.v(TAG, "Sending message to update editing state for " + String.valueOf(map.size()) + " field(s).");
        HashMap map2 = new HashMap();
        for (Map.Entry<String, TextEditState> entry : map.entrySet()) {
            TextEditState value = entry.getValue();
            map2.put(entry.getKey(), createEditingStateJSON(value.text, value.selectionStart, value.selectionEnd, -1, -1));
        }
        this.channel.invokeMethod("TextInputClient.updateEditingStateWithTag", Arrays.asList(Integer.valueOf(i2), map2));
    }
}
