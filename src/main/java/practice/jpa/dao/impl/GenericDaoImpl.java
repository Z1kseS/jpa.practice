package practice.jpa.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import practice.jpa.dao.GenericDao;

public abstract class GenericDaoImpl<Entity, Key> implements GenericDao<Entity, Key>{

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	protected EntityManager em;
	
	protected abstract Class<Entity> entityClass();
	protected abstract String getEntityName();
	
	@SuppressWarnings("unchecked")
	public List<Entity> getAll(){
		return (List<Entity>) em.createQuery("select c from " + getEntityName()).getResultList();
	}

	public Entity create(Entity obj){
		em.persist(obj);
		return obj;
	}

	public Entity read(Key key){
		return em.find(entityClass(), key);
	}

	public void update(Entity obj){
		em.merge(obj);
	}

	public boolean delete(Key key){
		Entity obj = read(key);
		
		if(obj == null)
			return false ;
		em.remove(read(key));
		
		return true;
	}
}
