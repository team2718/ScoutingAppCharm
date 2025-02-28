package com.team2718.scoutingappcharm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team2718.scoutingappcharm.data_entity.ScoutingReport
import kotlinx.coroutines.launch
import kotlin.random.Random

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("team2718-prefs", Context.MODE_PRIVATE)

    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "team2718-db").fallbackToDestructiveMigration().allowMainThreadQueries().build()
    private val scoutingReports = db.scoutingReportDao()

    var shouldMakeNewReport: Boolean = false
    var doPageSkipping: Boolean = false
    private val noneReport = ScoutingReport(0)
    var currentReport: ScoutingReport = noneReport

    var viewReportList: List<ScoutingReport>? = null
    var viewReportIndex: Int = 0

    lateinit var bottomNavigationView: BottomNavigationView

    fun updateDB(blocking: Boolean = false) {
        if (blocking) {
            updateDBHelper()
        } else {
            viewModelScope.launch { updateDBHelper() }
        }
    }

    private fun updateDBHelper() {
        if (currentReport.uid > 0) {
            scoutingReports.insertReplace(currentReport)
            prefs.edit().putInt("current_report", currentReport.uid).apply()
            Log.i("SharedViewModel","Wrote ${currentReport.uid} to the database.")
        }
    }

    fun newReport() {
        currentReport = ScoutingReport(Random.nextInt(1, 1000000000))
        prefs.edit().putInt("current_report", currentReport.uid).apply()
        Log.i("SharedViewModel","Created new report ${currentReport.uid}.")
    }

    fun clearReport() {
        currentReport = noneReport
        prefs.edit().remove("current_report").apply()
    }

    fun getCompleteReports(): List<ScoutingReport> {
        viewReportList = scoutingReports.getAllComplete()
        return viewReportList.orEmpty()
    }

    fun deleteReport(report: ScoutingReport) {
        // scoutingReports.delete(report)
        report.stagesComplete = 100 // Deletion value - we don't actually want to delete any reports
        scoutingReports.insertReplace(report)
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