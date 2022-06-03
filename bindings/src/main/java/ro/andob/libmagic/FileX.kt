package ro.andob.libmagic

import java.io.File

fun File.getMimeType() : String = LibMagic.getFileMimeType(this)
