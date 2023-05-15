package br.edu.ifrs.poa.organicpercent.ui.form

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifrs.poa.organicpercent.OrganicPercentApplication
import br.edu.ifrs.poa.organicpercent.R
import br.edu.ifrs.poa.organicpercent.model.Product
import br.edu.ifrs.poa.organicpercent.model.Supplier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity() {

    private lateinit var productNameEditText: EditText
    private lateinit var productDescriptionEditText: EditText
    private lateinit var productValueEditText: EditText
    private lateinit var supplierNameEditText: EditText
    private lateinit var supplierAddressEditText: EditText
    private lateinit var supplierPhoneEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        productNameEditText = findViewById(R.id.activity_form_edit_text_product_name)
        productDescriptionEditText = findViewById(R.id.activity_form_edit_text_product_description)
        productValueEditText = findViewById(R.id.activity_form_edit_text_product_value)
        supplierNameEditText = findViewById(R.id.activity_form_edit_text_supplier_name)
        supplierAddressEditText = findViewById(R.id.activity_form_edit_text_supplier_address)
        supplierPhoneEditText = findViewById(R.id.activity_form_edit_text_supplier_phone)
        submitButton = findViewById(R.id.activity_form_save_button)

        submitButton.setOnClickListener { onSubmitClicked() }
    }

    private fun onSubmitClicked() {
        if (formIsValid()) {
            val productName = productNameEditText.text.toString().trim()
            val productDescription = productDescriptionEditText.text.toString().trim()
            val productValue = productValueEditText.text.toString().trim()
            val supplierName = supplierNameEditText.text.toString().trim()
            val supplierAddress = supplierAddressEditText.text.toString().trim()
            val supplierPhone = supplierPhoneEditText.text.toString().trim()
            val product = Product(
                name = productName,
                description = productDescription,
                value = productValue,
                supplierName = supplierName
            )
            val supplier =
                Supplier(name = supplierName, address = supplierAddress, phone = supplierPhone)

            val app = applicationContext as OrganicPercentApplication
            val supplierDao = app.db.supplierDao()
            val productDao = app.db.productDao()

            // Perform database operations on a background thread
            CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                supplierDao.insert(supplier)
                productDao.insert(product)
                setResult(Activity.RESULT_OK)
                finish()
            }

        } else {

            Toast.makeText(this, "Corrija os erros no formulário", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formIsValid(): Boolean {
        var isValid = true

        val productName = productNameEditText.text.toString().trim()
        if (productName.isEmpty()) {
            productNameEditText.error = "Nome do produto é obrigatório"
            isValid = false
        }

        val productDescription = productDescriptionEditText.text.toString().trim()
        if (productDescription.isEmpty()) {
            productDescriptionEditText.error = "Descrição do produto é obrigatória"
            isValid = false
        }

        val productValue = productValueEditText.text.toString().trim()
        if (productValue.isEmpty()) {
            productValueEditText.error = "Valor do produto é obrigatório"
            isValid = false
        } else {
            try {
                val value = productValue.toDouble()
                if (value <= 0) {
                    productValueEditText.error = "Valor do produto deve ser maior que zero"
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                productValueEditText.error = "Valor do produto inválido"
                isValid = false
            }
        }

        val supplierName = supplierNameEditText.text.toString().trim()
        if (supplierName.isEmpty()) {
            supplierNameEditText.error = "Nome do fornecedor é obrigatório"
            isValid = false
        }

        val supplierAddress = supplierAddressEditText.text.toString().trim()
        if (supplierAddress.isEmpty()) {
            supplierAddressEditText.error = "Endereço do fornecedor é obrigatório"
            isValid = false
        }

        val supplierPhone = supplierPhoneEditText.text.toString().trim()
        if (supplierPhone.isEmpty()) {
            supplierPhoneEditText.error = "Telefone do fornecedor é obrigatório"
            isValid = false
        }

        return isValid
    }
}