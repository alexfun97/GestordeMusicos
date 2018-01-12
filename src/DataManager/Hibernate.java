package DataManager;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Cantante;
import modelo.Genero;

public class Hibernate implements DataManager {

	Session session;

	private ObservableList<Cantante> cantantes;
	private ObservableList<String> nombre;

	public Hibernate() {

		HibernateUtil util = new HibernateUtil();

		session = util.getSessionFactory().openSession();

	}

	@Override
	public ObservableList<Cantante> transicionDatos() {
		cantantes = FXCollections.observableArrayList();
		Query q = session.createQuery("select e from Cantante e");
		List results = q.list();

		Iterator cantantesIterator = results.iterator();

		while (cantantesIterator.hasNext()) {
			Cantante cant = (Cantante) cantantesIterator.next();
			cantantes.add(cant);

		}
		return cantantes;
	}

	@Override
	public void insercionDatos(Cantante cantante) {

		session.beginTransaction();
		
		session.save(cantante);
		
		session.getTransaction().commit();

	}

	@Override
	public void borradoDatos(Cantante cantante) {
		
		session.beginTransaction();
		
		session.delete(cantante);
		
		session.getTransaction().commit();

	}

	@Override
	public void borradoTabla() {
		
		session.beginTransaction();
		
		Query q = session.createQuery("select e from Cantante e");
		List results = q.list();

		Iterator cantantesIterator = results.iterator();

		while (cantantesIterator.hasNext()) {
			Cantante cant = (Cantante) cantantesIterator.next();
			session.delete(cant);
		}
		
		session.getTransaction().commit();
	}

	public ObservableList<Cantante> exportarDatos() {
		return this.transicionDatos();
	}

	public void importarDatos(ObservableList<Cantante> arrayCantantes) {

		this.borradoTabla();

		for (int x = 0; x < arrayCantantes.size(); x++) {
			this.insercionDatos(arrayCantantes.get(x));
		}
	}

	@Override
	public ObservableList<String> nombreGeneros() {
		nombre = FXCollections.observableArrayList();
		
		Query q = session.createQuery("select Nombre from Genero e");
		List results = q.list();

		Iterator generoIterator = results.iterator();

		while (generoIterator.hasNext()) {
			String gen = (String) generoIterator.next();
			nombre.add(gen);
		}
		
		return nombre;
	}

	@Override
	public Genero pedirGenero(String nomGenero) {
		Query q = session.createQuery("select e from Genero e where e.Nombre = '" + nomGenero + "'");
		List results = q.list();

		Iterator cantantesIterator = results.iterator();

		Genero cant = (Genero) cantantesIterator.next();
			
		return cant;
	}

	@Override
	public void modificado(Cantante cantante) {
		
		session.beginTransaction();
		
		session.update(cantante);
		
		session.getTransaction().commit();
		
	}

}
