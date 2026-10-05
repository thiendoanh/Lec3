package com.ute.lab3_twoscreens

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab3_twoscreens.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var secondLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        secondLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val reply = result.data?.getStringExtra("EXTRA_REPLY") ?: "Không có dữ liệu"
                binding.tvResult.text = "Kết quả nhận được: $reply"
            }
        }

        binding.btnOpenSecond.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("EXTRA_MSG", "Xin chào từ Màn hình 1!")
            }
            secondLauncher.launch(intent)
        }

        binding.btnCall.setOnClickListener {
            makePhoneCall("0905123456")
        }

        binding.btnOpenWeb.setOnClickListener {
            openWebsite("https://ute.udn.vn")
        }

        binding.btnSendEmail.setOnClickListener {
            sendEmail("daotao@ute.udn.vn", "Báo cáo Thực hành Lab 3", "Nội dung báo cáo...")
        }
    }


    private fun makePhoneCall(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        safeStartImplicitIntent(dialIntent, "Chọn ứng dụng gọi điện")
    }

    private fun openWebsite(webUrl: String) {
        val webIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(webUrl)
        }
        safeStartImplicitIntent(webIntent, "Chọn trình duyệt Web")
    }

    private fun sendEmail(email: String, subject: String, body: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        safeStartImplicitIntent(emailIntent, "Chọn ứng dụng Email")
    }

    private fun safeStartImplicitIntent(intent: Intent, chooserTitle: String) {
        try {
            val chooser = Intent.createChooser(intent, chooserTitle)
            startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "Không tìm thấy ứng dụng phù hợp để thực hiện tác vụ!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}