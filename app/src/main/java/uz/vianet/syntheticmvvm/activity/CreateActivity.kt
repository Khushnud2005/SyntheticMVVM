package uz.vianet.syntheticmvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_create.*
import uz.vianet.syntheticmvvm.R
import uz.vianet.syntheticmvvm.viewmodel.CreateViewModel
import kotlinx.android.synthetic.main.activity_create.*
import uz.vianet.syntheticmvvm.model.Post

class CreateActivity : AppCompatActivity() {

    lateinit var viewModel: CreateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[CreateViewModel::class.java]

        btn_submitCreate.setOnClickListener(View.OnClickListener {
            val title: String = et_titleCreate.getText().toString()
            val body: String = et_bodyCreate.getText().toString().trim { it <= ' ' }
            val id_user: String = et_userIdCreate.getText().toString().trim { it <= ' ' }
            if (title.isNotEmpty() && body.isNotEmpty() && id_user.isNotEmpty()){
                val post = Post(id_user.toInt(), title, body)
                viewModel.apiPostCreate(post)
                viewModel.newPost.observe(this){
                    val intent = Intent()
                    intent.putExtra("title", it.title)
                    setResult(RESULT_OK, intent)
                    super@CreateActivity.onBackPressed()
                }

            }

        })
    }
}