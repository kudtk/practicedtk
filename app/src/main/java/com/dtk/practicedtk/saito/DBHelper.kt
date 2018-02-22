package com.dtk.practicedtk.saito

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

/**
 * Created by saitetu on 2018/02/22.
 */
class DBHelper (context: Context): ManagedSQLiteOpenHelper(context,"ramen.db",null,1){
    companion object {
        val tableName = "ramen"
        private  var instance :DBHelper? = null;

        fun getInstance(context: Context):DBHelper{
            return instance ?: DBHelper(context.applicationContext)!!
        }
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.run { createTable(tableName,ifNotExists = true,
                columns = *arrayOf("id" to INTEGER,"year" to INTEGER,"month" to INTEGER,"day" to INTEGER,"ramen" to INTEGER,"uri" to TEXT))
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}