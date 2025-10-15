package ru.spring.entity_manager.location;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationService {

    private final LocationEntityConverter locationEntityConverter;
    private final LocationRepository locationRepository;


    public LocationService(LocationEntityConverter locationEntityConverter, LocationRepository locationRepository) {
        this.locationEntityConverter = locationEntityConverter;
        this.locationRepository = locationRepository;
    }

    @Transactional(readOnly = true)
    public List<Location> getLocations() {
        return locationRepository.findAll().stream()
                .map(locationEntityConverter::convertToModel)
                .toList();
    }

    public Location createLocation(Location location) {
        LocationEntity locationToSave = locationEntityConverter.convertToEntity(location);
        LocationEntity locationEntity = locationRepository.save(locationToSave);

        return locationEntityConverter.convertToModel(locationEntity);
    }

    public void deleteLocation(Integer locationId) {
        locationExistingValidation(locationId);
        locationRepository.deleteById(locationId);
    }

    @Transactional(readOnly = true)
    public Location getLocationById(Integer locationId) {
        locationExistingValidation(locationId);
        LocationEntity locationEntity = locationRepository.findById(locationId)
                .orElseThrow();

        return locationEntityConverter.convertToModel(locationEntity);
    }

    public Location updateLocation(Integer locationId, Location location) {
        locationExistingValidation(locationId);
        locationRepository.updateLocation(
                locationId,
                location.name(),
                location.address(),
                location.capacity(),
                location.description()
        );

        return locationEntityConverter.convertToModel(locationRepository.findById(locationId).orElseThrow());
    }

    private void locationExistingValidation(Integer locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new IllegalArgumentException(
                    "Локация с id=%s не существует".formatted(locationId)
            );
        }
    }
}
