package com.example.simonsays

import android.app.Application
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Players")
data class Player(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val name:String,
    val score: Long
){
    constructor(name: String, score: Long) : this(0, name, score)
}