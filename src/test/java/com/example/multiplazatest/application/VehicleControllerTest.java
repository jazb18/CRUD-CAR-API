package com.example.multiplazatest.application;

import com.example.multiplazatest.domain.VehicleService;
import com.example.multiplazatest.domain.model.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
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
    void saveVehicle() throws Exception {
        when(vehicleService.saveVehicle(any(Vehicle.class))).thenReturn(vehicle);

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vehicle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.noOfVehicle").value("ABC123"));
    }

    @Test
    void getAllVehicles() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(Arrays.asList(vehicle));
        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noOfVehicle").value("ABC123"));
    }

    @Test
    void searchVehicleByIdIfExist() throws Exception {
        when(vehicleService.getVehicleById(1L)).thenReturn(vehicle);
        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noOfVehicle").value("ABC123"));
    }

    @Test
    void searchVehicleByIdIfNotExist() throws Exception {
        when(vehicleService.getVehicleById(1L)).thenReturn(vehicle);
        mockMvc.perform(get("/api/vehicles/"))
                .andExpect(status().isNotFound());
    }

    @Disabled
    @Test
    void deleteVehicleById() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(1L);

        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isNoContent());
    }
    @Disabled
    @Test
    void deleteVehicleByIdIfNotExist() throws Exception {
        doThrow(new RuntimeException("Not found Vehicle")).when(vehicleService).deleteVehicle(1L);

        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateVehicleIfExist() throws Exception {
        Vehicle updateVehicle = new Vehicle();
        updateVehicle.setNoOfVehicle("DEF456");
        updateVehicle.setModelName("Honda Civic");
        updateVehicle.setTypeVehicle("Carro");
        updateVehicle.setColour("Rojo");

        when(vehicleService.updateVehicle(eq(1L), any(Vehicle.class))).thenReturn(updateVehicle);

        mockMvc.perform(put("/api/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateVehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noOfVehicle").value("DEF456"));
    }

    @Test
    void updateVehicleIfNotExist() throws Exception {
        Vehicle updateVehicle = new Vehicle();
        updateVehicle.setNoOfVehicle("DEF456");
        updateVehicle.setModelName("Honda Civic");
        updateVehicle.setTypeVehicle("Carro");
        updateVehicle.setColour("Rojo");

        when(vehicleService.updateVehicle(eq(1L), any(Vehicle.class)))
                .thenThrow(new RuntimeException("Not found Vehicle"));

        mockMvc.perform(put("/api/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateVehicle)))
                .andExpect(status().isBadRequest());
    }
}