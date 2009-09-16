/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.commons.server.interfaces;

import de.cismet.belisEE.entity.DmsUrl;
import java.util.Set;

/**
 *
 * @author srichter
 */
public interface DocumentContainer {

    public Set<DmsUrl> getDokumente();

    public void setDokumente(Set<DmsUrl> urls);
}
