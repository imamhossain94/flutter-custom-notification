package com.newagedevs.flutter_custom_notification

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class NativeActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)

        //Initializing text-view
        val textView:TextView = findViewById(R.id.textView)

        //checking that the arguments are set or not
        if (this.intent.extras != null && this.intent.extras!!.containsKey("args")) {

            //storing arguments from intent to a variable
            val hashMap = intent.getSerializableExtra("args") as HashMap<*, *>?

            if(!hashMap!!.isNullOrEmpty()) {
                //set text into the textview
                textView.text = "Name: ${hashMap["name"]}\n" +
                                "Age: ${hashMap["age"]}\n" +
                                "Gender: ${hashMap["gender"]}\n" +
                                "Time: ${hashMap["current_time"]}\n"
            }
        }



    }
}