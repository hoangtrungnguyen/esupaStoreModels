package com.esupa.storemodels.models

import java.time.Instant

@Entity
@Table(name = "users") // Changed table name to "users" (plural, common convention)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null,

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String, // Store hashed passwords, never plaintext

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "first_name")
    val firstName: String? = null,

    @Column(name = "last_name")
    val lastName: String? = null,

    @Column(name = "enabled", nullable = false)
    val enabled: Boolean = true, // Default to true (active)

    // Consider using @ElementCollection for roles if they are simple strings
    // or @ManyToMany for a separate Role entity
    // @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    // @Column(name = "role")
    // val roles: Set<String> = HashSet(),

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now() // 'var' if you plan to update it manually,
    // or use JPA auditing for automatic updates

    // The 'code' field from the original commented out version is removed
    // as its purpose wasn't clear in a "general" user entity.
    // If 'name' was meant to be a display name, 'firstName' and 'lastName'
    // or a dedicated 'displayName' field would be more common.
) {
    // JPA requires a no-arg constructor, which data classes provide automatically
    // if all constructor parameters have default values.
    // However, for @Entity data classes, it's generally fine without explicit default values
    // for all properties if you have a JPA provider like Hibernate.
    // If you encounter issues, you might need to add default values or a no-arg constructor.

    // For automatic `updatedAt` timestamping with JPA Auditing:
    // 1. Add @EntityListeners(AuditingEntityListener::class) to this class.
    // 2. Add @EnableJpaAuditing to a @Configuration class in your application.
    // 3. Change `updatedAt` to:
    //    @LastModifiedDate
    //    @Column(name = "updated_at")
    //    var updatedAt: Instant? = null // or Instant = Instant.now()
    // 4. Similarly for `createdAt`:
    //    @CreatedDate
    //    @Column(name = "created_at", nullable = false, updatable = false)
    //    var createdAt: Instant? = null // or Instant = Instant.now()
}