package br.edu.ifrs.poa.organicpercent.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Long = 0,
    val supplierName: String,
    val name: String,
    val description: String,
    val value: String
)
