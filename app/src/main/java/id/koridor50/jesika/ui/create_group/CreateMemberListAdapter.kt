package id.koridor50.jesika.ui.create_group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.koridor50.jesika.R
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.databinding.ItemAnggotaGroupBaruBinding

class CreateMemberListAdapter :
    RecyclerView.Adapter<CreateMemberListAdapter.CreateMemberListViewHolder>() {

    inner class CreateMemberListViewHolder (val binding: ItemAnggotaGroupBaruBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind (user : User) = binding.apply {
            member = user
        }
    }

    private val members : MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateMemberListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAnggotaGroupBaruBinding>(inflater, R.layout.item_anggota_group_baru, parent, false)
        return CreateMemberListViewHolder(binding)
    }

    override fun getItemCount(): Int = members.size

    override fun onBindViewHolder(holder: CreateMemberListViewHolder, position: Int) {
        val member = members[position]
        holder.bind(member)
    }

    fun addItems (_member: User) {
        members.add(_member)

        notifyDataSetChanged()
    }
}