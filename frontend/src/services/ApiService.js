import axios from 'axios';

export default class ApiService {

    static BASE_URL = 'http://localhost:4040'

    static getHeader() {
        const token = localStorage.getItem('token');
        return {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
        };
    }

    /* AUTH */

    /* admin login */
    static async loginAdmin(adminLoginDetails) {
        const response = await axios.post(`${this.BASE_URL}/auth/admin`, adminLoginDetails);
        return response.data;
    }

    /* customer registration */
    static async register(registration) {
        const response = await axios.post(`${this.BASE_URL}/auth/register`, registration);
        return response.data;
    }

    /* customer login */
    static async login(customerLoginDetails) {
        const response = await axios.post(`${this.BASE_URL}/auth/login`, customerLoginDetails);
        return response.data;
    }

    /* CUSTOMER */

    /* get all customers */
    static async getAllCustomers() {
        const response = await axios.get(`${this.BASE_URL}/customer/all`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* get customer by id */
    static async getCustomerById(registrationNumber) {
        const response = await axios.get(`${this.BASE_URL}/customer/${registrationNumber}`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* delete customer */
    static async deleteCustomer(registrationNumber) {
        const response = await axios.delete(`${this.BASE_URL}/customer/delete/${registrationNumber}`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* get customer profile */
    static async getCustomerProfile() {
        const response = await axios.get(`${this.BASE_URL}/customer/profile`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* get customer booking history */
    static async getCustomerBookingHistory(registrationNumber) {
        const response = await axios.get(`${this.BASE_URL}/customer/bookings/${registrationNumber}`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* VEHICLE */

    /* add new vehicle */
    static async addNewVehicle(formData) {
        const result = await axios.post(`${this.BASE_URL}/vehicle/add`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    }

    /* get all vehicle types */
    static async getAllVehicleTypes() {
        const response = await axios.get(`${this.BASE_URL}/vehicle/types`);
        return response.data;
    }

    /* get all vehicles */
    static async getAllVehicles() {
        const result = await axios.get(`${this.BASE_URL}/vehicle/all`);
        return result.data;
    }

    /* get vehicle by id */
    static async getVehicleById(cabId) {
        const result = await axios.get(`${this.BASE_URL}/vehicle/${cabId}`);
        return result.data;
    }

    /* delete vehicle */
    static async deleteVehicle(cabId) {
        const result = await axios.delete(`${this.BASE_URL}/vehicle/delete/${cabId}`, {
            headers: this.getHeader()
        });
        return result.data;
    }

    /* update vehicle */
    static async updateVehicleDetails(cabId, formData) {
        const result = await axios.put(`${this.BASE_URL}/vehicle/update/${cabId}`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    }

    /* get all available vehicles */
    static async getAllAvailableVehicles() {
        const result = await axios.get(`${this.BASE_URL}/vehicle/all-available-vehicles`);
        return result.data;
    }

    /* get available vehicles by date, time and vehicle type */
    static async getAvailableVehiclesByDateTimeAndVehicleType(pickupDateTime, vehicleType) {
        const result = await axios.get(
            `${this.BASE_URL}/vehicle/available-vehicles-by-datetime-and-type?pickupDateTime=${pickupDateTime}&vehicleType=${vehicleType}`
        );
        return result.data;
    }

    /* BOOKING */
    
    /* save new booking */
    static async saveBooking(cabId, registrationNumber, booking) {
        const response = await axios.post(`${this.BASE_URL}/booking/book/${cabId}/${registrationNumber}`, booking, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* get all bookings */
    static async getAllBookings() {
        const result = await axios.get(`${this.BASE_URL}/booking/all`, {
            headers: this.getHeader()
        });
        return result.data;
    }

    /* get booking by confirmation code */
    static async findBookingByConfirmationCode(confirmationCode) {
        const result = await axios.get(`${this.BASE_URL}/booking/${confirmationCode}`);
        return result.data;
    }

    /* cancel booking */
    static async cancelBooking(bookingNumber) {
        const result = await axios.delete(`${this.BASE_URL}/booking/cancel/${bookingNumber}`, {
            headers: this.getHeader()
        });
        return result.data;
    }

    /* AUTHENTICATION CHECKER */

    static logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
    }

    static isAuthenticated() {
        const token = localStorage.getItem('token');
        return !!token;
    }

    static isAdmin() {
        const role = localStorage.getItem('role');
        return role === 'ADMIN';
    }

    static isCustomer() {
        const role = localStorage.getItem('role');
        return role === 'CUSTOMER';
    }
}
