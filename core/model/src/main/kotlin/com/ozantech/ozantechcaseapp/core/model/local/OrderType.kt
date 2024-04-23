package com.ozantech.ozantechcaseapp.core.model.local

enum class OrderType(val type: String) {
    Price(type = "price"),
    MarketCap(type = "marketCap"),
    DailyVolume(type = "24hVolume"),
    Change(type = "change"),
    ListedAt(type = "listedAt")
}