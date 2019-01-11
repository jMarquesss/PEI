/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpath;

/**
 *
 * @author ricar
 */
public class XpathNoResultsException extends Exception {
    public XpathNoResultsException() {
    }

    /**
     * Constructs an instance of <code>XpathNoResultsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public XpathNoResultsException(String msg) {
        super(msg);
    }

}
