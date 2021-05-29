package com.sharath.imageclassifierstep1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button.setOnClickListener {
            launchImgClassifierActivity(0) // layoutFlat : 0 = layout1
        }

        button2.setOnClickListener {
            launchImgClassifierActivity(1) // layoutFlat : 1 = layout2
        }
    }

    fun launchImgClassifierActivity(layoutFlag: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("layoutFlag", layoutFlag)
        startActivity(intent)
    }
}