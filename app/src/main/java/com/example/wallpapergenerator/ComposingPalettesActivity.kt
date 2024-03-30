package com.example.wallpapergenerator

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
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
        enableEdgeToEdge()
        setContentView(R.layout.composing_palettes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pickImage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.imageView)
        linearLayout = findViewById(R.id.linearLayout)

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
            generateColors(it)
        }
    }

    val getAction = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageView.setImageURI(it)
    }
    fun pickImage(view: View) {
        //getAction.launch("image/*")
        val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        changeImage.launch(pickImg)

        generateColors(view)
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
    fun generateColors(view: View) {
        val bitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
        val palette = Palette.Builder(bitmap).generate()
        lightVibrantLayout.setBackgroundColor(palette.lightVibrantSwatch?.rgb ?: R.color.lightVibrant)
        vibrantLayout.setBackgroundColor(palette.vibrantSwatch?.rgb ?: R.color.vibrant)
        darkVibrantLayout.setBackgroundColor(palette.darkVibrantSwatch?.rgb ?: R.color.darkVibrant)
        lightMutedLayout.setBackgroundColor(palette.lightMutedSwatch?.rgb ?: R.color.lightMuted)
        mutedLayout.setBackgroundColor(palette.mutedSwatch?.rgb ?: R.color.muted)
        darkMutedLayout.setBackgroundColor(palette.darkMutedSwatch?.rgb ?: R.color.darkMuted)
    }
    fun createPaletteAsync(bitmap: Bitmap) {
        Palette.from(bitmap)
            .generate { palette ->
                val defaultColor = ContextCompat.getColor(this, R.color.white)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(palette?.getLightVibrantColor(defaultColor) ?: defaultColor))
            }
    }

}