package com.example.shelforganizer.databaseConnectivity

data class Store(
    val placeName: String = "",
    val items: MutableList<Item> = mutableListOf()
)
data class Item(
    val itemName:String="",
    val shelf:String="",
    val category:String="",
    val quantity:Int=0,
    val price: Double=0.0
)
