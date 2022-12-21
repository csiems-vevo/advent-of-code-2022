package shared



/*
fun main(args: Array<String>) {
    // Graph setup
    val nodes = listOf("AA", "BB", "CC", "DD", "EE", "FF", "GG")
    val graph: MutableMap<String, MutableList<TopologicalSortAdjacencyList.Companion.Edge<String>>> = mutableMapOf()
    nodes.forEach {
        graph[it] = mutableListOf()
    }
    graph["AA"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("AA", "BB", 3))
    graph["AA"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("AA", "CC", 2))
    graph["AA"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("AA", "FF", 3))
    graph["BB"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("BB", "DD", 1))
    graph["BB"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("BB", "CC", 6))
    graph["CC"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("CC", "DD", 1))
    graph["CC"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("CC", "EE", 10))
    graph["DD"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("DD", "EE", 5))
    graph["FF"]!!.add(TopologicalSortAdjacencyList.Companion.Edge("FF", "EE", 7))
    val ordering = TopologicalSortAdjacencyList.topologicalSort(graph, nodes.size)

    // Prints: [GG, AA, FF, BB, CC, DD, EE]
    println(ordering.contentToString())

    // Finds all the shortest paths starting at node 0
    val dists = TopologicalSortAdjacencyList.dagShortestPath(graph, "AA", nodes)

    // Find the shortest path from 0 to 4 which is 8.0
    println(dists["EE"])

    // Find the shortest path from 0 to 6 which
    // is null since 6 is not reachable!
    println(dists["GG"])
}
*/

class TopologicalSortAdjacencyList<T> {

    companion object {
        // describe edges in the graph
        class Edge<T>(var from: T, var to: T, var weight: Int)

        // Finds a topological ordering of the nodes in a Directed Acyclic Graph (DAG)
        // The input to this function is an adjacency list for a graph and the number
        // of nodes in the graph.
        //
        // NOTE: 'numNodes' is not necessarily the number of nodes currently present
        // in the adjacency list since you can have singleton nodes with no edges which
        // wouldn't be present in the adjacency list but are still part of the graph!
        //
        inline fun <reified T> topologicalSort(graph: Map<T, List<Edge<T>>>, numNodes: Int): Array<T> {
            val ordering = graph.keys.toTypedArray()
            val visited = graph.keys.associateWith { false }.toMutableMap()
            var i = numNodes - 1
            graph.keys.forEach {
                if (!visited[it]!!) {
                    i = dfs(i, it, visited, ordering, graph)
                }
            }
            return ordering
        }

        // A useful application of the topological sort is to find the shortest path
        // between two nodes in a Directed Acyclic Graph (DAG). Given an adjacency list
        // this method finds the shortest path to all nodes starting at 'start'
        //
        // NOTE: 'numNodes' is not necessarily the number of nodes currently present
        // in the adjacency list since you can have singleton nodes with no edges which
        // wouldn't be present in the adjacency list but are still part of the graph!
        //
        inline fun <reified T> dagShortestPath(graph: MutableMap<T, MutableList<Edge<T>>>, start: T, nodes: List<T>): MutableMap<T, Int> {
            return findPath(nodes, graph, start)
        }

        inline fun <reified T> dagLongestPath(graph: MutableMap<T, MutableList<Edge<T>>>, start: T, nodes: List<T>): MutableMap<T, Int> {
            val longestPathGraph = graph.withEdgesInverted().toMap()
            val dist = findPath(nodes, longestPathGraph, start)
            return dist.keys.associateWith { key ->
                dist[key]?.times(-1) ?: -1
            }.toMutableMap()
        }

        // performs a depth first search on the graph to give
        // us the topological ordering we want. Instead of maintaining a stack
        // of the nodes we see we simply place them inside the ordering array
        // in reverse order for simplicity.
        fun <T> dfs(
            index: Int, at: T, visited: MutableMap<T, Boolean>, ordering: Array<T>, graph: Map<T, List<Edge<T>>>
        ): Int {
            var i = index
            visited[at] = true
            val edges = graph[at]
            if (edges != null) {
                for (edge in edges) {
                    if (!visited[edge.to]!!) {
                        i = dfs(i, edge.to, visited, ordering, graph)
                    }
                }
            }
            ordering[i] = at
            return i - 1
        }

        inline fun <reified T> findPath(
            nodes: List<T>,
            graph: Map<T, MutableList<Edge<T>>>,
            start: T
        ): MutableMap<T, Int> {
            val numNodes = nodes.size
            val topsort = topologicalSort(graph, numNodes)
            val dist = mutableMapOf<T, Int>()
            dist[start] = 0
            topsort.forEach {
                val adjacentEdges = graph[it]
                if (adjacentEdges != null) {
                    for (edge in adjacentEdges) {
                        val newDist = dist[it]!! + edge.weight
                        if (dist[edge.to] == null) {
                            dist[edge.to] = newDist
                        } else dist[edge.to] = (dist[edge.to]!!).coerceAtMost(newDist)
                    }
                }
            }
            return dist
        }

        fun <T>  MutableMap<T, MutableList<Edge<T>>>.withEdgesInverted(): MutableMap<T, MutableList<Edge<T>>> {
            this.keys.forEach {
                val reversedEdges = this[it]!!.map { edge ->
                    Edge(edge.from, edge.to, edge.weight * -1)
                }.toMutableList()
                this[it] = reversedEdges
            }
            return this
        }
    }
}

