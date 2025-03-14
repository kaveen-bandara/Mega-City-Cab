import React, { useState } from 'react';
import ApiService from '../../service/ApiService';

const FindBookingPage = () => {
    const [confirmationCode, setConfirmationCode] = useState('');
    const [bookingDetails, setBookingDetails] = useState(null);
    const [error, setError] = useState(null);

    const handleSearch = async () => {
        if (!confirmationCode.trim()) {
            setError("Please Enter a booking confirmation code!");
            setTimeout(() => setError(''), 5000);
            return;
        }
        try {
            const response = await ApiService.findBookingByConfirmationCode(confirmationCode);
            setBookingDetails(response.booking);
            setError(null);
        } catch (error) {
            setError(error.response?.data?.message || error.message);
            setTimeout(() => setError(''), 5000);
        }
    };

    return (
        <div className='find-booking-page'>
            <h2>Find Booking</h2>
            <div className='search-container'>
                <input
                    required
                    type='text'
                    placeholder="Enter your booking confirmation code"
                    value={confirmationCode}
                    onChange={(e) => setConfirmationCode(e.target.value)}
                />
                <button onClick={handleSearch}>Find</button>
            </div>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {bookingDetails && (
                <div className='booking-details'>
                    <h3>Booking Details</h3>
                    <p>Confirmation Code: {bookingDetails.confirmationCode}</p>
                    <p>Destination: {bookingDetails.destination}</p>
                    <p>Start Date: {bookingDetails.startDate}</p>
                    <p>End Date: {bookingDetails.endDate}</p>
                    <p>Message: {bookingDetails.message}</p>

                    <br />
                    <hr />
                    <br />
                    <h3>Booker Details</h3>
                    <div>
                        <p> Name: {bookingDetails.user.name}</p>
                        <p> Email: {bookingDetails.user.email}</p>
                        <p> Phone Number: {bookingDetails.user.mobileNumber}</p>
                    </div>

                    <br />
                    <hr />
                    <br />
                    <h3>Vehicle Details</h3>
                    <div>
                        <p> Vehicle Type: {bookingDetails.vehicle.vehicleType}</p>
                        <p> License Plate: {bookingDetails.vehicle.licensePlate}</p>
                        <p> Model: {bookingDetails.vehicle.model}</p>
                        <p> Color: {bookingDetails.vehicle.color}</p>
                        <p> Driver Name: {bookingDetails.vehicle.driverName}</p>
                        <img src={bookingDetails.vehicle.vehiclePhotoUrl} alt="" sizes='' srcSet='' />
                    </div>
                </div>
            )}
        </div>
    );
};

export default FindBookingPage;
