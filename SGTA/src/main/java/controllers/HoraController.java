package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Dia;
import models.Hora;
import models.Rol;
import services.JPAService;

public class HoraController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Hora hora) {
		JPAService.runInTransaction(em ->{
			em.persist(hora);
			return null;
		});
	}
	
	public static void update (Hora hora) {
		JPAService.runInTransaction(em ->{
			em.merge(hora);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Hora> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select h from Hora h").getResultList()
        );
	}
	
	static	Hora us;
	public static  Hora getSpecificRolById(long rol) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select h from hora h where h.idHora = ?1");
			query.setParameter(1, rol);

			if(query.getResultList().size()>0) {
				us = (Hora) query.getSingleResult();
			}
			
			return null;
		}
        ); 
		
		return us;
		
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
