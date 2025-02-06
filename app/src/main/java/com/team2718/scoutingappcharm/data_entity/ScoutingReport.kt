package com.team2718.scoutingappcharm.data_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["team_number"])], tableName = "reports")
data class ScoutingReport (
    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "team_number") var teamNumber: Int? = 0,
    @ColumnInfo(name = "match_number") var matchNumber: Int? = 0
)