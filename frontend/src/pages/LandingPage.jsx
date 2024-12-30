import React from "react";
import NavbarLandingPage from "../components/NavbarLandingPage";
import ContentLandingPage from "../components/ContentLandingPage";
import Footer from "../components/Footer";
import "../styles/landingPage.css";

function LandingPage() {
  return (
    <>
      <NavbarLandingPage />
      <ContentLandingPage />
      <Footer />
    </>
  );
}

export default LandingPage;
