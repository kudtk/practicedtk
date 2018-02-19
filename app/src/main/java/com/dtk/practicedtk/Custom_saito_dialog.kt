package com.dtk.practicedtk

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import com.dtk.practicedtk.R.id.image
import kotlinx.android.synthetic.main.dialog_saito.*
import com.dtk.practicedtk.R.id.spinner
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import android.graphics.Bitmap




/**
 * Created by saitetu on 2018/02/19.
 */

class Custom_saito_dialog : DialogFragment() {
    var imageview : ImageView? = null
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
        imageview = content.findViewById(R.id.image_view)
        var spin = content.findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(activity,
                R.array.ramen, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter
        content.findViewById<Button>(R.id.camera_button).setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,1001)
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("photo","読まれた")
        if (requestCode == 1001) {
            if (data!!.getExtras() != null) {
                Log.d("photo", data.toString())
                imageview!!.setImageBitmap(data.getExtras().get("data") as Bitmap)
            }
        }
    }
}