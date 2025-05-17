package com.esupa.storemodels.models

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant

// Enum for Order Status
enum class OrderStatus {
    PENDING,        // Order received, awaiting payment or processing
    PROCESSING,     // Order is being prepared
    SHIPPED,        // Order has been shipped
    DELIVERED,      // Order has been delivered
    CANCELED,       // Order was canceled
    RETURNED        // Order was returned
}

@Entity
@Table(name = "orders") // "orders" is a common table name
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY) // LAZY is often preferred for performance
    @JoinColumn(name = "user_id", nullable = false)
    val user: User, // Assumes a User entity exists and is linked

    @Column(name = "order_date", nullable = false)
    val orderDate: Instant = Instant.now(),

    @Enumerated(EnumType.STRING) // Store enum as string in the DB
    @Column(name = "status", nullable = false)
    var status: OrderStatus = OrderStatus.PENDING,

    @Column(name = "total_amount", nullable = false)
    var totalAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    var shippingAddress: String? = null,

    @Column(name = "billing_address", columnDefinition = "TEXT")
    var billingAddress: String? = null,

    @Column(name = "payment_method")
    var paymentMethod: String? = null,

    @Column(name = "payment_status")
    var paymentStatus: String? = null, // e.g., PENDING, COMPLETED, FAILED

    @Column(name = "notes", columnDefinition = "TEXT")
    var notes: String? = null, // Customer or internal notes

    // One Order can have many OrderItems
    // CascadeType.ALL means operations (persist, remove, etc.) on Order propagate to OrderItems
    // OrphanRemoval = true means if an OrderItem is removed from this list, it's deleted from DB
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var orderItems: MutableList<OrderItem> = mutableListOf(),

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()
) {
    // Helper function to add an order item and set the bidirectional relationship
    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this // Set the 'order' property in OrderItem
    }

    // Helper function to remove an order item
    fun removeOrderItem(orderItem: OrderItem) {
        orderItems.remove(orderItem)
        orderItem.order = null
    }

    // Consider adding a pre-update hook for updatedAt if not using JPA Auditing
    // @PreUpdate
    // fun onUpdate() {
    //     updatedAt = Instant.now()
    // }
}


@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null,

    @Column(name = "index", nullable = false)
    var index: Int,

    @ManyToOne(fetch = FetchType.LAZY) // LAZY to avoid loading the whole order unless needed
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order? = null, // Bidirectional: 'mappedBy' in Order entity points to this 'order' field

    @ManyToOne(fetch = FetchType.LAZY) // LAZY to avoid loading full product details unless needed
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product, // The product that was ordered

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @Column(name = "price_at_purchase", nullable = false)
    val priceAtPurchase: BigDecimal, // Price of one unit of the product at the time of order

    // Optional: You might also consider a 'subtotal' field for this line item
    @Column(name = "subtotal", nullable = false)
    val subtotal: BigDecimal // Calculated as quantity * priceAtPurchase
//     This would typically be calculated in your service layer or on-the-fly.
) {
    // JPA requires a no-arg constructor.
    // For data classes, this is usually handled if all properties have default values,
    // or if your JPA provider (like Hibernate) is configured to work with Kotlin data classes.
    // If you encounter issues, you might need to add default values or an explicit no-arg constructor.

    // Example of a constructor if you want to calculate subtotal upon creation:
    // constructor(
    //     order: Order,
    //     product: Product,
    //     quantity: Int,
    //     priceAtPurchase: BigDecimal
    // ) : this(
    //     id = null,
    //     order = order,
    //     product = product,
    //     quantity = quantity,
    //     priceAtPurchase = priceAtPurchase
    //     // subtotal = priceAtPurchase.multiply(BigDecimal(quantity)) // Initialize subtotal
    // )

    // Ensure equals() and hashCode() primarily use 'id' if non-null,
    // or rely on the data class implementation if 'id' can be null for new entities.
    // The default data class implementation should be fine for most cases.
}

@Entity
@Table(name = "order_product_items")
data class OrderProductItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null,


    )
