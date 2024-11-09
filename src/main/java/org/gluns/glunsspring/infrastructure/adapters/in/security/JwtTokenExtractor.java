package org.gluns.glunsspring.infrastructure.adapters.in.security;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Service for handling JWT tokens
 */
@Service
public class JwtTokenExtractor {

    // TODO: Make it async and a model user class
    public String getId(String authHeader) {
        String userId = "not found";
        String userName = "not found";
        DecodedJWT jwt = JWT.decode(authHeader.replace("Bearer", "").trim());

        if (jwt.getClaim("sub") != null && !jwt.getClaim("sub").isNull()) {
            userId = jwt.getClaim("sub").asString();
        }

        if (jwt.getClaim("preferred_username") != null && !jwt.getClaim("preferred_username").isNull()) {
            userName = jwt.getClaim("preferred_username").asString();
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();

        json.put("userId", userId);
        json.put("userName", userName);
        return userId;
    }

}
