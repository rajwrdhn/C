package InitialSynthesis;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.sun.javafx.collections.MappingChange.Map;
/**
 * This class represents the Normalisation of OWL Ontology.
 * @author raj
 *
 */
public class ELNormalise {
	//OWLDatafactory is an interface for creating entities, class expressions and axioms.
	OWLDataFactory v_datafactory;
	//refering to class axioms of InitialSynthesis package
	ELAxioms v_elaxioms;
	Map<OWLClassExpression,OWLClassExpression> V_definitions;
	Map<OWLObjectOneOf,OWLClass> v_definitionsForNegativeNominals;
	int v_firstReplacementIndex;
    //ExpressionManager m_expressionManager;
	//PLVisitor m_plVisitor;
	Map<OWLDataRange,OWLDatatype> m_dataRangeDefinitions; 
	    // contains custom datatype definitions from DatatypeDefinition axioms
	public ELNormalise(OWLDataFactory factory,ELAxioms axioms) {
		v_datafactory = factory;
		v_elaxioms = axioms;
	}
	public static void main (String [] args) {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o;
		
		//man.loadOntologyFromOntologyDocument(file);
	}
}
