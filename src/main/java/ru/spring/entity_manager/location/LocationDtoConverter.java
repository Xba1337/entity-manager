package ru.spring.entity_manager.location;

import org.springframework.stereotype.Component;

@Component
public class LocationDtoConverter {

    public Location convertToModel(LocationDto locationDto) {

        return new Location(
                locationDto.id(),
                locationDto.name(),
                locationDto.address(),
                locationDto.capacity(),
                locationDto.description()
        );
    }

    public LocationDto convertToDto(Location location) {
        return new LocationDto(
                location.id(),
                location.name(),
                location.address(),
                location.capacity(),
                location.description()
        );
    }
}
