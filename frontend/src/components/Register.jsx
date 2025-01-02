import React, { useState } from "react";
import axios from "axios";
import "../styles/register.css";
import Swal from "sweetalert2";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent the form from submitting and refreshing the page

    if (formData.password !== formData.confirmPassword) {
      Swal.fire({
        icon: "error",
        title: "Parolele nu se potrivesc!",
        text: "Te rugăm să verifici parolele.",
      });
      return;
    }

    // Send the data to the backend when the button is clicked
    try {
      const response = await axios.post(
        "http://localhost:8081/register",
        formData
      );
      console.log(response.data); // Handle the response from the backend
      Swal.fire({
        icon: "success",
        title: "Registrare reușită!",
        text: "Contul tău a fost creat cu succes. Acum te poți conecta!",
        confirmButtonText: "OK",
      }).then((result) => {
        if (result.isConfirmed) {
          // Redirect to login page or another page
          navigate("/login");
        }
      });
    } catch (error) {
      console.error("Error:", error.response.data);
      if (error.response.data.includes("already exists"))
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Numele de utilizator sau emailul este folosit!",
        });
      else
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "A apărut o eroare la înregistrare!",
        });
    }
  };

  return (
    <section className="register">
      <h2 className="header">StockSpring</h2>
      <form onSubmit={handleSubmit}>
        <div className="input-box username">
          <label htmlFor="username">Numele de utilizator</label>
          <input
            type="text"
            className="field"
            placeholder="Adăugați-vă numele de utilizator"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
          />
        </div>
        <div className="input-box">
          <label htmlFor="email">Adresă Email</label>
          <input
            type="email"
            className="field"
            placeholder="Adăugați-vă adresa de Email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="input-box">
          <label htmlFor="password">Parolă</label>
          <input
            type="password"
            name="password"
            className="field password"
            placeholder="Adăugați-vă parola"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>
        <div className="input-box">
          <label htmlFor="confirmPassword">Confirmare Parolă</label>
          <input
            type="password"
            name="confirmPassword"
            className="field password"
            placeholder="Confirmați parola"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
          />
        </div>
        <button id="register" type="submit">
          Înregistrare
        </button>
      </form>
    </section>
  );
};

export default Register;
