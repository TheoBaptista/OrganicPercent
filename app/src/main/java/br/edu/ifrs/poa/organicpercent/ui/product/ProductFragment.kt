package br.edu.ifrs.poa.organicpercent.ui.product

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
import br.edu.ifrs.poa.organicpercent.database.ProductDao
import br.edu.ifrs.poa.organicpercent.model.Product
import br.edu.ifrs.poa.organicpercent.ui.recyclerview.adapter.ProductListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductFragment : Fragment() {

    private lateinit var productDao: ProductDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDao = (requireActivity().application as OrganicPercentApplication).db.productDao()

        recyclerView = view.findViewById(R.id.product_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val products = productDao.listAll()
            withContext(Dispatchers.Main) {
                val productAdapter = ProductListAdapter(
                    products,
                    this@ProductFragment::onDeleteProduct,
                    this@ProductFragment::onUpdateProduct
                )
                recyclerView.adapter = productAdapter
                productListAdapter = productAdapter
            }
        }
    }

    private fun onDeleteProduct(product: Product) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Excluir fornecedor")
        alertDialogBuilder.setMessage("Deseja excluir o fornecedor ?")
        alertDialogBuilder.setPositiveButton("Sim") { _, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    productDao.delete(product)
                    val updatedSuppliers = productDao.listAll()
                    withContext(Dispatchers.Main) {
                        productListAdapter.setData(updatedSuppliers)
                        showSnackbar("Produto excluído com sucesso")
                    }
                }
            }
        }
        alertDialogBuilder.setNegativeButton("Não") { _, _ ->
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun onUpdateProduct(product: Product) {
        val intent = Intent(requireContext(), UpdateProductActivity::class.java)
        intent.putExtra(UpdateProductActivity.EXTRA_PRODUCT_ID, product.productId)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            val products = productDao.listAll()
            withContext(Dispatchers.Main) {
                productListAdapter.setData(products)
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}