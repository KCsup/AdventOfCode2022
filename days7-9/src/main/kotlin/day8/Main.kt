package day8

val lines =  java.io.File(System.getProperty("user.dir") + "/src/main/kotlin/day8/day8input.txt").readLines()

fun main() {


    var totalVisible = 0
    var highestScore = 0
    for(y in lines.indices) {
        for(x in lines[y].indices) {
            if(y == 0 || y == lines.size - 1 || x == 0 || x == lines[y].length - 1) {
                totalVisible += 1
                continue
            }

            if(anyVisible(x, y)) totalVisible += 1

            val score = getScenicScore(x, y)
            if(score > highestScore) {
                highestScore = score
            }
        }
    }

    println("Total Visible: $totalVisible")

    println("Highest Scenic Score: $highestScore")
}

fun getLevelAt(x: Int, y: Int): Int = Integer.parseInt(lines[y][x].toString())

fun getScenicScore(x: Int, y: Int): Int {
    val currentLevel = getLevelAt(x, y)

    var leftScore = 0
    for(checkLeft in x - 1 downTo 0) {
        if(getLevelAt(checkLeft, y) >= currentLevel) {
            leftScore += 1
            break
        }

        leftScore += 1
    }

    var rightScore = 0
    for(checkRight in x + 1 until lines[y].length) {
        if(getLevelAt(checkRight, y) >= currentLevel) {
            rightScore += 1
            break
        }

        rightScore += 1
    }

    var upScore = 0
    for(checkUp in y - 1 downTo 0) {
        if(getLevelAt(x, checkUp) >= currentLevel) {
            upScore += 1
            break
        }

        upScore += 1
    }

    var downScore = 0
    for(checkDown in y + 1 until lines.size) {
        if(getLevelAt(x, checkDown) >= currentLevel) {
            downScore += 1
            break
        }

        downScore += 1
    }

    return leftScore * rightScore * upScore * downScore
}

fun anyVisible(x: Int, y: Int): Boolean {
    val currentLevel = getLevelAt(x, y)
    for(checkX in 0 until x) {
        if(getLevelAt(checkX, y) >= currentLevel) break

        if(checkX == x - 1) {
            return true
        }
    }

    for(checkX in x + 1 until lines[y].length) {
        if(getLevelAt(checkX, y) >= currentLevel) break

        if(checkX == lines[y].length - 1) {
            return true
        }
    }

    for(checkY in 0 until y) {
        if(getLevelAt(x, checkY) >= currentLevel) break

        if(checkY == y - 1) {
            return true
        }
    }

    for(checkY in y + 1 until lines.size) {
        if(getLevelAt(x, checkY) >= currentLevel) break

        if(checkY == lines.size - 1) {
            return true
        }
    }

    return false
}
