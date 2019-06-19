package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity 
@Table(name = "TBL_DOCENTE_ACTIVIDAD")
public class DocenteActividad implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_DOCENTE_ACTIVIDAD")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDocenteActividad;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario docente;
	
	@ManyToOne
	@JoinColumn(name = "ID_ACTIVIDAD")
	private Actividad actividad;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERIODO")
	private Periodo periodo;
	
	public DocenteActividad() {
		// TODO Auto-generated constructor stub
	}

	public long getIdDocenteActividad() {
		return idDocenteActividad;
	}

	public void setIdDocenteActividad(long idDocenteActividad) {
		this.idDocenteActividad = idDocenteActividad;
	}

	public Usuario getDocente() {
		return docente;
	}

	public void setDocente(Usuario docente) {
		this.docente = docente;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	

}
