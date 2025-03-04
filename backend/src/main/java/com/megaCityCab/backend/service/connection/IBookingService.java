package com.megaCityCab.backend.service.connection;

import com.megaCityCab.backend.dto.Response;
import com.megaCityCab.backend.entity.Booking;

public interface IBookingService {

    Response saveBooking(String cabId, String registrationNumber, Booking bookingRequest);

    Response getAllBookings();

    Response findBookingByConfirmationCode(String confirmationCode);

    Response cancelBooking(String bookingNumber);
}
