package ro.andob.libmagic_binding

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import ro.andob.libmagic.getMimeType

class SampleActivity : Activity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)

        val textView = TextView(this)
        textView.setBackgroundColor(Color.BLACK)
        textView.setTextColor(Color.WHITE)
        textView.text = test()
        setContentView(textView)
    }

    private fun test() : String
    {
        val expectedResults = mapOf(
            "sample.doc" to "application/msword",
            "sample.docx" to "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "sample.csv" to "text/csv",
            "sample.xls" to "application/vnd.ms-excel",
            "sample.xlsx" to "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "sample.ppt" to "application/vnd.ms-powerpoint",
            "sample.pptx" to "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "sample.pdf" to "application/pdf",
            "sample.jpg" to "image/jpeg",
            "sample.png" to "image/png",
            "sample.gif" to "image/gif",
            "sample.ifc" to "application/octet-stream",
            "sample.html" to "text/html",
            "sample.json" to "application/json",
            "sample.txt" to "text/plain",
        )

        return expectedResults.map { (fileName, expectedMimeType) -> test(fileName, expectedMimeType) }.joinToString(separator = "\n")
    }

    private fun test(fileName : String, expectedMimeType : String) : String
    {
        val file = getFileStreamPath(fileName)
        if (file.exists())
            file.delete()

        assets.open(fileName).use { inputStream ->
            file.outputStream().use { outputStream ->
                outputStream.write(inputStream.readBytes())
            }
        }

        val actualMimeType = file.getMimeType()

        return if (expectedMimeType == actualMimeType)
            "[OK] On $fileName got $actualMimeType"
        else "[FAIL] On $fileName got $actualMimeType but $expectedMimeType was expected!!!"
    }
}
