package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var tipAmountTextView: TextView
    private lateinit var billValueTextView: TextView
    private lateinit var tipPercentTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_text)
        seekBar = findViewById(R.id.seek_bar)
        tipAmountTextView = findViewById(R.id.tip_amount_tv)
        billValueTextView = findViewById(R.id.bill_value_tv)
        tipPercentTextView = findViewById(R.id.tip_percent_tv)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val amount = s.toString().toDoubleOrNull()
                if (amount != null && amount > 0) {
                    billValueTextView.text = String.format("Bill Value: $%.2f", amount)
                    updateTipAmount(amount)
                } else {
                    billValueTextView.text = ""
                    tipAmountTextView.text = ""
                    tipPercentTextView.text = ""
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val amount = editText.text.toString().toDoubleOrNull()
                if (amount != null && amount > 0) {
                    updateTipAmount(amount)
                } else {
                    tipAmountTextView.text = ""
                    tipPercentTextView.text = ""
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateTipAmount(amount: Double) {
        val tipPercent = seekBar.progress
        val tipAmount = (amount * tipPercent) / 100
        tipAmountTextView.text = String.format("Tip Amount: $%.2f", tipAmount)
        tipPercentTextView.text = getString(R.string.tip_message, tipPercent)
    }
}
