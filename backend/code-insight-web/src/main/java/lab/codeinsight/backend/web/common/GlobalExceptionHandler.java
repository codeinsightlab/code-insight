package lab.codeinsight.backend.web.common;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centralized exception translation for REST endpoints.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles bean validation errors from request-body binding.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors().stream().findFirst()
				.map(error -> error.getField() + " " + error.getDefaultMessage()).orElse("Invalid request");
		return ApiResponse.fail(message);
	}

	/**
	 * Handles parameter-level validation failures.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<String> handleConstraintViolationException(ConstraintViolationException ex) {
		return ApiResponse.fail(ex.getMessage());
	}

	/**
	 * Handles business validation exceptions expressed as illegal arguments.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ApiResponse.fail(ex.getMessage());
	}

	/**
	 * Handles all uncaught server exceptions.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse<String> handleException(Exception ex) {
		return ApiResponse.fail(ex.getMessage());
	}
}
