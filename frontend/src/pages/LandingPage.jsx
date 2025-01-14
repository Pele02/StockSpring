import React, { useEffect } from "react";
import NavbarLandingPage from "../components/NavbarLandingPage";
import ContentLandingPage from "../components/ContentLandingPage";
import Footer from "../components/Footer";
import "../styles/landingPage.css";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function LandingPage() {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("authToken");

    if (token) {
      try {
        // Decode the JWT token to check if it's expired
        const decodedToken = jwtDecode(token);
        const currentTime = Date.now() / 1000;

        if (decodedToken.exp > currentTime) {
          // Token is valid, redirect to dashboard
          navigate("/dashboard");
        } else {
          // Token is expired, you can clear it and let the user log in again
          localStorage.removeItem("authToken");
        }
      } catch (error) {
        // If the token is invalid, remove it and let the user log in again
        localStorage.removeItem("authToken");
      }
    }
  }, [navigate]);

  return (
    <>
      <NavbarLandingPage />
      <ContentLandingPage />
      <Footer />
    </>
  );
}

export default LandingPage;
