package com.dth.razak.drarchiver.archivers

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import java.io.File
import java.io.IOException
import org.apache.commons.compress.archivers.zip.ZipFile
import org.apache.commons.compress.utils.IOUtils
import java.io.FileInputStream
import java.io.FileOutputStream


class Zip: IArchiver {

    override fun compress(name: String, vararg files: File) {
        try {
            val fileOut = FileOutputStream(name)
            val out = ZipArchiveOutputStream(fileOut)
            for (file in files){
                addToArchiveCompression(out, file)
            }
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

            val currFile = File(destination, entry.name)
            val parent = currFile.parentFile
            if (!parent.exists()){
                parent.mkdirs()
            }

            val out = FileOutputStream(currFile)

            try {
                IOUtils.copy(zip.getInputStream(entry), out, 1024)
            }catch (ex: IOException){
                println(ex.message)
            }

            out.close()
        }
    }

    private fun addToArchiveCompression(out: ZipArchiveOutputStream, file: File, dir: String = ""){
        val name = dir + File.separator + file.name
        if (file.isFile){
            val entry = out.createArchiveEntry(file, name)
            out.putArchiveEntry(entry)
            val inStream = FileInputStream(file)

            IOUtils.copy(inStream, out, 1024)

            inStream.close()
            out.closeArchiveEntry()
        } else if (file.isDirectory){
            val children = file.listFiles()
            if (children != null){
                for (child in children){
                    addToArchiveCompression(out, child, name)
                }
            }
        } else {
            println("${file.name} is not supported!")
        }
    }
}