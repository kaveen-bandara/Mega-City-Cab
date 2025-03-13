package com.megaCityCab.backend.service.implementation;

import com.megaCityCab.backend.dto.BookingDTO;
import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Booking;
import com.megaCityCab.backend.entity.User;
import com.megaCityCab.backend.entity.Vehicle;
import com.megaCityCab.backend.exception.OurException;
import com.megaCityCab.backend.repository.BookingRepository;
import com.megaCityCab.backend.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public Response saveBooking(String vehicleId, String userId, Booking bookingRequest) {

        Response response = new Response();

        try {
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new OurException("Vehicle not found!"));
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found!"));

            List<Booking> existingBookings = vehicle.getBookings();
            if (!vehicleIsAvailable(bookingRequest, existingBookings)) {
                throw new OurException("Vehicle not available for the selected period!");
            }
            String confirmationCode = Utilities.generateRandomConfirmationCode(10);

            bookingRequest.setVehicle(vehicle);
            bookingRequest.setUser(user);
            bookingRequest.setConfirmationCode(confirmationCode);

            Booking savedBooking = bookingRepository.save(bookingRequest);

            // Add the booking to the user's bookings list
            List<Booking> userBookings = user.getBookings();
            userBookings.add(savedBooking);
            user.setBookings(userBookings);
            userRepository.save(user);

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
    public Response cancelBooking(String bookingId) {

        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking not found!"));

            // Remove the booking from the associated user
            User user = booking.getUser();
            if (user != null) {
                user.getBookings().removeIf(b -> b.getId().equals(bookingId));
                userRepository.save(user);
            }

            // Remove the booking from the associated vehicle
            Vehicle vehicle = booking.getVehicle();
            if (vehicle != null) {
                vehicle.getBookings().removeIf(b -> b.getId().equals(bookingId));
                vehicleRepository.save(vehicle);
            }

            bookingRepository.deleteById(bookingId);

            response.setMessage("Successfully canceled booking!");
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

        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getStartDate().equals(existingBooking.getStartDate())
                                || bookingRequest.getEndDate().isBefore(existingBooking.getEndDate())
                                || (bookingRequest.getStartDate().isAfter(existingBooking.getStartDate())
                                && bookingRequest.getStartDate().isBefore(existingBooking.getEndDate()))
                                || (bookingRequest.getStartDate().isBefore(existingBooking.getStartDate())

                                && bookingRequest.getEndDate().equals(existingBooking.getEndDate()))
                                || (bookingRequest.getStartDate().isBefore(existingBooking.getStartDate())

                                && bookingRequest.getEndDate().isAfter(existingBooking.getEndDate()))

                                || (bookingRequest.getStartDate().equals(existingBooking.getEndDate())
                                && bookingRequest.getEndDate().equals(existingBooking.getStartDate()))

                                || (bookingRequest.getStartDate().equals(existingBooking.getEndDate())
                                && bookingRequest.getEndDate().equals(bookingRequest.getStartDate()))
                );
    }
}
