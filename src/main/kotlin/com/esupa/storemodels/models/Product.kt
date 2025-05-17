package com.esupa.storemodels.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant


@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false) // ID should not be updatable once set
    val id: Long? = null,

    @Column(name = "name", nullable = false) // Name is usually required
    val name: String = "", // Made non-nullable as product name is essential

    @Column(name = "description", columnDefinition = "TEXT") // TEXT for longer descriptions
    val description: String? = null,

    @Column(name = "price")
    val price: BigDecimal? = null, // Using BigDecimal for currency

    @Column(name = "sku", unique = true) // SKU is often unique
    val sku: String? = null,

    @Column(name = "quantity")
    val quantity: Int? = null,

    @Column(name = "category")
    val category: String? = null,

    @Column(name = "image_url")
    val imageUrl: String? = null,

    // You could also add auditing fields like:
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),
    //
    @Column(name = "updated_at")
    val updatedAt: Instant = Instant.now()
//     For automatic timestamping, you might need @EnableJpaAuditing and @EntityListeners(AuditingEntityListener::class)
)