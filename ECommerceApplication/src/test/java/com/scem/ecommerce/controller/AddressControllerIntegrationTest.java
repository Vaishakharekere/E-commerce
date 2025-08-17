package com.scem.ecommerce.controller;

import com.scem.ecommerce.entity.Address;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.security.JwtAuthenticationFilter;
import com.scem.ecommerce.util.AuthUtil;
import com.scem.ecommerce.util.JwtTokenUtil;
import com.scem.ecommerce.dao.AddressRepository;
import com.scem.ecommerce.dao.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)  // disables security filters for easier testing
class AddressControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @SuppressWarnings("removal")
	@MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @SuppressWarnings("removal")
	@MockBean
    private JwtTokenUtil jwtTokenUtil;

    @SuppressWarnings("removal")
	@MockBean
    private AuthUtil authUtil;

    @BeforeEach
    void setUp() {
        // Clear database to avoid stale data issues
        addressRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        // Save and capture managed instance
        testUser = userRepository.save(user);

        // Mock AuthUtil to return the persisted user as the current authenticated user
        Mockito.when(authUtil.getCurrentUser()).thenReturn(testUser);
    }

    @Test
    void testAddAddress() throws Exception {
        Address address = new Address();
        address.setAddress("123 Main St");

        mockMvc.perform(post("/api/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(address)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    void testGetAddressesByUser() throws Exception {
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setUser(testUser);
        addressRepository.save(address);

        mockMvc.perform(get("/api/addresses"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].address").value("123 Main St"));
    }

    @Test
    void testUpdateAddress() throws Exception {
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setUser(testUser);
        Address savedAddress = addressRepository.save(address);

        Address updatedDetails = new Address();
        updatedDetails.setAddress("456 New St");

        mockMvc.perform(put("/api/addresses/" + savedAddress.getAddressId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.address").value("456 New St"));
    }

    @Test
    void testDeleteAddress() throws Exception {
        Address address = new Address();
        address.setAddress("123 Main St");
        address.setUser(testUser); // must be managed persisted user
        Address savedAddress = addressRepository.save(address);

        Mockito.when(authUtil.getCurrentUser()).thenReturn(testUser); // make sure this stays updated

        mockMvc.perform(delete("/api/addresses/" + savedAddress.getAddressId()))
            .andDo(print())  // print full response and exception stacktrace
            .andExpect(status().isNoContent());
    }

}
