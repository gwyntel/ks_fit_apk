package com.xiaomi.push;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class cr {

    /* renamed from: a, reason: collision with root package name */
    private static volatile cr f23541a;

    /* renamed from: a, reason: collision with other field name */
    private Context f239a;

    /* renamed from: a, reason: collision with other field name */
    private cq f240a;

    /* renamed from: a, reason: collision with other field name */
    private final HashMap<String, cp> f242a = new HashMap<>();

    /* renamed from: a, reason: collision with other field name */
    private ThreadPoolExecutor f243a = new ThreadPoolExecutor(1, 1, 15, TimeUnit.SECONDS, new LinkedBlockingQueue());

    /* renamed from: a, reason: collision with other field name */
    private final ArrayList<a> f241a = new ArrayList<>();

    public static abstract class a implements Runnable {

        /* renamed from: a, reason: collision with other field name */
        private a f245a;

        /* renamed from: a, reason: collision with other field name */
        private String f246a;

        /* renamed from: a, reason: collision with other field name */
        private WeakReference<Context> f247a;

        /* renamed from: b, reason: collision with root package name */
        protected String f23543b;

        /* renamed from: a, reason: collision with other field name */
        protected cp f244a = null;

        /* renamed from: a, reason: collision with other field name */
        private Random f248a = new Random();

        /* renamed from: a, reason: collision with root package name */
        private int f23542a = 0;

        public a(String str) {
            this.f246a = str;
        }

        /* renamed from: a, reason: collision with other method in class */
        public Object mo255a() {
            return null;
        }

        public abstract void a(Context context, SQLiteDatabase sQLiteDatabase);

        public void b(Context context) {
        }

        @Override // java.lang.Runnable
        public final void run() throws IOException {
            Context context;
            WeakReference<Context> weakReference = this.f247a;
            if (weakReference == null || (context = weakReference.get()) == null || context.getFilesDir() == null || this.f244a == null || TextUtils.isEmpty(this.f246a)) {
                return;
            }
            File file = new File(this.f246a);
            u.a(context, new File(file.getParentFile(), bo.b(file.getAbsolutePath())), new ct(this, context));
        }

        void a(cp cpVar, Context context) {
            this.f244a = cpVar;
            this.f23543b = cpVar.a();
            this.f247a = new WeakReference<>(context);
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m257a() {
            return this.f244a == null || TextUtils.isEmpty(this.f23543b) || this.f247a == null;
        }

        public void a(a aVar) {
            this.f245a = aVar;
        }

        public void a(Context context, Object obj) {
            cr.a(context).a(this);
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m256a() {
            return this.f246a;
        }

        public SQLiteDatabase a() {
            return this.f244a.getWritableDatabase();
        }

        void a(Context context) {
            a aVar = this.f245a;
            if (aVar != null) {
                aVar.a(context, mo255a());
            }
            b(context);
        }
    }

    public static class d extends a {

        /* renamed from: a, reason: collision with root package name */
        private String f23550a;

        /* renamed from: a, reason: collision with other field name */
        protected String[] f252a;

        public d(String str, String str2, String[] strArr) {
            super(str);
            this.f23550a = str2;
            this.f252a = strArr;
        }

        @Override // com.xiaomi.push.cr.a
        public void a(Context context, SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.delete(this.f23543b, this.f23550a, this.f252a);
        }
    }

    public static class e extends a {

        /* renamed from: a, reason: collision with root package name */
        private ContentValues f23551a;

        public e(String str, ContentValues contentValues) {
            super(str);
            this.f23551a = contentValues;
        }

        @Override // com.xiaomi.push.cr.a
        public void a(Context context, SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.insert(this.f23543b, null, this.f23551a);
        }
    }

    private cr(Context context) {
        this.f239a = context;
    }

    public void b(a aVar) {
        cp cpVarA;
        if (aVar == null) {
            return;
        }
        if (this.f240a == null) {
            throw new IllegalStateException("should exec init method first!");
        }
        String strM256a = aVar.m256a();
        synchronized (this.f242a) {
            try {
                cpVarA = this.f242a.get(strM256a);
                if (cpVarA == null) {
                    cpVarA = this.f240a.a(this.f239a, strM256a);
                    this.f242a.put(strM256a, cpVarA);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.f243a.isShutdown()) {
            return;
        }
        aVar.a(cpVarA, this.f239a);
        a((Runnable) aVar);
    }

    public static class c extends a {

        /* renamed from: a, reason: collision with root package name */
        private ArrayList<a> f23549a;

        public c(String str, ArrayList<a> arrayList) {
            super(str);
            ArrayList<a> arrayList2 = new ArrayList<>();
            this.f23549a = arrayList2;
            arrayList2.addAll(arrayList);
        }

        @Override // com.xiaomi.push.cr.a
        public void a(Context context, SQLiteDatabase sQLiteDatabase) {
            Iterator<a> it = this.f23549a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next != null) {
                    next.a(context, sQLiteDatabase);
                }
            }
        }

        @Override // com.xiaomi.push.cr.a
        public final void a(Context context) {
            super.a(context);
            Iterator<a> it = this.f23549a.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next != null) {
                    next.a(context);
                }
            }
        }
    }

    public static cr a(Context context) {
        if (f23541a == null) {
            synchronized (cr.class) {
                try {
                    if (f23541a == null) {
                        f23541a = new cr(context);
                    }
                } finally {
                }
            }
        }
        return f23541a;
    }

    private void a() {
        ah.a(this.f239a).b(new cs(this), com.xiaomi.push.service.az.a(this.f239a).a(is.StatDataProcessFrequency.a(), 5));
    }

    public static abstract class b<T> extends a {

        /* renamed from: a, reason: collision with root package name */
        private int f23544a;

        /* renamed from: a, reason: collision with other field name */
        private String f249a;

        /* renamed from: a, reason: collision with other field name */
        private List<String> f250a;

        /* renamed from: a, reason: collision with other field name */
        private String[] f251a;

        /* renamed from: b, reason: collision with root package name */
        private List<T> f23545b;

        /* renamed from: c, reason: collision with root package name */
        private String f23546c;

        /* renamed from: d, reason: collision with root package name */
        private String f23547d;

        /* renamed from: e, reason: collision with root package name */
        private String f23548e;

        public b(String str, List<String> list, String str2, String[] strArr, String str3, String str4, String str5, int i2) {
            super(str);
            this.f23545b = new ArrayList();
            this.f250a = list;
            this.f249a = str2;
            this.f251a = strArr;
            this.f23546c = str3;
            this.f23547d = str4;
            this.f23548e = str5;
            this.f23544a = i2;
        }

        public abstract T a(Context context, Cursor cursor);

        @Override // com.xiaomi.push.cr.a
        public void a(Context context, SQLiteDatabase sQLiteDatabase) {
            String[] strArr;
            this.f23545b.clear();
            List<String> list = this.f250a;
            if (list == null || list.size() <= 0) {
                strArr = null;
            } else {
                String[] strArr2 = new String[this.f250a.size()];
                this.f250a.toArray(strArr2);
                strArr = strArr2;
            }
            int i2 = this.f23544a;
            Cursor cursorQuery = sQLiteDatabase.query(super.f23543b, strArr, this.f249a, this.f251a, this.f23546c, this.f23547d, this.f23548e, i2 > 0 ? String.valueOf(i2) : null);
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                do {
                    T tA = a(context, cursorQuery);
                    if (tA != null) {
                        this.f23545b.add(tA);
                    }
                } while (cursorQuery.moveToNext());
                cursorQuery.close();
            }
            a(context, (List) this.f23545b);
        }

        public abstract void a(Context context, List<T> list);

        @Override // com.xiaomi.push.cr.a
        public SQLiteDatabase a() {
            return ((a) this).f244a.getReadableDatabase();
        }
    }

    public void a(a aVar) {
        cp cpVarA;
        if (aVar == null) {
            return;
        }
        if (this.f240a != null) {
            String strM256a = aVar.m256a();
            synchronized (this.f242a) {
                try {
                    cpVarA = this.f242a.get(strM256a);
                    if (cpVarA == null) {
                        cpVarA = this.f240a.a(this.f239a, strM256a);
                        this.f242a.put(strM256a, cpVarA);
                    }
                } finally {
                }
            }
            if (this.f243a.isShutdown()) {
                return;
            }
            aVar.a(cpVarA, this.f239a);
            synchronized (this.f241a) {
                this.f241a.add(aVar);
                a();
            }
            return;
        }
        throw new IllegalStateException("should exec init method first!");
    }

    public void a(Runnable runnable) {
        if (this.f243a.isShutdown()) {
            return;
        }
        this.f243a.execute(runnable);
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m254a(String str) {
        return a(str).a();
    }

    public void a(ArrayList<a> arrayList) {
        if (this.f240a != null) {
            HashMap map = new HashMap();
            if (this.f243a.isShutdown()) {
                return;
            }
            Iterator<a> it = arrayList.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (next.m257a()) {
                    next.a(a(next.m256a()), this.f239a);
                }
                ArrayList arrayList2 = (ArrayList) map.get(next.m256a());
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    map.put(next.m256a(), arrayList2);
                }
                arrayList2.add(next);
            }
            for (String str : map.keySet()) {
                ArrayList arrayList3 = (ArrayList) map.get(str);
                if (arrayList3 != null && arrayList3.size() > 0) {
                    c cVar = new c(str, arrayList3);
                    cVar.a(((a) arrayList3.get(0)).f244a, this.f239a);
                    this.f243a.execute(cVar);
                }
            }
            return;
        }
        throw new IllegalStateException("should exec setDbHelperFactory method first!");
    }

    private cp a(String str) {
        cp cpVarA = this.f242a.get(str);
        if (cpVarA == null) {
            synchronized (this.f242a) {
                if (cpVarA == null) {
                    try {
                        cpVarA = this.f240a.a(this.f239a, str);
                        this.f242a.put(str, cpVarA);
                    } finally {
                    }
                }
            }
        }
        return cpVarA;
    }
}
