package id.koridor50.jesika.ui.tambah_anggota

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.koridor50.jesika.R

class TambahAnggotaFragment : Fragment() {

    companion object {
        fun newInstance() =
            TambahAnggotaFragment()
    }

    private lateinit var viewModel: TambahAnggotaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tambah_anggota_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TambahAnggotaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}