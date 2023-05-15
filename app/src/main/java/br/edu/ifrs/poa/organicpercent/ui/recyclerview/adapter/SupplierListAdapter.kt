package br.edu.ifrs.poa.organicpercent.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifrs.poa.organicpercent.R
import br.edu.ifrs.poa.organicpercent.model.Supplier

class SupplierListAdapter(
    private var suppliers: List<Supplier>
) : RecyclerView.Adapter<SupplierListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.supplier_name_textview)
        val addressView: TextView = view.findViewById(R.id.supplier_address_textview)
        val phoneView: TextView = view.findViewById(R.id.supplier_phone_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.supplier_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val supplier = suppliers[position]
        holder.nameView.text = "${supplier.name}"
        holder.addressView.text = "Endereço: ${supplier.address}"
        holder.phoneView.text = "Telefone: ${supplier.phone}"
    }

    override fun getItemCount() = suppliers.size

    fun setData(newData: List<Supplier>) {
        this.suppliers = newData
        notifyDataSetChanged()
    }

}