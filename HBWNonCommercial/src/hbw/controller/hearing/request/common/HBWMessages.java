package hbw.controller.hearing.request.common;

public final class HBWMessages {   

    public static final String CREATE_HEARING_FILE_SIZE_LIMIT = "The total size of your files cannot exceed 20MB.";
    public static final String CREATE_HEARING_SUMMON_ALREADY_SUBMITTED = "A hearing request has already been submitted for this summons. No further documentation can be uploaded.";
    public static final String CREATE_HEARING_GENERIC_ERROR = "Sorry, we are experiencing unexpected technical difficulties. Please try again later.";
    public static final String CREATE_HEARING_CORRUPT_FILE = " has been identified as potentially corrupt or malicious. Cannot be uploaded.";
    public static final String CREATE_HEARING_NOT_A_VALID_FILE_NAME = " is not a valid file name. Remove any special characters and try again.";   
    public static final String CREATE_HEARING_NOT_A_VALID_FILE_SIZE = " is larger than 20MB. Please submit a smaller file.";
    public static final String CREATE_HEARING_DUPLICATE_FILE = "You have already submitted this file successfully. It cannot be uploaded again.";
    public static final String CREATE_HEARING_INVALID_DEFENCE = "There is a problem with the text that you have entered.  Please remove any special characters and try again.";    
    public static final String VIOLATION_SEARCH_INVALID_ENTRY = "The violation number you entered is not valid. Check that your violation number is correct and try again.";    
    public static final String FILE_UPLOAD_SERVLET_MAX_PAGES = "Your document cannot exceed 50 pages. If you would like to submit more than 50 pages, please do not request a hearing online. Submit your hearing request and evidence by mail or in person.";
    public static final String FILE_UPLOAD_SERVLET_MAX_SIZE = "Your file size cannot exceed 20MB. Please upload smaller files, or consider submitting your hearing request and evidence by mail or in person.";
    public static final String FILE_UPLOAD_SERVLET_FILE_ALREADY_EXIST = "A file by this name has already been uploaded. Please rename your file and try again.";
    public static final String FILE_UPLOAD_SERVLET_FILE_IS_DAMAGED = "File is damaged and cannot be uploaded.";
    public static final String FILE_UPLOAD_SERVLET_TOTAL_FILE_SIZE = "The total size of your files cannot exceed 20MB.";
    public static final String FILE_UPLOAD_SERVLET_INVALID_PDF = "The file is damaged or is not a valid PDF file and cannot be uploaded.";
    public static final String FILE_UPLOAD_SERVLET_INVALID_TIFF = "The file is damaged or is not a valid TIFF file and cannot be uploaded.";
    public static final String FILE_UPLOAD_SERVLET_INVALID_GIF = "Animated GIFs cannot be uploaded. Please submit a PDF, JPEG/JPG, TIFF, BMP, or non-animated gif.";
    public static final String FILE_UPLOAD_SERVLET_SUPPORTED_FILE = "File type is not supported. Please submit a PDF, JPEG/JPG, TIFF, BMP, or non-animated gif.";
    
        
    
    // public static final String CREATE_HEARING_ = "";
 
    //String s = "hello %s!";
    //s = String.format(s, "world");
}