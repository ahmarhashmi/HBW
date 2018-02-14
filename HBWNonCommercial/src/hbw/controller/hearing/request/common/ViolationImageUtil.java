/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.ibm.mm.sdk.common.DKParts;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import hbw.controller.hearing.request.model.CMItem;

/**
 * @author Ahmar
 *
 */
public class ViolationImageUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(ViolationImageUtil.class);

    private static final Font HELV_24_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD);
    private static final Font HELV_14 = FontFactory.getFont(FontFactory.HELVETICA, 14);

    private HttpServletRequest httpRequest;

    // public static void main(String[] args) {
    // createImageReportPdfFromTiff(getVioImageLoc(), getVioNum(), "Internet", "",
    // "", outStream, issueDate);
    // }

    /**
     * Generates PDF from the vio image at stored file loc and the arg vio num and
     * streams it to arg out stream Variant that takes violation number as an
     * argument 02-27-2014
     */
    public static void writeVioImagePdfToStream(String vioNum, String title, String locationName,
	    java.io.OutputStream outStream) throws java.io.IOException, DocumentException {
	String imageLocation = "";
	try {
	    imageLocation = getVioTiffImageToFile(vioNum);
	} catch (Exception e) {
	    LOGGER.error("Error retrieving image for violation number: " + vioNum + ".  Error is: "
		    + e.getLocalizedMessage());
	    // leave imageLocation as "", will show not found to the user
	}
	if (locationName == null || locationName.trim().length() == 0) {
	    locationName = "CityPay"; // override "internet" with "CityPay"
	}
	if (title == null || title.trim().length() == 0) {
	    title = "Image Copy"; // override default title of "NYCServ Violation Copy" for use by CityPay
	}

	createImageReportPdfFromTiff(imageLocation, vioNum, locationName, "", "", title, outStream, new Date());
    }

    /**
     * Save vio image as tiff file Creation date: (08/13/04 5:33:38 PM)
     * 
     * @parameter String violationNumber - used to request gif to be save to a file
     * @return imageFullPath the file loc where the gif can be found on the web
     */
    public static String getVioTiffImageToFile(String violationNumber) throws Exception {
	String fileName = File.separator + "ImageCopy_" + violationNumber + ".tif";
	String imageFullPath = Resource.VIO_IMAGE_INTERNET_WRITE_PATH.getValue() + fileName;

	File imageFile = new File(imageFullPath);

	if (!imageFile.exists()) {
	    imageFullPath = saveVioTiffImageToFile(violationNumber, imageFullPath);
	}

	return imageFullPath;
    }

    /**
     * goes to content manager and gets image, DOES NOT convert to GIF. public
     * static so anyone can use easily - thread safe Creation date: (11/18/02
     * 5:56:33 PM)
     */

    public static String saveVioTiffImageToFile(String vioNumber, String inputImagePath) {
	LOGGER.info("In saveVioTiffImageToFile() for " + vioNumber + " Input File " + inputImagePath);
	java.io.BufferedOutputStream out = null; // out stream for image file output
	String imageFullPath = ""; // holds return value, "" if error
	DKDatastoreICM dataSource = null;
	LOGGER.info("In saveVioTiffImageToFile() for " + vioNumber + " before long start.");
	long start;
	long execution_time_begin = System.currentTimeMillis();

	LOGGER.info("In saveVioTiffImageToFile() for " + vioNumber + "after long start.");
	try {

	    CMItem cmItem = new CMItem("NOV");
	    cmItem.setAttrVal("FileName", inputImagePath);
	    cmItem.setAttrVal("NOVID", vioNumber);
	    ArrayList<CMItem> ddos = findCMItem(cmItem, new Boolean(true), true);

	    // process if an agency front copy was found, otherwise return default ("")
	    if (ddos.size() > 0) {

		com.ibm.mm.sdk.common.DKDDO ddo = ((CMItem) ddos.get(0)).getDdo();
		com.ibm.mm.sdk.common.DKParts dkParts = (DKParts) ddo
			.getDataByName(com.ibm.mm.sdk.common.DKConstant.DKPARTS);
		if (dkParts != null) {
		    Object obj = dkParts.createIterator().next(); // assume only one
		    if (obj != null && obj instanceof com.ibm.mm.sdk.common.DKLobICM) {
			imageFullPath = inputImagePath;
			((com.ibm.mm.sdk.common.DKLobICM) obj).getContentToClientFile(imageFullPath, 1); // option 1
													 // creates new
													 // or overwites
													 // existing
													 // file
		    }
		}

		long execution_finish_retrieval_time = System.currentTimeMillis();
		LOGGER.info("Wrote TIFF vio image via getContentToClientFile() to temp location: " + imageFullPath);
		long execution_tiffwrite = System.currentTimeMillis();

		LOGGER.info("CM_IMAGE_RETRIEVAL:  " + " NOV ID: " + vioNumber + "  ||   CM INDEX ID: ["
			+ ddo.getPidObject().getPrimaryId() + "]  ||   Total Processing time: "
			+ (execution_tiffwrite - execution_time_begin) + "ms  ||  Image Size: "
			+ new File(imageFullPath).length() + " bytes  ||  Retrieval Time: "
			+ (execution_finish_retrieval_time - execution_time_begin)
			+ "ms  ||  TIFF conversion time: NOT NEEDED "
			+ "ms  ||  TIFF file write: INCLUDED IN RETRIEVAL TIME");
	    }

	} catch (Exception ex) {
	    LOGGER.error(ex.getMessage());
	    LOGGER.error("Error getting image for violation: " + vioNumber + ".  Error is: " + ex);
	    imageFullPath = ""; // error - no file
	} finally {
	    // clean up
	    try {
		if (dataSource != null && dataSource.isConnected()) {
		    start = new Date().getTime();
		    dataSource.disconnect();
		    dataSource.destroy();
		    LOGGER.info("====> Successfully closed and destroyed CM DataSource.");
		    LOGGER.info("====> Disconnected from CM in: " + (new Date().getTime() - start) + " ms.");
		    if (out != null) {
			out.close();
			LOGGER.info("Closed stream to temp location: " + imageFullPath);
		    }
		}
	    } catch (Exception e) {
		LOGGER.error("Error closing content manager connection in ImageRequestThread.  Error is: " + e);
	    }
	}

	return imageFullPath;
    }

    private static ArrayList<CMItem> findCMItem(CMItem cmItem, Boolean callFromServer, Boolean isWithContent) {

	ArrayList<CMItem> ddos = new ArrayList<CMItem>();

	// We don't have access to the database from where existing system is fetching
	// the data.
	return ddos;
    }

    /**
     * 
     * @param imagePath
     * @param vioNum
     * @param locationName
     * @param pageNumText
     * @param docketNumber
     * @param title
     * @param pdfOut
     * @param issueDate
     * @throws java.io.IOException
     * @throws DocumentException
     */
    public static void createImageReportPdfFromTiff(String imagePath, String vioNum, String locationName,
	    String pageNumText, String docketNumber, String title, OutputStream pdfOut, Date issueDate)
	    throws java.io.IOException, DocumentException {
	LOGGER.info("In createImageReportPdfFromTiff for vio: " + vioNum + ", issue date: " + issueDate + ", location: "
		+ locationName);
	Document document = new Document();
	document.setPageSize(PageSize.LETTER);
	// document.setMargins(margin, margin, margin,margin); // left, right, top,
	// bottom
	PdfWriter pdfWriter = PdfWriter.getInstance(document, pdfOut);
	pdfWriter.setPdfVersion(PdfWriter.VERSION_1_2);

	document.open();

	// Title
	Element titleTable = getTitleElementWithBarcode(vioNum, locationName, title, pdfWriter);
	document.add(titleTable);

	// centered table for image
	PdfPTable imageTable = new PdfPTable(1);
	imageTable.setWidthPercentage(100);

	if (imagePath != null && imagePath.trim().length() > 0) {
	    // vio image
	    Image vioImage = getImage(imagePath);
	    float maxHeight = 11.0f * 72; // handheld vios start with "7". They look better 10 inches hi
	    float maxWidth = 50.0f * 72; // make a non-factor - so will scale to fit height
	    vioImage.scaleToFit(maxWidth, maxHeight);
	    // place image directly on the page so it can be positioned to move timestamp
	    // off
	    vioImage.setAbsolutePosition(1.97f * 72, -1.2f * 72); // excessive white-space makes us move some of the
								  // white off ot the page bottom
	    vioImage.setTransparency(new int[] { 255, 255 }); // make white transparent so footer and header show
							      // through
	    pdfWriter.getDirectContent().addImage(vioImage);
	} else {
	    // no image available
	    String text = "\n\n\nThere is no Image found";
	    text += "\nfor Violation Number: " + vioNum;
	    if (docketNumber != null && docketNumber.trim().length() > 0) {
		text += "\n\n\nDocket number: " + docketNumber;
	    }
	    Paragraph noImgPara = new Paragraph(text, HELV_14);
	    noImgPara.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(noImgPara);
	}

	document.close();
    }

    /**
     * Answers iText Element that is table with title text and barcode specified in
     * vioNum
     */
    private static Element getTitleElementWithBarcode(String vioNum, String location, String title, PdfWriter pdfWriter)
	    throws DocumentException {

	PdfPTable titleTable = new PdfPTable(100);
	titleTable.setWidthPercentage(100);
	PdfPCell cell = null;
	// empty left cell
	cell = new PdfPCell(new Phrase(""));
	cell.setBorderWidth(0);
	cell.setColspan(22);
	titleTable.addCell(cell);

	// Title
	Paragraph para = new Paragraph(new Phrase(title + "\n", HELV_24_BOLD));
	para.add(new Phrase(location, HELV_14));
	para.setAlignment(Element.ALIGN_CENTER);
	cell = new PdfPCell(para);
	cell.setBorderWidth(0);
	cell.setColspan(55);
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	cell.setVerticalAlignment(Element.ALIGN_TOP);
	titleTable.addCell(cell);

	// Barcode
	// com.lowagie.text.pdf.BarcodeInter25 barcode = new
	// com.lowagie.text.pdf.BarcodeInter25();
	Barcode barcode = new Barcode128(); // need 128 for ECB vio nums
	// Barcode barcode = new Barcode39();
	Image bcImage = null;
	// if this is an AVPS ECB vio num it may have extra leading zero - remove it
	barcode.setCode(vioNum);
	PdfContentByte cb = pdfWriter.getDirectContent();
	try {
	    bcImage = barcode.createImageWithBarcode(cb, null, null);
	} catch (Throwable t) {
	    // can throw IllegalArgumentException if data cant be repped as a barcode
	    LOGGER.info(
		    "Error rendering barcode for Image report for violation number: " + vioNum + ".  Error is: " + t);
	}
	if (bcImage != null) {
	    bcImage.scaleToFit(1.5f * 72, 1.5f * 72); // scale to 1.5 inch width, 1.5 inch height
	    cell = new PdfPCell(bcImage);
	} else {
	    cell = new PdfPCell(new Phrase(""));
	}
	cell.setBorderWidth(0);
	cell.setColspan(23);
	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
	titleTable.addCell(cell);

	return titleTable;

    }

    /**
     * Answers an image element by reading image data from arg path. Assumes file is
     * gif image
     */
    @SuppressWarnings("deprecation")
    private static Image getImage(String imagePath) throws IOException, DocumentException {
	Image theImage = null;
	RandomAccessFileOrArray ra = null;
	try {
	    ra = new RandomAccessFileOrArray(imagePath);
	    theImage = TiffImage.getTiffImage(ra, 1); // always page 1
	} catch (OutOfMemoryError oome) {
	    // final stopgap to prevent core and heap dumps - this can happen due to bug in
	    // iText when reading some TIFF images
	    // as soon as we return, theImage should be garbage collected and we are free to
	    // go about our business
	    String msg = "OutOfMemoryError encountered reading image file: " + imagePath;
	    LOGGER.info(msg);
	    throw new DocumentException(msg);
	} finally {
	    if (ra != null) {
		ra.close();
	    }
	}

	return theImage;

    }

    /**
     * Gets the httpRequest for this bean - set in init Creation date: (10/15/2001
     * 9:54:24 AM)
     * 
     * @return javax.servlet.http.HttpServletRequest
     */
    public HttpServletRequest getHttpRequest() {
	return httpRequest;
    }

}
