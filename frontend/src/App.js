import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/common/Navbar';
import FooterComponents from './components/common/Footer';
import LoginPage from './components/auth/LoginPage';
import RegisterPage from './components/auth/RegisterPage';
import HomePage from './components/home/HomePage';
import AllVehiclesPage from './components/booking/AllVehiclesPage';
import VehicleDetailsBookingPage from './components/booking/VehicleDetailsPage';
import FindBookingPage from './components/booking/FindBookingPage';
import AdminPage from './components/admin/AdminPage';
import ManageVehiclePage from './components/admin/ManageVehiclePage';
import EditVehiclePage from './components/admin/EditVehiclePage';
import AddVehiclePage from './components/admin/AddVehiclePage';
import ManageBookingsPage from './components/admin/ManageBookingsPage';
import EditBookingPage from './components/admin/EditBookingPage';
import ProfilePage from './components/profile/ProfilePage';
import EditProfilePage from './components/profile/EditProfilePage';
import { ProtectedRoute, AdminRoute } from './services/guard';

function App() {
  return (
    <BrowserRouter>
      <div className='app'>
        <Navbar />
        <div className='content'>
          <Routes>
            {/* Public Routes */}
            <Route exact path='/home' element={<HomePage />} />
            <Route exact path='/login' element={<LoginPage />} />
            <Route path='/register' element={<RegisterPage />} />
            <Route path='/vehicles' element={<AllVehiclesPage />} />
            <Route path='/find-booking' element={<FindBookingPage />} />

            {/* Protected Routes */}
            <Route path='/vehicle-details-book/:vehicleId'
              element={<ProtectedRoute element={<VehicleDetailsBookingPage />} />}
            />
            <Route path='/profile'
              element={<ProtectedRoute element={<ProfilePage />} />}
            />
            <Route path='/edit-profile'
              element={<ProtectedRoute element={<EditProfilePage />} />}
            />

            {/* Admin Routes */}
            <Route path='/admin'
              element={<AdminRoute element={<AdminPage />} />}
            />
            <Route path='/admin/manage-vehicles'
              element={<AdminRoute element={<ManageVehiclePage />} />}
            />
            <Route path='/admin/edit-vehicle/:vehicleId'
              element={<AdminRoute element={<EditVehiclePage />} />}
            />
            <Route path='/admin/add-vehicle'
              element={<AdminRoute element={<AddVehiclePage />} />}
            />
            <Route path='/admin/manage-bookings'
              element={<AdminRoute element={<ManageBookingsPage />} />}
            />
            <Route path='/admin/edit-booking/:bookingCode'
              element={<AdminRoute element={<EditBookingPage />} />}
            />

            {/* Fallback Route */}
            <Route path='*' element={<Navigate to='/login' />} />
          </Routes>
        </div>
        <FooterComponents />
      </div>
    </BrowserRouter>
  );
}

export default App;
