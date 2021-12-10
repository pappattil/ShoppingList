package com.example.shoppinglist.data

import androidx.room.*


@Dao
interface ListDAO {

    @Query("SELECT * FROM shoptable")
    fun getAllLists(): List<ShopList>

    @Insert
    fun insertList(vararg list: ShopList): Long

    @Delete
    fun deleteList(list:ShopList)

    @Update
    fun updateList(list:ShopList)

}