/**
 * 
 */
package hbw.controller.hearing.request.model;

import java.util.Hashtable;

import com.ibm.mm.sdk.common.DKDDO;

/**
 * @author Ahmar Hashmi
 *
 */
public class CMItem {

    private String itemType = null;

    private Hashtable<String, String> attrVal = new Hashtable<String, String>();

    private DKDDO ddo = null;

    public CMItem(String itemType) {
	this.itemType = itemType;
    }

    /**
     * @return the itemType
     */
    public String getItemType() {
	return itemType;
    }

    /**
     * @param itemType
     *            the itemType to set
     */
    public void setItemType(String itemType) {
	this.itemType = itemType;
    }

    /**
     * @return the attrVal
     */
    public Hashtable<String, String> getAttrVal() {
	return attrVal;
    }

    /**
     * @param attrVal
     *            the attrVal to set
     */
    public void setAttrVal(String attr, String val) {
	attrVal.put(attr, val);
    }

    /**
     * @return the ddo
     */
    public DKDDO getDdo() {
	return ddo;
    }

    /**
     * @param ddo
     *            the ddo to set
     */
    public void setDdo(DKDDO ddo) {
	this.ddo = ddo;
    }

}
