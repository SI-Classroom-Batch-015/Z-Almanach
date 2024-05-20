package com.example.mojopediadbzedition.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mojopediadbzedition.data.model.Character

@Database(entities = [Character::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {

    // DAo Deklarieren
    abstract val characterDao: CharacterDao

    companion object {

        // Instanz der Datenbank wird später initialisiert
        private lateinit var dbInstance: CharacterDatabase

        fun getDatabase(context: Context): CharacterDatabase {
            // Überprüfen, ob die DBInsatnz initialisiert wurde
            if (!this::dbInstance.isInitialized) {
                // ...wenn noch nicht, dann jetzt aber Ja
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    "character_db"
                ).build()
            }
            return dbInstance
        }
    }
}