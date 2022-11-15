package com.hospitalsystem.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hospitals")
public class Hospital {

    @Id
    private String id;
    private String name;
    private Location geoLocation;

    public Hospital(String name, Location geoLocation) {
        this.name = name;
        this.geoLocation = geoLocation;
    }
}
