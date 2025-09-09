package com.google.common.reflect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class Invokable<T, R> implements AnnotatedElement, Member {
    private static final boolean ANNOTATED_TYPE_EXISTS = initAnnotatedTypeExists();
    private final AccessibleObject accessibleObject;
    private final Member member;

    static class ConstructorInvokable<T> extends Invokable<T, T> {

        /* renamed from: a, reason: collision with root package name */
        final Constructor f14724a;

        ConstructorInvokable(Constructor constructor) {
            super(constructor);
            this.f14724a = constructor;
        }

        private boolean mayNeedHiddenThis() {
            Class<T> declaringClass = this.f14724a.getDeclaringClass();
            if (declaringClass.getEnclosingConstructor() != null) {
                return true;
            }
            return declaringClass.getEnclosingMethod() != null ? !Modifier.isStatic(r1.getModifiers()) : (declaringClass.getEnclosingClass() == null || Modifier.isStatic(declaringClass.getModifiers())) ? false : true;
        }

        @Override // com.google.common.reflect.Invokable
        Type[] a() {
            return this.f14724a.getGenericExceptionTypes();
        }

        @Override // com.google.common.reflect.Invokable
        Type[] b() {
            Type[] genericParameterTypes = this.f14724a.getGenericParameterTypes();
            if (genericParameterTypes.length <= 0 || !mayNeedHiddenThis()) {
                return genericParameterTypes;
            }
            Class<?>[] parameterTypes = this.f14724a.getParameterTypes();
            return (genericParameterTypes.length == parameterTypes.length && parameterTypes[0] == getDeclaringClass().getEnclosingClass()) ? (Type[]) Arrays.copyOfRange(genericParameterTypes, 1, genericParameterTypes.length) : genericParameterTypes;
        }

        @Override // com.google.common.reflect.Invokable
        Type c() {
            Class<? super T> declaringClass = getDeclaringClass();
            TypeVariable<Class<? super T>>[] typeParameters = declaringClass.getTypeParameters();
            return typeParameters.length > 0 ? Types.j(declaringClass, typeParameters) : declaringClass;
        }

        @Override // com.google.common.reflect.Invokable
        final Annotation[][] d() {
            return this.f14724a.getParameterAnnotations();
        }

        @Override // com.google.common.reflect.Invokable
        final Object e(Object obj, Object[] objArr) {
            try {
                return this.f14724a.newInstance(objArr);
            } catch (InstantiationException e2) {
                throw new RuntimeException(this.f14724a + " failed.", e2);
            }
        }

        @Override // com.google.common.reflect.Invokable
        public final TypeVariable<?>[] getTypeParameters() {
            TypeVariable<Class<? super T>>[] typeParameters = getDeclaringClass().getTypeParameters();
            TypeVariable<Constructor<T>>[] typeParameters2 = this.f14724a.getTypeParameters();
            TypeVariable<?>[] typeVariableArr = new TypeVariable[typeParameters.length + typeParameters2.length];
            System.arraycopy(typeParameters, 0, typeVariableArr, 0, typeParameters.length);
            System.arraycopy(typeParameters2, 0, typeVariableArr, typeParameters.length, typeParameters2.length);
            return typeVariableArr;
        }

        @Override // com.google.common.reflect.Invokable
        public final boolean isOverridable() {
            return false;
        }

        @Override // com.google.common.reflect.Invokable
        public final boolean isVarArgs() {
            return this.f14724a.isVarArgs();
        }
    }

    static class MethodInvokable<T> extends Invokable<T, Object> {

        /* renamed from: a, reason: collision with root package name */
        final Method f14725a;

        MethodInvokable(Method method) {
            super(method);
            this.f14725a = method;
        }

        @Override // com.google.common.reflect.Invokable
        Type[] a() {
            return this.f14725a.getGenericExceptionTypes();
        }

        @Override // com.google.common.reflect.Invokable
        Type[] b() {
            return this.f14725a.getGenericParameterTypes();
        }

        @Override // com.google.common.reflect.Invokable
        Type c() {
            return this.f14725a.getGenericReturnType();
        }

        @Override // com.google.common.reflect.Invokable
        final Annotation[][] d() {
            return this.f14725a.getParameterAnnotations();
        }

        @Override // com.google.common.reflect.Invokable
        final Object e(Object obj, Object[] objArr) {
            return this.f14725a.invoke(obj, objArr);
        }

        @Override // com.google.common.reflect.Invokable
        public final TypeVariable<?>[] getTypeParameters() {
            return this.f14725a.getTypeParameters();
        }

        @Override // com.google.common.reflect.Invokable
        public final boolean isOverridable() {
            return (isFinal() || isPrivate() || isStatic() || Modifier.isFinal(getDeclaringClass().getModifiers())) ? false : true;
        }

        @Override // com.google.common.reflect.Invokable
        public final boolean isVarArgs() {
            return this.f14725a.isVarArgs();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    Invokable(AccessibleObject accessibleObject) {
        Preconditions.checkNotNull(accessibleObject);
        this.accessibleObject = accessibleObject;
        this.member = (Member) accessibleObject;
    }

    public static Invokable<?, Object> from(Method method) {
        return new MethodInvokable(method);
    }

    private static boolean initAnnotatedTypeExists() throws ClassNotFoundException {
        try {
            Class.forName("java.lang.reflect.AnnotatedType");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    abstract Type[] a();

    abstract Type[] b();

    abstract Type c();

    abstract Annotation[][] d();

    abstract Object e(Object obj, Object[] objArr);

    public boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof Invokable)) {
            return false;
        }
        Invokable invokable = (Invokable) obj;
        return getOwnerType().equals(invokable.getOwnerType()) && this.member.equals(invokable.member);
    }

    @Override // java.lang.reflect.AnnotatedElement
    @CheckForNull
    public final <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this.accessibleObject.getAnnotation(cls);
    }

    @Override // java.lang.reflect.AnnotatedElement
    public final Annotation[] getAnnotations() {
        return this.accessibleObject.getAnnotations();
    }

    @Override // java.lang.reflect.AnnotatedElement
    public final Annotation[] getDeclaredAnnotations() {
        return this.accessibleObject.getDeclaredAnnotations();
    }

    @Override // java.lang.reflect.Member
    public final Class<? super T> getDeclaringClass() {
        return (Class<? super T>) this.member.getDeclaringClass();
    }

    public final ImmutableList<TypeToken<? extends Throwable>> getExceptionTypes() {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Type type : a()) {
            builder.add((ImmutableList.Builder) TypeToken.of(type));
        }
        return builder.build();
    }

    @Override // java.lang.reflect.Member
    public final int getModifiers() {
        return this.member.getModifiers();
    }

    @Override // java.lang.reflect.Member
    public final String getName() {
        return this.member.getName();
    }

    public TypeToken<T> getOwnerType() {
        return TypeToken.of((Class) getDeclaringClass());
    }

    @IgnoreJRERequirement
    public final ImmutableList<Parameter> getParameters() {
        Type[] typeArrB = b();
        Annotation[][] annotationArrD = d();
        Object[] objArr = new Object[typeArrB.length];
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i2 = 0; i2 < typeArrB.length; i2++) {
            builder.add((ImmutableList.Builder) new Parameter(this, i2, TypeToken.of(typeArrB[i2]), annotationArrD[i2], objArr[i2]));
        }
        return builder.build();
    }

    public final TypeToken<? extends R> getReturnType() {
        return (TypeToken<? extends R>) TypeToken.of(c());
    }

    public abstract TypeVariable<?>[] getTypeParameters();

    public int hashCode() {
        return this.member.hashCode();
    }

    @CanIgnoreReturnValue
    @CheckForNull
    public final R invoke(@CheckForNull T t2, Object... objArr) throws IllegalAccessException, InvocationTargetException {
        return (R) e(t2, (Object[]) Preconditions.checkNotNull(objArr));
    }

    public final boolean isAbstract() {
        return Modifier.isAbstract(getModifiers());
    }

    public final boolean isAccessible() {
        return this.accessibleObject.isAccessible();
    }

    @Override // java.lang.reflect.AnnotatedElement
    public final boolean isAnnotationPresent(Class<? extends Annotation> cls) {
        return this.accessibleObject.isAnnotationPresent(cls);
    }

    public final boolean isFinal() {
        return Modifier.isFinal(getModifiers());
    }

    public final boolean isNative() {
        return Modifier.isNative(getModifiers());
    }

    public abstract boolean isOverridable();

    public final boolean isPackagePrivate() {
        return (isPrivate() || isPublic() || isProtected()) ? false : true;
    }

    public final boolean isPrivate() {
        return Modifier.isPrivate(getModifiers());
    }

    public final boolean isProtected() {
        return Modifier.isProtected(getModifiers());
    }

    public final boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }

    public final boolean isStatic() {
        return Modifier.isStatic(getModifiers());
    }

    public final boolean isSynchronized() {
        return Modifier.isSynchronized(getModifiers());
    }

    @Override // java.lang.reflect.Member
    public final boolean isSynthetic() {
        return this.member.isSynthetic();
    }

    public abstract boolean isVarArgs();

    public final <R1 extends R> Invokable<T, R1> returning(Class<R1> cls) {
        return returning(TypeToken.of((Class) cls));
    }

    public final void setAccessible(boolean z2) throws SecurityException {
        this.accessibleObject.setAccessible(z2);
    }

    public String toString() {
        return this.member.toString();
    }

    public final boolean trySetAccessible() throws SecurityException {
        try {
            this.accessibleObject.setAccessible(true);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static <T> Invokable<T, T> from(Constructor<T> constructor) {
        return new ConstructorInvokable(constructor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R1 extends R> Invokable<T, R1> returning(TypeToken<R1> typeToken) {
        if (typeToken.isSupertypeOf(getReturnType())) {
            return this;
        }
        throw new IllegalArgumentException("Invokable is known to return " + getReturnType() + ", not " + typeToken);
    }
}
