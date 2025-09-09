package com.google.common.graph;

import com.google.common.annotations.Beta;
import java.util.Set;

@Beta
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class GraphsBridgeMethods {
    GraphsBridgeMethods() {
    }

    public static <N> Set<N> reachableNodes(Graph<N> graph, N n2) {
        return Graphs.reachableNodes((Graph) graph, (Object) n2);
    }

    public static <N> Graph<N> transitiveClosure(Graph<N> graph) {
        return Graphs.transitiveClosure((Graph) graph);
    }
}
