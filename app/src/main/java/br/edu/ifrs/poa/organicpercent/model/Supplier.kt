package br.edu.ifrs.poa.organicpercent.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suppliers")
data class Supplier(
    @PrimaryKey(autoGenerate = true) val supplierId: Long = 0,
    var name: String,
    var address: String,
    var phone: String
)
