package id.koridor50.jesika.ui.new_member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.koridor50.jesika.R
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.databinding.ItemAnggotaBaruBinding

class NewMemberListAdapter : RecyclerView.Adapter<NewMemberListAdapter.NewMemberListViewHolder>() {

    inner class NewMemberListViewHolder (val binding: ItemAnggotaBaruBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(_user: User) = binding.apply {
            user = _user
        }
    }

    private val members : MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMemberListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAnggotaBaruBinding>(inflater, R.layout.item_anggota_baru, parent, false)
        return NewMemberListViewHolder(binding)
    }

    override fun getItemCount(): Int = members.size

    override fun onBindViewHolder(holder: NewMemberListViewHolder, position: Int) {
        val member = members[position]
        holder.bind(member)
    }

    fun addItems (_member: User) {
        if (members.isNotEmpty())
            members.clear()

        members.add(_member)
        notifyDataSetChanged()
    }
}