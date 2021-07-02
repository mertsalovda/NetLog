package ru.mertsalovda.netlog

class ObservableList<T>(initialValue: T) {

    private var value: T = initialValue

    private val subscribers = mutableListOf<Subscriber<T>>()

    fun subscribe(subscriber: Any, callback: (T) -> Unit) {
        subscribers.add(Subscriber(subscriber, callback))
    }

    fun unsubscribe(subscriber: Any) {
        val foundSubscriber = subscribers.firstOrNull { it.subscriber == subscriber }
        subscribers.remove(foundSubscriber)
    }

    fun getValue(): T = value

    fun setValue(newValue: T) {
        value = newValue
        for (subscriber in subscribers) {
            subscriber.callback.invoke(value)
        }
    }

    private data class Subscriber<R>(val subscriber: Any, val callback: (R) -> Unit)
}