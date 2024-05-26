package com.example.zalmanach.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zalmanach.data.model.Character
import com.example.zalmanach.data.model.Transformation

@Dao
interface DragonballDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query("SELECT * FROM character_table")
    fun getAllCharacter(): LiveData<List<Character>>

    @Query("DELETE from character_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransformations(transformations: List<Transformation>)

    @Query("SELECT * FROM transformation_table")
    fun getAllTransformations(): LiveData<List<Transformation>>

    @Query("DELETE FROM transformation_table")
    suspend fun deleteAllTransformations()
}