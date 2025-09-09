package com.google.firebase.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
class CycleDetector {

    private static class ComponentNode {
        private final Component<?> component;
        private final Set<ComponentNode> dependencies = new HashSet();
        private final Set<ComponentNode> dependents = new HashSet();

        ComponentNode(Component component) {
            this.component = component;
        }

        void a(ComponentNode componentNode) {
            this.dependencies.add(componentNode);
        }

        void b(ComponentNode componentNode) {
            this.dependents.add(componentNode);
        }

        Component c() {
            return this.component;
        }

        Set d() {
            return this.dependencies;
        }

        boolean e() {
            return this.dependencies.isEmpty();
        }

        boolean f() {
            return this.dependents.isEmpty();
        }

        void g(ComponentNode componentNode) {
            this.dependents.remove(componentNode);
        }
    }

    private static class Dep {
        private final Qualified<?> anInterface;
        private final boolean set;

        public boolean equals(Object obj) {
            if (!(obj instanceof Dep)) {
                return false;
            }
            Dep dep = (Dep) obj;
            return dep.anInterface.equals(this.anInterface) && dep.set == this.set;
        }

        public int hashCode() {
            return ((this.anInterface.hashCode() ^ 1000003) * 1000003) ^ Boolean.valueOf(this.set).hashCode();
        }

        private Dep(Qualified<?> qualified, boolean z2) {
            this.anInterface = qualified;
            this.set = z2;
        }
    }

    static void a(List list) {
        Set<ComponentNode> graph = toGraph(list);
        Set<ComponentNode> roots = getRoots(graph);
        int i2 = 0;
        while (!roots.isEmpty()) {
            ComponentNode next = roots.iterator().next();
            roots.remove(next);
            i2++;
            for (ComponentNode componentNode : next.d()) {
                componentNode.g(next);
                if (componentNode.f()) {
                    roots.add(componentNode);
                }
            }
        }
        if (i2 == list.size()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (ComponentNode componentNode2 : graph) {
            if (!componentNode2.f() && !componentNode2.e()) {
                arrayList.add(componentNode2.c());
            }
        }
        throw new DependencyCycleException(arrayList);
    }

    private static Set<ComponentNode> getRoots(Set<ComponentNode> set) {
        HashSet hashSet = new HashSet();
        for (ComponentNode componentNode : set) {
            if (componentNode.f()) {
                hashSet.add(componentNode);
            }
        }
        return hashSet;
    }

    private static Set<ComponentNode> toGraph(List<Component<?>> list) {
        Set<ComponentNode> set;
        HashMap map = new HashMap(list.size());
        Iterator<Component<?>> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                Iterator it2 = map.values().iterator();
                while (it2.hasNext()) {
                    for (ComponentNode componentNode : (Set) it2.next()) {
                        for (Dependency dependency : componentNode.c().getDependencies()) {
                            if (dependency.isDirectInjection() && (set = (Set) map.get(new Dep(dependency.getInterface(), dependency.isSet()))) != null) {
                                for (ComponentNode componentNode2 : set) {
                                    componentNode.a(componentNode2);
                                    componentNode2.b(componentNode);
                                }
                            }
                        }
                    }
                }
                HashSet hashSet = new HashSet();
                Iterator it3 = map.values().iterator();
                while (it3.hasNext()) {
                    hashSet.addAll((Set) it3.next());
                }
                return hashSet;
            }
            Component<?> next = it.next();
            ComponentNode componentNode3 = new ComponentNode(next);
            for (Qualified<? super Object> qualified : next.getProvidedInterfaces()) {
                Dep dep = new Dep(qualified, !next.isValue());
                if (!map.containsKey(dep)) {
                    map.put(dep, new HashSet());
                }
                Set set2 = (Set) map.get(dep);
                if (!set2.isEmpty() && !dep.set) {
                    throw new IllegalArgumentException(String.format("Multiple components provide %s.", qualified));
                }
                set2.add(componentNode3);
            }
        }
    }
}
