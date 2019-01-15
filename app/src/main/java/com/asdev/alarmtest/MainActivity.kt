package com.asdev.alarmtest

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.PermissionChecker
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonGetAlarms(v: View?) {
        Thread {
            getAlarms()
        }.apply {
            isDaemon = true
            name = "AlarmFetch"
            start()
        }
    }

    fun getAlarms() {
        val tag = "tag_alarm"
        if(PermissionChecker.checkSelfPermission(applicationContext, android.Manifest.permission.SET_ALARM) == PermissionChecker.PERMISSION_GRANTED) {
            Log.i(tag, "Has set alarm perm")
        }

        val uri = Uri.parse("content://com.android.deskclock/alarms")
//        val uri = Uri.parse("content://com.android.alarmclock/alarm")
        val c = contentResolver.query(uri, null, null, null, null)

        if(c == null) {
            Log.i(tag, "cursor is null")
            return
        }

        Log.i(tag, "no of records are " + c.count)
        Log.i(tag, "no of columns are " + c.columnCount)
        val names = c.columnNames
        for (temp in names) {
            println(temp)
        }
        if (c.moveToFirst()) {
            do {
                for (j in 0 until c.columnCount) {
                    Log.i(tag, c.getColumnName(j)
                            + " which has value " + c.getString(j))
                }
            } while (c.moveToNext())
        }
        c.close()
    }
}
