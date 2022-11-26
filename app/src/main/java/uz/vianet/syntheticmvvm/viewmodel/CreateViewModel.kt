package uz.vianet.syntheticmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.vianet.syntheticmvvm.model.Post
import uz.vianet.syntheticmvvm.network.RetrofitHttp

class CreateViewModel :ViewModel() {
    val newPost = MutableLiveData<Post>()

    fun apiPostCreate(post: Post) {
        RetrofitHttp.postService.createPost(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                newPost.value = response.body()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("@@@", t.toString())
            }
        })
    }
}