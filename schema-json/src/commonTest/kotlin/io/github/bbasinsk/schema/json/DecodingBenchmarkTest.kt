package io.github.bbasinsk.schema.json

import io.github.bbasinsk.schema.Schema
import io.github.bbasinsk.schema.Schema.Companion.case
import io.github.bbasinsk.schema.json.decodeFromJsonString
import io.github.bbasinsk.schema.json.kotlinx.decodeFromJsonString as kotlinxDecodeFromJsonString
import io.github.bbasinsk.validation.Validation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.measureTime

class DecodingBenchmarkTest {

    // DTOs

    data class Address(val street: String, val city: String, val state: String, val zip: String, val country: String)
    data class Customer(val name: String, val email: String, val age: Int, val verified: Boolean, val tags: List<String>)
    data class LineItem(val sku: String, val name: String, val qty: Int, val price: Double, val discount: Double?)

    sealed interface OrderStatus {
        data class Shipped(val trackingId: String) : OrderStatus
        data class Delivered(val date: String) : OrderStatus
    }

    data class Order(
        val id: String, val amount: Double, val currency: String,
        val customer: Customer, val items: List<LineItem>,
        val shipping: Address, val billing: Address,
        val status: OrderStatus, val notes: String?,
        val metadata: Map<String, String>
    )

    // Schemas

    private fun Schema.Companion.address(): Schema<Address> = record(
        field(string(), "street") { street },
        field(string(), "city") { city },
        field(string(), "state") { state },
        field(string(), "zip") { zip },
        field(string(), "country") { country },
        ::Address
    )

    private fun Schema.Companion.customer(): Schema<Customer> = record(
        field(string(), "name") { name },
        field(string(), "email") { email },
        field(int(), "age") { age },
        field(boolean(), "verified") { verified },
        field(list(string()), "tags") { tags },
        ::Customer
    )

    private fun Schema.Companion.lineItem(): Schema<LineItem> = record(
        field(string(), "sku") { sku },
        field(string(), "name") { name },
        field(int(), "qty") { qty },
        field(double(), "price") { price },
        field(double().optional(), "discount") { discount },
        ::LineItem
    )

    private fun Schema.Companion.shipped(): Schema<OrderStatus.Shipped> = record(
        field(string(), "trackingId") { trackingId },
        OrderStatus::Shipped
    )

    private fun Schema.Companion.delivered(): Schema<OrderStatus.Delivered> = record(
        field(string(), "date") { date },
        OrderStatus::Delivered
    )

    private fun Schema.Companion.orderStatus(): Schema<OrderStatus> = union(
        case(shipped()),
        case(delivered())
    )

    private fun Schema.Companion.order(): Schema<Order> = record(
        field(string(), "id") { id },
        field(double(), "amount") { amount },
        field(string(), "currency") { currency },
        field(customer(), "customer") { customer },
        field(list(lineItem()), "items") { items },
        field(address(), "shipping") { shipping },
        field(address(), "billing") { billing },
        field(orderStatus(), "status") { status },
        field(string().optional(), "notes") { notes },
        field(stringMap(string()), "metadata") { metadata },
        ::Order
    )

    // Test data

    private val testOrder = Order(
        id = "ORD-20260303-001",
        amount = 249.97,
        currency = "USD",
        customer = Customer(
            name = "Jane Doe",
            email = "jane@example.com",
            age = 34,
            verified = true,
            tags = listOf("vip", "early-adopter", "beta-tester")
        ),
        items = listOf(
            LineItem("SKU-001", "Widget Pro", 2, 79.99, 5.0),
            LineItem("SKU-042", "Gadget Lite", 1, 49.99, null),
            LineItem("SKU-100", "Cable USB-C", 3, 12.99, 1.50)
        ),
        shipping = Address("123 Main St", "Springfield", "IL", "62701", "US"),
        billing = Address("456 Oak Ave", "Shelbyville", "IL", "62565", "US"),
        status = OrderStatus.Shipped("TRK-98765"),
        notes = "Leave at front door",
        metadata = mapOf("source" to "web", "campaign" to "spring-sale", "ref" to "abc123")
    )

    private val orderSchema = Schema.order()

    @Test
    fun `benchmark source vs kotlinx decoding`() {
        val jsonString = orderSchema.encodeToJsonString(testOrder)

        // Verify both decoders produce the same result
        val sourceResult = orderSchema.decodeFromJsonString(jsonString)
        val kotlinxResult = orderSchema.kotlinxDecodeFromJsonString(jsonString, Json.Default)
        assertTrue(sourceResult is Validation.Valid, "Source decoder failed: $sourceResult")
        assertTrue(kotlinxResult is Validation.Valid, "Kotlinx decoder failed: $kotlinxResult")
        assertTrue(sourceResult.value == kotlinxResult.value, "Decoders produced different results")

        // Warmup
        val warmupIterations = 1_000
        repeat(warmupIterations) { orderSchema.decodeFromJsonString(jsonString) }
        repeat(warmupIterations) { orderSchema.kotlinxDecodeFromJsonString(jsonString, Json.Default) }

        // Measure
        val iterations = 10_000
        val sourceTimes = LongArray(iterations)
        val kotlinxTimes = LongArray(iterations)

        for (i in 0 until iterations) {
            sourceTimes[i] = measureTime { orderSchema.decodeFromJsonString(jsonString) }.inWholeNanoseconds
        }
        for (i in 0 until iterations) {
            kotlinxTimes[i] = measureTime { orderSchema.kotlinxDecodeFromJsonString(jsonString, Json.Default) }.inWholeNanoseconds
        }

        sourceTimes.sort()
        kotlinxTimes.sort()

        fun stats(label: String, times: LongArray) {
            val avg = times.average() / 1000.0
            val p50 = times[times.size / 2] / 1000.0
            val min = times.first() / 1000.0
            val max = times.last() / 1000.0
            println("$label  avg=${avg.toLong()}  p50=${p50.toLong()}  min=${min.toLong()}  max=${max.toLong()}  (us)")
        }

        println()
        println("=== Decoding Benchmark ($iterations iterations) ===")
        stats("Source ", sourceTimes)
        stats("Kotlinx", kotlinxTimes)

        val ratio = kotlinxTimes.average() / sourceTimes.average()
        println("Ratio (source / kotlinx): ${(sourceTimes.average() / kotlinxTimes.average()).toLong()}x")
        println()
    }
}
