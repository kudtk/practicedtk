package com.dtk.practicedtk

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_saito.*
import com.dtk.practicedtk.R.id.spinner
import android.widget.ArrayAdapter
import android.widget.Spinner


/**
 * Created by saitetu on 2018/02/19.
 */

class Custom_saito_dialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(activity)
        var args_day = arguments.getIntegerArrayList("day")
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val content = inflater.inflate(R.layout.dialog_saito, dialog_view)

        builder.setView(content)
        content.findViewById<TextView>(R.id.year_text).text = args_day[0].toString()
        content.findViewById<TextView>(R.id.month_text).text = args_day[1].toString()
        content.findViewById<TextView>(R.id.day_text).text = args_day[2].toString()
        var spin = content.findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(activity,
                R.array.ramen, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter
        builder.setMessage("設定")
                .setPositiveButton("ok",DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
                .setNegativeButton("閉じる", DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        // Create the AlertDialog object and return it
        return builder.create()
    }
}