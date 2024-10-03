package com.example.multiplazatest.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String noOfVehicle;
    private String colour;
    private String brand;
    private String owner;
    private String modelName;
    private String typeVehicle;
    private String timeOfIn;
}
