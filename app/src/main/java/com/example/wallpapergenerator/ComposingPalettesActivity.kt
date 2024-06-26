package com.example.wallpapergenerator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.palette.graphics.Palette
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ComposingPalettesActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var linearLayout: LinearLayout
    private lateinit var lightVibrantLayout: FrameLayout
    private lateinit var vibrantLayout: FrameLayout
    private lateinit var darkVibrantLayout: FrameLayout
    private lateinit var lightMutedLayout: FrameLayout
    private lateinit var mutedLayout: FrameLayout
    private lateinit var darkMutedLayout: FrameLayout

    private lateinit var pickImageButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.composing_palettes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pickImage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.imageView)
        linearLayout = findViewById(R.id.linearLayout)

        // Устанавливаем первый фон
        linearLayout.setBackgroundResource(R.drawable.dark_purple_box)

        // Добавляем второй фон поверх первого
        val drawable = resources.getDrawable(R.drawable.color_picker_bg_wave_2)
        linearLayout.background = LayerDrawable(arrayOf(linearLayout.background, drawable))

        lightVibrantLayout =  findViewById(R.id.lightVibrantColorLayout)
        vibrantLayout =  findViewById(R.id.vibrantColorLayout)
        darkVibrantLayout =  findViewById(R.id.darkVibrantColorLayout)
        lightMutedLayout =  findViewById(R.id.lightMutedColorLayout)
        mutedLayout =  findViewById(R.id.mutedColorLayout)
        darkMutedLayout =  findViewById(R.id.darkMutedColorLayout)

        pickImageButton = findViewById(R.id.pickImageButton)
        pickImageButton.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
            generateColors()
        }
    }

    val getAction = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageView.setImageURI(it)
    }
    fun pickImage(view: View) {
        //getAction.launch("image/*")
        val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        changeImage.launch(pickImg)

        generateColors()
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                imageView.setImageURI(imgUri)
            }
        }
    private fun generateColors() {
        val bitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
        val palette = Palette.Builder(bitmap).generate()
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // задаем FrameLayout-ам цвета
        lightVibrantLayout.setBackgroundColor(palette.lightVibrantSwatch?.rgb ?: 0)
        // заносим цвета в sharedPreferences, откуда потом будем брать их в слудуюших активити
        editor.putInt("lightVibrant", palette.lightVibrantSwatch?.rgb ?: 0)

        vibrantLayout.setBackgroundColor(palette.vibrantSwatch?.rgb ?: 0)
        editor.putInt("vibrant", palette.vibrantSwatch?.rgb ?: 0)

        darkVibrantLayout.setBackgroundColor(palette.darkVibrantSwatch?.rgb ?: 0)
        editor.putInt("darkVibrant", palette.darkVibrantSwatch?.rgb ?: 0)

        lightMutedLayout.setBackgroundColor(palette.lightMutedSwatch?.rgb ?: 0)
        editor.putInt("lightMuted", palette.lightMutedSwatch?.rgb ?: 0)

        mutedLayout.setBackgroundColor(palette.mutedSwatch?.rgb ?: 0)
        editor.putInt("muted", palette.mutedSwatch?.rgb ?: 0)

        darkMutedLayout.setBackgroundColor(palette.darkMutedSwatch?.rgb ?: 0)
        editor.putInt("darkMuted", palette.darkMutedSwatch?.rgb ?: 0)

        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        generateColors()
    }
}