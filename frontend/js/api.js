// ============================================
// api.js — All API calls to Gateway port 8080
// ============================================

const BASE_URL = "http://localhost:8080";

// ─────────────────────────────────────────
// 🔑 AUTH HELPER — get token from storage
// ─────────────────────────────────────────
function getToken() {
    return localStorage.getItem("token");
}

function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${getToken()}`
    };
}

// ─────────────────────────────────────────
// 👤 USER API
// ─────────────────────────────────────────
const UserAPI = {

    // Register new user
    register: async (name, email, password) => {
        const response = await fetch(`${BASE_URL}/api/users/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, email, password })
        });
        return response.json();
    },

    // Login user → returns JWT token
    login: async (email, password) => {
        const response = await fetch(`${BASE_URL}/api/users/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });
        return response.json();
    }
};

// ─────────────────────────────────────────
// 🏨 HOTEL API
// ─────────────────────────────────────────
const HotelAPI = {

    // Get all hotels
    getAllHotels: async () => {
        const response = await fetch(`${BASE_URL}/api/hotels`, {
            headers: getAuthHeaders()
        });
        return response.json();
    },

    // Get hotel by id
    getHotelById: async (id) => {
        const response = await fetch(`${BASE_URL}/api/hotels/${id}`, {
            headers: getAuthHeaders()
        });
        return response.json();
    },

    // Search hotels by city
    getHotelsByCity: async (city) => {
        const response = await fetch(`${BASE_URL}/api/hotels/city/${city}`, {
            headers: getAuthHeaders()
        });
        return response.json();
    },

    // Get available rooms of a hotel
    getAvailableRooms: async (hotelId) => {
        const response = await fetch(
            `${BASE_URL}/api/hotels/${hotelId}/rooms/available`,
            { headers: getAuthHeaders() }
        );
        return response.json();
    }
};

// ─────────────────────────────────────────
// 📅 BOOKING API
// ─────────────────────────────────────────
const BookingAPI = {

    // Create booking
    createBooking: async (bookingData) => {
        const response = await fetch(`${BASE_URL}/api/bookings`, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(bookingData)
        });
        return response.json();
    },

    // Get user bookings
    getUserBookings: async (email) => {
        const response = await fetch(
            `${BASE_URL}/api/bookings/user/${email}`,
            { headers: getAuthHeaders() }
        );
        return response.json();
    },

    // Cancel booking
    cancelBooking: async (bookingId) => {
        const response = await fetch(
            `${BASE_URL}/api/bookings/${bookingId}/cancel`,
            {
                method: "PUT",
                headers: getAuthHeaders()
            }
        );
        return response.json();
    }
};