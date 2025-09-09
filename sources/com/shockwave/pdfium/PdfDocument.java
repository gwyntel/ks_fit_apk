package com.shockwave.pdfium;

import android.graphics.RectF;
import android.os.ParcelFileDescriptor;
import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class PdfDocument {

    /* renamed from: a, reason: collision with root package name */
    long f19833a;

    /* renamed from: b, reason: collision with root package name */
    ParcelFileDescriptor f19834b;

    /* renamed from: c, reason: collision with root package name */
    final Map f19835c = new ArrayMap();

    public static class Bookmark {

        /* renamed from: a, reason: collision with root package name */
        String f19836a;

        /* renamed from: b, reason: collision with root package name */
        long f19837b;

        /* renamed from: c, reason: collision with root package name */
        long f19838c;
        private List<Bookmark> children = new ArrayList();

        public List<Bookmark> getChildren() {
            return this.children;
        }

        public long getPageIdx() {
            return this.f19837b;
        }

        public String getTitle() {
            return this.f19836a;
        }

        public boolean hasChildren() {
            return !this.children.isEmpty();
        }
    }

    public static class Link {
        private RectF bounds;
        private Integer destPageIdx;
        private String uri;

        public Link(RectF rectF, Integer num, String str) {
            this.bounds = rectF;
            this.destPageIdx = num;
            this.uri = str;
        }

        public RectF getBounds() {
            return this.bounds;
        }

        public Integer getDestPageIdx() {
            return this.destPageIdx;
        }

        public String getUri() {
            return this.uri;
        }
    }

    public static class Meta {

        /* renamed from: a, reason: collision with root package name */
        String f19839a;

        /* renamed from: b, reason: collision with root package name */
        String f19840b;

        /* renamed from: c, reason: collision with root package name */
        String f19841c;

        /* renamed from: d, reason: collision with root package name */
        String f19842d;

        /* renamed from: e, reason: collision with root package name */
        String f19843e;

        /* renamed from: f, reason: collision with root package name */
        String f19844f;

        /* renamed from: g, reason: collision with root package name */
        String f19845g;

        /* renamed from: h, reason: collision with root package name */
        String f19846h;

        public String getAuthor() {
            return this.f19840b;
        }

        public String getCreationDate() {
            return this.f19845g;
        }

        public String getCreator() {
            return this.f19843e;
        }

        public String getKeywords() {
            return this.f19842d;
        }

        public String getModDate() {
            return this.f19846h;
        }

        public String getProducer() {
            return this.f19844f;
        }

        public String getSubject() {
            return this.f19841c;
        }

        public String getTitle() {
            return this.f19839a;
        }
    }

    PdfDocument() {
    }

    public boolean hasPage(int i2) {
        return this.f19835c.containsKey(Integer.valueOf(i2));
    }
}
