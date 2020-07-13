package id.koridor50.jesika.ui.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.databinding.LoginFragmentBinding
import id.koridor50.jesika.utils.savePref
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject lateinit var viewModel: LoginViewModel
    lateinit var binding: LoginFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        viewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            saveToSp(it)
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
        })
    }

    private fun saveToSp(user: User) {
        requireContext().savePref(PrefKey.USERIDPREFKEY, user.id)
        user.localCommunity?.let {
            requireContext().savePref(PrefKey.LOCALCOMMUNITYIDPREFKEY, user.localCommunity.id)
        }
    }
}