package bm.app.khazaddumarmoury.uploads.application.ports;

import bm.app.khazaddumarmoury.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import lombok.Value;

public interface UploadUseCase {

    Upload save(SaveUploadCommand command);

    @Value
    @AllArgsConstructor
    class SaveUploadCommand {
        String filename;
        byte[] file;
        String contentType;
    }
}
