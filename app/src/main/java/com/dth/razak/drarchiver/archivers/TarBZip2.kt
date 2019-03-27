package com.dth.razak.drarchiver.archivers

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class TarBZip2: Archiver() {

    override fun compress(name: String, vararg files: File) {
        try {
            val fileOut = FileOutputStream(name)
            val bZip2Out = BZip2CompressorOutputStream(fileOut)
            val out = TarArchiveOutputStream(bZip2Out)

            super.compressFiles(out, *files)

            out.close()
            bZip2Out.close()
            fileOut.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }

    override fun decompress(name: String, destination: File) {
        try {
            val fileInput = FileInputStream(name)
            val bZip2Input = BZip2CompressorInputStream(fileInput)
            val bZip2 = TarArchiveInputStream(bZip2Input)
            var entry = bZip2.nextTarEntry
            while (entry != null){
                if (entry.isDirectory){
                    entry = bZip2.nextTarEntry
                    continue
                }

                super.decompressEntry(entry, destination, bZip2)

                entry = bZip2.nextTarEntry
            }
            fileInput.close()
            bZip2Input.close()
            bZip2.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }
}