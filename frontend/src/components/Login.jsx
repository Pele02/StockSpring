import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Swal from "sweetalert2";
import "../styles/login.css";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.username || !formData.password) {
      setErrorMessage("Completează toate câmpurile.");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8081/auth/login",
        formData
      );

      localStorage.setItem("authToken", response.data.token);

      Swal.fire({
        icon: "success",
        title: "Autentificare reușită!",
        text: "Bine ai venit în contul tău!",
      }).then((result) => {
        if (result.isConfirmed) {
          navigate("/dashboard");
        }
      });
    } catch (error) {
      console.error("Login Error:", error);
      if (error.response && error.response.data) {
        setErrorMessage(
          error.response.data.message ||
            "Numele de utilizator sau parola sunt greșite!"
        );
      } else {
        setErrorMessage("A apărut o eroare necunoscută.");
      }
    }
  };

  return (
    <section className="login-container">
      <h2 className="login-header">StockSpring</h2>
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="login-input-box">
          <label htmlFor="username" className="login-label">
            Numele de utilizator
          </label>
          <input
            type="text"
            className="login-field"
            placeholder="Adăugați-vă numele de utilizator"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
          />
        </div>
        <div className="login-input-box">
          <label htmlFor="password" className="login-label">
            Parolă
          </label>
          <input
            type="password"
            name="password"
            className="login-field"
            placeholder="Adăugați-vă parola"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>

        {errorMessage && <p className="login-error-message">{errorMessage}</p>}

        <button className="login-btn" type="submit">
          Conectează-te
        </button>

        <Link to="/forgot-password" className="login-forgot-password">
          Ai uitat parola?
        </Link>
      </form>
    </section>
  );
};

export default Login;
