package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Periodo;
import models.Rol;
import services.JPAService;

public class PeriodoController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Periodo periodo) {
		JPAService.runInTransaction(em ->{
			em.persist(periodo);
			return null;
		});
	}
	
	public static void update (Periodo periodo) {
		JPAService.runInTransaction(em ->{
			em.merge(periodo);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Periodo> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select p from Periodo p").getResultList()
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
