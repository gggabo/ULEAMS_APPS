package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import controllers.PeriodoController;
import controllers.UsuarioController;
import models.Periodo;
import models.Usuario;
import utils.message;

public class VwHorario extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = 1L;
	
	public VwHorario() {
		
		cargarDatos();
		setCss();
		addComponent(buildUI());
		// TODO Auto-generated constructor stub
	}
	
	public VerticalLayout mainLayout = new VerticalLayout();
	public Panel pnlPrincipal = new Panel();
	public FormLayout mainFrm;
	public HorizontalLayout toolbar = new HorizontalLayout();
	public VerticalLayout layoutPrincipal = new VerticalLayout();
	public MenuBar mainMenu = new MenuBar();
	
	public Panel pnlGestionHorario = new Panel();
	public VerticalLayout horarioLayout = new VerticalLayout();
	public HorizontalLayout toolbarGestion = new HorizontalLayout();
	public MenuBar mainMenuGestion = new MenuBar();
	
	public ComboBox<Periodo> cmbPeriodo = new ComboBox<>();
	public List<Periodo> listPeriodo = new ArrayList<>();
	
	public Component buildUI() {
		
		toolbar.setWidth("100%");
		toolbar.setSpacing(true);
		toolbar.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbar.setResponsive(true);
		toolbar.addComponents(cmbPeriodo);
		
		toolbarGestion.setWidth("100%");
		toolbarGestion.setSpacing(true);
		toolbarGestion.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarGestion.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarGestion.setResponsive(true);
		toolbarGestion.addComponents(mainMenuGestion);
		
		mainMenuGestion.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuGestion.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuGestion.setResponsive(true);
		
		mainMenuGestion.addItem("Agregar docente", VaadinIcons.USER_CHECK, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				/*userNewEdit(null);
				accion="guardar";*/
			}
		});		
		
	//	mainMenu.addItem("Imprimir", VaadinIcons.PRINT, null);		
		
		/*mainMenu.addItem("Importar usuarios", VaadinIcons.INSERT, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {*/
				//importUserView();
				
				/*userNewEdit(null);
				accion="guardar";*/
			/*}
		});*/
		
	/*	filtertxt.setPlaceholder("Buscar por nombres o cedula");
		filtertxt.setValueChangeMode(ValueChangeMode.LAZY);
		filtertxt.setSizeFull();
		filtertxt.addValueChangeListener(e ->{
			listUsuarios.clear();
			listUsuarios.addAll(UsuarioController.search(filtertxt.getValue()));
			gridUsuario.setItems(listUsuarios);
		}); 
		
		clearFilter.addClickListener(e->{
			filtertxt.clear();
		});
		
		filtering.addComponents(filtertxt,clearFilter);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filtering.addStyleName("custom-margins");
				   
		gridUsuario.addColumn(Usuario::getCedula).setCaption("CÉDULA/DNI");
		gridUsuario.addColumn(Usuario -> Usuario.getApellido_paterno() +" "+ Usuario.getApellido_materno() +" "+
		Usuario.getNombre_uno() +" "+ Usuario.getNombre_dos()
				).setCaption("NOMBRES Y APELLIDOS").setId("NOMBRES");
		gridUsuario.addColumn(Usuario::getNombre_usuario).setCaption("USUARIO");
		
		gridUsuario.setWidth("100%");
		gridUsuario.setSelectionMode(SelectionMode.NONE);
		gridUsuario.addComponentColumn(Usuario -> {
	 
			Button b = new Button("Editar");
			b.addClickListener(clickb ->{  
				userNewEdit(Usuario);
				cedula.setValue(Usuario.getCedula());
				apellido_paterno.setValue(Usuario.getApellido_paterno());
				apellido_materno.setValue(Usuario.getApellido_materno());
				nombre_uno.setValue(Usuario.getNombre_uno());
				nombre_dos.setValue(Usuario.getNombre_dos());
				correo.setValue(Usuario.getCorreo()); 
				telefono.setValue(Usuario.getTelefono());
				nombre_usuario.setValue(Usuario.getNombre_usuario());
				clave.setValue("");
				uploadField.setValue(Usuario.getImagen());	
				
				listGridRol.addAll(Usuario.getRoles()); 
				
				listLabsTw.addAll(UsuarioController.getAllLabsByRol(Usuario.getId()));
				HashSet<Laboratorio> labsSelect = new HashSet<Laboratorio>(listLabsTw);
				
				twLaboratorio.setValue(labsSelect);
				
				Iterator<Rol> rolIterator = listGridRol.iterator(); 
				Rol r; 
				while(rolIterator.hasNext()) {
					r=rolIterator.next();
					if(r.getIdRol()==4) { 
						twLaboratorio.setVisible(true);
					}
				}
				
				gridRol.setItems(listGridRol);
				accion = "modificar";
				
			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);
			
			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 ->{
				listUsuarios.remove(Usuario);
				gridUsuario.setItems(listUsuarios);
				Usuario.setEstado(0);
				UsuarioController.update(Usuario);
				message.warringMessage("Usuario eliminado");
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponents(b,b2);
			return hl;			
		}).setCaption("Opciones");*/
		
		horarioLayout.addComponents(toolbarGestion, new Button("asgasdg"));
		horarioLayout.setMargin(false);
		
		pnlGestionHorario.setCaption("Asignación de actividades docentes");
		pnlGestionHorario.setIcon(VaadinIcons.CALENDAR);
		pnlGestionHorario.setContent(horarioLayout);
		
		layoutPrincipal.addComponents(toolbar, pnlGestionHorario);//,filtering,gridUsuario);
		layoutPrincipal.setMargin(false);
		
		pnlPrincipal.setCaption("Gestión docente");
		pnlPrincipal.setIcon(VaadinIcons.SUITCASE);
		pnlPrincipal.setContent(layoutPrincipal);
		
		mainLayout.addComponents(pnlPrincipal);
		return mainLayout;
	}
	
	public void setCss() {
		cmbPeriodo.setWidth("30%");
		cmbPeriodo.setTextInputAllowed(false);
		cmbPeriodo.setEmptySelectionAllowed(false);
		cmbPeriodo.setStyleName(ValoTheme.COMBOBOX_SMALL);
		
	}
	
	public void cargarDatos() {
		listPeriodo = PeriodoController.findAll();
		cmbPeriodo.setItems(listPeriodo);
		cmbPeriodo.setItemCaptionGenerator(Periodo::getPeriodo);
		if (listPeriodo.size() > 0) {
			cmbPeriodo.setSelectedItem(listPeriodo.get(0));
		}
	}

}
