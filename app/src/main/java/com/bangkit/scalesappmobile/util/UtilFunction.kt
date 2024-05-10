package com.bangkit.scalesappmobile.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bangkit.scalesappmobile.data.remote.scales.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T,
): Resource<T> {
    return withContext(dispatcher) {
        try {
            Timber.e("Success")
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            when (throwable) {
                is IOException -> {
                    Timber.e("IO Exception $throwable")
                    Resource.Error(
                        message = "Tolong cek koneksi internet anda dan coba lagi",
                        throwable = throwable
                    )
                }

                is HttpException -> {
                    val stringErrorBody = errorBodyAsString(throwable)
                    Timber.e("stringErrorBody $stringErrorBody")
                    if (stringErrorBody != null) {
                        val errorResponse = convertStringErrorResponseToJsonObject(stringErrorBody)
                        Timber.e("errorResponse $errorResponse")
                        Resource.Error(
                            message = errorResponse?.message,
                            throwable = throwable
                        )
                    } else {
                        Resource.Error(
                            message = "Terjadi kegagalan yang tidak diketahui, coba lagi nanti",
                            throwable = throwable
                        )
                    }
                }

                else -> {
                    Timber.e("In else statement $throwable")
                    Resource.Error(
                        message = "Terjadi kegagalan yang tidak diketahui, coba lagi nanti",
                        throwable = throwable
                    )
                }
            }
        }
    }
}

fun errorBodyAsString(throwable: HttpException): String? {
    val reader = throwable.response()?.errorBody()?.charStream()
    return reader?.use { it.readText() }
}

private fun convertStringErrorResponseToJsonObject(jsonString: String): ErrorResponse? {
    val gson = Gson()
    return gson.fromJson(jsonString, ErrorResponse::class.java)
}

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

//fun createMultipartBody(context: Context, uri: Uri, multipartName: String): MultipartBody.Part {
//    val documentImage = context.imageUriToImageBitmap(uri)
//    val file = File(uri.path!!)
//    val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
//    documentImage.compress(Bitmap.CompressFormat.JPEG, 100, os)
//    os.close()
//    val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
//    return MultipartBody.Part.createFormData(name = multipartName, file.name, requestBody)
//}

fun createMultipartBody(context: Context, uri: Uri, multipartName: String): MultipartBody.Part {
    val documentImage = context.imageUriToImageBitmap(uri)
    val compressedImage = compressImage(documentImage)
    val bitmapPath = saveImage(context, compressedImage)
    val filename = File(bitmapPath?.path!!)
//    val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
//    documentImage.compress(Bitmap.CompressFormat.JPEG, 100, os)
//    os.close()
//    val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
//    return MultipartBody.Part.createFormData(name = multipartName, file.name, requestBody)
    // Create the request body from the saved image file
    val requestBody = filename.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(multipartName, filename.name, requestBody)
}

fun compressImage(bitmap: Bitmap): Bitmap {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
    val byteArray = outputStream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

@Throws(IOException::class)
fun saveImage(context: Context, bitmap: Bitmap): Uri? {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File.createTempFile(
        "PNG_${timestamp}_",
        ".png",
        storageDir
    )

    return try {
        imageFile.outputStream().use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
        }
        Uri.fromFile(imageFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


lateinit var currentPhotoPath: String
fun createImageFile(context: Context): File? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "PNG_${timeStamp}_",
        ".png",
        storageDir
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = absolutePath
    }
}

@Suppress("DEPRECATION")
fun Context.imageUriToImageBitmap(uri: Uri): Bitmap {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images
            .Media.getBitmap(contentResolver, uri)
    } else {
        val source = ImageDecoder
            .createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}
