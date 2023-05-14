package br.edu.ifrs.poa.organicpercent.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suppliers")
data class Supplier(
    @PrimaryKey(autoGenerate = true) val supplierId: Long = 0,
    val name: String,
    val address: String,
    val phone: String
)
