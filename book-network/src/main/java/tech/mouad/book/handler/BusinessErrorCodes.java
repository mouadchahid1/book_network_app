package tech.mouad.book.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCodes {
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "Not Implemented"),
    ACCOUNT_LOCKED(302, HttpStatus.LOCKED, "Account is locked"),
    INVALID_CREDENTIALS(303, HttpStatus.UNAUTHORIZED, "Invalid credentials"),
    USER_NOT_FOUND(304, HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(305, HttpStatus.CONFLICT, "User already exists"),
    INVALID_TOKEN(306, HttpStatus.UNAUTHORIZED, "Invalid token"),
    TOKEN_EXPIRED(307, HttpStatus.UNAUTHORIZED, "Token expired"),
    TOKEN_NOT_FOUND(308, HttpStatus.NOT_FOUND, "Token not found"),
    INVALID_REQUEST(309, HttpStatus.BAD_REQUEST, "Invalid request"),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "Forbidden"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "Not found"),
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "Bad request"),
    METHOD_NOT_ALLOWED(405, HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"),
    CONFLICT(409, HttpStatus.CONFLICT, "Conflict"),
    UNSUPPORTED_MEDIA_TYPE(415, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported media type"),
    UNPROCESSABLE_ENTITY(422, HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable entity"),
    TOO_MANY_REQUESTS(429, HttpStatus.TOO_MANY_REQUESTS, "Too many requests"),
    INCORRECT_CURRENT_PASSWORD(310, HttpStatus.BAD_REQUEST, "Incorrect current password"),
    NEW_PASSWORD_DOES_NOT_MATCH(311, HttpStatus.BAD_REQUEST, "New password does not match"),
    ACCOUNT_DISABLED(312, HttpStatus.FORBIDDEN, "Account is disabled"),
    BAD_CREDENTIALS(313, HttpStatus.BAD_REQUEST, "The email or the password are incorrect"),
    ;

    private final Integer code;
    private final String description;
    private final HttpStatus httpStatus;


    BusinessErrorCodes(Integer code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
