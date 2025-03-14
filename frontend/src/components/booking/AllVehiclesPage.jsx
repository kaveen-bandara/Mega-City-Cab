import React, { useState, useEffect } from 'react';
import ApiService from '../../service/ApiService';
import Pagination from '../common/Pagination';
import VehicleResult from '../common/VehicleResult';
import VehicleSearch from '../common/VehicleSearch';

const AllVehiclesPage = () => {
  const [vehicles, setVehicles] = useState([]);
  const [filteredVehicles, setFilteredVehicles] = useState([]);
  const [vehicleTypes, setVehicleTypes] = useState([]);
  const [selectedVehicleType, setSelectedVehicleType] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [vehiclesPerPage] = useState(5);

  const handleSearchResult = (results) => {
    setVehicles(results);
    setFilteredVehicles(results);
  };

  useEffect(() => {
    const fetchVehicles = async () => {
        try {
            const response = await ApiService.getAllVehicles();
            const allVehicles = response.vehicleList;
            setVehicles(allVehicles);
            setFilteredVehicles(allVehicles);
        } catch (error) {
            console.error("Error fetching vehicles: ", error.message);
        }
    };

    const fetchVehicleTypes = async () => {
        try {
            const types = await ApiService.getAllVehicleTypes();
            setVehicleTypes(types);
        } catch (error) {
            console.error("Error fetching vehicle types: ", error.message);
        }
    };

    fetchVehicles();
    fetchVehicleTypes();
  }, []);

  const handleVehicleTypeChange = (e) => {
    setSelectedVehicleType(e.target.value);
    filterVehicles(e.target.value);
  };

  const filterVehicles = (type) => {
    if (type === '') {
      setFilteredVehicles(vehicles);
    } else {
      const filtered = vehicles.filter((vehicle) => vehicle.vehicleType === type);
      setFilteredVehicles(filtered);
    }
    setCurrentPage(1);
  };

  const indexOfLastVehicle = currentPage * vehiclesPerPage;
  const indexOfFirstVehicle = indexOfLastVehicle - vehiclesPerPage;
  const currentVehicles = filteredVehicles.slice(indexOfFirstVehicle, indexOfLastVehicle);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className='all-vehicles'>
      <h2>All Vehicles</h2>
      <div className='all-vehicle-filter-div'>
        <label>Filter by Vehicle Type:</label>
        <select value={selectedVehicleType} onChange={handleVehicleTypeChange}>
          <option value=''>All</option>
          {vehicleTypes.map((type) => (
            <option key={type} value={type}>
              {type}
            </option>
          ))}
        </select>
      </div>
      
      <VehicleSearch handleSearchResult={handleSearchResult} />
      <VehicleResult vehicleSearchResults={currentVehicles} />

      <Pagination
        vehiclesPerPage={vehiclesPerPage}
        totalVehicles={filteredVehicles.length}
        currentPage={currentPage}
        paginate={paginate}
      />
    </div>
  );
};

export default AllVehiclesPage;
