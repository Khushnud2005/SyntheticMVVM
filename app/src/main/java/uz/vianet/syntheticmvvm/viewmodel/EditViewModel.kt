package uz.vianet.syntheticmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.vianet.syntheticmvvm.model.Post
import uz.vianet.syntheticmvvm.network.RetrofitHttp

class EditViewModel : ViewModel() {

    val editedPost = MutableLiveData<Post>()

    fun apiPostUpdate(post: Post) {
        Log.d("@@EditViewModel","${post.title} Post Came To ViewModel")
        RetrofitHttp.postService.updatePost(post.id, post)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.body() != null) {
                        Log.d("@@EditViewModel","${response.body()!!.title} Post Responded")
                        editedPost.value = response.body()
                    } else {
                        Log.d("@@@", response.toString())
                    }
                }

                override fun onFailure(call: Call<Post?>, t: Throwable) {
                    Log.d("@@@", t.toString())
                }
            })
    }
}