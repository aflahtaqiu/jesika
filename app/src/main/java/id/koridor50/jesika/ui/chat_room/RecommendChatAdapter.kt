package id.koridor50.jesika.ui.chat_room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.koridor50.jesika.R
import id.koridor50.jesika.data.model.LocalCommunityUser
import id.koridor50.jesika.databinding.ItemChatbotBinding
import id.koridor50.jesika.databinding.ItemMemberListBinding

class RecommendChatAdapter constructor(callback: RecommendChatCallback): RecyclerView.Adapter<RecommendChatAdapter.RecommendChatViewHolder>() {

    inner class RecommendChatViewHolder (val binding: ItemChatbotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (string: String) = binding.apply {
            data = string
        }
    }

    private val items : MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemChatbotBinding>(inflater, R.layout.item_chatbot, parent, false)
        return RecommendChatViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecommendChatViewHolder, position: Int) {
        val member = items[position]
        holder.bind(member)
    }

    fun addItems (_items: List<String>) {
        if (items.isNotEmpty())
            items.clear()
        items.addAll(_items)

        notifyDataSetChanged()
    }

    interface RecommendChatCallback {
        fun onRecomendChatClicked (string: String)
    }
}