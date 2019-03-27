package com.dth.razak.drarchiver.archivers

import org.apache.commons.compress.archivers.jar.JarArchiveInputStream
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class Jar: Archiver() {

    override fun compress(name: String, vararg files: File) {
        try {
            val fileOut = FileOutputStream(name)
            val out = JarArchiveOutputStream(fileOut)

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
            val jar = JarArchiveInputStream(fileInput)
            var entry = jar.nextJarEntry
            while (entry != null){
                if (entry.isDirectory){
                    entry = jar.nextJarEntry
                    continue
                }

                super.decompressEntry(entry, destination, jar)

                entry = jar.nextJarEntry
            }
            jar.close()
            fileInput.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }
}