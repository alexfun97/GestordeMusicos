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
		Genero x = null;
		Query q = session.createQuery("select e from Genero e");
		List results = q.list();

		Iterator cantantesIterator = results.iterator();

		while (cantantesIterator.hasNext()) {
			Genero cant = (Genero) cantantesIterator.next();
			if(cant.getNombre().equals(cantante.getGenero())){
				x = cant;
			}

		}
		
		cantante = new Cantante(cantante.getID(), cantante.getNombre(), cantante.getNacimiento(), cantante.getNacionalidad(), x.getNombre());
		session.beginTransaction();
		

		session.save(cantante);
		
		session.getTransaction().commit();

	}

	@Override
	public void borradoDatos(Cantante cantante) {
		// TODO Auto-generated method stub

	}

	@Override
	public ObservableList<String> muestraUno(Cantante cantante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borradoTabla() {
		// TODO Auto-generated method stub

	}

	@Override
	public ObservableList<Cantante> exportarDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void importarDatos(ObservableList<Cantante> cantantes) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

}
