package com.gorauskas.util.structures

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import io.kotlintest.shouldBe

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DoublyLinkedListTest {

    private var dll = DoublyLinkedList<String>()

    @BeforeAll
    fun init() {
        (0..9).forEach {
            dll.insertLast(Node<String>("string $it"))
        }
    }

    @Test
    @Order(1)
    fun `doubly linked list size is ten test`() {
        dll.size.shouldBe(DoublyLinkedListTestHelper.LIST_SIZE_01)
    }

    @Test
    @Order(2)
    fun `doubly linked list first last current test`() {
        dll.first.toString().shouldBe(DoublyLinkedListTestHelper.ITEM_FIRST)
        dll.last.toString().shouldBe(DoublyLinkedListTestHelper.ITEM_LAST)
        dll.current.toString().shouldBe(DoublyLinkedListTestHelper.ITEM_CURRENT)
    }

    @Test
    @Order(3)
    fun `doubly linked list seek next test`() {
        dll.seekNext().toString().shouldBe(DoublyLinkedListTestHelper.ITEM_SEEK_NEXT)
    }

    @Test
    @Order(4)
    fun `doubly linked list seek node test`() {
        dll.seekNode(DoublyLinkedListTestHelper.NODE_TO_SEEK).toString()
            .shouldBe(DoublyLinkedListTestHelper.ITEM_SEEK_NODE)
    }

    @Test
    @Order(5)
    fun `doubly linked list seek previous test`() {
        dll.seekPrevious().toString().shouldBe(DoublyLinkedListTestHelper.ITEM_SEEK_PREVIOUS)
    }

    @Test
    @Order(6)
    fun `doubly linked list seek last test`() {
        dll.seekLast().toString().shouldBe(DoublyLinkedListTestHelper.ITEM_SEEK_LAST)
    }

    @Test
    @Order(7)
    fun `doubly linked list seek first test`() {
        dll.seekFirst().toString().shouldBe(DoublyLinkedListTestHelper.ITEM_SEEK_FIRST)
    }

    @Test
    @Order(8)
    fun `doubly linked list remove test`() {
        dll.remove(dll.seekNode(DoublyLinkedListTestHelper.NODE_TO_SEEK))
        dll.size.shouldBe(DoublyLinkedListTestHelper.LIST_SIZE_02)
    }

    @Test
    @Order(9)
    fun `doubly linked list insert before test`() {
        dll.insertBefore(
            dll.seekNode(DoublyLinkedListTestHelper.NODE_TO_SEEK)!!,
            DoublyLinkedListTestHelper.NEW_NODE_01
        )
        dll.size.shouldBe(DoublyLinkedListTestHelper.LIST_SIZE_01)
        dll.seekNode(DoublyLinkedListTestHelper.NODE_TO_SEEK).toString()
            .shouldBe(DoublyLinkedListTestHelper.NEW_NODE_01.toString())
    }

    @Test
    @Order(10)
    fun `doubly linked list insert after test`() {
        dll.insertAfter(
            dll.seekNode(DoublyLinkedListTestHelper.NODE_TO_SEEK)!!,
            DoublyLinkedListTestHelper.NEW_NODE_02
        )
        dll.size.shouldBe(DoublyLinkedListTestHelper.LIST_SIZE_03)
        dll.seekNode(DoublyLinkedListTestHelper.NODE_TO_SEEK).toString()
            .shouldBe(DoublyLinkedListTestHelper.NEW_NODE_01.toString())
        dll.seekNext().toString().shouldBe(DoublyLinkedListTestHelper.NEW_NODE_02.toString())
    }

    @Test
    @Order(11)
    fun `doubly linked list insert first test`() {
        dll.insertFirst(DoublyLinkedListTestHelper.NEW_NODE_03)
        dll.first.shouldBe(DoublyLinkedListTestHelper.NEW_NODE_03)
    }

    @Test
    @Order(12)
    fun `doubly linked list insert last test`() {
        dll.insertLast(DoublyLinkedListTestHelper.NEW_NODE_04)
        dll.last.shouldBe(DoublyLinkedListTestHelper.NEW_NODE_04)
    }

    @Test
    @Order(13)
    fun `doubly linked list seek index test`() {
        val idx = dll.seekIndex(DoublyLinkedListTestHelper.NEW_NODE_02)
        idx.shouldBe(DoublyLinkedListTestHelper.INDEX_TO_SEEK)
    }

    @Test
    @Order(14)
    fun `doubly linked list clear test`() {
        dll.clear()
        dll.size.shouldBe(DoublyLinkedListTestHelper.LIST_SIZE_04)
        dll.first.shouldBe(null)
    }

    @Test
    @Order(15)
    fun `doubly linked list is empty test`() {
        dll.isEmpty.shouldBe(true)
    }
}

object DoublyLinkedListTestHelper {
    const val LIST_SIZE_01 = 10
    const val LIST_SIZE_02 = 9
    const val LIST_SIZE_03 = 11
    const val LIST_SIZE_04 = 0
    const val NODE_TO_SEEK = 5L
    const val INDEX_TO_SEEK = 7L
    val NEW_NODE_01 = Node("String A")
    val NEW_NODE_02 = Node("String X")
    val NEW_NODE_03 = Node("String Y")
    val NEW_NODE_04 = Node("String Z")
    const val ITEM_FIRST = "Node(value: string 0)"
    const val ITEM_LAST = "Node(value: string 9)"
    const val ITEM_CURRENT = "Node(value: string 0)"
    const val ITEM_SEEK_NEXT = "Node(value: string 1)"
    const val ITEM_SEEK_NODE = "Node(value: string 5)"
    const val ITEM_SEEK_PREVIOUS = "Node(value: string 4)"
    const val ITEM_SEEK_LAST = "Node(value: string 9)"
    const val ITEM_SEEK_FIRST = "Node(value: string 0)"
}