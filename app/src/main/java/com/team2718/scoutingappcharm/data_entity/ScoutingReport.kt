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
//    @ColumnInfo(name = "predicted_winner") var predictedWinner: Int = 0,
    // Starting Position (0 = Left, 1 = Middle, 2 = Right)
    @ColumnInfo(name = "starting_position") var startingPosition: Int = 0,
    @ColumnInfo(name = "unix_time_complete") var unixTimeComplete: Int = 0, // Time the report was finished in unix time stamp
    @ColumnInfo(name = "stages_complete") var stagesComplete: Int = 0, // 0=New, 1=Match Info, 2=Auto, etc.

    // Robot Abilities
    @ColumnInfo(name = "notes") var notes: String = "",

    // Auto
    @ColumnInfo(name = "auto_did_leave") var didLeave: Boolean = false,
    @ColumnInfo(name = "auto_fuel_scored") var autoFuel: Int = 0,
    @ColumnInfo(name = "auto_fuel_missed") var autoFuelMissed: Int = 0,
    @ColumnInfo(name = "auto_climbed") var autoClimbed: Boolean = false,

    // Teleop
    @ColumnInfo(name = "tele_fuel_rate_score") var teleFuelRateScore: Int = 0,
    @ColumnInfo(name = "tele_acc_score") var teleAccScore: Int = 0,
    @ColumnInfo(name = "tele_pass_score") var telePassScore: Int = 0,
    @ColumnInfo(name = "tele_def_score") var teleDefScore: Int = 0,
    // Hang (0 = No climb, 1 = L1, 2 = L2, 3 = L3)
    @ColumnInfo(name = "climb_type") var climbType: Int = 0,

    // Endgame
    // Cards Received (0 = None, 1 = Yellow, 2 = Red)
    @ColumnInfo(name = "card_received") var cardReceived: Int = 0,
)