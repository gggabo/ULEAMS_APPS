package components;

import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import models.Usuario;
import utils.readXlsUsers;

public class uploadXls extends Div{
	private static final long serialVersionUID = 1L;
	
	private MemoryBuffer memoryBuffer = new MemoryBuffer();
	private Upload upload = new Upload(memoryBuffer);
	private readXlsUsers readXls;
	private Grid<Usuario> gridImportUser;
	
	Image img = new Image();
	
	public uploadXls(Grid<Usuario> gridImportUser) {
		this.gridImportUser = gridImportUser;
		add(upload);
		//setDefaultConfig();
		initComponents();
	}
	 
	public void initComponents() {
		upload.setAcceptedFileTypes("application/vnd.ms-excel");
		upload.setMaxFileSize(3000000);   
		//upload.setWidth("100px");		
		
		upload.addSucceededListener(e -> {
			  //InputStream inputStream = memoryBuffer.getInputStream();
			 /* try {
				data = IOUtils.toByteArray(inputStream);				
				setValue(data);
			} catch (IOException e1) {
				e1.printStackTrace();
			}*/
			
			readXls = new readXlsUsers(memoryBuffer.getInputStream());
			gridImportUser.setItems(readXls.getXlsUsersResultList());
			//System.out.println(readXls.getXlsUsersResultList());
			
		});
		
		getElement().addEventListener("file-remove", event->{
			clear();
		});
		
	}
	
	/*public void setValue(byte[] dato) {
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
	}*/
		
	public void clear() {
		//data = null;
		//upload.getUploadButton().setVisible(true);
		//upload.getDropLabelIcon().setVisible(true); 
		//content.removeAll();
		//content.add(lb);
		readXls.clear();
		gridImportUser.setItems(readXls.getListUsuariosImport());
	}
	
	/*Label lb = new Label("Arrastar aquí");
	
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
	*/
	public List<Usuario> getReadXls(){
		return readXls.getListUsuariosImport();
	}
}
