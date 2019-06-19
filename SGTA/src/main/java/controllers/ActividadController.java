package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Actividad;
import models.Periodo;
import models.Rol;
import services.JPAService;

public class ActividadController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Actividad actividad) {
		JPAService.runInTransaction(em ->{
			em.persist(actividad);
			return null;
		});
	}
	
	public static void update (Actividad actividad) {
		JPAService.runInTransaction(em ->{
			em.merge(actividad);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Actividad> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select a from Actividad a").getResultList()
        );
	}
	
	/*static	Periodo pe;
	public static  Rol getSpecificRolById(long rol) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select r from Rol r where r.idRol = ?1");
			query.setParameter(1, rol);

			if(query.getResultList().size()>0) {
				 = (Rol) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return us;
		
	}*/
	
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
