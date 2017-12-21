/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Ahmar Nadeem
 *
 */
public final class ZipUtil {

    /**
     * 
     * @param dirName
     * @param nameZipFile
     * @throws IOException
     */
    public static void zipDirectory(String dirName, String nameZipFile) throws IOException {
	if (new File(dirName).listFiles().length <= 1) {
	    return;
	}

	File[] filesInFolder = new File(dirName).listFiles();
	ZipOutputStream zip = null;
	FileOutputStream fW = null;
	fW = new FileOutputStream(nameZipFile);
	zip = new ZipOutputStream(fW);

	for (File file : filesInFolder) {
	    if (file.getName().contains(".zip")) {
		continue;
	    }
	    zip.putNextEntry(new ZipEntry(file.getName()));
	}
	zip.close();
	fW.close();
    }

}
