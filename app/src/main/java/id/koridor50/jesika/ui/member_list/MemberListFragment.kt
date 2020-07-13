package id.koridor50.jesika.ui.member_list

import android.content.Context
import android.os.Bundle
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
import id.koridor50.jesika.databinding.MemberListFragmentBinding
import javax.inject.Inject

class MemberListFragment : Fragment() {

    @Inject lateinit var viewModel: MemberListViewModel
    private lateinit var binding: MemberListFragmentBinding
    private lateinit var adapter: MemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.member_list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MemberListAdapter()
        binding.fragment = this

        binding.rvMemberList.adapter = adapter
        binding.rvMemberList.layoutManager = LinearLayoutManager(context)

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            binding.user = it
        })

        viewModel.membersLiveData.observe(viewLifecycleOwner, Observer {
            binding.localCommunity = it
            adapter.addItems(it.anggota)
        })
    }

    fun popBackStack () {
        findNavController().popBackStack()
    }
}