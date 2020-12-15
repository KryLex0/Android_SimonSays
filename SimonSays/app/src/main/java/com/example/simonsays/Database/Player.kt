package com.example.simonsays.Database

import android.app.Application
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Players")
data class Player(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val name:String,
    val score: Long,
    val difficulty: String
){
    constructor(name: String, score: Long, difficulty: String) : this(0, name, score, difficulty)
}