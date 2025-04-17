package bookiepedia.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents a generic, authenticated "APIGateway" request made to a lambda function.
 * @param <T> The type of the concrete request that should be created from this LambdaRequest
 */
public class AuthenticatedLambdaRequest<T> extends LambdaRequest<T> {

    private static final long serialVersionUID = -6364947496818289027L;

    /**
     * Use the given converter to create an instance of T from the claims included in the request's JWT token.
     * @param converter Contains the conversion code
     * @return A instance of T that contains data from the request's claims.
     */
    public T fromUserClaims(Function<Map<String, String>, T> converter) {
        try {
            return converter.apply(getClaims());
        } catch (Exception e) {
            throw new RuntimeException("Unable to get user information from request.", e);
        }
    }

    // New getClaims() method to handle unsafe/unchecked operation
    @SuppressWarnings("unchecked")
    private Map<String, String> getClaims() throws JsonProcessingException {
        if (System.getenv().get("AWS_SAM_LOCAL") == null) {
            // Get the claims from the authorizer context
            Object claimsObj = super.getRequestContext().getAuthorizer().get("claims");
            if (claimsObj instanceof Map) {
                // Verify that the map contains String keys and String values
                Map<?, ?> rawClaims = (Map<?, ?>) claimsObj;
                for (Map.Entry<?, ?> entry : rawClaims.entrySet()) {
                    if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) {
                        throw new IllegalStateException("Claims map contains non-String key or value: " + entry);
                    }
                }
                return (Map<String, String>) rawClaims;
            } else {
                throw new IllegalStateException("Authorizer claims are not a Map: " + claimsObj);
            }
        } else {
            // Local SAM environment: decode claims from the Authorization header
            return getClaimsFromAuthHeader(super.getHeaders().get("Authorization"));
        }
    }

//    This code was not letting the project build, was performing an unsafe/unchecked operation:

//    super.getRequestContext().getAuthorizer().get("claims") returns an Object (as per the AWS Lambda Java runtime API
//      for APIGatewayProxyRequestEvent).

//    Were casting this Object directly to Map<String, String>, which is a generic type. Java’s type system cannot
//      verify at compile time that the Object is indeed a Map<String, String>, so it issues an "unchecked cast" warning.

//    This is unsafe because if the claims object is not a Map<String, String> at runtime
//      (e.g., if it’s a Map<String, Object> or something else), you’ll get a ClassCastException

    // Original
//    private Map<String, String> getClaims() throws JsonProcessingException {
//        // If we are running locally using SAM, we have to manually decode claims from the JWT Token.
//        return System.getenv().get("AWS_SAM_LOCAL") == null ?
//                (Map<String, String>) super.getRequestContext().getAuthorizer().get("claims") :
//                getClaimsFromAuthHeader(super.getHeaders().get("Authorization"));
//    }

    private Map<String, String> getClaimsFromAuthHeader(final String authorizationHeader)
            throws JsonProcessingException {
        String jwt = getJWTFromAuthHeader(authorizationHeader);
        return getClaimsFromJWT(jwt);
    }

    private String getJWTFromAuthHeader(final String authorizationHeader) {
        return authorizationHeader.split("\\s")[1];
    }

    private Map<String, String> getClaimsFromJWT(final String jwt) throws JsonProcessingException {
        Base64.Decoder decoder = Base64.getDecoder();

        String[] sections = jwt.split("\\.");
        String payload = new String(decoder.decode(sections[1]));

        return super.MAPPER.readValue(payload, new TypeReference<HashMap<String, String>>() {
        });
    }
}
