package ru.hse.coursework.data_provider.controller

import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.hse.coursework.data_provider.api.PhotoControllerApi
import ru.hse.coursework.data_provider.service.PhotoService

@RestController
@RequestMapping(PHOTO_BASE_PATH_URL)
class PhotoController(
    private val photoService: PhotoService
) : PhotoControllerApi {

    @PutMapping(UPLOAD_PHOTO_URL, consumes = [MULTIPART_FORM_DATA_VALUE])
    override fun uploadFile(
        @RequestParam photo: MultipartFile,
        @RequestParam type: String,
        @RequestParam(required = false) photoUrl: String?
    ): String =
        photoService.uploadFile(photo, type, photoUrl)

    @GetMapping(DOWNLOAD_PHOTO_URL)
    override fun downloadFile(@RequestParam key: String): ResponseEntity<ByteArray> =
        photoService.downloadFile(key)
}
