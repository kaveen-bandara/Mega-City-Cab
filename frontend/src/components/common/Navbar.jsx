import React from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import ApiService from '../../services/ApiService';

function Navbar() {
    const isAuthenticated = ApiService.isAuthenticated();
    const isAdmin = ApiService.isAdmin();
    const isCustomer = ApiService.isCustomer();
    const navigate = useNavigate();

    const handleLogout = () => {
        const isLogout = window.confirm("Are you sure you want to logout?");
        if(isLogout) {
            ApiService.logout();
            navigate('/home');
        }
    };

    return (
        <nav className='navbar'>
            <div className='navbar-brand'>
                <NavLink to='/home'>Mega City Cab</NavLink>
            </div>
            <ul className='navbar-ul'>
                <li><NavLink to='/home' activeclassname='active'>Home</NavLink></li>
                <li><NavLink to='/vehicles' activeclassname='active'>Vehicles</NavLink></li>
                <li><NavLink to='/find-booking' activeclassname='active'>Find my Booking</NavLink></li>

                {isCustomer && <li><NavLink to='/profile' activeclassname='active'>Profile</NavLink></li>}
                {isAdmin && <li><NavLink to='/admin' activeclassname='active'>Admin</NavLink></li>}

                {!isAuthenticated &&<li><NavLink to='/login' activeclassname='active'>Login</NavLink></li>}
                {!isAuthenticated &&<li><NavLink to='/register' activeclassname='active'>Register</NavLink></li>}
                {isAuthenticated && <li onClick={handleLogout}><Link>Logout</Link></li>}
            </ul>
        </nav>
    );
}

export default Navbar;
