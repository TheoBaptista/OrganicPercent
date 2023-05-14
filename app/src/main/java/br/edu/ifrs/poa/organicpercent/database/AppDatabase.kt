package br.edu.ifrs.poa.organicpercent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifrs.poa.organicpercent.model.Product
import br.edu.ifrs.poa.organicpercent.model.Supplier

@Database(entities = [Supplier::class, Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun supplierDao(): SupplierDao
}