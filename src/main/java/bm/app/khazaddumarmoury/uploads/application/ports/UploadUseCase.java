package bm.app.khazaddumarmoury.uploads.application.ports;

import bm.app.khazaddumarmoury.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

public interface UploadUseCase {

    Upload save(SaveUploadCommand command);

    /**
     * To serve uploaded pictures.
     */
    Optional<Upload> getById(String id);

    @Value
    @AllArgsConstructor
    class SaveUploadCommand {
        String filename;
        byte[] file;
        String contentType;
    }
}
