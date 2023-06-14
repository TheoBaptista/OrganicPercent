package br.edu.ifrs.poa.organicpercent.ui.product

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifrs.poa.organicpercent.OrganicPercentApplication
import br.edu.ifrs.poa.organicpercent.R
import br.edu.ifrs.poa.organicpercent.database.ProductDao
import br.edu.ifrs.poa.organicpercent.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateProductActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }

    private lateinit var productDao: ProductDao
    private lateinit var product: Product

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var valueEditText: EditText
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        productDao = (application as OrganicPercentApplication).db.productDao()

        nameEditText = findViewById(R.id.edit_text_name)
        descriptionEditText = findViewById(R.id.edit_text_description)
        valueEditText = findViewById(R.id.edit_text_value)
        updateButton = findViewById(R.id.button_update)

        val productId = intent.getLongExtra(EXTRA_PRODUCT_ID, -1)
        if (productId == -1L) {
            finish()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            product = productDao.findById(productId)!!

            withContext(Dispatchers.Main) {
                nameEditText.setText(product.name)
                descriptionEditText.setText(product.description)
                valueEditText.setText(product.value)

                updateButton.setOnClickListener {
                    val updatedName = nameEditText.text.toString()
                    val updatedDescription = descriptionEditText.text.toString()
                    val updatedValue = valueEditText.text.toString()

                    if (!formIsValid()) {
                        Toast.makeText(
                            this@UpdateProductActivity,
                            "Corrija os erros no formulário!!",
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }

                    product.name = updatedName
                    product.description = updatedDescription
                    product.value = updatedValue

                    CoroutineScope(Dispatchers.IO).launch {
                        productDao.update(product)
                    }

                    finish()
                }
            }
        }

    }

    private fun formIsValid(): Boolean {
        var isValid = true

        val productName = nameEditText.text.toString().trim()
        if (productName.isEmpty()) {
            nameEditText.error = "Nome do produto é obrigatório"
            isValid = false
        }

        val productDescription = descriptionEditText.text.toString().trim()
        if (productDescription.isEmpty()) {
            descriptionEditText.error = "Descrição do produto é obrigatória"
            isValid = false
        }

        val productValue = valueEditText.text.toString().trim()
        if (productValue.isEmpty()) {
            valueEditText.error = "Valor do produto é obrigatório"
            isValid = false
        } else {
            try {
                val value = productValue.toDouble()
                if (value <= 0) {
                    valueEditText.error = "Valor do produto deve ser maior que zero"
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                valueEditText.error = "Valor do produto inválido"
                isValid = false
            }
        }
        return isValid
    }
}