package com.alive.myapplication.utils

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.alive.myapplication.R
import org.joda.time.PeriodType
import org.joda.time.PeriodType.yearMonthDay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

object DateUtils {

    /*@RequiresApi(Build.VERSION_CODES.O)
    fun getAgeNumber(year:Int, month:Int, dayOfMonth:Int) : String {
            val months: Int = Period.between(
                LocalDate.of(year,month,dayOfMonth),
                LocalDate.now()
            ).months
            val years:Int = Period.between(
                LocalDate.of(year,month,dayOfMonth),
                LocalDate.now()
            ).years

       *//* val period: org.joda.time.Period = org.joda.time.Period(startdate, endDate, yearMonthDay())
        val totalyears: Int = period.getYears()
        val totalMonths: Int = period.getMonths()
        val totalDays: Int = period.getDays()*//*
        Log.d("vs",
            "Year $years Month $months")

        return ""
    }*/

    fun getAgeNumber(context: Context, selectedDob :Long):String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        Log.d("vs",
            "Year $year Month $month DayOfMonth $dayOfMonth")
        val todaysDate = "$dayOfMonth/$month/$year"
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.parse(todaysDate)
        val todaysTime = date.time
        var totalyears: Int = 0
        var totalMonths: Int = 0
        var totalDays: Int = 0
        if(selectedDob <= todaysTime) {
            val period: org.joda.time.Period =
                org.joda.time.Period(selectedDob, todaysTime, yearMonthDay())
             totalyears = period.getYears()
             totalMonths = period.getMonths()
             totalDays = period.getDays()
            Log.d("vs",
                "Year $totalyears Month $totalMonths days $totalDays")

        } else {

        }
        return String.format(context.resources.getString(R.string.age_text),
            totalyears,totalMonths,totalDays)

    }
}