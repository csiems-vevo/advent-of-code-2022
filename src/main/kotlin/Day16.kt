import shared.TopologicalSortAdjacencyList.Companion.Edge
import shared.TopologicalSortAdjacencyList.Companion.dagLongestPath
import shared.TopologicalSortAdjacencyList.Companion.topologicalSort


fun doStuff(input: List<String>) {
    val valves = parseInput(input)
    val graph: MutableMap<String, MutableList<Edge<String>>> = mutableMapOf()
    valves.forEach {from ->
        graph[from.name] = mutableListOf()
        from.adjacencies.forEach { to ->
            val toFlowRate = valves.first { it.name == to }.flowRate
            graph[from.name]!!.add(Edge(from.name, to, toFlowRate))
        }
    }
    val ordering = topologicalSort(graph, valves.size)
    println(ordering.contentToString())
    val dists = dagLongestPath(graph, "AA", valves.map { it.name })
    println(dists)
}

fun parseInput(input: List<String>): List<Valve> {
    return input.map { line ->
        Valve(
            line.substringAfter("Valve ").substringBefore(" has flow"),
            line.substringAfter("rate=").substringBefore(";").toInt(),
            line.substringAfter("to valves ").substringAfter("to valve ").split(",").map { it.trim() }
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

