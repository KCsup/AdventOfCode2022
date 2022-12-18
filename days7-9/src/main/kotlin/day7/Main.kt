package day7

fun main() {
    val lines =  java.io.File(System.getProperty("user.dir") + "/src/main/kotlin/day7/day7input.txt").readLines()
    val root = Folder("root")
    var testArray = emptyArray<String>()
    testArray.forEach { println(it) }
    testArray += "eeee"
    testArray.forEach { println(it) }

    var currentOut: Array<String> = emptyArray()
    var currentDir = "root"
    for(line in lines) {
        val lineArgs = line.split(" ")
        if(lineArgs[0] == "$") {
            when(lineArgs[1]) {
                "ls" -> {
                    if(currentOut.isNotEmpty()) parseListCommand(currentOut)
                    currentOut = emptyArray()
                }
                "cd" -> {

                }
            }
        }

        currentOut += line
    }
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

fun getFolderSize(folder: Folder, currentSize: Int): Int {
    var size = currentSize
    for(file in folder.subFiles) {
        if (file !is Folder) {
            size += file.size
            continue
        }

        size += getFolderSize(file, size)
    }

    return size
}

fun getFolderSize(folder: Folder): Int {
    return getFolderSize(folder, 0)
}

fun findFile(file: File, start: Folder, fromDir: String): String {
    if(start.subFiles.contains(file)) return fromDir + start.name

    for(f in start.subFiles) {
        if(f is Folder) {
            val potentialFind = findFile(file, f, start.name + "/")

            if (potentialFind != "") return potentialFind
        }
    }

    return ""
}

fun findFile(file: File, start: Folder): String {
    return findFile(file, start, "")
}


open class File(open val name: String, val size: Int)

class Folder(override val name: String, val subFiles: MutableList<File>) : File(name, 0) {
    constructor(name: String) : this(name, mutableListOf())
    fun addFile(file: File) = subFiles.add(file)

    fun getSubFile(path: String): File? {
        return subFiles.find { it.name == path }
    }
}
