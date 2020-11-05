package se.ecutb.cardealers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import se.ecutb.cardealers.entities.Car;
import se.ecutb.cardealers.repositories.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriPort = 4000)
@ActiveProfiles("test")
public class CarControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private CarRepository carRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(value = "admin",roles = {"ADMIN"})
    void getAllCars() throws Exception{
        given(carRepository.findAll()).willReturn(List.of(getValidCar()));

        mockMvc.perform(get("/api/shop/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("shop/cars-get-all"));
    }

    //@Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void getCarById()throws Exception{
        given(carRepository.findById(any())).willReturn(Optional.of(getValidCar()));

        mockMvc.perform(get("/api/shop/cars/{id}", UUID.randomUUID().toString())
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("shop/cars-get-one",
                        pathParameters(
                                parameterWithName("id").description("UUID string of desired car to get.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Car ID"),
                                fieldWithPath("brand").description("Cars Brand "),
                                fieldWithPath("model").description("Cars Model"),
                                fieldWithPath("registerNo").description("Cars Register number"),
                                fieldWithPath("price").description("Cars price"),
                                fieldWithPath("yearModel").description("Cars year model"),
                                fieldWithPath("weight").description("Cars weight"),
                                fieldWithPath("numOfSeats").description("Cars number Of Seat"),
                                fieldWithPath("equipment").description("Cars equipment"),
                                fieldWithPath("mileNo").description("Cars mile number"),
                                fieldWithPath("fuel").description("Cars fuel"),
                                fieldWithPath("gearbox").description("Cars gearbox"),
                                fieldWithPath("horsepower").description("Cars horsepower"),
                                fieldWithPath("carType").description("Cars type"),
                                fieldWithPath("carAge").description("Cars age"),
                                fieldWithPath("status").description("Cars status")
                        )));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void saveCar()throws Exception {
        Car car = getValidCar();
        car.setId(null);
        String carJson = objectMapper.writeValueAsString(car);
        carJson = carJson.replace("}",",\"car \":\"" + car.getRegisterNo() + "\"}");

        given(carRepository.save(any())).willReturn(getValidCar());

        CarControllerTest.ConstrainedFields fields = new CarControllerTest.ConstrainedFields(Car.class);

        mockMvc.perform(post("/api/shop/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carJson))
                .andExpect(status().isCreated())
                .andDo(document("shop/cars-new",
                        responseFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("brand").description("Cars Brand "),
                                fieldWithPath("model").description("Cars Model"),
                                fieldWithPath("registerNo").description("Cars Register number"),
                                fieldWithPath("price").description("Cars price"),
                                fieldWithPath("yearModel").description("Cars year model"),
                                fieldWithPath("weight").description("Cars weight"),
                                fieldWithPath("numOfSeats").description("Cars number Of Seat"),
                                fieldWithPath("equipment").description("Cars equipment"),
                                fieldWithPath("mileNo").description("Cars mile number"),
                                fieldWithPath("fuel").description("Cars fuel"),
                                fieldWithPath("gearbox").description("Cars gearbox"),
                                fieldWithPath("horsepower").description("Cars horsepower"),
                                fieldWithPath("carType").description("Cars type"),
                                fieldWithPath("carAge").description("Cars age"),
                                fieldWithPath("status").description("Cars status")
                        )));
    }

    //@Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void updateCar() throws Exception {
        Car car = getValidCar();
        String carJson = objectMapper.writeValueAsString(car);
        carJson = carJson.replace("}",",\"car \":\"" + car.getRegisterNo() + "\"}");
        System.out.println("car:\n" + carJson);

        given(carRepository.existsById(any())).willReturn(true);

        CarControllerTest.ConstrainedFields fields = new CarControllerTest.ConstrainedFields(Car.class);


        mockMvc.perform(put("/api/shop/cars/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(carJson))
                .andExpect(status().isNoContent())
                .andDo(document("shop/cars-update",
                        pathParameters(
                                parameterWithName("id").description("UUID string of desired car to update.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Car ID"),
                                fieldWithPath("brand").description("Cars Brand "),
                                fieldWithPath("model").description("Cars Model"),
                                fieldWithPath("registerNo").description("Cars Register number"),
                                fieldWithPath("price").description("Cars price"),
                                fieldWithPath("yearModel").description("Cars year model"),
                                fieldWithPath("weight").description("Cars weight"),
                                fieldWithPath("numOfSeats").description("Cars number Of Seat"),
                                fieldWithPath("equipment").description("Cars equipment"),
                                fieldWithPath("mileNo").description("Cars mile number"),
                                fieldWithPath("fuel").description("Cars fuel"),
                                fieldWithPath("gearbox").description("Cars gearbox"),
                                fieldWithPath("horsepower").description("Cars horsepower"),
                                fieldWithPath("carType").description("Cars type"),
                                fieldWithPath("carAge").description("Cars age"),
                                fieldWithPath("status").description("Cars status")
                        )));

    }

    private Car getValidCar() {
        return Car.builder()
                .id(UUID.randomUUID().toString())
                .brand("Volvo").model("xc90").registerNo("volvo-999").price(300000.0).yearModel(2018).weight(3000).numOfSeats(5).equipment(null).mileNo(1000).fuel("disel").gearbox("automate").horsepower(226).carType("SEDAN").carAge("2 year").status("STOCK").build();
    }
    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }

}
