package hbw.controller.hearing.request.common;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.log4j2.Log4j2LoggerFactory;

/**
 * @author Ahmar Nadeem
 * 
 */
public final class FileUtil {

    private static Logger LOGGER = Log4j2LoggerFactory.getLogger(FileUtil.class);

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
		    LOGGER.error("Previously uploaded files couldn't be deleted. Exception: {}", e);
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
}
