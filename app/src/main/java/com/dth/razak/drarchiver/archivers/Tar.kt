package com.dth.razak.drarchiver.archivers

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


open class Tar: Archiver() {

    override fun compress(name: String, vararg files: File) {
        try {
            val fileOut = FileOutputStream(name)
            val out = TarArchiveOutputStream(fileOut)

            super.compressFiles(out, *files)

            out.close()
            fileOut.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }

    override fun decompress(name: String, destination: File) {
        try {
            val fileInput = FileInputStream(name)
            val tar = TarArchiveInputStream(fileInput)
            var entry = tar.nextTarEntry
            while (entry != null) {
                if (entry.isDirectory) {
                    entry = tar.nextTarEntry
                    continue
                }

                super.decompressEntry(entry, destination, tar)

                entry = tar.nextTarEntry
            }
            tar.close()
            fileInput.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }
}