package com.sharath.imageclassifierstep1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions
//import com.google.mlkit.vision.label.defaults.ImageLabelerOptions // for default options
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.getIntExtra("layoutFlag", 0) == 0) {
            setContentView(R.layout.activity_main)
        } else if (intent.getIntExtra("layoutFlag", 0) == 1) {
            setContentView(R.layout.activity_main2)
        } else {
            setContentView(R.layout.activity_main)
        }

        // initiating local model
        val localModel = LocalModel.Builder()
            .setAssetFilePath("model.tflite")
            .build()

        val fileName = "flower1.jpg"
        val bitMap: Bitmap? = assetToBitmap(fileName)

        // loading img into imageview
        bitMap?.apply { imageToLabel.setImageBitmap(this) }

        // btn click to start img labeling
        btnTest.setOnClickListener {

            val options = CustomImageLabelerOptions.Builder(localModel)
                .setConfidenceThreshold(0.7f)
                .setMaxResultCount(5)
                .build()
            //val lable = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS) // for default options
            val lable = ImageLabeling.getClient(options)

            val inputImage = InputImage.fromBitmap(bitMap, 0)
            var lableText = ""
            lable.process(inputImage).addOnSuccessListener {
                for (lable in it) {
                    lableText += "${lable.text} : ${lable.confidence} \n"
                }
                txtOutput.text = lableText
            }. addOnFailureListener {
                Toast.makeText(this@MainActivity, "failed with exception ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}

fun Context.assetToBitmap(fileName: String): Bitmap? {
    return try {
        with(assets.open(fileName)) {
            BitmapFactory.decodeStream(this)
        }
    } catch (e: Exception) {null}
}