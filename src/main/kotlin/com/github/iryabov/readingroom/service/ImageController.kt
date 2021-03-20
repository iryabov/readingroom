package com.github.iryabov.readingroom.service

import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/images")
class ImageController {
    @CrossOrigin(origins = ["*"])
    @PostMapping("/convert")
    fun convert(@RequestParam("file") file: MultipartFile): ImageInfo {
        val content = IOUtils.toByteArray(file.inputStream)
        val encodedString: String = Base64.getEncoder().encodeToString(content)
        return ImageInfo(
                url = "data:image/png;base64,$encodedString",
                id = file.originalFilename,
                fileName = file.originalFilename,
                size = file.size
        )
    }
}

class ImageInfo(
        val url: String,
        val id: String? = null,
        val fileName: String? = null,
        val size: Long? = null
)