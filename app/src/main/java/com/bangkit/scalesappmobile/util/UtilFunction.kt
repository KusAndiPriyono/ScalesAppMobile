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
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
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

fun createMultipartBody(uri: Uri, multipartName: String): MultipartBody.Part {
    val documentImage = BitmapFactory.decodeFile(uri.path!!)
    val file = File(uri.path!!)
    val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
    documentImage.compress(Bitmap.CompressFormat.PNG, 100, os)
    os.close()
    val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name = multipartName, file.name, requestBody)
}

fun createImageFile(context: Context): File? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "PNG_${timeStamp}_",
        ".png",
        storageDir
    )
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
