package com.mamba.framework.mvc;


import javax.transaction.Transactional;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService<T extends Id> {
    public abstract BaseDao<T> getDao();

    @Transactional
    public T persist(T entity) {
        return getDao().persist(entity);
    }

    @Transactional
    public void remove(Serializable entityId) {
        getDao().remove(entityId);
    }

    @Transactional
    public T merge(T entity) {
        return getDao().merge(entity);
    }

    @Transactional
    public T mergeByExample(T example) {
        T persistEntity = getDao().find(example.getId());
        if (null != persistEntity) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(example.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    // 过滤class, id属性
                    if (!key.equals("class") && !key.equals("id")) {
                        Method getter = property.getReadMethod();
                        Object value = getter.invoke(example);
                        if (null != value) {
                            Method setter = property.getWriteMethod();
                            setter.invoke(persistEntity, value);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            persistEntity = getDao().merge(persistEntity);
        }
        return persistEntity;
    }

    public T find(Serializable entityId) {
        return getDao().find(entityId);
    }

    public List<T> findByExample(Paging paging, T example) {
        return getDao().findByExample(paging.getStartIndex(), paging.getPageSize(), buildExampleMap(example));
    }

    public Long countByExample(T example) {
        return getDao().countByExample(buildExampleMap(example));
    }

    public Map<String, Object> buildExampleMap(T example) {
        Map<String, Object> exampleMap = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(example.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(example);
                    if (null != value) {
                        exampleMap.put(key, value);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exampleMap;
    }
}
