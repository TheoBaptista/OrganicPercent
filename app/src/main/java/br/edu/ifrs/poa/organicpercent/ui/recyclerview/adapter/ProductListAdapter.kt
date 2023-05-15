package br.edu.ifrs.poa.organicpercent.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifrs.poa.organicpercent.R
import br.edu.ifrs.poa.organicpercent.model.Product

class ProductListAdapter(
    private var products: List<Product>
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.product_name_textview)
        val supplierNameView: TextView = view.findViewById(R.id.supplier_product_name_textview)
        val descriptionView: TextView = view.findViewById(R.id.product_description_textview)
        val valueView: TextView = view.findViewById(R.id.product_value_textview)
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
    }

    override fun getItemCount() = products.size

    fun setData(newData: List<Product>) {
        this.products = newData
        notifyDataSetChanged()
    }

}