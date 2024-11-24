import React from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import landingPageImg from "../assets/images/landingPage.png";
import "../styles/ContentLandingPage.css";

const ContentLandingPage = () => {
  return (
    <>
      <div className="container-fluid">
        <div className="row">
          <div className="col">
            <div className="header-text">
              <h1>Urmărește investițiile tale</h1>
              <p>simplu și rapid cu ajutorul nostru</p>
            </div>
            <ul className="list-unstyled">
              <li>Fii la curent cu performanța acțiunilor tale</li>
              <li>Monitorizează evoluția pieței</li>
              <li>Accesează rapoarte detaliate</li>
            </ul>
            <div className="row justify-content-center btn-row">
              <div className="col-4">
                <Link to="/register">
                  <button
                    type="button"
                    className="btn btn-primary btn-lg content-page-btn"
                  >
                    Începe acum
                  </button>
                </Link>
              </div>
              <div className="col-4">
                <Link to="/login">
                  <button
                    type="button"
                    className="btn btn-outline-secondary btn-sm content-page-btn"
                  >
                    Loghează-te
                  </button>
                </Link>
              </div>
            </div>
          </div>

          <div className="col">
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
