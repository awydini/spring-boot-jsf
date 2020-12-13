package net.aydini.modescisc.cif.web.action.framework;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */

public abstract class AbstractActionBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2677643722252696411L;
    

    public AbstractActionBean() {
    }

    public Map<String, String> getRequestParameterMap() {
        return this.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }

    public Map<String, String[]> getRequestParameterValuesMap() {
        return this.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
    }

    public String getCurrentViewID() {
        String viewId = this.getCurrentInstance().getViewRoot().getViewId();
        return viewId.substring(viewId.lastIndexOf(47) + 1);
    }

    public Locale getCurrentLocale() {
        FacesContext facesContext = this.getCurrentInstance();
        Locale requestLocale = facesContext.getViewRoot().getLocale();
        return requestLocale == null ? facesContext.getApplication().getDefaultLocale() : requestLocale;
    }

    protected FacesContext getCurrentInstance() {
        return FacesContext.getCurrentInstance();
    }

    public boolean isPostback() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public void resetPagination(String compId) {
        DataTable dataTable = (DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(compId);
        if (dataTable != null) {
            dataTable.reset();
        }

    }

    protected boolean isInternetExplorer(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        return userAgent.indexOf("MSIE") > -1;
    }

    public void closeDialog() {
        RequestContext.getCurrentInstance().closeDialog((Object)null);
    }

    protected static HttpServletRequest getRequestUrl(FacesContext facesContext) {
        Object request = facesContext.getExternalContext().getRequest();
        return request instanceof HttpServletRequest ? (HttpServletRequest)request : null;
    }
}
