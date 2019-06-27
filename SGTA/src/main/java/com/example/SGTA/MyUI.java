package com.example.SGTA;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import views.MainView;
import views.vwLogin;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("dashboard")
public class MyUI extends UI {
	private static final long serialVersionUID = 1L;

	@Override
    protected void init(VaadinRequest vaadinRequest) {
    	Responsive.makeResponsive(this); 
    	addStyleName(ValoTheme.UI_WITH_MENU); 
    	setLocale(vaadinRequest.getLocale());
    	getPage().setTitle("SGT");
    	
    	//setContent(new vwLogin(MainUI.this));
    	
    	if(VaadinSession.getCurrent().getAttribute("LOGIN")!=null) {
			/*MainView vwprincipal = new MainView(ui)
			UI.getCurrent().setContent(vwprincipal);*/
			showMainView();		
		}else {
			
	        setContent(new vwLogin(MyUI.this));
	        
		}
    	 
    	//showMainView();
    	
    }
    
    public void showMainView() {
        setContent(new MainView(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    /*public AccessControl getAccessControl() {
        return accessControl;
    }*/

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
}
