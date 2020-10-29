package se.ecutb.cardealers.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Year;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Car {
    @Id
    private String id;
    @NotEmpty(message = "Brand can not be empty")
    @Size(min = 3, max = 15, message = "Brand length invalid")
    private String brand;
    @NotEmpty(message = " Model can not be empty")
    private String model;
    @Indexed(unique = true)
    @NotEmpty(message = "RegisterNumber can not be empty")
    private String registerNo;
    private Double price;
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
