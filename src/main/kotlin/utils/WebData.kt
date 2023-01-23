package com.codepwn.x19viewer.utils

import com.codepwn.x19viewer.PluginMain
import com.codepwn.x19viewer.PluginMain.dataFolder
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipInputStream


object WebData {

    fun unzipData() {
        val mainPath = File("${dataFolder}/webData/")
        val mainZipPath = File("${dataFolder}/webData/res.zip")
        if (!mainPath.exists()) {
            mainPath.mkdir()

            val resZip = PluginMain.javaClass.getResourceAsStream("/assets/WebData/res.zip")

            val mainPathZipStream = FileOutputStream(mainZipPath)
            resZip?.copyTo(mainPathZipStream)
            resZip?.close()
            mainPathZipStream.close()

            //logger.info(dataFolder.toString())
            val mainZip = ZipInputStream(FileInputStream(mainZipPath))
            var mainZipEntry = mainZip.nextEntry
            while (mainZipEntry != null) {
                val current = File("$mainPath/${mainZipEntry.name}")
                if (mainZipEntry.isDirectory) {
                    current.mkdirs()
                } else {
                    current.parentFile?.mkdirs()
                    mainZip.buffered().copyTo(current.outputStream())
                    mainZipEntry = mainZip.nextEntry
                }
            }
            mainZip.close()
            mainZipPath.delete()
        }
    }
}