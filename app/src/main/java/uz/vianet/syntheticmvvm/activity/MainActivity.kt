package uz.vianet.syntheticmvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.vianet.syntheticmvvm.R
import uz.vianet.syntheticmvvm.model.Post
import uz.vianet.syntheticmvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import uz.vianet.syntheticmvvm.adapter.PostAdapter
import uz.vianet.syntheticmvvm.utils.Utils.toast

class MainActivity : AppCompatActivity() {

    var posts = ArrayList<Post>()

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        floating.setOnClickListener { openCreateActivity() }
        viewModel.apiPostList()
        viewModel.allPosts.observe(this) {
            refreshAdapter(it)
        }

        val extras = intent.extras
        if (extras != null) {
            pb_loading.visibility = View.VISIBLE
            Toast.makeText(this@MainActivity,"${extras.getString("title")} Edited", Toast.LENGTH_LONG).show()
            viewModel.apiPostList()
        }
    }

    private fun refreshAdapter(posts: ArrayList<Post>) {
        val adapter = PostAdapter(this, posts)
        recyclerView.setAdapter(adapter)
        pb_loading.visibility = View.GONE
    }
    fun openCreateActivity() {
        val intent = Intent(this@MainActivity, CreateActivity::class.java)
        launchCreateActivity.launch(intent)
    }

    var launchCreateActivity = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            pb_loading.visibility = View.VISIBLE
            if (result.data != null) {
                val title = result.data!!.getStringExtra("title")
                toast(this@MainActivity,"$title Created")
                viewModel.apiPostList()
            }
        } else {
            Toast.makeText(this@MainActivity, "Operation canceled", Toast.LENGTH_LONG).show()
        }
    }






}