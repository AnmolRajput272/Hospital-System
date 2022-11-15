package com.hospitalsystem.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvHospital {

    private String hospital;
    private Double latitude;
    private Double longitude;

}
