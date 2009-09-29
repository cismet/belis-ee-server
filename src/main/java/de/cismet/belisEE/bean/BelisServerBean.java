/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belisEE.bean;


import de.cismet.belisEE.bean.interfaces.BelisServerRemote;
import de.cismet.belisEE.entity.Bauart;
import de.cismet.belisEE.entity.Doppelkommando;
import de.cismet.belisEE.entity.Klassifizierung;
import de.cismet.belisEE.entity.Leitungstyp;
import de.cismet.belisEE.entity.Leuchtentyp;
import de.cismet.belisEE.entity.Mastart;
import de.cismet.belisEE.entity.Masttyp;
import de.cismet.belisEE.entity.MaterialLeitung;
import de.cismet.belisEE.entity.MaterialMauerlasche;
import de.cismet.belisEE.entity.Querschnitt;
import de.cismet.belisEE.entity.UnterhaltMast;
import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.entity.Energielieferant;
import de.cismet.belisEE.entity.GeomToEntityIndex;
import de.cismet.cismap.commons.BoundingBox;
import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.belisEE.entity.Kennziffer;
import de.cismet.belisEE.entity.Leuchte;
import de.cismet.belisEE.entity.Mauerlasche;
import de.cismet.belisEE.entity.Schaltstelle;
import de.cismet.belisEE.entity.Stadtbezirk;
import de.cismet.belisEE.entity.Standort;
import de.cismet.belisEE.util.StandortKey;
import de.cismet.belisEE.entity.Strassenschluessel;
import de.cismet.belisEE.entity.UnterhaltLeuchte;
import de.cismet.belisEE.util.BelisEEUtils;
import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;
import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.Geom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.ejb.Stateless;

//ToDo logging umstellen
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import org.apache.commons.collections.comparators.ReverseComparator;

/**
 *
 * @author spuhl
 */
@Stateless
public class BelisServerBean extends AbstractServiceBean implements BelisServerRemote {

    //ToDo is it a good idea to make a basic identity for generic id access or propertyChange support ??     
    public Set<Standort> retrieveStandort(
            StandortKey key) throws ActionNotSuccessfulException {
        //findFlurstueckeByKey
        try {


            if (key == null) {
                System.out.println("The search key is null");
                throw new ActionNotSuccessfulException("The search key is null");
            }
            System.out.println("Finde Standort: ");
            System.out.println("Strassenschlüssel       : " + key.getStrassenschluessel().getPk());
            System.out.println("Kennziffer: " + key.getKennziffer());
            System.out.println("laufende Nummer : " + key.getLaufendeNummer());
            //ToDo what todo if one of the values is null --> extra flag useWildcard
            String strasse = null;
            if (key.getStrassenschluessel() == null || (strasse = key.getStrassenschluessel().getPk()) == null) {
                System.out.println("At least the strassenschluessel must be != null");
                return null;
            }
            Short kennziffer = null;
            if (key.getKennziffer() == null || (kennziffer = key.getKennziffer().getKennziffer()) == null) {
                System.out.println("kennziffer is null --> wildcard");
            }
            Short lfdNummer = null;
            if ((lfdNummer = key.getLaufendeNummer()) == null) {
                System.out.println("lfdNummer is null --> wildcard");
            }
            //ToDo optimise
            System.out.println("Strasse: " + strasse);
            List<Standort> result = null;
            if (strasse != null && lfdNummer != null && kennziffer != null) {
                System.out.println("Standort.findStandortByStrassenschluesselByKennzifferBylfdNummer");
                result = (List<Standort>) em.createNamedQuery("Standort.findStandortByStrassenschluesselByKennzifferBylfdNummer").setParameter("strassenschluessel", strasse).setParameter("kennziffer", kennziffer).setParameter("laufendeNummer", lfdNummer).getResultList();
            } else if (strasse != null && lfdNummer != null) {
                System.out.println("Standort.findStandortByStrassenschluesselBylfdNummer");
                result = (List<Standort>) em.createNamedQuery("Standort.findStandortByStrassenschluesselBylfdNummer").setParameter("strassenschluessel", strasse).setParameter("laufendeNummer", lfdNummer).getResultList();
            } else if (strasse != null && kennziffer != null) {
                System.out.println("Standort.findStandortByStrassenschluesselByKennziffer");
                result = (List<Standort>) em.createNamedQuery("Standort.findStandortByStrassenschluesselByKennziffer").setParameter("strassenschluessel", strasse).setParameter("kennziffer", kennziffer).getResultList();
            } else {
                System.out.println("Standort.findStandortByStrassenschluessel");
                result = (List<Standort>) em.createNamedQuery("Standort.findStandortByStrassenschluessel").setParameter("strassenschluessel", strasse).getResultList();
            }
//            if()
////            List<Standort> maeste = (List<Standort>) em.createNamedQuery(
////                    "findStandort").setParameter("strassenschluessel",   key.getStrassenschluessel().getStrasse()).setParameter("kennziffer", "%").setParameter("laufendeNummer", "%").getResultList();
//                    List<Standort> maeste = (List<Standort>) em.createNamedQuery(
//                    "Standort.findStandortByStrassenschluessel").setParameter("strassenschluessel",   key.getStrassenschluessel().getStrasse()).getResultList();
            if (result != null && (result.size() != 0)) {
//                if (maeste. if (masize() > 1) {
//                    System.out.println("Maeste count: " + maeste.size());
//                    throw new Exception("Multiple Maeste should only be one");
//                } else {          
                System.out.println("Found Standort");
                return new HashSet<Standort>(result);
            } else {
                System.out.println("No Standort found");
                return null;
            }

        } catch (Exception ex) {
            System.out.println("Failure during Standort querying: " + key);
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Failure during Standort querying");
        }
    }

    public Set<Schaltstelle> retrieveSchaltstelle(
            String key, Short laufendeNummer) throws ActionNotSuccessfulException {
        //findFlurstueckeByKey
        try {
            if (key == null) {
                System.out.println("The search key is null");
                throw new ActionNotSuccessfulException("The search key is null");
            }
            System.out.println("Finde Schaltstelle: ");
            System.out.println("Strassenschlüssel       : " + key);
            //ToDo what todo if one of the values is null --> extra flag useWildcard            
            if (key == null) {
                System.out.println("The strassenschluessel must be != null");
                return null;
            }

            Short lfdNummer = null;
            if ((laufendeNummer) == null) {
                System.out.println("lfdNummer is null --> wildcard");
            }

            //ToDo optimise
            System.out.println("Strasse: " + key);
            List<Schaltstelle> result = null;
            if (key != null && lfdNummer != null) {
                System.out.println("Schaltstelle.findSchaltstelleByStrassenschluesselBylfdNummer");
                result = (List<Schaltstelle>) em.createNamedQuery("Schaltstelle.findSchaltstelleByStrassenschluesselBylfdNummer").setParameter("strassenschluessel", key).setParameter("laufendeNummer", lfdNummer).getResultList();
            } else {
                System.out.println("Schaltstelle.findSchaltstelleByStrassenschluessel");
                result = (List<Schaltstelle>) em.createNamedQuery("Schaltstelle.findSchaltstelleByStrassenschluessel").setParameter("strassenschluessel", key).getResultList();
            }
            if (result != null && (result.size() != 0)) {
                System.out.println("Found Schaltstelle");
                return new HashSet<Schaltstelle>(result);
            } else {
                System.out.println("No Schaltstelle found");
                return null;
            }

        } catch (Exception ex) {
            System.out.println("Failure during Schaltstelle querying: " + key);
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Failure during Schaltstelle querying");
        }
    }
    //ToDo Generic Method redundant code

    public Set<Mauerlasche> retrieveMauerlasche(
            String key, Short laufendeNummer) throws ActionNotSuccessfulException {
        //findFlurstueckeByKey
        try {
            if (key == null) {
                System.out.println("The search key is null");
                throw new ActionNotSuccessfulException("The search key is null");
            }
            System.out.println("Finde Mauerlasche: ");
            System.out.println("Strassenschlüssel       : " + key);
            //ToDo what todo if one of the values is null --> extra flag useWildcard
            String strasse = null;
            if (key == null) {
                System.out.println("The strassenschluessel must be != null");
                return null;
            }

            Short lfdNummer = null;
            if ((laufendeNummer) == null) {
                System.out.println("lfdNummer is null --> wildcard");
            }

            //ToDo optimise
            System.out.println("Strasse: " + strasse);
            List<Mauerlasche> result = null;
            if (strasse != null && lfdNummer != null) {
                System.out.println("Mauerlasche.findMauerlascheByStrassenschluesselBylfdNummer");
                result = (List<Mauerlasche>) em.createNamedQuery("Mauerlasche.findMauerlascheByStrassenschluesselBylfdNummer").setParameter("strassenschluessel", strasse).setParameter("laufendeNummer", lfdNummer).getResultList();
            } else {
                System.out.println("Mauerlasche.findMauerlascheByStrassenschluessel");
                result = (List<Mauerlasche>) em.createNamedQuery("Mauerlasche.findMauerlascheByStrassenschluessel").setParameter("strassenschluessel", strasse).getResultList();
            }
            if (result != null && (result.size() != 0)) {
                System.out.println("Found Mauerlasche");
                return new HashSet<Mauerlasche>(result);
            } else {
                System.out.println("No Mauerlasche found");
                return null;
            }

        } catch (Exception ex) {
            System.out.println("Failure during Mauerlasche querying: " + key);
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Failure during Mauerlasche querying");
        }
    }

    //ToDo generic method. Everytime the same !!!!!!!!
    //Don't even nedd the named query just generate the query on the fly with the class information
    public Set<Strassenschluessel> getAllStrassenschluessel() throws ActionNotSuccessfulException {
        try {
            List<Strassenschluessel> nutzungsarten = (List<Strassenschluessel>) em.createNamedQuery(
                    "Strassenschluessel.findAllStrassenschluessel").getResultList();
            return new HashSet(nutzungsarten);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Energielieferant> getAllEnergielieferanten() throws ActionNotSuccessfulException {
        try {
            List<Energielieferant> energieLieferanten = (List<Energielieferant>) em.createNamedQuery(
                    "Energielieferant.findAllEnergielieferanten").getResultList();
            return new HashSet(energieLieferanten);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<UnterhaltLeuchte> getAllUnterhaltLeuchte() throws ActionNotSuccessfulException {
        try {
            List<UnterhaltLeuchte> unterhaltLeuchte = (List<UnterhaltLeuchte>) em.createNamedQuery(
                    "UnterhaltLeuchte.findAllUnterhaltLeuchte").getResultList();
            return new HashSet(unterhaltLeuchte);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Kennziffer> getAllKennziffer() throws ActionNotSuccessfulException {
        try {
            List<Kennziffer> nutzungsarten = (List<Kennziffer>) em.createNamedQuery(
                    "Kennziffer.findAllKennziffer").getResultList();
            return new HashSet(nutzungsarten);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }
    //ToDo idea one method which delivers all the normalized Strings lookup via Classname

    public Set<Stadtbezirk> getAllStadtbezirke() throws ActionNotSuccessfulException {
        try {
            List<Stadtbezirk> stadtbezirke = (List<Stadtbezirk>) em.createNamedQuery(
                    "Stadtbezirk.findAllStadtbezirke").getResultList();
            return new HashSet(stadtbezirke);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Klassifizierung> getAllKlassifizierungen() throws ActionNotSuccessfulException {
        try {
            List<Klassifizierung> stadtbezirke = (List<Klassifizierung>) em.createNamedQuery(
                    "Klassifizierung.findAllKlassifizierungen").getResultList();
            return new HashSet(stadtbezirke);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Mastart> getAllMastarten() throws ActionNotSuccessfulException {
        try {
            List<Mastart> stadtbezirke = (List<Mastart>) em.createNamedQuery(
                    "Mastart.findAllMastarten").getResultList();
            return new HashSet(stadtbezirke);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Masttyp> getAllMasttypen() throws ActionNotSuccessfulException {
        try {
            List<Masttyp> stadtbezirke = (List<Masttyp>) em.createNamedQuery(
                    "Masttyp.findAllMasttypen").getResultList();
            return new HashSet(stadtbezirke);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<UnterhaltMast> getAllUnterhaltMast() throws ActionNotSuccessfulException {
        try {
            List<UnterhaltMast> stadtbezirke = (List<UnterhaltMast>) em.createNamedQuery(
                    "UnterhaltMast.findAllUnterhaltMast").getResultList();
            return new HashSet(stadtbezirke);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<MaterialLeitung> getAllMaterialLeitung() throws ActionNotSuccessfulException {
        try {
            List<MaterialLeitung> material = (List<MaterialLeitung>) em.createNamedQuery(
                    "MaterialLeitung.findAllMaterialLeitung").getResultList();
            return new HashSet(material);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Leitungstyp> getAllLeitungstypen() throws ActionNotSuccessfulException {
        try {
            List<Leitungstyp> leitungstypen = (List<Leitungstyp>) em.createNamedQuery(
                    "Leitungstyp.findAllLeitungstyp").getResultList();
            return new HashSet(leitungstypen);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Querschnitt> getAllQuerschnitte() throws ActionNotSuccessfulException {
        try {
            List<Querschnitt> querschnitte = (List<Querschnitt>) em.createNamedQuery(
                    "Querschnitt.findAllQuerschnitt").getResultList();
            return new HashSet(querschnitte);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<MaterialMauerlasche> getAllMaterialMauerlasche() throws ActionNotSuccessfulException {
        try {
            List<MaterialMauerlasche> materialMauerlasche = (List<MaterialMauerlasche>) em.createNamedQuery(
                    "MaterialMauerlasche.findAllMaterialMauerlasche").getResultList();
            return new HashSet(materialMauerlasche);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Bauart> getAllBauarten() throws ActionNotSuccessfulException {
        try {
            List<Bauart> bauarten = (List<Bauart>) em.createNamedQuery(
                    "Bauart.findAllBauart").getResultList();
            return new HashSet(bauarten);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Leuchtentyp> getAllLeuchtentypen() throws ActionNotSuccessfulException {
        try {
            List<Leuchtentyp> leuchtentypen = (List<Leuchtentyp>) em.createNamedQuery(
                    "Leuchtentyp.findAllLeuchtentyp").getResultList();
            return new HashSet(leuchtentypen);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    public Set<Doppelkommando> getAllDoppelkommando() throws ActionNotSuccessfulException {
        try {
            List<Doppelkommando> leuchtentypen = (List<Doppelkommando>) em.createNamedQuery(
                    "Doppelkommando.findAllDoppelkommando").getResultList();
            return new HashSet(leuchtentypen);
        } catch (Exception ex) {
            System.out.println("Error while querying entity");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while querying entity");
        }
    }

    //ToDo Not sure if needed only if there coming other objects like Mauerlaschen    
    //ToDo Leuchtenproblem
    public TreeSet<BaseEntity> getObjectsByKey(String strassenschluessel, Short kennziffer, Short laufendeNummer) throws ActionNotSuccessfulException {
        //Todo make better --> generalize
        final Set<Standort> standorte = retrieveStandort(new StandortKey(strassenschluessel, kennziffer, laufendeNummer));
        final Set<Schaltstelle> schaltstellen = retrieveSchaltstelle(strassenschluessel, laufendeNummer);
        final Set<Mauerlasche> mauerlaschen = retrieveMauerlasche(strassenschluessel, laufendeNummer);
        //final TreeSet<BaseEntity> results = new TreeSet<BaseEntity>(new EntityComparator());
        final TreeSet<BaseEntity> results = new TreeSet<BaseEntity>(new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
        if (standorte != null) {
            addCollectionToSortedSet(results,standorte);
        }
        if (schaltstellen != null) {
            addCollectionToSortedSet(results,schaltstellen);
            //results.addAll(schaltstellen);
        }
        if (mauerlaschen != null) {
            addCollectionToSortedSet(results,mauerlaschen);
            //results.addAll(mauerlaschen);
        }
        return results;
    }

    public TreeSet saveObjects(Set<BaseEntity> objectsToSave, String userString) throws ActionNotSuccessfulException {
        System.out.println("save objects");
        TreeSet<BaseEntity> savedEntities = new TreeSet(new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
        ArrayList<BaseEntity> errornousEntities = new ArrayList<BaseEntity>();
        try {
            if (objectsToSave != null) {
                for (BaseEntity curEntity : objectsToSave) {
                    try {
                        if (curEntity != null) {
                            if (BelisEEUtils.getEntityId(curEntity) == null) {
                                System.out.println("Entity Id is not set --> persisting entity (create).");
                                if (curEntity instanceof Standort) {
                                    determineNextLaufendenummer((Standort) curEntity);
                                }
                                em.persist(curEntity);
//                                if (curEntity instanceof Standort) {
//                                   final Set<Leuchte> leuchten =((Standort)curEntity).getLeuchten();
//                                   if(leuchten != null && leuchten.size() > 0){
//                                       System.out.println("Standort has leuchten, setting backrefs from Leuchte to Standort");
//                                       for(Leuchte curLeuchte:leuchten){
//                                           curLeuchte.setStandort((Standort)curEntity);
//                                           em.persist(curLeuchte);
//                                       }
//                                   }
//                                }
                                if (curEntity instanceof GeoBaseEntity) {
                                    System.out.println("instance is GeoBaseEntity");
                                    updateGeomIndex((GeoBaseEntity) curEntity, true);
                                }
                                savedEntities.add(curEntity);
                            } else {
                                System.out.println("Entity Id is set --> checking Modification.");
                                //if (curEntity.isWasModified()) {
                                System.out.println("Entity Id is set --> merge entity (update).");                                
                                final BaseEntity refreshedEntity = em.merge(curEntity);
                                em.flush();
                                if (refreshedEntity instanceof GeoBaseEntity) {
                                    //ToDo
                                    //updateGeomIndex((GeoBaseEntity) curEntity, false);
                                    updateGeomIndex((GeoBaseEntity) refreshedEntity, false);
                                }
                                savedEntities.add(refreshedEntity);
                            //} else {
                            //    System.out.println("Entity was not modified --> skipping Entity");
                            //}
                            }
                        } else {
                            System.out.println("Entity is null --> skipping Entity");
                        }
                    } catch (Exception ex) {
                        System.out.println("Error while saving Entity: " + curEntity);
                        ex.printStackTrace();
                        errornousEntities.add(curEntity);
                    }
                }
            } else {
                System.out.println("There are no Objects to save.");
                throw new ActionNotSuccessfulException("There are no Objects to save.");
            }
        } catch (Exception ex) {
            System.out.println("Error while saving entities");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while saving entities", ex);
        }
        if (errornousEntities.size() == 0) {
            System.out.println("Saving of entities successful");
        } else {
            System.out.println("There were problems during saving the entities errorCount: " + errornousEntities.size());
            throw new ActionNotSuccessfulException("There were problems during saving the entities", errornousEntities);
        }
        return savedEntities;
    }

    //ToDo if something goes wrong the problem is which entities are removed and which are not ?
    public void deleteEntities(Set<BaseEntity> objectsToDelete, String userString) throws ActionNotSuccessfulException {
        if (objectsToDelete != null) {
            try {
                for (BaseEntity curObject : objectsToDelete) {
                    deleteEntity(curObject, userString);
                }
            } catch (Exception ex) {
                System.out.println("Error while deleting entities");
                ex.printStackTrace();
                throw new ActionNotSuccessfulException("Error while deleting entities", ex);
            }
        } else {
            System.out.println("Objects to delete == null");
        }
        System.out.println("deleting of all entities successful");
    }

    //ToDo check if a lock for the entity is exisiting.
    public void deleteEntity(BaseEntity objectToDelete, String userString) throws ActionNotSuccessfulException {
        if (objectToDelete != null) {
            try {
                em.remove(em.merge(objectToDelete));
                em.flush();
            } catch (Exception ex) {
                System.out.println("Error while deleting entity");
                ex.printStackTrace();
                throw new ActionNotSuccessfulException("Error while deleting entity", ex);
            }
        } else {
            System.out.println("Object to delete == null");
        }
        System.out.println("deleting of entity successful");
    }

//    public Set refreshObjects(Set<BaseEntity> objectsToRefresh) throws ActionNotSuccessfulException {
//        final ArrayList<BaseEntity> errornousEntities = new ArrayList<BaseEntity>();
//        final ArrayList refreshedObjects = new ArrayList();
//        try {
//            if (objectsToRefresh != null) {
//                for (BaseEntity curEntity : objectsToRefresh) {
//                    try {
//                        //ToDo Threading
//                        //if (curEntity.isWasModified()) {
//                        refreshedObjects.add(em.find(curEntity.getClass(), BelisEEUtils.getEntityId(curEntity)));
//                    //} else {
//                    //   System.out.println("No need to refresh Entity: " + curEntity + " was not modified.");
//                    //    refreshedObjects.add(curEntity);
//                    //}
//                    //persist(curEntity);
//                    //em.refresh(curEntity);
//                    } catch (Exception ex) {
//                        System.out.println("Error while refreshing Entity: " + curEntity);
//                        ex.printStackTrace();
//                        errornousEntities.add(curEntity);
//                    }
//                }
//                if (errornousEntities.size() == 0) {
//                    System.out.println("refreshing of entities successful");
//                } else {
//                    System.out.println("There were problems during refreshing the entities errorCount: " + errornousEntities.size());
//                    throw new ActionNotSuccessfulException("There were problems during refreshing the entities", errornousEntities);
//                }
//                return new HashSet(refreshedObjects);
//            } else {
//                return new HashSet();
//            }
//        } catch (Exception ex) {
//            System.out.println("Error while refreshing entities");
//            ex.printStackTrace();
//            throw new ActionNotSuccessfulException("Error while refreshing entities", ex);
//        }
//    }

    public Set refreshObjects(Set<BaseEntity> objectsToRefresh) throws ActionNotSuccessfulException {
        System.out.println("refresh objects");
        final ArrayList<BaseEntity> errornousEntities = new ArrayList<BaseEntity>();
        final TreeSet refreshedObjects = new TreeSet(new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
        final HashMap<Class, ArrayList> entityIDs = new HashMap();
        try {
            if (objectsToRefresh != null && objectsToRefresh.size() > 0) {
                for (BaseEntity curEntity : objectsToRefresh) {
                    try {

                        if (entityIDs.containsKey(curEntity.getClass())) {
                            final ArrayList classIdList = entityIDs.get(curEntity.getClass());
                            classIdList.add(BelisEEUtils.getEntityId(curEntity));
                        } else {
                            final ArrayList newClassIdList = new ArrayList();
                            newClassIdList.add(BelisEEUtils.getEntityId(curEntity));
                            entityIDs.put(curEntity.getClass(), newClassIdList);
                        }


                        for (Class curClass : entityIDs.keySet()) {
                            List curClassResults = em.createNamedQuery(
                                    curClass.getSimpleName() + ".refresh").setParameter("ids", entityIDs.get(curClass)).getResultList();
                            System.out.println("found: " + curClassResults);
                            //ToDo
                            addCollectionToSortedSet(refreshedObjects, curClassResults);
                            //refreshedObjects.addAll(curClassResults);
                        }

                    //ToDo Threading
                    //if (curEntity.isWasModified()) {
                    //refreshedObjects.add(em.find(curEntity.getClass(), BelisEEUtils.getEntityId(curEntity)));
                    //} else {
                    //   System.out.println("No need to refresh Entity: " + curEntity + " was not modified.");
                    //    refreshedObjects.add(curEntity);
                    //}
                    //persist(curEntity);
                    //em.refresh(curEntity);
                    } catch (Exception ex) {
                        System.out.println("Error while refreshing Entity: " + curEntity);
                        ex.printStackTrace();
                        errornousEntities.add(curEntity);
                    }
                }
                if (errornousEntities.size() == 0) {
                    System.out.println("refreshing of entities successful");
                } else {
                    System.out.println("There were problems during refreshing the entities errorCount: " + errornousEntities.size());
                    throw new ActionNotSuccessfulException("There were problems during refreshing the entities", errornousEntities);
                }
                return new HashSet(refreshedObjects);
            } else {
                return new HashSet();
            }
        } catch (Exception ex) {
            System.out.println("Error while refreshing entities");
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Error while refreshing entities", ex);
        }
    }

    public TreeSet getObjectsByBoundingBox(BoundingBox bb) throws ActionNotSuccessfulException {
        final TreeSet result = new TreeSet(new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
        try {
            //ToDo create namedNativeQuery for reusability
            System.out.println("GeometryText: " + bb.getGeometryFromTextLineString());
            List<GeomToEntityIndex> geomToEntityIndices = (List<GeomToEntityIndex>) em.createNativeQuery(
                    //ToDo optimize
                    //"SELECT id,geometry FROM GeomToEntityIndex WHERE envelope(geometryfromtext(?,-1)) && geometry", GeomToEntityIndex.class).setParameter(1, bb.getGeometryFromTextLineString()).getResultList();
                    "SELECT geom_to_entity_index.id,geom_to_entity_index.entityclass,geom_to_entity_index.entityid,geom_to_entity_index.fk_geom FROM geom_to_entity_index,geom WHERE geom.id = geom_to_entity_index.fk_geom AND envelope(geometryfromtext(?,-1)) && geom.geo_field", GeomToEntityIndex.class).setParameter(1, bb.getGeometryFromTextLineString()).getResultList();
            if (geomToEntityIndices != null && geomToEntityIndices.size() > 0) {
                System.out.println("There are results. size: " + geomToEntityIndices.size());
            } else {
                System.out.println("There are no results. size: " + geomToEntityIndices.size());
                return result;
            }

            System.out.println("Start searching for entities");
            final HashMap<Class, ArrayList> entityIDs = new HashMap();
            for (GeomToEntityIndex currentIndex : geomToEntityIndices) {
//                final Object foundedEntity = getObjectForIndex(currentIndex);
//                if (foundedEntity != null) {
//                    System.out.println("Adding Entity: " + foundedEntity + " to result");
//                    result.add(foundedEntity);
//                }
                if (entityIDs.containsKey(currentIndex.getEntityClass())) {
                    final ArrayList classIdList = entityIDs.get(currentIndex.getEntityClass());
                    classIdList.add(currentIndex.getEntityID());
                } else {
                    final ArrayList newClassIdList = new ArrayList();
                    newClassIdList.add(currentIndex.getEntityID());
                    entityIDs.put(currentIndex.getEntityClass(), newClassIdList);
                }
            }

            for (Class curClass : entityIDs.keySet()) {
                System.out.println("Class to search: "+ curClass.getSimpleName()+", id: "+curClass+" ,entityIDs: "+entityIDs.get(curClass));
                List curClassResults = em.createNamedQuery(
                        curClass.getSimpleName() + ".refresh").setParameter("ids", entityIDs.get(curClass)).getResultList();
                System.out.println("found: " + curClassResults);
                addCollectionToSortedSet(result, curClassResults);
                //result.addAll(curClassResults);
            }
            System.out.println("Entities in result set: "+result.size());
            return result;
        } catch (Exception ex) {
            System.out.println("Failure during boundingBox querying: " + bb);
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Failure during boundingBox querying");
        }
    }

    //ToDo Leuchten problem
    //ToDo would be cooler with queryable superclass seems not to work with mappedSupperclasses --> would only be one query dosen'T matter how much entites
    public Object getObjectsByGeom(Geom geom) throws ActionNotSuccessfulException {
        try {
            final GeomToEntityIndex geomIndex = getGeomIndexById(geom.getId());
            if (geomIndex != null) {
                return em.find(geomIndex.getEntityClass(), geomIndex.getEntityID());
            }
            return null;
        //System.out.println("found Standort for geometry");

//            System.out.println("Standort.findStandortByGeom.");
//            //ToDo are there only
//            try {
//                final Standort standort = (Standort) em.createNamedQuery("Standort.findStandortByGeom").setParameter("geometrie", geom).getSingleResult();
//                if (standort != null) {
//                    //System.out.println("found Standort for geometry");
//                    return standort;
//                } else {
//                    //System.out.println("No Standort found for geometry");
//                }
//            } catch (NoResultException ex) {
//                //System.out.println("No Standort found for geometry " + geom);
//            }
//            try {
//                final Schaltstelle schaltstelle = (Schaltstelle) em.createNamedQuery("Schaltstelle.findSchaltstelleByGeom").setParameter("geometrie", geom).getSingleResult();
//                if (schaltstelle != null) {
//                    //System.out.println("found schaltstelle for geometry");
//                    return schaltstelle;
//                } else {
//                    //System.out.println("No schaltstelle found for geometry");
//                }
//            } catch (NoResultException ex) {
//                //System.out.println("No Schaltstelle found for geometry " + geom);
//            }
//            try {
//                final Mauerlasche mauerlasche = (Mauerlasche) em.createNamedQuery("Mauerlasche.findStandortByGeom").setParameter("geometrie", geom).getSingleResult();
//                if (mauerlasche != null) {
//                    //System.out.println("found mauerlasche for geometry");
//                    return mauerlasche;
//                } else {
//                    //System.out.println("No mauerlasche found for geometry");
//                }
//            } catch (NoResultException ex) {
//                //System.out.println("No Mauerlasche found for geometry " + geom);
//            }
//            try {
//                final Leitung leitung = (Leitung) em.createNamedQuery("Leitung.findStandortByGeom").setParameter("geometrie", geom).getSingleResult();
//                if (leitung != null) {
//                    //System.out.println("found leitung for geometry");
//                    return leitung;
//                } else {
//                    //System.out.println("No leitung found for geometry");
//                }
//            } catch (NoResultException ex) {
//                //System.out.println("No Leitung found for geometry " + geom);
//            }
//            //System.out.println("No Entities found for Geom");
//            return null;
        } catch (NonUniqueResultException ex) {
            System.out.println("more than one result from geom query" + geom);
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("more than one result from geom query", ex);
        } catch (Exception ex) {
            System.out.println("Failure during geom querying: " + geom);
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Failure during geom querying", ex);
        }
    }

    public Object getObjectForIndex(GeomToEntityIndex index) throws ActionNotSuccessfulException {
        try {
            if (index != null) {
                return em.find(index.getEntityClass(), index.getEntityID());
            }
            return null;
        //System.out.println("found Standort for geometry");

//            System.out.println("Standort.findStandortByGeom.");
//            //ToDo are there only
//            try {
//                final Standort standort = (Standort) em.createNamedQuery("Standort.findStandortByGeom").setParameter("geometrie", geom).getSingleResult();
//                if (standort != null) {
//                    //System.out.println("found Standort for geometry");
//                    return standort;
//                } else {
//                    //System.out.println("No Standort found for geometry");
//                }
//            } catch (NoResultException ex) {
//                //System.out.println("No Standort found for geometry " + geom);
//            }
//            try {
//                final Schaltstelle schaltstelle = (Schaltstelle) em.createNamedQuery("Schaltstelle.findSchaltstelleByGeom").setParameter("geometrie", geom).getSingleResult();
//                if (schaltstelle != null) {
//                    //System.out.println("found schaltstelle for geometry");
//                    return schaltstelle;
//                } else {
//                    //System.out.println("No schaltstelle found for geometry");
//                }
//            } catch (NoResultException ex) {
//                //System.out.println("No Schaltstelle found for geometry " + geom);
//            }
//            try {
//                final Mauerlasche mauerlasche = (Mauerlasche) em.createNamedQuery("Mauerlasche.findStandortByGeom").setParameter("geometrie", geom).getSingleResult();
//                if (mauerlasche != null) {
//                    //System.out.println("found mauerlasche for geometry");
//                    return mauerlasche;
//                } else {
//                    //System.out.println("No mauerlasche found for geometry");
//                }
//            } catch (NoResultException ex) {
//                //System.out.println("No Mauerlasche found for geometry " + geom);
//            }
//            try {
//                final Leitung leitung = (Leitung) em.createNamedQuery("Leitung.findStandortByGeom").setParameter("geometrie", geom).getSingleResult();
//                if (leitung != null) {
//                    //System.out.println("found leitung for geometry");
//                    return leitung;
//                } else {
//                    //System.out.println("No leitung found for geometry");
//                }
//            } catch (NoResultException ex) {
//                //System.out.println("No Leitung found for geometry " + geom);
//            }
//            //System.out.println("No Entities found for Geom");
//            return null;
        } catch (NonUniqueResultException ex) {
            System.out.println("more than one result from geom query" + index);
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("more than one result from geom query", ex);
        } catch (Exception ex) {
            System.out.println("Failure during geom querying: " + index);
            ex.printStackTrace();
            throw new ActionNotSuccessfulException("Failure during geom querying", ex);
        }
    }

    private void determineNextLaufendenummer(Standort standort) throws ActionNotSuccessfulException {
        System.out.println("determine next laufendenummer");
        if (standort != null) {
            //ToDo would be cooler to use the objects itself as parameter;
            String strasse = null;
            if (standort.getStrassenschluessel() == null || (strasse = standort.getStrassenschluessel().getPk()) == null) {
                System.out.println("strassenschluessel must be != null");
            }
            Short kennziffer = null;
            if (standort.getKennziffer() == null || (kennziffer = standort.getKennziffer().getKennziffer()) == null) {
                System.out.println("kennziffer must be != null");
            }
            if (kennziffer != null && strasse != null) {
                try {
                    final Short highestNumber = (Short) em.createNamedQuery(
                            "Standort.findHighestLaufendenummer").setParameter("strassenschluessel", strasse).setParameter("kennziffer", kennziffer).getSingleResult();
                    if (highestNumber == null) {
                        System.out.println("there is no highest laufende nummer using 0.");
                        standort.setLaufendeNummer((short) 0);
                    } else {
                        System.out.println("the highest laufende nummer is: " + highestNumber);
                        //ToDo best way to add Short ?
                        standort.setLaufendeNummer((short) (highestNumber + ((short) 1)));
                    }
                    setLeuchtenPropertiesDependingOnStandort(standort);
                    return;
                } catch (NoResultException ex) {
                    System.out.println("there is no result. Setting laufendenummer to 0.");
                    standort.setLaufendeNummer((short) 0);
                    setLeuchtenPropertiesDependingOnStandort(standort);
                    return;
                } catch (Exception ex) {
                    System.out.println("Error while querying entity");
                    ex.printStackTrace();
                    throw new ActionNotSuccessfulException("Error while querying highest laufendenummer", ex);
                }
            }
        }
        throw new ActionNotSuccessfulException("Not possible to determine laufendenummer kennziffer and strassenschlüssel of standort must be set.");
    }

    private void setLeuchtenPropertiesDependingOnStandort(Standort standort) {
        if (standort != null) {

            final Set<Leuchte> leuchten = standort.getLeuchten();
            if (leuchten != null) {
                System.out.println("Setting properties of Leuchte.");
                if (standort.isStandortMast()) {
                    System.out.println("Standort is Mast.");
                } else {
                    System.out.println("Standort is no Mast.");
                }
                //ToDo check if there is only one Leuchte per no mast standort
                for (Leuchte curLeuchte : leuchten) {
                    if (standort.isStandortMast()) {
                        curLeuchte.setStrassenschluessel(standort.getStrassenschluessel());
                        curLeuchte.setKennziffer(standort.getKennziffer());
                    }
                    curLeuchte.setLaufendeNummer(standort.getLaufendeNummer());
                }
            } else {
                System.out.println("No leuchten to set properties.");
            }
        }
    }

    private void updateGeomIndex(GeoBaseEntity entity, boolean isNew)
            throws ActionNotSuccessfulException {
        if (isNew) {
            if (entity.getGeometrie() != null) {
                System.out.println("Persisted GeoBaseEntity has Geom object. Creating entry in index");
                final GeomToEntityIndex newIndex = new GeomToEntityIndex();
                newIndex.setGeometry(entity.getGeometrie());
                newIndex.setEntityClass(entity.getClass());
                final Object entityID = BelisEEUtils.getEntityId(entity);
                if (entityID != null) {
                    newIndex.setEntityID((Long) entityID);
                } else {
                    throw new ActionNotSuccessfulException("Entity has no id, can't create geom index");
                }
                em.persist(newIndex);
            }
        } else {
            System.out.println("Persisted GeoBaseEntity is saved checking Geom index");
            if (entity.getGeometrie() != null) {
                System.out.println("Geometry is available.");
                final GeomToEntityIndex index = getGeomIndexById(entity.getGeometrie().getId());
                if (index != null) {
                    System.out.println("IndexAvailable.Updating Geometry");
                    index.setGeometry(entity.getGeometrie());
                    em.merge(index);
                    em.flush();
                } else {
                    System.out.println("Warning no index available. Creating Index");
                    updateGeomIndex(entity, true);
                }
            } else {
                System.out.println("Entity has no geometry set. Doing nothing");
            }
        }
    }

    private GeomToEntityIndex getGeomIndexById(Integer id) throws ActionNotSuccessfulException {
        if (id != null) {
            try {
                GeomToEntityIndex geomIndex = (GeomToEntityIndex) em.createNamedQuery(
                        "GeomToEntityIndex.findGeomToEntityIndexByGeomId").setParameter("id", id).getSingleResult();
                return geomIndex;
            } catch (NoResultException ex) {
                return null;
            } catch (Exception ex) {
                System.out.println("Error while querying entity");
                ex.printStackTrace();
                throw new ActionNotSuccessfulException("Error while querying entity");
            }
        }
        return null;
    }

    private static void addCollectionToSortedSet(SortedSet sortedSet,Collection collection){
        if(sortedSet != null && collection != null && collection.size() >0){
            System.out.println("adding Collection: "+collection+"to sorted set: "+sortedSet);
            for(Object curObject:collection){                 
                final boolean wasAdded = sortedSet.add(curObject);
//                if(sortedSet instanceof TreeSet){
//                    System.out.println("hascode: "+curObject.hashCode());
//                    System.out.println("toString: "+curObject.toString());
//                    System.out.println("contains Object: "+((TreeSet)sortedSet).contains(curObject));
//                    if(((TreeSet)sortedSet).contains(curObject)){
//                        Iterator it =((TreeSet)sortedSet).descendingIterator();
//                        while(it.hasNext()){
//                            Object curObj = it.next();
//                            System.out.println("hascode: "+curObj.hashCode());
//                            System.out.println("toString: "+curObj.toString());
//                        }
//
//                    }
//                }
                System.out.println("Added element "+curObject+" to set: "+wasAdded);
//                if(sortedSet.size() > 0){
//                    System.out.println("Elment are equals: "+sortedSet.first().equals(curObject));
//                    //System.out.println("Elements compared: "+ new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
//                }
            }
        }
    }
}
