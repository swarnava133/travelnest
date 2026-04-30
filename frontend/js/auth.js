// ============================================
// auth.js — Login, Register, JWT management
// ============================================

// Save user data after login
function saveUserData(token, email, role) {
    localStorage.setItem("token", token);
    localStorage.setItem("email", email);
    localStorage.setItem("role", role);
}

// Get logged in user email
function getUserEmail() {
    return localStorage.getItem("email");
}

// Check if user is logged in
function isLoggedIn() {
    return localStorage.getItem("token") !== null;
}

// Logout — clear all data
function logout() {
    localStorage.clear();
    window.location.href = "login.html";
}

// Protect page — redirect to login if not logged in
function requireAuth() {
    if (!isLoggedIn()) {
        window.location.href = "login.html";
    }
}

// Update navbar based on login status
function updateNavbar() {
    const navLinks = document.getElementById("nav-links");
    if (!navLinks) return;

    if (isLoggedIn()) {
        navLinks.innerHTML = `
            <a href="hotels.html">Hotels</a>
            <a href="dashboard.html">My Bookings</a>
            <a href="#" onclick="logout()">Logout</a>
        `;
    } else {
        navLinks.innerHTML = `
            <a href="hotels.html">Hotels</a>
            <a href="login.html">Login</a>
            <a href="register.html">Register</a>
        `;
    }
}