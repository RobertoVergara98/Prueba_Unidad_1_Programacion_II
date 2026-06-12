package com.example.saborchileno.modelo

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int {
        val precioInt = itemMenu.precio.toIntOrNull() ?: 0
        return precioInt * cantidad
    }
}