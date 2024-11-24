import React, { useState } from "react";
import Modal from "./Modal";

const Footer = () => {
  const [showPolicyModal, setShowPolicyModal] = useState(false);
  const [showTermsModal, setShowTermsModal] = useState(false);

  return (
    <footer className="text-center text-light mt-5">
      <div className="container text-center">
        <p>
          &copy; 2024 Tot conținutul de pe acest site este protejat prin
          drepturi de autor.
        </p>
        <div className="d-flex justify-content-center">
          <button
            type="button"
            className="btn btn-link text-light"
            onClick={() => setShowPolicyModal(true)}
          >
            Politica de Confidențialitate
          </button>
          <span className="text-light" style={{ marginTop: "-6px" }}>
            |
          </span>
          <button
            type="button"
            className="btn btn-link text-light"
            onClick={() => setShowTermsModal(true)}
          >
            Termeni și Condiții
          </button>
        </div>
      </div>

      <Modal
        show={showPolicyModal}
        title="Politica de Confidențialitate"
        content={
          <>
            <p>Colectarea informațiilor</p>
            <p>
              {" "}
              Colectăm informații personale atunci când vizitați site-ul nostru
              sau utilizați anumite funcționalități, precum formularele de
              contact sau crearea unui cont. Informațiile colectate includ
              numele, adresa de email și alte date relevante.
            </p>
            <p>Utilizarea informațiilor</p>
            <p>
              Informațiile pe care le colectăm sunt utilizate pentru a
              îmbunătăți serviciile noastre și pentru a răspunde solicitărilor
              dumneavoastră. Nu vom distribui sau vinde datele dumneavoastră
              personale fără consimțământul explicit al acestora.
            </p>
            <p>Securitatea datelor</p>
            <p>
              Implementăm măsuri de securitate pentru a proteja informațiile
              dumneavoastră personale, inclusiv criptarea datelor și mecanisme
              de protecție a accesului.
            </p>
            <p>Drepturile utilizatorilor</p>
            <p>
              Aveți dreptul de a solicita accesul, corectarea sau ștergerea
              datelor personale pe care le-am colectat. Contactați-ne pentru a
              exercita aceste drepturi.
            </p>
            <p>Modificări ale politicii de confidențialitate</p>
            <p>
              Politica de confidențialitate poate fi actualizată periodic. Orice
              modificări vor fi publicate pe această pagină și vor intra în
              vigoare imediat ce sunt disponibile.
            </p>
          </>
        }
        onClose={() => setShowPolicyModal(false)}
      />
      <Modal
        show={showTermsModal}
        title="Termeni și Condiții"
        content={
          <>
            <p>Acceptarea Termenilor</p>
            <p>
              Prin accesarea și utilizarea acestui site, acceptați termenii și
              condițiile prezentate. Dacă nu sunteți de acord cu aceștia, vă
              rugăm să nu utilizați site-ul nostru.
            </p>
            <p>Modificări ale Termenilor</p>
            <p>
              Ne rezervăm dreptul de a modifica acești termeni în orice moment.
              Vă recomandăm să verificați periodic această secțiune pentru a fi
              la curent cu eventualele modificări.
            </p>
            <p>Drepturi de autor</p>
            <p>
              Toate materialele de pe acest site sunt protejate prin drepturi de
              autor. Utilizarea acestora fără permisiune este interzisă.
            </p>
            <p>Utilizarea site-ului</p>
            <p>
              Site-ul este destinat utilizării doar în scopuri legale. Este
              interzis să folosiți site-ul pentru activități ilegale sau care ar
              putea dăuna altor utilizatori.
            </p>
            <p>Limitarea răspunderii</p>
            <p>
              Nu ne asumăm responsabilitatea pentru daunele cauzate de
              utilizarea site-ului. Utilizatorii sunt responsabili de protejarea
              echipamentelor lor împotriva virușilor și altor software-uri
              dăunătoare.
            </p>
          </>
        }
        onClose={() => setShowTermsModal(false)}
      />
    </footer>
  );
};

export default Footer;
