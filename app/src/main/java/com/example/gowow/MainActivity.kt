package com.example.gowow

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.gowow.service.service
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Notificationchannel()
        val sharedpref =  getSharedPreferences("BrushPref",Context.MODE_PRIVATE)
        val pasttime =sharedpref.getString("time","")
        findViewById<TextView>(R.id.pastime).text =pasttime

    }

    private fun Notificationchannel(){
        val name = "RemiderChannel"
        val descriptionT = "Channel for Remider app"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel_id = "1"

        val channel = NotificationChannel(channel_id,name,importance).apply {
            description = descriptionT
        }


        val nofi:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nofi.createNotificationChannel(channel)
    }

    @SuppressLint("CutPasteId")
    override fun onStart() {
        super.onStart()
        val sharedpref =  getSharedPreferences("BrushPref",Context.MODE_PRIVATE)
        val edit = sharedpref.edit()
        val calendar = Calendar.getInstance()

val picker = findViewById<TimePicker>(R.id.timePicker)
        val create = findViewById<Button>(R.id.create)

        create.setOnClickListener {

            picker.setOnTimeChangedListener { view, hourOfDay, minute ->
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    hourOfDay,
                    minute
                )
            }
            val string = "${picker.hour} : ${picker.minute}"
            edit.apply {
                putString("time",string)
                apply()
            }

            findViewById<TextView>(R.id.pastime).text = string
            createAlaram(calendar.timeInMillis)


        }


        val update = findViewById<Button>(R.id.update)
        val cancel = findViewById<Button>(R.id.cancel)
        update.setOnClickListener {
            createAlaram(calendar.timeInMillis)
        }
        cancel.setOnClickListener {
            findViewById<TextView>(R.id.pastime).text = "No alram Scheduled"
            sharedpref.edit().apply {
                putString("time","No alram Scheduled")
                apply()
            }
            cancelAlaram()

        }



    }

    private fun createAlaram(timeinmills:Long){
        val intent = Intent(this,service::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,1,intent,0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeinmills,
           AlarmManager.INTERVAL_DAY,
            pendingIntent

        )

    }

    private fun cancelAlaram(){
        val intent = Intent(this,service::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,1,intent,0)
        alarmManager.cancel(pendingIntent)

    }

}