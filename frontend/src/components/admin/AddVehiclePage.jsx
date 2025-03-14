import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../services/ApiService';

const AddVehiclePage = () => {
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
    const [vehicleTypes, setVehicleTypes] = useState([]);
    const [newVehicleType, setNewVehicleType] = useState(false);


    useEffect(() => {
        const fetchVehicleTypes = async () => {
            try {
                const types = await ApiService.getAllVehicleTypes();
                setVehicleTypes(types);
            } catch (error) {
                console.error("Error fetching vehicle types: ", error.message);
            }
        };
        fetchVehicleTypes();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setVehicleDetails(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleVehicleTypeChange = (e) => {
        if (e.target.value === 'new') {
            setNewVehicleType(true);
            setVehicleDetails(prevState => ({ ...prevState, vehicleType: '' }));
        } else {
            setNewVehicleType(false);
            setVehicleDetails(prevState => ({ ...prevState, vehicleType: e.target.value }));
        }
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

    const addVehicle = async () => {
        if (!vehicleDetails.licensePlate
            || !vehicleDetails.vehicleType
            || !vehicleDetails.model 
            || !vehicleDetails.color  
            || !vehicleDetails.description
            || !vehicleDetails.fare
            || !vehicleDetails.driverName) {
            setError("All vehicle details must be provided!");
            setTimeout(() => setError(''), 5000);
            return;
        }

        if (!window.confirm("Do you want to add this vehicle?")) {
            return;
        }

        try {
            const formData = new FormData();
            formData.append('licensePlate', vehicleDetails.licensePlate);
            formData.append('vehicleType', vehicleDetails.vehicleType);
            formData.append('model', vehicleDetails.model);
            formData.append('color', vehicleDetails.color);
            formData.append('description', vehicleDetails.description);
            formData.append('fare', vehicleDetails.fare);
            formData.append('driverName', vehicleDetails.driverName);

            if (file) {
                formData.append('photo', file);
            }

            const result = await ApiService.addNewVehicle(formData);
            if (result.statusCode === 200) {
                setSuccess("Vehicle added successfully!");
                
                setTimeout(() => {
                    setSuccess('');
                    navigate('/admin/manage-vehicles');
                }, 3000);
            }
        } catch (error) {
            setError(error.response?.data?.message || error.message);
            setTimeout(() => setError(''), 5000);
        }
    };

    return (
        <div className='edit-vehicle-container'>
            <h2>Add New Vehicle</h2>
            {error && <p className='error-message'>{error}</p>}
            {success && <p className='success-message'>{success}</p>}
            <div className='edit-vehicle-form'>
                <div className='form-group'>
                    {preview && (
                        <img src={preview} alt="Vehicle Preview" className='vehicle-photo-preview' />
                    )}
                    <input
                        type='file'
                        name='vehiclePhoto'
                        onChange={handleFileChange}
                    />
                </div>

                <div className='form-group'>
                    <label>Vehicle Type</label>
                    <select value={vehicleDetails.vehicleType} onChange={handleVehicleTypeChange}>
                        <option value=''>Select a vehicle type</option>
                        {vehicleTypes.map(type => (
                            <option key={type} value={type}>{type}</option>
                        ))}
                        <option value='new'>Other (please specify)</option>
                    </select>
                    {newVehicleType && (
                        <input
                            type='text'
                            name='vehicleType'
                            placeholder="Enter new vehicle type"
                            value={vehicleDetails.vehicleType}
                            onChange={handleChange}
                        />
                    )}
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
                </div>
                <div className='form-group'>
                    <label>Driver Name</label>
                    <input
                        type='text'
                        name='driverName'
                        value={vehicleDetails.driverName}
                        onChange={handleChange}
                    />
                </div>
                <button className='update-button' onClick={addVehicle}>Add Vehicle</button>
            </div>
        </div>
    );
};

export default AddVehiclePage;
