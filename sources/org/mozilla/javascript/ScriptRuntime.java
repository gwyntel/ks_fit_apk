package org.mozilla.javascript;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.NativeIterator;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.typedarrays.NativeArrayBuffer;
import org.mozilla.javascript.typedarrays.NativeDataView;
import org.mozilla.javascript.v8dtoa.DoubleConversion;
import org.mozilla.javascript.v8dtoa.FastDtoa;
import org.mozilla.javascript.xml.XMLLib;
import org.mozilla.javascript.xml.XMLObject;

/* loaded from: classes5.dex */
public class ScriptRuntime {
    private static final String DEFAULT_NS_TAG = "__default_namespace__";
    public static final int ENUMERATE_ARRAY = 2;
    public static final int ENUMERATE_ARRAY_NO_ITERATOR = 5;
    public static final int ENUMERATE_KEYS = 0;
    public static final int ENUMERATE_KEYS_NO_ITERATOR = 3;
    public static final int ENUMERATE_VALUES = 1;
    public static final int ENUMERATE_VALUES_IN_ORDER = 6;
    public static final int ENUMERATE_VALUES_NO_ITERATOR = 4;
    public static final double NaN;
    public static final Double NaNobj;
    public static final Object[] emptyArgs;
    public static final String[] emptyStrings;
    public static MessageProvider messageProvider;
    public static final double negativeZero;
    public static final Class<?> BooleanClass = Kit.classOrNull("java.lang.Boolean");
    public static final Class<?> ByteClass = Kit.classOrNull("java.lang.Byte");
    public static final Class<?> CharacterClass = Kit.classOrNull("java.lang.Character");
    public static final Class<?> ClassClass = Kit.classOrNull("java.lang.Class");
    public static final Class<?> DoubleClass = Kit.classOrNull("java.lang.Double");
    public static final Class<?> FloatClass = Kit.classOrNull("java.lang.Float");
    public static final Class<?> IntegerClass = Kit.classOrNull("java.lang.Integer");
    public static final Class<?> LongClass = Kit.classOrNull("java.lang.Long");
    public static final Class<?> NumberClass = Kit.classOrNull("java.lang.Number");
    public static final Class<?> ObjectClass = Kit.classOrNull("java.lang.Object");
    public static final Class<?> ShortClass = Kit.classOrNull("java.lang.Short");
    public static final Class<?> StringClass = Kit.classOrNull("java.lang.String");
    public static final Class<?> DateClass = Kit.classOrNull("java.util.Date");
    public static final Class<?> ContextClass = Kit.classOrNull("org.mozilla.javascript.Context");
    public static final Class<?> ContextFactoryClass = Kit.classOrNull("org.mozilla.javascript.ContextFactory");
    public static final Class<?> FunctionClass = Kit.classOrNull("org.mozilla.javascript.Function");
    public static final Class<?> ScriptableObjectClass = Kit.classOrNull("org.mozilla.javascript.ScriptableObject");
    public static final Class<Scriptable> ScriptableClass = Scriptable.class;
    public static Locale ROOT_LOCALE = new Locale("");
    private static final Object LIBRARY_SCOPE_KEY = "LIBRARY_SCOPE";

    private static class DefaultMessageProvider implements MessageProvider {
        private DefaultMessageProvider() {
        }

        @Override // org.mozilla.javascript.ScriptRuntime.MessageProvider
        public String getMessage(String str, Object[] objArr) {
            Context currentContext = Context.getCurrentContext();
            try {
                return new MessageFormat(ResourceBundle.getBundle("org.mozilla.javascript.resources.Messages", currentContext != null ? currentContext.getLocale() : Locale.getDefault()).getString(str)).format(objArr);
            } catch (MissingResourceException unused) {
                throw new RuntimeException("no message resource found for message property " + str);
            }
        }
    }

    private static class IdEnumeration implements Serializable {
        private static final long serialVersionUID = 1;
        Object currentId;
        boolean enumNumbers;
        int enumType;
        Object[] ids;
        int index;
        Scriptable iterator;
        Scriptable obj;
        ObjToIntMap used;

        private IdEnumeration() {
        }
    }

    public interface MessageProvider {
        String getMessage(String str, Object[] objArr);
    }

    static class NoSuchMethodShim implements Callable {
        String methodName;
        Callable noSuchMethodMethod;

        NoSuchMethodShim(Callable callable, String str) {
            this.noSuchMethodMethod = callable;
            this.methodName = str;
        }

        @Override // org.mozilla.javascript.Callable
        public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
            return this.noSuchMethodMethod.call(context, scriptable, scriptable2, new Object[]{this.methodName, ScriptRuntime.newArrayLiteral(objArr, null, context, scriptable)});
        }
    }

    static {
        double dLongBitsToDouble = Double.longBitsToDouble(9221120237041090560L);
        NaN = dLongBitsToDouble;
        negativeZero = Double.longBitsToDouble(Long.MIN_VALUE);
        NaNobj = new Double(dLongBitsToDouble);
        messageProvider = new DefaultMessageProvider();
        emptyArgs = new Object[0];
        emptyStrings = new String[0];
    }

    protected ScriptRuntime() {
    }

    public static Object add(Object obj, Object obj2, Context context) {
        Object objAddValues;
        Object objAddValues2;
        if ((obj instanceof Number) && (obj2 instanceof Number)) {
            return wrapNumber(((Number) obj).doubleValue() + ((Number) obj2).doubleValue());
        }
        if ((obj instanceof XMLObject) && (objAddValues2 = ((XMLObject) obj).addValues(context, true, obj2)) != Scriptable.NOT_FOUND) {
            return objAddValues2;
        }
        if ((obj2 instanceof XMLObject) && (objAddValues = ((XMLObject) obj2).addValues(context, false, obj)) != Scriptable.NOT_FOUND) {
            return objAddValues;
        }
        if ((obj instanceof Symbol) || (obj2 instanceof Symbol)) {
            throw typeError0("msg.not.a.number");
        }
        if (obj instanceof Scriptable) {
            obj = ((Scriptable) obj).getDefaultValue(null);
        }
        if (obj2 instanceof Scriptable) {
            obj2 = ((Scriptable) obj2).getDefaultValue(null);
        }
        return ((obj instanceof CharSequence) || (obj2 instanceof CharSequence)) ? new ConsString(toCharSequence(obj), toCharSequence(obj2)) : ((obj instanceof Number) && (obj2 instanceof Number)) ? wrapNumber(((Number) obj).doubleValue() + ((Number) obj2).doubleValue()) : wrapNumber(toNumber(obj) + toNumber(obj2));
    }

    public static void addInstructionCount(Context context, int i2) {
        int i3 = context.instructionCount + i2;
        context.instructionCount = i3;
        if (i3 > context.instructionThreshold) {
            context.observeInstructionCount(i3);
            context.instructionCount = 0;
        }
    }

    public static Object applyOrCall(boolean z2, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Scriptable topCallScope;
        Object[] applyArguments;
        int length = objArr.length;
        Callable callable = getCallable(scriptable2);
        if (length == 0) {
            topCallScope = null;
        } else if (context.hasFeature(15)) {
            topCallScope = toObjectOrNull(context, objArr[0], scriptable);
        } else {
            Object obj = objArr[0];
            topCallScope = obj == Undefined.instance ? Undefined.SCRIPTABLE_UNDEFINED : toObjectOrNull(context, obj, scriptable);
        }
        if (topCallScope == null && context.hasFeature(15)) {
            topCallScope = getTopCallScope(context);
        }
        if (z2) {
            applyArguments = length <= 1 ? emptyArgs : getApplyArguments(context, objArr[1]);
        } else if (length <= 1) {
            applyArguments = emptyArgs;
        } else {
            int i2 = length - 1;
            applyArguments = new Object[i2];
            System.arraycopy(objArr, 1, applyArguments, 0, i2);
        }
        return callable.call(context, scriptable, topCallScope, applyArguments);
    }

    public static Scriptable bind(Context context, Scriptable scriptable, String str) {
        Scriptable parentScope = scriptable.getParentScope();
        XMLObject xMLObject = null;
        if (parentScope != null) {
            XMLObject xMLObject2 = null;
            while (true) {
                if (!(scriptable instanceof NativeWith)) {
                    while (!ScriptableObject.hasProperty(scriptable, str)) {
                        Scriptable parentScope2 = parentScope.getParentScope();
                        if (parentScope2 != null) {
                            Scriptable scriptable2 = parentScope;
                            parentScope = parentScope2;
                            scriptable = scriptable2;
                        }
                    }
                    return scriptable;
                }
                Scriptable prototype = scriptable.getPrototype();
                if (prototype instanceof XMLObject) {
                    XMLObject xMLObject3 = (XMLObject) prototype;
                    if (xMLObject3.has(context, str)) {
                        return xMLObject3;
                    }
                    if (xMLObject2 == null) {
                        xMLObject2 = xMLObject3;
                    }
                } else if (ScriptableObject.hasProperty(prototype, str)) {
                    return prototype;
                }
                Scriptable parentScope3 = parentScope.getParentScope();
                if (parentScope3 == null) {
                    break;
                }
                Scriptable scriptable3 = parentScope;
                parentScope = parentScope3;
                scriptable = scriptable3;
            }
            scriptable = parentScope;
            xMLObject = xMLObject2;
        }
        if (context.useDynamicScope) {
            scriptable = checkDynamicScope(context.topCallScope, scriptable);
        }
        return ScriptableObject.hasProperty(scriptable, str) ? scriptable : xMLObject;
    }

    @Deprecated
    public static Object call(Context context, Object obj, Object obj2, Object[] objArr, Scriptable scriptable) {
        if (!(obj instanceof Function)) {
            throw notFunctionError(toString(obj));
        }
        Function function = (Function) obj;
        Scriptable objectOrNull = toObjectOrNull(context, obj2, scriptable);
        if (objectOrNull != null) {
            return function.call(context, scriptable, objectOrNull, objArr);
        }
        throw undefCallError(objectOrNull, "function");
    }

    public static Ref callRef(Callable callable, Scriptable scriptable, Object[] objArr, Context context) {
        if (!(callable instanceof RefCallable)) {
            throw constructError("ReferenceError", getMessage1("msg.no.ref.from.function", toString(callable)));
        }
        RefCallable refCallable = (RefCallable) callable;
        Ref refRefCall = refCallable.refCall(context, scriptable, objArr);
        if (refRefCall != null) {
            return refRefCall;
        }
        throw new IllegalStateException(refCallable.getClass().getName() + ".refCall() returned null");
    }

    public static Object callSpecial(Context context, Callable callable, Scriptable scriptable, Object[] objArr, Scriptable scriptable2, Scriptable scriptable3, int i2, String str, int i3) {
        if (i2 == 1) {
            if (scriptable.getParentScope() == null && NativeGlobal.isEvalFunction(callable)) {
                return evalSpecial(context, scriptable2, scriptable3, objArr, str, i3);
            }
        } else {
            if (i2 != 2) {
                throw Kit.codeBug();
            }
            if (NativeWith.isWithFunction(callable)) {
                throw Context.reportRuntimeError1("msg.only.from.new", "With");
            }
        }
        return callable.call(context, scriptable2, scriptable, objArr);
    }

    static void checkDeprecated(Context context, String str) {
        int languageVersion = context.getLanguageVersion();
        if (languageVersion >= 140 || languageVersion == 0) {
            String message1 = getMessage1("msg.deprec.ctor", str);
            if (languageVersion != 0) {
                throw Context.reportRuntimeError(message1);
            }
            Context.reportWarning(message1);
        }
    }

    static Scriptable checkDynamicScope(Scriptable scriptable, Scriptable scriptable2) {
        if (scriptable == scriptable2) {
            return scriptable;
        }
        Scriptable prototype = scriptable;
        do {
            prototype = prototype.getPrototype();
            if (prototype == scriptable2) {
                return scriptable;
            }
        } while (prototype != null);
        return scriptable2;
    }

    public static RegExpProxy checkRegExpProxy(Context context) {
        RegExpProxy regExpProxy = getRegExpProxy(context);
        if (regExpProxy != null) {
            return regExpProxy;
        }
        throw Context.reportRuntimeError0("msg.no.regexp");
    }

    public static boolean cmp_LE(Object obj, Object obj2) {
        double number;
        double number2;
        if ((obj instanceof Number) && (obj2 instanceof Number)) {
            number = ((Number) obj).doubleValue();
            number2 = ((Number) obj2).doubleValue();
        } else {
            if ((obj instanceof Symbol) || (obj2 instanceof Symbol)) {
                throw typeError0("msg.compare.symbol");
            }
            if (obj instanceof Scriptable) {
                obj = ((Scriptable) obj).getDefaultValue(NumberClass);
            }
            if (obj2 instanceof Scriptable) {
                obj2 = ((Scriptable) obj2).getDefaultValue(NumberClass);
            }
            if ((obj instanceof CharSequence) && (obj2 instanceof CharSequence)) {
                return obj.toString().compareTo(obj2.toString()) <= 0;
            }
            number = toNumber(obj);
            number2 = toNumber(obj2);
        }
        return number <= number2;
    }

    public static boolean cmp_LT(Object obj, Object obj2) {
        double number;
        double number2;
        if ((obj instanceof Number) && (obj2 instanceof Number)) {
            number = ((Number) obj).doubleValue();
            number2 = ((Number) obj2).doubleValue();
        } else {
            if ((obj instanceof Symbol) || (obj2 instanceof Symbol)) {
                throw typeError0("msg.compare.symbol");
            }
            if (obj instanceof Scriptable) {
                obj = ((Scriptable) obj).getDefaultValue(NumberClass);
            }
            if (obj2 instanceof Scriptable) {
                obj2 = ((Scriptable) obj2).getDefaultValue(NumberClass);
            }
            if ((obj instanceof CharSequence) && (obj2 instanceof CharSequence)) {
                return obj.toString().compareTo(obj2.toString()) < 0;
            }
            number = toNumber(obj);
            number2 = toNumber(obj2);
        }
        return number < number2;
    }

    public static EcmaError constructError(String str, String str2) {
        int[] iArr = new int[1];
        return constructError(str, str2, Context.getSourcePositionFromStack(iArr), iArr[0], null, 0);
    }

    public static Scriptable createArrowFunctionActivation(NativeFunction nativeFunction, Scriptable scriptable, Object[] objArr, boolean z2) {
        return new NativeCall(nativeFunction, scriptable, objArr, true, z2);
    }

    @Deprecated
    public static Scriptable createFunctionActivation(NativeFunction nativeFunction, Scriptable scriptable, Object[] objArr) {
        return createFunctionActivation(nativeFunction, scriptable, objArr, false);
    }

    private static XMLLib currentXMLLib(Context context) {
        Scriptable scriptable = context.topCallScope;
        if (scriptable == null) {
            throw new IllegalStateException();
        }
        XMLLib xMLLibExtractFromScope = context.cachedXMLLib;
        if (xMLLibExtractFromScope == null) {
            xMLLibExtractFromScope = XMLLib.extractFromScope(scriptable);
            if (xMLLibExtractFromScope == null) {
                throw new IllegalStateException();
            }
            context.cachedXMLLib = xMLLibExtractFromScope;
        }
        return xMLLibExtractFromScope;
    }

    static String defaultObjectToSource(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        boolean zHas;
        boolean z2;
        Object obj;
        ObjToIntMap objToIntMap = context.iterating;
        if (objToIntMap == null) {
            context.iterating = new ObjToIntMap(31);
            z2 = true;
            zHas = false;
        } else {
            zHas = objToIntMap.has(scriptable2);
            z2 = false;
        }
        StringBuilder sb = new StringBuilder(128);
        if (z2) {
            sb.append("(");
        }
        sb.append('{');
        if (!zHas) {
            try {
                context.iterating.intern(scriptable2);
                Object[] ids = scriptable2.getIds();
                for (int i2 = 0; i2 < ids.length; i2++) {
                    Object obj2 = ids[i2];
                    if (obj2 instanceof Integer) {
                        int iIntValue = ((Integer) obj2).intValue();
                        obj = scriptable2.get(iIntValue, scriptable2);
                        if (obj != Scriptable.NOT_FOUND) {
                            if (i2 > 0) {
                                sb.append(", ");
                            }
                            sb.append(iIntValue);
                            sb.append(':');
                            sb.append(uneval(context, scriptable, obj));
                        }
                    } else {
                        String str = (String) obj2;
                        obj = scriptable2.get(str, scriptable2);
                        if (obj != Scriptable.NOT_FOUND) {
                            if (i2 > 0) {
                                sb.append(", ");
                            }
                            if (isValidIdentifierName(str, context, context.isStrictMode())) {
                                sb.append(str);
                            } else {
                                sb.append('\'');
                                sb.append(escapeString(str, '\''));
                                sb.append('\'');
                            }
                            sb.append(':');
                            sb.append(uneval(context, scriptable, obj));
                        }
                    }
                }
            } catch (Throwable th) {
                if (z2) {
                    context.iterating = null;
                }
                throw th;
            }
        }
        if (z2) {
            context.iterating = null;
        }
        sb.append('}');
        if (z2) {
            sb.append(')');
        }
        return sb.toString();
    }

    static String defaultObjectToString(Scriptable scriptable) {
        if (scriptable == null) {
            return "[object Null]";
        }
        if (Undefined.isUndefined(scriptable)) {
            return "[object Undefined]";
        }
        return "[object " + scriptable.getClassName() + ']';
    }

    @Deprecated
    public static Object delete(Object obj, Object obj2, Context context) {
        return delete(obj, obj2, context, false);
    }

    public static boolean deleteObjectElem(Scriptable scriptable, Object obj, Context context) {
        if (isSymbol(obj)) {
            ScriptableObject.ensureSymbolScriptable(scriptable).delete((Symbol) obj);
            return !r3.has(r2, scriptable);
        }
        String stringIdOrIndex = toStringIdOrIndex(context, obj);
        if (stringIdOrIndex != null) {
            scriptable.delete(stringIdOrIndex);
            return !scriptable.has(stringIdOrIndex, scriptable);
        }
        scriptable.delete(lastIndexResult(context));
        return !scriptable.has(r2, scriptable);
    }

    private static Object doScriptableIncrDecr(Scriptable scriptable, String str, Scriptable scriptable2, Object obj, int i2) {
        double number;
        boolean z2 = (i2 & 2) != 0;
        if (obj instanceof Number) {
            number = ((Number) obj).doubleValue();
        } else {
            number = toNumber(obj);
            if (z2) {
                obj = wrapNumber(number);
            }
        }
        Number numberWrapNumber = wrapNumber((i2 & 1) == 0 ? number + 1.0d : number - 1.0d);
        scriptable.put(str, scriptable2, numberWrapNumber);
        return z2 ? obj : numberWrapNumber;
    }

    public static Object doTopCall(Callable callable, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        return doTopCall(callable, context, scriptable, scriptable2, objArr, context.isTopLevelStrict);
    }

    @Deprecated
    public static Object elemIncrDecr(Object obj, Object obj2, Context context, int i2) {
        return elemIncrDecr(obj, obj2, context, getTopCallScope(context), i2);
    }

    public static void enterActivationFunction(Context context, Scriptable scriptable) {
        if (context.topCallScope == null) {
            throw new IllegalStateException();
        }
        NativeCall nativeCall = (NativeCall) scriptable;
        nativeCall.parentActivationCall = context.currentActivationCall;
        context.currentActivationCall = nativeCall;
        nativeCall.defineAttributesForArguments();
    }

    public static Scriptable enterDotQuery(Object obj, Scriptable scriptable) {
        if (obj instanceof XMLObject) {
            return ((XMLObject) obj).enterDotQuery(scriptable);
        }
        throw notXmlError(obj);
    }

    public static Scriptable enterWith(Object obj, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return objectOrNull instanceof XMLObject ? ((XMLObject) objectOrNull).enterWith(scriptable) : new NativeWith(scriptable, objectOrNull);
        }
        throw typeError1("msg.undef.with", toString(obj));
    }

    private static void enumChangeObject(IdEnumeration idEnumeration) {
        Object[] objArr;
        Object[] ids = null;
        while (true) {
            Scriptable scriptable = idEnumeration.obj;
            if (scriptable == null) {
                break;
            }
            ids = scriptable.getIds();
            if (ids.length != 0) {
                break;
            } else {
                idEnumeration.obj = idEnumeration.obj.getPrototype();
            }
        }
        if (idEnumeration.obj != null && (objArr = idEnumeration.ids) != null) {
            int length = objArr.length;
            if (idEnumeration.used == null) {
                idEnumeration.used = new ObjToIntMap(length);
            }
            for (int i2 = 0; i2 != length; i2++) {
                idEnumeration.used.intern(objArr[i2]);
            }
        }
        idEnumeration.ids = ids;
        idEnumeration.index = 0;
    }

    public static Object enumId(Object obj, Context context) {
        IdEnumeration idEnumeration = (IdEnumeration) obj;
        if (idEnumeration.iterator != null) {
            return idEnumeration.currentId;
        }
        int i2 = idEnumeration.enumType;
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3) {
                        if (i2 != 4) {
                            if (i2 != 5) {
                                throw Kit.codeBug();
                            }
                        }
                    }
                }
                return context.newArray(ScriptableObject.getTopLevelScope(idEnumeration.obj), new Object[]{idEnumeration.currentId, enumValue(obj, context)});
            }
            return enumValue(obj, context);
        }
        return idEnumeration.currentId;
    }

    @Deprecated
    public static Object enumInit(Object obj, Context context, boolean z2) {
        return enumInit(obj, context, z2 ? 1 : 0);
    }

    private static Object enumInitInOrder(Context context, IdEnumeration idEnumeration) {
        Scriptable scriptable = idEnumeration.obj;
        if (!(scriptable instanceof ScriptableObject)) {
            throw typeError1("msg.not.iterable", toString(scriptable));
        }
        ScriptableObject scriptableObject = (ScriptableObject) scriptable;
        SymbolKey symbolKey = SymbolKey.ITERATOR;
        if (!ScriptableObject.hasProperty(scriptableObject, symbolKey)) {
            throw typeError1("msg.not.iterable", toString(idEnumeration.obj));
        }
        Object property = ScriptableObject.getProperty(scriptableObject, symbolKey);
        if (!(property instanceof Callable)) {
            throw typeError1("msg.not.iterable", toString(idEnumeration.obj));
        }
        Object objCall = ((Callable) property).call(context, idEnumeration.obj.getParentScope(), idEnumeration.obj, new Object[0]);
        if (!(objCall instanceof Scriptable)) {
            throw typeError1("msg.not.iterable", toString(idEnumeration.obj));
        }
        idEnumeration.iterator = (Scriptable) objCall;
        return idEnumeration;
    }

    public static Boolean enumNext(Object obj) {
        IdEnumeration idEnumeration = (IdEnumeration) obj;
        Scriptable scriptable = idEnumeration.iterator;
        if (scriptable != null) {
            if (idEnumeration.enumType == 6) {
                return enumNextInOrder(idEnumeration);
            }
            Object property = ScriptableObject.getProperty(scriptable, ES6Iterator.NEXT_METHOD);
            if (!(property instanceof Callable)) {
                return Boolean.FALSE;
            }
            try {
                idEnumeration.currentId = ((Callable) property).call(Context.getContext(), idEnumeration.iterator.getParentScope(), idEnumeration.iterator, emptyArgs);
                return Boolean.TRUE;
            } catch (JavaScriptException e2) {
                if (e2.getValue() instanceof NativeIterator.StopIteration) {
                    return Boolean.FALSE;
                }
                throw e2;
            }
        }
        while (true) {
            Scriptable scriptable2 = idEnumeration.obj;
            if (scriptable2 == null) {
                return Boolean.FALSE;
            }
            int i2 = idEnumeration.index;
            Object[] objArr = idEnumeration.ids;
            if (i2 == objArr.length) {
                idEnumeration.obj = scriptable2.getPrototype();
                enumChangeObject(idEnumeration);
            } else {
                idEnumeration.index = i2 + 1;
                Object obj2 = objArr[i2];
                ObjToIntMap objToIntMap = idEnumeration.used;
                if (objToIntMap == null || !objToIntMap.has(obj2)) {
                    if (obj2 instanceof Symbol) {
                        continue;
                    } else if (obj2 instanceof String) {
                        String str = (String) obj2;
                        Scriptable scriptable3 = idEnumeration.obj;
                        if (scriptable3.has(str, scriptable3)) {
                            idEnumeration.currentId = str;
                            break;
                        }
                    } else {
                        int iIntValue = ((Number) obj2).intValue();
                        Scriptable scriptable4 = idEnumeration.obj;
                        if (scriptable4.has(iIntValue, scriptable4)) {
                            idEnumeration.currentId = idEnumeration.enumNumbers ? Integer.valueOf(iIntValue) : String.valueOf(iIntValue);
                        }
                    }
                }
            }
        }
        return Boolean.TRUE;
    }

    private static Boolean enumNextInOrder(IdEnumeration idEnumeration) {
        Object property = ScriptableObject.getProperty(idEnumeration.iterator, ES6Iterator.NEXT_METHOD);
        if (!(property instanceof Callable)) {
            throw notFunctionError(idEnumeration.iterator, ES6Iterator.NEXT_METHOD);
        }
        Context context = Context.getContext();
        Scriptable parentScope = idEnumeration.iterator.getParentScope();
        Scriptable object = toObject(context, parentScope, ((Callable) property).call(context, parentScope, idEnumeration.iterator, emptyArgs));
        Object property2 = ScriptableObject.getProperty(object, ES6Iterator.DONE_PROPERTY);
        if (property2 != Scriptable.NOT_FOUND && toBoolean(property2)) {
            return Boolean.FALSE;
        }
        idEnumeration.currentId = ScriptableObject.getProperty(object, "value");
        return Boolean.TRUE;
    }

    public static Object enumValue(Object obj, Context context) {
        IdEnumeration idEnumeration = (IdEnumeration) obj;
        if (isSymbol(idEnumeration.currentId)) {
            return ScriptableObject.ensureSymbolScriptable(idEnumeration.obj).get((Symbol) idEnumeration.currentId, idEnumeration.obj);
        }
        String stringIdOrIndex = toStringIdOrIndex(context, idEnumeration.currentId);
        if (stringIdOrIndex != null) {
            Scriptable scriptable = idEnumeration.obj;
            return scriptable.get(stringIdOrIndex, scriptable);
        }
        int iLastIndexResult = lastIndexResult(context);
        Scriptable scriptable2 = idEnumeration.obj;
        return scriptable2.get(iLastIndexResult, scriptable2);
    }

    public static boolean eq(Object obj, Object obj2) {
        Object objEquivalentValues;
        Object objEquivalentValues2;
        Object objEquivalentValues3;
        Object objEquivalentValues4;
        Object objEquivalentValues5;
        if (obj == null || obj == Undefined.instance) {
            if (obj2 == null || obj2 == Undefined.instance) {
                return true;
            }
            if (!(obj2 instanceof ScriptableObject) || (objEquivalentValues = ((ScriptableObject) obj2).equivalentValues(obj)) == Scriptable.NOT_FOUND) {
                return false;
            }
            return ((Boolean) objEquivalentValues).booleanValue();
        }
        if (obj instanceof Number) {
            return eqNumber(((Number) obj).doubleValue(), obj2);
        }
        if (obj == obj2) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return eqString((CharSequence) obj, obj2);
        }
        if (obj instanceof Boolean) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            if (obj2 instanceof Boolean) {
                return zBooleanValue == ((Boolean) obj2).booleanValue();
            }
            if (!(obj2 instanceof ScriptableObject) || (objEquivalentValues5 = ((ScriptableObject) obj2).equivalentValues(obj)) == Scriptable.NOT_FOUND) {
                return eqNumber(zBooleanValue ? 1.0d : 0.0d, obj2);
            }
            return ((Boolean) objEquivalentValues5).booleanValue();
        }
        if (!(obj instanceof Scriptable)) {
            warnAboutNonJSObject(obj);
            return obj == obj2;
        }
        if (!(obj2 instanceof Scriptable)) {
            if (obj2 instanceof Boolean) {
                if (!(obj instanceof ScriptableObject) || (objEquivalentValues2 = ((ScriptableObject) obj).equivalentValues(obj2)) == Scriptable.NOT_FOUND) {
                    return eqNumber(((Boolean) obj2).booleanValue() ? 1.0d : 0.0d, obj);
                }
                return ((Boolean) objEquivalentValues2).booleanValue();
            }
            if (obj2 instanceof Number) {
                return eqNumber(((Number) obj2).doubleValue(), obj);
            }
            if (obj2 instanceof CharSequence) {
                return eqString((CharSequence) obj2, obj);
            }
            return false;
        }
        if ((obj instanceof ScriptableObject) && (objEquivalentValues4 = ((ScriptableObject) obj).equivalentValues(obj2)) != Scriptable.NOT_FOUND) {
            return ((Boolean) objEquivalentValues4).booleanValue();
        }
        if ((obj2 instanceof ScriptableObject) && (objEquivalentValues3 = ((ScriptableObject) obj2).equivalentValues(obj)) != Scriptable.NOT_FOUND) {
            return ((Boolean) objEquivalentValues3).booleanValue();
        }
        if (!(obj instanceof Wrapper) || !(obj2 instanceof Wrapper)) {
            return false;
        }
        Object objUnwrap = ((Wrapper) obj).unwrap();
        Object objUnwrap2 = ((Wrapper) obj2).unwrap();
        if (objUnwrap != objUnwrap2) {
            return isPrimitive(objUnwrap) && isPrimitive(objUnwrap2) && eq(objUnwrap, objUnwrap2);
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x006b, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static boolean eqNumber(double r5, java.lang.Object r7) {
        /*
        L0:
            r0 = 0
            if (r7 == 0) goto L6b
            java.lang.Object r1 = org.mozilla.javascript.Undefined.instance
            if (r7 != r1) goto L8
            goto L6b
        L8:
            boolean r1 = r7 instanceof java.lang.Number
            r2 = 1
            if (r1 == 0) goto L19
            java.lang.Number r7 = (java.lang.Number) r7
            double r3 = r7.doubleValue()
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 != 0) goto L18
            r0 = r2
        L18:
            return r0
        L19:
            boolean r1 = r7 instanceof java.lang.CharSequence
            if (r1 == 0) goto L27
            double r3 = toNumber(r7)
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 != 0) goto L26
            r0 = r2
        L26:
            return r0
        L27:
            boolean r1 = r7 instanceof java.lang.Boolean
            if (r1 == 0) goto L3e
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L36
            r3 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            goto L38
        L36:
            r3 = 0
        L38:
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 != 0) goto L3d
            r0 = r2
        L3d:
            return r0
        L3e:
            boolean r1 = isSymbol(r7)
            if (r1 == 0) goto L45
            return r0
        L45:
            boolean r1 = r7 instanceof org.mozilla.javascript.Scriptable
            if (r1 == 0) goto L68
            boolean r0 = r7 instanceof org.mozilla.javascript.ScriptableObject
            if (r0 == 0) goto L63
            java.lang.Number r0 = wrapNumber(r5)
            r1 = r7
            org.mozilla.javascript.ScriptableObject r1 = (org.mozilla.javascript.ScriptableObject) r1
            java.lang.Object r0 = r1.equivalentValues(r0)
            java.lang.Object r1 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r0 == r1) goto L63
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r5 = r0.booleanValue()
            return r5
        L63:
            java.lang.Object r7 = toPrimitive(r7)
            goto L0
        L68:
            warnAboutNonJSObject(r7)
        L6b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.eqNumber(double, java.lang.Object):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x008f, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean eqString(java.lang.CharSequence r5, java.lang.Object r6) {
        /*
        L0:
            r0 = 0
            if (r6 == 0) goto L8f
            java.lang.Object r1 = org.mozilla.javascript.Undefined.instance
            if (r6 != r1) goto L9
            goto L8f
        L9:
            boolean r1 = r6 instanceof java.lang.CharSequence
            r2 = 1
            if (r1 == 0) goto L2a
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            int r1 = r5.length()
            int r3 = r6.length()
            if (r1 != r3) goto L29
            java.lang.String r5 = r5.toString()
            java.lang.String r6 = r6.toString()
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L29
            r0 = r2
        L29:
            return r0
        L2a:
            boolean r1 = r6 instanceof java.lang.Number
            if (r1 == 0) goto L42
            java.lang.String r5 = r5.toString()
            double r3 = toNumber(r5)
            java.lang.Number r6 = (java.lang.Number) r6
            double r5 = r6.doubleValue()
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 != 0) goto L41
            r0 = r2
        L41:
            return r0
        L42:
            boolean r1 = r6 instanceof java.lang.Boolean
            if (r1 == 0) goto L61
            java.lang.String r5 = r5.toString()
            double r3 = toNumber(r5)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r5 = r6.booleanValue()
            if (r5 == 0) goto L59
            r5 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            goto L5b
        L59:
            r5 = 0
        L5b:
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 != 0) goto L60
            r0 = r2
        L60:
            return r0
        L61:
            boolean r1 = isSymbol(r6)
            if (r1 == 0) goto L68
            return r0
        L68:
            boolean r1 = r6 instanceof org.mozilla.javascript.Scriptable
            if (r1 == 0) goto L8c
            boolean r0 = r6 instanceof org.mozilla.javascript.ScriptableObject
            if (r0 == 0) goto L86
            r0 = r6
            org.mozilla.javascript.ScriptableObject r0 = (org.mozilla.javascript.ScriptableObject) r0
            java.lang.String r1 = r5.toString()
            java.lang.Object r0 = r0.equivalentValues(r1)
            java.lang.Object r1 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r0 == r1) goto L86
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r5 = r0.booleanValue()
            return r5
        L86:
            java.lang.Object r6 = toPrimitive(r6)
            goto L0
        L8c:
            warnAboutNonJSObject(r6)
        L8f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.eqString(java.lang.CharSequence, java.lang.Object):boolean");
    }

    private static RuntimeException errorWithClassName(String str, Object obj) {
        return Context.reportRuntimeError1(str, obj.getClass().getName());
    }

    public static String escapeAttributeValue(Object obj, Context context) {
        return currentXMLLib(context).escapeAttributeValue(obj);
    }

    public static String escapeString(String str) {
        return escapeString(str, Typography.quote);
    }

    public static String escapeTextValue(Object obj, Context context) {
        return currentXMLLib(context).escapeTextValue(obj);
    }

    public static Object evalSpecial(Context context, Scriptable scriptable, Object obj, Object[] objArr, String str, int i2) {
        if (objArr.length < 1) {
            return Undefined.instance;
        }
        Object obj2 = objArr[0];
        if (!(obj2 instanceof CharSequence)) {
            if (context.hasFeature(11) || context.hasFeature(9)) {
                throw Context.reportRuntimeError0("msg.eval.nonstring.strict");
            }
            Context.reportWarning(getMessage0("msg.eval.nonstring"));
            return obj2;
        }
        if (str == null) {
            int[] iArr = new int[1];
            String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
            if (sourcePositionFromStack != null) {
                i2 = iArr[0];
                str = sourcePositionFromStack;
            } else {
                str = "";
            }
        }
        String strMakeUrlForGeneratedScript = makeUrlForGeneratedScript(true, str, i2);
        ErrorReporter errorReporterForEval = DefaultErrorReporter.forEval(context.getErrorReporter());
        Evaluator evaluatorCreateInterpreter = Context.createInterpreter();
        if (evaluatorCreateInterpreter == null) {
            throw new JavaScriptException("Interpreter not present", str, i2);
        }
        Script scriptCompileString = context.compileString(obj2.toString(), evaluatorCreateInterpreter, errorReporterForEval, strMakeUrlForGeneratedScript, 1, null);
        evaluatorCreateInterpreter.setEvalScriptFlag(scriptCompileString);
        return ((Callable) scriptCompileString).call(context, scriptable, (Scriptable) obj, emptyArgs);
    }

    public static void exitActivationFunction(Context context) {
        NativeCall nativeCall = context.currentActivationCall;
        context.currentActivationCall = nativeCall.parentActivationCall;
        nativeCall.parentActivationCall = null;
    }

    static NativeCall findFunctionActivation(Context context, Function function) {
        for (NativeCall nativeCall = context.currentActivationCall; nativeCall != null; nativeCall = nativeCall.parentActivationCall) {
            if (nativeCall.function == function) {
                return nativeCall;
            }
        }
        return null;
    }

    static Object[] getApplyArguments(Context context, Object obj) {
        if (obj == null || obj == Undefined.instance) {
            return emptyArgs;
        }
        if (obj instanceof Scriptable) {
            Scriptable scriptable = (Scriptable) obj;
            if (isArrayLike(scriptable)) {
                return context.getElements(scriptable);
            }
        }
        if (obj instanceof ScriptableObject) {
            return emptyArgs;
        }
        throw typeError0("msg.arg.isnt.array");
    }

    public static Object[] getArrayElements(Scriptable scriptable) {
        long lengthProperty = NativeArray.getLengthProperty(Context.getContext(), scriptable);
        if (lengthProperty > 2147483647L) {
            throw new IllegalArgumentException();
        }
        int i2 = (int) lengthProperty;
        if (i2 == 0) {
            return emptyArgs;
        }
        Object[] objArr = new Object[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            Object property = ScriptableObject.getProperty(scriptable, i3);
            if (property == Scriptable.NOT_FOUND) {
                property = Undefined.instance;
            }
            objArr[i3] = property;
        }
        return objArr;
    }

    static Callable getCallable(Scriptable scriptable) {
        if (scriptable instanceof Callable) {
            return (Callable) scriptable;
        }
        Object defaultValue = scriptable.getDefaultValue(FunctionClass);
        if (defaultValue instanceof Callable) {
            return (Callable) defaultValue;
        }
        throw notFunctionError(defaultValue, scriptable);
    }

    @Deprecated
    public static Callable getElemFunctionAndThis(Object obj, Object obj2, Context context) {
        return getElemFunctionAndThis(obj, obj2, context, getTopCallScope(context));
    }

    static Function getExistingCtor(Context context, Scriptable scriptable, String str) {
        Object property = ScriptableObject.getProperty(scriptable, str);
        if (property instanceof Function) {
            return (Function) property;
        }
        if (property == Scriptable.NOT_FOUND) {
            throw Context.reportRuntimeError1("msg.ctor.not.found", str);
        }
        throw Context.reportRuntimeError1("msg.not.ctor", str);
    }

    public static ScriptableObject getGlobal(Context context) {
        Class<?> clsClassOrNull = Kit.classOrNull("org.mozilla.javascript.tools.shell.Global");
        if (clsClassOrNull != null) {
            try {
                return (ScriptableObject) clsClassOrNull.getConstructor(ContextClass).newInstance(context);
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception unused) {
            }
        }
        return new ImporterTopLevel(context);
    }

    static Object getIndexObject(String str) {
        long jIndexFromString = indexFromString(str);
        return jIndexFromString >= 0 ? Integer.valueOf((int) jIndexFromString) : str;
    }

    public static ScriptableObject getLibraryScopeOrNull(Scriptable scriptable) {
        return (ScriptableObject) ScriptableObject.getTopScopeValue(scriptable, LIBRARY_SCOPE_KEY);
    }

    public static String getMessage(String str, Object[] objArr) {
        return messageProvider.getMessage(str, objArr);
    }

    public static String getMessage0(String str) {
        return getMessage(str, null);
    }

    public static String getMessage1(String str, Object obj) {
        return getMessage(str, new Object[]{obj});
    }

    public static String getMessage2(String str, Object obj, Object obj2) {
        return getMessage(str, new Object[]{obj, obj2});
    }

    public static String getMessage3(String str, Object obj, Object obj2, Object obj3) {
        return getMessage(str, new Object[]{obj, obj2, obj3});
    }

    public static String getMessage4(String str, Object obj, Object obj2, Object obj3, Object obj4) {
        return getMessage(str, new Object[]{obj, obj2, obj3, obj4});
    }

    public static Callable getNameFunctionAndThis(String str, Context context, Scriptable scriptable) {
        Scriptable parentScope = scriptable.getParentScope();
        if (parentScope != null) {
            return (Callable) nameOrFunction(context, scriptable, parentScope, str, true);
        }
        Object obj = topScopeName(context, scriptable, str);
        if (obj instanceof Callable) {
            storeScriptable(context, scriptable);
            return (Callable) obj;
        }
        if (obj == Scriptable.NOT_FOUND) {
            throw notFoundError(scriptable, str);
        }
        throw notFunctionError(obj, str);
    }

    @Deprecated
    public static Object getObjectElem(Object obj, Object obj2, Context context) {
        return getObjectElem(obj, obj2, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object getObjectIndex(Object obj, double d2, Context context) {
        return getObjectIndex(obj, d2, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object getObjectProp(Object obj, String str, Context context) {
        return getObjectProp(obj, str, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object getObjectPropNoWarn(Object obj, String str, Context context) {
        return getObjectPropNoWarn(obj, str, context, getTopCallScope(context));
    }

    @Deprecated
    public static Callable getPropFunctionAndThis(Object obj, String str, Context context) {
        return getPropFunctionAndThis(obj, str, context, getTopCallScope(context));
    }

    private static Callable getPropFunctionAndThisHelper(Object obj, String str, Context context, Scriptable scriptable) {
        if (scriptable == null) {
            throw undefCallError(obj, str);
        }
        Object property = ScriptableObject.getProperty(scriptable, str);
        if (!(property instanceof Callable)) {
            Object property2 = ScriptableObject.getProperty(scriptable, "__noSuchMethod__");
            if (property2 instanceof Callable) {
                property = new NoSuchMethodShim((Callable) property2, str);
            }
        }
        if (!(property instanceof Callable)) {
            throw notFunctionError(scriptable, property, str);
        }
        storeScriptable(context, scriptable);
        return (Callable) property;
    }

    public static RegExpProxy getRegExpProxy(Context context) {
        return context.getRegExpProxy();
    }

    public static Scriptable getTopCallScope(Context context) {
        Scriptable scriptable = context.topCallScope;
        if (scriptable != null) {
            return scriptable;
        }
        throw new IllegalStateException();
    }

    public static Object getTopLevelProp(Scriptable scriptable, String str) {
        return ScriptableObject.getProperty(ScriptableObject.getTopLevelScope(scriptable), str);
    }

    static String[] getTopPackageNames() {
        return "Dalvik".equals(System.getProperty("java.vm.name")) ? new String[]{"java", "javax", "org", "com", "edu", com.alipay.sdk.m.k.b.f9362k, "android"} : new String[]{"java", "javax", "org", "com", "edu", com.alipay.sdk.m.k.b.f9362k};
    }

    public static Callable getValueFunctionAndThis(Object obj, Context context) {
        if (!(obj instanceof Callable)) {
            throw notFunctionError(obj);
        }
        Callable callable = (Callable) obj;
        Scriptable parentScope = callable instanceof Scriptable ? ((Scriptable) callable).getParentScope() : null;
        if (parentScope == null && (parentScope = context.topCallScope) == null) {
            throw new IllegalStateException();
        }
        if (parentScope.getParentScope() != null && !(parentScope instanceof NativeWith) && (parentScope instanceof NativeCall)) {
            parentScope = ScriptableObject.getTopLevelScope(parentScope);
        }
        storeScriptable(context, parentScope);
        return callable;
    }

    public static boolean hasObjectElem(Scriptable scriptable, Object obj, Context context) {
        if (isSymbol(obj)) {
            return ScriptableObject.hasProperty(scriptable, (Symbol) obj);
        }
        String stringIdOrIndex = toStringIdOrIndex(context, obj);
        return stringIdOrIndex == null ? ScriptableObject.hasProperty(scriptable, lastIndexResult(context)) : ScriptableObject.hasProperty(scriptable, stringIdOrIndex);
    }

    public static boolean hasTopCall(Context context) {
        return context.topCallScope != null;
    }

    public static boolean in(Object obj, Object obj2, Context context) {
        if (obj2 instanceof Scriptable) {
            return hasObjectElem((Scriptable) obj2, obj, context);
        }
        throw typeError0("msg.in.not.object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x005a, code lost:
    
        if (r4 <= (r5 != 0 ? 8 : 7)) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long indexFromString(java.lang.String r12) {
        /*
            int r0 = r12.length()
            r1 = -1
            if (r0 <= 0) goto L68
            r3 = 0
            char r4 = r12.charAt(r3)
            r5 = 45
            r6 = 48
            r7 = 1
            if (r4 != r5) goto L20
            if (r0 <= r7) goto L20
            char r4 = r12.charAt(r7)
            if (r4 != r6) goto L1d
            return r1
        L1d:
            r5 = r7
        L1e:
            r8 = r5
            goto L22
        L20:
            r5 = r3
            goto L1e
        L22:
            int r4 = r4 + (-48)
            if (r4 < 0) goto L68
            r9 = 9
            if (r4 > r9) goto L68
            if (r5 == 0) goto L2f
            r10 = 11
            goto L31
        L2f:
            r10 = 10
        L31:
            if (r0 > r10) goto L68
            int r10 = -r4
            int r8 = r8 + r7
            if (r10 == 0) goto L4b
        L37:
            if (r8 == r0) goto L4b
            char r4 = r12.charAt(r8)
            int r4 = r4 - r6
            if (r4 < 0) goto L4b
            if (r4 > r9) goto L4b
            int r3 = r10 * 10
            int r3 = r3 - r4
            int r8 = r8 + 1
            r11 = r10
            r10 = r3
            r3 = r11
            goto L37
        L4b:
            if (r8 != r0) goto L68
            r12 = -214748364(0xfffffffff3333334, float:-1.4197688E31)
            if (r3 > r12) goto L5c
            if (r3 != r12) goto L68
            if (r5 == 0) goto L59
            r12 = 8
            goto L5a
        L59:
            r12 = 7
        L5a:
            if (r4 > r12) goto L68
        L5c:
            if (r5 == 0) goto L5f
            goto L60
        L5f:
            int r10 = -r10
        L60:
            long r0 = (long) r10
            r2 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = r0 & r2
            return r0
        L68:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.indexFromString(java.lang.String):long");
    }

    public static void initFunction(Context context, Scriptable scriptable, NativeFunction nativeFunction, int i2, boolean z2) {
        if (i2 == 1) {
            String functionName = nativeFunction.getFunctionName();
            if (functionName == null || functionName.length() == 0) {
                return;
            }
            if (z2) {
                scriptable.put(functionName, scriptable, nativeFunction);
                return;
            } else {
                ScriptableObject.defineProperty(scriptable, functionName, nativeFunction, 4);
                return;
            }
        }
        if (i2 != 3) {
            throw Kit.codeBug();
        }
        String functionName2 = nativeFunction.getFunctionName();
        if (functionName2 == null || functionName2.length() == 0) {
            return;
        }
        while (scriptable instanceof NativeWith) {
            scriptable = scriptable.getParentScope();
        }
        scriptable.put(functionName2, scriptable, nativeFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [org.mozilla.javascript.ClassCache] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.lang.Object, org.mozilla.javascript.Scriptable, org.mozilla.javascript.ScriptableObject] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    public static ScriptableObject initSafeStandardObjects(Context context, ScriptableObject scriptableObject, boolean z2) {
        TopLevel nativeObject = scriptableObject;
        if (scriptableObject == null) {
            nativeObject = new NativeObject();
        }
        nativeObject.associateValue(LIBRARY_SCOPE_KEY, nativeObject);
        new ClassCache().associate(nativeObject);
        BaseFunction.init(nativeObject, z2);
        NativeObject.init(nativeObject, z2);
        Scriptable objectPrototype = ScriptableObject.getObjectPrototype(nativeObject);
        ScriptableObject.getClassPrototype(nativeObject, "Function").setPrototype(objectPrototype);
        if (nativeObject.getPrototype() == null) {
            nativeObject.setPrototype(objectPrototype);
        }
        NativeError.init(nativeObject, z2);
        NativeGlobal.init(context, nativeObject, z2);
        NativeArray.init(nativeObject, z2);
        if (context.getOptimizationLevel() > 0) {
            NativeArray.setMaximumInitialCapacity(200000);
        }
        NativeString.init(nativeObject, z2);
        NativeBoolean.init(nativeObject, z2);
        NativeNumber.init(nativeObject, z2);
        NativeDate.init(nativeObject, z2);
        NativeMath.init(nativeObject, z2);
        NativeJSON.init(nativeObject, z2);
        NativeWith.init(nativeObject, z2);
        NativeCall.init(nativeObject, z2);
        NativeScript.init(nativeObject, z2);
        NativeIterator.init(nativeObject, z2);
        NativeArrayIterator.init(nativeObject, z2);
        NativeStringIterator.init(nativeObject, z2);
        boolean z3 = context.hasFeature(6) && context.getE4xImplementationFactory() != null;
        ScriptableObject scriptableObject2 = nativeObject;
        new LazilyLoadedCtor(scriptableObject2, "RegExp", "org.mozilla.javascript.regexp.NativeRegExp", z2, true);
        new LazilyLoadedCtor(scriptableObject2, "Continuation", "org.mozilla.javascript.NativeContinuation", z2, true);
        if (z3) {
            String implementationClassName = context.getE4xImplementationFactory().getImplementationClassName();
            ScriptableObject scriptableObject3 = nativeObject;
            new LazilyLoadedCtor(scriptableObject3, "XML", implementationClassName, z2, true);
            new LazilyLoadedCtor(scriptableObject3, "XMLList", implementationClassName, z2, true);
            new LazilyLoadedCtor(scriptableObject3, "Namespace", implementationClassName, z2, true);
            new LazilyLoadedCtor(scriptableObject3, "QName", implementationClassName, z2, true);
        }
        if ((context.getLanguageVersion() >= 180 && context.hasFeature(14)) || context.getLanguageVersion() >= 200) {
            ScriptableObject scriptableObject4 = nativeObject;
            new LazilyLoadedCtor(scriptableObject4, NativeArrayBuffer.CLASS_NAME, "org.mozilla.javascript.typedarrays.NativeArrayBuffer", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Int8Array", "org.mozilla.javascript.typedarrays.NativeInt8Array", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Uint8Array", "org.mozilla.javascript.typedarrays.NativeUint8Array", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Uint8ClampedArray", "org.mozilla.javascript.typedarrays.NativeUint8ClampedArray", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Int16Array", "org.mozilla.javascript.typedarrays.NativeInt16Array", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Uint16Array", "org.mozilla.javascript.typedarrays.NativeUint16Array", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Int32Array", "org.mozilla.javascript.typedarrays.NativeInt32Array", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Uint32Array", "org.mozilla.javascript.typedarrays.NativeUint32Array", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Float32Array", "org.mozilla.javascript.typedarrays.NativeFloat32Array", z2, true);
            new LazilyLoadedCtor(scriptableObject4, "Float64Array", "org.mozilla.javascript.typedarrays.NativeFloat64Array", z2, true);
            new LazilyLoadedCtor(scriptableObject4, NativeDataView.CLASS_NAME, "org.mozilla.javascript.typedarrays.NativeDataView", z2, true);
        }
        if (context.getLanguageVersion() >= 200) {
            NativeSymbol.init(context, nativeObject, z2);
        }
        if (nativeObject instanceof TopLevel) {
            nativeObject.cacheBuiltins();
        }
        return nativeObject;
    }

    public static void initScript(NativeFunction nativeFunction, Scriptable scriptable, Context context, Scriptable scriptable2, boolean z2) {
        if (context.topCallScope == null) {
            throw new IllegalStateException();
        }
        int paramAndVarCount = nativeFunction.getParamAndVarCount();
        if (paramAndVarCount == 0) {
            return;
        }
        Scriptable parentScope = scriptable2;
        while (parentScope instanceof NativeWith) {
            parentScope = parentScope.getParentScope();
        }
        while (true) {
            int i2 = paramAndVarCount - 1;
            if (paramAndVarCount == 0) {
                return;
            }
            String paramOrVarName = nativeFunction.getParamOrVarName(i2);
            boolean paramOrVarConst = nativeFunction.getParamOrVarConst(i2);
            if (ScriptableObject.hasProperty(scriptable2, paramOrVarName)) {
                ScriptableObject.redefineProperty(scriptable2, paramOrVarName, paramOrVarConst);
            } else if (paramOrVarConst) {
                ScriptableObject.defineConstProperty(parentScope, paramOrVarName);
            } else if (z2) {
                parentScope.put(paramOrVarName, parentScope, Undefined.instance);
            } else {
                ScriptableObject.defineProperty(parentScope, paramOrVarName, Undefined.instance, 4);
            }
            paramAndVarCount = i2;
        }
    }

    public static ScriptableObject initStandardObjects(Context context, ScriptableObject scriptableObject, boolean z2) {
        ScriptableObject scriptableObjectInitSafeStandardObjects = initSafeStandardObjects(context, scriptableObject, z2);
        new LazilyLoadedCtor(scriptableObjectInitSafeStandardObjects, "Packages", "org.mozilla.javascript.NativeJavaTopPackage", z2, true);
        new LazilyLoadedCtor(scriptableObjectInitSafeStandardObjects, "getClass", "org.mozilla.javascript.NativeJavaTopPackage", z2, true);
        new LazilyLoadedCtor(scriptableObjectInitSafeStandardObjects, "JavaAdapter", "org.mozilla.javascript.JavaAdapter", z2, true);
        new LazilyLoadedCtor(scriptableObjectInitSafeStandardObjects, "JavaImporter", "org.mozilla.javascript.ImporterTopLevel", z2, true);
        for (String str : getTopPackageNames()) {
            new LazilyLoadedCtor(scriptableObjectInitSafeStandardObjects, str, "org.mozilla.javascript.NativeJavaTopPackage", z2, true);
        }
        return scriptableObjectInitSafeStandardObjects;
    }

    public static boolean instanceOf(Object obj, Object obj2, Context context) {
        if (!(obj2 instanceof Scriptable)) {
            throw typeError0("msg.instanceof.not.object");
        }
        if (obj instanceof Scriptable) {
            return ((Scriptable) obj2).hasInstance((Scriptable) obj);
        }
        return false;
    }

    private static boolean isArrayLike(Scriptable scriptable) {
        return scriptable != null && ((scriptable instanceof NativeArray) || (scriptable instanceof Arguments) || ScriptableObject.hasProperty(scriptable, SessionDescription.ATTR_LENGTH));
    }

    public static boolean isArrayObject(Object obj) {
        return (obj instanceof NativeArray) || (obj instanceof Arguments);
    }

    static boolean isGeneratedScript(String str) {
        return str.indexOf("(eval)") >= 0 || str.indexOf("(Function)") >= 0;
    }

    public static boolean isJSLineTerminator(int i2) {
        if ((57296 & i2) != 0) {
            return false;
        }
        return i2 == 10 || i2 == 13 || i2 == 8232 || i2 == 8233;
    }

    public static boolean isJSWhitespaceOrLineTerminator(int i2) {
        return isStrWhiteSpaceChar(i2) || isJSLineTerminator(i2);
    }

    public static boolean isNaN(Object obj) {
        if (obj == NaNobj) {
            return true;
        }
        if (obj instanceof Double) {
            Double d2 = (Double) obj;
            return d2.doubleValue() == NaN || Double.isNaN(d2.doubleValue());
        }
        if (!(obj instanceof Float)) {
            return false;
        }
        Float f2 = (Float) obj;
        return ((double) f2.floatValue()) == NaN || Float.isNaN(f2.floatValue());
    }

    public static boolean isPrimitive(Object obj) {
        return obj == null || obj == Undefined.instance || (obj instanceof Number) || (obj instanceof String) || (obj instanceof Boolean);
    }

    public static boolean isRhinoRuntimeType(Class<?> cls) {
        return cls.isPrimitive() ? cls != Character.TYPE : cls == StringClass || cls == BooleanClass || NumberClass.isAssignableFrom(cls) || ScriptableClass.isAssignableFrom(cls);
    }

    static boolean isSpecialProperty(String str) {
        return str.equals("__proto__") || str.equals("__parent__");
    }

    static boolean isStrWhiteSpaceChar(int i2) {
        if (i2 == 32 || i2 == 160 || i2 == 65279 || i2 == 8232 || i2 == 8233) {
            return true;
        }
        switch (i2) {
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            default:
                if (Character.getType(i2) == 12) {
                }
                break;
        }
        return true;
    }

    static boolean isSymbol(Object obj) {
        return ((obj instanceof NativeSymbol) && ((NativeSymbol) obj).isSymbol()) || (obj instanceof SymbolKey);
    }

    static boolean isValidIdentifierName(String str, Context context, boolean z2) {
        int length = str.length();
        if (length == 0 || !Character.isJavaIdentifierStart(str.charAt(0))) {
            return false;
        }
        for (int i2 = 1; i2 != length; i2++) {
            if (!Character.isJavaIdentifierPart(str.charAt(i2))) {
                return false;
            }
        }
        return !TokenStream.isKeyword(str, context.getLanguageVersion(), z2);
    }

    private static boolean isVisible(Context context, Object obj) {
        ClassShutter classShutter = context.getClassShutter();
        return classShutter == null || classShutter.visibleToScripts(obj.getClass().getName());
    }

    public static boolean jsDelegatesTo(Scriptable scriptable, Scriptable scriptable2) {
        for (Scriptable prototype = scriptable.getPrototype(); prototype != null; prototype = prototype.getPrototype()) {
            if (prototype.equals(scriptable2)) {
                return true;
            }
        }
        return false;
    }

    static int lastIndexResult(Context context) {
        return context.scratchIndex;
    }

    public static Scriptable lastStoredScriptable(Context context) {
        Scriptable scriptable = context.scratchScriptable;
        context.scratchScriptable = null;
        return scriptable;
    }

    public static long lastUint32Result(Context context) {
        long j2 = context.scratchUint32;
        if ((j2 >>> 32) == 0) {
            return j2;
        }
        throw new IllegalStateException();
    }

    public static Scriptable leaveDotQuery(Scriptable scriptable) {
        return ((NativeWith) scriptable).getParentScope();
    }

    public static Scriptable leaveWith(Scriptable scriptable) {
        return ((NativeWith) scriptable).getParentScope();
    }

    static String makeUrlForGeneratedScript(boolean z2, String str, int i2) {
        if (z2) {
            return str + '#' + i2 + "(eval)";
        }
        return str + '#' + i2 + "(Function)";
    }

    public static Ref memberRef(Object obj, Object obj2, Context context, int i2) {
        if (obj instanceof XMLObject) {
            return ((XMLObject) obj).memberRef(context, obj2, i2);
        }
        throw notXmlError(obj);
    }

    public static Object name(Context context, Scriptable scriptable, String str) {
        Scriptable parentScope = scriptable.getParentScope();
        if (parentScope != null) {
            return nameOrFunction(context, scriptable, parentScope, str, false);
        }
        Object obj = topScopeName(context, scriptable, str);
        if (obj != Scriptable.NOT_FOUND) {
            return obj;
        }
        throw notFoundError(scriptable, str);
    }

    @Deprecated
    public static Object nameIncrDecr(Scriptable scriptable, String str, int i2) {
        return nameIncrDecr(scriptable, str, Context.getContext(), i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0027, code lost:
    
        r6 = r2;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0075 A[LOOP:0: B:3:0x0002->B:42:0x0075, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x004e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.Object nameOrFunction(org.mozilla.javascript.Context r5, org.mozilla.javascript.Scriptable r6, org.mozilla.javascript.Scriptable r7, java.lang.String r8, boolean r9) {
        /*
            r0 = 0
            r1 = r6
        L2:
            boolean r2 = r1 instanceof org.mozilla.javascript.NativeWith
            if (r2 == 0) goto L29
            org.mozilla.javascript.Scriptable r1 = r1.getPrototype()
            boolean r2 = r1 instanceof org.mozilla.javascript.xml.XMLObject
            if (r2 == 0) goto L1f
            org.mozilla.javascript.xml.XMLObject r1 = (org.mozilla.javascript.xml.XMLObject) r1
            boolean r2 = r1.has(r8, r1)
            if (r2 == 0) goto L1b
            java.lang.Object r6 = r1.get(r8, r1)
            goto L65
        L1b:
            if (r0 != 0) goto L48
            r0 = r1
            goto L48
        L1f:
            java.lang.Object r2 = org.mozilla.javascript.ScriptableObject.getProperty(r1, r8)
            java.lang.Object r3 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r2 == r3) goto L48
        L27:
            r6 = r2
            goto L65
        L29:
            boolean r2 = r1 instanceof org.mozilla.javascript.NativeCall
            if (r2 == 0) goto L3f
            java.lang.Object r1 = r1.get(r8, r1)
            java.lang.Object r2 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r1 == r2) goto L48
            if (r9 == 0) goto L3b
            org.mozilla.javascript.Scriptable r6 = org.mozilla.javascript.ScriptableObject.getTopLevelScope(r7)
        L3b:
            r4 = r1
            r1 = r6
            r6 = r4
            goto L65
        L3f:
            java.lang.Object r2 = org.mozilla.javascript.ScriptableObject.getProperty(r1, r8)
            java.lang.Object r3 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r2 == r3) goto L48
            goto L27
        L48:
            org.mozilla.javascript.Scriptable r1 = r7.getParentScope()
            if (r1 != 0) goto L75
            java.lang.Object r6 = topScopeName(r5, r7, r8)
            java.lang.Object r1 = org.mozilla.javascript.Scriptable.NOT_FOUND
            if (r6 != r1) goto L64
            if (r0 == 0) goto L5f
            if (r9 != 0) goto L5f
            java.lang.Object r6 = r0.get(r8, r0)
            goto L64
        L5f:
            java.lang.RuntimeException r5 = notFoundError(r7, r8)
            throw r5
        L64:
            r1 = r7
        L65:
            if (r9 == 0) goto L74
            boolean r7 = r6 instanceof org.mozilla.javascript.Callable
            if (r7 == 0) goto L6f
            storeScriptable(r5, r1)
            goto L74
        L6f:
            java.lang.RuntimeException r5 = notFunctionError(r6, r8)
            throw r5
        L74:
            return r6
        L75:
            r4 = r1
            r1 = r7
            r7 = r4
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.nameOrFunction(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.String, boolean):java.lang.Object");
    }

    public static Ref nameRef(Object obj, Context context, Scriptable scriptable, int i2) {
        return currentXMLLib(context).nameRef(context, obj, scriptable, i2);
    }

    public static Scriptable newArrayLiteral(Object[] objArr, int[] iArr, Context context, Scriptable scriptable) {
        int length = objArr.length;
        int i2 = 0;
        int length2 = iArr != null ? iArr.length : 0;
        int i3 = length + length2;
        if (i3 <= 1 || length2 * 2 >= i3) {
            Scriptable scriptableNewArray = context.newArray(scriptable, i3);
            int i4 = 0;
            int i5 = 0;
            while (i2 != i3) {
                if (i4 == length2 || iArr[i4] != i2) {
                    scriptableNewArray.put(i2, scriptableNewArray, objArr[i5]);
                    i5++;
                } else {
                    i4++;
                }
                i2++;
            }
            return scriptableNewArray;
        }
        if (length2 != 0) {
            Object[] objArr2 = new Object[i3];
            int i6 = 0;
            int i7 = 0;
            while (i2 != i3) {
                if (i6 == length2 || iArr[i6] != i2) {
                    objArr2[i2] = objArr[i7];
                    i7++;
                } else {
                    objArr2[i2] = Scriptable.NOT_FOUND;
                    i6++;
                }
                i2++;
            }
            objArr = objArr2;
        }
        return context.newArray(scriptable, objArr);
    }

    public static Scriptable newBuiltinObject(Context context, Scriptable scriptable, TopLevel.Builtins builtins, Object[] objArr) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        Function builtinCtor = TopLevel.getBuiltinCtor(context, topLevelScope, builtins);
        if (objArr == null) {
            objArr = emptyArgs;
        }
        return builtinCtor.construct(context, topLevelScope, objArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.mozilla.javascript.Scriptable newCatchScope(java.lang.Throwable r11, org.mozilla.javascript.Scriptable r12, java.lang.String r13, org.mozilla.javascript.Context r14, org.mozilla.javascript.Scriptable r15) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.newCatchScope(java.lang.Throwable, org.mozilla.javascript.Scriptable, java.lang.String, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable):org.mozilla.javascript.Scriptable");
    }

    static Scriptable newNativeError(Context context, Scriptable scriptable, TopLevel.NativeErrors nativeErrors, Object[] objArr) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        Function nativeErrorCtor = TopLevel.getNativeErrorCtor(context, topLevelScope, nativeErrors);
        if (objArr == null) {
            objArr = emptyArgs;
        }
        return nativeErrorCtor.construct(context, topLevelScope, objArr);
    }

    public static Scriptable newObject(Context context, Scriptable scriptable, String str, Object[] objArr) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        Function existingCtor = getExistingCtor(context, topLevelScope, str);
        if (objArr == null) {
            objArr = emptyArgs;
        }
        return existingCtor.construct(context, topLevelScope, objArr);
    }

    @Deprecated
    public static Scriptable newObjectLiteral(Object[] objArr, Object[] objArr2, Context context, Scriptable scriptable) {
        return newObjectLiteral(objArr, objArr2, null, context, scriptable);
    }

    public static Object newSpecial(Context context, Object obj, Object[] objArr, Scriptable scriptable, int i2) {
        if (i2 == 1) {
            if (NativeGlobal.isEvalFunction(obj)) {
                throw typeError1("msg.not.ctor", "eval");
            }
        } else {
            if (i2 != 2) {
                throw Kit.codeBug();
            }
            if (NativeWith.isWithFunction(obj)) {
                return NativeWith.newWithSpecial(context, scriptable, objArr);
            }
        }
        return newObject(obj, context, scriptable, objArr);
    }

    public static RuntimeException notFoundError(Scriptable scriptable, String str) {
        throw constructError("ReferenceError", getMessage1("msg.is.not.defined", str));
    }

    public static RuntimeException notFunctionError(Object obj) {
        return notFunctionError(obj, obj);
    }

    private static RuntimeException notXmlError(Object obj) {
        throw typeError1("msg.isnt.xml.object", toString(obj));
    }

    public static String numberToString(double d2, int i2) {
        if (i2 < 2 || i2 > 36) {
            throw Context.reportRuntimeError1("msg.bad.radix", Integer.toString(i2));
        }
        if (d2 != d2) {
            return "NaN";
        }
        if (d2 == Double.POSITIVE_INFINITY) {
            return "Infinity";
        }
        if (d2 == Double.NEGATIVE_INFINITY) {
            return "-Infinity";
        }
        if (d2 == 0.0d) {
            return "0";
        }
        if (i2 != 10) {
            return DToA.JS_dtobasestr(i2, d2);
        }
        String strNumberToString = FastDtoa.numberToString(d2);
        if (strNumberToString != null) {
            return strNumberToString;
        }
        StringBuilder sb = new StringBuilder();
        DToA.JS_dtostr(sb, 0, 0, d2);
        return sb.toString();
    }

    public static Object[] padArguments(Object[] objArr, int i2) {
        if (i2 < objArr.length) {
            return objArr;
        }
        Object[] objArr2 = new Object[i2];
        int i3 = 0;
        while (i3 < objArr.length) {
            objArr2[i3] = objArr[i3];
            i3++;
        }
        while (i3 < i2) {
            objArr2[i3] = Undefined.instance;
            i3++;
        }
        return objArr2;
    }

    @Deprecated
    public static Object propIncrDecr(Object obj, String str, Context context, int i2) {
        return propIncrDecr(obj, str, context, getTopCallScope(context), i2);
    }

    public static EcmaError rangeError(String str) {
        return constructError("RangeError", str);
    }

    public static Object refDel(Ref ref, Context context) {
        return wrapBoolean(ref.delete(context));
    }

    public static Object refGet(Ref ref, Context context) {
        return ref.get(context);
    }

    @Deprecated
    public static Object refIncrDecr(Ref ref, Context context, int i2) {
        return refIncrDecr(ref, context, getTopCallScope(context), i2);
    }

    @Deprecated
    public static Object refSet(Ref ref, Object obj, Context context) {
        return refSet(ref, obj, context, getTopCallScope(context));
    }

    public static boolean same(Object obj, Object obj2) {
        if (!typeof(obj).equals(typeof(obj2))) {
            return false;
        }
        if (!(obj instanceof Number)) {
            return eq(obj, obj2);
        }
        if (isNaN(obj) && isNaN(obj2)) {
            return true;
        }
        return obj.equals(obj2);
    }

    public static Object searchDefaultNamespace(Context context) {
        Scriptable topCallScope = context.currentActivationCall;
        if (topCallScope == null) {
            topCallScope = getTopCallScope(context);
        }
        while (true) {
            Scriptable parentScope = topCallScope.getParentScope();
            if (parentScope == null) {
                Object property = ScriptableObject.getProperty(topCallScope, DEFAULT_NS_TAG);
                if (property == Scriptable.NOT_FOUND) {
                    return null;
                }
                return property;
            }
            Object obj = topCallScope.get(DEFAULT_NS_TAG, topCallScope);
            if (obj != Scriptable.NOT_FOUND) {
                return obj;
            }
            topCallScope = parentScope;
        }
    }

    public static void setBuiltinProtoAndParent(ScriptableObject scriptableObject, Scriptable scriptable, TopLevel.Builtins builtins) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        scriptableObject.setParentScope(topLevelScope);
        scriptableObject.setPrototype(TopLevel.getBuiltinPrototype(topLevelScope, builtins));
    }

    public static Object setConst(Scriptable scriptable, Object obj, Context context, String str) {
        if (scriptable instanceof XMLObject) {
            scriptable.put(str, scriptable, obj);
        } else {
            ScriptableObject.putConstProperty(scriptable, str, obj);
        }
        return obj;
    }

    public static Object setDefaultNamespace(Object obj, Context context) {
        Scriptable topCallScope = context.currentActivationCall;
        if (topCallScope == null) {
            topCallScope = getTopCallScope(context);
        }
        Object defaultXmlNamespace = currentXMLLib(context).toDefaultXmlNamespace(context, obj);
        if (topCallScope.has(DEFAULT_NS_TAG, topCallScope)) {
            topCallScope.put(DEFAULT_NS_TAG, topCallScope, defaultXmlNamespace);
        } else {
            ScriptableObject.defineProperty(topCallScope, DEFAULT_NS_TAG, defaultXmlNamespace, 6);
        }
        return Undefined.instance;
    }

    public static void setEnumNumbers(Object obj, boolean z2) {
        ((IdEnumeration) obj).enumNumbers = z2;
    }

    public static void setFunctionProtoAndParent(BaseFunction baseFunction, Scriptable scriptable) {
        baseFunction.setParentScope(scriptable);
        baseFunction.setPrototype(ScriptableObject.getFunctionPrototype(scriptable));
    }

    public static Object setName(Scriptable scriptable, Object obj, Context context, Scriptable scriptable2, String str) {
        if (scriptable != null) {
            ScriptableObject.putProperty(scriptable, str, obj);
        } else {
            if (context.hasFeature(11) || context.hasFeature(8)) {
                Context.reportWarning(getMessage1("msg.assn.create.strict", str));
            }
            Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable2);
            if (context.useDynamicScope) {
                topLevelScope = checkDynamicScope(context.topCallScope, topLevelScope);
            }
            topLevelScope.put(str, topLevelScope, obj);
        }
        return obj;
    }

    @Deprecated
    public static Object setObjectElem(Object obj, Object obj2, Object obj3, Context context) {
        return setObjectElem(obj, obj2, obj3, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object setObjectIndex(Object obj, double d2, Object obj2, Context context) {
        return setObjectIndex(obj, d2, obj2, context, getTopCallScope(context));
    }

    @Deprecated
    public static Object setObjectProp(Object obj, String str, Object obj2, Context context) {
        return setObjectProp(obj, str, obj2, context, getTopCallScope(context));
    }

    public static void setObjectProtoAndParent(ScriptableObject scriptableObject, Scriptable scriptable) {
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        scriptableObject.setParentScope(topLevelScope);
        scriptableObject.setPrototype(ScriptableObject.getClassPrototype(topLevelScope, scriptableObject.getClassName()));
    }

    public static void setRegExpProxy(Context context, RegExpProxy regExpProxy) {
        if (regExpProxy == null) {
            throw new IllegalArgumentException();
        }
        context.regExpProxy = regExpProxy;
    }

    public static boolean shallowEq(Object obj, Object obj2) {
        if (obj == obj2) {
            if (!(obj instanceof Number)) {
                return true;
            }
            double dDoubleValue = ((Number) obj).doubleValue();
            return dDoubleValue == dDoubleValue;
        }
        if (obj == null || obj == Undefined.instance || obj == Undefined.SCRIPTABLE_UNDEFINED) {
            Object obj3 = Undefined.instance;
            return (obj == obj3 && obj2 == Undefined.SCRIPTABLE_UNDEFINED) || (obj == Undefined.SCRIPTABLE_UNDEFINED && obj2 == obj3);
        }
        if (obj instanceof Number) {
            return (obj2 instanceof Number) && ((Number) obj).doubleValue() == ((Number) obj2).doubleValue();
        }
        if (obj instanceof CharSequence) {
            if (obj2 instanceof CharSequence) {
                return obj.toString().equals(obj2.toString());
            }
            return false;
        }
        if (obj instanceof Boolean) {
            if (obj2 instanceof Boolean) {
                return obj.equals(obj2);
            }
            return false;
        }
        if (obj instanceof Scriptable) {
            return (obj instanceof Wrapper) && (obj2 instanceof Wrapper) && ((Wrapper) obj).unwrap() == ((Wrapper) obj2).unwrap();
        }
        warnAboutNonJSObject(obj);
        return obj == obj2;
    }

    @Deprecated
    public static Ref specialRef(Object obj, String str, Context context) {
        return specialRef(obj, str, context, getTopCallScope(context));
    }

    private static void storeIndexResult(Context context, int i2) {
        context.scratchIndex = i2;
    }

    private static void storeScriptable(Context context, Scriptable scriptable) {
        if (context.scratchScriptable != null) {
            throw new IllegalStateException();
        }
        context.scratchScriptable = scriptable;
    }

    public static void storeUint32Result(Context context, long j2) {
        if ((j2 >>> 32) != 0) {
            throw new IllegalArgumentException();
        }
        context.scratchUint32 = j2;
    }

    public static Object strictSetName(Scriptable scriptable, Object obj, Context context, Scriptable scriptable2, String str) {
        if (scriptable != null) {
            ScriptableObject.putProperty(scriptable, str, obj);
            return obj;
        }
        throw constructError("ReferenceError", "Assignment to undefined \"" + str + "\" in strict mode");
    }

    static double stringPrefixToNumber(String str, int i2, int i3) {
        return stringToNumber(str, i2, str.length() - 1, i3, true);
    }

    static double stringToNumber(String str, int i2, int i3, int i4) {
        return stringToNumber(str, i2, i3, i4, false);
    }

    public static long testUint32String(String str) {
        int length = str.length();
        if (1 <= length && length <= 10) {
            int iCharAt = str.charAt(0) - '0';
            if (iCharAt == 0) {
                return length == 1 ? 0L : -1L;
            }
            if (1 <= iCharAt && iCharAt <= 9) {
                long j2 = iCharAt;
                for (int i2 = 1; i2 != length; i2++) {
                    int iCharAt2 = str.charAt(i2) - '0';
                    if (iCharAt2 < 0 || iCharAt2 > 9) {
                        return -1L;
                    }
                    j2 = (j2 * 10) + iCharAt2;
                }
                if ((j2 >>> 32) == 0) {
                    return j2;
                }
            }
        }
        return -1L;
    }

    public static JavaScriptException throwCustomError(Context context, Scriptable scriptable, String str, String str2) {
        int[] iArr = {0};
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        return new JavaScriptException(context.newObject(scriptable, str, new Object[]{str2, sourcePositionFromStack, Integer.valueOf(iArr[0])}), sourcePositionFromStack, iArr[0]);
    }

    public static JavaScriptException throwError(Context context, Scriptable scriptable, String str) {
        int[] iArr = {0};
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        return new JavaScriptException(newBuiltinObject(context, scriptable, TopLevel.Builtins.Error, new Object[]{str, sourcePositionFromStack, Integer.valueOf(iArr[0])}), sourcePositionFromStack, iArr[0]);
    }

    public static boolean toBoolean(Object obj) {
        while (!(obj instanceof Boolean)) {
            if (obj == null || obj == Undefined.instance) {
                return false;
            }
            if (obj instanceof CharSequence) {
                return ((CharSequence) obj).length() != 0;
            }
            if (obj instanceof Number) {
                double dDoubleValue = ((Number) obj).doubleValue();
                return dDoubleValue == dDoubleValue && dDoubleValue != 0.0d;
            }
            if (!(obj instanceof Scriptable)) {
                warnAboutNonJSObject(obj);
                return true;
            }
            if ((obj instanceof ScriptableObject) && ((ScriptableObject) obj).avoidObjectDetection()) {
                return false;
            }
            if (Context.getContext().isVersionECMA1()) {
                return true;
            }
            obj = ((Scriptable) obj).getDefaultValue(BooleanClass);
            if ((obj instanceof Scriptable) && !isSymbol(obj)) {
                throw errorWithClassName("msg.primitive.expected", obj);
            }
        }
        return ((Boolean) obj).booleanValue();
    }

    public static CharSequence toCharSequence(Object obj) {
        return obj instanceof NativeString ? ((NativeString) obj).toCharSequence() : obj instanceof CharSequence ? (CharSequence) obj : toString(obj);
    }

    public static int toInt32(Object obj) {
        return obj instanceof Integer ? ((Integer) obj).intValue() : toInt32(toNumber(obj));
    }

    public static double toInteger(Object obj) {
        return toInteger(toNumber(obj));
    }

    public static Scriptable toIterator(Context context, Scriptable scriptable, Scriptable scriptable2, boolean z2) {
        if (!ScriptableObject.hasProperty(scriptable2, NativeIterator.ITERATOR_PROPERTY_NAME)) {
            return null;
        }
        Object property = ScriptableObject.getProperty(scriptable2, NativeIterator.ITERATOR_PROPERTY_NAME);
        if (!(property instanceof Callable)) {
            throw typeError0("msg.invalid.iterator");
        }
        Object objCall = ((Callable) property).call(context, scriptable, scriptable2, new Object[]{z2 ? Boolean.TRUE : Boolean.FALSE});
        if (objCall instanceof Scriptable) {
            return (Scriptable) objCall;
        }
        throw typeError0("msg.iterator.primitive");
    }

    public static double toNumber(Object obj) {
        while (!(obj instanceof Number)) {
            if (obj == null) {
                return 0.0d;
            }
            if (obj == Undefined.instance) {
                return NaN;
            }
            if (obj instanceof String) {
                return toNumber((String) obj);
            }
            if (obj instanceof CharSequence) {
                return toNumber(obj.toString());
            }
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue() ? 1.0d : 0.0d;
            }
            if (obj instanceof Symbol) {
                throw typeError0("msg.not.a.number");
            }
            if (!(obj instanceof Scriptable)) {
                warnAboutNonJSObject(obj);
                return NaN;
            }
            obj = ((Scriptable) obj).getDefaultValue(NumberClass);
            if ((obj instanceof Scriptable) && !isSymbol(obj)) {
                throw errorWithClassName("msg.primitive.expected", obj);
            }
        }
        return ((Number) obj).doubleValue();
    }

    public static Scriptable toObject(Scriptable scriptable, Object obj) {
        return obj instanceof Scriptable ? (Scriptable) obj : toObject(Context.getContext(), scriptable, obj);
    }

    @Deprecated
    public static Scriptable toObjectOrNull(Context context, Object obj) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        if (obj == null || obj == Undefined.instance) {
            return null;
        }
        return toObject(context, getTopCallScope(context), obj);
    }

    public static Object toPrimitive(Object obj) {
        return toPrimitive(obj, null);
    }

    public static String toString(Object obj) {
        while (obj != null) {
            if (obj == Undefined.instance || obj == Undefined.SCRIPTABLE_UNDEFINED) {
                return "undefined";
            }
            if (obj instanceof String) {
                return (String) obj;
            }
            if (obj instanceof CharSequence) {
                return obj.toString();
            }
            if (obj instanceof Number) {
                return numberToString(((Number) obj).doubleValue(), 10);
            }
            if (obj instanceof Symbol) {
                throw typeError0("msg.not.a.string");
            }
            if (!(obj instanceof Scriptable)) {
                return obj.toString();
            }
            obj = ((Scriptable) obj).getDefaultValue(StringClass);
            if ((obj instanceof Scriptable) && !isSymbol(obj)) {
                throw errorWithClassName("msg.primitive.expected", obj);
            }
        }
        return TmpConstant.GROUP_ROLE_UNKNOWN;
    }

    static String toStringIdOrIndex(Context context, Object obj) {
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            int i2 = (int) dDoubleValue;
            if (i2 != dDoubleValue) {
                return toString(obj);
            }
            storeIndexResult(context, i2);
            return null;
        }
        String string = obj instanceof String ? (String) obj : toString(obj);
        long jIndexFromString = indexFromString(string);
        if (jIndexFromString < 0) {
            return string;
        }
        storeIndexResult(context, (int) jIndexFromString);
        return null;
    }

    public static char toUint16(Object obj) {
        return (char) DoubleConversion.doubleToInt32(toNumber(obj));
    }

    public static long toUint32(double d2) {
        return DoubleConversion.doubleToInt32(d2) & 4294967295L;
    }

    private static Object topScopeName(Context context, Scriptable scriptable, String str) {
        if (context.useDynamicScope) {
            scriptable = checkDynamicScope(context.topCallScope, scriptable);
        }
        return ScriptableObject.getProperty(scriptable, str);
    }

    public static EcmaError typeError(String str) {
        return constructError("TypeError", str);
    }

    public static EcmaError typeError0(String str) {
        return typeError(getMessage0(str));
    }

    public static EcmaError typeError1(String str, Object obj) {
        return typeError(getMessage1(str, obj));
    }

    public static EcmaError typeError2(String str, Object obj, Object obj2) {
        return typeError(getMessage2(str, obj, obj2));
    }

    public static EcmaError typeError3(String str, String str2, String str3, String str4) {
        return typeError(getMessage3(str, str2, str3, str4));
    }

    @Deprecated
    public static BaseFunction typeErrorThrower() {
        return typeErrorThrower(Context.getCurrentContext());
    }

    public static String typeof(Object obj) {
        if (obj == null) {
            return "object";
        }
        if (obj == Undefined.instance) {
            return "undefined";
        }
        if (obj instanceof ScriptableObject) {
            return ((ScriptableObject) obj).getTypeOf();
        }
        if (obj instanceof Scriptable) {
            return obj instanceof Callable ? "function" : "object";
        }
        if (obj instanceof CharSequence) {
            return "string";
        }
        if (obj instanceof Number) {
            return "number";
        }
        if (obj instanceof Boolean) {
            return TypedValues.Custom.S_BOOLEAN;
        }
        throw errorWithClassName("msg.invalid.type", obj);
    }

    public static String typeofName(Scriptable scriptable, String str) {
        Context context = Context.getContext();
        Scriptable scriptableBind = bind(context, scriptable, str);
        return scriptableBind == null ? "undefined" : typeof(getObjectProp(scriptableBind, str, context));
    }

    public static RuntimeException undefCallError(Object obj, Object obj2) {
        return typeError2("msg.undef.method.call", toString(obj), toString(obj2));
    }

    private static RuntimeException undefDeleteError(Object obj, Object obj2) {
        throw typeError2("msg.undef.prop.delete", toString(obj), toString(obj2));
    }

    public static RuntimeException undefReadError(Object obj, Object obj2) {
        return typeError2("msg.undef.prop.read", toString(obj), toString(obj2));
    }

    public static RuntimeException undefWriteError(Object obj, Object obj2, Object obj3) {
        return typeError3("msg.undef.prop.write", toString(obj), toString(obj2), toString(obj3));
    }

    static String uneval(Context context, Scriptable scriptable, Object obj) {
        if (obj == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (obj == Undefined.instance) {
            return "undefined";
        }
        if (obj instanceof CharSequence) {
            String strEscapeString = escapeString(obj.toString());
            StringBuilder sb = new StringBuilder(strEscapeString.length() + 2);
            sb.append(Typography.quote);
            sb.append(strEscapeString);
            sb.append(Typography.quote);
            return sb.toString();
        }
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            return (dDoubleValue != 0.0d || 1.0d / dDoubleValue >= 0.0d) ? toString(dDoubleValue) : "-0";
        }
        if (obj instanceof Boolean) {
            return toString(obj);
        }
        if (!(obj instanceof Scriptable)) {
            warnAboutNonJSObject(obj);
            return obj.toString();
        }
        Scriptable scriptable2 = (Scriptable) obj;
        if (ScriptableObject.hasProperty(scriptable2, "toSource")) {
            Object property = ScriptableObject.getProperty(scriptable2, "toSource");
            if (property instanceof Function) {
                return toString(((Function) property).call(context, scriptable, scriptable2, emptyArgs));
            }
        }
        return toString(obj);
    }

    public static Object updateDotQuery(boolean z2, Scriptable scriptable) {
        return ((NativeWith) scriptable).updateDotQuery(z2);
    }

    private static void warnAboutNonJSObject(Object obj) {
        if ("true".equals(getMessage0("params.omit.non.js.object.warning"))) {
            return;
        }
        String message2 = getMessage2("msg.non.js.object.warning", obj, obj.getClass().getName());
        Context.reportWarning(message2);
        System.err.println(message2);
    }

    public static Boolean wrapBoolean(boolean z2) {
        return z2 ? Boolean.TRUE : Boolean.FALSE;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.mozilla.javascript.Scriptable wrapException(java.lang.Throwable r11, org.mozilla.javascript.Scriptable r12, org.mozilla.javascript.Context r13) {
        /*
            boolean r0 = r11 instanceof org.mozilla.javascript.EcmaError
            r1 = 0
            if (r0 == 0) goto L12
            org.mozilla.javascript.EcmaError r11 = (org.mozilla.javascript.EcmaError) r11
            java.lang.String r0 = r11.getName()
            java.lang.String r2 = r11.getErrorMessage()
        Lf:
            r3 = r2
        L10:
            r2 = r1
            goto L64
        L12:
            boolean r0 = r11 instanceof org.mozilla.javascript.WrappedException
            java.lang.String r2 = "JavaException"
            if (r0 == 0) goto L42
            org.mozilla.javascript.WrappedException r11 = (org.mozilla.javascript.WrappedException) r11
            java.lang.Throwable r0 = r11.getWrappedException()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r0.getClass()
            java.lang.String r4 = r4.getName()
            r3.append(r4)
            java.lang.String r4 = ": "
            r3.append(r4)
            java.lang.String r4 = r0.getMessage()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r10 = r2
            r2 = r0
            r0 = r10
            goto L64
        L42:
            boolean r0 = r11 instanceof org.mozilla.javascript.EvaluatorException
            if (r0 == 0) goto L4f
            org.mozilla.javascript.EvaluatorException r11 = (org.mozilla.javascript.EvaluatorException) r11
            java.lang.String r2 = r11.getMessage()
            java.lang.String r0 = "InternalError"
            goto Lf
        L4f:
            r0 = 13
            boolean r0 = r13.hasFeature(r0)
            if (r0 == 0) goto Lc6
            org.mozilla.javascript.WrappedException r0 = new org.mozilla.javascript.WrappedException
            r0.<init>(r11)
            java.lang.String r11 = r11.toString()
            r3 = r11
            r11 = r0
            r0 = r2
            goto L10
        L64:
            java.lang.String r4 = r11.sourceName()
            if (r4 != 0) goto L6c
            java.lang.String r4 = ""
        L6c:
            int r5 = r11.lineNumber()
            r6 = 2
            r7 = 1
            r8 = 0
            if (r5 <= 0) goto L83
            r9 = 3
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r9[r8] = r3
            r9[r7] = r4
            java.lang.Integer r3 = java.lang.Integer.valueOf(r5)
            r9[r6] = r3
            goto L89
        L83:
            java.lang.Object[] r9 = new java.lang.Object[r6]
            r9[r8] = r3
            r9[r7] = r4
        L89:
            org.mozilla.javascript.Scriptable r3 = r13.newObject(r12, r0, r9)
            java.lang.String r4 = "name"
            org.mozilla.javascript.ScriptableObject.putProperty(r3, r4, r0)
            boolean r0 = r3 instanceof org.mozilla.javascript.NativeError
            if (r0 == 0) goto L9c
            r0 = r3
            org.mozilla.javascript.NativeError r0 = (org.mozilla.javascript.NativeError) r0
            r0.setStackProvider(r11)
        L9c:
            r0 = 7
            if (r2 == 0) goto Lb2
            boolean r4 = isVisible(r13, r2)
            if (r4 == 0) goto Lb2
            org.mozilla.javascript.WrapFactory r4 = r13.getWrapFactory()
            java.lang.Object r2 = r4.wrap(r13, r12, r2, r1)
            java.lang.String r4 = "javaException"
            org.mozilla.javascript.ScriptableObject.defineProperty(r3, r4, r2, r0)
        Lb2:
            boolean r2 = isVisible(r13, r11)
            if (r2 == 0) goto Lc5
            org.mozilla.javascript.WrapFactory r2 = r13.getWrapFactory()
            java.lang.Object r11 = r2.wrap(r13, r12, r11, r1)
            java.lang.String r12 = "rhinoException"
            org.mozilla.javascript.ScriptableObject.defineProperty(r3, r12, r11, r0)
        Lc5:
            return r3
        Lc6:
            java.lang.RuntimeException r11 = org.mozilla.javascript.Kit.codeBug()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.wrapException(java.lang.Throwable, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Context):org.mozilla.javascript.Scriptable");
    }

    public static Integer wrapInt(int i2) {
        return Integer.valueOf(i2);
    }

    public static Number wrapNumber(double d2) {
        return d2 != d2 ? NaNobj : new Double(d2);
    }

    public static Scriptable wrapRegExp(Context context, Scriptable scriptable, Object obj) {
        return context.getRegExpProxy().wrapRegExp(context, scriptable, obj);
    }

    public static Scriptable createFunctionActivation(NativeFunction nativeFunction, Scriptable scriptable, Object[] objArr, boolean z2) {
        return new NativeCall(nativeFunction, scriptable, objArr, false, z2);
    }

    @Deprecated
    public static Object delete(Object obj, Object obj2, Context context, boolean z2) {
        return delete(obj, obj2, context, getTopCallScope(context), z2);
    }

    public static Object doTopCall(Callable callable, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr, boolean z2) {
        if (scriptable == null) {
            throw new IllegalArgumentException();
        }
        if (context.topCallScope != null) {
            throw new IllegalStateException();
        }
        context.topCallScope = ScriptableObject.getTopLevelScope(scriptable);
        context.useDynamicScope = context.hasFeature(7);
        boolean z3 = context.isTopLevelStrict;
        context.isTopLevelStrict = z2;
        try {
            Object objDoTopCall = context.getFactory().doTopCall(callable, context, scriptable, scriptable2, objArr);
            context.topCallScope = null;
            context.cachedXMLLib = null;
            context.isTopLevelStrict = z3;
            if (context.currentActivationCall == null) {
                return objDoTopCall;
            }
            throw new IllegalStateException();
        } catch (Throwable th) {
            context.topCallScope = null;
            context.cachedXMLLib = null;
            context.isTopLevelStrict = z3;
            if (context.currentActivationCall != null) {
                throw new IllegalStateException();
            }
            throw th;
        }
    }

    public static Object elemIncrDecr(Object obj, Object obj2, Context context, Scriptable scriptable, int i2) {
        double number;
        Object objectElem = getObjectElem(obj, obj2, context, scriptable);
        boolean z2 = (i2 & 2) != 0;
        if (objectElem instanceof Number) {
            number = ((Number) objectElem).doubleValue();
        } else {
            number = toNumber(objectElem);
            if (z2) {
                objectElem = wrapNumber(number);
            }
        }
        Number numberWrapNumber = wrapNumber((i2 & 1) == 0 ? number + 1.0d : number - 1.0d);
        setObjectElem(obj, obj2, numberWrapNumber, context, scriptable);
        return z2 ? objectElem : numberWrapNumber;
    }

    @Deprecated
    public static Object enumInit(Object obj, Context context, int i2) {
        return enumInit(obj, context, getTopCallScope(context), i2);
    }

    public static String escapeString(String str, char c2) throws RuntimeException {
        int i2;
        if (c2 != '\"' && c2 != '\'') {
            Kit.codeBug();
        }
        int length = str.length();
        StringBuilder sb = null;
        for (int i3 = 0; i3 != length; i3++) {
            char cCharAt = str.charAt(i3);
            int i4 = 32;
            if (' ' > cCharAt || cCharAt > '~' || cCharAt == c2 || cCharAt == '\\') {
                if (sb == null) {
                    sb = new StringBuilder(length + 3);
                    sb.append(str);
                    sb.setLength(i3);
                }
                if (cCharAt != ' ') {
                    if (cCharAt != '\\') {
                        switch (cCharAt) {
                            case '\b':
                                i4 = 98;
                                break;
                            case '\t':
                                i4 = 116;
                                break;
                            case '\n':
                                i4 = 110;
                                break;
                            case 11:
                                i4 = 118;
                                break;
                            case '\f':
                                i4 = 102;
                                break;
                            case '\r':
                                i4 = 114;
                                break;
                            default:
                                i4 = -1;
                                break;
                        }
                    } else {
                        i4 = 92;
                    }
                }
                if (i4 >= 0) {
                    sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                    sb.append((char) i4);
                } else if (cCharAt == c2) {
                    sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                    sb.append(c2);
                } else {
                    if (cCharAt < 256) {
                        sb.append("\\x");
                        i2 = 2;
                    } else {
                        sb.append("\\u");
                        i2 = 4;
                    }
                    for (int i5 = (i2 - 1) * 4; i5 >= 0; i5 -= 4) {
                        int i6 = (cCharAt >> i5) & 15;
                        sb.append((char) (i6 < 10 ? i6 + 48 : i6 + 87));
                    }
                }
            } else if (sb != null) {
                sb.append(cCharAt);
            }
        }
        return sb == null ? str : sb.toString();
    }

    public static Callable getElemFunctionAndThis(Object obj, Object obj2, Context context, Scriptable scriptable) {
        Scriptable objectOrNull;
        Object property;
        if (isSymbol(obj2)) {
            objectOrNull = toObjectOrNull(context, obj, scriptable);
            if (objectOrNull == null) {
                throw undefCallError(obj, String.valueOf(obj2));
            }
            property = ScriptableObject.getProperty(objectOrNull, (Symbol) obj2);
        } else {
            String stringIdOrIndex = toStringIdOrIndex(context, obj2);
            if (stringIdOrIndex != null) {
                return getPropFunctionAndThis(obj, stringIdOrIndex, context, scriptable);
            }
            int iLastIndexResult = lastIndexResult(context);
            objectOrNull = toObjectOrNull(context, obj, scriptable);
            if (objectOrNull == null) {
                throw undefCallError(obj, String.valueOf(obj2));
            }
            property = ScriptableObject.getProperty(objectOrNull, iLastIndexResult);
        }
        if (!(property instanceof Callable)) {
            throw notFunctionError(property, obj2);
        }
        storeScriptable(context, objectOrNull);
        return (Callable) property;
    }

    public static Object getObjectElem(Object obj, Object obj2, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return getObjectElem(objectOrNull, obj2, context);
        }
        throw undefReadError(obj, obj2);
    }

    public static Object getObjectIndex(Object obj, double d2, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull == null) {
            throw undefReadError(obj, toString(d2));
        }
        int i2 = (int) d2;
        return ((double) i2) == d2 ? getObjectIndex(objectOrNull, i2, context) : getObjectProp(objectOrNull, toString(d2), context);
    }

    public static Object getObjectProp(Object obj, String str, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return getObjectProp(objectOrNull, str, context);
        }
        throw undefReadError(obj, str);
    }

    public static Object getObjectPropNoWarn(Object obj, String str, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull == null) {
            throw undefReadError(obj, str);
        }
        Object property = ScriptableObject.getProperty(objectOrNull, str);
        return property == Scriptable.NOT_FOUND ? Undefined.instance : property;
    }

    public static Callable getPropFunctionAndThis(Object obj, String str, Context context, Scriptable scriptable) {
        return getPropFunctionAndThisHelper(obj, str, context, toObjectOrNull(context, obj, scriptable));
    }

    public static Object nameIncrDecr(Scriptable scriptable, String str, Context context, int i2) {
        do {
            if (context.useDynamicScope && scriptable.getParentScope() == null) {
                scriptable = checkDynamicScope(context.topCallScope, scriptable);
            }
            Scriptable prototype = scriptable;
            do {
                if ((prototype instanceof NativeWith) && (prototype.getPrototype() instanceof XMLObject)) {
                    break;
                }
                Object obj = prototype.get(str, scriptable);
                if (obj != Scriptable.NOT_FOUND) {
                    return doScriptableIncrDecr(prototype, str, scriptable, obj, i2);
                }
                prototype = prototype.getPrototype();
            } while (prototype != null);
            scriptable = scriptable.getParentScope();
        } while (scriptable != null);
        throw notFoundError(scriptable, str);
    }

    public static Scriptable newObjectLiteral(Object[] objArr, Object[] objArr2, int[] iArr, Context context, Scriptable scriptable) {
        Scriptable scriptableNewObject = context.newObject(scriptable);
        int length = objArr.length;
        for (int i2 = 0; i2 != length; i2++) {
            Object obj = objArr[i2];
            int i3 = iArr == null ? 0 : iArr[i2];
            Object obj2 = objArr2[i2];
            if (!(obj instanceof String)) {
                scriptableNewObject.put(((Integer) obj).intValue(), scriptableNewObject, obj2);
            } else if (i3 == 0) {
                String str = (String) obj;
                if (isSpecialProperty(str)) {
                    specialRef(scriptableNewObject, str, context, scriptable).set(context, scriptable, obj2);
                } else {
                    scriptableNewObject.put(str, scriptableNewObject, obj2);
                }
            } else {
                ((ScriptableObject) scriptableNewObject).setGetterOrSetter((String) obj, 0, (Callable) obj2, i3 == 1);
            }
        }
        return scriptableNewObject;
    }

    public static RuntimeException notFunctionError(Object obj, Object obj2) {
        String string = obj2 == null ? TmpConstant.GROUP_ROLE_UNKNOWN : obj2.toString();
        return obj == Scriptable.NOT_FOUND ? typeError1("msg.function.not.found", string) : typeError2("msg.isnt.function", string, typeof(obj));
    }

    public static Object propIncrDecr(Object obj, String str, Context context, Scriptable scriptable, int i2) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull == null) {
            throw undefReadError(obj, str);
        }
        Scriptable prototype = objectOrNull;
        do {
            Object obj2 = prototype.get(str, objectOrNull);
            if (obj2 != Scriptable.NOT_FOUND) {
                return doScriptableIncrDecr(prototype, str, objectOrNull, obj2, i2);
            }
            prototype = prototype.getPrototype();
        } while (prototype != null);
        Double d2 = NaNobj;
        objectOrNull.put(str, objectOrNull, d2);
        return d2;
    }

    public static Object refIncrDecr(Ref ref, Context context, Scriptable scriptable, int i2) {
        double number;
        Object objWrapNumber = ref.get(context);
        boolean z2 = (i2 & 2) != 0;
        if (objWrapNumber instanceof Number) {
            number = ((Number) objWrapNumber).doubleValue();
        } else {
            number = toNumber(objWrapNumber);
            if (z2) {
                objWrapNumber = wrapNumber(number);
            }
        }
        Number numberWrapNumber = wrapNumber((i2 & 1) == 0 ? number + 1.0d : number - 1.0d);
        ref.set(context, scriptable, numberWrapNumber);
        return z2 ? objWrapNumber : numberWrapNumber;
    }

    public static Object refSet(Ref ref, Object obj, Context context, Scriptable scriptable) {
        return ref.set(context, scriptable, obj);
    }

    public static Object setObjectElem(Object obj, Object obj2, Object obj3, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return setObjectElem(objectOrNull, obj2, obj3, context);
        }
        throw undefWriteError(obj, obj2, obj3);
    }

    public static Object setObjectIndex(Object obj, double d2, Object obj2, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull == null) {
            throw undefWriteError(obj, String.valueOf(d2), obj2);
        }
        int i2 = (int) d2;
        return ((double) i2) == d2 ? setObjectIndex(objectOrNull, i2, obj2, context) : setObjectProp(objectOrNull, toString(d2), obj2, context);
    }

    public static Object setObjectProp(Object obj, String str, Object obj2, Context context, Scriptable scriptable) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return setObjectProp(objectOrNull, str, obj2, context);
        }
        throw undefWriteError(obj, str, obj2);
    }

    public static Ref specialRef(Object obj, String str, Context context, Scriptable scriptable) {
        return SpecialRef.createSpecial(context, scriptable, obj, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static double stringToNumber(java.lang.String r25, int r26, int r27, int r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ScriptRuntime.stringToNumber(java.lang.String, int, int, int, boolean):double");
    }

    public static double toInteger(double d2) {
        if (d2 != d2) {
            return 0.0d;
        }
        return (d2 == 0.0d || d2 == Double.POSITIVE_INFINITY || d2 == Double.NEGATIVE_INFINITY) ? d2 : d2 > 0.0d ? Math.floor(d2) : Math.ceil(d2);
    }

    public static Object toPrimitive(Object obj, Class<?> cls) {
        if (!(obj instanceof Scriptable)) {
            return obj;
        }
        Object defaultValue = ((Scriptable) obj).getDefaultValue(cls);
        if (!(defaultValue instanceof Scriptable) || isSymbol(defaultValue)) {
            return defaultValue;
        }
        throw typeError0("msg.bad.default.value");
    }

    public static long toUint32(Object obj) {
        return toUint32(toNumber(obj));
    }

    public static BaseFunction typeErrorThrower(Context context) {
        if (context.typeErrorThrower == null) {
            BaseFunction baseFunction = new BaseFunction() { // from class: org.mozilla.javascript.ScriptRuntime.1
                static final long serialVersionUID = -5891740962154902286L;

                @Override // org.mozilla.javascript.BaseFunction, org.mozilla.javascript.Function, org.mozilla.javascript.Callable
                public Object call(Context context2, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
                    throw ScriptRuntime.typeError0("msg.op.not.allowed");
                }

                @Override // org.mozilla.javascript.BaseFunction
                public int getLength() {
                    return 0;
                }
            };
            setFunctionProtoAndParent(baseFunction, context.topCallScope);
            baseFunction.preventExtensions();
            context.typeErrorThrower = baseFunction;
        }
        return context.typeErrorThrower;
    }

    public static Object delete(Object obj, Object obj2, Context context, Scriptable scriptable, boolean z2) {
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        if (objectOrNull != null) {
            return wrapBoolean(deleteObjectElem(objectOrNull, obj2, context));
        }
        if (z2) {
            return Boolean.TRUE;
        }
        throw undefDeleteError(obj, obj2);
    }

    public static Object enumInit(Object obj, Context context, Scriptable scriptable, int i2) {
        IdEnumeration idEnumeration = new IdEnumeration();
        Scriptable objectOrNull = toObjectOrNull(context, obj, scriptable);
        idEnumeration.obj = objectOrNull;
        if (i2 == 6) {
            idEnumeration.enumType = i2;
            idEnumeration.iterator = null;
            return enumInitInOrder(context, idEnumeration);
        }
        if (objectOrNull == null) {
            return idEnumeration;
        }
        idEnumeration.enumType = i2;
        idEnumeration.iterator = null;
        if (i2 != 3 && i2 != 4 && i2 != 5) {
            idEnumeration.iterator = toIterator(context, objectOrNull.getParentScope(), idEnumeration.obj, i2 == 0);
        }
        if (idEnumeration.iterator == null) {
            enumChangeObject(idEnumeration);
        }
        return idEnumeration;
    }

    static Object getIndexObject(double d2) {
        int i2 = (int) d2;
        if (i2 == d2) {
            return Integer.valueOf(i2);
        }
        return toString(d2);
    }

    public static Ref nameRef(Object obj, Object obj2, Context context, Scriptable scriptable, int i2) {
        return currentXMLLib(context).nameRef(context, obj, obj2, scriptable, i2);
    }

    public static EcmaError constructError(String str, String str2, int i2) {
        int[] iArr = new int[1];
        String sourcePositionFromStack = Context.getSourcePositionFromStack(iArr);
        int i3 = iArr[0];
        if (i3 != 0) {
            iArr[0] = i3 + i2;
        }
        return constructError(str, str2, sourcePositionFromStack, iArr[0], null, 0);
    }

    public static int toInt32(Object[] objArr, int i2) {
        if (i2 < objArr.length) {
            return toInt32(objArr[i2]);
        }
        return 0;
    }

    public static double toInteger(Object[] objArr, int i2) {
        if (i2 < objArr.length) {
            return toInteger(objArr[i2]);
        }
        return 0.0d;
    }

    @Deprecated
    public static Scriptable toObject(Scriptable scriptable, Object obj, Class<?> cls) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        return toObject(Context.getContext(), scriptable, obj);
    }

    public static Object getObjectElem(Scriptable scriptable, Object obj, Context context) {
        Object property;
        if (scriptable instanceof XMLObject) {
            property = ((XMLObject) scriptable).get(context, obj);
        } else if (isSymbol(obj)) {
            property = ScriptableObject.getProperty(scriptable, (Symbol) obj);
        } else {
            String stringIdOrIndex = toStringIdOrIndex(context, obj);
            if (stringIdOrIndex == null) {
                property = ScriptableObject.getProperty(scriptable, lastIndexResult(context));
            } else {
                property = ScriptableObject.getProperty(scriptable, stringIdOrIndex);
            }
        }
        return property == Scriptable.NOT_FOUND ? Undefined.instance : property;
    }

    public static Object getObjectProp(Scriptable scriptable, String str, Context context) {
        Object property = ScriptableObject.getProperty(scriptable, str);
        if (property != Scriptable.NOT_FOUND) {
            return property;
        }
        if (context.hasFeature(11)) {
            Context.reportWarning(getMessage1("msg.ref.undefined.prop", str));
        }
        return Undefined.instance;
    }

    public static Ref memberRef(Object obj, Object obj2, Object obj3, Context context, int i2) {
        if (obj instanceof XMLObject) {
            return ((XMLObject) obj).memberRef(context, obj2, obj3, i2);
        }
        throw notXmlError(obj);
    }

    public static Scriptable newObject(Object obj, Context context, Scriptable scriptable, Object[] objArr) {
        if (obj instanceof Function) {
            return ((Function) obj).construct(context, scriptable, objArr);
        }
        throw notFunctionError(obj);
    }

    public static Object setObjectElem(Scriptable scriptable, Object obj, Object obj2, Context context) {
        if (scriptable instanceof XMLObject) {
            ((XMLObject) scriptable).put(context, obj, obj2);
        } else if (isSymbol(obj)) {
            ScriptableObject.putProperty(scriptable, (Symbol) obj, obj2);
        } else {
            String stringIdOrIndex = toStringIdOrIndex(context, obj);
            if (stringIdOrIndex == null) {
                ScriptableObject.putProperty(scriptable, lastIndexResult(context), obj2);
            } else {
                ScriptableObject.putProperty(scriptable, stringIdOrIndex, obj2);
            }
        }
        return obj2;
    }

    public static Object setObjectProp(Scriptable scriptable, String str, Object obj, Context context) {
        ScriptableObject.putProperty(scriptable, str, obj);
        return obj;
    }

    public static int toInt32(double d2) {
        return DoubleConversion.doubleToInt32(d2);
    }

    public static Scriptable toObjectOrNull(Context context, Object obj, Scriptable scriptable) {
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        if (obj == null || obj == Undefined.instance) {
            return null;
        }
        return toObject(context, scriptable, obj);
    }

    public static Object getObjectIndex(Scriptable scriptable, int i2, Context context) {
        Object property = ScriptableObject.getProperty(scriptable, i2);
        return property == Scriptable.NOT_FOUND ? Undefined.instance : property;
    }

    public static RuntimeException notFunctionError(Object obj, Object obj2, String str) {
        int iIndexOf;
        String string = toString(obj);
        if ((obj instanceof NativeFunction) && (iIndexOf = string.indexOf(123, string.indexOf(41))) > -1) {
            string = string.substring(0, iIndexOf + 1) + "...}";
        }
        if (obj2 == Scriptable.NOT_FOUND) {
            return typeError2("msg.function.not.found.in", str, string);
        }
        return typeError3("msg.isnt.function.in", str, string, typeof(obj2));
    }

    public static Object setObjectIndex(Scriptable scriptable, int i2, Object obj, Context context) {
        ScriptableObject.putProperty(scriptable, i2, obj);
        return obj;
    }

    public static Scriptable toObject(Context context, Scriptable scriptable, Object obj) {
        if (isSymbol(obj)) {
            NativeSymbol nativeSymbol = new NativeSymbol((NativeSymbol) obj);
            setBuiltinProtoAndParent(nativeSymbol, scriptable, TopLevel.Builtins.Symbol);
            return nativeSymbol;
        }
        if (obj instanceof Scriptable) {
            return (Scriptable) obj;
        }
        if (obj instanceof CharSequence) {
            NativeString nativeString = new NativeString((CharSequence) obj);
            setBuiltinProtoAndParent(nativeString, scriptable, TopLevel.Builtins.String);
            return nativeString;
        }
        if (obj instanceof Number) {
            NativeNumber nativeNumber = new NativeNumber(((Number) obj).doubleValue());
            setBuiltinProtoAndParent(nativeNumber, scriptable, TopLevel.Builtins.Number);
            return nativeNumber;
        }
        if (obj instanceof Boolean) {
            NativeBoolean nativeBoolean = new NativeBoolean(((Boolean) obj).booleanValue());
            setBuiltinProtoAndParent(nativeBoolean, scriptable, TopLevel.Builtins.Boolean);
            return nativeBoolean;
        }
        if (obj != null) {
            if (obj != Undefined.instance) {
                Object objWrap = context.getWrapFactory().wrap(context, scriptable, obj, null);
                if (objWrap instanceof Scriptable) {
                    return (Scriptable) objWrap;
                }
                throw errorWithClassName("msg.invalid.type", obj);
            }
            throw typeError0("msg.undef.to.object");
        }
        throw typeError0("msg.null.to.object");
    }

    public static EcmaError constructError(String str, String str2, String str3, int i2, String str4, int i3) {
        return new EcmaError(str, str2, str3, i2, str4, i3);
    }

    public static String toString(Object[] objArr, int i2) {
        return i2 < objArr.length ? toString(objArr[i2]) : "undefined";
    }

    public static String toString(double d2) {
        return numberToString(d2, 10);
    }

    public static double toNumber(Object[] objArr, int i2) {
        return i2 < objArr.length ? toNumber(objArr[i2]) : NaN;
    }

    public static double toNumber(String str) {
        char cCharAt;
        int i2;
        char cCharAt2;
        int length = str.length();
        int i3 = 0;
        while (i3 != length) {
            char cCharAt3 = str.charAt(i3);
            if (!isStrWhiteSpaceChar(cCharAt3)) {
                int i4 = length - 1;
                while (true) {
                    cCharAt = str.charAt(i4);
                    if (!isStrWhiteSpaceChar(cCharAt)) {
                        break;
                    }
                    i4--;
                }
                Context currentContext = Context.getCurrentContext();
                boolean z2 = currentContext == null || currentContext.getLanguageVersion() < 200;
                int i5 = 16;
                if (cCharAt3 == '0') {
                    int i6 = i3 + 2;
                    if (i6 <= i4) {
                        char cCharAt4 = str.charAt(i3 + 1);
                        if (cCharAt4 != 'x' && cCharAt4 != 'X') {
                            i5 = (z2 || !(cCharAt4 == 'o' || cCharAt4 == 'O')) ? (z2 || !(cCharAt4 == 'b' || cCharAt4 == 'B')) ? -1 : 2 : 8;
                        }
                        if (i5 != -1) {
                            if (z2) {
                                return stringPrefixToNumber(str, i6, i5);
                            }
                            return stringToNumber(str, i6, i4, i5);
                        }
                    }
                } else if (z2 && ((cCharAt3 == '+' || cCharAt3 == '-') && (i2 = i3 + 3) <= i4 && str.charAt(i3 + 1) == '0' && ((cCharAt2 = str.charAt(i3 + 2)) == 'x' || cCharAt2 == 'X'))) {
                    double dStringPrefixToNumber = stringPrefixToNumber(str, i2, 16);
                    return cCharAt3 == '-' ? -dStringPrefixToNumber : dStringPrefixToNumber;
                }
                if (cCharAt == 'y') {
                    if (cCharAt3 == '+' || cCharAt3 == '-') {
                        i3++;
                    }
                    if (i3 + 7 == i4 && str.regionMatches(i3, "Infinity", 0, 8)) {
                        return cCharAt3 == '-' ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
                    }
                    return NaN;
                }
                String strSubstring = str.substring(i3, i4 + 1);
                for (int length2 = strSubstring.length() - 1; length2 >= 0; length2--) {
                    char cCharAt5 = strSubstring.charAt(length2);
                    if (('0' > cCharAt5 || cCharAt5 > '9') && cCharAt5 != '.' && cCharAt5 != 'e' && cCharAt5 != 'E' && cCharAt5 != '+' && cCharAt5 != '-') {
                        return NaN;
                    }
                }
                try {
                    return Double.parseDouble(strSubstring);
                } catch (NumberFormatException unused) {
                    return NaN;
                }
            }
            i3++;
        }
        return 0.0d;
    }

    public static CharSequence add(CharSequence charSequence, Object obj) {
        return new ConsString(charSequence, toCharSequence(obj));
    }

    public static CharSequence add(Object obj, CharSequence charSequence) {
        return new ConsString(toCharSequence(obj), charSequence);
    }

    @Deprecated
    public static Scriptable toObject(Context context, Scriptable scriptable, Object obj, Class<?> cls) {
        return toObject(context, scriptable, obj);
    }
}
