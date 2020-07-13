package id.koridor50.jesika.ui.redeem_promo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.databinding.RedeemPromoFragmentBinding
import kotlinx.android.synthetic.main.confirmation_dialog_layout.*
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
        binding.fragment = this

        val voucherArgs by navArgs<RedeemPromoFragmentArgs>()

        viewModel.getVoucherDetail(voucherArgs.idVoucher)

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            binding.user = it
        })

        viewModel.voucherLiveData.observe(viewLifecycleOwner, Observer {
            binding.voucher = it
        })
    }

    fun popBackStack () {
        findNavController().popBackStack()
    }

    fun showConfirmationDialog() {
        activity?.let {
            val dialog = Dialog(it)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.confirmation_dialog_layout)

            viewModel.messageLiveData.observe(viewLifecycleOwner, Observer { dialog.tvMessage.text = it })
            viewModel.isSuccessCheckoutLiveData.observe(viewLifecycleOwner, Observer {isCheckout->

                if(isCheckout) {
                    dialog.dismiss()
                    showSuccessDialog()
                }
            })

            dialog.btnYes.setOnClickListener {
                viewModel.checkoutVoucher()
            }

            dialog.btnNo.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    private fun showSuccessDialog () {
        activity?.let {
            val dialog = Dialog(it)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.success_dialog_layout)

            viewModel.voucherLiveData.observe(viewLifecycleOwner, Observer { dialog.tvMessage.text = "Anda telah menggunakan voucher ${it.name}" })

            dialog.btnYes.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}