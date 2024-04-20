package com.example.wallpapergenerator

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

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

    /*
    * Обновляет цвета FrameLayout-ов главного экрана, доставая их из sharedPreferences
    * */
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

    fun runCreateImageActivity(view: View) {
        val intent = Intent(this, CreateImageActivity::class.java)
        startActivity(intent)
    }

    /*
    * Обновляет цвета в главном меню при возобновлении активити
    * (просто вызвывает setNewTintOnLinLayout)
    * */
    override fun onResume() {
        super.onResume()
        setNewTintOnLinLayout()
    }
}