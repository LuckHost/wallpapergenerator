package com.example.wallpapergenerator

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.palette.graphics.Palette


class ComposingPalettesActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.composing_palettes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.imageView)
        linearLayout = findViewById(R.id.linearLayout)
    }

    val getAction = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageView.setImageURI(it)
    }
    fun pickImage(view: View) {
        getAction.launch("image/*")
        generateColors(view)
    }
    fun generateColors(view: View) {
        val bitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
        val palette = Palette.Builder(bitmap).generate()
        linearLayout.setBackgroundColor(palette.darkMutedSwatch?.rgb ?: R.color.darkMuted)
    }
    fun createPaletteAsync(bitmap: Bitmap) {
        Palette.from(bitmap)
            .generate { palette ->
                val defaultColor = ContextCompat.getColor(this, R.color.white)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(palette?.getLightVibrantColor(defaultColor) ?: defaultColor))
            }
    }

}