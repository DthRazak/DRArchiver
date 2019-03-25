package com.dth.razak.drarchiver.archivers

import java.io.File
import java.io.IOException
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry
import org.apache.commons.compress.archivers.sevenz.SevenZFile
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile
import java.io.FileInputStream
import java.io.FileOutputStream


class SevenZ: IArchiver {
    override fun compress(name: String, vararg files: File) {
        try {
            val out = SevenZOutputFile(File(name))
            for (file in files){
                addToArchiveCompression(out, file, "")
            }
            out.close()
        }catch (ex: IOException){
            println(ex.message)
        }
    }

    override fun decompress(name: String, destination: File) {
        val sevenZFile = SevenZFile(File(name))
        var entry: SevenZArchiveEntry? = sevenZFile.nextEntry
        while (entry != null){
            if (entry.isDirectory){
                entry = sevenZFile.nextEntry
                continue
            }
            val currFile = File(destination, entry.name)
            val parent = currFile.parentFile
            if (!parent.exists()){
                parent.mkdirs()
            }
            val out = FileOutputStream(currFile)
            val content = ByteArray(entry.size.toInt())
            sevenZFile.read(content, 0, content.size)
            out.write(content)
            out.close()
            entry = sevenZFile.nextEntry
        }
    }

    private fun addToArchiveCompression(out: SevenZOutputFile, file: File, dir: String){
        val name = dir + File.separator + file.name
        if (file.isFile){
            val entry: SevenZArchiveEntry? = out.createArchiveEntry(file, name)
            out.putArchiveEntry(entry)

            val inStream = FileInputStream(file)
            val content = ByteArray(1024)
            var count = inStream.read(content)
            while (count > 0){
                out.write(content, 0, count)
                count = inStream.read(content)
            }
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
