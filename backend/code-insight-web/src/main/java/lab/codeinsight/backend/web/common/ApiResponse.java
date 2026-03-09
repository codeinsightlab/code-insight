package lab.codeinsight.backend.web.common;

/**
 * Generic API response envelope used by web controllers.
 */
public record ApiResponse<T>(boolean success, T data, String message) {

	/**
	 * Creates a successful response payload.
	 */
	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(true, data, "OK");
	}

	/**
	 * Creates a failed response payload with message.
	 */
	public static <T> ApiResponse<T> fail(String message) {
		return new ApiResponse<>(false, null, message);
	}
}
