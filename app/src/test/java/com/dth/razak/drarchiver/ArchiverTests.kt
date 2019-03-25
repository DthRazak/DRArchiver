package com.dth.razak.drarchiver

import com.dth.razak.drarchiver.archivers.SevenZ
import org.junit.Test
import org.junit.Assert.*
import java.io.File


val path = File(".").canonicalPath!! + "/src/test/java/com/dth/razak/drarchiver/"

class SevenZTest {

    private val sz: SevenZ = SevenZ()

    @Test
    fun decompressTest(){
        sz.decompress(path + "archives/TwoTxtFiles.7z", File(path + "files/TwoTxtFiles"))
        val folder = File(path + "files/TwoTxtFiles")
        assertTrue(folder.exists())
        assertEquals(listOf("a.txt", "b.txt"), folder.list().map { it.toString() })
    }

    @Test
    fun compressTest(){
        sz.compress(path + "archives/java_coffee.7z", File(path + "files/java_coffee.ico"))
        val archive = File(path + "archives/java_coffee.7z")
        assertTrue(archive.exists())
    }
}