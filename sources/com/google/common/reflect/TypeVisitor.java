package com.google.common.reflect;

import com.google.common.collect.Sets;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Set;

@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class TypeVisitor {
    private final Set<Type> visited = Sets.newHashSet();

    TypeVisitor() {
    }

    void a(Class cls) {
    }

    void b(GenericArrayType genericArrayType) {
    }

    void c(ParameterizedType parameterizedType) {
    }

    void d(TypeVariable typeVariable) {
    }

    void e(WildcardType wildcardType) {
    }

    public final void visit(Type... typeArr) {
        for (Type type : typeArr) {
            if (type != null && this.visited.add(type)) {
                try {
                    if (type instanceof TypeVariable) {
                        d((TypeVariable) type);
                    } else if (type instanceof WildcardType) {
                        e((WildcardType) type);
                    } else if (type instanceof ParameterizedType) {
                        c((ParameterizedType) type);
                    } else if (type instanceof Class) {
                        a((Class) type);
                    } else {
                        if (!(type instanceof GenericArrayType)) {
                            throw new AssertionError("Unknown type: " + type);
                        }
                        b((GenericArrayType) type);
                    }
                } catch (Throwable th) {
                    this.visited.remove(type);
                    throw th;
                }
            }
        }
    }
}
