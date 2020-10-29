package se.ecutb.cardealers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AppUser {
    @Id
    private String id;
    @NotEmpty(message = "Firstname can not be empty")
    @Size(min = 3, max = 15, message = "Brand length invalid")
    private String firstname;
    @NotEmpty(message = "Lastname can not be empty")
    @Size(min = 3, max = 10 , message = "Firstname length invalid")
    private String lastname;
    @Email(message = "E-mail address invalid")
    private String mail;
    @Pattern(regexp = "([0-9]){2,4}-([0-9]){5,8}", message = "Phone number invalid")
    private String phone;
    @NotBlank(message = "Username must contain a value")
    @Indexed(unique = true)
    private String username;
    @Size(min = 4, max = 10, message = "Password length invalid")
    @NotBlank(message = "Password must contain a value")
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private List<Roles> acl;

}
