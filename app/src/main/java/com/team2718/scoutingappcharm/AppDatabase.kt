package com.team2718.scoutingappcharm

import androidx.room.Database
import androidx.room.RoomDatabase
import com.team2718.scoutingappcharm.dao.ScoutingReportDao
import com.team2718.scoutingappcharm.data_entity.ScoutingReport

@Database(entities = [ScoutingReport::class], version = 20250306, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoutingReportDao(): ScoutingReportDao
}
