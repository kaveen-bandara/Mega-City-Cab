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

    /* registration */
    static async register(registration) {
        const response = await axios.post(`${this.BASE_URL}/auth/register`, registration);
        return response.data;
    }

    /* login */
    static async login(loginDetails) {
        const response = await axios.post(`${this.BASE_URL}/auth/login`, loginDetails);
        return response.data;
    }

    /* USER */

    /* get all users */
    static async getAllUsers() {
        const response = await axios.get(`${this.BASE_URL}/user/all`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* get user by id */
    static async getUserById(userId) {
        const response = await axios.get(`${this.BASE_URL}/user/${userId}`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* delete user */
    static async deleteUser(userId) {
        const response = await axios.delete(`${this.BASE_URL}/user/delete/${userId}`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* get user profile */
    static async getUserProfile() {
        const response = await axios.get(`${this.BASE_URL}/user/profile`, {
            headers: this.getHeader()
        });
        return response.data;
    }

    /* get user booking history */
    static async getUserBookingHistory(userId) {
        const response = await axios.get(`${this.BASE_URL}/user/bookings/${userId}`, {
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
    static async getVehicleById(vehicleId) {
        const result = await axios.get(`${this.BASE_URL}/vehicle/${vehicleId}`);
        return result.data;
    }

    /* delete vehicle */
    static async deleteVehicle(vehicleId) {
        const result = await axios.delete(`${this.BASE_URL}/vehicle/delete/${vehicleId}`, {
            headers: this.getHeader()
        });
        return result.data;
    }

    /* update vehicle */
    static async updateVehicleDetails(vehicleId, formData) {
        const result = await axios.put(`${this.BASE_URL}/vehicle/update/${vehicleId}`, formData, {
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

    /* get available vehicles by date and vehicle type */
    static async getAvailableVehiclesByDateAndVehicleType(startDate, endDate, vehicleType) {
        const result = await axios.get(
            `${this.BASE_URL}/vehicle/available-vehicles-by-date-and-type?startDate=${startDate}&endDate=${endDate}&vehicleType=${vehicleType}`
        );
        return result.data;
    }

    /* BOOKING */
    
    /* save new booking */
    static async saveBooking(vehicleId, userId, booking) {
        const response = await axios.post(`${this.BASE_URL}/booking/book/${vehicleId}/${userId}`, booking, {
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
    static async getBookingByConfirmationCode(confirmationCode) {
        const result = await axios.get(`${this.BASE_URL}/booking/${confirmationCode}`);
        return result.data;
    }

    /* cancel booking */
    static async cancelBooking(bookingId) {
        const result = await axios.delete(`${this.BASE_URL}/booking/cancel/${bookingId}`, {
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
