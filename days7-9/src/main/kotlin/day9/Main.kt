package day9

val lines =  java.io.File(System.getProperty("user.dir") + "/src/main/kotlin/day9/day9input.txt").readLines()

fun main() {

    val ropePoints: Array<Pair<Double, Double>> = Array(10) { Pair(0.0, 0.0) }

    val visitedTail: MutableList<Pair<Double, Double>> = mutableListOf()

    for(line in lines) {
        val commandArgs = line.split(" ")
        repeat(commandArgs[1].toInt()) {
            when (commandArgs[0]) {
                "R" -> ropePoints[0] = Pair(ropePoints[0].first + 1, ropePoints[0].second)
                "L" -> ropePoints[0] = Pair(ropePoints[0].first - 1, ropePoints[0].second)
                "U" -> ropePoints[0] = Pair(ropePoints[0].first, ropePoints[0].second + 1)
                "D" -> ropePoints[0] = Pair(ropePoints[0].first, ropePoints[0].second - 1)
            }

            for(p in 1 until ropePoints.size) {
                val updatedTail =
                    getUpdatedRopePoints(ropePoints[p - 1].first, ropePoints[p - 1].second, ropePoints[p].first, ropePoints[p].second)

                ropePoints[p] = Pair(updatedTail.first, updatedTail.second)

                if(p == ropePoints.size - 1 && !visitedTail.contains(ropePoints[p])) visitedTail.add(ropePoints[p])
            }


        }
    }

    println("Final Head:(${ropePoints[0].first}, ${ropePoints[0].first})")
    println("Final Tail:(${ropePoints.last().first}, ${ropePoints.last().second})")
    println("Number of Unique Spots: ${visitedTail.size}")


}

fun getDistanceHorizontal(xInitial: Double, xFinal: Double) = xFinal - xInitial

fun getDistanceVertical(yInitial: Double, yFinal: Double) = yFinal - yInitial

fun getUpdatedRopePoints(xHead: Double, yHead: Double, xTail: Double, yTail: Double): Pair<Double, Double> {
    var newXTail = xTail
    var newYTail = yTail
    val distH = getDistanceHorizontal(xTail, xHead)
    val distV = getDistanceVertical(yTail, yHead)
    when {
        (distH > 1 && distV >= 1) || (distV > 1 && distH >= 1) -> { // Top Right
            newXTail += 1
            newYTail += 1
        }
        (distH < -1 && distV >= 1) || (distV > 1 && distH <= -1) -> { // Top Left
            newXTail -= 1
            newYTail += 1
        }
        (distH < -1 && distV <= -1) || (distV < -1 && distH <= -1) -> { // Bottom Left
            newXTail -= 1
            newYTail -= 1
        }
        (distH > 1 && distV <= -1) || (distV < -1 && distH >= 1) -> { // Bottom Right
            newXTail += 1
            newYTail -= 1
        }
        distH > 1 -> newXTail += 1 // Right
        distH < -1 ->  newXTail -= 1 // Left
        distV > 1 -> newYTail += 1 // Top
        distV < -1 -> newYTail -= 1 // Bottom
    }

    return Pair(newXTail, newYTail)
}
