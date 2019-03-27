package com.dth.razak.drarchiver.archivers

import org.apache.commons.compress.archivers.ArchiveEntry
import org.apache.commons.compress.archivers.ArchiveOutputStream
import org.apache.commons.compress.utils.IOUtils
import java.io.*


abstract class Archiver: IArchiver {

    protected fun decompressEntry(entry: ArchiveEntry, destination: File, inputStream: InputStream){
        val currFile = File(destination, entry.name)
        val parent = currFile.parentFile
        if (!parent.exists()){
            parent.mkdirs()
        }

        val out = FileOutputStream(currFile)

        try {
            IOUtils.copy(inputStream, out, 1024)
        }catch (ex: IOException){
            println(ex.message)
        }

        out.close()
    }

    protected fun compressFiles(out: ArchiveOutputStream, vararg files: File){
        for (file in files){
            addToArchiveCompression(out, file)
        }
    }

    private fun addToArchiveCompression(out: ArchiveOutputStream, file: File, dir: String = ""){
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