import React, { useState } from 'react';
import VehicleResult from '../common/VehicleResult';
import VehicleSearch from '../common/VehicleSearch';

const HomePage = () => {

    const [vehicleSearchResults, setVehicleSearchResults] = useState([]);

    // Function to handle search results
    const handleSearchResult = (results) => {
        setVehicleSearchResults(results);
    };

    return (
        <div className='home'>
            {/* HEADER / BANNER SECTION */}
            <section>
                <header className='header-banner'>
                    <img src='./assets/images/cab.jpg' alt="Mega City Cab" className='header-image' />
                    <div className='overlay'></div>
                    <div className='animated-texts overlay-content'>
                        <h1>
                            Welcome to <span className='mega-city-cab-color'>Mega City Cab</span>
                        </h1><br />
                        <h3>Step into a haven of comfort and care</h3>
                    </div>
                </header>
            </section>

            {/* SEARCH / FIND AVAILABLE VEHICLE SECTION */}
            <VehicleSearch handleSearchResult={handleSearchResult} />
            <VehicleResult setVehicleSearchResults={vehicleSearchResults} />

            <h4><a className='view-vehicles-home' href='/vehicles'>All Vehicles</a></h4>

            <h2 className='home-services'>Services with our cabs at <span className='mega-city-cab-color'>Mega City Cab</span></h2>

            {/* SERVICES SECTION */}
            <section className='service-section'>
                <div className='service-card'>
                    <img src='./assets/images/ac.png' alt="Air Conditioning" />
                    <div className='service-details'>
                        <h3 className='service-title'>Air Conditioning</h3>
                        <p className='service-description'>Stay cool and comfortable throughout your trip.</p>
                    </div>
                </div>
                <div className='service-card'>
                    <img src='./assets/images/mini-bar.png' alt="Security" />
                    <div className='service-details'>
                        <h3 className='service-title'>Security</h3>
                        <p className='service-description'>A secure travel for our customers is our main priority.</p>
                    </div>
                </div>
                <div className='service-card'>
                    <img src='./assets/images/parking.png' alt="Efficient" />
                    <div className='service-details'>
                        <h3 className='service-title'>Efficient</h3>
                        <p className='service-description'>Our drivers are trained to make better driving decisions giving you the best passenger experience.</p>
                    </div>
                </div>
                <div className='service-card'>
                    <img src='./assets/images/wifi.png' alt="WiFi" />
                    <div className='service-details'>
                        <h3 className='service-title'>WiFi</h3>
                        <p className='service-description'>Stay connected throughout your trip with complimentary high-speed Wi-Fi access.</p>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default HomePage;
