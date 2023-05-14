package br.edu.ifrs.poa.organicpercent.database

import androidx.room.*
import br.edu.ifrs.poa.organicpercent.model.Supplier

@Dao
interface SupplierDao {
    @Insert
    fun insert(supplier: Supplier)

    @Update
    fun update(supplier: Supplier)

    @Delete
    fun delete(supplier: Supplier)

    @Query("SELECT * FROM suppliers")
    fun listAll(): List<Supplier>
}