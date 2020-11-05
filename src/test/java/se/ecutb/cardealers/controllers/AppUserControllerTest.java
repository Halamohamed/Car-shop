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
import se.ecutb.cardealers.entities.AppUser;
import se.ecutb.cardealers.entities.Roles;
import se.ecutb.cardealers.repositories.AppUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@ExtendWith(RestDocumentationExtension.class)

@AutoConfigureRestDocs(uriPort = 4000)
@ActiveProfiles("test")
public class AppUserControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AppUserRepository userRepository;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void findAllUsers() throws Exception {
        given(userRepository.findAll()).willReturn(List.of(getValidUser()));

        mockMvc.perform(get("/api/shop/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("shop/users-get-all"));
    }

    //@Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void findUserById() throws Exception {
        given(userRepository.findById(any())).willReturn(Optional.of(getValidUser()));

        mockMvc.perform(get("/api/shop/users/{id}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("shop/users-get-one",
                        pathParameters(
                                parameterWithName("id").description("UUID string of desired user to get.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("User ID"),
                                fieldWithPath("firstname").description("Users firstname"),
                                fieldWithPath("lastname").description("Users lastname"),
                                fieldWithPath("mail").description("Users mail address"),
                                fieldWithPath("phone").description("Users phone number"),
                                fieldWithPath("username").description("Users username"),
                                fieldWithPath("acl").description("Users authorities")
                        )));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void saveUser() throws Exception {
        AppUser user = getValidUser();
        user.setId(null);
        String userJson = objectMapper.writeValueAsString(user);
        userJson = userJson.replace("}", ",\"password\":\"" + user.getPassword() + "\"}");

        given(userRepository.save(any())).willReturn(getValidUser());

        AppUserControllerTest.ConstrainedFields fields = new AppUserControllerTest.ConstrainedFields(AppUser.class);

        mockMvc.perform(post("/api/shop/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andDo(document("shop/users-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("firstname").description("Users firstname"),
                                fields.withPath("lastname").description("Users lastname"),
                                fields.withPath("mail").description("Users mail address"),
                                fields.withPath("phone").description("Users phone number"),
                                fields.withPath("username").description("Users username"),
                                fields.withPath("password").description("Users password"),
                                fields.withPath("acl").description("Users authorities")

                        ),
                        responseFields(
                                fieldWithPath("id").description("User ID"),
                                fieldWithPath("firstname").description("Users firstname"),
                                fieldWithPath("lastname").description("Users lastname"),
                                fieldWithPath("mail").description("Users mail address"),
                                fieldWithPath("phone").description("Users phone number"),
                                fieldWithPath("username").description("Users username"),
                                fieldWithPath("acl").description("Users authorities")
                        )));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void updateUser() throws Exception {
        AppUser user = getValidUser();
        String userJson = objectMapper.writeValueAsString(user);
        userJson = userJson.replace("}", ",\"password\":\"" + user.getPassword() + "\"}");
        System.out.println("user:\n" + userJson);

        AppUserControllerTest.ConstrainedFields fields = new AppUserControllerTest.ConstrainedFields(AppUser.class);

        given(userRepository.existsById(any())).willReturn(true);

        mockMvc.perform(put("/api/shop/users/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isNoContent())
                .andDo(document("shop/users-update",
                        pathParameters(
                                parameterWithName("id").description("UUID string of desired user to update.")
                        ),
                        requestFields(
                                fields.withPath("id").description("User ID"),
                                fields.withPath("firstname").description("Users firstname"),
                                fields.withPath("lastname").description("Users lastname"),
                                fields.withPath("mail").description("Users mail address"),
                                fields.withPath("phone").description("Users phone number"),
                                fields.withPath("username").description("Users username"),
                                fields.withPath("password").description("Users password"),
                                fields.withPath("acl").description("Users authorities")
                        )));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void deleteUser() throws Exception {

        given(userRepository.existsById(any())).willReturn(true);

        mockMvc.perform(delete("/api/shop/users/{id}", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("shop/users-delete",
                        pathParameters(
                                parameterWithName("id").description("UUID string of desired user to delete.")
                        )));
    }

    private AppUser getValidUser() {
        return AppUser.builder()
                .id(UUID.randomUUID().toString())
                .firstname("Hala")
                .lastname("Mohammed")
                .mail("hala@example.com")
                .phone("072-7245858")
                .username("hala")
                .password("hala")
                .acl(List.of(Roles.ADMIN))
                .build();
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
