package ru.hse.coursework.data_provider.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import ru.hse.coursework.data_provider.controller.DOWNLOAD_PHOTO_URL
import ru.hse.coursework.data_provider.controller.UPLOAD_PHOTO_URL

@Tag(
    name = "Контроллер загрузки фотографий",
    description = "Позволяет загружать и выгружать фотографии пользователей и маршрутов"
)
@ApiResponses(
    value = [
        ApiResponse(responseCode = "200", description = "Успешный запрос"),
        ApiResponse(responseCode = "201", description = "Файл успешно загружен"),
        ApiResponse(responseCode = "400", description = "Некорректный запрос",
            content = [Content(mediaType = "text/plain", schema = Schema(implementation = String::class))]),
        ApiResponse(responseCode = "404", description = "Файл не найден",
            content = [Content(mediaType = "text/plain", schema = Schema(implementation = String::class))]),
        ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = [Content(mediaType = "text/plain", schema = Schema(implementation = String::class))])
    ]
)
interface PhotoControllerApi {

    @Operation(
        summary = "Загрузить фотографию",
        description = "Загружает фотографию пользователя или маршрута в хранилище S3. Возвращает URL к загруженному файлу."
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        value = [UPLOAD_PHOTO_URL]
    )
    fun uploadFile(
        @Parameter(description = "Фотография в формате multipart/form-data")
        @RequestParam("photo") photo: MultipartFile,

        @Parameter(description = "Тип фотографии: user или route")
        @RequestParam("type") type: String,

        @Parameter(description = "URL уже существующей фотографии (если нужно перезаписать)", required = false)
        @RequestParam("photoUrl") photoUrl: String? = null
    ): String

    @Operation(
        summary = "Скачать фотографию",
        description = "Скачивает фотографию по ключу (ключ — это часть URL после имени бакета)"
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = [DOWNLOAD_PHOTO_URL]
    )
    fun downloadFile(
        @Parameter(description = "Ключ файла в хранилище")
        @RequestParam("key") key: String
    ): ResponseEntity<ByteArray>
}
