import React from 'react';
import { Link } from 'react-router-dom';

const BookingResult = ({ bookingSearchResults }) => {
    return (
        <div className='booking-results'>
        {bookingSearchResults.map((booking) => (
            <div key={booking.id} className='booking-result-item'>
            <p>Vehicle Id: {booking.vehicleId}</p>
            <p>User Id: {booking.userId}</p>
            <p>Destination: {booking.destination}</p>
            <p>Start Date: {booking.startDate}</p>
            <p>End Date: {booking.endDate}</p>
            <p>Message: {booking.message}</p>
            <p>Status: {booking.status}</p>
            <Link to={`/admin/edit-booking/${booking.id}`} className='edit-link'>Edit</Link>
            </div>
        ))}
        </div>
    );
};

export default BookingResult;
