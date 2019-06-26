package models;

import java.io.Serializable;
import java.time.LocalTime;
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
@Table(name = "TBL_HORA")
public class Hora implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_HORA")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idHora;
	
	@Column(name = "HORA1")
	private LocalTime hora1;
	
	@Column(name = "HORA2")
	private LocalTime hora2;
	
	@OneToMany(mappedBy = "hora", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HorarioDocenteActividad>  horarioDocenteActividades = new ArrayList<>();
	
	@Column(name = "ESTADO")
	private int estado;
	
	public Hora() {
		// TODO Auto-generated constructor stub
	}

	


	public Hora(LocalTime hora1, LocalTime hora2, int estado) {
		super();
		this.hora1 = hora1;
		this.hora2 = hora2;
		this.estado = estado;
	}




	public LocalTime getHora1() {
		return hora1;
	}




	public void setHora1(LocalTime hora1) {
		this.hora1 = hora1;
	}




	public LocalTime getHora2() {
		return hora2;
	}

	public String getHoras() {
		return hora1.toString() + hora2.toString();
	}



	public void setHora2(LocalTime hora2) {
		this.hora2 = hora2;
	}




	public List<HorarioDocenteActividad> getHorarioDocenteActividades() {
		return horarioDocenteActividades;
	}



	public void setHorarioDocenteActividades(List<HorarioDocenteActividad> horarioDocenteActividades) {
		this.horarioDocenteActividades = horarioDocenteActividades;
	}



	public long getIdHora() {
		return idHora;
	}

	public void setIdHora(long idHora) {
		this.idHora = idHora;
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
		result = prime * result + (int) (idHora ^ (idHora >>> 32));
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
		Hora other = (Hora) obj;
		if (idHora != other.idHora)
			return false;
		return true;
	}




	@Override
	public String toString() {
		return "Hora [idHora=" + idHora + ", hora1=" + hora1 + ", hora2=" + hora2 + ", horarioDocenteActividades="
				+ horarioDocenteActividades + ", estado=" + estado + "]";
	}

	
	
}
