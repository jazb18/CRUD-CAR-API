package com.example.multiplazatest.domain;

import com.example.multiplazatest.domain.model.Vehicle;
import com.example.multiplazatest.domain.repository.VehicleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public Vehicle getVehicleById(Long id) {
        Optional<Vehicle> OptionalVehicle = vehicleRepository.findById(id);
        return OptionalVehicle.get();
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Vehicle updateVehicle(Long id, Vehicle updateVehicle) {
        Optional<Vehicle> OptionalVehicle = vehicleRepository.findById(id);
        Vehicle vehicleToUpdate = OptionalVehicle.get();
        vehicleToUpdate.setNoOfVehicle(updateVehicle.getNoOfVehicle());
        vehicleToUpdate.setColour(updateVehicle.getColour());
        vehicleToUpdate.setBrand(updateVehicle.getBrand());
        vehicleToUpdate.setOwner(updateVehicle.getOwner());
        vehicleToUpdate.setModelName(updateVehicle.getModelName());
        vehicleToUpdate.setTypeVehicle(updateVehicle.getTypeVehicle());
        vehicleToUpdate.setTimeOfIn(updateVehicle.getTimeOfIn());
        vehicleRepository.save(vehicleToUpdate);
        return vehicleToUpdate;
    }
}
