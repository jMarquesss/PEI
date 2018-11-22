/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpath;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricar
 */
public class Xpath {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        XMLManagement teste = new XMLManagement("C:\\Users\\ricar\\Desktop\\ESTG\\PEI\\Trabalho\\XML\\exerciseXml.xml", "C:\\Users\\ricar\\Desktop\\ESTG\\PEI\\Trabalho\\XSD\\Exercise.xsd");

        teste.read(true);
        System.out.println(teste.validate(true));
        /*
        try {

            System.out.println(XpathEvaluator.applyXpathExpressionToString("/CATALOG/CD[COUNTRY=\"USA\"]", teste.getDocument()));
        } catch (XpathNoResultsException ex) {
            Logger.getLogger(Xpath.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
    }

}
