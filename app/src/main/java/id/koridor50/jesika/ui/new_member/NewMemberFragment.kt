package id.koridor50.jesika.ui.new_member

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.databinding.NewMemberFragmentBinding
import kotlinx.android.synthetic.main.new_member_fragment.*
import javax.inject.Inject

class NewMemberFragment : Fragment() {

    @Inject
    lateinit var viewModel: NewMemberViewModel
    private lateinit var binding: NewMemberFragmentBinding
    private lateinit var adapter: NewMemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_member_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewmodel = viewModel
        adapter = NewMemberListAdapter()

        rvNewMemberIdentity.layoutManager = LinearLayoutManager(context)
        rvNewMemberIdentity.adapter = adapter

        viewModel.newMemberLiveData.observe(viewLifecycleOwner, Observer {
            adapter.addItems(it)
        })
    }

}