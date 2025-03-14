import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ApiService from '../../services/ApiService';

const EditBookingPage = () => {
    const navigate = useNavigate();
    const { confirmationCode } = useParams();
    const [bookingDetails, setBookingDetails] = useState(null);
    const [error, setError] = useState(null);
    const [success, setSuccessMessage] = useState(null);

    useEffect(() => {
        const fetchBookingDetails = async () => {
            try {
                const response = await ApiService.getBookingByConfirmationCode(confirmationCode);
                setBookingDetails(response.booking);
            } catch (error) {
                setError(error.message);
            }
        };

        fetchBookingDetails();
    }, [confirmationCode]);

    const makeBooking = async (bookingId) => {
        if (!window.confirm("Are you sure you want to make book?")) {
            return;
        }

        try {
            const response = await ApiService.cancelBooking(bookingId);
            if (response.statusCode === 200) {
                setSuccessMessage("The b0oking was Successfully made!")
                
                setTimeout(() => {
                    setSuccessMessage('');
                    navigate('/admin/manage-bookings');
                }, 3000);
            }
        } catch (error) {
            setError(error.response?.data?.message || error.message);
            setTimeout(() => setError(''), 5000);
        }
    };

    return (
        <div className='find-booking-page'>
            <h2>Booking Detail</h2>
            {error && <p className='error-message'>{error}</p>}
            {success && <p className='success-message'>{success}</p>}
            {bookingDetails && (
                <div className='booking-details'>
                    <h3>Booking Details</h3>
                    <p>Confirmation Code: {bookingDetails.confirmationCode}</p>
                    <p>Destination: {bookingDetails.destination}</p>
                    <p>Start Date: {bookingDetails.startDate}</p>
                    <p>End Date: {bookingDetails.endDate}</p>
                    <p>Message: {bookingDetails.message}</p>
                    <p>Guest Email: {bookingDetails.guestEmail}</p>

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
                        <p> Fare: Rs.{bookingDetails.vehicle.fare}</p>
                        <p> Description: {bookingDetails.vehicle.description}</p>
                        <p> Driver Name: {bookingDetails.vehicle.driverName}</p>
                        <img src={bookingDetails.vehicle.vehiclePhotoUrl} alt="" sizes='' srcSet='' />
                    </div>
                    <button
                        className='acheive-booking'
                        onClick={() => makeBooking(bookingDetails.id)}>Make Booking
                    </button>
                </div>
            )}
        </div>
    );
};

export default EditBookingPage;
