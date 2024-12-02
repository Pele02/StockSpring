import React, { useState } from "react";
import "../styles/contact.css";
import Swal from "sweetalert2";

const ContactForm = () => {
  const [result, setResult] = useState("");
  const contactKey = import.meta.env.VITE_APP_KEY_CONTACT_FORM;

  const onSubmit = async (event) => {
    event.preventDefault();
    setResult("Sending....");

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
          title: "Good job!",
          text: "The message was send!",
          icon: "success",
        });
        event.target.reset();
      } else {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Something went wrong!",
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
        <h2>Contact Form</h2>
        <div className="input-box">
          <label>Full Name</label>
          <input
            type="text"
            className="field"
            placeholder="Enter your name"
            name="name"
            required
          />
        </div>
        <div className="input-box">
          <label>Email Address</label>
          <input
            type="text"
            className="field"
            placeholder="Enter your email"
            name="email"
            required
          />
        </div>
        <div className="input-box">
          <label>Your Message</label>
          <textarea
            name="message"
            id=""
            className="field message"
            placeholder="Enter your message"
            required
          ></textarea>
        </div>
        <button type="submit">Send Message</button>
      </form>
    </section>
  );
};

export default ContactForm;
