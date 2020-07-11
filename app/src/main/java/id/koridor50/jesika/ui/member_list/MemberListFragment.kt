package id.koridor50.jesika.ui.member_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.koridor50.jesika.R

class MemberListFragment : Fragment() {

    companion object {
        fun newInstance() = MemberListFragment()
    }

    private lateinit var viewModel: MemberListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.member_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MemberListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}