/**
 * 
 */
package hbw.controller.hearing.request.common;

/**
 * @author Ahmar Nadeem
 * 
 *         This class contains all the constants defined to be used in the
 *         system.
 */
public final class Constants {

	/** The array representing the allowed types of evidences */
	public static final String[] ALLOWED_IMAGE_TYPES = { "image/pjpeg",
			"image/jpeg", "image/jpg", "image/tiff", "image/bmp",
			"application/pdf" };

	public static final String BACK_SLASH = "\\";

	public static final String FRONT_SLASH = "/";

	public static final String VIOLATION_NUMBER = "violationNumber";

	public static final String VIOLATION_INFO = "VIOLATION_INFO";

	public static final String VIOLATION_IN_SYSTEM = "VIOLATION_IN_SYSTEM";

	public static final String PDF = "application/pdf";

	public static final String TIFF = "image/tiff";

	public static final String SUMMON = "summon";

	public static final String JWT_SECRET_KEY = "TheNewYorkCityDepartmentOfFinance";

	public static final String RETURN_CODE_ERROR = "2";

	public static final String RETURN_CODE = "ReturnCode";

	public static final String UTF8 = "UTF-8";

	public static final String POST = "POST";

	public static final String GET = "GET";

	public static final String PAGE_COUNTS = "PAGE_COUNTS";

	public static final String AES_KEY = "HBWNonCommercial"; // 128 bit key

	public static final String AES_INIT_VECTOR = "AbcdefghijkLMNOP"; // 16 bytes
																		// IV
}