package com.example.zalmanach.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Planet
import com.example.zalmanach.data.model.Transformation

@Database(entities = [Character::class, Transformation::class, Planet::class], version = 3)
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
                ).fallbackToDestructiveMigration()    // Löscht die Datenbank bei Schemaänderung
                    .build()
            }
            return dbInstance
        }
    }
}