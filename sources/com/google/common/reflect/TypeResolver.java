package com.google.common.reflect;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.reflect.Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.CheckForNull;
import kotlin.text.Typography;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class TypeResolver {
    private final TypeTable typeTable;

    private static final class TypeMappingIntrospector extends TypeVisitor {
        private final Map<TypeVariableKey, Type> mappings = Maps.newHashMap();

        private TypeMappingIntrospector() {
        }

        static ImmutableMap f(Type type) {
            Preconditions.checkNotNull(type);
            TypeMappingIntrospector typeMappingIntrospector = new TypeMappingIntrospector();
            typeMappingIntrospector.visit(type);
            return ImmutableMap.copyOf((Map) typeMappingIntrospector.mappings);
        }

        private void map(TypeVariableKey typeVariableKey, Type type) {
            if (this.mappings.containsKey(typeVariableKey)) {
                return;
            }
            Type type2 = type;
            while (type2 != null) {
                if (typeVariableKey.a(type2)) {
                    while (type != null) {
                        type = this.mappings.remove(TypeVariableKey.b(type));
                    }
                    return;
                }
                type2 = this.mappings.get(TypeVariableKey.b(type2));
            }
            this.mappings.put(typeVariableKey, type);
        }

        @Override // com.google.common.reflect.TypeVisitor
        void a(Class cls) {
            visit(cls.getGenericSuperclass());
            visit(cls.getGenericInterfaces());
        }

        @Override // com.google.common.reflect.TypeVisitor
        void c(ParameterizedType parameterizedType) {
            Class cls = (Class) parameterizedType.getRawType();
            TypeVariable[] typeParameters = cls.getTypeParameters();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Preconditions.checkState(typeParameters.length == actualTypeArguments.length);
            for (int i2 = 0; i2 < typeParameters.length; i2++) {
                map(new TypeVariableKey(typeParameters[i2]), actualTypeArguments[i2]);
            }
            visit(cls);
            visit(parameterizedType.getOwnerType());
        }

        @Override // com.google.common.reflect.TypeVisitor
        void d(TypeVariable typeVariable) {
            visit(typeVariable.getBounds());
        }

        @Override // com.google.common.reflect.TypeVisitor
        void e(WildcardType wildcardType) {
            visit(wildcardType.getUpperBounds());
        }
    }

    static final class TypeVariableKey {
        private final TypeVariable<?> var;

        TypeVariableKey(TypeVariable typeVariable) {
            this.var = (TypeVariable) Preconditions.checkNotNull(typeVariable);
        }

        static TypeVariableKey b(Type type) {
            if (type instanceof TypeVariable) {
                return new TypeVariableKey((TypeVariable) type);
            }
            return null;
        }

        private boolean equalsTypeVariable(TypeVariable<?> typeVariable) {
            return this.var.getGenericDeclaration().equals(typeVariable.getGenericDeclaration()) && this.var.getName().equals(typeVariable.getName());
        }

        boolean a(Type type) {
            if (type instanceof TypeVariable) {
                return equalsTypeVariable((TypeVariable) type);
            }
            return false;
        }

        public boolean equals(@CheckForNull Object obj) {
            if (obj instanceof TypeVariableKey) {
                return equalsTypeVariable(((TypeVariableKey) obj).var);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(this.var.getGenericDeclaration(), this.var.getName());
        }

        public String toString() {
            return this.var.toString();
        }
    }

    private static class WildcardCapturer {

        /* renamed from: a, reason: collision with root package name */
        static final WildcardCapturer f14733a = new WildcardCapturer();
        private final AtomicInteger id;

        @CheckForNull
        private Type captureNullable(@CheckForNull Type type) {
            if (type == null) {
                return null;
            }
            return a(type);
        }

        private WildcardCapturer forTypeVariable(final TypeVariable<?> typeVariable) {
            return new WildcardCapturer(this, this.id) { // from class: com.google.common.reflect.TypeResolver.WildcardCapturer.1

                /* renamed from: c, reason: collision with root package name */
                final /* synthetic */ WildcardCapturer f14735c;

                {
                    this.f14735c = this;
                }

                @Override // com.google.common.reflect.TypeResolver.WildcardCapturer
                TypeVariable b(Type[] typeArr) {
                    LinkedHashSet linkedHashSet = new LinkedHashSet(Arrays.asList(typeArr));
                    linkedHashSet.addAll(Arrays.asList(typeVariable.getBounds()));
                    if (linkedHashSet.size() > 1) {
                        linkedHashSet.remove(Object.class);
                    }
                    return super.b((Type[]) linkedHashSet.toArray(new Type[0]));
                }
            };
        }

        private WildcardCapturer notForTypeVariable() {
            return new WildcardCapturer(this.id);
        }

        final Type a(Type type) {
            Preconditions.checkNotNull(type);
            if ((type instanceof Class) || (type instanceof TypeVariable)) {
                return type;
            }
            if (type instanceof GenericArrayType) {
                return Types.h(notForTypeVariable().a(((GenericArrayType) type).getGenericComponentType()));
            }
            if (!(type instanceof ParameterizedType)) {
                if (!(type instanceof WildcardType)) {
                    throw new AssertionError("must have been one of the known types");
                }
                WildcardType wildcardType = (WildcardType) type;
                return wildcardType.getLowerBounds().length == 0 ? b(wildcardType.getUpperBounds()) : type;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class cls = (Class) parameterizedType.getRawType();
            TypeVariable<?>[] typeParameters = cls.getTypeParameters();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (int i2 = 0; i2 < actualTypeArguments.length; i2++) {
                actualTypeArguments[i2] = forTypeVariable(typeParameters[i2]).a(actualTypeArguments[i2]);
            }
            return Types.k(notForTypeVariable().captureNullable(parameterizedType.getOwnerType()), cls, actualTypeArguments);
        }

        TypeVariable b(Type[] typeArr) {
            return Types.i(WildcardCapturer.class, "capture#" + this.id.incrementAndGet() + "-of ? extends " + Joiner.on(Typography.amp).join(typeArr), typeArr);
        }

        private WildcardCapturer() {
            this(new AtomicInteger());
        }

        private WildcardCapturer(AtomicInteger atomicInteger) {
            this.id = atomicInteger;
        }
    }

    static TypeResolver d(Type type) {
        return new TypeResolver().g(TypeMappingIntrospector.f(type));
    }

    static TypeResolver e(Type type) {
        return new TypeResolver().g(TypeMappingIntrospector.f(WildcardCapturer.f14733a.a(type)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T expectArgument(Class<T> cls, Object obj) {
        try {
            return cls.cast(obj);
        } catch (ClassCastException unused) {
            throw new IllegalArgumentException(obj + " is not a " + cls.getSimpleName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void populateTypeMappings(final Map<TypeVariableKey, Type> map, Type type, final Type type2) {
        if (type.equals(type2)) {
            return;
        }
        new TypeVisitor() { // from class: com.google.common.reflect.TypeResolver.1
            @Override // com.google.common.reflect.TypeVisitor
            void a(Class cls) {
                if (type2 instanceof WildcardType) {
                    return;
                }
                throw new IllegalArgumentException("No type mapping from " + cls + " to " + type2);
            }

            @Override // com.google.common.reflect.TypeVisitor
            void b(GenericArrayType genericArrayType) {
                Type type3 = type2;
                if (type3 instanceof WildcardType) {
                    return;
                }
                Type typeG = Types.g(type3);
                Preconditions.checkArgument(typeG != null, "%s is not an array type.", type2);
                TypeResolver.populateTypeMappings(map, genericArrayType.getGenericComponentType(), typeG);
            }

            @Override // com.google.common.reflect.TypeVisitor
            void c(ParameterizedType parameterizedType) {
                Type type3 = type2;
                if (type3 instanceof WildcardType) {
                    return;
                }
                ParameterizedType parameterizedType2 = (ParameterizedType) TypeResolver.expectArgument(ParameterizedType.class, type3);
                if (parameterizedType.getOwnerType() != null && parameterizedType2.getOwnerType() != null) {
                    TypeResolver.populateTypeMappings(map, parameterizedType.getOwnerType(), parameterizedType2.getOwnerType());
                }
                Preconditions.checkArgument(parameterizedType.getRawType().equals(parameterizedType2.getRawType()), "Inconsistent raw type: %s vs. %s", parameterizedType, type2);
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
                Preconditions.checkArgument(actualTypeArguments.length == actualTypeArguments2.length, "%s not compatible with %s", parameterizedType, parameterizedType2);
                for (int i2 = 0; i2 < actualTypeArguments.length; i2++) {
                    TypeResolver.populateTypeMappings(map, actualTypeArguments[i2], actualTypeArguments2[i2]);
                }
            }

            @Override // com.google.common.reflect.TypeVisitor
            void d(TypeVariable typeVariable) {
                map.put(new TypeVariableKey(typeVariable), type2);
            }

            @Override // com.google.common.reflect.TypeVisitor
            void e(WildcardType wildcardType) {
                Type type3 = type2;
                if (type3 instanceof WildcardType) {
                    WildcardType wildcardType2 = (WildcardType) type3;
                    Type[] upperBounds = wildcardType.getUpperBounds();
                    Type[] upperBounds2 = wildcardType2.getUpperBounds();
                    Type[] lowerBounds = wildcardType.getLowerBounds();
                    Type[] lowerBounds2 = wildcardType2.getLowerBounds();
                    Preconditions.checkArgument(upperBounds.length == upperBounds2.length && lowerBounds.length == lowerBounds2.length, "Incompatible type: %s vs. %s", wildcardType, type2);
                    for (int i2 = 0; i2 < upperBounds.length; i2++) {
                        TypeResolver.populateTypeMappings(map, upperBounds[i2], upperBounds2[i2]);
                    }
                    for (int i3 = 0; i3 < lowerBounds.length; i3++) {
                        TypeResolver.populateTypeMappings(map, lowerBounds[i3], lowerBounds2[i3]);
                    }
                }
            }
        }.visit(type);
    }

    private Type resolveGenericArrayType(GenericArrayType genericArrayType) {
        return Types.h(resolveType(genericArrayType.getGenericComponentType()));
    }

    private ParameterizedType resolveParameterizedType(ParameterizedType parameterizedType) {
        Type ownerType = parameterizedType.getOwnerType();
        return Types.k(ownerType == null ? null : resolveType(ownerType), (Class) resolveType(parameterizedType.getRawType()), resolveTypes(parameterizedType.getActualTypeArguments()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Type[] resolveTypes(Type[] typeArr) {
        Type[] typeArr2 = new Type[typeArr.length];
        for (int i2 = 0; i2 < typeArr.length; i2++) {
            typeArr2[i2] = resolveType(typeArr[i2]);
        }
        return typeArr2;
    }

    private WildcardType resolveWildcardType(WildcardType wildcardType) {
        return new Types.WildcardTypeImpl(resolveTypes(wildcardType.getLowerBounds()), resolveTypes(wildcardType.getUpperBounds()));
    }

    Type[] f(Type[] typeArr) {
        for (int i2 = 0; i2 < typeArr.length; i2++) {
            typeArr[i2] = resolveType(typeArr[i2]);
        }
        return typeArr;
    }

    TypeResolver g(Map map) {
        return new TypeResolver(this.typeTable.b(map));
    }

    public Type resolveType(Type type) {
        Preconditions.checkNotNull(type);
        return type instanceof TypeVariable ? this.typeTable.a((TypeVariable) type) : type instanceof ParameterizedType ? resolveParameterizedType((ParameterizedType) type) : type instanceof GenericArrayType ? resolveGenericArrayType((GenericArrayType) type) : type instanceof WildcardType ? resolveWildcardType((WildcardType) type) : type;
    }

    public TypeResolver where(Type type, Type type2) {
        HashMap mapNewHashMap = Maps.newHashMap();
        populateTypeMappings(mapNewHashMap, (Type) Preconditions.checkNotNull(type), (Type) Preconditions.checkNotNull(type2));
        return g(mapNewHashMap);
    }

    private static class TypeTable {
        private final ImmutableMap<TypeVariableKey, Type> map;

        TypeTable() {
            this.map = ImmutableMap.of();
        }

        final Type a(final TypeVariable typeVariable) {
            return resolveInternal(typeVariable, new TypeTable(this) { // from class: com.google.common.reflect.TypeResolver.TypeTable.1

                /* renamed from: c, reason: collision with root package name */
                final /* synthetic */ TypeTable f14732c;

                {
                    this.f14732c = this;
                }

                @Override // com.google.common.reflect.TypeResolver.TypeTable
                public Type resolveInternal(TypeVariable<?> typeVariable2, TypeTable typeTable) {
                    return typeVariable2.getGenericDeclaration().equals(typeVariable.getGenericDeclaration()) ? typeVariable2 : this.resolveInternal(typeVariable2, typeTable);
                }
            });
        }

        final TypeTable b(Map map) {
            ImmutableMap.Builder builder = ImmutableMap.builder();
            builder.putAll(this.map);
            for (Map.Entry entry : map.entrySet()) {
                TypeVariableKey typeVariableKey = (TypeVariableKey) entry.getKey();
                Type type = (Type) entry.getValue();
                Preconditions.checkArgument(!typeVariableKey.a(type), "Type variable %s bound to itself", typeVariableKey);
                builder.put(typeVariableKey, type);
            }
            return new TypeTable(builder.buildOrThrow());
        }

        Type resolveInternal(TypeVariable typeVariable, TypeTable typeTable) {
            Type type = this.map.get(new TypeVariableKey(typeVariable));
            if (type != null) {
                return new TypeResolver(typeTable).resolveType(type);
            }
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length == 0) {
                return typeVariable;
            }
            Type[] typeArrResolveTypes = new TypeResolver(typeTable).resolveTypes(bounds);
            return (Types.NativeTypeVariableEquals.f14747a && Arrays.equals(bounds, typeArrResolveTypes)) ? typeVariable : Types.i(typeVariable.getGenericDeclaration(), typeVariable.getName(), typeArrResolveTypes);
        }

        private TypeTable(ImmutableMap<TypeVariableKey, Type> immutableMap) {
            this.map = immutableMap;
        }
    }

    public TypeResolver() {
        this.typeTable = new TypeTable();
    }

    private TypeResolver(TypeTable typeTable) {
        this.typeTable = typeTable;
    }
}
