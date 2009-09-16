/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.commons.server.entity;

import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.cismap.commons.jtsgeometryfactories.PostGisGeometryFactory;
import de.cismet.commons.server.interfaces.Geom;
import de.cismet.commons.server.interfaces.GeometrySlot;
import java.awt.Color;
import java.awt.Paint;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.postgis.PGgeometry;

/**
 *
 * @author spuhl
 */
//ToDo!!! why is the supperclass not working in the testeeapplication the field are inherited right
@MappedSuperclass
public class GeoBaseEntity extends BaseEntity implements GeometrySlot{
  
    @Transient
    protected transient FeatureAnnotationSymbol mapIcon = null;
    
    @Transient
    private Boolean isEditable = false;
    @Transient
    private Boolean isHidden = false;
    //workaround
    @Transient
    private Boolean modifiable = true;

    @Transient
    private boolean isSelectable = true;

    @OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name="fk_geom")
    protected  Geom geometrie;
//    
    public Geom getGeometrie() {
        return geometrie;
    }
    
    public void setGeometrie(Geom val) {
        this.geometrie = val; 
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
    }
    
    public boolean canBeSelected() {
        return isSelectable;
    }

    @Override
    public Geometry getGeometry() {
       if(getGeometrie() != null && getGeometrie().getGeomField() != null){
        return PostGisGeometryFactory.createJtsGeometry(getGeometrie().getGeomField());
        } else {
            return null;
        } 
    }

    public void hide(boolean hiding) {
        isHidden = hiding;
    }

    public Boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(Boolean modifiable) {
        this.modifiable = modifiable;
    }    
    
    public boolean isEditable() {
        if (!isModifiable()) {
            return false;
        }
        if(isEditable!= null){
            return isEditable;
        } else {
            return false;
        }
    }

    //Temporary because transient attribute seems to cause nullpointer exception
    public boolean isHidden() {
        return false;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }
//
    @Override
    public void setGeometry(Geometry geom) {
        if(getGeometrie() == null){            
          setGeometrie(new Geom());
        } 

        try{
            if(geom == null){
                getGeometrie().setGeomField(null);            
            } else {
                getGeometrie().setGeomField(PGgeometry.geomFromString(PostGisGeometryFactory.getPostGisCompliantDbString(geom)));            
            }
            
            } catch(Exception ex){
                System.out.println("Fehler beim setzend er Geometrie");
                ex.printStackTrace();
            }
    }
//
    public Paint getFillingPaint() {
        return Color.GRAY;
    }

    public Paint getLinePaint() {
        return Color.BLACK;
    }

    public int getLineWidth() {
        return 1;
    }

    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        return null;
    }

    public float getTransparency() {
        return 1f;
    }

    public boolean isHighlightingEnabled() {
        return true;
    }

    //ToDo implement correct
    public void setFillingPaint(Paint fillingStyle) {
        
    }

    public void setHighlightingEnabled(boolean enabled) {
        
    }

    public void setLinePaint(Paint linePaint) {
        
    }

    public void setLineWidth(int width) {
        
    }

    public void setPointAnnotationSymbol(FeatureAnnotationSymbol featureAnnotationSymbol) {
        
    }

    public void setTransparency(float transparrency) {
        
    }
    
    public  String getKeyString(){
        return "";
    }

}
