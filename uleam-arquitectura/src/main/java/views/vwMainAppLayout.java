package views;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

//@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
//@StyleSheet("styles/mystyle.css")
@Route(value = "app")
public class vwMainAppLayout extends AppLayout  {
	private static final long serialVersionUID = 1L;
	//VIEWS
	public vwUsuarios vwusuarios;
	public vwActividadDocente vwactividadDocente;
	
	VerticalLayout mainLayout = new VerticalLayout(); 
	byte[] data; 
	
	Button closeSession = new Button("Cerrar sesión");
	
	public vwMainAppLayout() {
		
		Image img = new Image("images/logo_header_titulo.png", "Vaadin Logo");
        img.setHeight("44px");
        addToNavbar(new DrawerToggle(), img, closeSession); 
        
        closeSession.addClickListener(e->{
        	UI.getCurrent().getPage().setLocation(
					"/uleam-arquitectura/");
			//vaciarMenu();
			UI.getCurrent().getSession().close();
        });
        
        Tab inicio = new Tab("Inicio");
        inicio.addComponentAsFirst(VaadinIcon.HOME.create());
        Tab actividadDocente = new Tab("Actividades docente");
        actividadDocente.addComponentAsFirst(VaadinIcon.TASKS.create());
        Tab gestorHorario = new Tab("Gestor de horarios");
        gestorHorario.addComponentAsFirst(VaadinIcon.CALENDAR_USER.create());
        Tab usuarios = new Tab("Usuarios");
        usuarios.addComponentAsFirst(VaadinIcon.USER_CARD.create());
        Tabs tabs = new Tabs(inicio, actividadDocente, gestorHorario, usuarios);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);
                        
        //mainLayout.addClassName("main-background");
        setContent(mainLayout);
        mainLayout.setSizeFull();
        
        tabs.addSelectedChangeListener(event -> {
        	if(event.getSelectedTab()==inicio) {
        	
        		setContent(mainLayout);
        	}
        	if(event.getSelectedTab()==gestorHorario) {
        		
        		MemoryBuffer memoryBuffer = new MemoryBuffer();
        		Upload upload = new Upload(memoryBuffer);
        		upload.setMaxFileSize(8000000);      		

        		upload.addFinishedListener(e -> {
        		  InputStream inputStream = memoryBuffer.getInputStream();
        		  try {
					data = IOUtils.toByteArray(inputStream);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		});
        		
        		setContent(upload);
        	}
        	
        	if(event.getSelectedTab()==actividadDocente) {
            	if(vwactividadDocente==null) {
            		vwactividadDocente = new vwActividadDocente();
            	}
        		setContent(vwactividadDocente);
        	}
        	
        	if(event.getSelectedTab()==usuarios) {
            	if(vwusuarios==null) {
            		vwusuarios = new vwUsuarios();
            	}
        		setContent(vwusuarios);
        	}
        	
        	if(event.getSelectedTab()==actividadDocente) {
            	if(vwactividadDocente==null) {
            		vwactividadDocente = new vwActividadDocente();
            	}
        		setContent(vwactividadDocente);
        	}
        	
        	
        });
        
	}
	
	private Component newMenuItem(String nombre, VaadinIcon vicon) {
		HorizontalLayout hl= new HorizontalLayout();
		Icon icon = new Icon(vicon);	
		Span span = new Span(nombre);
		icon.setSize("20px");
		hl.add(icon,span);
		return hl;
	}
	
}
