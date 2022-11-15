package com.hospitalsystem.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Element{
    public Distance distance;
    public Duration duration;
    public DurationInTraffic duration_in_traffic;
    public String status;
}
