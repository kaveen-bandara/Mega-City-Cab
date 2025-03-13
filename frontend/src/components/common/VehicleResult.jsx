import React from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const VehicleResult = ({ vehicleSearchResults }) => {
    const navigate = useNavigate();
    const isAdmin = ApiService.isAdmin();
    
    return (
        <section className='vehicle-results'>
            {vehicleSearchResults && vehicleSearchResults.length > 0 && (
                <div className='vehicle-list'>
                    {vehicleSearchResults.map(vehicle => (
                        <div key={vehicle.id} className='vehicle-list-item'>
                            <img className='vehicle-list-item-image' src={vehicle.vehiclePhotoUrl} alt={vehicle.vehicleType} />
                            <div className='vehicle-details'>
                                <h3>{vehicle.vehicleType}</h3>
                                <p>Price: Rs.{vehicle.vehiclePrice}</p>
                                <p>Description: {vehicle.vehicleDescription}</p>
                            </div>

                            <div className='book-now-div'>
                                {isAdmin ? (
                                    <button
                                        className='edit-vehicle-button'
                                        onClick={() => navigate(`/admin/edit-vehicle/${vehicle.id}`)}
                                    >
                                        Edit Vehicle Details
                                    </button>
                                ) : (
                                    <button
                                        className='book-now-button'
                                        onClick={() => navigate(`/vehicle-details-book/${vehicle.id}`)}
                                    >
                                        View/Book Now
                                    </button>
                                )}
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </section>
    );
};

export default VehicleResult;
