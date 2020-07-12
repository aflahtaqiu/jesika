package id.koridor50.jesika.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject lateinit var homeViewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cvCreateGroup.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToCreateGroupFragment()) }
        cvAddNewMember.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToTambahAnggotaFragment()) }
    }
}