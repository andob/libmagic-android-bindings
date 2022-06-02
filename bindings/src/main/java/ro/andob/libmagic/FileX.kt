package ro.andob.libmagic

import java.io.File

fun File.getMimeType(errorHandler : LibMagicErrorHandler = LibMagicErrorHandler.getDefault()) : String = LibMagic.getFileMimeType(this, errorHandler)
