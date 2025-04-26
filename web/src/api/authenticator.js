// web/src/authenticator.js
import BindingClass from "../util/bindingClass";
import { Auth } from "aws-amplify";

export default class Authenticator extends BindingClass {
  constructor() {
    super();

    const methodsToBind = ["getCurrentUserInfo"];
    this.bindClassMethods(methodsToBind, this);

    this.configureCognito();
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
  }

  async isUserLoggedIn() {
    try {
      await Auth.currentAuthenticatedUser();
      return true;
    } catch {
      return false;
    }
  }

  async getUserToken() {
    const cognitoSession = await Auth.currentSession();
    return cognitoSession.getIdToken().getJwtToken();
  }


  // Adding this didnt do anything
  async login() {
    await Auth.federatedSignIn({ provider: 'COGNITO' });
  }

  async logout() {
    await Auth.signOut();
  }

  // Use the trimming technique from before, but not the one that separates after the '.'
  // use the first option that sort of builds the string out
  // amplify needs the full domain name in its config.
  // or you can just not copy and paste the full thing and change what the local deploy outputs i guess
  configureCognito() {
    const cognitoDomainPrefix = process.env.COGNITO_DOMAIN.replace(/\.auth\.us-east-2\.amazoncognito\.com$/, '');
    const config = {
      region: process.env.COGNITO_REGION,
      userPoolId: process.env.COGNITO_USER_POOL_ID,
      userPoolWebClientId: process.env.COGNITO_USER_POOL_CLIENT_ID,
      oauth: {
        domain: cognitoDomainPrefix,
        redirectSignIn: process.env.COGNITO_REDIRECT_SIGNIN,
        redirectSignOut: process.env.COGNITO_REDIRECT_SIGNOUT,
        scope: ["email", "openid", "phone", "profile"],
        responseType: "code",
      },
    };
    
    Auth.configure(config);
  }
}