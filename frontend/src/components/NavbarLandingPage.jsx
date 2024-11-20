import React from "react";
import { NavLink } from "react-router-dom";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "../styles/NavbarLandingPage.css";

const NavbarLandingPage = () => {
  const activeLink = ({ isActive }) =>
    isActive ? "nav-link active-link" : "nav-link";

  return (
    <>
      <nav className="navbar navbar-expand-lg ">
        <p className="navbar-brand">StockSpring</p>
        <div className="navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item">
              <NavLink className={activeLink} to="">
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className={activeLink} to="/functionality">
                Funcționalități
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className={activeLink} to="/support">
                Suport
              </NavLink>
            </li>
          </ul>
          <div className="ms-auto d-flex">
            <Link to="/login">
              <button type="button" className="btn btn-outline-light">
                Autentificare
              </button>
            </Link>
            <Link to="/register">
              <button type="button" className="btn btn-light">
                Înregistrare
              </button>
            </Link>
          </div>
        </div>
      </nav>
    </>
  );
};

export default NavbarLandingPage;
