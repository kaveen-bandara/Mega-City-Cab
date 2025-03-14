import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ApiService from '../../services/ApiService';

const VehicleSearch = ({ handleSearchResult }) => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [vehicleType, setVehicleType] = useState('');
  const [vehicleTypes, setVehicleTypes] = useState([]);
  const [error, setError] = useState('');

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

  const showError = (message, timeout = 5000) => {
    setError(message);
    setTimeout(() => {
      setError('');
    }, timeout);
  };

  const handleInternalSearch = async () => {
    if (!startDate || !endDate || !vehicleType) {
      showError("Please select all fields!");
      return false;
    }
    try {
      const formattedStartDate = startDate ? startDate.toISOString().split('T')[0] : null;
      const formattedEndDate = endDate ? endDate.toISOString().split('T')[0] : null;
      const response = await ApiService.getAvailableVehiclesByDateAndVehicleType(formattedStartDate, formattedEndDate, vehicleType);

      if (response.statusCode === 200) {
        if (response.vehicleList.length === 0) {
          showError("Vehicle is currently unavailable for this date range on the selected vehicle type!");
          return
        }
        handleSearchResult(response.vehicleList);
        setError('');
      }
    } catch (error) {
      showError("Unown error occured: " + error.response.data.message);
    }
  };

  return (
    <section>
      <div className='search-container'>
        <div className='search-field'>
          <label>Start Date</label>
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            dateFormat='dd/MM/yyyy'
            placeholderText="Select Start Date"
          />
        </div>
        <div className='search-field'>
          <label>End Date</label>
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            dateFormat='dd/MM/yyyy'
            placeholderText='Select End Date'
          />
        </div>

        <div className='search-field'>
          <label>Vehicle Type</label>
          <select value={vehicleType} onChange={(e) => setVehicleType(e.target.value)}>
            <option disabled value="">
              Select vehicle Type
            </option>
            {vehicleTypes.map((vehicleType) => (
              <option key={vehicleType} value={vehicleType}>
                {vehicleType}
              </option>
            ))}
          </select>
        </div>
        <button className='home-search-button' onClick={handleInternalSearch}>
          Search vehicles
        </button>
      </div>
      {error && <p className='error-message'>{error}</p>}
    </section>
  );
};

export default VehicleSearch;
