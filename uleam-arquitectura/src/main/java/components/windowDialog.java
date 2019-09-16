package components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class windowDialog extends Dialog{
	private static final long serialVersionUID = 1L;
	private Div windowLayout = new Div();
	private Div windowHeader = new Div();
	private Div windowHeaderContain = new Div();
	private Div contentLayout = new Div();
	private Button succes = new Button("Aceptar");
	private Button cancel = new Button("Cancelar");
	private HorizontalLayout buttonsLayout = new HorizontalLayout();
	private Div buttonsRoot = new Div();
	public Div getButtonsRoot() {
		return buttonsRoot;
	}

	public void setButtonsRoot(Div buttonsRoot) {
		this.buttonsRoot = buttonsRoot;
	}

	private Label title = new Label();
	private Icon icon;
	
	public windowDialog() {
		setConfig();
	}
	 
	private void setConfig() {
		succes.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL); 
		succes.setIcon(VaadinIcon.CHECK_CIRCLE.create());
		cancel.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
		cancel.setIcon(VaadinIcon.CLOSE.create());
		buttonsLayout.add(cancel, succes); 
		buttonsLayout.setPadding(false);
		
		icon = new Icon(VaadinIcon.FORM);
		icon.setClassName("window-title-icon");
		title.setClassName("window-title");
				
		windowHeaderContain.add(icon,title);
		windowHeader.add(windowHeaderContain);
		windowHeader.addClassName("window-header");
		
		buttonsRoot.addClassName("window-footer");
		buttonsRoot.setWidthFull();
		buttonsRoot.add(buttonsLayout);		
		
		windowLayout.add(windowHeader,contentLayout, buttonsRoot);
		
		add(windowLayout);
		
	}

	public Div getContentLayout() {
		return contentLayout;
	}

	public void setContentLayout(Div contentLayout) {
		this.contentLayout = contentLayout;
	}

	public Button getSucces() {
		return succes;
	}

	public void setSucces(Button succes) {
		this.succes = succes;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}
	
	public void setContent(Component c) {
		contentLayout.add(c);
	}
	
	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	public void setIcon(VaadinIcon icon) {
		this.icon.getElement().setAttribute("icon", "vaadin:"+icon.name().toLowerCase());
	}
	
}
