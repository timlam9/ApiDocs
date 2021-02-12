package co.api.docs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.api.docs.databinding.ActivityMainBinding
import co.api.docs.databinding.ItemApiBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ApiAdapter()
        binding.apis.adapter = adapter

        val retrofit =
            Retrofit.Builder()
                .baseUrl("http://192.168.1.101:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DocsApi::class.java)
        retrofit.apis().enqueue(object : Callback<List<Api>> {
            override fun onResponse(call: Call<List<Api>>, response: Response<List<Api>>) {
                adapter.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Api>>, t: Throwable) {
                Log.d("TAGARA", t.toString())
            }

        })
    }

    class ApiAdapter : ListAdapter<Api, ApiViewHolder>(ApiDiffUtil()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
            return ApiViewHolder(ItemApiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

    }

    class ApiViewHolder(private val binding: ItemApiBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(api: Api) {
            binding.title.text = api.name
        }

    }

    class ApiDiffUtil : DiffUtil.ItemCallback<Api>() {

        override fun areItemsTheSame(oldItem: Api, newItem: Api): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Api, newItem: Api): Boolean {
            return oldItem == newItem
        }

    }
}
