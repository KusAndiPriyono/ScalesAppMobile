package com.bangkit.scalesappmobile.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bangkit.scalesappmobile.R
import com.bangkit.scalesappmobile.data.remote.scales.ErrorResponse
import com.bangkit.scalesappmobile.domain.model.AllForm
import com.google.gson.Gson
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.geom.Rectangle
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
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
    val documentImage = BitmapFactory.decodeFile(uri.path)
    val file = File(documentImage.toString(), "image.png")
    val os: OutputStream = FileOutputStream(file)
    documentImage.compress(Bitmap.CompressFormat.JPEG, 100, os)
    os.close()
    val requestBody = file.asRequestBody("imageCover/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name = multipartName, file.name, requestBody)
}

fun compressImage(bitmap: Bitmap): Bitmap {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
    val byteArray = outputStream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

@Throws(IOException::class)
fun saveImage(context: Context, bitmap: Bitmap): Uri? {
    val imageFile = createImageFile(context) ?: return null

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


fun createImageFile(context: Context): File? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileName = "scales-${timeStamp}-cover_"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(fileName, ".png", storageDir)
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

// Adjust the input format to match the given date string format
fun formatDate(date: String): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX yyyy", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return try {
        val parsedDate = inputFormat.parse(date)
        outputFormat.format(parsedDate!!)
    } catch (e: Exception) {
        e.printStackTrace()
        "Invalid Date"
    }
}

fun createPdfFile(context: Context, data: AllForm): File? {
    val directoryPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath ?: return null
    val file = File("$directoryPath/scales.pdf")

    return try {
        val pdfWriter = PdfWriter(file)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument, PageSize.A4)
        val margin = 36f
        document.setMargins(margin, margin, margin, margin)

        val font = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE)

        // Header
        val headerImageId = R.drawable.logo_myr
        val bitmap = BitmapFactory.decodeResource(context.resources, headerImageId)
            ?: throw FileNotFoundException("Drawable resource not found: $headerImageId")
        val headerImage = Image(ImageDataFactory.create(bitmapToByteArray(bitmap)))
        headerImage.setWidth(50f)
        headerImage.setHorizontalAlignment(HorizontalAlignment.CENTER)

        val headerTable = Table(floatArrayOf(100f, 285f, 190f))
        headerTable.addCell(Cell().add(headerImage).setBorder(SolidBorder(ColorConstants.GRAY, 1f)))
        headerTable.addCell(
            Cell().add(
                Paragraph("PT. MAYORA INDAH, Tbk\nJatake-Tangerang")
                    .setFontSize(15f)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.RED)
            ).setBorder(SolidBorder(ColorConstants.GRAY, 1f))
        )
        headerTable.addCell(
            Cell().add(
                Paragraph("SERTIFIKAT KALIBRASI")
                    .setFontSize(10f)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE)
                    .setBold()
            ).setBorder(SolidBorder(ColorConstants.GRAY, 1f))
        )
        document.add(headerTable)

        // Department
        document.add(
            Paragraph("DEPARTEMENT TEKNIK\nCALIBRATION")
                .setFontSize(15f)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.BLUE)
                .setBold()
        )

        // Identitas Alat
        document.add(
            Paragraph("IDENTITAS ALAT")
                .setFontSize(10f)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontColor(ColorConstants.BLACK)
                .setBold()
        )

        val dataIdentitasAlat = Table(floatArrayOf(200f, 350f))
        with(dataIdentitasAlat) {
            addCell(Cell().add(Paragraph("Nomor Alat")))
            addCell(Cell().add(Paragraph(data.scale.measuringEquipmentIdNumber)))
            addCell(Cell().add(Paragraph("Nama Alat")))
            addCell(Cell().add(Paragraph(data.scale.name)))
            addCell(Cell().add(Paragraph("Merk Pabrik / Tipe")))
            addCell(Cell().add(Paragraph("${data.scale.brand} / ${data.scale.kindType}")))
            addCell(Cell().add(Paragraph("Nomor Seri")))
            addCell(Cell().add(Paragraph(data.scale.serialNumber)))
            addCell(Cell().add(Paragraph("Kapasitas")))
            addCell(Cell().add(Paragraph("${data.scale.rangeCapacity}${data.scale.unit}")))
        }
        document.add(dataIdentitasAlat)

        // Identitas Pemilik
        document.add(
            Paragraph("IDENTITAS PEMILIK")
                .setFontSize(10f)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontColor(ColorConstants.BLACK)
                .setBold()
        )

        val dataIdentitasPemilik = Table(floatArrayOf(200f, 350f))
        with(dataIdentitasPemilik) {
            addCell(Cell().add(Paragraph("Nama Pemilik")))
            addCell(Cell().add(Paragraph(data.scale.parentMachineOfEquipment)))
            addCell(Cell().add(Paragraph("Metoda Kalibrasi")))
            addCell(Cell().add(Paragraph(data.calibrationMethod)))
            addCell(Cell().add(Paragraph("Acuan Standard")))
            addCell(Cell().add(Paragraph(data.reference)))
            addCell(Cell().add(Paragraph("Standar Kalibrasi")))
            addCell(Cell().add(Paragraph(data.standardCalibration)))
            addCell(Cell().add(Paragraph("Tanggal Kalibrasi")))
            addCell(Cell().add(Paragraph(formatDate(data.createdAt.toString()))))
            addCell(Cell().add(Paragraph("Tempat Kalibrasi")))
            addCell(Cell().add(Paragraph(data.scale.location)))
            addCell(Cell().add(Paragraph("Suhu")))
            addCell(Cell().add(Paragraph("${data.suhu}°C")))
            addCell(Cell().add(Paragraph("Keterangan Kalibrasi")))
            addCell(Cell().add(Paragraph(data.resultCalibration)))
            addCell(Cell().add(Paragraph("Berlaku Sampai")))
            addCell(Cell().add(Paragraph(formatDate(data.validUntil.toString()))))
        }
        document.add(dataIdentitasPemilik)

        // Hasil Kalibrasi
        document.add(
            Paragraph("HASIL KALIBRASI")
                .setFontSize(10f)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontColor(ColorConstants.BLACK)
                .setBold()
        )

        val dataHasilKalibrasi = Table(floatArrayOf(200f, 350f))
        with(dataHasilKalibrasi) {
            addCell(Cell().add(Paragraph("Posisi anak timbangan di tengah")))
            addCell(Cell().add(Paragraph(data.readingCenter.toString())))
            addCell(Cell().add(Paragraph("Posisi anak timbangan di depan")))
            addCell(Cell().add(Paragraph(data.readingFront.toString())))
            addCell(Cell().add(Paragraph("Posisi anak timbangan di belakang")))
            addCell(Cell().add(Paragraph(data.readingBack.toString())))
            addCell(Cell().add(Paragraph("Posisi anak timbangan di kiri")))
            addCell(Cell().add(Paragraph(data.readingLeft.toString())))
            addCell(Cell().add(Paragraph("Posisi anak timbangan di kanan")))
            addCell(Cell().add(Paragraph(data.readingRight.toString())))
            addCell(Cell().add(Paragraph("Total")))
            addCell(Cell().add(Paragraph(data.maxTotalReading.toString()).setFont(font)))
        }
        document.add(dataHasilKalibrasi)

        // Footer
        document.add(
            Paragraph("Tangerang, ${formatDate(Date().toString())}")
                .setFontSize(12f)
                .setTextAlignment(TextAlignment.LEFT)
        )

        // Close document
        document.close()
        pdfDocument.close()

        file
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

