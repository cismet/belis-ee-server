/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belisEE.util;

import de.cismet.belisEE.entity.Leuchte;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author spuhl
 */
public class LeuchteComparator implements Comparator<Leuchte>, Serializable {

    public int compare(Leuchte l1, Leuchte l2) {
        if (l1 != null && l2 != null) {
            if (l1.equals(l2)) {
                return 0;
            } else if (((Leuchte) l1).getLeuchtennummer() != null && ((Leuchte) l2).getLeuchtennummer() != null) {
                System.out.println("l1: "+((Leuchte) l1).getLeuchtennummer()+" l2: "+((Leuchte) l2).getLeuchtennummer());
                if (((Leuchte) l1).getLeuchtennummer() == ((Leuchte) l2).getLeuchtennummer()) {
                    System.out.println("Leuchtennummer are equal");
                    return 1;
                } else if (((Leuchte) l1).getLeuchtennummer() > ((Leuchte) l2).getLeuchtennummer()) {
                    System.out.println("l1 Leuchtennummer greater l2 leuchtennummer");
                    return 1;
                } else {
                    System.out.println("l2 Leuchtennummer greater l1 Leuchtennummer");
                    return -1;
                }
            } else if (((Leuchte) l1).getLeuchtennummer() != null) {
                return 1;
            } else if (((Leuchte) l2).getLeuchtennummer() != null) {
                return -1;
            } else {
                return 1;
            }
        } else if (l1 != null) {
            return 1;
        } else if (l2 != null) {
            return -1;
        } else {
            return 0;
        }
    }
}
