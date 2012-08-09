/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belisEE.util;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;
import java.util.Comparator;

/**
 *
 * @author spuhl
 */
//ToDo move to BaseEntityClass
public class CriteriaStringComparator implements Comparator<BaseEntity> {

    private static Comparator instance;

    private CriteriaStringComparator() {
    }

    public static Comparator getInstance() {
        if (CriteriaStringComparator.instance == null) {
            synchronized (CriteriaStringComparator.class) {
                if (CriteriaStringComparator.instance == null) {
                    CriteriaStringComparator.instance = new CriteriaStringComparator();
                }
            }
        }
        return instance;
    }

    @Override
    public int compare(BaseEntity o1, BaseEntity o2) {
        if (o1 != null && o2 != null) {
            return AlphanumComparator.getInstance().compare(o1.getCompareCriteriaString(), o2.getCompareCriteriaString());
        } else if (o1 != null) {
            return 1;
        } else if (o2 != null) {
            return -1;
        } else {
            return 0;
        }

    }
}
