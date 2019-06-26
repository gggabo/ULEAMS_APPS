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
public class HorarioDocente implements Serializable {
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
	
	public HorarioDocente() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
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
		HorarioDocente other = (HorarioDocente) obj;
		if (dia == null) {
			if (other.dia != null)
				return false;
		} else if (!dia.equals(other.dia))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		return true;
	}

	
	
	

}
