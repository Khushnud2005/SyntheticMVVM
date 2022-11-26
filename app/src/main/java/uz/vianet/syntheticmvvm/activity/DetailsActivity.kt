package uz.vianet.syntheticmvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import uz.vianet.syntheticmvvm.R
import uz.vianet.syntheticmvvm.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            Log.d("###", "extras not NULL - ")
            val id = extras.getInt("id")
            viewModel.apiPostDetail(id)
            viewModel.detailPost.observe(this){
                tv_title_detail.setText(it.title.uppercase())
                tv_body_detail.setText(it.body)
            }

        }
    }
}