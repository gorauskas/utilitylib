package com.gorauskas.util.structures

import java.lang.StringBuilder

/**
 * Node of T is a DoublyLinkedList Node wrapper class around T, which is the
 * actual data we want to hold in the list
 */
class Node<T> (
    var next: Node<T>?,
    var previous: Node<T>?,
    var value: T?
) {
    constructor(value: T?): this(null, null, value)

    override fun toString() =
        "Node(value: ${value})"
}

/**
 * This implementation of a DoublyLinkedList is what's called an intrusive list.
 * These lists require the _reference_ properties to be embedded directly into the
 * object being linked. Unlike externally linked lists, where a separate object is
 * used to hold a reference to the object and previous/next references, the intrusive
 * list makes its presence known in the object being linked. See Node class above.
 *
 * Uses less memory: Because the reference fields are in the Node itself, it is no
 * longer necessary to keep other references to link an item onto a list. Traversing
 * the list is always one reference away to the next item. Removing from the list is
 * simply a matter of clearing the reference links. Can be implemented purely with
 * pojos.
 *
 * IRL this can be used for: Represent a deck of cards in a game; Browser history;
 * Undo-Redo functionality; etc.
 *
 * Caveat: have not tested thread safety
 */
class DoublyLinkedList<T> {

    /**
     * A reference to the first node of T in the list
     */
    var first: Node<T>? = null

    /**
     * A reference to the last node of T in the list
     */
    var last: Node<T>? = null

    /**
     * A refrerence to the current node of T in the list
     */
    var current: Node<T>? = null

    /**
     * The current size of the list
     */
    var size: Long = 0

    /**
     * Is the list currently empty?
     */
    val isEmpty: Boolean
        get() {
            return first == null
        }

    /**
     * Inserts a new node of T at the first position in the list
     * @param new the new node of T to insert
     */
    fun insertFirst(new: Node<T>) {
        if (first == null) {
            first = new
            last = first
            current = first
            size++
        } else {
            insertBefore(first!!, new)
        }
    }

    /**
     * Insert a new node before the given node
     * @param node the given node to insert before
     * @param new the new node of T to be inserted
     */
    fun insertBefore(node: Node<T>, new: Node<T>) {
        new.next = node

        if (node.previous == null) {
            new.previous = null
            first = new
        } else {
            new.previous = node.previous
            node.previous!!.next = new
        }

        node.previous = new
        size++
    }

    /**
     * Insert a new node after the given node
     * @param node the given node to insert after
     * @param new the new node of T to be inserted
     */
    fun insertAfter(node: Node<T>, new: Node<T>) {
        new.previous = node

        if (node.next == null) {
            new.next = null
            last = new
        } else {
            new.next = node.next
            node.next!!.previous = new
        }

        node.next = new
        size++
    }

    /**
     * Inserts a new node of T at the last position in the list
     * @param new the new node of T to insert
     */
    fun insertLast(new: Node<T>) {
        if (last == null) {
            insertFirst(new)
        } else {
            insertAfter(last!!, new)
        }
    }

    /**
     * Removes the node of T from the list
     * @param node the node of T to be removed
     */
    fun remove(node: Node<T>?) {
        if (node == null) return

        if (node.previous == null) {
            first = node.next
        } else {
            node.previous!!.next = node.next
        }

        if (node.next == null) {
            last = node.previous
        } else {
            node.next!!.previous = node.previous
        }

        size--
    }

    /**
     * Clears the list
     */
    fun clear() {
        var node = first
        remove(first)
        while (node != null) {
            node = node.next
            remove(node)
        }
    }

    /**
     * Go to the first node
     * @return the first node of T
     */
    fun seekFirst(): Node<T>? {
        current = first
        return current
    }

    /**
     * Go to the previous node from the current
     * @return the previous node of T
     */
    fun seekPrevious(): Node<T>? {
        return if (current?.previous == null) {
            first
        } else {
            current = current!!.previous
            current
        }
    }

    /**
     * Go to the next node from the current
     * @return the next node of T
     */
    fun seekNext(): Node<T>? {
        return if (current?.next == null) {
            last
        } else {
            current = current!!.next
            current
        }
    }

    /**
     * Go to the last node
     * @return the last node of T
     */
    fun seekLast(): Node<T>? {
        current = last
        return current
    }

    /**
     * Seek a node given an index
     */
    fun seekNode(index: Long): Node<T>? {
        if (index < 0 || index > size) {
            throw IllegalArgumentException("Index out of bounds")
        }

        if (index == 0L) return first
        if (index == size) return last

        var count = 1L
        var node = first?.next

        while (count < index && node != null) {
            count++
            node = node.next
        }

        current = node
        return current
    }

    /**
     * Seek an index given a node
     */
    fun seekIndex(node: Node<T>): Long {
        var n = first
        var c = 0L

        while (n !== node && c <= size) {
            n = n?.next
            c++
        }

        return c
    }

    /**
     * Prints the list in a human-readable format
     */
    fun dump() {
        println("DoublyLinkedList(${this})")
    }

    override fun toString(): String =
        StringBuilder().also {
            var node = first
            while (node != null) {
                it.append("${node}, ")
                node = node.next
            }
        }.toString()

}

