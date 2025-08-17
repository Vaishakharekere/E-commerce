package com.scem.ecommerce.service;

import com.scem.ecommerce.dao.AddressRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Address;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.exception.AddressNotFoundException;
import com.scem.ecommerce.service.impl.AddressServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock
    private AddressRepository addressRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private AddressServiceImpl addressService;

    private User user;
    private Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId(1L);
        user.setEmail("user@example.com");

        address = new Address();
        address.setAddressId(1L);
        address.setAddress("123 Main St");
        address.setUser(user);
    }

    @Test
    void testAddAddress() {
        when(addressRepo.save(any(Address.class))).thenReturn(address);

        Address saved = addressService.addAddress(user, address);

        assertNotNull(saved);
        assertEquals("123 Main St", saved.getAddress());
        assertEquals(user, saved.getUser());
        verify(addressRepo, times(1)).save(address);
    }

    @Test
    void testGetAddressesByUser() {
        when(addressRepo.findByUser(user)).thenReturn(Collections.singletonList(address));

        List<Address> addresses = addressService.getAddressesByUser(user);

        assertEquals(1, addresses.size());
        assertEquals(address, addresses.get(0));
        verify(addressRepo).findByUser(user);
    }

    @Test
    void testUpdateAddress_Success() {
        Address newDetails = new Address();
        newDetails.setAddress("456 New St");

        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepo.save(address)).thenReturn(address);

        Address updated = addressService.updateAddress(1L, user, newDetails);

        assertEquals("456 New St", updated.getAddress());
        verify(addressRepo).save(address);
    }

    @Test
    void testUpdateAddress_NotFound() {
        when(addressRepo.findById(1L)).thenReturn(Optional.empty());

        Address newDetails = new Address();
        newDetails.setAddress("456 New St");

        assertThrows(AddressNotFoundException.class, () ->
                addressService.updateAddress(1L, user, newDetails));
    }

    @Test
    void testUpdateAddress_NotOwner() {
        Address otherAddress = new Address();
        otherAddress.setUser(new User()); // different user

        when(addressRepo.findById(1L)).thenReturn(Optional.of(otherAddress));

        Address newDetails = new Address();
        newDetails.setAddress("New Street");

        assertThrows(AddressNotFoundException.class, () ->
                addressService.updateAddress(1L, user, newDetails));
    }

    @Test
    void testDeleteAddress_Success() {
        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));
        doNothing().when(addressRepo).delete(address);

        assertDoesNotThrow(() -> addressService.deleteAddress(1L, user));
        verify(addressRepo).delete(address);
    }

    @Test
    void testDeleteAddress_NotFound() {
        when(addressRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.deleteAddress(1L, user));
    }

    @Test
    void testDeleteAddress_NotOwner() {
        Address otherAddress = new Address();
        otherAddress.setUser(new User()); // different user

        when(addressRepo.findById(1L)).thenReturn(Optional.of(otherAddress));

        assertThrows(AddressNotFoundException.class, () -> addressService.deleteAddress(1L, user));
    }
}
