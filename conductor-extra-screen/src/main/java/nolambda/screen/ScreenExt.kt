package nolambda.screen

fun <T> T.asSingleEvent() = SingleEvent(this)
