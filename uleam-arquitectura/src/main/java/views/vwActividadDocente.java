package views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.claspina.confirmdialog.ButtonOption;
import org.claspina.confirmdialog.ConfirmDialog;
import org.vaadin.olli.TwinColSelect;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;

import components.Panel;
import components.windowDialog;
import controllers.ActividadController;
import controllers.DocenteActividadController;
import controllers.PeriodoController;
import controllers.RolController;
import controllers.UsuarioController;
import models.Actividad;
import models.DocenteActividad;
import models.Periodo;
import models.Rol;
import models.Usuario;
import utils.message;

public class vwActividadDocente extends Div {
	private static final long serialVersionUID = 1L;
	
	public vwActividadDocente() {
		//setSizeFull();
		getElement().getStyle().set("padding", "0px 6px 0px 6px"); 
		add(buildView());
		initComponets();
		loadDefaultItems();
		loadItems(); 
		setEvents();
		// TODO Auto-generated constructor stub
	}
	
	private Select<Periodo> cmbPeriodo = new Select<Periodo>();
	private List<Periodo> listPeriodo = new ArrayList<>();
	
	private Panel mainPanel = new Panel();
	private Panel pnlGestorActividades = new Panel();
	
	private Div mainLayout = new Div();
	
	private Div mainLayoutActividad = new Div();
	private MenuBar toolbar = new MenuBar();
	
    public TextField filtertxtDocenteActividad = new TextField();
	
    private Div gestorActividadLayout = new Div();
	public Grid<DocenteActividad> gridActividadDocente = new Grid<DocenteActividad>();
	public List<DocenteActividad> listActividadDocente = new ArrayList<>();
	
	public Rol rolDocente = RolController.getSpecificRolById(4L);
	public Rol rolAdmin= RolController.getSpecificRolById(1L);
	public List<Rol> listRoles = new ArrayList<>();
	
	public Component buildView() {
				
		pnlGestorActividades.add();
		pnlGestorActividades.setTitle("Actividades docentes");
		pnlGestorActividades.setIcon(VaadinIcon.TASKS);
		
		toolbar.addThemeVariants(MenuBarVariant.LUMO_SMALL, MenuBarVariant.LUMO_TERTIARY);
		MenuItem agregarDocente = toolbar.addItem("Agregar docente", e->{
			searchUser();
		});
		agregarDocente.addComponentAsFirst(VaadinIcon.PLUS.create());
		
		MenuItem imprimir = toolbar.addItem("Imprimir");
		imprimir.addComponentAsFirst(VaadinIcon.PRINT.create()); 
				
		mainLayoutActividad.add(toolbar);
		pnlGestorActividades.setContent(mainLayoutActividad);
		
		mainLayout.add(cmbPeriodo, pnlGestorActividades);
		
		filtertxtDocenteActividad.getStyle().set("padding-left", "2px");
		filtertxtDocenteActividad.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		filtertxtDocenteActividad.setClearButtonVisible(true);
		filtertxtDocenteActividad.setPrefixComponent(VaadinIcon.SEARCH.create());
		filtertxtDocenteActividad.setPlaceholder("Buscar");
		filtertxtDocenteActividad.setWidth("30%");
		filtertxtDocenteActividad.setValueChangeMode(ValueChangeMode.LAZY);
		
		gestorActividadLayout.add(filtertxtDocenteActividad, gridActividadDocente);
		pnlGestorActividades.setContent(gestorActividadLayout);
		
		mainPanel.add(mainLayout);
		mainPanel.setTitle("Gestor de actividades docentes");
		mainPanel.setIcon(VaadinIcon.TASKS);
		return mainPanel; 
	}
	
	TwinColSelect<Actividad> twActividad = new TwinColSelect<>();
	public List<Actividad> listActividad = new ArrayList<>();
	
	public void initComponets() {
		//cmbPeriodo.setLabel("Periodo");
		cmbPeriodo.setWidth("30%");
		cmbPeriodo.getStyle().set("padding-left", "2px");
		
		//DOCENTE ACTIVIDAD
		gridActividadDocente.setWidth("100%"); 
		gridActividadDocente.addColumn(DocenteActividad -> DocenteActividad.getDocente().getCedula()).setHeader("CÉDULA").setResizable(true).setAutoWidth(true);;
		gridActividadDocente.addColumn(DocenteActividad -> DocenteActividad.getDocente().getApellido_paterno() +" "+ DocenteActividad.getDocente().getApellido_materno() +" "+
				DocenteActividad.getDocente().getNombre_uno() +" "+ DocenteActividad.getDocente().getNombre_dos()
				).setHeader("NOMBRES Y APELLIDOS").setResizable(true).setAutoWidth(true);;
		gridActividadDocente.addComponentColumn(DocenteActividad ->{
			return DocenteActividad.getActividadesRender();			
		}).setHeader("ACTIVIDADES DOCENTES ASIGNADAS").setResizable(true).setWidth("320px");
		
		gridActividadDocente.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		gridActividadDocente.setWidth("100%");
		gridActividadDocente.setSelectionMode(Grid.SelectionMode.NONE);
		gridActividadDocente.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COMPACT, GridVariant.MATERIAL_COLUMN_DIVIDERS);
		
		gridActividadDocente.addComponentColumn(Usuario -> {
			 
			Button b = new Button();
			b.addClickListener(clickb ->{  
				twActividad.clear();
				
				Set<Actividad> act = new HashSet<>(Usuario.getActividades());
				twActividad.setValue(act);
				twActividad.addSelectionListener(e->{
					
				});
				
				ConfirmDialog.create()
			    .withCaption("Selección de actividades")
			    .withMessage(twActividad)
			    .withCancelButton(ButtonOption.caption("Cancelar"), ButtonOption.icon(VaadinIcon.CLOSE))
			    .withOkButton(() -> {
			        
			    	Usuario.setActividades(new ArrayList<>(twActividad.getValue()));
					
					DocenteActividadController.update(Usuario);
					
					listActividadDocente = DocenteActividadController.getAllByPeriodo(cmbPeriodo.getValue().getIdPerido(), rolAdmin, rolDocente);
					gridActividadDocente.setItems(listActividadDocente);
			    	
			    }, ButtonOption.focus(), ButtonOption.caption("Aceptar"),ButtonOption.icon(VaadinIcon.CHECK))
			    .open(); 
				
			});
			b.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
			b.addThemeVariants(ButtonVariant.LUMO_SMALL);
			b.setIcon(new Icon(VaadinIcon.TASKS));
			b.getStyle().set("cursor", "pointer");
			
			Button b2 = new Button();
			b2.addClickListener(clickb ->{  
						
				
			});
			b2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			b2.addThemeVariants(ButtonVariant.LUMO_SMALL);
			b2.setIcon(new Icon(VaadinIcon.CALENDAR_USER));
			b2.getStyle().set("cursor", "pointer");
			
			Button b3 = new Button();
			b3.addClickListener(clickb2 ->{
				
			});
			b3.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
			b3.addThemeVariants(ButtonVariant.LUMO_SMALL);
			b3.setIcon(new Icon(VaadinIcon.TRASH));
			b3.getStyle().set("cursor", "pointer");
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.add(b,b2,b3);
			return hl;			 
		}).setHeader("Opciones").setResizable(true).setAutoWidth(true);
		
		//SEARCH DOCENTE
		gridDocenteSearch.setWidth("100%");
		gridDocenteSearch.addColumn(Usuario -> Usuario.getCedula()).setHeader("CÉDULA").setResizable(true).setAutoWidth(true);;
		gridDocenteSearch.addColumn(Usuario -> Usuario.getApellido_paterno() +" "+ Usuario.getApellido_materno() +" "+
				Usuario.getNombre_uno() +" "+ Usuario.getNombre_dos()
				).setHeader("NOMBRES Y APELLIDOS").setResizable(true).setAutoWidth(true);;
		
		gridDocenteSearch.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		gridDocenteSearch.setWidth("100%");
		gridDocenteSearch.setSelectionMode(Grid.SelectionMode.NONE);
		gridDocenteSearch.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COMPACT);
		
		gridDocenteSearch.addComponentColumn(Usuario -> {
			 
			Button b = new Button("Seleccionar");
			b.addClickListener(clickb ->{  
				
				DocenteActividad da = new DocenteActividad();
				da.setDocente(Usuario);
				da.setPeriodo(cmbPeriodo.getValue());
				
				//System.out.println(listActividadDocente);
				
				if(listActividadDocente.contains(da)){
					message.informationMessage("El registro ya está seleccionado");
					return;
				}
				
				listActividadDocente.add(da);
				gridActividadDocente.setItems(listActividadDocente); 
				
				DocenteActividadController.save(da);
				
			});
			b.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
			b.addThemeVariants(ButtonVariant.LUMO_SMALL);
			b.setIcon(new Icon(VaadinIcon.CHECK));

			HorizontalLayout hl = new HorizontalLayout();
			hl.setPadding(false);
			hl.add(b);
			return hl;			
		}).setHeader("Opciones").setResizable(true).setAutoWidth(true);
		
	}
	
	
	public void loadItems() {
		listActividadDocente = DocenteActividadController.getAllByPeriodo(cmbPeriodo.getValue().getIdPerido(), rolAdmin, rolDocente);
		gridActividadDocente.setItems(listActividadDocente);
	}
	
	public void loadDefaultItems() {
		listPeriodo = PeriodoController.findAll();
		cmbPeriodo.setItems(listPeriodo);
		cmbPeriodo.setItemLabelGenerator(Periodo::getPeriodo);
		cmbPeriodo.setEmptySelectionAllowed(false); 
		cmbPeriodo.setValue(PeriodoController.getMaxPeriodo());
		
		listActividad = ActividadController.findAll();
		twActividad.setItems(new HashSet<Actividad>(listActividad));
		 
	}
	
	private void setEvents() {
		cmbPeriodo.addValueChangeListener(e->{
			listActividadDocente = DocenteActividadController.getAllByPeriodo(cmbPeriodo.getValue().getIdPerido(), rolAdmin, rolDocente);
			gridActividadDocente.setItems(listActividadDocente);
		});
		
		filterSearch.addValueChangeListener(e->{
			listDocenteSearch = UsuarioController.search(e.getValue());
			gridDocenteSearch.setItems(listDocenteSearch);
		});
		
		filtertxtDocenteActividad.addValueChangeListener(e->{
			//listActividadDocente.clear();
			listActividadDocente = DocenteActividadController.search(cmbPeriodo.getValue().getIdPerido(),e.getValue());
			gridActividadDocente.setItems(listActividadDocente);
		});
		
	}
	
	private Div layoutSearchMain = new Div();
	private TextField filterSearch = new TextField();
	public Grid<Usuario> gridDocenteSearch = new Grid<Usuario>();
	public List<Usuario> listDocenteSearch = new ArrayList<>();
	
	private void searchUser() {
		windowDialog  window = new windowDialog();
		
		filterSearch.getStyle().set("padding-left", "2px");
		filterSearch.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		filterSearch.setClearButtonVisible(true);
		filterSearch.setPrefixComponent(VaadinIcon.SEARCH.create());
		filterSearch.setPlaceholder("Buscar");
		filterSearch.setValueChangeMode(ValueChangeMode.LAZY);
		
		layoutSearchMain.add(filterSearch, gridDocenteSearch);
		
		window.getSucces().setVisible(false);
		window.getCancel().setText("Cerrar");
		window.getCancel().addClickListener(e->{
			window.close();
		});
		
		window.setContent(layoutSearchMain);
		window.setWidth("700px");
		/*window.setCloseOnEsc(false);
		window.setCloseOnOutsideClick(false);*/
		window.setIcon(VaadinIcon.USER);
		window.setTitle("Busqueda de docentes");
		window.open();
		
		listDocenteSearch = UsuarioController.search("");
		gridDocenteSearch.setItems(listDocenteSearch);
		
		//listDocenteSearch UsuarioController.search("");
	}
	
	private Component newMenuItem(String nombre, VaadinIcon vicon) {
		HorizontalLayout hl= new HorizontalLayout();
		Icon icon = new Icon(vicon);	
		Span span = new Span(nombre);
		//span.getStyle().set("","");
		icon.setSize("20px");
		hl.setSpacing(false);
		hl.add(icon,span);
		return hl;
	}
	
}
