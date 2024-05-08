package com.bangkit.scalesappmobile.presentatiom.createscales

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.crop
import com.mr0xf00.easycrop.rememberImageCropper
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@Destination
@Composable
fun CreateScalesScreen(
    navigator: CreateScalesNavigator,
    viewModel: CreateScalesViewModel = hiltViewModel(),
) {
    fun createMultipartBody(uri: Uri, multipartName: String): MultipartBody.Part {
        val documentImage = BitmapFactory.decodeFile(uri.path!!)
        val file = File(uri.path!!)
        val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
        documentImage.compress(Bitmap.CompressFormat.PNG, 100, os)
        os.close()
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name = multipartName, file.name, requestBody)
    }

    val name = viewModel.scalesName.value
    val brand = viewModel.scalesBrand.value
    val kindType = viewModel.scalesKindType.value
    val serialNumber = viewModel.scalesSerialNumber.value
    val rangeCapacity = viewModel.scalesRangeCapacity.value
    val unit = viewModel.scalesUnit.value

    val context = LocalContext.current

    val imageCropper = rememberImageCropper()
    val scope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<File?>(null) }
    var compressedImageUri by remember { mutableStateOf<Uri?>(null) }

    var cameraPermissionState by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                cameraPermissionState = granted
            }
        )

    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                if (imageUri != null) {
                    createMultipartBody(imageUri!!.toUri(), multipartName = "imageCover")
                    compressedImageUri = imageUri!!.toUri()

                    scope.launch {
                        when (imageCropper.crop(uri = imageUri!!.toUri(), context = context)) {
                            CropError.LoadingError -> {
                                Toast.makeText(
                                    context,
                                    "CropError.LoadingError",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            CropError.SavingError -> {
                                Toast.makeText(context, "CropError.SavingError", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            CropResult.Cancelled -> {
                                Toast.makeText(context, "CropResult.Cancelled", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is CropResult.Success -> {
                                viewModel.createNewScales
                            }
                        }
                    }
                }
            }

        })


}