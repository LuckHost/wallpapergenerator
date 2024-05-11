package com.example.wallpapergenerator

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class CreateBitmapFromColors {
    fun createCirclesBitmap(width: Int, height: Int, colors: Array<Int>): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val random = Random(System.currentTimeMillis())
        for (i in 0 until 35) {
            // Random coordinates and sizes of shapes
            val shapeX = random.nextInt(width)
            val shapeY = random.nextInt(height)
            val shapeSize = random.nextInt(250, 850).toFloat()

            // Every time the circle is too big, the total number of shapes will decrease
            if (shapeSize > 600) {
                i + (shapeSize / 100).toInt()
            }

            val shapeColor = colors.random()
            val paint = Paint()
            paint.color = shapeColor
            canvas.drawCircle(shapeX.toFloat(), shapeY.toFloat(), shapeSize, paint)
        }

        return bitmap
    }

    fun createWavesBitmap(width: Int, height: Int, colors: Array<Int>): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val wavesCount = colors.size
        val waveHeight = height.toFloat() / wavesCount

        for (i in 0 until wavesCount) {
            val paint = Paint().apply {
                color = colors[i]
                style = Paint.Style.FILL
            }

            // Вычисляем смещение по оси X для каждой волны
            val xOffset = width * (i + 1) / (wavesCount + 1)

            for (y in 0 until height) {
                // Вычисляем x-координату для текущей точки с учетом смещения
                val x = - width / 4f + xOffset + width / 4f * sin(2 * Math.PI * y / height + i * Math.PI / 3)
                canvas.drawLine(width.toFloat(), y.toFloat(), x.toFloat(), y.toFloat(), paint)
            }
        }

        return bitmap
    }
    /*
    Creates pretty bad pictures at the moment
     */
    fun createSquaresBitmap(width: Int, height: Int, colors: Array<Int>): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint()
        paint.isAntiAlias = true

        val skyColors = colors.sliceArray(2 until 6).toIntArray()

        val skyGradient = LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            skyColors,
            floatArrayOf(0f, 0.5f, 0.85f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = skyGradient
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        val random = Random(System.currentTimeMillis())
        for (i in 0 until 10) {
            // Random coordinates and sizes of shapes
            val shapeX = random.nextInt(width/2)
            val shapeY = random.nextInt(height/2)
            val shapeWidth = random.nextInt(width*1/3, width*2/3).toFloat()
            val shapeHeight = random.nextInt(height*2/3, height).toFloat()



            val shapeColor = colors.sliceArray(1..2).random()
            val paint = Paint()
            paint.color = shapeColor
            canvas.drawRect(shapeX.toFloat(), shapeY.toFloat(), shapeWidth, shapeHeight, paint)
        }

        return bitmap
    }

    /*
    Creates pretty bad pictures at the moment
     */
    private fun generateBeautifulTrianglePoint(x1: Float, y1: Float, x2: Float, y2: Float): Pair<Float, Float> {
        val midX = (x1 + x2) / 2


        val midY = (y1 + y2) / 2

        // Находим длину отрезка AB
        val lengthAB = sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))

        // Генерируем случайный угол в диапазоне от 30 до 90 градусов
        val angle = Math.toRadians((55..75).random().toFloat().toDouble())

        // Находим координаты точки C
        val newX = ((midX + (lengthAB)) * cos(angle)).toFloat()
        val newY = ((midY + (lengthAB)) * sin(angle)).toFloat()

        return Pair(newX, newY)
    }
    /*
    Creates pretty bad pictures at the moment
     */
    fun createTrianglesBitmap(width: Int, height: Int, colors: Array<Int>): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint()
        paint.isAntiAlias = true

        val random = Random(System.currentTimeMillis())
        for (i in 0 until 35) {
            // Random coordinates and sizes of shapes

            val shapeWidth = random.nextInt(width*1/3, width*2/3).toFloat()
            val shapeHeight = random.nextInt(height*2/3, height).toFloat()

            val path = android.graphics.Path().apply {
                val shapeX = random.nextInt(width).toFloat()
                val shapeY = random.nextInt(height).toFloat()
                moveTo(shapeX, shapeY) // Вершина

                val shapeX2 = random.nextInt(width).toFloat()
                val shapeY2 = random.nextInt(height).toFloat()
                lineTo(shapeX2, shapeY2 ) // Левый нижний угол

                val shapeXY3 = generateBeautifulTrianglePoint(shapeX, shapeY, shapeX2, shapeY2)
                val shapeX3 = shapeXY3.first
                val shapeY3 = shapeXY3.second
                lineTo(shapeX3, shapeY3) // Правый нижний угол
                close() // Замыкаем треугольник
            }

            val shapeColor = colors.random()
            paint.color = shapeColor
            canvas.drawPath(path, paint)
        }

        return bitmap
    }
}