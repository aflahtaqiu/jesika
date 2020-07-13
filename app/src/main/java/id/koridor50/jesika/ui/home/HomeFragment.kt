package id.koridor50.jesika.ui.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        viewModel.localCommunityLiveData.observe(viewLifecycleOwner, Observer {
            binding.localCommunity = it
        })

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            binding.user = it
        })

        cvCreateGroup.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToCreateGroupFragment()) }
        cvAddNewMember.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNewMemberFragment()) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocalCommunityData()
    }

    fun moveToChatRoom () {
        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToChatRoomFragment())
    }
}