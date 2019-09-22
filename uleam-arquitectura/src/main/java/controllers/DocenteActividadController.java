package controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import models.DocenteActividad;
import models.Rol;
import services.JPAService;

public class DocenteActividadController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void save (DocenteActividad docenteActividad) {
		JPAService.runInTransaction(em ->{
			em.persist(docenteActividad);
			return null;
		});
	}
	
	public static void update (DocenteActividad docenteActividad) {
		JPAService.runInTransaction(em ->{
			em.merge(docenteActividad);
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static List<DocenteActividad> findAll() {
		return JPAService.runInTransaction(em ->
        em.createQuery("select da from DocenteActividad da").getResultList()
        );
	}
	
	static List<DocenteActividad> listDocenteActividad = new ArrayList<>();
	 
	@SuppressWarnings("unchecked")
	public static List<DocenteActividad> getAllByPeriodo(long idPeriodo, Rol rolAdmin, Rol rolDocente){
		return JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select da from DocenteActividad da where da.periodo.idPeriodo = ?1 "
					+ " and (?2 MEMBER OF da.docente.roles or ?3 MEMBER OF da.docente.roles)"
					);
			query.setParameter(1, idPeriodo);
			query.setParameter(2, rolAdmin);
			query.setParameter(3, rolDocente);
			
			
			listDocenteActividad = query.getResultList();
			
			Iterator<DocenteActividad> iteratorDocenteAct = listDocenteActividad.iterator();
		    DocenteActividad da;
			while(iteratorDocenteAct.hasNext()) {
				da = iteratorDocenteAct.next();
				da.getActividades().size();
			}
			
			return listDocenteActividad;
		}
        ); 
	}
	
	@SuppressWarnings("unchecked")
	public static List<DocenteActividad> search(long idPeriodo, String searchField){
		return JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select da from DocenteActividad da where da.periodo.idPeriodo = ?1 and da.docente.estado = 1 and" 
					+"(concat (da.docente.apellido_paterno,' ',da.docente.apellido_materno,' ',da.docente.nombre_uno,' ',da.docente.nombre_dos) LIKE ?2 " 
					+"or da.docente.Cedula LIKE ?2)"
					);
			query.setParameter(1, idPeriodo);
			query.setParameter(2, "%" + searchField + "%");
			
			listDocenteActividad = query.getResultList();
			
			Iterator<DocenteActividad> iteratorDocenteAct = listDocenteActividad.iterator();
		    DocenteActividad da;
			while(iteratorDocenteAct.hasNext()) {
				da = iteratorDocenteAct.next();
				da.getActividades().size();
			}
			
			return listDocenteActividad;
		}
        ); 
	}
	
	@SuppressWarnings("unchecked")
	public static List<DocenteActividad> search(String searchField){
		return JPAService.runInTransaction(em->{
			Query query = em.createQuery("SELECT da from DocenteActividad da.docente where da.docente.estado = 1 and"
					+ "(concat (da.docente.apellido_paterno,' ',da.docente.apellido_materno,' ',da.docente.nombre_uno,' ',da.docente.nombre_dos) LIKE ?1 "
					+ "or cedula LIKE ?1) "
					//+ "order by concat (u.apellido_paterno,' ',u.apellido_materno,' ',u.nombre_uno,' ',u.nombre_dos))"
					);
			query.setParameter(1, "%" + searchField + "%");
			
			return query.getResultList();
		});
	}
	
	/*static	Rol us;
	public static  Rol getSpecificRolById(long rol) {		
		JPAService.runInTransaction(em ->{
			Query query = em.createQuery("select r from Rol r where r.idRol = ?1");
			query.setParameter(1, rol);

			if(query.getResultList().size()>0) {
				us = (Rol) query.getSingleResult();
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
