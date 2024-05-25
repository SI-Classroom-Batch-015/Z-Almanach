package com.example.zalmanach.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zalmanach.data.model.Character

@Database(entities = [Character::class], version = 1)
abstract class DragonballDatabase : RoomDatabase() {

    // DAo Deklarieren
    abstract val dragonballDao: DragonballDao

    companion object {

        // Instanz der Datenbank wird später initialisiert.
        private lateinit var dbInstance: DragonballDatabase

        fun getDatabase(context: Context): DragonballDatabase {
            // Überprüfen, ob die DBInsatnz initialisiert wurde
            if (!this::dbInstance.isInitialized) {
                // ...wenn noch nicht, dann jetzt aber Ja
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DragonballDatabase::class.java,
                    "dragonball_db"
                ).build()
            }
            return dbInstance
        }
    }
}