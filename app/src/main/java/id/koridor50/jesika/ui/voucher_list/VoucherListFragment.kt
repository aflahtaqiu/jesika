package id.koridor50.jesika.ui.voucher_list

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.data.model.Voucher
import id.koridor50.jesika.databinding.VoucherListFragmentBinding
import kotlinx.android.synthetic.main.voucher_list_fragment.*
import kotlinx.android.synthetic.main.voucher_list_fragment.view.*
import javax.inject.Inject

class VoucherListFragment : Fragment(), VoucherListAdapter.VoucherListCallback {

   @Inject lateinit var viewModel: VoucherListViewModel
    private lateinit var adapter: VoucherListAdapter
    private lateinit var binding: VoucherListFragmentBinding
    private lateinit var list : List<Voucher>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.voucher_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VoucherListAdapter(this)
        binding.fragment = this

        rvVoucherList.adapter = adapter
        rvVoucherList.layoutManager = GridLayoutManager(context, 2)

        viewModel.voucherLiveData.observe(viewLifecycleOwner, Observer {
            list = it
            adapter.addItems(it.filter { voucher -> voucher.category == 1})
        })

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            tv_user_poins.text = it.roundPoin
        })
        onCategoryClickListener()
    }

    private fun onCategoryClickListener() {
        ln_cat_1.setOnClickListener {
            clickEffect(0)
            adapter.addItems(list.filter { v ->  v.category == 1})
        }
        ln_cat_2.setOnClickListener {
            clickEffect(1)
            adapter.addItems(list.filter { v ->  v.category == 2})
        }
        ln_cat_3.setOnClickListener {
            clickEffect(2)
            adapter.addItems(list.filter { v ->  v.category == 3})
        }
        ln_cat_4.setOnClickListener {
            clickEffect(3)
            adapter.addItems(list.filter { v ->  v.category == 4})
        }
        ln_cat_5.setOnClickListener {
            clickEffect(4)
            adapter.addItems(list.filter { v ->  v.category == 5})
        }
    }

    override fun onVoucherClicked(idVoucher: Int) {
        findNavController().navigate(VoucherListFragmentDirections
            .actionVoucherListFragmentToRedeemPromoFragment(idVoucher))
    }

    fun popBackStack () {
        findNavController().popBackStack()
    }

    private fun clickEffect(index: Int) {
        val listLinearLayout = arrayOf(ln_cat_1, ln_cat_2, ln_cat_3, ln_cat_4, ln_cat_5)
        val listTextView = arrayOf(tv_cat_1, tv_cat_2, tv_cat_3, tv_cat_4, tv_cat_5)
        for (i in listLinearLayout.indices) {
            if (i == index) {
                listLinearLayout[i].background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_solid_blue)
                listTextView[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                listLinearLayout[i].background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_circle_stroke_grey)
                listTextView[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.primaryColor))
            }
        }
    }
}