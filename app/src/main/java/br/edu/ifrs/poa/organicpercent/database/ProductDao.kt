package br.edu.ifrs.poa.organicpercent.database

import androidx.room.*
import br.edu.ifrs.poa.organicpercent.model.Product

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("SELECT * FROM products")
    fun listAll(): List<Product>

    @Query("SELECT * FROM products WHERE productId = :productId")
    fun findById(productId: Long): Product?
}