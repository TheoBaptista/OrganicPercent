package br.edu.ifrs.poa.organicpercent.ui.supplier

import android.app.AlertDialog
import android.content.Intent
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
import br.edu.ifrs.poa.organicpercent.model.Supplier
import br.edu.ifrs.poa.organicpercent.ui.recyclerview.adapter.SupplierListAdapter
import com.google.android.material.snackbar.Snackbar
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
                val supplierAdapter = SupplierListAdapter(
                    suppliers,
                    this@SupplierFragment::onDeleteSupplier,
                    this@SupplierFragment::onUpdateSupplier
                )
                recyclerView.adapter = supplierAdapter
                supplierListAdapter = supplierAdapter
            }
        }
    }

    private fun onDeleteSupplier(supplier: Supplier) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Excluir fornecedor")
        alertDialogBuilder.setMessage("Deseja excluir o fornecedor ?")
        alertDialogBuilder.setPositiveButton("Sim") { _, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    supplierDao.delete(supplier)
                    val updatedSuppliers = supplierDao.listAll()
                    withContext(Dispatchers.Main) {
                        supplierListAdapter.setData(updatedSuppliers)
                        showSnackbar("Fornecedor excluído com sucesso")
                    }
                }
            }
        }
        alertDialogBuilder.setNegativeButton("Não") { _, _ ->
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun onUpdateSupplier(supplier: Supplier) {
        val intent = Intent(requireContext(), UpdateSupplierActivity::class.java)
        intent.putExtra(UpdateSupplierActivity.EXTRA_SUPPLIER_ID, supplier.supplierId)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            val suppliers = withContext(Dispatchers.IO) { supplierDao.listAll() }
            supplierListAdapter.setData(suppliers)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}