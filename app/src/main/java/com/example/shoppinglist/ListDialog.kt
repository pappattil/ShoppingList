package com.example.shoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.shoppinglist.data.ShopList
import kotlinx.android.synthetic.main.list_dialog.view.*

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class ListDialog: DialogFragment() {

    interface ListHandler {
        fun listCreated(list: ShopList)
    }

    private lateinit var listHandler: ListHandler


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ListHandler) {
            listHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the ListHandler interface."
            )
        }
    }

    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etCategory: Spinner
    private lateinit var etDetails: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

      val dialogBuilder = AlertDialog.Builder(requireContext())
          dialogBuilder.setTitle(getString(R.string.dialogAdd))
      val dialogView = requireActivity().layoutInflater.inflate(R.layout.list_dialog, null)
      val spinnerCategory = dialogView.spinnerCategory
      val categoryAdapter = ArrayAdapter.createFromResource(
          requireActivity(),
          R.array.category_array,
          android.R.layout.simple_spinner_item
      )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter

        dialogBuilder.setView(dialogView)

        etName = dialogView.etName
        etPrice = dialogView.etPrice
        etCategory = dialogView.spinnerCategory
        etDetails = dialogView.etDetails

    dialogBuilder.setPositiveButton("OK") { dialog, witch ->

        listHandler.listCreated(
                ShopList(
                        null,
                        etName.text.toString(),
                        try { etPrice.text.toString().toInt()}catch(e: Exception){0.toString().toInt()},
                        etCategory.selectedItemPosition,
                        etDetails.text.toString(),
                        status = false,
                        getString(R.string.currency)
                )
        )

    }

        dialogBuilder.setNegativeButton("Cancel") { dialog, witch ->
        }

        return dialogBuilder.create()
    }

}


