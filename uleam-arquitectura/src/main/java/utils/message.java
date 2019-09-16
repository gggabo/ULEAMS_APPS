package utils;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification.Position;

public class message {

	private static Notification msj;
	
	public static void errorMessage(String mensaje){
		Span span = new Span("Sistema de gestión docente");
		Span spanMsj = new Span(mensaje);
		VerticalLayout layout = new VerticalLayout();
		layout.add(span, spanMsj);
		layout.setPadding(false);
		layout.setSpacing(false);
		msj = new Notification(layout);
		msj.setDuration(2000);
		msj.open();
		msj.setPosition(Position.TOP_END);
		msj.addThemeVariants(NotificationVariant.LUMO_ERROR);
	}
	
	public static void successMessage(String mensaje){
		Span span = new Span("Sistema de gestión docente");
		Span spanMsj = new Span(mensaje);
		VerticalLayout layout = new VerticalLayout();
		layout.add(span, spanMsj);
		layout.setPadding(false);
		layout.setSpacing(false);
		msj = new Notification(layout);
		msj.setDuration(2000);
		msj.open();
		msj.setPosition(Position.TOP_END);
		msj.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
	}
	
	public static void informationMessage(String mensaje){
		Span span = new Span("Sistema de gestión docente");
		Span spanMsj = new Span(mensaje);
		VerticalLayout layout = new VerticalLayout();
		layout.add(span, spanMsj);
		layout.setPadding(false);
		layout.setSpacing(false);
		msj = new Notification(layout);
		msj.setDuration(2000);
		msj.open();
		msj.setPosition(Position.TOP_END);
		msj.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
	}
	
}
