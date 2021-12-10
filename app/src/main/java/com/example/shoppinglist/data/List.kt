package com.example.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shoptable")
data class ShopList(
        @PrimaryKey(autoGenerate = true) var listID: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "price") var price: Int,
        @ColumnInfo(name = "category") var category: Int,
        @ColumnInfo(name = "details") var details: String,
        @ColumnInfo(name = "status") var status: Boolean,
        @ColumnInfo(name = "currency") var currency: String
):Serializable
