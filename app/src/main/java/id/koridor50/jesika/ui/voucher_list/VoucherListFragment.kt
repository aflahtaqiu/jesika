package id.koridor50.jesika.ui.voucher_list

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.data.model.Voucher
import kotlinx.android.synthetic.main.voucher_list_fragment.*
import javax.inject.Inject

class VoucherListFragment : Fragment(), VoucherListAdapter.VoucherListCallback {

   @Inject lateinit var viewModel: VoucherListViewModel

    private lateinit var adapter: VoucherListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.voucher_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VoucherListAdapter(this)

        rvVoucherList.adapter = adapter
        rvVoucherList.layoutManager = GridLayoutManager(context, 2)

        viewModel.voucherLiveData.observe(viewLifecycleOwner, Observer {
            adapter.addItems(it)
        })
    }

    override fun onVoucherClicked(idVoucher: Int) {
        findNavController().navigate(VoucherListFragmentDirections
            .actionVoucherListFragmentToRedeemPromoFragment(idVoucher))
    }
}