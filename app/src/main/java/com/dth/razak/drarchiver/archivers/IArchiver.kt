package com.dth.razak.drarchiver.archivers

import java.io.File

interface IArchiver {
    fun compress(name: String, vararg  files: File)

    fun decompress(name: String, destination: File)
}