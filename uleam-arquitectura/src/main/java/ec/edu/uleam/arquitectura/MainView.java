package ec.edu.uleam.arquitectura;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import controllers.LoginController;
import views.vwMainAppLayout;

/**
 * The main view contains a button and a click listener.
 */
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@HtmlImport("styles/shared-styles.html")
@StyleSheet("styles/mystyle.css")

@Route("")
//@PWA(name = "Sistema gestiÃ³n docente", shortName = "SGDA")
public class MainView extends Div {
	private static final long serialVersionUID = 1L;
	float var;
	byte[] data; 
	
	vwMainAppLayout mainApp = new vwMainAppLayout();
	
    public MainView() { 
        setSizeFull();
        addClassName("main-background");
        LoginI18n viewLogin = LoginI18n.createDefault();
        viewLogin.setHeader(new LoginI18n.Header());
        viewLogin.getHeader().setTitle("Sgda");  
        viewLogin.getHeader().setDescription("Sistema de gestión docente de la Facultad de Arquitectura");
        viewLogin.getForm().setUsername("Usuario");
        viewLogin.getForm().setTitle("Iniciar sesión");
        viewLogin.getForm().setPassword("Clave");
        viewLogin.getForm().setSubmit("Iniciar sesión");
        viewLogin.getForm().setForgotPassword("Olvidé mi clave");
        viewLogin.getErrorMessage().setTitle("Usuario/clave inválido");
        viewLogin.getErrorMessage().setMessage("Revise el usuario o clave e intente nuevamente");
        viewLogin.setAdditionalInformation("Derechos reservados Facultad de Arquitectura ULEAM 2019");
              
        LoginOverlay login = new LoginOverlay();
        login.setForgotPasswordButtonVisible(false);
        login.setI18n(viewLogin);
        login.addLoginListener(e->{
        	boolean isAuthenticated = authenticate(e);
            if (isAuthenticated) {
            Notification n = new Notification();
            	n.setDuration(2000);
            	n.setPosition(Position.BOTTOM_CENTER);
            	n.setText("Bienvenido "+VaadinSession.getCurrent().getAttribute("NOMBRE_PERSONA"));
            	n.open();
            	
            	login.close();
            	add(mainApp);
            	//add(new RouterLink("app", vwMainAppLayout.class));
            	
                //navigateToMainPage();
            } else {
                login.setError(true);
                login.setEnabled(true);
            }
        });   
        
        
        if(VaadinSession.getCurrent().getAttribute("LOGIN")!=null) {
        	 login.setOpened(false);
        	 add(mainApp);
        }else {
        	 login.setOpened(true);
        }
        
        //setContent(login);
        
       /* */
        
        
        
    }
    
	private boolean authenticate(LoginEvent e) {
		
		if(LoginController.login(e.getUsername(),e.getPassword())) {
			return true;
		}else
		
		return false;
	}
}

