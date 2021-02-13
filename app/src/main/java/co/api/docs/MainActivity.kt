package co.api.docs

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.api.docs.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ApiAdapter {
            viewModel.onItemClicked(it)
        }
        binding.apis.adapter = adapter

        viewModel.list.onEach { adapter.submitList(it) }.launchIn(lifecycleScope)

        val retrofit =
            Retrofit.Builder()
                .baseUrl("http://192.168.1.101:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DocsApi::class.java)
        retrofit.apis().enqueue(object : Callback<List<Api>> {
            override fun onResponse(call: Call<List<Api>>, response: Response<List<Api>>) {
                viewModel.onDocReceived(response.body()!!)
            }

            override fun onFailure(call: Call<List<Api>>, t: Throwable) {
                Log.d("TAGARA", t.toString())
            }

        })
    }

}

