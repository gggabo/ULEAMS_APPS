package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import controllers.ActividadController;
import controllers.DocenteActividadController;
import controllers.PeriodoController;
import controllers.RolController;
import controllers.UsuarioController;
import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;
import models.Actividad;
import models.DocenteActividad;
import models.Periodo;
import models.Rol;
import models.Usuario;
import utils.dialogWindow;
import utils.message;

public class VwAsignacionActividad extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = 1L;
	
	public VwAsignacionActividad() {
		
		cargarDatos();
		initBuscarUsuario();
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
	
	//public VerticalLayout usuarioLayout = new VerticalLayout();
	public CssLayout filteringDocenteActividad = new CssLayout();
	public Button clearFilterDocenteActividad = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filtertxtDocenteActividad = new TextField();
	
	public Grid<DocenteActividad> gridActividadDocente = new Grid<DocenteActividad>();
	public List<DocenteActividad> listActividadDocente = new ArrayList<>();
	
	public TwinColSelect<Actividad> twActividad = new TwinColSelect<>();
	public List<Actividad> listActividad = new ArrayList<>();
	
	public Rol rolDocente = RolController.getSpecificRolById(4L);
	public Rol rolAdmin= RolController.getSpecificRolById(1L);
	public List<Rol> listRoles = new ArrayList<>();
		
	public Component buildUI() {
		
		toolbar.setWidth("100%");
		toolbar.setSpacing(true);
		toolbar.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbar.setResponsive(true);
		toolbar.addComponents(new Label("Periodo "),cmbPeriodo);
		toolbar.setExpandRatio(cmbPeriodo, 1);
		
		/*listRoles.add(rolDocente);
		listRoles.add(rolAdmin);*/
		cmbPeriodo.addValueChangeListener(new ValueChangeListener<Periodo>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent<Periodo> event) {
				listActividadDocente = DocenteActividadController.getAllByPeriodo(cmbPeriodo.getValue().getIdPerido(), rolAdmin, rolDocente);
				gridActividadDocente.setItems(listActividadDocente);
			}
		});
		
		toolbarGestion.setWidth("100%");
		toolbarGestion.setSpacing(true);
		toolbarGestion.setStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		toolbarGestion.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		toolbarGestion.setResponsive(true);
		toolbarGestion.addComponents(mainMenuGestion);
		
		mainMenuGestion.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		mainMenuGestion.addStyleName(ValoTheme.MENUBAR_SMALL);
		mainMenuGestion.setResponsive(true);
		
		mainMenuGestion.addItem("Agregar docente", VaadinIcons.PLUS, new Command() {
			private static final long serialVersionUID = 1L;
			@Override 
			public void menuSelected(MenuItem selectedItem) {
				buscarUsuario();
				/*userNewEdit(null);
				accion="guardar";*/
			}
		});		
		
		mainMenuGestion.addItem("Guardar", VaadinIcons.CHECK, new Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				buscarUsuario();
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
		
		filtertxtDocenteActividad.setPlaceholder("Buscar por nombres o cedula");
		filtertxtDocenteActividad.setValueChangeMode(ValueChangeMode.LAZY);
		filtertxtDocenteActividad.setSizeFull();
		filtertxtDocenteActividad.addValueChangeListener(e ->{
			listActividadDocente.clear();
			listActividadDocente.addAll(DocenteActividadController.search(cmbPeriodo.getValue().getIdPerido(),filtertxtDocenteActividad.getValue()));
			gridActividadDocente.setItems(listActividadDocente);
		}); 
		
		clearFilterDocenteActividad.addClickListener(e->{
			filtertxtDocenteActividad.clear();
		});
		
		filteringDocenteActividad.addComponents(filtertxtDocenteActividad,clearFilterDocenteActividad);
		filteringDocenteActividad.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filteringDocenteActividad.addStyleName("custom-margins");
				   
		//gridActividadDocente.addColumn(Usuario::getCedula).setCaption("CÉDULA/DNI");
		gridActividadDocente.setWidth("100%");
		gridActividadDocente.setBodyRowHeight(50);
		gridActividadDocente.addColumn(DocenteActividad -> DocenteActividad.getDocente().getCedula()).setCaption("CÉDULA");
		gridActividadDocente.addColumn(DocenteActividad -> DocenteActividad.getDocente().getApellido_paterno() +" "+ DocenteActividad.getDocente().getApellido_materno() +" "+
				DocenteActividad.getDocente().getNombre_uno() +" "+ DocenteActividad.getDocente().getNombre_dos()
				).setCaption("NOMBRES Y APELLIDOS").setId("NOMBRES");
		//gridActividadDocente.addColumn(DocenteActividad-> DocenteActividad.getActividades().toString()).setCaption("ACTIVIDADES DOCENTES ASIGNADAS");
		gridActividadDocente.addComponentColumn(DocenteActividad ->{
			return DocenteActividad.getActividadesRender();			
		}).setCaption("ACTIVIDADES DOCENTES ASIGNADAS");
		
		gridActividadDocente.setSelectionMode(SelectionMode.NONE);
		gridActividadDocente.addComponentColumn(Usuario -> {
	 
			Button b = new Button("Editar");
			b.addClickListener(clickb ->{  
				/*userNewEdit(Usuario);
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
				accion = "modificar";*/
				
			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);
			
			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 ->{
				/*listUsuarios.remove(Usuario);
				gridUsuario.setItems(listUsuarios);
				Usuario.setEstado(0);
				UsuarioController.update(Usuario);
				message.warringMessage("Usuario eliminado");*/
			});
			b2.setStyleName(ValoTheme.BUTTON_DANGER);
			b2.addStyleName(ValoTheme.BUTTON_SMALL);
			b2.setIcon(VaadinIcons.ERASER);
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponents(b,b2);
			return hl;			
		}).setCaption("Opciones");
		
		horarioLayout.addComponents(toolbarGestion, filteringDocenteActividad, gridActividadDocente);
		horarioLayout.setMargin(false);
		
		pnlGestionHorario.setCaption("Asignación de actividades docentes");
		pnlGestionHorario.setIcon(VaadinIcons.TASKS);
		pnlGestionHorario.setContent(horarioLayout);
		
		layoutPrincipal.addComponents(toolbar, pnlGestionHorario);//,filtering,gridUsuario);
		layoutPrincipal.setMargin(false);
		
		pnlPrincipal.setCaption("Gestión de actividades docente");
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
		
		listActividadDocente = DocenteActividadController.getAllByPeriodo(cmbPeriodo.getValue().getIdPerido(), rolAdmin, rolDocente);
		gridActividadDocente.setItems(listActividadDocente);
		
		listActividad = ActividadController.findAll();
		twActividad.setItems(listActividad);
		twActividad.setItemCaptionGenerator(Actividad::getActividad);
		
	}
	
	public VerticalLayout usuarioLayout = new VerticalLayout();
	public CssLayout filtering = new CssLayout();
	public Button clearFilter = new Button(VaadinIcons.CLOSE_CIRCLE);
	public TextField filtertxt = new TextField();
	public Grid<Usuario> gridUsuario = new Grid<>();
	public List<Usuario> listUsuarios = new ArrayList<>();
	
	private void initBuscarUsuario() {
		filtertxt.setPlaceholder("Buscar por nombres o cedula");
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
	 
			Button b = new Button("Seleccionar");
			b.addClickListener(clickb ->{ 
				
				DocenteActividad da = new DocenteActividad();
				da.setDocente(Usuario);
				da.setPeriodo(cmbPeriodo.getValue());
				
				if(listActividadDocente.contains(da)){
					message.warringMessage("El registro ya está seleccionado");
					return;
				}
				
				twActividad.clear();
				twActividad.setRows(6);
				twActividad.setWidth("530px");
				
				HorizontalLayout hl = new HorizontalLayout();
				hl.setCaption("Actividades a asignar");
				hl.addComponent(twActividad);
				
				/*Iterator<Componente> iterator = ComponenteController.getAllComponentByEquipo(Equipo.getIdEquipo()).iterator();
				List<Componente> listComp = new ArrayList<>();
				TwinColSelect<Componente> twComp = new TwinColSelect<>();
				while(iterator.hasNext()) {
					Componente c = iterator.next();
					listComp.add(c);
				}	
				twComp.setItems(listComp);
				twComp.setItemCaptionGenerator(Componente::getNombre);
				twComp.setRows(6);
				twComp.setWidth("530px");

				hl.addComponents(twComp);
                */
				MessageBox.createQuestion()
				.withCaption("Actividades docente")
				.withCancelButton(ButtonOption.caption("Cancelar"))
				.withMessage(hl)
				.withOkButton(() -> {
					List<Actividad> listActividadSave = new ArrayList<>(twActividad.getValue());
					da.setActividades(listActividadSave);
					
					listActividadDocente.add(da);
					gridActividadDocente.setItems(listActividadDocente); 
					
					DocenteActividadController.save(da);
					
					/*List<Componente> complist = new ArrayList<>(twComp.getSelectedItems());
					te.setComponentes(complist);*/

			    },ButtonOption.caption("Aceptar"))
				.open();
				
				//da.setActividades(null);
				
				/*ProyectoParticipante pp = new ProyectoParticipante(Usuario, false);
				
				if(!listProyectoParticipante.contains(pp)) { 
				   listProyectoParticipante.add(pp);
				   gridParticipante.setItems(listProyectoParticipante); 
				}else {
					message.warringMessage("El registro ya está seleccionado");
				}*/
				

				
			});
			b.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			b.addStyleName(ValoTheme.BUTTON_SMALL);
			b.setIcon(VaadinIcons.EDIT);
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponents(b);
			return hl;			
		}).setCaption("Opciones");

		usuarioLayout.addComponents(filtering,gridUsuario);
		usuarioLayout.setMargin(false);
	}
	
	private void buscarUsuario() {
		cargarDatosUsuario();
		dialogWindow dialogReactivoWindow = new dialogWindow("Búsqueda de docentes", VaadinIcons.USERS);
		
		dialogReactivoWindow.getCancelButton().addClickListener(e -> {
			dialogReactivoWindow.close();
		});
		
		VerticalLayout vroot = new VerticalLayout();
		vroot.addComponents(usuarioLayout);
		vroot.setMargin(false);
		vroot.setSpacing(false);
		
		dialogReactivoWindow.setResponsive(true);
		dialogReactivoWindow.setWidth("65%");
		dialogReactivoWindow.addComponentBody(vroot);
		dialogReactivoWindow.getOkButton().setVisible(false);
		dialogReactivoWindow.getCancelButton().setCaption("Cerrar");
		dialogReactivoWindow.getFooterText().setValue("Opciones");
		dialogReactivoWindow.getLayoutComponent().setMargin(false);
		UI.getCurrent().addWindow(dialogReactivoWindow);
	}
	
	private void cargarDatosUsuario() {
		listUsuarios = UsuarioController.findAll();
		gridUsuario.setItems(listUsuarios);
	}

}
