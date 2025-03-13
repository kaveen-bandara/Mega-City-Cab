package com.megaCityCab.backend.service.implementation;

import com.megaCityCab.backend.dto.BookingDTO;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Booking;
import com.megaCityCab.backend.entity.Vehicle;
import com.megaCityCab.backend.exception.OurException;
import com.megaCityCab.backend.repository.BookingRepository;
import com.megaCityCab.backend.repository.CustomerRepository;
import com.megaCityCab.backend.repository.VehicleRepository;
import com.megaCityCab.backend.service.connection.IBookingService;
import com.megaCityCab.backend.utilities.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Response saveBooking(String cabId, String registrationNumber, Booking bookingRequest) {

        Response response = new Response();

        try {
            Vehicle vehicle = vehicleRepository.findById(cabId).orElseThrow(() -> new OurException("Vehicle not found!"));
            Customer customer = customerRepository.findById(registrationNumber).orElseThrow(() -> new OurException("Customer not found!"));

            List<Booking> existingBookings = vehicle.getBookings();
            if (!vehicleIsAvailable(bookingRequest, existingBookings)) {
                throw new OurException("Vehicle not available for the selected period!");
            }
            String confirmationCode = Utilities.generateRandomConfirmationCode(10);

            bookingRequest.setVehicle(vehicle);
            bookingRequest.setCustomer(customer);
            bookingRequest.setConfirmationCode(confirmationCode);

            Booking savedBooking = bookingRepository.save(bookingRequest);

            // Add the booking to the customer's bookings list
            List<Booking> customerBookings = customer.getBookings();
            customerBookings.add(savedBooking);
            customer.setBookings(customerBookings);
            customerRepository.save(customer);

            // Add the booking to the vehicle's bookings list
            List<Booking> roomBookings = vehicle.getBookings();
            roomBookings.add(savedBooking);
            vehicle.setBookings(roomBookings);
            vehicleRepository.save(vehicle);

            response.setStatusCode(200);
            response.setMessage("Successfully saved booking!");
            response.setBookingConfirmationCode(confirmationCode);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while saving booking " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllBookings() {

        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utilities.mapBookingListEntityToBookingListDTO(bookingList);

            response.setMessage("Successfully gotten all bookings!");
            response.setStatusCode(200);
            response.setBookingList(bookingDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all bookings " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findByConfirmationCode(confirmationCode).orElseThrow(() -> new OurException("Booking not found!"));
            BookingDTO bookingDTO = Utilities.mapBookingEntityToBookingDTOPlusBookedVehicles(booking, true);

            response.setMessage("Successfully gotten booking by confirmation code!");
            response.setStatusCode(200);
            response.setBooking(bookingDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting booking by confirmation code " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(String bookingNumber) {

        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingNumber).orElseThrow(() -> new OurException("Booking not found!"));

            // Remove the booking from the associated customer
            Customer customer = booking.getCustomer();
            if (customer != null) {
                customer.getBookings().removeIf(b -> b.getBookingNumber().equals(bookingNumber));
                customerRepository.save(customer);
            }

            // Remove the booking from the associated vehicle
            Vehicle vehicle = booking.getVehicle();
            if (vehicle != null) {
                vehicle.getBookings().removeIf(b -> b.getBookingNumber().equals(bookingNumber));
                vehicleRepository.save(vehicle);
            }

            bookingRepository.deleteById(bookingNumber);

            response.setMessage("Successfully cancelling booking!");
            response.setStatusCode(200);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error cancelling booking " + e.getMessage());
        }
        return response;
    }

    private boolean vehicleIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream().noneMatch(existingBooking -> bookingRequest.getPickupDateTime().equals(existingBooking.getPickupDateTime()));
    }
}
