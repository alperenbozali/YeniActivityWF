package com.mrcaracal.havadurumumrc.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mrcaracal.havadurumumrc.R

class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_main)

        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val textView5 = findViewById<TextView>(R.id.derecev1)
        val textView6 = findViewById<TextView>(R.id.derecev2)
        val textView7 = findViewById<TextView>(R.id.derecev3)
        val nameGet: String? = intent.getStringExtra("Name")
        val nameGet2: String? = intent.getStringExtra("Name2")
        val nameGet3: String? = intent.getStringExtra("Name3")
        val nameGet4: String? = intent.getStringExtra("Name4")
        val nameGet5: String? = intent.getStringExtra("Name5")
        val nameGet6: String? = intent.getStringExtra("Name6")

        textView2.text = nameGet
        textView3.text = nameGet2
        textView4.text = nameGet3
        textView5.text = nameGet4
        textView6.text = nameGet5
        textView7.text = nameGet6




        val buttonGeri = findViewById<Button>(R.id.btn_geri)

        buttonGeri.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

    }
}