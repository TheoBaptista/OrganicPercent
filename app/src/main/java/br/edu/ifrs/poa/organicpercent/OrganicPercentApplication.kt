package br.edu.ifrs.poa.organicpercent

import android.app.Application
import androidx.room.Room
import br.edu.ifrs.poa.organicpercent.database.AppDatabase

class OrganicPercentApplication : Application() {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-db"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}