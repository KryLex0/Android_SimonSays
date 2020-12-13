package com.example.simonsays

import androidx.room.*

@Dao
interface PlayerDao {

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)

    @Query("SELECT * FROM Players WHERE name LIKE :nomP AND " +
            "score LIKE :scoreP LIMIT 1")
    fun findPlayerByData(nomP: String, scoreP: Long): Player

    @Query("SELECT * FROM Players")
    fun getAll(): List<Player>

    @Query("SELECT * FROM Players WHERE id=(SELECT max(id) FROM Players)")
    fun getLastScore(): Player

    @Query("SELECT * FROM Players WHERE score=(SELECT max(score) FROM Players)")
    fun getHighScore(): Player



    @Query("SELECT * FROM Players ORDER BY score DESC, name ASC LIMIT 10")
    fun getTenLastAll(): List<Player>

    @Query("SELECT * FROM Players WHERE difficulty LIKE 'Facile' ORDER BY score DESC, name ASC LIMIT 10")
    fun getTenLastFacile(): List<Player>

    @Query("SELECT * FROM Players WHERE difficulty LIKE 'Normal' ORDER BY score DESC, name ASC LIMIT 10")
    fun getTenLastNormal(): List<Player>

    @Query("SELECT * FROM Players WHERE difficulty LIKE 'Difficile' ORDER BY score DESC, name ASC LIMIT 10")
    fun getTenLastDifficile(): List<Player>



    @Query("DELETE FROM Players WHERE id LIKE :playerId")
    fun deletePlayerFromId(playerId: Long)

    @Query("DELETE FROM Players")
    fun deleteAll()
/*
    @Query("SELECT id FROM Players WHERE name LIKE :nom AND " +
            "score LIKE :score LIMIT 1")
    fun getId(nom: String, score: Long): Player

    @Query("DELETE FROM Players WHERE idP LIKE :id AND " + "nameP LIKE :nom AND " + "scoreP LIKE :score LIMIT 1")
    fun removePlayer(idP:Long, nomP: String, scoreP: Long) : Player

    @Query("SELECT * FROM Players WHERE id IN (:playerIds)")
    fun loadAllByIds(playerIds: IntArray): List<Player>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg players: Player)
*/



}