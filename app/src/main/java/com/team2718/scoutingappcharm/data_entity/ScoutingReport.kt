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
    // Alliance, Predicted Winner (0 = Red, 1 = Blue)
    @ColumnInfo(name = "alliance") var alliance: Int = 0,
    @ColumnInfo(name = "predicted_winner") var predictedWinner: Int = 0,
    // Starting Position (0 = Left, 1 = Middle, 2 = Right)
    @ColumnInfo(name = "starting_position") var startingPosition: Int = 0,
    @ColumnInfo(name = "unix_time_complete") var unixTimeComplete: Int = 0, // Time the report was finished in unix time stamp
    @ColumnInfo(name = "stages_complete") var stagesComplete: Int = 0, // 0=New, 1=Match Info, 2=Auto, etc.

    // Robot Abilities
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
    @ColumnInfo(name = "teleop_L1") var teleopL1: Int = 0,
    @ColumnInfo(name = "teleop_L2") var teleopL2: Int = 0,
    @ColumnInfo(name = "teleop_L3") var teleopL3: Int = 0,
    @ColumnInfo(name = "teleop_L4") var teleopL4: Int = 0,
    // Hang (0 = No Hang, 1 = Park, 2 = Shallow, 3 = Deep, 4 = Shallow Failed, 5 = Deep Failed)
    @ColumnInfo(name = "hang_type") var hangType: Int = 0,
    @ColumnInfo(name = "played_defense") var playedDefense: Boolean = false,

    // Endgame
    // Cards Received (0 = None, 1 = Yellow, 2 = Red)
    @ColumnInfo(name = "card_received") var cardReceived: Int = 0,
)