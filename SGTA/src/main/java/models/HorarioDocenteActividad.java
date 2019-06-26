package models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity 
@Table(name = "TBL_HORARIO_DOCENTE_ACTIVIDAD")
public class HorarioDocenteActividad implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_HORARIO_DOCENTE_ACTIVIDAD")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idHorarioDocenteActidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_DIA")
	private Dia dia;
	
	@ManyToOne
	@JoinColumn(name = "ID_HORA")
	private Hora hora;
	
	@ManyToOne
	@JoinColumn(name = "ID_DOCENTE_PERIODO")
	private DocenteActividad docenteActividad;
	
	@ManyToOne
	@JoinColumn(name = "ID_ACTIVIDAD")
	private Actividad actividad;
	
	public HorarioDocenteActividad() {
		// TODO Auto-generated constructor stub
	}

	public long getIdHorarioDocenteActidad() {
		return idHorarioDocenteActidad;
	}

	public void setIdHorarioDocenteActidad(long idHorarioDocenteActidad) {
		this.idHorarioDocenteActidad = idHorarioDocenteActidad;
	}

	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	public Hora getHora() {
		return hora;
	}

	public void setHora(Hora hora) {
		this.hora = hora;
	}

	public DocenteActividad getDocenteActividad() {
		return docenteActividad;
	}

	public void setDocenteActividad(DocenteActividad docenteActividad) {
		this.docenteActividad = docenteActividad;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	
	
	

}
