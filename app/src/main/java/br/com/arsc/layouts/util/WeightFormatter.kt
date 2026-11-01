package br.com.arsc.layouts.util

import java.util.Locale

fun formatWeight(value: String): String {
    val digits = value.filter { it in '0'..'9' }
    val number = digits.toLongOrNull() ?: 0L

    return String.format(
        Locale("pt", "BR"),
        "%.2f",
        number / 100.0
    )
}