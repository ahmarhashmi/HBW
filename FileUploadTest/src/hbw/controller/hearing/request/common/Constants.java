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

	/** maximum allowed number of evidences to be uploaded */
	public static final int MAX_NUMBER_OF_EVIDENCES = 50;
	
	/** maximum allowed size of total uploaded evidences */
	public static final int MAX_TOTAL_SIZE_OF_EVIDENCE = (int) (Math.pow(1024, 2) * 20);// 20MB
}
