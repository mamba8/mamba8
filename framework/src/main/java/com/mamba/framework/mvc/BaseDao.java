package com.mamba.framework.mvc;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class BaseDao<T extends Id> implements Serializable {
	@PersistenceContext(unitName = "default")
	protected EntityManager em = null;

	protected Class<T> entityClass = null;
	protected String entityName = null;

	public BaseDao() {
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();

		this.entityClass = (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
		entityName = this.entityClass.getSimpleName();
	}

	public T persist(T entity) {
		em.persist(entity);
		return entity;
	}

	public void remove(Serializable entityId) {
		em.remove(em.getReference(entityClass, entityId));
	}

	public T merge(T entity) {
		em.merge(entity);
		return entity;
	}

	public T find(Serializable entityId) {
		return em.find(entityClass, entityId);
	}

	public List<T> findByExample(int fistResult, int maxResult, Map<String, Object> propertyValueMap) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> root = cq.from(entityClass);

		return em.createQuery(buildWhere(cb, cq, root, propertyValueMap)).setFirstResult(fistResult)
				.setMaxResults(maxResult).getResultList();
	}

	public Long countByExample(Map<String, Object> propertyValueMap) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> root = cq.from(entityClass);

		return em.createQuery(buildWhere(cb, cq, root, propertyValueMap).select(cb.count(root))).getSingleResult();
	}

	private <Q> CriteriaQuery<Q> buildWhere(CriteriaBuilder cb, CriteriaQuery<Q> cq, Root<T> root,
			Map<String, Object> propertyValueMap) {
		Predicate[] Predicates = new Predicate[propertyValueMap.size()];
		int index = 0;
		for (Map.Entry<String, Object> entry : propertyValueMap.entrySet()) {
			if (null != entry.getValue()) {
				Predicates[index] = cb.equal(root.get(entry.getKey()), entry.getValue());
			} else {
				Predicates[index] = cb.isNull(root.get(entry.getKey()));
			}
			index++;
		}
		return cq.where(Predicates);
	}

}
