package com.ute.lab3_twoscreens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab3_twoscreens.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedMsg = intent.getStringExtra("EXTRA_MSG") ?: ""
        binding.tvReceivedData.text = receivedMsg

        binding.btnSave.setOnClickListener {
            val replyText = binding.edtResponse.text.toString().trim()
            val resultIntent = Intent().apply {
                putExtra("EXTRA_REPLY", replyText)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}