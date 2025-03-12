package com.example.lab4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var fname: EditText
    private lateinit var lname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var submitButton: Button

    private val fileName = "user_data.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fname = findViewById(R.id.fname)
        lname = findViewById(R.id.lname)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        submitButton = findViewById(R.id.submit)

        loadData()

        submitButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val data = "${fname.text.toString()} ${lname.text.toString()}\n${email.text.toString()}\n${password.text.toString()}"
        try {
            openFileOutput(fileName, MODE_PRIVATE).use {
                it.write(data.toByteArray())
            }
            Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save data!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData() {
        try {
            openFileInput(fileName).use { stream ->
                val reader = BufferedReader(InputStreamReader(stream))
                val lines = reader.readLines()
                if (lines.size >= 3) {
                    val nameParts = lines[0].split(" ")
                    if (nameParts.size >= 2) {
                        fname.setText(nameParts[0])
                        lname.setText(nameParts[1])
                    }
                    email.setText(lines[1])
                    password.setText(lines[2])
                    Toast.makeText(this, "Data loaded successfully!", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "No saved data found!", Toast.LENGTH_SHORT).show()
        }
    }
}