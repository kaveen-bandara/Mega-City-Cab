package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Booking;

public interface IBookingService {

    Response saveBooking(String vehicleId, String userId, Booking bookingRequest);

    Response getAllBookings();

    Response getBookingByConfirmationCode(String confirmationCode);

    Response cancelBooking(String bookingId);
}
