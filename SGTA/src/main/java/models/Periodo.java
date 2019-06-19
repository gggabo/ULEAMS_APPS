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
@Table(name = "TBL_PERIODO")
public class Periodo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_PERIODO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idPerido;
	
	@Column(name = "PERIODO")
	private String periodo;
	
	@OneToMany(mappedBy = "periodo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocenteActividad>  docenteActividades = new ArrayList<>();	
	
	@Column(name = "estado")
	private int estado;
	
	public Periodo() {
		// TODO Auto-generated constructor stub
	}

	public Periodo(String periodo) {
		super();
		this.periodo = periodo;
	}

	public long getIdPerido() {
		return idPerido;
	}

	public void setIdPerido(long idPerido) {
		this.idPerido = idPerido;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPerido ^ (idPerido >>> 32));
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
		Periodo other = (Periodo) obj;
		if (idPerido != other.idPerido)
			return false;
		return true;
	}
	
	
	
}
