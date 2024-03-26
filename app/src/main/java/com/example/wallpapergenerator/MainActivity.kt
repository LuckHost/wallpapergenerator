package com.example.wallpapergenerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun runComposingPalettesActivity(view: View) {
        val intent = Intent(this, ComposingPalettesActivity::class.java)
        startActivity(intent)
    }
    fun toastMe(view: View) {
        val myToast = Toast.makeText(this, "Hello, Toast!", Toast.LENGTH_SHORT)
        myToast.show()
    }
}