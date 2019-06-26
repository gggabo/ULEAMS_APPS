package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name = "TBL_DIA")
public class Dia implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_DIA")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDia;
	
	@Column(name = "DIA")
	private String dia;
	
	@OneToMany(mappedBy = "dia", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HorarioDocente>  horarioDocenteActividades = new ArrayList<>();	
	
	@Column(name = "ESTADO")
	private int estado;
	
	public Dia() {
		// TODO Auto-generated constructor stub
	}

	public Dia(String dia, int estado) {
		super();
		this.dia = dia;
		this.estado = estado;
	}

	public long getIdDia() {
		return idDia;
	}

	public void setIdDia(long idDia) {
		this.idDia = idDia;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
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
		result = prime * result + (int) (idDia ^ (idDia >>> 32));
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
		Dia other = (Dia) obj;
		if (idDia != other.idDia)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dia [idDia=" + idDia + ", dia=" + dia + ", estado=" + estado + "]";
	}

	
	
	
	
}
