import shared.TopologicalSortAdjacencyList.Companion.Edge
import shared.TopologicalSortAdjacencyList.Companion.dagShortestPath
import shared.TopologicalSortAdjacencyList.Companion.topologicalSort


fun doStuff(input: List<String>) {
    val valves = parseInput(input)
    val nodes = valves.map { it.name }



}

fun parseInput(input: List<String>): List<Valve> {
    return input.map { line ->
        Valve(
            line.substringAfter("Valve ").substringBefore(" has flow"),
            line.substringAfter("rate=").substringBefore(";").toInt(),
            line.substringAfter("to valves ").split(",").map { it.trim() }
        )
    }
}

fun buildAdjacencies(valves: List<Valve>): Map<Valve, List<String>> = valves.associateWith { it.adjacencies }

operator fun Map<Valve, List<String>>.get(valveName: String) = this.entries.firstOrNull { it.key.name == valveName }
operator fun MutableMap<Valve, List<String>>.set(valve: Valve, other: Valve) {
    val adj = this[valve]!!
    this.remove(valve)
    this[other] = adj
}

data class Valve(val name: String, val flowRate: Int, val adjacencies: List<String>, val isOpen: Boolean = false)





fun main(args: Array<String>) {
    // Graph setup
    val nodes = listOf("AA", "BB", "CC", "DD", "EE", "FF", "GG")
    val graph: MutableMap<String, MutableList<Edge<String>>> = mutableMapOf()
    nodes.forEach {
        graph[it] = mutableListOf()
    }
    graph["AA"]!!.add(Edge("AA", "BB", 3))
    graph["AA"]!!.add(Edge("AA", "CC", 2))
    graph["AA"]!!.add(Edge("AA", "FF", 3))
    graph["BB"]!!.add(Edge("BB", "DD", 1))
    graph["BB"]!!.add(Edge("BB", "CC", 6))
    graph["CC"]!!.add(Edge("CC", "DD", 1))
    graph["CC"]!!.add(Edge("CC", "EE", 10))
    graph["DD"]!!.add(Edge("DD", "EE", 5))
    graph["FF"]!!.add(Edge("FF", "EE", 7))
    val ordering = topologicalSort(graph, nodes.size)

    // Prints: [GG, AA, FF, BB, CC, DD, EE]
    println(ordering.contentToString())

    // Finds all the shortest paths starting at node 0
    val dists = dagShortestPath(graph, "AA", nodes)

    // Find the shortest path from 0 to 4 which is 8.0
    println(dists["EE"])

    // Find the shortest path from 0 to 6 which
    // is null since 6 is not reachable!
    println(dists["GG"])
}
