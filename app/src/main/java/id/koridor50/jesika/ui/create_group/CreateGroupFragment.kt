package id.koridor50.jesika.ui.create_group

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
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
import id.koridor50.jesika.databinding.CreateGroupFragmentBinding
import kotlinx.android.synthetic.main.confirmation_dialog_layout.*
import javax.inject.Inject

class CreateGroupFragment : Fragment() {

    @Inject lateinit var viewModel: CreateGroupViewModel
    private lateinit var binding: CreateGroupFragmentBinding
    private lateinit var adapter: CreateMemberListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_group_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CreateMemberListAdapter()

        binding.viewmodel = viewModel
        binding.fragment = this

        binding.rvCreateMemberList.adapter = adapter
        binding.rvCreateMemberList.layoutManager = LinearLayoutManager(context)

        viewModel.memberLiveData.observe(viewLifecycleOwner, Observer {
            adapter.addItems(it)
        })

        viewModel.isSuccessLiveData.observe(viewLifecycleOwner, Observer {isSuccess ->
            if (isSuccess)
                showSuccessDialog()
        })
    }

    fun popBackStack () {
        findNavController().popBackStack()
    }

    private fun showSuccessDialog () {
        activity?.let {
            val dialog = Dialog(it)
            var message = "Anda telah membuat local community "

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.success_dialog_layout)


            viewModel.localCommunityNameLiveData.observe(viewLifecycleOwner, Observer {
                dialog.tvMessage.text = message + it
            })

            dialog.btnYes.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}