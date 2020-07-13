package id.koridor50.jesika.ui.member_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.koridor50.jesika.R
import id.koridor50.jesika.data.model.LocalCommunityUser
import id.koridor50.jesika.databinding.ItemMemberListBinding

class MemberListAdapter constructor(val callback: MemberListAdapterCallback) : RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {

    inner class MemberListViewHolder (val binding: ItemMemberListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (item: LocalCommunityUser) = binding.apply {
            data = item
        }
    }

    private val items : MutableList<LocalCommunityUser> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMemberListBinding>(inflater, R.layout.item_member_list, parent, false)
        return MemberListViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        val member = items[position]
        holder.bind(member)

        holder.binding.tvRemove.setOnClickListener { callback.onRemoveClicked(member.id, member.user.name) }
    }

    fun addItems (_items: List<LocalCommunityUser>) {
        if (items.isNotEmpty())
            items.clear()
        items.addAll(_items)

        notifyDataSetChanged()
    }

    interface MemberListAdapterCallback {
        fun onRemoveClicked(idUser: Int, removedMemberName: String)
    }
}