package dev.sarquella.studyplanner.helpers.extensions

import android.text.Editable
import android.text.TextWatcher
import br.com.sapereaude.maskedEditText.MaskedEditText


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */
 
fun MaskedEditText.afterTextChanged(callback: (Editable?) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            callback(s)
        }
    })
}