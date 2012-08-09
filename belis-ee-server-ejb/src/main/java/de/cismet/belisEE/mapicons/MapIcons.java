/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belisEE.mapicons;

import de.cismet.cismap.commons.tools.IconUtils;
import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;

/**
 *
 * @author spuhl
 */
public class MapIcons implements Serializable {

    public transient static Image icoMast = new javax.swing.ImageIcon(MapIcons.class.getResource("/de/cismet/belisEE/mapicons/mast.png")).getImage();
    public transient static Image icoMastSelected = IconUtils.changeImageColor(icoMast, Color.BLUE, 1.0f);
    public transient static Image icoMastWithLeuchte = new javax.swing.ImageIcon(MapIcons.class.getResource("/de/cismet/belisEE/mapicons/mastWithLeuchte.png")).getImage();
    public transient static Image icoMastWithLeuchteSelected = IconUtils.changeImageColor(icoMastWithLeuchte, Color.BLUE, 1.0f);
    public transient static Image icoLeuchte = new javax.swing.ImageIcon(MapIcons.class.getResource("/de/cismet/belisEE/mapicons/leuchte.png")).getImage();
    public transient static Image icoLeuchteSelected = IconUtils.changeImageColor(icoLeuchte, Color.BLUE, 1.0f);
    public transient static Image icoSchaltstelle = new javax.swing.ImageIcon(MapIcons.class.getResource("/de/cismet/belisEE/mapicons/schaltstelle.png")).getImage();
    public transient static Image icoAbzweigdose = new javax.swing.ImageIcon(MapIcons.class.getResource("/de/cismet/belisEE/mapicons/abzweigdose.png")).getImage();
    public transient static Image icoSchaltstelleSelected = IconUtils.changeImageColor(icoSchaltstelle, Color.BLUE, 1.0f);
    public transient static Image icoMauerlasche = new javax.swing.ImageIcon(MapIcons.class.getResource("/de/cismet/belisEE/mapicons/mauerlasche.png")).getImage();
    public transient static Image icoMauerlascheSelected = IconUtils.changeImageColor(icoMauerlasche, Color.BLUE, 1.0f);
}
