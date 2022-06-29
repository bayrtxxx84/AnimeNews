package com.example.animenews

class APICall {

    fun returnItems(): List<ItemDataClass> {
        return listOf<ItemDataClass>(
            ItemDataClass(1, R.drawable.arturos, "Arturos"),
            ItemDataClass(2, R.drawable.foodpanda, "FootPanda"),
            ItemDataClass(3, R.drawable.swiggy, "Swiggy"),
            ItemDataClass(4, R.drawable.uber, "Uber"),
            ItemDataClass(5, R.drawable.yelp, "Yelp"),
            ItemDataClass(6, R.drawable.zomato, "Zomato")
        )
    }
}