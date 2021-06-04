package com.automatedcontacttracing.act.idcapture

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import com.automatedcontacttracing.act.R
import com.automatedcontacttracing.act.base.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_id_capture.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws


class IdCaptureFragment : BaseFragment() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val RESULT_LOAD_IMG = 231
    private var currentPhotoPath: String? = null
    private var tempPhotoPath: String? = null
    private var isFromGallery: Boolean = false

    override fun getLayoutResId(): Int = R.layout.fragment_id_capture
    override fun shouldShowBackButton(): Boolean = true

    override fun onResume() {
        super.onResume()
        if (currentPhotoPath != null && !isFromGallery) {
            displayImage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clCaptureImage.setOnClickListener {
            dispatchTakePictureIntent()
        }

        clUploadImage.setOnClickListener {
            getImageFromGallery()
            isFromGallery = true
        }

        btnNext.setOnClickListener {
            if (currentPhotoPath == null) {
                showError("Please select/capture a photo of your ID!")
                return@setOnClickListener
            }
            mSharedPrefHelper.setAbsoluteImagePath(currentPhotoPath!!)
            val action =
                IdCaptureFragmentDirections.actionIdCaptureFragmentToBasicInfoConfirmationFragment()
            it.findNavController().navigate(action)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                try {
                    currentPhotoPath = tempPhotoPath
                    displayImage()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            if (requestCode == RESULT_LOAD_IMG) {
                try {
                    val imageUri: Uri? = data?.data
                    val imageStream: InputStream? = imageUri?.let {
                        requireActivity().contentResolver.openInputStream(
                            it
                        )
                    }
                    currentPhotoPath = imageUri?.path
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    imgIdPlaceHolder.setImageBitmap(selectedImage)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun displayImage() {
        val imageBitmap = MediaStore.Images.Media.getBitmap(
            requireActivity().contentResolver,
            Uri.fromFile(File(currentPhotoPath))
        )
        imgIdPlaceHolder.setImageBitmap(imageBitmap)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            tempPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            activity?.packageManager?.let { packageManager ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.automatedcontacttracing.act.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    private fun getImageFromGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }
}