package com.megaCityCab.backend.utilities;

import com.megaCityCab.backend.dto.BookingDTO;
import com.megaCityCab.backend.dto.CustomerDTO;
import com.megaCityCab.backend.dto.VehicleDTO;
import com.megaCityCab.backend.entity.Booking;
import com.megaCityCab.backend.entity.Customer;
import com.megaCityCab.backend.entity.Vehicle;

import java.security.SecureRandom;
import java.util.List;
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

    public static CustomerDTO mapCustomerEntityToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setRegistrationNumber(customer.getRegistrationNumber());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setNic(customer.getNic());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        customerDTO.setEmail(customer.getEmail());

        return customerDTO;
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
        vehicleDTO.setDriverName(vehicle.getDriverName());

        return vehicleDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookingNumber(booking.getBookingNumber());
        bookingDTO.setPickupLocation(booking.getPickupLocation());
        bookingDTO.setDropoffLocation(booking.getDropoffLocation());
        bookingDTO.setPickupDateTime(booking.getPickupDateTime());
        bookingDTO.setMessage(booking.getMessage());
        bookingDTO.setConfirmationCode(booking.getConfirmationCode());

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
        vehicleDTO.setDriverName(vehicle.getDriverName());

        if(vehicle.getBookings() != null) {
            vehicleDTO.setBookings(vehicle.getBookings().stream().map(Utilities::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }

        return vehicleDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedVehicles(Booking booking, boolean mapUser) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookingNumber(booking.getBookingNumber());
        bookingDTO.setPickupLocation(booking.getPickupLocation());
        bookingDTO.setDropoffLocation(booking.getDropoffLocation());
        bookingDTO.setPickupDateTime(booking.getPickupDateTime());
        bookingDTO.setMessage(booking.getMessage());
        bookingDTO.setConfirmationCode(booking.getConfirmationCode());

        if(mapUser) {
            bookingDTO.setCustomer(Utilities.mapCustomerEntityToCustomerDTO(booking.getCustomer()));
        }

        if(booking.getVehicle() != null) {
            bookingDTO.setVehicle(mapVehicleEntityToVehicleDTO(booking.getVehicle()));
        }

        return bookingDTO;
    }

    public static CustomerDTO mapCustomerEntityToCustomerDTOPlusCustomerBookingsAndVehicle(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setRegistrationNumber(customer.getRegistrationNumber());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setNic(customer.getNic());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        customerDTO.setEmail(customer.getEmail());

        if(!customer.getBookings().isEmpty()) {
            customerDTO.setBookings(customer.getBookings().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedVehicles(booking, false)).collect(Collectors.toList()));
        }

        return customerDTO;
    }

    public static List<CustomerDTO> mapCustomerListEntityToCustomerListDTO(List<Customer> customerList) {
        return customerList.stream().map(Utilities::mapCustomerEntityToCustomerDTO).collect(Collectors.toList());
    }

    public static List<VehicleDTO> mapVehicleListEntityToVehicleListDTO(List<Vehicle> vehicleList) {
        return vehicleList.stream().map(Utilities::mapVehicleEntityToVehicleDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream().map(Utilities::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }
}
