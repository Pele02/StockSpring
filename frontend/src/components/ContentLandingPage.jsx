import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import landingPageImg from "../assets/images/landingPage.png";

const ContentLandingPage = () => {
  return (
    <>
      <div class="container">
        <div class="row">
          <div class="col">
            <p>Left Side</p>
          </div>
          <div class="col">
            <img
              src={landingPageImg}
              className=" .img-fluid. max-width: 100%"
              alt="Landing Page"
            />
          </div>
        </div>
      </div>
    </>
  );
};

export default ContentLandingPage;
