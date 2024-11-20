import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../styles/NavbarLandingPage.css";

const NavbarLandingPage = () => {
  return (
    <>
      <nav className="navbar navbar-expand-lg ">
        <p className="navbar-brand">StockSpring</p>
        <div className="navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item">
              <a className="nav-link" href="#">
                Home
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#">
                Funcționalități
              </a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#">
                Suport
              </a>
            </li>
          </ul>
          <div className="ms-auto d-flex">
            <button type="button" className="btn btn-outline-light">
              Login
            </button>
            <button type="button" className="btn btn-light">
              Register
            </button>
          </div>
        </div>
      </nav>
    </>
  );
};

export default NavbarLandingPage;
