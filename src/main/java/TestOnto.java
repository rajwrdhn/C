

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.google.common.base.Strings;

public class TestOnto {
	public static void main(Strings [] args) {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o;		
		try {
			o = man.createOntology();
			System.out.println(o);
		} catch (OWLOntologyCreationException e) {
			//System.out.println(e);
			e.printStackTrace();
		}
	}
}
