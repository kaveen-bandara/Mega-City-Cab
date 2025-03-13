import React from 'react';
import { Link } from 'react-router-dom';

const BookingResult = ({ bookingSearchResults }) => {
    return (
        <div className='booking-results'>
        {bookingSearchResults.map((booking) => (
            <div key={booking.id} className='booking-result-item'>
            <p>Cab Id: {booking.cabId}</p>
            <p>Registration Number: {booking.registrationNumber}</p>
            <p>Pickup Date & Time: {booking.pickupDateTime}</p>
            <p>Status: {booking.status}</p>
            <Link to={`/admin/edit-booking/${booking.id}`} className='edit-link'>Edit</Link>
            </div>
        ))}
        </div>
    );
};

export default BookingResult;
