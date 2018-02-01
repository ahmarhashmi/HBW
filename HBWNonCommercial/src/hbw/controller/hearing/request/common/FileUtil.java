package hbw.controller.hearing.request.common;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
//import com.ibm.ws.util.Base64;
import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author Ahmar Nadeem
 * 
 *         A utility class for handling files like to check animated gif or not,
 *         display/store an image etc.
 * 
 */
public final class FileUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean isAnimatedGIF(File file) throws IOException {
	if (file == null || !file.canRead()) {
	    throw new RuntimeException("Not a valid file or cannot be read.");
	}
	ImageReader is = ImageIO.getImageReadersBySuffix("GIF").next();
	ImageInputStream iis = ImageIO.createImageInputStream(file);
	is.setInput(iis);
	int images = is.getNumImages(true);
	iis.close();
	return images > 1;
    }

    /**
     * 
     * @param request
     * @return
     */
    public static File validateAndGetEvidenceUploadPath(HttpServletRequest request) {
	HttpSession session = request.getSession();
	String violationNumber = (String) session.getAttribute(Constants.VIOLATION_NUMBER);
	String basePath = Resource.EVIDENCE_UPLOAD_LOCATION.getValue();
	if (basePath != null && !(Constants.BACK_SLASH.equals(basePath.substring(basePath.length() - 1))
		|| Constants.FRONT_SLASH.equals(basePath.substring(basePath.length() - 1)))) {
	    basePath += File.separator;
	}

	/**
	 * If the directory with the name of violation number already contains
	 * files/folders other than the current session, we need to delete those files
	 * to keep the directory clean.
	 */
	File pathTillViolationNumber = new File(basePath + violationNumber.trim());
	if (pathTillViolationNumber.exists()) {
	    for (File child : pathTillViolationNumber.listFiles()) {
		if (child.getName().equals(session.getId())) {
		    continue;
		}

		try {
		    FileUtils.forceDelete(child);
		} catch (IOException e) {
		    LOGGER.error("Previously uploaded files couldn't be deleted. Exception: "+ e);
		}
	    }
	}

	/**
	 * Now build the path where we need to actually store the files.
	 */
	File evidencePath = new File(
		basePath + violationNumber.trim() + File.separator + session.getId() + File.separator);
	if (!evidencePath.exists()) {
	    evidencePath.mkdirs();
	}

	return evidencePath;
    }

    /**
     * A utility function to return file extension if the provided file name is
     * valid. Returns the same string if it doesn't contain a dot or is null.
     * 
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
	if (fileName != null && fileName.contains(".")) {
	    return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	return fileName;
    }

    /**
     * Utility function to encode file to a base64 byte array converted to a string.
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String encodeFileToBase64Binary(File file) throws IOException {
	byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
	return new String(encoded);
	//return Base64.encode(FileUtils.readFileToByteArray(file));
    }

    /**
     * Utility to check if the file is valid pdf file or not.
     * 
     * @param data
     * @return
     */
    public static boolean isPDF(byte[] data) {

	// FIXME: need to find a perfect solution to validate pdf content.
	// Collection mimeTypes = MimeUtil.getMimeTypes(data);
	// MimeDetector md = new OpendesktopMimeDetector();

	if (data != null && data.length > 4 && data[0] == 0x25 && // %
		data[1] == 0x50 && // P
		data[2] == 0x44 && // D
		data[3] == 0x46 && // F
		data[4] == 0x2D) { // -

	    // version 1.3 file terminator
	    if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x33 && data[data.length - 7] == 0x25 && // %
		    data[data.length - 6] == 0x25 && // %
		    data[data.length - 5] == 0x45 && // E
		    data[data.length - 4] == 0x4F && // O
		    data[data.length - 3] == 0x46 && // F
		    data[data.length - 2] == 0x20 && // SPACE
		    data[data.length - 1] == 0x0A) { // EOL
		return true;
	    }

	    // version 1.3 file terminator
	    if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x34 && data[data.length - 6] == 0x25 && // %
		    data[data.length - 5] == 0x25 && // %
		    data[data.length - 4] == 0x45 && // E
		    data[data.length - 3] == 0x4F && // O
		    data[data.length - 2] == 0x46 && // F
		    data[data.length - 1] == 0x0A) { // EOL
		return true;
	    }
	}
	return false;
    }

}
