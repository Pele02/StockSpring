import React, { useState } from "react";
import "../styles/contact.css";
import Swal from "sweetalert2";

const ContactForm = () => {
  const [result, setResult] = useState("");
  const contactKey = import.meta.env.VITE_APP_KEY_CONTACT_FORM;

  const onSubmit = async (event) => {
    event.preventDefault();
    setResult("Se trimite....");

    const formData = new FormData(event.target);
    formData.append("access_key", contactKey);

    try {
      const response = await fetch("https://api.web3forms.com/submit", {
        method: "POST",
        body: formData,
      });

      const data = await response.json();

      if (data.success) {
        Swal.fire({
          title: "Bună treabă!",
          text: "Mesajul a fost trimis cu succes!",
          icon: "success",
        });
        event.target.reset();
      } else {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Ceva nu a mers bine, încearcă din nou!",
        });
        setResult(data.message);
      }
    } catch (error) {
      console.error("Error", error);
      setResult("An error occurred. Please try again.");
    }
  };

  return (
    <section className="contact">
      <form onSubmit={onSubmit}>
        <h2>Formular Contact</h2>
        <div className="input-box">
          <label>Nume</label>
          <input
            type="text"
            className="field"
            placeholder="Adăugați-vă numele"
            name="name"
            required
          />
        </div>
        <div className="input-box">
          <label>Adresă Email</label>
          <input
            type="text"
            className="field"
            placeholder="Adăugați-vă adresa de Email"
            name="email"
            required
          />
        </div>
        <div className="input-box">
          <label>Mesajul dvs.</label>
          <textarea
            name="message"
            id=""
            className="field message"
            placeholder="Adăugați-vă mesajul pe care doriți să îl trimiteți"
            required
          ></textarea>
        </div>
        <button type="submit">Trimite mesaj</button>
      </form>
    </section>
  );
};

export default ContactForm;
