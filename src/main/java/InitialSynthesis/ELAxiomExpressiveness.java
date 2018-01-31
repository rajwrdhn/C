package InitialSynthesis;


import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

public class ELAxiomExpressiveness implements OWLClassExpressionVisitor {
	public boolean v_hasAtMostRestrictions;
    public boolean v_hasInverseRoles;
    public boolean v_hasNominals;
    public boolean v_hasDatatypes;
    public boolean v_hasSWRLRules;
    
    public ELAxiomExpressiveness(ELAxioms elAxioms) {
        for (OWLClassExpression[] inclusion : elAxioms.m_conceptInclusions)
            for (OWLClassExpression description : inclusion)
                description.accept(this);
        for (OWLObjectPropertyExpression[] inclusion : elAxioms.v_simpleObjectPropertyInclusions) {
            visitProperty(inclusion[0]);
            visitProperty(inclusion[1]);
        }
        for (ELAxioms.ComplexObjectPropertyInclusion inclusion : elAxioms.v_complexObjectPropertyInclusions) {
            for (OWLObjectPropertyExpression subObjectProperty : inclusion.v_subObjectProperties)
                visitProperty(subObjectProperty);
            visitProperty(inclusion.v_superObjectProperty);
        }
        for (OWLObjectPropertyExpression[] disjoint : elAxioms.v_disjointObjectProperties)
            for (OWLObjectPropertyExpression aDisjoint : disjoint) visitProperty(aDisjoint);
        for (OWLObjectPropertyExpression property : elAxioms.v_reflexiveObjectProperties)
            visitProperty(property);
        for (OWLObjectPropertyExpression property : elAxioms.v_irreflexiveObjectProperties)
            visitProperty(property);
        for (OWLObjectPropertyExpression property : elAxioms.v_asymmetricObjectProperties)
            visitProperty(property);
        if (!elAxioms.v_dataProperties.isEmpty()
        		|| !elAxioms.v_disjointDataProperties.isEmpty()
        		|| !elAxioms.v_dataPropertyInclusions.isEmpty()
        		|| !elAxioms.v_dataRangeInclusions.isEmpty()
        		|| !elAxioms.v_definedDatatypesIRIs.isEmpty())
            v_hasDatatypes=true;
        //for (OWLIndividualAxiom fact : elAxioms.v_facts)
         //   fact.accept(ELAxiomExpressiveness.this);
        v_hasSWRLRules=!elAxioms.v_rules.isEmpty();
    	
    }

    protected void visitProperty(OWLObjectPropertyExpression object) {
        if (object.isAnonymous())
            v_hasInverseRoles=true;
    }

    public void visit(OWLClass desc) {
    }

    public void visit(OWLObjectComplementOf object) {
        object.getOperand().accept(this);
    }

    public void visit(OWLObjectIntersectionOf object) {
        for (OWLClassExpression description : object.getOperands())
            description.accept(this);
    }

    public void visit(OWLObjectUnionOf object) {
        for (OWLClassExpression description : object.getOperands())
            description.accept(this);
    }

    public void visit(OWLObjectOneOf object) {
        v_hasNominals=true;
    }

    public void visit(OWLObjectSomeValuesFrom object) {
        visitProperty(object.getProperty());
        object.getFiller().accept(this);
    }

    public void visit(OWLObjectHasValue object) {
        v_hasNominals=true;
        visitProperty(object.getProperty());
    }

    public void visit(OWLObjectHasSelf object) {
        visitProperty(object.getProperty());
    }

    public void visit(OWLObjectAllValuesFrom object) {
        visitProperty(object.getProperty());
        object.getFiller().accept(this);
    }

    public void visit(OWLObjectMinCardinality object) {
        visitProperty(object.getProperty());
        object.getFiller().accept(this);
    }

    public void visit(OWLObjectMaxCardinality object) {
        v_hasAtMostRestrictions=true;
        visitProperty(object.getProperty());
        object.getFiller().accept(this);
    }

    public void visit(OWLObjectExactCardinality object) {
        v_hasAtMostRestrictions=true;
        visitProperty(object.getProperty());
        object.getFiller().accept(this);
    }

    public void visit(OWLDataHasValue object) {
        v_hasDatatypes=true;
    }

    public void visit(OWLDataSomeValuesFrom object) {
        v_hasDatatypes=true;
    }

    public void visit(OWLDataAllValuesFrom object) {
        v_hasDatatypes=true;
    }

    public void visit(OWLDataMinCardinality object) {
        v_hasDatatypes=true;
    }

    public void visit(OWLDataMaxCardinality object) {
        v_hasDatatypes=true;
    }

    public void visit(OWLDataExactCardinality object) {
        v_hasDatatypes=true;
    }

     public void visit(OWLClassAssertionAxiom object) {
        object.getClassExpression().accept(ELAxiomExpressiveness.this);
    }

    public void visit(OWLObjectPropertyAssertionAxiom object) {
        visitProperty(object.getProperty());
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom object) {
        visitProperty(object.getProperty());
    }

    public void visit(OWLDataPropertyAssertionAxiom object) {
        v_hasDatatypes=true;
    }
}
