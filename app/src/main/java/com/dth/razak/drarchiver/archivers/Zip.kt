package com.dth.razak.drarchiver.archivers

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import org.apache.commons.compress.archivers.zip.ZipFile
import java.io.FileOutputStream
import java.io.File
import java.io.IOException


class Zip: Archiver() {

    override fun compress(name: String, vararg files: File) {
        try {
            val fileOut = FileOutputStream(name)
            val out = ZipArchiveOutputStream(fileOut)

            super.compressFiles(out, *files)

            out.close()
            fileOut.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }

    override fun decompress(name: String, destination: File) {
        val zip = ZipFile(File(name))
        for (entry in zip.entries){
            if (entry.isDirectory){
                continue
            }

            try {
                val inputStream = zip.getInputStream(entry)

                super.decompressEntry(entry, destination, inputStream)

                inputStream.close()
            }catch (ex: IOException){
                println(ex.message)
            }
        }
    }
}