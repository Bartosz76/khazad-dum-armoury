package bm.app.khazaddumarmoury.uploads.application;

import bm.app.khazaddumarmoury.uploads.application.ports.UploadUseCase;
import bm.app.khazaddumarmoury.uploads.domain.Upload;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
class UploadService implements UploadUseCase {

    /**
     * Maps are the best when I want do store data in memory.
     * String - due to the fact that the Id of the Upload is of that type.
     */
    private final Map<String, Upload> storage = new ConcurrentHashMap<>();

    /**
     * Id will be generated as a random String. For that I will use Apache Commons Lang library.
     * It's the mechanism used for newId variable. It generates a random String consisting of both
     * letters and digits and of 8 length.
     */
    @Override
    public Upload save(SaveUploadCommand command) {
        String newId = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        Upload upload = new Upload(
                newId,
                command.getFile(),
                command.getContentType(),
                command.getFilename(),
                LocalDateTime.now());
        storage.put(upload.getId(), upload);
        System.out.println("Painting saved: " + upload.getFilename() + " with the id of " + newId + ".");
        return upload;
    }

    @Override
    public Optional<Upload> getById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void removeById(String id) {
        storage.remove(id);
    }
}
