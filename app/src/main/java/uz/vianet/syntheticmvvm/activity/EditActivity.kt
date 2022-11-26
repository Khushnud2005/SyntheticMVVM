package uz.vianet.syntheticmvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import uz.vianet.syntheticmvvm.R
import uz.vianet.syntheticmvvm.viewmodel.EditViewModel
import kotlinx.android.synthetic.main.activity_edit.*
import uz.vianet.syntheticmvvm.model.Post

class EditActivity : AppCompatActivity() {

    lateinit var id: String
    lateinit var viewModel: EditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initViews()
    }
    fun initViews(){
        viewModel = ViewModelProvider(this)[EditViewModel::class.java]
        val extras = intent.extras
        if (extras != null) {
            Log.d("###", "extras not NULL - ")
            et_userIdEdit.setText(extras.getString("user_id"))
            et_titleEdit.setText(extras.getString("title"))
            et_textEdit.setText(extras.getString("body"))
            id = extras.getString("id")!!
        }
        btn_submitEdit.setOnClickListener {
            val title = et_titleEdit.text.toString()
            val body = et_textEdit.text.toString().trim { it <= ' ' }
            val id_user = et_userIdEdit.text.toString().trim { it <= ' ' }

            if (title.isNotEmpty() && body.isNotEmpty() && id_user.isNotEmpty()){
                val post = Post(id.toInt(),id_user.toInt(), title, body)
                Log.d("@@EditActivity","Pot model Created")
                viewModel.apiPostUpdate(post)
                viewModel.editedPost.observe(this){
                    Log.d("@@EditActivity","${it.title} Post Edited")
                    val intent = Intent(this@EditActivity, MainActivity::class.java)
                    intent.putExtra("title", it.title)
                    startActivity(intent)
                }
            }
        }
    }
}