package id.koridor50.jesika.ui.redeem_promo

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.databinding.RedeemPromoFragmentBinding
import javax.inject.Inject

class RedeemPromoFragment : Fragment() {

    @Inject lateinit var viewModel: RedeemPromoViewModel
    private lateinit var binding: RedeemPromoFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.redeem_promo_fragment, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel

        val voucherArgs by navArgs<RedeemPromoFragmentArgs>()

        viewModel.getVoucherDetail(voucherArgs.idVoucher)

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            binding.user = it
        })

        viewModel.voucherLiveData.observe(viewLifecycleOwner, Observer {
            binding.voucher = it
        })
    }
}