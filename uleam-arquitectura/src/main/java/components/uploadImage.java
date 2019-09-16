package components;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;

public class uploadImage extends Div{
	private static final long serialVersionUID = 1L;
	
	private MemoryBuffer memoryBuffer = new MemoryBuffer();
	private Upload upload = new Upload(memoryBuffer);
	Image img = new Image();
	
	private byte[] data;
	
	public uploadImage() {
		add(upload);
		setDefaultConfig();
		initComponents();
	}
	 
	public void initComponents() {
		upload.setAcceptedFileTypes("image/png", "image/jpeg");
		upload.setMaxFileSize(8000000);   
		upload.setWidth("100px");		
		
		upload.addSucceededListener(e -> {
			  InputStream inputStream = memoryBuffer.getInputStream();
			  try {
				data = IOUtils.toByteArray(inputStream);				
				setValue(data);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		getElement().addEventListener("file-remove", event->{
			clear();
		});
		
	}
	
	public void setValue(byte[] dato) {
		//setDefaultConfig();
		if(dato==null) {
			clear();
		}else { 
		
		data = dato;
		//upload.getUploadButton().setVisible(false);
		upload.getDropLabelIcon().setVisible(false);
		StreamResource resource = new StreamResource("img.jpg", () -> new ByteArrayInputStream(data));
		img.setSrc(resource);
		img.setWidth("80px");
		content.removeAll();
		content.add(img);
		}
	}
		
	public void clear() {
		data = null;
		//upload.getUploadButton().setVisible(true);
		upload.getDropLabelIcon().setVisible(true);
		content.removeAll();
		content.add(lb);
	}
	
	Label lb = new Label("Arrastar aquí");
	
	Div content = new Div();
	Button btnUp = new Button("Foto");
	Icon icon = new Icon(VaadinIcon.UPLOAD);
	
	public void setDefaultConfig() {
		content.add(lb);
		upload.setDropLabel(content);
		btnUp.addThemeVariants(ButtonVariant.LUMO_SMALL);
		upload.setUploadButton(btnUp);
		upload.setDropLabelIcon(icon);
		
		img.addClassName("image-upload");
		
	}
	
	public byte[] getValue() {
		return data;
	}
}
