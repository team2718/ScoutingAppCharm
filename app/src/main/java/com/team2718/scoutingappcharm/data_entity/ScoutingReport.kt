package com.team2718.scoutingappcharm.data_entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(indices = [Index(value = ["team_number"])], tableName = "reports")
data class ScoutingReport (
    @PrimaryKey var uid: Int,

    // Report info
    @ColumnInfo(name = "team_number") var teamNumber: Int = 0,
    @ColumnInfo(name = "match_number") var matchNumber: Int = 0,
    @ColumnInfo(name = "scout_name") var scoutName: String = "",
    @ColumnInfo(name = "unix_time_complete") var unixTimeComplete: Int = 0, // Time the report was finished in unix time stamp
    @ColumnInfo(name = "stages_complete") var stagesComplete: Int = 0, // 0=New, 1=Match Info, 2=Auto, etc.

    // Robot Abilities
    @ColumnInfo(name = "can_coral_station_intake") var canCoralStationIntake: Boolean = false,
    @ColumnInfo(name = "can_coral_floor_intake") var canCoralFloorIntake: Boolean = false,
    @ColumnInfo(name = "can_algae_reef_intake") var canAlgaeReefIntake: Boolean = false,
    @ColumnInfo(name = "can_algae_reef_remove") var canAlgaeReefRemove: Boolean = false,
    @ColumnInfo(name = "can_algae_floor_intake") var canAlgaeFloorIntake: Boolean = false,
    @ColumnInfo(name = "notes") var notes: String = "",

    // Auto
    @ColumnInfo(name = "auto_L1") var autoL1: Int = 0,
    @ColumnInfo(name = "auto_L2") var autoL2: Int = 0,
    @ColumnInfo(name = "auto_L3") var autoL3: Int = 0,
    @ColumnInfo(name = "auto_L4") var autoL4: Int = 0,
    @ColumnInfo(name = "auto_did_leave") var didLeave: Boolean = false,
    @ColumnInfo(name = "auto_num_processed") var autoNumProcessed: Int = 0,
    @ColumnInfo(name = "auto_num_net_from_robot") var autoNumNetFromRobot: Int = 0,

    // Teleop
    @ColumnInfo(name = "teleop_num_processed") var teleopNumProcessed: Int = 0,
    @ColumnInfo(name = "teleop_num_net_from_robot") var teleopNumNetFromRobot: Int = 0,
    @ColumnInfo(name = "teleop_num_net_from_human") var teleopNumNetFromHuman: Int = 0,
    @ColumnInfo(name = "teleop_num_net_missed_human") var teleopNumNetMissedHuman: Int = 0,
    @ColumnInfo(name = "teleop_L1") var teleopL1: Int = 0,
    @ColumnInfo(name = "teleop_L2") var teleopL2: Int = 0,
    @ColumnInfo(name = "teleop_L3") var teleopL3: Int = 0,
    @ColumnInfo(name = "teleop_L4") var teleopL4: Int = 0,

    // Endgame
    // Hang (0 = No Hang, 1 = Park, 2 = Shallow, 3 = Deep)
    @ColumnInfo(name = "hang_type") var hang_type: Int = 0,
)