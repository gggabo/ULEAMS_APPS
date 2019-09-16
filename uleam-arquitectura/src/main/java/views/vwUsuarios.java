package views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.vaadin.gatanaso.MultiselectComboBox;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import components.Panel;
import components.uploadImage;
import components.uploadXls;
import components.windowDialog;
import controllers.RolController;
import controllers.UsuarioController;
import models.Rol;
import models.Usuario;
import utils.message;

@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@Route("usuarios")
public class vwUsuarios extends Div {
	private static final long serialVersionUID = 1L;

	public vwUsuarios() {
		setHeight("95%");
		add(buildView()); 
		
		setPlaceHolder();
		setConfigComponents();
		loadGridItems("");
		loadDefaultItems();
		initComponents();
		addValidation();
		buildUserEvents();
	}
	 
	private Panel panel = new Panel();
	private Div vlRoot = new Div();
	private MenuBar toolbar = new MenuBar();
	private TextField filter = new TextField();
	
	public Grid<Usuario> gridUsuario = new Grid<>();
	public List<Usuario> listUsuarios = new ArrayList<>();
	
	public Grid<Usuario> gridImportUsuario = new Grid<>();
	public List<Usuario> listImportUsuarios = new ArrayList<>();
	
	public Component buildView() {
		toolbar.addThemeVariants(MenuBarVariant.LUMO_SMALL);
		toolbar.addItem(newMenuItem("Nuevo Usuario", VaadinIcon.PLUS), e->{
			accion = "guardar";
			clearComponentsUser();
			newEditUser(null);
		});
		toolbar.addItem(newMenuItem("Importar", VaadinIcon.DOWNLOAD_ALT), e->{
			importUserXls();
		});
		toolbar.addItem(newMenuItem("Imprimir", VaadinIcon.PRINT));
		
		filter.setClearButtonVisible(true);
		filter.addClassName("filter-text");
		filter.setPlaceholder("Buscar");
		Icon icon = VaadinIcon.SEARCH.create();
		filter.setPrefixComponent(icon);
		filter.setValueChangeMode(ValueChangeMode.LAZY);;
		filter.addValueChangeListener(e->{
			listUsuarios = UsuarioController.search(e.getValue());
			gridUsuario.setItems(listUsuarios);
		});
		
		vlRoot.add(toolbar, filter, gridUsuario);
		//vlRoot.setSpacing(false);
		panel.setContent(vlRoot);
		panel.setIcon(VaadinIcon.ADJUST);
		panel.setTitle("Gestión de usuarios");
		return panel;
	}
	
	public void initComponents() {
		gridUsuario.addColumn(Usuario::getCedula).setHeader("CÉDULA/DNI").setResizable(true).setAutoWidth(true);
		gridUsuario.addColumn(Usuario -> Usuario.getApellido_paterno() +" "+ Usuario.getApellido_materno() +" "+
		Usuario.getNombre_uno() +" "+ Usuario.getNombre_dos()
				).setHeader("NOMBRES Y APELLIDOS").setResizable(true).setAutoWidth(true).setId("NOMBRES");
		gridUsuario.addColumn(Usuario::getNombre_usuario).setHeader("USUARIO").setResizable(true).setAutoWidth(true);
		
		gridUsuario.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		gridUsuario.setWidth("100%");
		gridUsuario.setSelectionMode(Grid.SelectionMode.NONE);
		gridUsuario.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COMPACT);
		
		gridUsuario.addComponentColumn(Usuario -> {
			 
			Button b = new Button("Editar");
			b.addClickListener(clickb ->{  
				accion = "modificar";
				clearComponentsUser();
				newEditUser(Usuario);
				cedula.setValue(Usuario.getCedula());
				nombre1.setValue(Usuario.getNombre_uno());
				nombre2.setValue(Usuario.getNombre_dos());
				apellido1.setValue(Usuario.getApellido_paterno());
				apellido2.setValue(Usuario.getApellido_materno());
				telefono.setValue(Usuario.getTelefono());
				direccion.setValue(Usuario.getDireccion());
				correo.setValue(Usuario.getCorreo());
				usuario.setValue(Usuario.getNombre_usuario());
				clave.setValue("");
				upload.setValue(Usuario.getImagen());
				List<Rol> rolSelect = new ArrayList<>();
				rolSelect=Usuario.getRoles();
				rol.setValue(new HashSet<Rol>(rolSelect));			
				
			});
			b.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			b.addThemeVariants(ButtonVariant.LUMO_SMALL);
			b.setIcon(new Icon(VaadinIcon.EDIT));
			
			Button b2 = new Button("Eliminar");
			b2.addClickListener(clickb2 ->{
				listUsuarios.remove(Usuario);
				gridUsuario.setItems(listUsuarios);
				Usuario.setEstado(0);
				UsuarioController.update(Usuario);
				message.informationMessage("Registro eliminado correctamente");
			});
			b2.addThemeVariants(ButtonVariant.LUMO_ERROR);
			b2.addThemeVariants(ButtonVariant.LUMO_SMALL);
			b2.setIcon(new Icon(VaadinIcon.CLOSE));
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.add(b,b2);
			return hl;			
		}).setHeader("Opciones").setResizable(true).setAutoWidth(true);
		
		gridImportUsuario.addColumn(Usuario::getCedula).setHeader("CÉDULA/DNI").setResizable(true).setAutoWidth(true);
		gridImportUsuario.addColumn(Usuario -> Usuario.getApellido_paterno() +" "+ Usuario.getApellido_materno() +" "+
		Usuario.getNombre_uno() +" "+ Usuario.getNombre_dos()
				).setHeader("NOMBRES Y APELLIDOS").setResizable(true).setAutoWidth(true).setId("NOMBRES");
		gridImportUsuario.addColumn(Usuario::getNombre_usuario).setHeader("USUARIO").setResizable(true).setAutoWidth(true);
		gridImportUsuario.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		gridImportUsuario.setWidth("100%");
		gridImportUsuario.setHeight("280px");
		gridImportUsuario.setSelectionMode(Grid.SelectionMode.NONE);
		gridImportUsuario.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COMPACT);
		
		
	}
	
	public void loadGridItems(String search) {
		listUsuarios = UsuarioController.search(search);
		gridUsuario.setItems(listUsuarios);
	}
	public void loadDefaultItems() {
		listRol = RolController.findAll();
		rol.setItems(listRol);
		rol.setItemLabelGenerator(Rol::getNombre); 
	}
	
	//private Dialog window = new Dialog();
	private FormLayout formUsuario = new FormLayout();
	private TextField cedula = new TextField("Cédula");
	private TextField nombre1 = new TextField("Primer nombre");
	private TextField nombre2 = new TextField("Segundo Nombre");
	private TextField apellido1 = new TextField("Apellido paterno");
	private TextField apellido2 = new TextField("Apellido materno");
	private TextField direccion = new TextField("Dirección");
	private TextField telefono = new TextField("Teléfono");
	private EmailField correo = new EmailField("Correo");
	private TextField usuario = new TextField("Usuario");
	private PasswordField clave = new PasswordField("Clave");
	private uploadImage upload = new uploadImage();

	private MultiselectComboBox<Rol> rol = new MultiselectComboBox<>();
	private List<Rol> listRol =  new ArrayList<>();
	
	String accion = "guardar";
	
	private void newEditUser(Usuario cusuario) {
		
		windowDialog window = new windowDialog();
		
		window.getSucces().addClickListener(e ->{
			if(accion.equals("guardar")) {
				
				if (!validator.isValid()) {
					validator.validate();
					
					message.errorMessage("Hay errores en los campos de texto");
					return; 
				}
				
				if(UsuarioController.DBcontainsUser(cedula.getValue())) {
					message.informationMessage("El usuario ya se encuentra registrado");
					return;
				}
				
				if(correo.isInvalid()) {
					message.errorMessage("Hay errores en los campos de texto");
					return;
				}
				
				Usuario us = new Usuario(cedula.getValue().toUpperCase().trim(),nombre1.getValue().toUpperCase().trim(), 
						nombre2.getValue().toUpperCase().trim(), apellido1.getValue().toUpperCase().trim(), 
						apellido2.getValue().toUpperCase().trim(),correo.getValue().trim(), telefono.getValue().trim(), direccion.getValue().toUpperCase().trim(), 
						upload.getValue(),usuario.getValue().toLowerCase().trim(), DigestUtils.shaHex(clave.getValue().trim()),1);

				List<Rol> rolAsig = new ArrayList<>(rol.getValue());
				us.setRoles(rolAsig);
				
				UsuarioController.save(us); 
				//accion = "modificar";
				loadGridItems("");
			}else {
				cusuario.setCedula(cedula.getValue().toUpperCase().trim());
				cusuario.setNombre_uno(nombre1.getValue().toUpperCase().trim());
				cusuario.setNombre_dos(nombre2.getValue().toUpperCase().trim());
				cusuario.setApellido_paterno(apellido1.getValue().toUpperCase().trim());
				cusuario.setApellido_materno(apellido2.getValue().toUpperCase().trim());
				cusuario.setCorreo(correo.getValue().trim());
				cusuario.setTelefono(telefono.getValue().toUpperCase().trim());
				cusuario.setDireccion(direccion.getValue().toUpperCase().trim());
				cusuario.setImagen(upload.getValue());
				cusuario.setNombre_usuario(usuario.getValue().toUpperCase().trim());
				if(!clave.getValue().isEmpty()) {
					cusuario.setClave(DigestUtils.shaHex(clave.getValue().trim()));
				}
				
				List<Rol> rolAsig = new ArrayList<>(rol.getValue());
				cusuario.setRoles(rolAsig);
				/*listUsuarios = UsuarioController.search(filter.getValue());
				gridUsuario.setItems(listUsuarios);*/
				UsuarioController.update(cusuario);
				loadGridItems(filter.getValue());
			}
			
			window.close();
			message.successMessage("Acción realiza correctamente");
			
		});
		
		window.getCancel().addClickListener(e ->{
			if(window.isOpened()) {
				window.close();
			}
		});
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setPadding(false);
		
		formUsuario.add(cedula,nombre1,nombre2,apellido1,apellido2,direccion,telefono,correo,usuario,clave,rol);
		formUsuario.setResponsiveSteps(
		        new ResponsiveStep("25em", 1),
		        new ResponsiveStep("32em", 2),
		        new ResponsiveStep("40em", 3));
		hl.add(formUsuario,upload);
		formUsuario.setColspan(rol, 2);
		
		window.setContent(hl);
		window.setWidth("700px");
		window.setCloseOnEsc(false);
		window.setCloseOnOutsideClick(false);
		window.setIcon(VaadinIcon.USER);
		window.setTitle("Gestión de usuarios");
		window.open();
	}
	
	private uploadXls uploadUsers = new uploadXls(gridImportUsuario);
    
	private void importUserXls() {
		
		windowDialog window = new windowDialog();
		
		window.getSucces().addClickListener(e ->{
			
			if(uploadUsers.getReadXls().size()==0) {
				message.errorMessage("No hay registros para guardar");
				return;
			}
			
			Iterator<Usuario> u = uploadUsers.getReadXls().iterator();
			Usuario ureg, udis;
			
			while(u.hasNext()) {
				ureg = u.next();
				
				if(!UsuarioController.DBcontainsUser(ureg.getCedula())) {
					UsuarioController.save(ureg);
					
				}	
				
				udis = UsuarioController.getSpecificUserDisable(ureg.getCedula());
				if(udis != null) {
					if(udis.getEstado()==0 ) {
						udis.setEstado(1); 
						UsuarioController.update(udis);
					}
				}
			
			}
									
			window.close();
			loadGridItems("");
			message.successMessage("Importación realizada con éxito");
			
		});
		
		window.getCancel().addClickListener(e ->{
			if(window.isOpened()) {
				window.close();
			}
		});
		
		VerticalLayout vl = new VerticalLayout();
		vl.setPadding(false);
		/*
		formUsuario.add(cedula,nombre1,nombre2,apellido1,apellido2,direccion,telefono,correo,usuario,clave,rol);
		formUsuario.setResponsiveSteps(
		        new ResponsiveStep("25em", 1),
		        new ResponsiveStep("32em", 2),
		        new ResponsiveStep("40em", 3));
		hl.add(formUsuario,upload);
		formUsuario.setColspan(rol, 2);*/
		
		vl.add(uploadUsers, gridImportUsuario);
		window.setContent(vl);
		
		
		
		window.setWidth("700px");
		window.setCloseOnEsc(false);
		window.setCloseOnOutsideClick(false);
		window.setIcon(VaadinIcon.USER);
		window.setTitle("Importación de usuarios");
		window.open();
	}
	
	public void setPlaceHolder() {
		cedula.setPlaceholder("1313253586");
		nombre1.setPlaceholder("Gabriel");
		nombre2.setPlaceholder("Gregorio");
		apellido1.setPlaceholder("Salvatierra");
		apellido2.setPlaceholder("Tumbaco");
		direccion.setPlaceholder("Calle 12 de Marzo y Av. Rocafuerte");
		telefono.setPlaceholder("0988178940");
		correo.setPlaceholder("gabriel.salvatierra@uleam.edu.ec");
		usuario.setPlaceholder("p1313253575");
		clave.setPlaceholder("*******");
		rol.setPlaceholder("Seleccionar");
	}
	
	public void setConfigComponents() {
		cedula.setMaxLength(10);
		nombre1.setMaxLength(20);
		nombre2.setMaxLength(20);
		apellido1.setMaxLength(20);
		apellido2.setMaxLength(20);
		direccion.setMaxLength(30);
		telefono.setMaxLength(10);
		correo.setMaxLength(40);
		correo.setClearButtonVisible(true);
		correo.setErrorMessage("Ingrese un correo válido");
		usuario.setMaxLength(11);
		clave.setMaxLength(20);
		rol.setLabel("Rol");
		filter.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		filter.setWidth("30%");
		//upload.getDropLabelIcon().setVisible(false);
	}
	
	public void clearComponentsUser() {
		cedula.clear();
		nombre1.clear();
		nombre2.clear();
		apellido1.clear();
		apellido2.clear();
		direccion.clear();
		telefono.clear();
		correo.clear();
		usuario.clear();
		clave.clear();
		upload.clear();
		listRol.clear();
		rol.clear();
		
	}
	
	Binder<Usuario> validator = new Binder<>();
	
	private void addValidation() {
		
		validator.forField(cedula)
		.asRequired("Dato requerido")
		.bind(Usuario::getCedula, Usuario::setCedula);
		
		validator.forField(apellido1)
		.asRequired("Dato requerido")
		.bind(Usuario::getApellido_paterno, Usuario::setApellido_paterno);
		
		validator.forField(nombre1)
		.asRequired("Dato requerido")
		.bind(Usuario::getNombre_uno, Usuario::setNombre_uno);				
	}
	
	private void buildUserEvents() {
		cedula.setValueChangeMode(ValueChangeMode.LAZY);
		cedula.addValueChangeListener(e->{
			usuario.setValue(e.getValue());
			clave.setValue(e.getValue());
		});
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
