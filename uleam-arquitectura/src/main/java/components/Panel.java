package components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class Panel extends Div {
	private static final long serialVersionUID = 1L;
	private Div contentPanel = new Div();
	private Div elementContent = new Div(); 
	private Div header = new Div();
	private Label title = new Label();
	private Icon icon = VaadinIcon.USER.create();
	
	
	public Panel() {
		//setSizeFull();
		setHeightFull();
		setClassName("panel");
		header.setClassName("panel-header"); 
		header.setWidthFull();
		title.setClassName("panel-title");
		icon.setClassName("panel-title-icon");
		
		header.add(icon,title);
		
		contentPanel.setWidthFull();
		contentPanel.add(header, elementContent);
		
		elementContent.setWidthFull();
		
		add(contentPanel);
		// TODO Auto-generated constructor stub
	}
	
	public void setContent(Component component) {
		elementContent.add(component);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	public void setIcon(VaadinIcon icon) {
		this.icon.getElement().setAttribute("icon", "vaadin:"+icon.name().toLowerCase());
	}
}
