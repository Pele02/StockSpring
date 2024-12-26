import React, { useState } from "react";
import "../styles/register.css";

const Register = () => {
  const [formData, setFormData] = useState({
    prenume: "",
    nume: "",
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

  const handleSubmit = (e) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      alert("Parolele nu se potrivesc!");
      return;
    }
    console.log(formData);
  };

  return (
    <section className="register">
      <h2 className="header">StockSpring</h2>
      <form onSubmit={handleSubmit}>
        <div className="input-name">
          <div className="input-box prenume">
            <label htmlFor="prenume">Prenume</label>
            <input
              type="text"
              className="field"
              placeholder="Adăugați-vă prenumele"
              name="prenume"
              value={formData.prenume}
              onChange={handleChange}
              required
            />
          </div>
          <div className="input-box nume">
            <label htmlFor="nume">Nume</label>
            <input
              type="text"
              className="field"
              placeholder="Adăugați-vă numele de familie"
              name="nume"
              value={formData.nume}
              onChange={handleChange}
              required
            />
          </div>
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
        <button type="submit">Înregistrare</button>
      </form>
    </section>
  );
};

export default Register;
