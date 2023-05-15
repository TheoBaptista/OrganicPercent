package br.edu.ifrs.poa.organicpercent.ui.supplier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifrs.poa.organicpercent.OrganicPercentApplication
import br.edu.ifrs.poa.organicpercent.R
import br.edu.ifrs.poa.organicpercent.database.SupplierDao
import br.edu.ifrs.poa.organicpercent.ui.recyclerview.adapter.SupplierListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SupplierFragment : Fragment() {

    private lateinit var supplierDao: SupplierDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var supplierListAdapter: SupplierListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_supplier, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        supplierDao = (requireActivity().application as OrganicPercentApplication).db.supplierDao()

        recyclerView = view.findViewById(R.id.supplier_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val suppliers = supplierDao.listAll()
            withContext(Dispatchers.Main) {
                val supplierAdapter = SupplierListAdapter(suppliers)
                recyclerView.adapter = supplierAdapter
                supplierListAdapter = supplierAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            val suppliers = supplierDao.listAll()
            withContext(Dispatchers.Main) {
                supplierListAdapter.setData(suppliers)
            }
        }
    }
}