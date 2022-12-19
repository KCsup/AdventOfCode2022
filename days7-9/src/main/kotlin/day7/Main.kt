package day7

import java.util.*

fun main() {
    val lines =  java.io.File(System.getProperty("user.dir") + "/src/main/kotlin/day7/day7input.txt").readLines().toMutableList()
    lines.removeFirst()

    val root = Folder("root")
    var currentDir = root
    var totalUnder = 0
    for(l in lines.indices) {
        val lineArgs = lines[l].split(" ")
        if(lineArgs[0] == "$") {
            when(lineArgs[1]) {
                "ls" -> {
                    var lineCheck = l + 1
                    var listOutput: Array<String> = emptyArray()
                    while(lineCheck < lines.size && lines[lineCheck].split(" ")[0] != "$") {
                        listOutput += lines[lineCheck]
                        lineCheck += 1
                    }

                    if(listOutput.isNotEmpty()) {
                        currentDir.setSubFiles(parseListCommand(listOutput))
                    }
                }
                "cd" -> {
                    if(lineArgs[2] == "..") {
                        val parent = getParentFile(currentDir, root)
                        if(parent != null) currentDir = parent
                    }
                    else {
                        val subFile = currentDir.getSubFile(lineArgs[2])
                        if(subFile != null && subFile is Folder) currentDir = subFile
                    }
                }
            }
        }
    }


    println("Total Under 100,000: ${parseTotal(root)}")

    // Part 2

    val unusedSpace = 70000000 - getFolderSize(root)
    val minimumDelete = 30000000 - unusedSpace
    println(minimumDelete)
    val deleteFolder = findMinimumDelete(root, minimumDelete)
    if(deleteFolder != null)
        println("File to Be Deleted Size: ${getFolderSize(deleteFolder)}")
}

fun findMinimumDelete(tree: Folder, minimum: Int): Folder? {
    var delete: Folder? = null

    for(file in tree.subFiles) {
        if(file !is Folder) continue

        val size = getFolderSize(file)
        if(size >= minimum && (delete == null || size < getFolderSize(delete))) delete = file

        val potentialDelete = findMinimumDelete(file, minimum)
        if(delete == null || (potentialDelete != null && getFolderSize( potentialDelete) < getFolderSize(delete)))
            delete = potentialDelete
    }

    return delete
}

fun parseTotal(tree: Folder): Int {
    var total = 0

    for(file in tree.subFiles) {
        if(file !is Folder) continue

        val size = getFolderSize(file)
        if(size <= 100000) total += size

        total += parseTotal(file)
    }

    return total
}


fun parseListCommand(output: Array<String>): MutableList<File> {
    val outList = mutableListOf<File>()

    for(o in output) {
        val args = o.split(" ")
        if(args[0] == "dir")
            outList.add(Folder(args[1]))
        else
            outList.add(File(args[1], args[0].toInt()))
    }

    return outList
}

fun getFolderSize(folder: Folder): Int {
    var size = 0
    for(file in folder.subFiles) {
        if (file !is Folder) {
            size += file.size
            continue
        }

        size += getFolderSize(file)
    }

    return size
}


fun getParentFile(file: File, start: Folder): Folder? {
    if(start.subFiles.contains(file)) return start

    for(f in start.subFiles) {
        if(f is Folder) {
            val potentialFind = getParentFile(file, f)

            if (potentialFind != null) return potentialFind
        }
    }

    return null
}


open class File(open val name: String, val size: Int)

class Folder(override val name: String, val subFiles: MutableList<File>) : File(name, 0) {
    constructor(name: String) : this(name, mutableListOf())
    fun addFile(file: File) = subFiles.add(file)

    fun setSubFiles(newFiles: MutableList<File>) {
        subFiles.clear()

        newFiles.forEach { subFiles.add(it) }
    }

    fun getSubFile(path: String): File? {
        return subFiles.find { it.name == path }
    }
}
