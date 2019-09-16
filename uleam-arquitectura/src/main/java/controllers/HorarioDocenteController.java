package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Dia;
import models.DocenteActividad;
import models.HorarioDocente;
import services.JPAService;

public class HorarioDocenteController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (HorarioDocente horarioDocenteActividad) {
		JPAService.runInTransaction(em ->{
			em.persist(horarioDocenteActividad);
			return null;
		});
	}
	
	public static void update (HorarioDocente horarioDocenteActividad) {
		JPAService.runInTransaction(em ->{
			em.merge(horarioDocenteActividad);
			return null;
		});
	}
	
	public static void delete (long id) {
		JPAService.runInTransaction(em ->{
			HorarioDocente hd = em.find(HorarioDocente.class, id);
			em.remove(hd);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<HorarioDocente> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select d from HorarioDocente d").getResultList()
        );
	}
	
	/*static	HorarioDocenteActividad us;
	public static  HorarioDocenteActividad getSpecificHorarioByDocente(DocenteActividad docenteActividad) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select d from HorarioDocenteActividad d where d.docenteActividad = ?1");
			query.setParameter(1, docenteActividad);

			if(query.getResultList().size()>0) {
				us = (HorarioDocenteActividad) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return us;
		
	}*/
	
	static List<HorarioDocente> hda;
	@SuppressWarnings("unchecked")
	public static  List<HorarioDocente> getSpecificHorarioByDocente(DocenteActividad docenteActividad, Dia dia) {		
	 JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select d from HorarioDocente d where d.docenteActividad = ?1 and d.dia = ?2 ORDER BY d.hora");
			query.setParameter(1, docenteActividad);
			query.setParameter(2, dia);
			hda = query.getResultList(); 
			return null;
		}
        ); 
		
		return hda;
	}
	
	/*static Rol rol;
	public static List<Laboratorio> getAllLabsByRol(long idLab) {		
		JPAService.runInTransaction(em ->{
			rol = em.find(Rol.class, idLab);
			rol.getLaboratorios().size();
			
			return null;
			
		}); 
		
		return rol.getLaboratorios();
	}
		*/
}
