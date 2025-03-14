package com.megaCityCab.backend.utilities;

import com.megaCityCab.backend.dto.BookingDTO;
import com.megaCityCab.backend.dto.UserDTO;
import com.megaCityCab.backend.dto.VehicleDTO;
import com.megaCityCab.backend.entity.Booking;
import com.megaCityCab.backend.entity.User;
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

    public static UserDTO mapUserEntityToUserDTO(User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public static VehicleDTO mapVehicleEntityToVehicleDTO(Vehicle vehicle) {

        VehicleDTO vehicleDTO = new VehicleDTO();

        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setLicensePlate(vehicle.getLicensePlate());
        vehicleDTO.setVehicleType(vehicle.getVehicleType());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setColor(vehicle.getColor());
        vehicleDTO.setVehiclePhotoUrl(vehicle.getVehiclePhotoUrl());
        vehicleDTO.setDescription(vehicle.getDescription());
        vehicleDTO.setFare(vehicle.getFare());
        vehicleDTO.setDriverName(vehicle.getDriverName());

        return vehicleDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setDestination(booking.getDestination());
        bookingDTO.setStartDate(booking.getStartDate());
        bookingDTO.setEndDate(booking.getEndDate());
        bookingDTO.setMessage(booking.getMessage());
        bookingDTO.setConfirmationCode(booking.getConfirmationCode());

        return bookingDTO;
    }

    public static VehicleDTO mapVehicleEntityToVehicleDTOPlusBookings(Vehicle vehicle) {

        VehicleDTO vehicleDTO = new VehicleDTO();

        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setLicensePlate(vehicle.getLicensePlate());
        vehicleDTO.setVehicleType(vehicle.getVehicleType());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setColor(vehicle.getColor());
        vehicleDTO.setVehiclePhotoUrl(vehicle.getVehiclePhotoUrl());
        vehicleDTO.setDescription(vehicle.getDescription());
        vehicleDTO.setFare(vehicle.getFare());
        vehicleDTO.setDriverName(vehicle.getDriverName());

        if (vehicle.getBookings() != null) {
            vehicleDTO.setBookings(vehicle.getBookings().stream().map(Utilities::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }

        return vehicleDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedVehicles(Booking booking, boolean mapUser) {

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setDestination(booking.getDestination());
        bookingDTO.setStartDate(booking.getStartDate());
        bookingDTO.setEndDate(booking.getEndDate());
        bookingDTO.setMessage(booking.getMessage());
        bookingDTO.setConfirmationCode(booking.getConfirmationCode());

        if (mapUser) {
            bookingDTO.setUser(Utilities.mapUserEntityToUserDTO(booking.getUser()));
        }

        if (booking.getVehicle() != null) {
            bookingDTO.setVehicle(mapVehicleEntityToVehicleDTO(booking.getVehicle()));
        }

        return bookingDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserBookingsAndVehicle(User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());
        userDTO.setRole(user.getRole());

        if (!user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedVehicles(booking, false)).collect(Collectors.toList()));
        }

        return userDTO;
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utilities::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<VehicleDTO> mapVehicleListEntityToVehicleListDTO(List<Vehicle> vehicleList) {
        return vehicleList.stream().map(Utilities::mapVehicleEntityToVehicleDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream().map(Utilities::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }
}
