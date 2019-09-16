package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vaadin.flow.component.html.Label;

@Entity 
@Table(name = "TBL_DOCENTE_PERIODO")
public class DocenteActividad implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_DOCENTE_PERIODO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDocentePeriodo;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario docente;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERIODO")
	private Periodo periodo;
	
	@ManyToMany
	@JoinTable(name = "TBL_DOCENTE_PERIODO_ACTIVIDAD", joinColumns= @JoinColumn(name = "ID_DOCENTE_PERIODO"), 
	inverseJoinColumns = @JoinColumn(name ="ID_ACTIVIDAD"))
	private List<Actividad> actividades = new ArrayList<>();
	
	public DocenteActividad() {
		// TODO Auto-generated constructor stub
	}

	public Usuario getDocente() {
		return docente;
	}

	public void setDocente(Usuario docente) {
		this.docente = docente;
	}


	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public long getIdDocentePeriodo() {
		return idDocentePeriodo;
	}

	public void setIdDocentePeriodo(long idDocentePeriodo) {
		this.idDocentePeriodo = idDocentePeriodo;
	}

	public List<Actividad> getActividades() {		
		return actividades;
	}

	public Label getActividadesRender() {
		Label lb = new Label();
		lb.getStyle().set("font-size", "small");
		lb.setSizeFull();
		String actividades = "";
		
		Iterator<Actividad> iteratorAct = getActividades().iterator();
		Actividad ac;
		while(iteratorAct.hasNext()) {
			ac = iteratorAct.next();
			actividades = actividades + ac.getActividad()+", ";
		}
		
		if(actividades.length()>0)
		actividades = actividades.substring(0, actividades.length()-2);
		
		lb.setText(actividades);
		
		return lb;
	}
	
	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docente == null) ? 0 : docente.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocenteActividad other = (DocenteActividad) obj;
		if (docente == null) {
			if (other.docente != null)
				return false;
		} else if (!docente.equals(other.docente))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DocenteActividad [idDocentePeriodo=" + idDocentePeriodo + ", docente=" + docente + ", periodo="
				+ periodo + ", actividades=" + actividades + "]";
	}

	

	

}
