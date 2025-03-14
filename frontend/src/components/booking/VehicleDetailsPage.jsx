import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ApiService from '../../services/ApiService';
import DatePicker from 'react-datepicker';

const VehicleDetailsPage = () => {
    const navigate = useNavigate();
    const { vehicleId } = useParams();
    const [vehicleDetails, setVehicleDetails] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [totalFare, setTotalFare] = useState(0);
    const [showDatePicker, setShowDatePicker] = useState(false);
    const [userId, setUserId] = useState('');
    const [showMessage, setShowMessage] = useState(false);
    const [confirmationCode, setConfirmationCode] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            try {
                setIsLoading(true);
                const response = await ApiService.getVehicleById(vehicleId);
                setVehicleDetails(response.vehicle);
                const userProfile = await ApiService.getUserProfile();
                setUserId(userProfile.user.id);
            } catch (error) {
                setError(error.response?.data?.message || error.message);
            } finally {
                setIsLoading(false);
            }
        };
        fetchData();
    }, [vehicleId]);


    const handleConfirmBooking = async () => {
        if (!startDate || !endDate) {
            setErrorMessage("Please select start date and end date!");
            setTimeout(() => setErrorMessage(''), 5000);
            return;
        }

        const oneDay = 24 * 60 * 60 * 1000;
        const start = new Date(startDate);
        const end = new Date(endDate);
        const totalDays = Math.round(Math.abs((end - start) / oneDay)) + 1;

        const vehicleFarePerDay = vehicleDetails.fare;
        const totalFare = vehicleFarePerDay * totalDays;

        setTotalFare(totalFare);
    };

    const acceptBooking = async () => {
        try {

            const start = new Date(startDate);
            const end = new Date(endDate);

            const formattedStartDate = new Date(start.getTime() - (start.getTimezoneOffset() * 60000)).toISOString().split('T')[0];
            const formattedEndDate = new Date(end.getTime() - (end.getTimezoneOffset() * 60000)).toISOString().split('T')[0];

            const booking = {
                startDate: formattedStartDate,
                endDate: formattedEndDate
            };

            const response = await ApiService.saveBooking(vehicleId, userId, booking);
            if (response.statusCode === 200) {
                setConfirmationCode(response.confirmationCode);
                setShowMessage(true);
                setTimeout(() => {
                setShowMessage(false);
                navigate('/vehicles');
                }, 10000);
            }
        } catch (error) {
            setErrorMessage(error.response?.data?.message || error.message);
            setTimeout(() => setErrorMessage(''), 5000);
        }
    };

    if (isLoading) {
        return <p className='vehicle-detail-loading'>Loading vehicle details...</p>;
    }

    if (error) {
        return <p className='vehicle-detail-loading'>{error}</p>;
    }

    if (!vehicleDetails) {
        return <p className='vehicle-detail-loading'>Vehicle not found.</p>;
    }

    const { vehicleType, licensePlate, model, color, fare, vehiclePhotoUrl, description, driverName, bookings } = vehicleDetails;

    return (
        <div className='vehicle-details-booking'>
            {showMessage && (
                <p className='booking-success-message'>
                Booking successful! Confirmation code: {confirmationCode}. An SMS and email of your booking details have been sent to you.
                </p>
            )}
            {errorMessage && (
                <p className='error-message'>
                {errorMessage}
                </p>
            )}
            <h2>Vehicle Details</h2>
            <br />
            <img src={vehiclePhotoUrl} alt={vehicleType} className='vehicle-details-image' />

            <div className='vehicle-details-info'>
                <h3>{vehicleType}</h3>
                <p>{licensePlate}</p>
                <p>{model}</p>
                <p>{color}</p>
                <p>Fare: Rs.{fare}</p>
                <p>{description}</p>
                <p>{driverName}</p>
            </div>
            {bookings && bookings.length > 0 && (
                <div>
                    <h3>Existing Booking Details</h3>
                    <ul className='booking-list'>
                        {bookings.map((booking, index) => (
                            <li key={booking.id} className='booking-item'>
                                <span className='booking-number'>Booking {index + 1} </span>
                                <span className='booking-text'>Start Date: {booking.startDate} </span>
                                <span className='booking-text'>End Date: {booking.endDate}</span>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        
            <div className='booking-info'>
                <button className='book-now-button' onClick={() => setShowDatePicker(true)}>Book Now</button>
                <button className='go-back-button' onClick={() => setShowDatePicker(false)}>Go Back</button>
                {showDatePicker && (
                    <div className='date-picker-container'>
                        <DatePicker
                        className='detail-search-field'
                        selected={startDate}
                        onChange={(date) => setStartDate(date)}
                        selectsStart
                        startDate={startDate}
                        endDate={endDate}
                        placeholderText="Start Date"
                        dateFormat='dd/MM/yyyy'
                        />

                        <DatePicker
                        className='detail-search-field'
                        selected={endDate}
                        onChange={(date) => setEndDate(date)}
                        selectsEnd
                        startDate={startDate}
                        endDate={endDate}
                        minDate={startDate}
                        placeholderText="End Date"
                        dateFormat='dd/MM/yyyy'
                        />

                        <div className='guest-container'>              
                            <button className='confirm-booking' onClick={handleConfirmBooking}>Confirm Booking</button>
                        </div>
                    </div>
                )}

                {totalFare > 0 && (
                    <div className='total-price'>
                        <p>Total Fare: ${totalFare}</p>
                        <button onClick={acceptBooking} className='accept-booking'>Accept Booking</button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default VehicleDetailsPage;
