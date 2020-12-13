package net.aydini.modescisc.cif.web.action.framework;

import java.util.List;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 * @param <E>
 */
public abstract class PaginationHelper<E> {
    private int pageSize;
    private int page;

    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    public abstract int getItemsCount();

    public abstract List<E> createPageDataModel();

    public int getPageFirstItem() {
        return this.page * this.pageSize;
    }

    public int getPageLastItem() {
        int i = this.getPageFirstItem() + this.pageSize - 1;
        int count = this.getItemsCount() - 1;
        if (i > count) {
            i = count;
        }

        if (i < 0) {
            i = 0;
        }

        return i;
    }

    public boolean isHasNextPage() {
        return (this.page + 1) * this.pageSize + 1 <= this.getItemsCount();
    }

    public void nextPage() {
        if (this.isHasNextPage()) {
            ++this.page;
        }

    }

    public void lastPage() {
        if (this.isHasNextPage()) {
            if (this.pageSize > 0) {
                this.page = this.getItemsCount() / this.pageSize;
            } else {
                this.page = 0;
            }
        }

    }

    public void firstPage() {
        this.page = 0;
    }

    public boolean isHasPreviousPage() {
        return this.page > 0;
    }

    public void previousPage() {
        if (this.isHasPreviousPage()) {
            --this.page;
        }

    }

    public int getPageSize() {
        return this.pageSize;
    }
}
