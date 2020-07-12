package id.koridor50.jesika.ui.voucher_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.koridor50.jesika.R
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.Voucher
import id.koridor50.jesika.databinding.ItemAnggotaGroupBaruBinding
import id.koridor50.jesika.databinding.ItemVoucherListBinding

class VoucherListAdapter(private val listener: VoucherListCallback): RecyclerView.Adapter<VoucherListAdapter.VoucherListViewHolder>() {

    inner class VoucherListViewHolder (val binding: ItemVoucherListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (item : Voucher) = binding.apply {
            voucher = item
        }
    }

    private val items : MutableList<Voucher> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVoucherListBinding>(inflater, R.layout.item_voucher_list, parent, false)
        return VoucherListViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VoucherListViewHolder, position: Int) {
        val member = items[position]
        holder.bind(member)

        holder.itemView.setOnClickListener { listener.onVoucherClicked(member.id) }
    }

    fun addItems (vouchers: List<Voucher>) {
        if (items.isNotEmpty())
            items.clear()
        items.addAll(vouchers)

        notifyDataSetChanged()
    }

    interface VoucherListCallback {
        fun onVoucherClicked (idVoucher: Int)
    }
}