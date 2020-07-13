package id.koridor50.jesika.ui.new_member

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.databinding.NewMemberFragmentBinding
import kotlinx.android.synthetic.main.confirmation_dialog_layout.*
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
        binding.fragment = this

        adapter = NewMemberListAdapter()

        rvNewMemberIdentity.layoutManager = LinearLayoutManager(context)
        rvNewMemberIdentity.adapter = adapter

        viewModel.newMemberLiveData.observe(viewLifecycleOwner, Observer {
            adapter.addItems(it)
        })

        viewModel.localCommunityLiveData.observe(viewLifecycleOwner, Observer {
            binding.localCommunity = it
        })

        viewModel.isSuccessLiveData.observe(viewLifecycleOwner, Observer {
            showSuccessDialog()
        })
    }

    fun popBackStack () {
        findNavController().popBackStack()
    }

    private fun showSuccessDialog () {
        activity?.let {
            val dialog = Dialog(it)
            var message = "Anda telah menambahkan "

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.success_dialog_layout)


            viewModel.newMemberLiveData.observe(viewLifecycleOwner, Observer {
                message += it.name
            })
            viewModel.localCommunityLiveData.observe(viewLifecycleOwner, Observer {
                message += " ke " + it.name
            })

            dialog.tvMessage.text = message

            dialog.btnYes.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}