package com.example.shopping_cart.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopping_cart.Model.ProductListModel

@Database(entities = [ProductListModel::class], version = 3)
abstract class RoomDabase : RoomDatabase() {
    abstract fun ProductListDao(): ProductListDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RoomDabase? = null

        fun getDatabase(context: Context): RoomDabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDabase::class.java,
                    "dbproductosList"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}