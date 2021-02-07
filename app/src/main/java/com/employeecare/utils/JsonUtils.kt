package com.employeecare.utils

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets

object JsonUtils {

    private val mGson = getGsonObject()

    private fun getGsonObject(): Gson {
        return Gson()
    }

    fun jsonify(`object`: Any?): String? {
        return mGson.toJson(`object`)
    }

    fun objectify(jsonString: String?, T: Class<*>?): Any {
        return mGson.fromJson(jsonString, T)
    }

    fun <T> arrayObjectify(jsonString: String, listType: Type): Any {
        return mGson.fromJson(jsonString, listType)
    }

    fun getStringFromJsonAssetFile(context: Context, assetFileName: String): String? {
        return try {
            val inputStream = context.assets.open(assetFileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            ""
        }
    }
}