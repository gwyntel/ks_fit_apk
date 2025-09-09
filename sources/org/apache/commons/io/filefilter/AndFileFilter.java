package org.apache.commons.io.filefilter;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class AndFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {
    private static final long serialVersionUID = 7215974688563965257L;
    private final List<IOFileFilter> fileFilters;

    public AndFileFilter() {
        this.fileFilters = new ArrayList();
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FileFilter
    public boolean accept(File file) {
        if (this.fileFilters.isEmpty()) {
            return false;
        }
        Iterator<IOFileFilter> it = this.fileFilters.iterator();
        while (it.hasNext()) {
            if (!it.next().accept(file)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public void addFileFilter(IOFileFilter iOFileFilter) {
        this.fileFilters.add(iOFileFilter);
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public List<IOFileFilter> getFileFilters() {
        return Collections.unmodifiableList(this.fileFilters);
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public boolean removeFileFilter(IOFileFilter iOFileFilter) {
        return this.fileFilters.remove(iOFileFilter);
    }

    @Override // org.apache.commons.io.filefilter.ConditionalFileFilter
    public void setFileFilters(List<IOFileFilter> list) {
        this.fileFilters.clear();
        this.fileFilters.addAll(list);
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        if (this.fileFilters != null) {
            for (int i2 = 0; i2 < this.fileFilters.size(); i2++) {
                if (i2 > 0) {
                    sb.append(",");
                }
                IOFileFilter iOFileFilter = this.fileFilters.get(i2);
                sb.append(iOFileFilter == null ? TmpConstant.GROUP_ROLE_UNKNOWN : iOFileFilter.toString());
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public AndFileFilter(List<IOFileFilter> list) {
        if (list == null) {
            this.fileFilters = new ArrayList();
        } else {
            this.fileFilters = new ArrayList(list);
        }
    }

    @Override // org.apache.commons.io.filefilter.AbstractFileFilter, org.apache.commons.io.filefilter.IOFileFilter, java.io.FilenameFilter
    public boolean accept(File file, String str) {
        if (this.fileFilters.isEmpty()) {
            return false;
        }
        Iterator<IOFileFilter> it = this.fileFilters.iterator();
        while (it.hasNext()) {
            if (!it.next().accept(file, str)) {
                return false;
            }
        }
        return true;
    }

    public AndFileFilter(IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        if (iOFileFilter != null && iOFileFilter2 != null) {
            this.fileFilters = new ArrayList(2);
            addFileFilter(iOFileFilter);
            addFileFilter(iOFileFilter2);
            return;
        }
        throw new IllegalArgumentException("The filters must not be null");
    }
}
