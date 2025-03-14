import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ApiService from '../../services/ApiService';

const EditVehiclePage = () => {
    const { vehicleId } = useParams();
    const navigate = useNavigate();
    const [vehicleDetails, setVehicleDetails] = useState({
        licensePlate: '',
        vehicleType: '',
        model: '',
        color: '',
        vehiclePhotoUrl: '',
        description: '',
        fare: '',
        driverName: ''
    });
    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState(null);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        const fetchVehicleDetails = async () => {
            try {
                const response = await ApiService.getVehicleById(vehicleId);
                setVehicleDetails({
                    vehiclePhotoUrl: response.vehicle.vehiclePhotoUrl,
                    vehicleType: response.vehicle.vehicleType,
                    licensePlate: response.vehicle.licensePlate,
                    model: response.vehicle.model,
                    color: response.vehicle.color,
                    description: response.vehicle.description,
                    fare: response.vehicle.fare,
                    driverName: response.vehicle.driverName,
                });
            } catch (error) {
                setError(error.response?.data?.message || error.message);
            }
        };
        fetchVehicleDetails();
    }, [vehicleId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setVehicleDetails(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];
        if (selectedFile) {
            setFile(selectedFile);
            setPreview(URL.createObjectURL(selectedFile));
        } else {
            setFile(null);
            setPreview(null);
        }
    };

    const handleUpdate = async () => {
        try {
            const formData = new FormData();
            formData.append('vehicleType', vehicleDetails.vehicleType);
            formData.append('licensePlate', vehicleDetails.licensePlate);
            formData.append('model', vehicleDetails.model);
            formData.append('color', vehicleDetails.color);
            formData.append('description', vehicleDetails.description);
            formData.append('fare', vehicleDetails.fare);
            formData.append('driverName', vehicleDetails.driverName);

            if (file) {
                formData.append('photo', file);
            }

            const result = await ApiService.updateVehicleDetails(vehicleId, formData);
            if (result.statusCode === 200) {
                setSuccess("Vehicle updated successfully!");
                
                setTimeout(() => {
                    setSuccess('');
                    navigate('/admin/manage-vehicles');
                }, 3000);
            }
            setTimeout(() => setSuccess(''), 5000);
        } catch (error) {
            setError(error.response?.data?.message || error.message);
            setTimeout(() => setError(''), 5000);
        }
    };

    const handleDelete = async () => {
        if (window.confirm("Do you want to delete this vehicle?")) {
            try {
                const result = await ApiService.deleteVehicle(vehicleId);
                if (result.statusCode === 200) {
                    setSuccess("Vehicle Deleted successfully!");
                    
                    setTimeout(() => {
                        setSuccess('');
                        navigate('/admin/manage-vehicles');
                    }, 3000);
                }
            } catch (error) {
                setError(error.response?.data?.message || error.message);
                setTimeout(() => setError(''), 5000);
            }
        }
    };

    return (
        <div className='edit-vehicle-container'>
            <h2>Edit Vehicle</h2>
            {error && <p className='error-message'>{error}</p>}
            {success && <p className='success-message'>{success}</p>}
            <div className='edit-vehicle-form'>
                <div className='form-group'>
                    {preview ? (
                        <img src={preview} alt="Vehicle Preview" className='vehicle-photo-preview' />
                    ) : (
                        vehicleDetails.vehiclePhotoUrl && (
                            <img src={vehicleDetails.vehiclePhotoUrl} alt="Vehicle" className='vehicle-photo' />
                        )
                    )}
                    <input
                        type='file'
                        name='vehiclePhoto'
                        onChange={handleFileChange}
                    />
                </div>
                <div className='form-group'>
                    <label>Vehicle Type</label>
                    <input
                        type='text'
                        name='vehicleType'
                        value={vehicleDetails.vehicleType}
                        onChange={handleChange}
                    />
                </div>
                <div className='form-group'>
                    <label>License Plate</label>
                    <input
                        type='text'
                        name='licensePlate'
                        value={vehicleDetails.licensePlate}
                        onChange={handleChange}
                    />
                </div>
                <div className='form-group'>
                    <label>Model</label>
                    <input
                        type='text'
                        name='model'
                        value={vehicleDetails.model}
                        onChange={handleChange}
                    />
                </div>
                <div className='form-group'>
                    <label>Color</label>
                    <input
                        type='text'
                        name='color'
                        value={vehicleDetails.color}
                        onChange={handleChange}
                    />
                </div>
                <div className='form-group'>
                    <label>Description</label>
                    <textarea
                        name='description'
                        value={vehicleDetails.description}
                        onChange={handleChange}
                    ></textarea>
                </div>
                <div className='form-group'>
                    <label>Fare</label>
                    <input
                        type='text'
                        name='fare'
                        value={vehicleDetails.fare}
                        onChange={handleChange}
                    />
                    <div className='form-group'>
                    <label>Driver Name</label>
                    <input
                        type='text'
                        name='driverName'
                        value={vehicleDetails.driverName}
                        onChange={handleChange}
                    />
                </div>
                </div>
                <button className='update-button' onClick={handleUpdate}>Update Vehicle</button>
                <button className='delete-button' onClick={handleDelete}>Delete Vehicle</button>
            </div>
        </div>
    );
};

export default EditVehiclePage;
