package com.dtk.practicedtk.saito

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.dialog_saito.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.dtk.practicedtk.R
import java.io.InputStream

import android.support.v4.content.FileProvider

import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.dtk.practicedtk.MainActivity
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Camera
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select


/**
 * Created by saitetu on 2018/02/19.
 */

class Custom_saito_dialog : DialogFragment() {
    var imageview : ImageView? = null
    private var filePath: String? = null
    var cameraUri:Uri? = null
    var ID:Int? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        var Db = DBHelper.getInstance(activity)
        val builder = AlertDialog.Builder(activity)
        var args_day = arguments.getIntegerArrayList("day")
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val content = inflater.inflate(R.layout.dialog_saito, dialog_view)
        var list = Db.readableDatabase.select(DBHelper.tableName).whereArgs("(year == {year} and month == {month} and day == {day})" ,"year" to args_day[0].toString() , "month" to args_day[1].toString() , "day" to args_day[2].toString()).parseList<DbData>(Listparse())
        builder.setView(content)
        imageview = content.findViewById(R.id.image_view)
        if(list != null) {
            imageview!!.setImageURI(Uri.parse(list[0].component6().toString()))
        }
         content.findViewById<TextView>(R.id.year_text).text = args_day[0].toString()
         content.findViewById<TextView>(R.id.month_text).text = args_day[1].toString()
         content.findViewById<TextView>(R.id.day_text).text = args_day[2].toString()



        var spin = content.findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(activity,
                R.array.ramen, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter
        content.findViewById<Button>(R.id.camera_button).setOnClickListener {
            AlertDialog.Builder(activity).setPositiveButton("take photo",DialogInterface.OnClickListener { dialogInterface, i ->
                // 保存先のフォルダーを作成
                val cameraFolder = File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES), "IMG")
                cameraFolder.mkdirs()

                // 保存ファイル名
                val fileName = SimpleDateFormat(
                        "ddHHmmss", Locale.US).format(Date())
                filePath = String.format("%s/%s.jpg", cameraFolder.getPath(), fileName)
                Log.d("debug", "filePath:" + filePath)

                // capture画像のファイルパス
                val cameraFile = File(filePath)
                cameraUri = FileProvider.getUriForFile(
                        activity.applicationContext,
                        "saito.fileprovider",
                        cameraFile)

                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
                startActivityForResult(intent,1001)
            }).setNegativeButton("choose photo",DialogInterface.OnClickListener { dialogInterface, i ->
                var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.setType("image/jpeg")
                startActivityForResult(intent,1002)

            }).show()
        }

        builder.setMessage("設定")
                .setPositiveButton("ok",DialogInterface.OnClickListener { dialog, id ->
                    Db.use {
                        insert(DBHelper.tableName,*arrayOf("year" to args_day[0].toLong(),"month" to args_day[1].toLong(),"day" to args_day[2].toLong(),"ramen" to  content.findViewById<Spinner>(R.id.spinner).selectedItem.toString(),"uri" to cameraUri.toString()))
                    }
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
            Log.d("photo","take")
            Log.d("photo", data.toString())
            registerDatabase(filePath!!)
            imageview!!.setImageURI(cameraUri!!)
        }else if (requestCode == 1002) {
            if(data != null){
                var uri:Uri = data?.data
                imageview!!.setImageURI(uri)
                cameraUri = uri
            }
        }
    }

    private fun registerDatabase(file: String) {
        val contentValues = ContentValues()
        val contentResolver = activity.getContentResolver()
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        contentValues.put("_data", file)
        contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }
}