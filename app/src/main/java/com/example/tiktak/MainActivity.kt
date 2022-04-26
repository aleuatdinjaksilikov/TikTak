package com.example.tiktak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1.setOnClickListener {
            val intent=Intent(this,SinglePlayer::class.java)
            startActivity(intent)
        }
        btn2.setOnClickListener {
            val intent=Intent(this,PlayActivity::class.java)
            startActivity(intent)
        }

    }
}