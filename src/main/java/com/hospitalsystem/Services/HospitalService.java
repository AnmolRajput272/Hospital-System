package com.hospitalsystem.Services;

import com.hospitalsystem.Models.ApiResponse;
import com.hospitalsystem.Models.Element;
import com.hospitalsystem.Models.Hospital;
import com.hospitalsystem.Models.HospitalElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.hospitalsystem.Utils.Configuration.api_url;
import static com.hospitalsystem.Utils.Configuration.api_key;

@Service
public class HospitalService {

    @Autowired
    MongoOperations mongoOperations;

    RestTemplate restTemplate = new RestTemplate();

    public void insertHospital(Hospital hospital){
        mongoOperations.save(hospital);
    }

    public List<Hospital> find_all(){
        return mongoOperations.findAll(Hospital.class);
    }

    public List<Hospital> find_by_distance(Double latitude, Double longitude, Double distance){

        Point center = new Point(longitude,latitude);
        Distance radius = new Distance(distance, Metrics.KILOMETERS);
        Circle circle = new Circle(center,radius);

        Query query = new Query();
        query.addCriteria(Criteria.where("geoLocation").withinSphere(circle));

        return mongoOperations.find(query,Hospital.class);
    }

    public List<HospitalElement> find_by_road_distance(Double latitude, Double longitude, Double distance){

        List<Hospital> hospitals = find_by_distance(latitude,longitude,distance);
        List<HospitalElement> hospitals_within_range = new ArrayList<>();
        String prefix = api_url+"?origins="+latitude+","+longitude+"&destinations=";
        String suffix = "&departure_time=now&key="+api_key;
        String destinations = "";

        for(int i=0;i<hospitals.size();i++){
            Hospital hospital = hospitals.get(i);
            String destination = hospital.getGeoLocation().getLatitude()+","+hospital.getGeoLocation().getLongitude()+(i== hospitals.size()-1 ? "" : "|");
            destinations += destination;
        }

        String request_url = prefix+destinations+suffix;
        ResponseEntity<ApiResponse> apiResponse = restTemplate.getForEntity(request_url,ApiResponse.class);
        ApiResponse response = apiResponse.getBody();

        List<Element> hospitalDistances = response.getRows().get(0).getElements();
        for(int i=0;i<hospitalDistances.size();i++){
            if(hospitalDistances.get(i).getDistance().getValue()<=(distance*1000)){
                hospitals_within_range.add(new HospitalElement(hospitals.get(i),hospitalDistances.get(i)));
            }
        }

        return hospitals_within_range;
    }

}
