package com.example.saborchileno

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.saborchileno.modelo.CuentaMesa
import com.example.saborchileno.modelo.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val cuentaMesa = CuentaMesa(mesa = 1)
    private val pastelDeChoclo = ItemMenu("Pastel de Choclo", "12000")
    private val cazuela = ItemMenu("Cazuela", "10000")
    private val formatoMoneda: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etCantidadPastel = findViewById<EditText>(R.id.etCantidadPastel)
        val etCantidadCazu = findViewById<EditText>(R.id.etCantidadCazu)
        val switchPropina = findViewById<SwitchCompat>(R.id.switchPropina)

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotalesPantalla()
        }

        val textWatcherComun = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val cantPastel = etCantidadPastel.text.toString().toIntOrNull() ?: 0
                val cantCazu = etCantidadCazu.text.toString().toIntOrNull() ?: 0

                cuentaMesa.agregarItem(pastelDeChoclo, cantPastel)
                cuentaMesa.agregarItem(cazuela, cantCazu)

                actualizarSubtotalesIndividuales(cantPastel, cantCazu)
                actualizarTotalesPantalla()
            }
        }

        etCantidadPastel.addTextChangedListener(textWatcherComun)
        etCantidadCazu.addTextChangedListener(textWatcherComun)
    }

    private fun actualizarSubtotalesIndividuales(cantPastel: Int, cantCazu: Int) {
        val txtSubtotalPastel = findViewById<TextView>(R.id.txtSubtotalPastel)
        val txtSubtotalCazu = findViewById<TextView>(R.id.txtSubtotalCazu)

        val subtotalPastel = (pastelDeChoclo.precio.toInt() * cantPastel)
        val subtotalCazu = (cazuela.precio.toInt() * cantCazu)

        txtSubtotalPastel.text = formatoMoneda.format(subtotalPastel)
        txtSubtotalCazu.text = formatoMoneda.format(subtotalCazu)
    }

    private fun actualizarTotalesPantalla() {
        val txtTotalComida = findViewById<TextView>(R.id.txtTotalComida)
        val txtTotalPropina = findViewById<TextView>(R.id.txtTotalPropina)
        val txtTotalFinal = findViewById<TextView>(R.id.txtTotalFinal)

        val comida = cuentaMesa.calcularTotalSinPropina()
        val propina = cuentaMesa.calcularPropina()
        val total = cuentaMesa.calcularTotalConPropina()

        txtTotalComida.text = formatoMoneda.format(comida)
        txtTotalPropina.text = formatoMoneda.format(propina)
        txtTotalFinal.text = formatoMoneda.format(total)
    }
}