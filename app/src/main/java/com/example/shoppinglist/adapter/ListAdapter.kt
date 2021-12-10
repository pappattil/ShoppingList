package com.example.shoppinglist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.ScrollingActivity
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.ShopList
import com.example.shoppinglist.touch.ListTouchHelperCallback
import kotlinx.android.synthetic.main.list_row.view.*
import java.util.*



class ListAdapter(private val context: Context,shopList: List<ShopList>) :RecyclerView.Adapter<ListAdapter.ViewHolder>(), ListTouchHelperCallback {
    private var listItems = mutableListOf<ShopList>()

    init {
       listItems.addAll(shopList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listView = LayoutInflater.from(context).inflate(R.layout.list_row, parent,false)
        return ViewHolder(listView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = listItems[position]
        var categorysrc = R.drawable.ic_launcher
        when(listItem.category){
            1 -> categorysrc= R.drawable.food
            2 -> categorysrc= R.drawable.electric
            3 -> categorysrc= R.drawable.book
            4 -> categorysrc= R.drawable.clothes
        }
        holder.tvName.text = listItem.name
        holder.tvPrice.text = listItem.price.toString()
        holder.tvCategory.setImageResource(categorysrc)
        holder.tvDetails.text = listItem.details
        holder.status.isChecked = listItem.status
        checkboxChoice(holder)
        holder.status.setOnClickListener{
            Thread {
                listItem.status = holder.status.isChecked
                AppDatabase.getInstance(context).listDao().updateList(listItem)
                (context as ScrollingActivity).runOnUiThread {
                    checkboxChoice(holder)
                    notifyItemChanged(listItem.hashCode())
                }
            }.start()
        }

        holder.btnDel.setOnClickListener{
            deleteItem(holder.adapterPosition)
        }
    }

    private fun checkboxChoice(holder: ViewHolder) {
        if (holder.status.isChecked) holder.row.setBackgroundColor(Color.parseColor("#b5b5b5"))
        else holder.row.setBackgroundColor(Color.parseColor("#acd7e5"))
    }

    private fun deleteItem(adapterPosition: Int) {
        val deleteItem = listItems[adapterPosition]
        Thread{
            AppDatabase.getInstance(context).listDao().deleteList(deleteItem)
            (context as ScrollingActivity).runOnUiThread {
                listItems.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }.start()
    }


    override fun getItemCount(): Int {
        return listItems.size
    }


    fun addList(list: ShopList){
        listItems.add(list)
        notifyItemInserted(listItems.lastIndex)
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvName = itemView.tvName!!
        var tvPrice = itemView.tvPrice!!
        var tvCategory = itemView.imageCategory!!
        var tvDetails = itemView.tvDetails!!
        var status = itemView.checkBox!!
        var row = itemView.listRow!!
        var btnDel = itemView.btnDel!!
    }

    override fun onDismissed(position: Int) {
        deleteItem(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Thread{
            val fromItem = listItems[fromPosition]
            val toItem = listItems[toPosition]
            val changer = fromItem.listID
            fromItem.listID = toItem.listID
            toItem.listID = changer

            AppDatabase.getInstance(context).listDao().updateList(fromItem)
            AppDatabase.getInstance(context).listDao().updateList(toItem)

            (context as ScrollingActivity).runOnUiThread {
                Collections.swap(listItems, fromPosition,toPosition)
                notifyItemMoved(fromPosition,toPosition)
            }
        }.start()
    }


}