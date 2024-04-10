package it.polimi.ingsw.am52.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The login request object. The method is "login" and the
 * data is a LoginData object.
 */
public class LoginRequest extends ClientRequest<LoginData> {

    //region Constructor

    /**
     * Creates a login request with the specified login data.
     * @param loginData The login data.
     */
    public LoginRequest(LoginData loginData) {
        super(loginData);
    }

    //endregion

    //region Overrides

    /**
     *
     * @return The "login" method.
     */
    @Override
    public String getMethod() {
        return "login";
    }

    //endregion
}
