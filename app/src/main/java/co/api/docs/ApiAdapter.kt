package co.api.docs

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.api.docs.databinding.ItemApiBinding
import co.api.docs.databinding.ItemEndpointBinding

class ApiAdapter(val onApiClicked: (Doc) -> Unit) : ListAdapter<Doc, RecyclerView.ViewHolder>(DocDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_api -> ApiViewHolder(ItemApiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                .also { viewHolder ->
                    viewHolder.itemView.setOnClickListener {
                        onApiClicked(getItem(viewHolder.adapterPosition))
                    }
                }
            else -> EndpointViewHolder(ItemEndpointBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ApiViewHolder -> holder.bind(getItem(position) as Doc.Api)
            is EndpointViewHolder -> holder.bind(getItem(position) as Doc.Endpoint)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Doc.Api -> R.layout.item_api
            is Doc.Endpoint -> R.layout.item_endpoint
        }
    }

}

class ApiViewHolder(private val binding: ItemApiBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(api: Doc.Api) {
        binding.title.text = api.name
    }

}

class EndpointViewHolder(private val binding: ItemEndpointBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(endpoint: Doc.Endpoint) {
        binding.endpointTitle.text = endpoint.path
        binding.method.text = "@" + endpoint.methodType
        if (endpoint.methodType == "GET") {
            binding.method.setTextColor(Color.BLACK)
            binding.method.setBackgroundColor(Color.MAGENTA)
        } else {
            binding.method.setTextColor(Color.WHITE)
            binding.method.setBackgroundColor(Color.BLUE)
        }
    }
}

class DocDiffUtil : DiffUtil.ItemCallback<Doc>() {

    override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return oldItem == newItem
    }

}
