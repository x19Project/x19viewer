package com.codepwn.x19viewer.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object PluginUtils {
    fun getRandomString(length: Int): String {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val sb = StringBuffer()
        for (i in 0 until length) {
            val number: Int = random.nextInt(62)
            sb.append(str[number])
        }
        return sb.toString()
    }

    fun copyFile(resFile: File, targetFile: File) {
        val fis = FileInputStream(resFile)
        val fos = FileOutputStream(targetFile)
        fis.copyTo(fos)
        fis.close()
        fos.close()
    }

    fun ts2d(timestampString: String): String {
        val formats = "yyyy-MM-dd HH:mm:ss"
        val timestamp = timestampString.toLong() * 1000
        return SimpleDateFormat(formats, Locale.CHINA).format(Date(timestamp))
    }
}