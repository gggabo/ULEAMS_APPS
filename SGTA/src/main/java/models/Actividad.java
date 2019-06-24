package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity 
@Table(name = "TBL_ACTIVIDAD")

public class Actividad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ACTIVIDAD")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idActividad;
	
	@Column(name = "ACTIVIDAD")
	private String actividad;
		
	@ManyToMany(mappedBy = "actividades", cascade = {CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<DocenteActividad> docenteActividades = new ArrayList<>();
	
	@Column(name = "ESTADO")
	private int estado;

	public Actividad() {
		// TODO Auto-generated constructor stub
	}
	
	public Actividad( String actividad, int estado) {
		super();
		this.actividad = actividad;
		this.estado = estado;
	}

	public long getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(long idActividad) {
		this.idActividad = idActividad;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idActividad ^ (idActividad >>> 32));
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
		Actividad other = (Actividad) obj;
		if (idActividad != other.idActividad)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return actividad;
	}
	
	
	
	
	
}
