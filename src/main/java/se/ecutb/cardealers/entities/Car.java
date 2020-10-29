package se.ecutb.cardealers.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Year;
import java.util.List;

@Data
@Builder
public class Car {
    @Id
    private String id;
    private String brand;
    private String model;
    private String registerNo;
    private Double pris;
    private Year yearModel;
    private int weight;
    private int numOfSeats;
    private List<String> equipment;
    private int mileNo;
    private String fuel;
    private String gearbox;
    private int horsepower;
    private CarType carType;
    private String carAge;
    private CarStatus status;

}
