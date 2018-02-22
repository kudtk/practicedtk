package com.dtk.practicedtk.saito

import android.widget.ListAdapter
import org.jetbrains.anko.db.MapRowParser

/**
 * Created by saitetu on 2018/02/22.
 */
class Listparse : MapRowParser<DbData>{
    override fun parseRow(columns: Map<String, Any?>): DbData {
        return DbData(columns["id"] as Long? , columns["year"] as Long?,columns["month"] as Long?,columns["day"] as Long?,columns["ramen"] as String?,columns["uri"] as String?)
    }
}