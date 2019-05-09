package dev.sarquella.studyplanner.helpers.extensions

import android.widget.RadioButton
import android.widget.RadioGroup


/*
 * Created by Adrià Sarquella Farrés
 * adria@sarquella.dev
 */

private fun RadioGroup.getSelectedButtonTintColor(): Int? =
    this.findViewById<RadioButton>(this.checkedRadioButtonId).buttonTintList?.defaultColor

fun RadioGroup.getSelectedButtonTintHexColor(): String = getSelectedButtonTintColor()?.toHexColor() ?: "#FFF"