package net.aydini.modescisc.cif.web.action.framework;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import net.aydini.modescisc.cif.domain.entity.framework.BaseEntityModel;
import net.aydini.modescisc.cif.service.framework.SortProperty;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 * @param <E>
 */
public abstract class ExtendedLazyDataModel<E extends BaseEntityModel> extends LazyDataModel<E> {
    /**
     * 
     */
    private static final long serialVersionUID = 8350892039584665948L;
    private Number count;

    public ExtendedLazyDataModel() {
    }

    public List<E> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.count = this.rowCount(filters);
        if (this.count == null) {
            return new ArrayList();
        } else {
            this.setRowCount(this.count.intValue());
            if (this.count.intValue() > 0) {
                if (filters != null) {
                    filters.values().removeIf(Objects::isNull);
                }

                List<E> load = this.lazyLoad(first, pageSize, Arrays.asList(new SortProperty(sortField, sortOrder)), filters);
                return load;
            } else {
                return new ArrayList();
            }
        }
    }

    public List<E> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        List<SortProperty> sorts = new ArrayList();
        if (multiSortMeta != null) {
            multiSortMeta.forEach((item) -> {
                sorts.add(new SortProperty(item.getSortField(), item.getSortOrder()));
            });
        }

        this.count = this.rowCount(filters);
        if (this.count == null) {
            return new ArrayList();
        } else {
            this.setRowCount(this.count.intValue());
            if (this.count.intValue() > 0) {
                if (filters != null) {
                    filters.values().removeIf(Objects::isNull);
                }

                List<E> load = this.lazyLoad(first, pageSize, sorts, filters);
                return load;
            } else {
                return new ArrayList();
            }
        }
    }

    public int getRowCount() {
        return this.count == null ? 0 : this.count.intValue();
    }

    public E getRowData(String rowKey) {
        List<E> res = (List)this.getWrappedData();
        List<E> wrappedData = new ArrayList(res);
        Iterator var4 = wrappedData.iterator();

        while(var4.hasNext()) {
            E e = (E) var4.next();
            if (e.getId() instanceof Long) {
                if (e.getId().equals(Long.valueOf(rowKey))) {
                    return e;
                }
            } else if (e.equals(rowKey)) {
                return e;
            }
        }

        return null;
    }

    public Object getRowKey(E instance) {
        return instance == null ? null : instance.getId();
    }

    protected abstract List<E> lazyLoad(int var1, int var2, List<SortProperty> var3, Map<String, Object> var4);

    protected abstract Number rowCount(Map<String, Object> var1);
}
