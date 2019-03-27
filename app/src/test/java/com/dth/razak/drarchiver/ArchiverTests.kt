package com.dth.razak.drarchiver

import com.dth.razak.drarchiver.archivers.*
import org.junit.AfterClass
import org.junit.Test
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import java.io.File


val path = File(".").canonicalPath!! + "/src/test/java/com/dth/razak/drarchiver/"

fun deleteRecursively(file: File){
    if (file.isDirectory){
        val entries = file.listFiles()
        if (entries != null){
            for (entry in entries){
                deleteRecursively(entry)
            }
        }
    }
    if (!file.delete()){
        println("Failed to delete $file")
    }
}


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SevenZTest {

    private val sz: SevenZ = SevenZ()

    companion object {
        @AfterClass @JvmStatic
        fun cleanUp(){
            deleteRecursively(File(path + "files/Decompressed"))
            deleteRecursively(File(path + "archives/compressed.7z"))
        }
    }

    @Test
    fun compressTest(){
        sz.compress(path + "archives/compressed.7z",
            File(path + "files/TwoTxtFiles"), File(path + "files/java_coffee.ico"))
        val archive = File(path + "archives/compressed.7z")
        assertTrue(archive.exists())
    }

    @Test
    fun decompressTest(){
        sz.decompress(path + "archives/compressed.7z", File(path + "files/Decompressed"))
        val folder = File(path + "files/Decompressed")
        assertTrue(folder.exists())
        assertEquals(listOf("TwoTxtFiles", "java_coffee.ico"), folder.list().map { it.toString() })
    }
}


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ZipTest{

    private val zip = Zip()

    companion object {
        @AfterClass @JvmStatic
        fun cleanUp(){
            deleteRecursively(File(path + "files/Decompressed"))
            deleteRecursively(File(path + "archives/compressed.zip"))
        }
    }

    @Test
    fun compressTest(){
        zip.compress(path + "archives/compressed.zip",
            File(path + "files/TwoTxtFiles"), File(path + "files/java_coffee.ico"))
        val archive = File(path + "archives/compressed.zip")
        assertTrue(archive.exists())
    }

    @Test
    fun decompressTest(){
        zip.decompress(path + "archives/compressed.zip", File(path + "files/Decompressed"))
        val folder = File(path + "files/Decompressed")
        assertTrue(folder.exists())
        assertEquals(listOf("TwoTxtFiles", "java_coffee.ico"), folder.list().map { it.toString() })
    }
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class JarTest{

    private val jar = Jar()

    companion object {
        @AfterClass @JvmStatic
        fun cleanUp(){
            deleteRecursively(File(path + "files/Decompressed"))
            deleteRecursively(File(path + "archives/compressed.jar"))
        }
    }

    @Test
    fun compressTest(){
        jar.compress(path + "archives/compressed.jar",
            File(path + "files/TwoTxtFiles"), File(path + "files/java_coffee.ico"))
        val archive = File(path + "archives/compressed.jar")
        assertTrue(archive.exists())
    }

    @Test
    fun decompressTest(){
        jar.decompress(path + "archives/compressed.jar", File(path + "files/Decompressed"))
        val folder = File(path + "files/Decompressed")
        assertTrue(folder.exists())
        assertEquals(listOf("TwoTxtFiles", "java_coffee.ico"), folder.list().map { it.toString() })
    }
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TarTest {

    private val tar: Tar = Tar()

    companion object {
        @AfterClass @JvmStatic
        fun cleanUp(){
            deleteRecursively(File(path + "files/Decompressed"))
            deleteRecursively(File(path + "archives/compressed.tar"))
        }
    }

    @Test
    fun compressTest(){
        tar.compress(path + "archives/compressed.tar",
            File(path + "files/TwoTxtFiles"), File(path + "files/java_coffee.ico"))
        val archive = File(path + "archives/compressed.tar")
        assertTrue(archive.exists())
    }

    @Test
    fun decompressTest(){
        tar.decompress(path + "archives/compressed.tar", File(path + "files/Decompressed"))
        val folder = File(path + "files/Decompressed")
        assertTrue(folder.exists())
        assertEquals(listOf("TwoTxtFiles", "java_coffee.ico"), folder.list().map { it.toString() })
    }
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TarGzTest {

    private val tarGz: TarGz = TarGz()

    companion object {
        @AfterClass @JvmStatic
        fun cleanUp(){
            deleteRecursively(File(path + "files/Decompressed"))
            deleteRecursively(File(path + "archives/compressed.tar.gz"))
        }
    }

    @Test
    fun compressTest(){
        tarGz.compress(path + "archives/compressed.tar.gz",
            File(path + "files/TwoTxtFiles"), File(path + "files/java_coffee.ico"))
        val archive = File(path + "archives/compressed.tar.gz")
        assertTrue(archive.exists())
    }

    @Test
    fun decompressTest(){
        tarGz.decompress(path + "archives/compressed.tar.gz", File(path + "files/Decompressed"))
        val folder = File(path + "files/Decompressed")
        assertTrue(folder.exists())
        assertEquals(listOf("TwoTxtFiles", "java_coffee.ico"), folder.list().map { it.toString() })
    }
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TarBZip2Test {

    private val tarGz: TarBZip2 = TarBZip2()

    companion object {
        @AfterClass @JvmStatic
        fun cleanUp(){
            deleteRecursively(File(path + "files/Decompressed"))
            deleteRecursively(File(path + "archives/compressed.tar.bz2"))
        }
    }

    @Test
    fun compressTest(){
        tarGz.compress(path + "archives/compressed.tar.bz2",
            File(path + "files/TwoTxtFiles"), File(path + "files/java_coffee.ico"))
        val archive = File(path + "archives/compressed.tar.bz2")
        assertTrue(archive.exists())
    }

    @Test
    fun decompressTest(){
        tarGz.decompress(path + "archives/compressed.tar.bz2", File(path + "files/Decompressed"))
        val folder = File(path + "files/Decompressed")
        assertTrue(folder.exists())
        assertEquals(listOf("TwoTxtFiles", "java_coffee.ico"), folder.list().map { it.toString() })
    }
}