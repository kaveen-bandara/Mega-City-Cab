package com.megaCityCab.backend.utilities;

import com.megaCityCab.backend.dto.*;
import com.megaCityCab.backend.entity.*;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class Utilities {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    public static AdminDTO mapAdminEntityToAdminDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();

        adminDTO.setAdminId(admin.getAdminId());
        adminDTO.setUsername(admin.getUsername());

        return adminDTO;
    }

    public static CustomerDTO mapCustomerEntityToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setRegistrationNumber(customer.getRegistrationNumber());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setNIC(customer.getNIC());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        customerDTO.setEmail(customer.getEmail());

        return customerDTO;
    }

    public static DriverDTO mapDriverEntityToDriverDTO(Driver driver) {
        DriverDTO driverDTO = new DriverDTO();

        driverDTO.setDriverId(driver.getDriverId());
        driverDTO.setName(driver.getName());
        driverDTO.setPhoneNumber(driver.getPhoneNumber());

        return driverDTO;
    }

    public static VehicleDTO mapVehicleEntityToVehicleDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();

        vehicleDTO.setCabId(vehicle.getCabId());
        vehicleDTO.setLicensePlate(vehicle.getLicensePlate());
        vehicleDTO.setVehicleType(vehicle.getVehicleType());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setColor(vehicle.getColor());
        vehicleDTO.setCabPhotoUrl(vehicle.getCabPhotoUrl());
        vehicleDTO.setDescription(vehicle.getDescription());
        vehicleDTO.setCabFare(vehicle.getCabFare());
        vehicleDTO.setIsActive(vehicle.getIsActive());
        vehicleDTO.setDriverId(vehicle.getDriverId());

        return vehicleDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookingNumber(booking.getBookingNumber());
        bookingDTO.setPickupLocation(booking.getPickupLocation());
        bookingDTO.setDropoffLocation(booking.getDropoffLocation());
        bookingDTO.setPickupDateTime(booking.getPickupDateTime());
        bookingDTO.setMessage(booking.getMessage());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        return bookingDTO;
    }

    public static VehicleDTO mapVehicleEntityToVehicleDTOPlusBookings(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();

        vehicleDTO.setCabId(vehicle.getCabId());
        vehicleDTO.setLicensePlate(vehicle.getLicensePlate());
        vehicleDTO.setVehicleType(vehicle.getVehicleType());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setColor(vehicle.getColor());
        vehicleDTO.setCabPhotoUrl(vehicle.getCabPhotoUrl());
        vehicleDTO.setDescription(vehicle.getDescription());
        vehicleDTO.setCabFare(vehicle.getCabFare());
        vehicleDTO.setIsActive(vehicle.getIsActive());
        vehicleDTO.setDriverId(vehicle.getDriverId());

        if (vehicle.getBookingIds() != null) {
            vehicleDTO.setBookings(vehicle.getBookingIds().stream().map(Utilities::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }

        return vehicleDTO;
    }
}
