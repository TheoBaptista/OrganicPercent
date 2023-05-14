package br.edu.ifrs.poa.organicpercent.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.ifrs.poa.organicpercent.OrganicPercentApplication
import br.edu.ifrs.poa.organicpercent.databinding.FragmentProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val productViewModel =
            ViewModelProvider(this)[ProductViewModel::class.java]

        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val app = requireContext().applicationContext as OrganicPercentApplication
        val supplierDao = app.db.supplierDao()
        val productDao = app.db.productDao()

        // Perform database operations on a background thread
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            val suppliers = supplierDao.listAll()
            val products = productDao.listAll()
            println("fornecedores: ${suppliers.size} produtos: ${products.size}")
        }

        val textView: TextView = binding.textHome
        productViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}