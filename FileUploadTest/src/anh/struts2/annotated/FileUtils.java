package anh.struts2.annotated;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * @author Ahmar Nadeem
 * 
 */
public final class FileUtils {

	public static void main(String[] args) {
		try {
			System.out.println(isAnimatedGIF(new File("C:\\Users\\Latifs\\Pictures\\giphy2.gif")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
}
