package controllers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import models.Dia;
import services.JPAService;

public class DiaController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (Dia dia) {
		JPAService.runInTransaction(em ->{
			em.persist(dia);
			return null;
		});
	}
	
	public static void update (Dia dia) {
		JPAService.runInTransaction(em ->{
			em.merge(dia);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<Dia> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select d from Dia d").getResultList()
        );
	}
	
	static	Dia us;
	public static  Dia getSpecificRolById(long rol) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select d from Dia h where d.idDia = ?1");
			query.setParameter(1, rol);

			if(query.getResultList().size()>0) {
				us = (Dia) query.getSingleResult();
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
