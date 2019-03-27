package com.dth.razak.drarchiver.archivers

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.File
import java.io.IOException


class TarGz: Archiver() {

    override fun compress(name: String, vararg files: File) {
        try {
            val fileOut = FileOutputStream(name)
            val gzipOut = GzipCompressorOutputStream(fileOut)
            val out = TarArchiveOutputStream(gzipOut)

            super.compressFiles(out, *files)

            out.close()
            gzipOut.close()
            fileOut.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }

    override fun decompress(name: String, destination: File) {
        try {
            val fileInput = FileInputStream(name)
            val gzipInput = GzipCompressorInputStream(fileInput)
            val tar = TarArchiveInputStream(gzipInput)
            var entry = tar.nextTarEntry
            while (entry != null){
                if (entry.isDirectory){
                    entry = tar.nextTarEntry
                    continue
                }

                super.decompressEntry(entry, destination, tar)

                entry = tar.nextTarEntry
            }
            fileInput.close()
            gzipInput.close()
            tar.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }
}