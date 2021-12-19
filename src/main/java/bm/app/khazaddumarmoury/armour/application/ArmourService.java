package bm.app.khazaddumarmoury.armour.application;

import bm.app.khazaddumarmoury.armour.application.port.ArmourUseCase;
import bm.app.khazaddumarmoury.armour.domain.Armour;
import bm.app.khazaddumarmoury.armour.domain.ArmourRepository;
import bm.app.khazaddumarmoury.uploads.application.ports.UploadUseCase;
import bm.app.khazaddumarmoury.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static bm.app.khazaddumarmoury.uploads.application.ports.UploadUseCase.*;

@Service
@AllArgsConstructor
class ArmourService implements ArmourUseCase {

    private final ArmourRepository armourRepository;
    /**
     * Uploads are in a different catalog, so I am taking to it from here via a port, not the
     * concrete implementation of the interface, because hexagonal.
     */
    private final UploadUseCase upload;

    /**
     * In case there are two implementations of ArmourRepository, the @Qualifier
     * allows me to determine which one of the implementations is going to be injected.
     * What I provide as a String is the bean's name.
     */

//    Only one implementation of the repository remains so below is not necessary.
//    Thanks to @AllArgsConstructor, Spring will automatically inject the only implementation
//    of the ArmourRepository it has.

//    public ArmourService(@Qualifier("memoryArmourRepository") ArmourRepository armourRepository) {
//        this.armourRepository = armourRepository;
//    }

    @Override
    public List<Armour> findByName(String name) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.getName().toLowerCase().startsWith(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Armour> findOneByName(String name) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.getName().startsWith(name))
                .findFirst();
    }

    @Override
    public List<Armour> findBySmith(String smith) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.getSmith().toLowerCase().contains(smith.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Adding a few CRUD methods. It's good to communicate via interfaces between different
     * application's layers because I am separating the concrete implementation from the
     * "blueprint" -> makes my application more flexible and easier to expand (OCP).
     * Hence, the ArmourUseCase interface of which this Service is a concrete implementation.
     */

    @Override
    public List<Armour> findAll() {
        return armourRepository.findAll();
    }

    @Override
    public Optional<Armour> findById(Long id) {
        return armourRepository.findById(id);
    }



    @Override
    public Optional<Armour> findOneByNameAndSmith(String name, String smith) {
        return armourRepository.findAll()
                               .stream()
                               .filter(armour -> armour.getName().startsWith(name))
                               .filter(armour -> armour.getSmith().startsWith(smith))
                               .findFirst();
    }

    @Override
    public List<Armour> findByNameAndSmith(String name, String smith) {
        return armourRepository.findAll()
                .stream()
                .filter(armour -> armour.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(armour -> armour.getSmith().toLowerCase().contains(smith.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Technically, I can just take Armour as a parameter and automatically load it into the "DB",
     * but that would prevent the logic, like validation, from being used here. I would need to do it
     * elsewhere. I'd prefer to validate the parameters before creating an object.
     * I can also just take in all the parameters separately... Or I can take one argument - a Command.
     */
    @Override
    public Armour addArmour(CreateArmourCommand command) { //If something goes wrong later on -> I added the price to the Command. Make sure there's no problem with that.
        Armour armour = command.toArmour();
        return armourRepository.save(armour);
    }

    @Override
    public void removeById(Long id) {
        armourRepository.removeById(id);
    }

    @Override
    public UpdateArmourResponse updateArmour(UpdateArmourCommand command) {
        return armourRepository.findById(command.getId())
        .map(armour -> {
            Armour updatedArmour = command.updateFields(armour);
            armourRepository.save(updatedArmour);
            return UpdateArmourResponse.SUCCESS;
        })
        .orElseGet(() -> new UpdateArmourResponse(false, Collections.singletonList("Armour not found with id: " + command.getId())));
    }

    /**
     * Below I am receiving parameters as a dto and then, if the armour piece for which I want to add a
     * painting exists, I am creating a new object out of the uploaded file with its parameters being initially
     * provided ones but converted to another dto and then the id of the newly created upload is being passed to
     * said armour piece. Next, I will save the file itself to memory.
     */
    @Override
    public void updateArmourPainting(UpdateArmourPaintingCommand command) {
        armourRepository.findById(command.getId())
                .ifPresent(armour -> {
                    Upload savedUpload = upload.save(new SaveUploadCommand(command.getFilename(), command.getFile(), command.getContentType()));
                    armour.setPaintingId(savedUpload.getId());
                });
    }

    /**
     * I have a dependency on UploadUseCase injected, so I can remove the id of the painting from Armour object and
     * refer to the Upload itself (via interface -> service, of course).
     * There is both .ifPresent() and an If, because the first I am checking if there's an armour piece and then
     * if it contains any painting's id.
     */
    @Override
    public void removeArmourPainting(Long id) {
        armourRepository.findById(id)
                .ifPresent(armour -> {
                    if (armour.getPaintingId() != null) {
                        upload.removeById(armour.getPaintingId());
                        armour.setPaintingId(null);
                        armourRepository.save(armour);
                    }
                });
    }
}
