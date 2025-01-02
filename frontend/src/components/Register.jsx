import React, { useState } from "react";
import axios from "axios";
import "../styles/register.css";

const Register = () => {
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
      alert("Parolele nu se potrivesc!");
      return;
    }

    // Send the data to the backend when the button is clicked
    try {
      const response = await axios.post(
        "http://localhost:8081/register",
        formData
      );
      console.log(response.data); // Handle the response from the backend
      alert("Registrare reușită!");
    } catch (error) {
      console.error("Error:", error.response.data);
      if (error.response.data.includes("already exists"))
        alert("Numele de utilizator sau emailul este folosit!");
      else alert("A apărut o eroare la înregistrare.");
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
