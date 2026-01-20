package ru.spring.entity_manager.location;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationDtoConverter locationDtoConverter;

    public LocationController(LocationService locationService, LocationDtoConverter locationDtoConverter) {
        this.locationService = locationService;
        this.locationDtoConverter = locationDtoConverter;
    }

    @GetMapping
    public List<LocationDto> getLocations() {
        return locationService.getLocations()
                .stream()
                .map(locationDtoConverter::convertToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public LocationDto getLocationById(@PathVariable Integer id) {
        Location location = locationService.getLocationById(id);

        return locationDtoConverter.convertToDto(location);
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody @Valid LocationDto locationDto) {
        Location location = locationDtoConverter.convertToModel(locationDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationDtoConverter
                        .convertToDto(locationService.createLocation(location)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationById(@PathVariable Integer id) {
        locationService.deleteLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public LocationDto updateLocationById(@PathVariable Integer id, @RequestBody @Valid LocationDto locationDto) {
        Location location = locationDtoConverter.convertToModel(locationDto);
        Location updatedLocation = locationService.updateLocation(id, location);

        return locationDtoConverter.convertToDto(updatedLocation);
    }
}
