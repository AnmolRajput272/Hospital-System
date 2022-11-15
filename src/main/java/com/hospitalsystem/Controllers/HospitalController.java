package com.hospitalsystem.Controllers;

import com.hospitalsystem.Models.Hospital;
import com.hospitalsystem.Models.HospitalElement;
import com.hospitalsystem.Services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hospitals")
    public List<Hospital> getAllHospitals(){
        return hospitalService.find_all();
    }

    @PostMapping("/hospital")
    public String insertHospital(@RequestBody Hospital hospital){
        hospitalService.insertHospital(hospital);
        return "Hospital Inserted";
    }

    @GetMapping("/get_hospitals_in_range")
    public List<Hospital> find_by_distance(@RequestParam(value="lat") Double latitude,
                                           @RequestParam(value="long") Double longitude,
                                            @RequestParam(value="dist") Double distance){
        return hospitalService.find_by_distance(latitude, longitude, distance);
    }

    @GetMapping("/get_hospitals_in_range_road")
    public List<HospitalElement> find_by_road_distance(@RequestParam(value="lat") Double latitude,
                                                       @RequestParam(value="long") Double longitude,
                                                       @RequestParam(value="dist") Double distance){
        return hospitalService.find_by_road_distance(latitude, longitude, distance);
    }

}
