package com.example.multiplazatest.domain;

import org.junit.jupiter.api.Test;

import com.example.multiplazatest.domain.model.Vehicle;
import com.example.multiplazatest.domain.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setNoOfVehicle("ABC123");
        vehicle.setModelName("Corolla");
        vehicle.setTypeVehicle("Carro");
        vehicle.setColour("Azul");
        vehicle.setBrand("Toyota");
        vehicle.setOwner("Juan");
        vehicle.setTimeOfIn("2021-09-01 10:00:00");
    }

    @Test
    void saveVehicle() {
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Vehicle result = vehicleService.saveVehicle(vehicle);

        assertNotNull(result);
        assertEquals("ABC123", result.getNoOfVehicle());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void getVehicleById() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        Optional<Vehicle> result = Optional.ofNullable(vehicleService.getVehicleById(1L));

        assertTrue(result.isPresent());
        assertEquals("ABC123", result.get().getNoOfVehicle());
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    void getAllVehicles() {
        List<Vehicle> vehiculos = Arrays.asList(vehicle);
        when(vehicleRepository.findAll()).thenReturn(vehiculos);
        List<Vehicle> result = vehicleService.getAllVehicles();

        assertEquals(1, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void deleteVehicle() {
        vehicleService.deleteVehicle(1L);
        verify(vehicleRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateVehicle() {
        Vehicle updateVehicle = new Vehicle();
        updateVehicle.setNoOfVehicle("DEF456");
        updateVehicle.setModelName("Honda Civic");
        updateVehicle.setTypeVehicle("Carro");
        updateVehicle.setColour("Rojo");

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle result = vehicleService.updateVehicle(1L, updateVehicle);

        assertEquals("DEF456", result.getNoOfVehicle());
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).save(vehicle);
    }
}