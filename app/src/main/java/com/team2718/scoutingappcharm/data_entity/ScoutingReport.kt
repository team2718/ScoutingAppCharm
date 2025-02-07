package com.team2718.scoutingappcharm.data_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["team_number"])], tableName = "reports")
data class ScoutingReport (
    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "team_number") var teamNumber: Int? = 0,
    @ColumnInfo(name = "match_number") var matchNumber: Int? = 0,
    @ColumnInfo(name = "can_coral_station_intake") var canCoralStationIntake: Boolean? = false,
    @ColumnInfo(name = "can_coral_floor_intake") var canCoralFloorIntake: Boolean? = false,
    @ColumnInfo(name = "can_algae_reef_intake") var canAlgaeReefIntake: Boolean? = false,
    @ColumnInfo(name = "can_algae_reef_remove") var canAlgaeReefRemove: Boolean? = false,
    @ColumnInfo(name = "can_algae_floor_intake") var canAlgaeFloorIntake: Boolean? = false,
    @ColumnInfo(name = "num_processed") var numProcessed: Int? = 0,
    @ColumnInfo(name = "num_net_from_robot") var numNetFromRobot: Int? = 0,
    @ColumnInfo(name = "num_net_from_human") var numNetFromHuman: Int? = 0,
    @ColumnInfo(name = "num_net_missed_human") var numNetMissedHuman: Int? = 0,
    @ColumnInfo(name = "teleop_L1") var teleopL1: Int? = 0,
    @ColumnInfo(name = "teleop_L2") var teleopL2: Int? = 0,
    @ColumnInfo(name = "teleop_L3") var teleopL3: Int? = 0,
    @ColumnInfo(name = "teleop_L4") var teleopL4: Int? = 0,
    @ColumnInfo(name = "deep_hang") var deepHang: Boolean? = false,
    @ColumnInfo(name = "shallow_hang") var shallowHang: Boolean? = false,
    @ColumnInfo(name = "no_hang") var noHang: Boolean? = false,
    @ColumnInfo(name = "auto_L1") var autoL1: Int? = 0,
    @ColumnInfo(name = "auto_L2") var autoL2: Int? = 0,
    @ColumnInfo(name = "auto_L3") var autoL3: Int? = 0,
    @ColumnInfo(name = "auto_L4") var autoL4: Int? = 0,
    @ColumnInfo(name = "did_leave") var didLeave: Boolean? = false,
    @ColumnInfo(name = "notes") var notes: String? = ""
)