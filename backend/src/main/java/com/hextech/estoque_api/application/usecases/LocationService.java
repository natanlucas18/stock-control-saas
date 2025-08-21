package com.hextech.estoque_api.application.usecases;

import com.hextech.estoque_api.application.dtos.LocationDTO;
import com.hextech.estoque_api.domain.entities.Client;
import com.hextech.estoque_api.domain.entities.Location;
import com.hextech.estoque_api.domain.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository repository;

    @Transactional
    public LocationDTO insert(LocationDTO requestDTO) {
        Location entity = new Location();
        entity.setName(requestDTO.getName());

        //Test: Lógica para obter client ainda será implementada
        Client client = new Client();
        client.setId(1L);
        entity.setClient(client);

        entity = repository.save(entity);

        return new LocationDTO(entity);
    }
}
