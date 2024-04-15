package com.example.wallpapergenerator

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var lightVibrantColorMainLayout: FrameLayout
    private lateinit var vibrantColorMainLayout: FrameLayout
    private lateinit var darkVibrantColorMainLayout: FrameLayout
    private lateinit var mutedColorMainLayout: FrameLayout
    private lateinit var lightMutedColorMainLayout: FrameLayout
    private lateinit var darkMutedColorMainLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lightVibrantColorMainLayout = findViewById(R.id.lightVibrantColorMainLayout)
        vibrantColorMainLayout = findViewById(R.id.vibrantColorMainLayout)
        darkVibrantColorMainLayout = findViewById(R.id.darkVibrantColorMainLayout)
        mutedColorMainLayout = findViewById(R.id.mutedColorMainLayout)
        lightMutedColorMainLayout = findViewById(R.id.lightMutedColorMainLayout)
        darkMutedColorMainLayout = findViewById(R.id.darkMutedColorMainLayout)

        setNewTintOnLinLayout()
    }

    private fun setNewTintOnLinLayout() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        lightVibrantColorMainLayout.background.setColorFilter(
            sharedPreferences.getInt("lightVibrant", Color.BLACK),
            PorterDuff.Mode.SRC_ATOP)

        vibrantColorMainLayout.background.setColorFilter(
            sharedPreferences.getInt("vibrant", Color.BLACK),
            PorterDuff.Mode.SRC_ATOP)

        darkVibrantColorMainLayout.background.setColorFilter(
            sharedPreferences.getInt("darkVibrant", Color.BLACK),
            PorterDuff.Mode.SRC_ATOP)

        lightMutedColorMainLayout.background.setColorFilter(
            sharedPreferences.getInt("lightMuted", Color.BLACK),
            PorterDuff.Mode.SRC_ATOP)

        mutedColorMainLayout.background.setColorFilter(
            sharedPreferences.getInt("muted", Color.BLACK),
            PorterDuff.Mode.SRC_ATOP)

        darkMutedColorMainLayout.background.setColorFilter(
            sharedPreferences.getInt("darkMuted", Color.BLACK),
            PorterDuff.Mode.SRC_ATOP)
    }
    fun runComposingPalettesActivity(view: View) {
        val intent = Intent(this, ComposingPalettesActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        setNewTintOnLinLayout()
    }

    fun toastMe(view: View) {
        val myToast = Toast.makeText(this, "Hello, Toast!", Toast.LENGTH_SHORT)
        myToast.show()
    }
}