package com.example.saborchileno.modelo

class CuentaMesa(val mesa: Int) {
    private val _items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val existente = _items.find { it.itemMenu.nombre == itemMenu.nombre }
        if (existente != null) {
            existente.cantidad = cantidad
        } else {
            _items.add(ItemMesa(itemMenu, cantidad))
        }
    }

    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        var total = 0
        for (item in _items) {
            total += item.calcularSubtotal()
        }
        return total
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) {
            (calcularTotalSinPropina() * 0.10).toInt()
        } else {
            0
        }
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}