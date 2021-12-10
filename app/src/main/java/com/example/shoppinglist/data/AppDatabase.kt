package com.example.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ShopList::class), version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun listDao():ListDAO

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if(INSTANCE==null){
                INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase::class.java,"shoptable.db").build()
            }
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}