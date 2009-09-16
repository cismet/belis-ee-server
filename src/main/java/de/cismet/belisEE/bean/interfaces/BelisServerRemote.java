/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belisEE.bean.interfaces;

import de.cismet.belisEE.entity.Bauart;
import de.cismet.belisEE.entity.Leitungstyp;
import de.cismet.belisEE.entity.MaterialLeitung;
import de.cismet.belisEE.entity.MaterialMauerlasche;
import de.cismet.belisEE.entity.Querschnitt;
import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.entity.Energielieferant;
import de.cismet.belisEE.entity.Kennziffer;
import de.cismet.belisEE.entity.Standort;
import de.cismet.belisEE.util.StandortKey;
import de.cismet.belisEE.entity.Doppelkommando;
import de.cismet.belisEE.entity.Klassifizierung;
import de.cismet.belisEE.entity.Leuchtentyp;
import de.cismet.belisEE.entity.Mastart;
import de.cismet.belisEE.entity.Masttyp;
import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.belisEE.entity.Stadtbezirk;
import de.cismet.belisEE.entity.Strassenschluessel;
import de.cismet.belisEE.entity.UnterhaltLeuchte;
import de.cismet.belisEE.entity.UnterhaltMast;
import de.cismet.cismap.commons.BoundingBox;
import de.cismet.commons.server.interfaces.Geom;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.Remote;

/**
 *
 * @author spuhl
 */
@Remote
public interface BelisServerRemote extends lockEnabled{

     //GetAll
     public Set<Strassenschluessel> getAllStrassenschluessel() throws ActionNotSuccessfulException;
     public Set<Energielieferant> getAllEnergielieferanten() throws ActionNotSuccessfulException;
     public Set<Kennziffer> getAllKennziffer() throws ActionNotSuccessfulException;
     public Set<Stadtbezirk> getAllStadtbezirke() throws ActionNotSuccessfulException;
     public Set<Mastart> getAllMastarten() throws ActionNotSuccessfulException;
     public Set<Masttyp> getAllMasttypen() throws ActionNotSuccessfulException;
     public Set<Klassifizierung> getAllKlassifizierungen() throws ActionNotSuccessfulException;
     public Set<UnterhaltMast> getAllUnterhaltMast() throws ActionNotSuccessfulException;
     public Set<UnterhaltLeuchte> getAllUnterhaltLeuchte() throws ActionNotSuccessfulException;
     public Set<MaterialLeitung> getAllMaterialLeitung() throws ActionNotSuccessfulException;
     public Set<Leitungstyp> getAllLeitungstypen() throws ActionNotSuccessfulException;
     public Set<Querschnitt> getAllQuerschnitte() throws ActionNotSuccessfulException;
     public Set<MaterialMauerlasche> getAllMaterialMauerlasche() throws ActionNotSuccessfulException;
     public Set<Bauart> getAllBauarten() throws ActionNotSuccessfulException;
     public Set<Leuchtentyp> getAllLeuchtentypen() throws ActionNotSuccessfulException;
     public Set<Doppelkommando> getAllDoppelkommando() throws ActionNotSuccessfulException;
     
     public TreeSet<BaseEntity> getObjectsByKey(String strassenschluessel,Short kennziffer,Short laufendeNummer) throws ActionNotSuccessfulException;
     public Set<Standort> retrieveStandort(StandortKey key) throws ActionNotSuccessfulException;
     public Set<BaseEntity> saveObjects(Set<BaseEntity> objectsToSave,String userString) throws ActionNotSuccessfulException;
     public Set<BaseEntity> refreshObjects(Set<BaseEntity> objectsToSave) throws ActionNotSuccessfulException;
     public void deleteEntity(BaseEntity objectsToDelete,String userString) throws ActionNotSuccessfulException;
     public void deleteEntities(Set<BaseEntity> objectsToDelete,String userString) throws ActionNotSuccessfulException;
     //by Geometry
     public TreeSet<BaseEntity> getObjectsByBoundingBox(BoundingBox bb) throws ActionNotSuccessfulException;
     public Object getObjectsByGeom(Geom geom) throws ActionNotSuccessfulException;
              
}
