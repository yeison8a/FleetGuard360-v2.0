package com.FleetGuard360.backend.service;

import com.FleetGuard360.backend.dto.TipeAlertRequest;
import com.FleetGuard360.backend.model.TipeAlert;
import com.FleetGuard360.backend.repository.TipeAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipeAlertService {

    @Autowired
    private TipeAlertRepository tipeAlertRepository;

    public List<TipeAlert> getAllTipeAlerts() {
        return tipeAlertRepository.findAll();
    }

    public TipeAlert createTipeAlert(TipeAlertRequest request){

        if(request.getNombre() == null || request.getNombre().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        TipeAlert tipe = new TipeAlert();
        tipe.setNombre(request.getNombre());
        tipe.setDescripcion(request.getDescripcion());
        return tipeAlertRepository.save(tipe);
    }

    public void deleteTipeAlert(Integer id) {

        if(tipeAlertRepository.existsById(id)){
            throw new IllegalArgumentException("El id no existe");
        }
        tipeAlertRepository.deleteById(id);
    }
}
