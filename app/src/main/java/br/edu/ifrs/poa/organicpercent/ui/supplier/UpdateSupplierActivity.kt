package br.edu.ifrs.poa.organicpercent.ui.supplier

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifrs.poa.organicpercent.OrganicPercentApplication
import br.edu.ifrs.poa.organicpercent.R
import br.edu.ifrs.poa.organicpercent.database.SupplierDao
import br.edu.ifrs.poa.organicpercent.model.Supplier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateSupplierActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SUPPLIER_ID = "extra_supplier_id"
    }

    private lateinit var supplierDao: SupplierDao
    private lateinit var supplier: Supplier

    private lateinit var nameEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_supplier)

        supplierDao = (application as OrganicPercentApplication).db.supplierDao()

        nameEditText = findViewById(R.id.edit_text_name)
        addressEditText = findViewById(R.id.edit_text_address)
        phoneEditText = findViewById(R.id.edit_text_phone)
        updateButton = findViewById(R.id.button_update)

        val supplierId = intent.getLongExtra(EXTRA_SUPPLIER_ID, -1)
        if (supplierId == -1L) {
            finish()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            supplier = supplierDao.findById(supplierId)!!

            withContext(Dispatchers.Main) {
                nameEditText.setText(supplier.name)
                addressEditText.setText(supplier.address)
                phoneEditText.setText(supplier.phone)

                updateButton.setOnClickListener {
                    val updatedName = nameEditText.text.toString()
                    val updatedAddress = addressEditText.text.toString()
                    val updatedPhone = phoneEditText.text.toString()

                    if (!formIsValid()) {
                        Toast.makeText(
                            this@UpdateSupplierActivity,
                            "Corrija os erros no formulário!!",
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }

                    supplier.name = updatedName
                    supplier.address = updatedAddress
                    supplier.phone = updatedPhone

                    CoroutineScope(Dispatchers.IO).launch {
                        supplierDao.update(supplier)
                    }


                    finish()
                }
            }
        }
    }

    private fun formIsValid(): Boolean {
        var isValid = true

        val supplierName = nameEditText.text.toString().trim()
        if (supplierName.isEmpty()) {
            nameEditText.error = "Nome do fornecedor é obrigatório"
            isValid = false
        }

        val supplierAddress = addressEditText.text.toString().trim()
        if (supplierAddress.isEmpty()) {
            addressEditText.error = "Endereço do fornecedor é obrigatório"
            isValid = false
        }

        val supplierPhone = phoneEditText.text.toString().trim()
        if (supplierPhone.isEmpty()) {
            phoneEditText.error = "Telefone do fornecedor é obrigatório"
            isValid = false
        }

        return isValid
    }
}
