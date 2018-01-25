/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ahmar Nadeem
 *
 */
public class Evidence implements Serializable {

    private static final long serialVersionUID = 6390670136029692023L;

    /** Complete name of the file like abc.jpeg */
    @JsonProperty("Name")
    private String name;

    /** The extension of the file like jpeg */
    @JsonProperty("Extension")
    private String extension;

    /** It should contain the Base64 of the provided file */
    @JsonProperty("Content")
    private String content;

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
	return extension;
    }

    /**
     * @param extension
     *            the extension to set
     */
    public void setExtension(String extension) {
	this.extension = extension;
    }

    /**
     * @return the content
     */
    public String getContent() {
	return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }
}
