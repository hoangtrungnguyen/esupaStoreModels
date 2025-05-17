package com.esupa.storemodels.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "customers")
data class Customer(
    @Id
    @Column(name = "id", nullable = false)
    val id: Long = -1,
    @Column(name = "name")
    val name: String = "",
    @Column(name = "code", nullable = false)
    val code: String = "",


    )
