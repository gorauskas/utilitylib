package com.gorauskas.util.structures

import java.util.Deque
import java.util.ArrayDeque

/**
 * A history data structure that provides the same functionality
 * as a browser history, but allows you to use any type. Only
 * depends on java.util.Deque internally. Basically, there are
 * 2 stacks (back and 4ward) with a gap in the middle for the
 * current item.
 * @param <T> The type parameter to hold in history
 */
class History<T: Any>(item: T? = null) {

    private var _F: Deque<T> = ArrayDeque()  // forward
    private var _B: Deque<T> = ArrayDeque()  // backward
    private var _C: T? = null                // current

    /**
     * Tells if the current pointer can be moved forward in history.
     */
    val canMoveForward: Boolean
        get() = this._F.count() > 0

    /**
     * Tells if the current pointer can be moved backward in history.
     */
    val canMoveBackward: Boolean
        get() = this._B.count() > 0

    /**
     * Returns the current item in the history. Pushes the state of the
     * current pointer into the backward stack and sets the new current
     * item. If the forward stack has items in it then clear that too.
     * @param value An item of type T
     */
    var current: T?
        get() = this._C
        set(value) {
            if (this.canMoveForward) {
                this._F.clear()
            }

            if (this._C != null) {
                this._B.push(this._C)
            }

            this._C = value
        }

    /**
     * Moves the current pointer one item forward in history
     * @return The new current item
     */
    fun next(): T? {
        if (this.canMoveForward) {
            this._B.push(this._C)
            this._C = this._F.pop()
        }

        return this.current
    }

    /**
     * Moves the current pointer one item back in history
     * @return The new current item
     */
    fun previous(): T? {
        if (this.canMoveBackward) {
            this._F.push(this._C)
            this._C = this._B.pop()
        }

        return this.current
    }

    /**
     * Dumps the entire history in chronological order to a List of T
     * Chrono order means LIFO
     * @return An ordered list of history items
     */
    fun dump(): List<T> =
        mutableListOf<T>().also {
            if (this.canMoveForward) {
                it.addAll(this._F.toList().reversed())
            }

            if (this._C != null) {
                it.add(this._C!!)
            }

            if (this.canMoveBackward) {
                it.addAll(this._B.toList())
            }
        }

}