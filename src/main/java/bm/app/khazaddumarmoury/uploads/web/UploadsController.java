package bm.app.khazaddumarmoury.uploads.web;

import bm.app.khazaddumarmoury.uploads.application.ports.UploadUseCase;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/uploads")
@AllArgsConstructor
public class UploadsController {

    private final UploadUseCase upload;

    /**
     * Map is used instead of e.g. ifPresent, because I will be creating a response out of what I find. And I
     * will be creating what I find because DTO.
     * In this method I can return either the file (the content of bytes) or I can have two endpoints. In this one
     * I can return metadata of the file and in the second I can serve the file.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UploadResponse> getUpload(@PathVariable String id) {
        return upload.getById(id)
                .map(file -> {
                    UploadResponse response = new UploadResponse(
                            file.getId(),
                            file.getContentType(),
                            file.getFilename(),
                            file.getCreatedAt()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build()); //.build() is required for .notFound().
    }

    /**
     * The endpoint serving the actual file.
     * In order to serve a file for a browser or a download, I need to serve specific headers. A header for
     * sending files is HttpHeaders.CONTENT_DISPOSITION and it requires specific content - I have it defined
     * as "contentDisposition" -> I think this is just a required structure.
     * I am also serving the actual file - the body. It is managed by "bytes" being supplied to "resource".
     * So I am sending the header, the file and I also need declare the content type of my request so the browser
     * can manage it properly (and not try to parse it into JSON for example). It's done by MediaType (which is an
     * ENUM).
     */
    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getUploadFile(@PathVariable String id) {
        return upload.getById(id)
                .map(file -> {
                    String contentDisposition = "attachment; filename=\"" + file.getFilename() + "\"";
                    byte[] bytes = file.getFile();
                    Resource resource = new ByteArrayResource(bytes);
                    return ResponseEntity
                            .ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                            .contentType(MediaType.parseMediaType(file.getContentType()))
                            .body(resource);
                })
                .orElse(ResponseEntity.notFound().build()); //.build() is required for .notFound().
    }

    /**
     * Contains everything except for the actual file - so it has all the information about the file instead.
     */
    @Value
    @AllArgsConstructor
    static class UploadResponse {
        String id;
        String contentType;
        String filename;
        LocalDateTime createdAt;
    }

}
