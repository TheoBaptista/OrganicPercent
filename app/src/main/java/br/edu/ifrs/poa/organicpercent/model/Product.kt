package br.edu.ifrs.poa.organicpercent.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Long = 0,
    var supplierName: String,
    var name: String,
    var description: String,
    var value: String
)
