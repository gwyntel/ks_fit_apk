package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.collect.Multiset;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@J2ktIncompatible
@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Serialization {

    static final class FieldSetter<T> {
        private final Field field;

        void a(Object obj, int i2) {
            try {
                this.field.set(obj, Integer.valueOf(i2));
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        void b(Object obj, Object obj2) {
            try {
                this.field.set(obj, obj2);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        private FieldSetter(Field field) throws SecurityException {
            this.field = field;
            field.setAccessible(true);
        }
    }

    private Serialization() {
    }

    static FieldSetter a(Class cls, String str) {
        try {
            return new FieldSetter(cls.getDeclaredField(str));
        } catch (NoSuchFieldException e2) {
            throw new AssertionError(e2);
        }
    }

    static void b(Map map, ObjectInputStream objectInputStream) {
        c(map, objectInputStream, objectInputStream.readInt());
    }

    static void c(Map map, ObjectInputStream objectInputStream, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            map.put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    static void d(Multimap multimap, ObjectInputStream objectInputStream) throws IOException {
        e(multimap, objectInputStream, objectInputStream.readInt());
    }

    static void e(Multimap multimap, ObjectInputStream objectInputStream, int i2) throws IOException {
        for (int i3 = 0; i3 < i2; i3++) {
            Collection collection = multimap.get(objectInputStream.readObject());
            int i4 = objectInputStream.readInt();
            for (int i5 = 0; i5 < i4; i5++) {
                collection.add(objectInputStream.readObject());
            }
        }
    }

    static void f(Multiset multiset, ObjectInputStream objectInputStream) {
        g(multiset, objectInputStream, objectInputStream.readInt());
    }

    static void g(Multiset multiset, ObjectInputStream objectInputStream, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            multiset.add(objectInputStream.readObject(), objectInputStream.readInt());
        }
    }

    static int h(ObjectInputStream objectInputStream) {
        return objectInputStream.readInt();
    }

    static void i(Map map, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(map.size());
        for (Map.Entry entry : map.entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    static void j(Multimap multimap, ObjectOutputStream objectOutputStream) {
        objectOutputStream.writeInt(multimap.asMap().size());
        for (Map.Entry entry : multimap.asMap().entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeInt(((Collection) entry.getValue()).size());
            Iterator it = ((Collection) entry.getValue()).iterator();
            while (it.hasNext()) {
                objectOutputStream.writeObject(it.next());
            }
        }
    }

    static void k(Multiset multiset, ObjectOutputStream objectOutputStream) {
        objectOutputStream.writeInt(multiset.entrySet().size());
        for (Multiset.Entry entry : multiset.entrySet()) {
            objectOutputStream.writeObject(entry.getElement());
            objectOutputStream.writeInt(entry.getCount());
        }
    }
}
