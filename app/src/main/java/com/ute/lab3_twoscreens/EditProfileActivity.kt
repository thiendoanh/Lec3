package com.ute.lab3_twoscreens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab3_twoscreens.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var originalStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Nhận dữ liệu sinh viên cũ gửi sang từ MainActivity
        originalStudent = intent.getSerializableExtra("STUDENT_DATA") as? Student
        originalStudent?.let {
            binding.edtName.setText(it.name)
            binding.edtClass.setText(it.className)
            binding.edtGpa.setText(it.gpa.toString())
        }

        // 2. Nút Lưu thông tin
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val className = binding.edtClass.text.toString().trim()
            val gpa = binding.edtGpa.text.toString().toDoubleOrNull()

            if (name.isEmpty() || className.isEmpty() || gpa == null || gpa !in 0.0..4.0) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin & GPA hợp lệ (0.0 - 4.0)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedStudent = originalStudent?.copy(
                name = name,
                className = className,
                gpa = gpa
            ) ?: return@setOnClickListener

            // Đóng gói dữ liệu trả về
            val resultIntent = Intent().apply {
                putExtra("UPDATED_STUDENT", updatedStudent)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // 3. Nút Hủy
        binding.btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}