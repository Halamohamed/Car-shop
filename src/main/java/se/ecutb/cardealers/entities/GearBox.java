package se.ecutb.cardealers.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class GearBox {

    @Id
    private String id;
    @NotEmpty
    private String model;
    @NotBlank
    private String type;

}
