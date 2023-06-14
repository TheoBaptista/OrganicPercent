package br.edu.ifrs.poa.organicpercent.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifrs.poa.organicpercent.R
import br.edu.ifrs.poa.organicpercent.model.Product
import br.edu.ifrs.poa.organicpercent.model.Supplier

class ProductListAdapter(
    private var products: List<Product>,
    private val onDeleteClickListener: (Product) -> Unit,
    private val onUpdateClickListener: (Product) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.product_name_textview)
        val supplierNameView: TextView = view.findViewById(R.id.supplier_product_name_textview)
        val descriptionView: TextView = view.findViewById(R.id.product_description_textview)
        val valueView: TextView = view.findViewById(R.id.product_value_textview)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
        val updateButton: ImageButton = view.findViewById(R.id.update_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nameView.text = "${product.name}"
        holder.supplierNameView.text = "Fornecedor: ${product.supplierName}"
        holder.descriptionView.text = "Descrição: ${product.description}"
        holder.valueView.text = "Valor: ${product.value}"

        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(product)
        }

        holder.updateButton.setOnClickListener {
            onUpdateClickListener(product)
        }
    }

    override fun getItemCount() = products.size

    fun setData(newData: List<Product>) {
        this.products = newData
        notifyDataSetChanged()
    }

}