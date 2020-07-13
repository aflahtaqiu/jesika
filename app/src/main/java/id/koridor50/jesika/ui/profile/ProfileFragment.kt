package id.koridor50.jesika.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.data.model.Voucher
import id.koridor50.jesika.databinding.ProfileFragmentBinding
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var adapter: UsedVouchersAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UsedVouchersAdapter()
        binding.fragment = this

        binding.rvUsedVouchers.adapter = adapter
        binding.rvUsedVouchers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            binding.profile = it
        })

        viewModel.voucherLiveData.observe(viewLifecycleOwner, Observer {
            binding.voucher = it
        })

        viewModel.usedVoucherLiveData.observe(viewLifecycleOwner, Observer {
            adapter.addItems(it)
        })
    }

    fun moveVoucherList () {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToVoucherListFragment())
    }

    fun moveRedeemVoucher (idVoucher: Int) {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToRedeemPromoFragment(idVoucher))
    }

    fun logout () {
        viewModel.clearPreference()
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAuthActivity())
    }
}