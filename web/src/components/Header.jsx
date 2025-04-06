import BookiepediaClient from "../api/bookiepediaClient";
import { useEffect, useState } from "react";
import "../css/global.css";

const Header = ({ client }) => {
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    const fetchUser = async () => {
      if (client) {
        const user = await client.getIdentity();
        setCurrentUser(user);
      }
    };
    fetchUser();
  }, [client]);

  return (
    <header className="header" id="header">
      <div className="site-title">
        <a href="/" className="header_home">
          Bookiepedia
        </a>
      </div>
      <div className="user">
        {currentUser ? (
          <a
            href="#"
            onClick={() => client.logout()}
            className="button"
          >
            Logout: {currentUser.name}
          </a>
        ) : (
          <a
            href="#"
            onClick={() => client.login()}
            className="button"
          >
            Login
          </a>
        )}
      </div>
    </header>
  );
};

export default Header;