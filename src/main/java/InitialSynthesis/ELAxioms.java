package InitialSynthesis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.SWRLAtom;

public class ELAxioms {
	Set<OWLClass> v_classes;
	Set<OWLObjectProperty> v_objectproperties;
    Set<OWLObjectProperty> v_objectPropertiesOccurringInOWLAxioms;
    Set<OWLObjectPropertyExpression> v_complexObjectPropertyExpressions;
    Set<OWLDataProperty> v_dataProperties;
    Set<OWLNamedIndividual> v_namedIndividuals;
    Collection<OWLClassExpression[]> m_conceptInclusions;
    Collection<OWLDataRange[]> v_dataRangeInclusions;
    Collection<OWLObjectPropertyExpression[]> v_simpleObjectPropertyInclusions;
    Collection<ComplexObjectPropertyInclusion> v_complexObjectPropertyInclusions;
    Collection<OWLObjectPropertyExpression[]> v_disjointObjectProperties;
    Set<OWLObjectPropertyExpression> v_reflexiveObjectProperties;
    Set<OWLObjectPropertyExpression> v_irreflexiveObjectProperties;
    Set<OWLObjectPropertyExpression> v_asymmetricObjectProperties;
    Collection<OWLDataPropertyExpression[]> v_dataPropertyInclusions;
    Collection<OWLDataPropertyExpression[]> v_disjointDataProperties;
    Collection<OWLIndividualAxiom> v_facts;
    Set<OWLHasKeyAxiom> v_hasKeys;
    Set<String> v_definedDatatypesIRIs; // contains custom datatypes from DatatypeDefinition axioms
    Collection<DisjunctiveRule> v_rules;
	public ELAxioms() {
		v_classes = new HashSet<OWLClass>();
		v_objectproperties = new HashSet<OWLObjectProperty>();
		
	}
    public static class ComplexObjectPropertyInclusion {
        public final OWLObjectPropertyExpression[] v_subObjectProperties;
        public final OWLObjectPropertyExpression v_superObjectProperty;

        public ComplexObjectPropertyInclusion(OWLObjectPropertyExpression[] subObjectProperties,OWLObjectPropertyExpression superObjectPropery) {
            v_subObjectProperties=subObjectProperties;
            v_superObjectProperty=superObjectPropery;
        }
        public ComplexObjectPropertyInclusion(OWLObjectPropertyExpression transitiveObjectProperty) {
            v_subObjectProperties=new OWLObjectPropertyExpression[] { transitiveObjectProperty,transitiveObjectProperty };
            v_superObjectProperty=transitiveObjectProperty;
        }
    }
    
    
    public static class DisjunctiveRule {
        public final SWRLAtom[] v_body;
        public final SWRLAtom[] v_head;
        
        public DisjunctiveRule(SWRLAtom[] body,SWRLAtom[] head) {
            v_body=body;
            v_head=head;
        }
        public String toString() {
            StringBuilder buffer=new StringBuilder();
            boolean first=true;
            for (SWRLAtom atom : v_body) {
                if (first)
                    first=false;
                else
                    buffer.append(" /\\ ");
                buffer.append(atom.toString());
            }
            buffer.append(" -: ");
            first=true;
            for (SWRLAtom atom : v_head) {
                if (first)
                    first=false;
                else
                    buffer.append(" \\/ ");
                buffer.append(atom.toString());
            }
            return buffer.toString();
        }
    }
}
