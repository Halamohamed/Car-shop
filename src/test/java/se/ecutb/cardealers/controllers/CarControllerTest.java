package se.ecutb.cardealers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@WebMvcTest(CarController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriPort = 8010)
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

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void getCarById()throws Exception{
        given(carRepository.findById(any())).willReturn(Optional.of(getValidCar()));

        mockMvc.perform(get("api/shop/cars/{id}", UUID.randomUUID().toString())
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
                                fieldWithPath("nuOfSeat").description("Cars number Of Seat"),
                                fieldWithPath("equipment").description("Cars equipment"),
                                fieldWithPath("milNo").description("Cars mile number"),
                                fieldWithPath("fuel").description("Cars fuel"),
                                fieldWithPath("gearbox").description("Cars gearbox"),
                                fieldWithPath("horsepower").description("Cars horsepower"),
                                fieldWithPath("carType").description("Cars type"),
                                fieldWithPath("carAge").description("Cars age"),
                                fieldWithPath("status").description("Cars status")
                        )));
    }

    void saveCar()throws Exception {
        Car car = getValidCar();
        car.setId(null);
        String carJson = objectMapper.writeValueAsString(car);
        carJson = carJson.replace("}",",\"car \":\"" + car.getRegisterNo() + "\"}");

        given(carRepository.save(any())).willReturn(getValidCar());

       // CarControllerTest fields = new CarControllerTest().ConstrainedFields(Car.class);

        mockMvc.perform(post("api/shop/cars")
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
                                fieldWithPath("nuOfSeat").description("Cars number Of Seat"),
                                fieldWithPath("equipment").description("Cars equipment"),
                                fieldWithPath("milNo").description("Cars mile number"),
                                fieldWithPath("fuel").description("Cars fuel"),
                                fieldWithPath("gearbox").description("Cars gearbox"),
                                fieldWithPath("horsepower").description("Cars horsepower"),
                                fieldWithPath("carType").description("Cars type"),
                                fieldWithPath("carAge").description("Cars age"),
                                fieldWithPath("status").description("Cars status")
                        )));
    }

    void updateCar() throws Exception {
        Car car = getValidCar();
        String carJson = objectMapper.writeValueAsString(car);
        carJson = carJson.replace("}",",\"car \":\"" + car.getRegisterNo() + "\"}");
        System.out.println("car:\n" + carJson);

        //CarControllerTest.ConstrainedFields fields = new CarControllerTest.ConstrainedFields(Car.class);

    }

    private Car getValidCar() {
        return Car.builder()
                .id(UUID.randomUUID().toString())
                .brand("Volvo").model("xc90").registerNo("volvo-999").price(300000.0).yearModel(2018).weight(3000).numOfSeats(5).equipment(null).mileNo(1000).fuel("disel").gearbox("automate").horsepower(226).carType("SEDAN").carAge("2 year").status("STOCK").build();
    }

}
