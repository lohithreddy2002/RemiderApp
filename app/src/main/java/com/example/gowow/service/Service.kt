package com.example.gowow.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.gowow.R
import java.util.*

class service: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val builder = NotificationCompat.Builder(context!!,"1").setContentTitle("Time Has Arrived")
            .setContentText("ohhhhh! it's time to Brush")
            .setSmallIcon(R.drawable.ic_launcher_foreground).setAutoCancel(true)
            .build()

        Toast.makeText(context, "Lohith", Toast.LENGTH_SHORT).show()

        context.let { NotificationManagerCompat.from(it) }.let {
            with(it){
                this.notify(1,builder)
            }
        }
    }

}