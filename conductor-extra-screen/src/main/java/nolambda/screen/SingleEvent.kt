package nolambda.screen

class SingleEvent<out T>(private val value: T) {
    private var fetched: Boolean = false

    fun run(block: T.() -> Unit) {
        if (!fetched) {
            fetched = true
            block(value)
        }
    }

    fun get(): T? {
        if (fetched) return null
        fetched = true
        return value
    }
}
