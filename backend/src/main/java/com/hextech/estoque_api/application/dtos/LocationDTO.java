package com.hextech.estoque_api.application.dtos;

import com.hextech.estoque_api.domain.entities.Location;

public class LocationDTO {

    private Long id;
    private String name;

    public LocationDTO() {
    }

    public LocationDTO(String name) {
        this.name = name;
    }

    public LocationDTO(Location entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
