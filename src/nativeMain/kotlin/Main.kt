fun main() {
    println("Hello, enter your name:")
    val name = readln()

    name.replace(" ", "").let {
        println("Your name contains ${it.length} letters")
    }
}
