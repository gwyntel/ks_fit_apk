package org.mozilla.javascript;

import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import kotlin.text.Typography;
import org.mozilla.javascript.json.JsonParser;

/* loaded from: classes5.dex */
public final class NativeJSON extends IdScriptableObject {
    private static final int Id_parse = 2;
    private static final int Id_stringify = 3;
    private static final int Id_toSource = 1;
    private static final Object JSON_TAG = "JSON";
    private static final int LAST_METHOD_ID = 3;
    private static final int MAX_ID = 3;
    private static final int MAX_STRINGIFY_GAP_LENGTH = 10;
    static final long serialVersionUID = -4567599697595654984L;

    private static class StringifyState {
        Context cx;
        String gap;
        String indent;
        List<Object> propertyList;
        Callable replacer;
        Scriptable scope;
        Object space;
        Stack<Scriptable> stack = new Stack<>();

        StringifyState(Context context, Scriptable scriptable, String str, String str2, Callable callable, List<Object> list, Object obj) {
            this.cx = context;
            this.scope = scriptable;
            this.indent = str;
            this.gap = str2;
            this.replacer = callable;
            this.propertyList = list;
            this.space = obj;
        }
    }

    private NativeJSON() {
    }

    static void init(Scriptable scriptable, boolean z2) {
        NativeJSON nativeJSON = new NativeJSON();
        nativeJSON.activatePrototypeMap(3);
        nativeJSON.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        nativeJSON.setParentScope(scriptable);
        if (z2) {
            nativeJSON.sealObject();
        }
        ScriptableObject.defineProperty(scriptable, "JSON", nativeJSON, 2);
    }

    private static String ja(NativeArray nativeArray, StringifyState stringifyState) {
        String str;
        if (stringifyState.stack.search(nativeArray) != -1) {
            throw ScriptRuntime.typeError0("msg.cyclic.value");
        }
        stringifyState.stack.push(nativeArray);
        String str2 = stringifyState.indent;
        stringifyState.indent += stringifyState.gap;
        LinkedList linkedList = new LinkedList();
        long length = nativeArray.getLength();
        long j2 = 0;
        while (j2 < length) {
            Object objStr = j2 > 2147483647L ? str(Long.toString(j2), nativeArray, stringifyState) : str(Integer.valueOf((int) j2), nativeArray, stringifyState);
            if (objStr == Undefined.instance) {
                linkedList.add(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                linkedList.add(objStr);
            }
            j2++;
        }
        if (linkedList.isEmpty()) {
            str = "[]";
        } else if (stringifyState.gap.length() == 0) {
            str = '[' + join(linkedList, ",") + ']';
        } else {
            str = "[\n" + stringifyState.indent + join(linkedList, ",\n" + stringifyState.indent) + '\n' + str2 + ']';
        }
        stringifyState.stack.pop();
        stringifyState.indent = str2;
        return str;
    }

    private static String jo(Scriptable scriptable, StringifyState stringifyState) {
        String str;
        if (stringifyState.stack.search(scriptable) != -1) {
            throw ScriptRuntime.typeError0("msg.cyclic.value");
        }
        stringifyState.stack.push(scriptable);
        String str2 = stringifyState.indent;
        stringifyState.indent += stringifyState.gap;
        List<Object> list = stringifyState.propertyList;
        Object[] array = list != null ? list.toArray() : scriptable.getIds();
        LinkedList linkedList = new LinkedList();
        for (Object obj : array) {
            Object objStr = str(obj, scriptable, stringifyState);
            if (objStr != Undefined.instance) {
                String str3 = quote(obj.toString()) + ":";
                if (stringifyState.gap.length() > 0) {
                    str3 = str3 + " ";
                }
                linkedList.add(str3 + objStr);
            }
        }
        if (linkedList.isEmpty()) {
            str = "{}";
        } else if (stringifyState.gap.length() == 0) {
            str = '{' + join(linkedList, ",") + '}';
        } else {
            str = "{\n" + stringifyState.indent + join(linkedList, ",\n" + stringifyState.indent) + '\n' + str2 + '}';
        }
        stringifyState.stack.pop();
        stringifyState.indent = str2;
        return str;
    }

    private static String join(Collection<Object> collection, String str) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        Iterator<Object> it = collection.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(it.next().toString());
        while (it.hasNext()) {
            sb.append(str);
            sb.append(it.next().toString());
        }
        return sb.toString();
    }

    private static Object parse(Context context, Scriptable scriptable, String str) {
        try {
            return new JsonParser(context, scriptable).parseValue(str);
        } catch (JsonParser.ParseException e2) {
            throw ScriptRuntime.constructError("SyntaxError", e2.getMessage());
        }
    }

    private static String quote(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append(Typography.quote);
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\f') {
                sb.append("\\f");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else if (cCharAt == '\"') {
                sb.append("\\\"");
            } else if (cCharAt != '\\') {
                switch (cCharAt) {
                    case '\b':
                        sb.append("\\b");
                        break;
                    case '\t':
                        sb.append("\\t");
                        break;
                    case '\n':
                        sb.append("\\n");
                        break;
                    default:
                        if (cCharAt < ' ') {
                            sb.append("\\u");
                            sb.append(String.format("%04x", Integer.valueOf(cCharAt)));
                            break;
                        } else {
                            sb.append(cCharAt);
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        sb.append(Typography.quote);
        return sb.toString();
    }

    private static String repeat(char c2, int i2) {
        char[] cArr = new char[i2];
        Arrays.fill(cArr, c2);
        return new String(cArr);
    }

    private static Object str(Object obj, Scriptable scriptable, StringifyState stringifyState) {
        Object property = obj instanceof String ? ScriptableObject.getProperty(scriptable, (String) obj) : ScriptableObject.getProperty(scriptable, ((Number) obj).intValue());
        if (property instanceof Scriptable) {
            Scriptable scriptable2 = (Scriptable) property;
            if (ScriptableObject.hasProperty(scriptable2, "toJSON") && (ScriptableObject.getProperty(scriptable2, "toJSON") instanceof Callable)) {
                property = ScriptableObject.callMethod(stringifyState.cx, scriptable2, "toJSON", new Object[]{obj});
            }
        }
        Callable callable = stringifyState.replacer;
        if (callable != null) {
            property = callable.call(stringifyState.cx, stringifyState.scope, scriptable, new Object[]{obj, property});
        }
        if (property instanceof NativeNumber) {
            property = Double.valueOf(ScriptRuntime.toNumber(property));
        } else if (property instanceof NativeString) {
            property = ScriptRuntime.toString(property);
        } else if (property instanceof NativeBoolean) {
            property = ((NativeBoolean) property).getDefaultValue(ScriptRuntime.BooleanClass);
        }
        if (property == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (property.equals(Boolean.TRUE)) {
            return "true";
        }
        if (property.equals(Boolean.FALSE)) {
            return RequestConstant.FALSE;
        }
        if (property instanceof CharSequence) {
            return quote(property.toString());
        }
        if (!(property instanceof Number)) {
            return (!(property instanceof Scriptable) || (property instanceof Callable)) ? Undefined.instance : property instanceof NativeArray ? ja((NativeArray) property, stringifyState) : jo((Scriptable) property, stringifyState);
        }
        double dDoubleValue = ((Number) property).doubleValue();
        return (dDoubleValue != dDoubleValue || dDoubleValue == Double.POSITIVE_INFINITY || dDoubleValue == Double.NEGATIVE_INFINITY) ? TmpConstant.GROUP_ROLE_UNKNOWN : ScriptRuntime.toString(property);
    }

    public static Object stringify(Context context, Scriptable scriptable, Object obj, Object obj2, Object obj3) {
        Callable callable;
        LinkedList linkedList;
        Object obj4;
        String str;
        String strSubstring;
        if (obj2 instanceof Callable) {
            callable = (Callable) obj2;
            linkedList = null;
        } else if (obj2 instanceof NativeArray) {
            LinkedList linkedList2 = new LinkedList();
            NativeArray nativeArray = (NativeArray) obj2;
            for (Integer num : nativeArray.getIndexIds()) {
                Object obj5 = nativeArray.get(num.intValue(), nativeArray);
                if ((obj5 instanceof String) || (obj5 instanceof Number)) {
                    linkedList2.add(obj5);
                } else if ((obj5 instanceof NativeString) || (obj5 instanceof NativeNumber)) {
                    linkedList2.add(ScriptRuntime.toString(obj5));
                }
            }
            linkedList = linkedList2;
            callable = null;
        } else {
            callable = null;
            linkedList = null;
        }
        Object objValueOf = obj3 instanceof NativeNumber ? Double.valueOf(ScriptRuntime.toNumber(obj3)) : obj3 instanceof NativeString ? ScriptRuntime.toString(obj3) : obj3;
        if (objValueOf instanceof Number) {
            int iMin = Math.min(10, (int) ScriptRuntime.toInteger(objValueOf));
            strSubstring = iMin > 0 ? repeat(' ', iMin) : "";
            objValueOf = Integer.valueOf(iMin);
        } else {
            if (!(objValueOf instanceof String)) {
                obj4 = objValueOf;
                str = "";
                StringifyState stringifyState = new StringifyState(context, scriptable, "", str, callable, linkedList, obj4);
                NativeObject nativeObject = new NativeObject();
                nativeObject.setParentScope(scriptable);
                nativeObject.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
                nativeObject.defineProperty("", obj, 0);
                return str("", nativeObject, stringifyState);
            }
            strSubstring = (String) objValueOf;
            if (strSubstring.length() > 10) {
                strSubstring = strSubstring.substring(0, 10);
            }
        }
        obj4 = objValueOf;
        str = strSubstring;
        StringifyState stringifyState2 = new StringifyState(context, scriptable, "", str, callable, linkedList, obj4);
        NativeObject nativeObject2 = new NativeObject();
        nativeObject2.setParentScope(scriptable);
        nativeObject2.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        nativeObject2.defineProperty("", obj, 0);
        return str("", nativeObject2, stringifyState2);
    }

    private static Object walk(Context context, Scriptable scriptable, Callable callable, Scriptable scriptable2, Object obj) {
        Object obj2 = obj instanceof Number ? scriptable2.get(((Number) obj).intValue(), scriptable2) : scriptable2.get((String) obj, scriptable2);
        if (obj2 instanceof Scriptable) {
            Scriptable scriptable3 = (Scriptable) obj2;
            if (scriptable3 instanceof NativeArray) {
                long length = ((NativeArray) scriptable3).getLength();
                for (long j2 = 0; j2 < length; j2++) {
                    if (j2 > 2147483647L) {
                        String string = Long.toString(j2);
                        Object objWalk = walk(context, scriptable, callable, scriptable3, string);
                        if (objWalk == Undefined.instance) {
                            scriptable3.delete(string);
                        } else {
                            scriptable3.put(string, scriptable3, objWalk);
                        }
                    } else {
                        int i2 = (int) j2;
                        Object objWalk2 = walk(context, scriptable, callable, scriptable3, Integer.valueOf(i2));
                        if (objWalk2 == Undefined.instance) {
                            scriptable3.delete(i2);
                        } else {
                            scriptable3.put(i2, scriptable3, objWalk2);
                        }
                    }
                }
            } else {
                for (Object obj3 : scriptable3.getIds()) {
                    Object objWalk3 = walk(context, scriptable, callable, scriptable3, obj3);
                    if (objWalk3 == Undefined.instance) {
                        if (obj3 instanceof Number) {
                            scriptable3.delete(((Number) obj3).intValue());
                        } else {
                            scriptable3.delete((String) obj3);
                        }
                    } else if (obj3 instanceof Number) {
                        scriptable3.put(((Number) obj3).intValue(), scriptable3, objWalk3);
                    } else {
                        scriptable3.put((String) obj3, scriptable3, objWalk3);
                    }
                }
            }
        }
        return callable.call(context, scriptable, scriptable2, new Object[]{obj, obj2});
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object obj;
        Object obj2;
        Object obj3;
        if (!idFunctionObject.hasTag(JSON_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == 1) {
            return "JSON";
        }
        if (iMethodId == 2) {
            String string = ScriptRuntime.toString(objArr, 0);
            obj = objArr.length > 1 ? objArr[1] : null;
            return obj instanceof Callable ? parse(context, scriptable, string, (Callable) obj) : parse(context, scriptable, string);
        }
        if (iMethodId != 3) {
            throw new IllegalStateException(String.valueOf(iMethodId));
        }
        int length = objArr.length;
        if (length != 1) {
            if (length != 2) {
                if (length != 3) {
                    obj3 = null;
                    obj2 = null;
                    return stringify(context, scriptable, obj, obj3, obj2);
                }
                obj = objArr[2];
            }
            Object obj4 = obj;
            obj = objArr[1];
            obj = obj4;
        } else {
            obj = null;
        }
        obj2 = obj;
        obj3 = obj;
        obj = objArr[0];
        return stringify(context, scriptable, obj, obj3, obj2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i2;
        int length = str.length();
        if (length == 5) {
            str2 = "parse";
            i2 = 2;
        } else if (length == 8) {
            str2 = "toSource";
            i2 = 1;
        } else if (length != 9) {
            str2 = null;
            i2 = 0;
        } else {
            str2 = "stringify";
            i2 = 3;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i2;
        }
        return 0;
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "JSON";
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        int i3 = 3;
        if (i2 > 3) {
            throw new IllegalStateException(String.valueOf(i2));
        }
        if (i2 == 1) {
            i3 = 0;
            str = "toSource";
        } else if (i2 == 2) {
            str = "parse";
            i3 = 2;
        } else {
            if (i2 != 3) {
                throw new IllegalStateException(String.valueOf(i2));
            }
            str = "stringify";
        }
        initPrototypeMethod(JSON_TAG, i2, str, i3);
    }

    public static Object parse(Context context, Scriptable scriptable, String str, Callable callable) {
        Object obj = parse(context, scriptable, str);
        Scriptable scriptableNewObject = context.newObject(scriptable);
        scriptableNewObject.put("", scriptableNewObject, obj);
        return walk(context, scriptable, callable, scriptableNewObject, "");
    }
}
