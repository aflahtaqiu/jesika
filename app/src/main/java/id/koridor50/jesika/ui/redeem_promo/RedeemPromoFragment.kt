package id.koridor50.jesika.ui.redeem_promo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.koridor50.jesika.R

class RedeemPromoFragment : Fragment() {

    companion object {
        fun newInstance() = RedeemPromoFragment()
    }

    private lateinit var viewModel: RedeemPromoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.redeem_promo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RedeemPromoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}