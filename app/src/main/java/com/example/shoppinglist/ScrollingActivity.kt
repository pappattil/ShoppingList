package com.example.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.shoppinglist.adapter.ListAdapter
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.ShopList
import com.example.shoppinglist.touch.ListRecyclerTouchCallback
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlin.concurrent.thread

class ScrollingActivity : AppCompatActivity(), ListDialog.ListHandler {
    private lateinit var listAdapter: ListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))

        initRecyclerView()

        fab.setOnClickListener {
            ListDialog().show(supportFragmentManager, "Add_Dialog")

        }

        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

    }

    private fun initRecyclerView() {
        thread{
            val listAll = AppDatabase.getInstance(this@ScrollingActivity).listDao().getAllLists()
            runOnUiThread {
                listAdapter = ListAdapter(this, listAll)
                recyclerList.adapter = listAdapter
                //recyclerList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                val touchCallback = ListRecyclerTouchCallback(listAdapter)
                val itemTouchHelper = ItemTouchHelper(touchCallback)
                itemTouchHelper.attachToRecyclerView(recyclerList)
            }
        }

    }

    

    override fun listCreated(list: ShopList) {
        thread{
            AppDatabase.getInstance(this@ScrollingActivity).listDao().insertList(list)
            runOnUiThread {
                listAdapter.addList(list)
            }

        }

    }
}