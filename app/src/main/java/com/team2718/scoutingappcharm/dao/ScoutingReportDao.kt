package com.team2718.scoutingappcharm.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.team2718.scoutingappcharm.data_entity.ScoutingReport

@Dao
interface ScoutingReportDao {
    @Query("SELECT * FROM reports")
    fun getAll(): List<ScoutingReport>

    @Query("SELECT * FROM reports WHERE stages_complete = 4 ORDER BY unix_time_complete DESC")
    fun getAllComplete(): List<ScoutingReport>

    @Query("SELECT * FROM reports WHERE uid = :uid")
    fun loadId(uid: Int): ScoutingReport?

    @Query("SELECT * FROM reports WHERE uid IN (:reportIds)")
    fun loadAllByIds(reportIds: IntArray): List<ScoutingReport>

    @Query("SELECT * FROM reports WHERE team_number = :teamNumber")
    fun findByTeamNumber(teamNumber: Int): List<ScoutingReport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReplace(vararg reports: ScoutingReport)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(vararg reports: ScoutingReport)

    @Delete
    fun delete(report: ScoutingReport)
}