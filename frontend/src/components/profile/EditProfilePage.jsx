import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const EditProfilePage = () => {
    const [customer, setUser] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const response = await ApiService.getUserProfile();
                setUser(response.user);
            } catch (error) {
                setError(error.message);
            }
        };

        fetchUserProfile();
    }, []);

    const handleDeleteProfile = async () => {
        if (!window.confirm('Are you sure you want to delete your account?')) {
            return;
        }
        try {
            await ApiService.deleteCustomer(customer.id);
            navigate('/signup');
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div className='edit-profile-page'>
            <h2>Edit Profile</h2>
            {error && <p className='error-message'>{error}</p>}
            {customer && (
                <div className='profile-details'>
                    <p><strong>Name:</strong> {customer.firstName} {customer.lastName}</p>
                    <p><strong>Email:</strong> {customer.email}</p>
                    <p><strong>Phone Number:</strong> {customer.phoneNumber}</p>
                    <button className='delete-profile-button' onClick={handleDeleteProfile}>Delete Profile</button>
                </div>
            )}
        </div>
    );
};

export default EditProfilePage;
