import { defineConfig, loadEnv } from "vite";
import react from "@vitejs/plugin-react";
import { Buffer } from "buffer";

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), "VITE_");

  return {
    plugins: [react()],
    define: {
      global: "window",
      "process.env": {
        API_BASE_URL: env.VITE_API_BASE_URL,
        COGNITO_DOMAIN: env.VITE_COGNITO_DOMAIN,
        COGNITO_USER_POOL_ID: env.VITE_COGNITO_USER_POOL_ID,
        COGNITO_USER_POOL_CLIENT_ID: env.VITE_COGNITO_USER_POOL_CLIENT_ID,
        COGNITO_REDIRECT_SIGNIN: env.VITE_COGNITO_REDIRECT_SIGNIN,
        COGNITO_REDIRECT_SIGNOUT: env.VITE_COGNITO_REDIRECT_SIGNOUT,
        COGNITO_REGION: env.VITE_COGNITO_REGION,
        ODDS_API_KEY: env.VITE_ODDS_API_KEY,
        API_LOCATION: process.env.API_LOCATION || "local", 
      },
    },
    resolve: {
      alias: {
        buffer: "buffer",
      },
    },
    optimizeDeps: {
      include: ["buffer"],
    },
  };
});