package nl.paardenvriendjes.pvapi.dao;

import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.custom.editors.PaardTypeEditor;
import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.Horse;
import nl.paardenvriendjes.pvapi.data.enums.PaardType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
@Slf4j
public class HorseDaoImpl extends AbstractDaoService<Horse> {

    @Autowired
    SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public HorseDaoImpl() {
        super(Horse.class);
    }

    @Override
    public Horse save(Horse horse) {
        // if (!horse.getSports().isEmpty()) {
        // horse.getSports().forEach( (key,value) -> updateHorseSports(key, value));
        horse.setCreatedonDate();
        horse.setActive(true);
        getCurrentSession().persist(horse);
        log.debug("saved One: " + horse.toString());
        return horse;
    }

    @Override
    public void remove(Horse horseToBeRemoved) {
        try {
            horseToBeRemoved.setActive(false);
            horseToBeRemoved.setDeactivatedDate();
            getCurrentSession().saveOrUpdate(horseToBeRemoved);
            log.debug("Deactivated Horse " + horseToBeRemoved.toString());
        } catch (Exception e) {
            log.error("Horse to be deactivated not successfull for id: " + horseToBeRemoved.getId());
        }
    }

    public void reactivate(Long id) {
        try {
            Horse horseToBeReactivated = (Horse) getCurrentSession().load(Horse.class, id);
            horseToBeReactivated.setActive(true);
            getCurrentSession().saveOrUpdate(horseToBeReactivated);
            log.debug("Reactivated Horse " + horseToBeReactivated.toString());
        } catch (Exception e) {
            log.error("Horse to be reactivated not successfull for id: " + id);
        }
    }

    public List<Horse> findHorseByName(String name) {

        Criteria criteria = getCurrentSession().createCriteria(Horse.class);
        if (name.length() < 3) {
            List<Horse> temp = new ArrayList<Horse>();
            return temp;
        } else {
            // query for first name
            criteria.add(Restrictions.ilike("naam", name, MatchMode.ANYWHERE));
            criteria.add(Restrictions.eq("active", true));
            criteria.setFirstResult(0);
            criteria.setMaxResults(20);
            // arrange sort on date;
            criteria.addOrder(Order.desc("name"));
            List<Horse> foundHorses = criteria.list();
            return foundHorses;
        }
    }

    public List<Horse> findHorseByPaardType(String paardtype) {
        if (paardtype.length() < 3) {

            List<Horse> temp = new ArrayList<Horse>();
            return temp;

        } else {
            PaardTypeEditor editor = new PaardTypeEditor();
            PaardType type = editor.returnAsPaardType(paardtype);
            Criteria criteria = getCurrentSession().createCriteria(Horse.class);
            criteria.add(Restrictions.eq("paardtype", type));
            criteria.add(Restrictions.eq("active", true));
            criteria.setFirstResult(0);
            criteria.setMaxResults(20);
            // arrange sort on lastname;
            criteria.addOrder(Order.desc("achternaam"));
            List<Horse> foundHorses = criteria.list();
            return foundHorses;
        }
    }

    public List<Horse> findHorseBySportType(String sporttype) {
        Criteria criteria = getCurrentSession().createCriteria(Horse.class, "horse");
        if (sporttype.length() < 2) {

            List<Horse> temp = new ArrayList<Horse>();
            return temp;
        }
        criteria.createAlias("horse.sports", "sports");
        criteria.add(Restrictions.eq("sports.indices", sporttype));
        criteria.add(Restrictions.eq("active", true));
        criteria.setFirstResult(0);
        criteria.setMaxResults(50);
        // arrange sort on lastname;
        criteria.addOrder(Order.desc("name"));
        List<Horse> foundHorses = criteria.list();
        return foundHorses;
    }


    public List<Horse> findHorseByGeboortedatum(Date geboortedatum) {
        Criteria criteria = getCurrentSession().createCriteria(Horse.class);

        criteria.add(Restrictions.gt("geboortedatum", geboortedatum));
        criteria.add(Restrictions.eq("active", true));
        criteria.setFirstResult(0);
        criteria.setMaxResults(50);
        // arrange sort on lastname;
        criteria.addOrder(Order.desc("name"));
        List<Horse> foundHorses = criteria.list();
        return foundHorses;
    }


//public void updateHorseSports(String sporttype, String sportlevel) {
//	
//	for (String type : SportType.values()) {
//	if (type == sporttype) { 
//	SportType selectedSportType = type; 	
//	}
//	}
//	for (SportLevel level : SportLevel.values()) { 
//	if (level == sportlevel) { 
//	SportLevel selectedSportLevel = level; 	
//	}	
//	}
//	if (selectedSportLevel == null) { 
//	throw IllegalArgumentException () ; 	
//	}
//	else { 
//	addSportToMap(selectedSportType, selectedSportLevel);
//	}
//}
//
//public void deleteHorseSports(String sportype, String sportlevel) {

//	for (SportType type : SportType.values()) {
//	if (type == sporttype) { 
//	SportType selectedSportType = type; 	
//	}
//	if (selectedSportType == null | selected Level == null) { 
//	throw IllegalArgumentException () ; 	
//	}
//	else { 
//	removeSportFromMap(selectedSportType);
//	}
//}

}
