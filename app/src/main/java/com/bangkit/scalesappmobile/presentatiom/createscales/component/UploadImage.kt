package com.bangkit.scalesappmobile.presentatiom.createscales.component

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@Composable
fun UploadImage(
    modifier: Modifier = Modifier,
    imageUri: Uri,
    onImageSelected: (Uri) -> Unit
) {

}

fun createMultipartBody(uri: Uri, multipartName: String): MultipartBody.Part {
//    val documentImage = Util.decodeFile(uri.path!!)
    val file = File(uri.path!!)
    val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
//    documentImage.compress(Bitmap.CompressFormat.JPEG, 100, os)
    os.close()
    val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name = multipartName, file.name, requestBody)
}