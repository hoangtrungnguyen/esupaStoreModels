package com.esupa.storemodels.models

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "stores")
data class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null,

    @Column(name = "name", nullable = false, unique = true)
    var name: String,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "address", columnDefinition = "TEXT")
    var address: String? = null,

    @Column(name = "contact_email")
    var contactEmail: String? = null,

    @Column(name = "contact_phone")
    var contactPhone: String? = null,

    @Column(name = "logo_url")
    var logoUrl: String? = null,

    @Column(name = "website_url")
    var websiteUrl: String? = null,

    // A store can have many products.
    // This establishes a one-to-many relationship.
    // 'mappedBy' indicates that the 'store' field in the Product entity owns the relationship.
    // If you decide to add a 'store' field to Product, uncomment and adjust.
    // @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    // var products: MutableList<Product> = mutableListOf(),

    // A store can have many orders.
    // This might be useful if you want to associate orders directly with a store,
    // especially in a multi-store platform.
    // If orders are always global or tied to users, this might not be needed.
    // @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    // var orders: MutableList<Order> = mutableListOf(),


    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true, // To enable/disable a store

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()
) {
    // Consider adding a pre-update hook for updatedAt if not using JPA Auditing
    // @PreUpdate
    // fun onUpdate() {
    //     updatedAt = Instant.now()
    // }
}