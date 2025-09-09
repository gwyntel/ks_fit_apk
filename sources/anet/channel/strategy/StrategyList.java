package anet.channel.strategy;

import anet.channel.strategy.l;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import com.taobao.accs.common.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/* loaded from: classes2.dex */
class StrategyList implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    private List<IPConnStrategy> f6969a;

    /* renamed from: b, reason: collision with root package name */
    private Map<Integer, ConnHistoryItem> f6970b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f6971c;

    /* renamed from: d, reason: collision with root package name */
    private transient Comparator<IPConnStrategy> f6972d;

    /* JADX INFO: Access modifiers changed from: private */
    interface Predicate<T> {
        boolean apply(T t2);
    }

    public StrategyList() {
        this.f6969a = new ArrayList();
        this.f6970b = new SerialLruCache(40);
        this.f6971c = false;
        this.f6972d = null;
    }

    public void checkInit() {
        if (this.f6969a == null) {
            this.f6969a = new ArrayList();
        }
        if (this.f6970b == null) {
            this.f6970b = new SerialLruCache(40);
        }
        Iterator<Map.Entry<Integer, ConnHistoryItem>> it = this.f6970b.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue().d()) {
                it.remove();
            }
        }
        for (IPConnStrategy iPConnStrategy : this.f6969a) {
            if (!this.f6970b.containsKey(Integer.valueOf(iPConnStrategy.getUniqueId()))) {
                this.f6970b.put(Integer.valueOf(iPConnStrategy.getUniqueId()), new ConnHistoryItem());
            }
        }
        Collections.sort(this.f6969a, a());
    }

    public List<IConnStrategy> getStrategyList() {
        if (this.f6969a.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        LinkedList linkedList = null;
        for (IPConnStrategy iPConnStrategy : this.f6969a) {
            ConnHistoryItem connHistoryItem = this.f6970b.get(Integer.valueOf(iPConnStrategy.getUniqueId()));
            if (connHistoryItem == null || !connHistoryItem.c()) {
                if (linkedList == null) {
                    linkedList = new LinkedList();
                }
                linkedList.add(iPConnStrategy);
            } else {
                ALog.i("awcn.StrategyList", "strategy ban!", null, Constants.KEY_STRATEGY, iPConnStrategy);
            }
        }
        return linkedList == null ? Collections.EMPTY_LIST : linkedList;
    }

    public void notifyConnEvent(IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if (!(iConnStrategy instanceof IPConnStrategy) || this.f6969a.indexOf(iConnStrategy) == -1) {
            return;
        }
        this.f6970b.get(Integer.valueOf(((IPConnStrategy) iConnStrategy).getUniqueId())).a(connEvent.isSuccess);
        Collections.sort(this.f6969a, this.f6972d);
    }

    public boolean shouldRefresh() {
        boolean z2;
        boolean z3 = true;
        loop0: while (true) {
            z2 = z3;
            for (IPConnStrategy iPConnStrategy : this.f6969a) {
                if (!this.f6970b.get(Integer.valueOf(iPConnStrategy.getUniqueId())).b()) {
                    if (iPConnStrategy.f6949a == 0) {
                        break;
                    }
                    z2 = false;
                }
            }
            z3 = false;
        }
        return (this.f6971c && z3) || z2;
    }

    public String toString() {
        return new ArrayList(this.f6969a).toString();
    }

    public void update(l.b bVar) {
        Iterator<IPConnStrategy> it = this.f6969a.iterator();
        while (it.hasNext()) {
            it.next().f6951c = true;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < bVar.f7036h.length; i3++) {
            int i4 = 0;
            while (true) {
                String[] strArr = bVar.f7034f;
                if (i4 >= strArr.length) {
                    break;
                }
                a(strArr[i4], 1, bVar.f7036h[i3]);
                i4++;
            }
            if (bVar.f7035g != null) {
                this.f6971c = true;
                int i5 = 0;
                while (true) {
                    String[] strArr2 = bVar.f7035g;
                    if (i5 < strArr2.length) {
                        a(strArr2[i5], 0, bVar.f7036h[i3]);
                        i5++;
                    }
                }
            } else {
                this.f6971c = false;
            }
        }
        if (bVar.f7037i != null) {
            while (true) {
                l.e[] eVarArr = bVar.f7037i;
                if (i2 >= eVarArr.length) {
                    break;
                }
                l.e eVar = eVarArr[i2];
                String str = eVar.f7051a;
                a(str, anet.channel.strategy.utils.c.c(str) ? -1 : 1, eVar.f7052b);
                i2++;
            }
        }
        ListIterator<IPConnStrategy> listIterator = this.f6969a.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().f6951c) {
                listIterator.remove();
            }
        }
        Collections.sort(this.f6969a, a());
    }

    private void a(String str, int i2, l.a aVar) {
        int iA = a(this.f6969a, new j(this, aVar, str, ConnProtocol.valueOf(aVar)));
        if (iA != -1) {
            IPConnStrategy iPConnStrategy = this.f6969a.get(iA);
            iPConnStrategy.cto = aVar.f7023c;
            iPConnStrategy.rto = aVar.f7024d;
            iPConnStrategy.heartbeat = aVar.f7026f;
            iPConnStrategy.f6949a = i2;
            iPConnStrategy.f6950b = 0;
            iPConnStrategy.f6951c = false;
            return;
        }
        IPConnStrategy iPConnStrategyA = IPConnStrategy.a(str, aVar);
        if (iPConnStrategyA != null) {
            iPConnStrategyA.f6949a = i2;
            iPConnStrategyA.f6950b = 0;
            if (!this.f6970b.containsKey(Integer.valueOf(iPConnStrategyA.getUniqueId()))) {
                this.f6970b.put(Integer.valueOf(iPConnStrategyA.getUniqueId()), new ConnHistoryItem());
            }
            this.f6969a.add(iPConnStrategyA);
        }
    }

    StrategyList(List<IPConnStrategy> list) {
        this.f6969a = new ArrayList();
        this.f6970b = new SerialLruCache(40);
        this.f6971c = false;
        this.f6972d = null;
        this.f6969a = list;
    }

    private Comparator a() {
        if (this.f6972d == null) {
            this.f6972d = new k(this);
        }
        return this.f6972d;
    }

    private static <T> int a(Collection<T> collection, Predicate<T> predicate) {
        if (collection == null) {
            return -1;
        }
        Iterator<T> it = collection.iterator();
        int i2 = 0;
        while (it.hasNext() && !predicate.apply(it.next())) {
            i2++;
        }
        if (i2 == collection.size()) {
            return -1;
        }
        return i2;
    }
}
