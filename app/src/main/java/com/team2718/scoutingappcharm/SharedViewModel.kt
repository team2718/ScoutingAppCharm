package com.team2718.scoutingappcharm

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.team2718.scoutingappcharm.data_entity.ScoutingReport
import kotlinx.coroutines.launch
import kotlin.random.Random

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("team2718-prefs", Context.MODE_PRIVATE)

    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "team2718-db").fallbackToDestructiveMigration().allowMainThreadQueries().build()
    private val scouting_reports = db.scoutingReportDao()

    // This is dangerous being public, but I'm sure it'll be fine
    var currentReport: ScoutingReport = ScoutingReport(0)

    fun updateDB() {
        AsyncTask.execute { scouting_reports.insertReplace(currentReport) }
        prefs.edit().putInt("current_report", currentReport.uid).apply()
        println("Wrote " + currentReport.uid + " to prefs!")
    }

    fun newReport() {
        currentReport = ScoutingReport(Random.nextInt())
    }

    init {
        viewModelScope.launch {
            if (prefs.contains("current_report")) {
                println("Loading report " + prefs.getInt("current_report", 0))
                val loadedReport = scouting_reports.loadId(prefs.getInt("current_report", 0))
                if (loadedReport != null) {
                    currentReport = loadedReport
                    println("Loaded report with team number " + loadedReport.teamNumber + "!")
                }
            }
        }
    }
}