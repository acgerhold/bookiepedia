import "./shim";
import React from "react";
import ReactDOM from "react-dom/client";
import GetEvents from "./pages/GetEvents";
import { Amplify } from "aws-amplify";

// Configure Amplify with Cognito settings
Amplify.configure({
  Auth: {
    region: "us-east-2", // Your AWS region (from pipeline.yaml: REGION)
    userPoolId: import.meta.env.VITE_COGNITO_USER_POOL_ID,
    userPoolWebClientId: import.meta.env.VITE_COGNITO_USER_POOL_CLIENT_ID,
    mandatorySignIn: false, // Set to true if you require users to sign in
    oauth: {
      domain: import.meta.env.VITE_COGNITO_DOMAIN,
      scope: ["email", "profile", "openid"],
      redirectSignIn: import.meta.env.VITE_COGNITO_REDIRECT_SIGNIN,
      redirectSignOut: import.meta.env.VITE_COGNITO_REDIRECT_SIGNOUT,
      responseType: "code", // For Cognito Hosted UI
    },
  },
  API: {
    endpoints: [
      {
        name: "bookiepedia-api",
        endpoint: import.meta.env.VITE_API_BASE_URL,
      },
    ],
  },
});

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <GetEvents />
  </React.StrictMode>
);