package se.ecutb.cardealers.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MotorCycle {

    @Id
    private String id;
    @NotEmpty(message = "Model can not be empty")
    private String model;
    @NotEmpty(message = "Register number must not be empty")
    @Indexed(unique = true)
    private String registerNo;
    private Double price;
    private int yearModel;
    private int weight;
    private List<String> equipment;
    private int mileNo;
    private String fuel;
    private String gearbox;
    private int horsepower;
}
