package com.team2718.scoutingappcharm

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.team2718.scoutingappcharm.data_entity.ScoutingReport
import kotlinx.coroutines.launch
import kotlin.random.Random

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("team2718-prefs", Context.MODE_PRIVATE)

    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "team2718-db").fallbackToDestructiveMigration().allowMainThreadQueries().build()
    private val scoutingReports = db.scoutingReportDao()

    var currentReport: ScoutingReport = ScoutingReport(0)

    fun updateDB() {
        viewModelScope.launch { scoutingReports.insertReplace(currentReport) }
        Log.i("SharedViewModel","Wrote ${currentReport.uid} to the database.")
    }

    fun newReport() {
        currentReport = ScoutingReport(Random.nextInt())
        Log.i("SharedViewModel","Created new report ${currentReport.uid}.")
    }

    init {
        viewModelScope.launch {
            val prefsCurrentReportID = prefs.getInt("current_report", 0)
            if (prefsCurrentReportID != 0) {
                println("Fetching report ${prefsCurrentReportID}...")
                val loadedReport = scoutingReports.loadId(prefsCurrentReportID)

                if (loadedReport != null) {
                    currentReport = loadedReport
                    println("Loaded report ${currentReport.uid}!")
                }
            }
        }
    }
}