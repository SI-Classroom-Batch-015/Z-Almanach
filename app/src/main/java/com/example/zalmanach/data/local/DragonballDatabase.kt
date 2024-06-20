package com.example.zalmanach.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Favorite
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation

@Database(entities = [Character::class, Transformation::class, Planet::class, Favorite::class], version = 4)
abstract class DragonballDatabase : RoomDatabase() {

    abstract val dragonballDao: DragonballDao

    companion object {

        private lateinit var dbInstance: DragonballDatabase

        fun getDatabase(context: Context): DragonballDatabase {
            // Überprüfen, ob die DBInsatnz initialisiert wurde
            if (!this::dbInstance.isInitialized) {
                // ...wenn noch nicht, dann jetzt aber Ja
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DragonballDatabase::class.java,
                    "dragonball_db"
                ).fallbackToDestructiveMigration()    // Löscht die Datenbank bei Schema-Änderung
                    .build()
            }
            return dbInstance
        }
    }
}