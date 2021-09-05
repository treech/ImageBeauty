package com.treech.imagebeauty

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.treech.imagebeauty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var source: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        source = BitmapFactory.decodeResource(resources, R.drawable.test)
        binding.iv.setImageBitmap(source)
    }

    fun showSource(view: View) {
        binding.iv.setImageBitmap(source)
    }

    fun beautyFromNative(view: View) {
        val start = System.currentTimeMillis()
        val width = source.width
        val height = source.height
        val buffer = IntArray(width * height)
        source.getPixels(buffer, 0, width, 1, 1, width - 1, height - 1)
        val result = NativeUtil.beauty(buffer, width, height)
        val bitmap = Bitmap.createBitmap(result, width, height, Bitmap.Config.ARGB_8888)
        val end = System.currentTimeMillis()
        Toast.makeText(this, "native:${end - start}ms", Toast.LENGTH_SHORT).show()
        binding.iv.setImageBitmap(bitmap)
    }

    fun beautyFromJava(view: View) {
        val start = System.currentTimeMillis()
        val result = JavaUtil.beauty(source)
        binding.iv.setImageBitmap(result)
        val end = System.currentTimeMillis()
        Toast.makeText(this, "java:${end - start}ms", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}