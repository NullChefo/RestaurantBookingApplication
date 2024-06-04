package com.nullchefo.restaurantbookings.service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.OWLEntityRenamer;



import org.springframework.stereotype.Service;

@Service
public class OntologyManagerService {


	private final OWLOntologyManager ontologyManager;
	private OWLOntology ontology;
	private final OWLDataFactory dataFactory;
	private OWLReasoner reasoner;

	private final String ontologyIRIStr;
	private boolean contains = false;

	public OntologyManagerService() {
		ontologyManager = OWLManager.createOWLOntologyManager();
		dataFactory = ontologyManager.getOWLDataFactory();
		loadOntologyFromFile("src/main/resources/ontology/food/food-ontology.owl");

		ontologyIRIStr = ontology.getOntologyID()
									  .getOntologyIRI().toString() + "#";

	}

	private void loadOntologyFromFile(final String pathName) {
		File ontoFile = new File(pathName);

		try {
			ontology = ontologyManager
					.loadOntologyFromOntologyDocument(ontoFile);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

	private String getClassFriendlyName(OWLClass cls) {

		String label = cls.getIRI().toString();
		label = label.substring(label.indexOf('#') + 1);

		return label;

	}

	private boolean containsSuperClass(
			Set<OWLClassExpression> setOfClasses,
			OWLClass superClass) {

		for(OWLClassExpression clsExps : setOfClasses) {

			for(OWLClass cls : clsExps.getClassesInSignature()) {
				if(cls.getIRI().equals(superClass.getIRI())) {
					contains = true;
				}else {
					if(!cls.getSubClasses(ontology).isEmpty()) {
						containsSuperClass(
								cls.getSuperClasses(ontology),
								superClass);
					}
				}
			}

		}
		return contains;
	}

	// Cuisne




	// Add cuisine to Food class


	// Edit Cuisine to Food class


	// Delete Cuisine from Food class




	// Meal class



	// Add Meal to Cuisine





	// class Ingredients



	// CookingMethod class









	private List<String> getAllToppings(OWLClass pizzaClass, OWLObjectProperty hasTopping) {

		List<String> toppings = new ArrayList<>();

		for(OWLAxiom axiom :
				pizzaClass.getReferencingAxioms(ontology)) {

			if(axiom.getAxiomType() == AxiomType.SUBCLASS_OF) {

				for(OWLObjectProperty op :
						axiom.getObjectPropertiesInSignature()) {

					if(op.getIRI().equals(hasTopping.getIRI())) {

						for(OWLClass classInAxiom:
								axiom.getClassesInSignature()) {

							if(!classInAxiom.getIRI().equals(pizzaClass.getIRI())) {
								toppings.add(getClassFriendlyName(classInAxiom));
							}

						}
					}
				}
			}
		}


		return toppings;
	}



	private void saveOntology() {
		try {
			ontologyManager.saveOntology(ontology);

		} catch (OWLOntologyStorageException e) {
			System.out.println("Cannot edit the file at that moment");
			e.printStackTrace();
		}
	}

	public void createPizzaType(String pizzaName) {
		//Класът който ще създаваме
		OWLClass pizzaClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + pizzaName));

		//Родителят където искаме да го поставим
		OWLClass parentClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + "NamedPizza"));

		//Създаваме аксиома на sublcass която ще е връзка между двете
		OWLSubClassOfAxiom subClassOf = dataFactory.getOWLSubClassOfAxiom(
				pizzaClass, parentClass);

		//Това ще ни позволи да добавим към класа новата информация
		AddAxiom axiom = new AddAxiom(ontology, subClassOf);

		//Това придизвиква извършване на действието
		ontologyManager.applyChange(axiom);

		//Предизвиква запазване на състоянито
		saveOntology();
	}

	public void addToppingToPizza(String pizzaName, String toppingName) {

		//Класа на пициата към която щше добавяме топинг
		OWLClass pizzaClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + pizzaName));

		//Топинг класа който ще сложим към пицата
		OWLClass toppingClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + toppingName));

		System.out.println("Pizza: " + pizzaName + " Topping: " + toppingName);

		//Взимаме инстанция на hasTopping property
		OWLObjectProperty hasTopping = dataFactory.getOWLObjectProperty(
				IRI.create(ontologyIRIStr + "hasTopping")
																	   );

		OWLClassExpression classExpression = dataFactory.getOWLObjectSomeValuesFrom(
				hasTopping, toppingClass);

		OWLSubClassOfAxiom axiom = dataFactory.getOWLSubClassOfAxiom(
				pizzaClass, classExpression);


		AddAxiom addAxiom = new AddAxiom(ontology, axiom);

		ontologyManager.applyChange(addAxiom);

		saveOntology();

	}

	public void deleteTopping(String name) {
		OWLClass classToRemove = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + name));

		OWLEntityRemover remover = new OWLEntityRemover(ontologyManager,
														ontology.getImportsClosure());

		classToRemove.accept(remover);

		ontologyManager.applyChanges(remover.getChanges());
		saveOntology();

	}

//	public void removeToppingFromPizza(String pizzaName, String toppingName) {
//
//		//Класа на пициата към която щше добавяме топинг
//		OWLClass pizzaClass = dataFactory.getOWLClass(
//				IRI.create(ontologyIRIStr + pizzaName));
//
//		//Топинг класа който ще сложим към пицата
//		OWLClass toppingClass = dataFactory.getOWLClass(
//				IRI.create(ontologyIRIStr + toppingName));
//
//		System.out.println("Pizza: " + pizzaName + " Topping: " + toppingName);
//
//		//Взимаме инстанция на hasTopping property
//		OWLObjectProperty hasTopping = dataFactory.getOWLObjectProperty(
//				IRI.create(ontologyIRIStr + "hasTopping")
//																	   );
//
//		OWLClassExpression classExpression = dataFactory.getOWLObjectSomeValuesFrom(
//				hasTopping, toppingClass);
//
//		OWLSubClassOfAxiom axiom = dataFactory.getOWLSubClassOfAxiom(
//				pizzaClass, classExpression);
//
//
//		RemoveAxiom removeAxiom = new RemoveAxiom(ontology, axiom);
//
//		ontologyManager.applyChange(removeAxiom);
//
//		saveOntology();
//
//	}
//
//	public void renameTopping(String oldName, String newName) {
//
//		OWLEntityRenamer renamer = new OWLEntityRenamer(ontologyManager,
//														ontology.getImportsClosure());
//
//		IRI newIRI = IRI.create(ontologyIRIStr + newName);
//		IRI oldIRI = IRI.create(ontologyIRIStr + oldName);
//
//		ontologyManager.applyChanges(renamer.changeIRI(oldIRI, newIRI));
//
//		saveOntology();
//
//	}
//
//	public ArrayList<Pizza> getPizzaByTopping(String topping){
//
//		ArrayList<Pizza> result = new ArrayList<>();
//
//		OWLObjectProperty hasTopping = dataFactory
//				.getOWLObjectProperty(
//						IRI.create(ontologyIRIStr + "hasTopping"));
//
//		OWLClass toppingClass = dataFactory.getOWLClass(
//				IRI.create(ontologyIRIStr + topping));
//
//
//		for(OWLAxiom axiom :
//				toppingClass.getReferencingAxioms(ontology)) {
//
//
//			if(axiom.getAxiomType() == AxiomType.SUBCLASS_OF) {
//
//
//				for(OWLObjectProperty op:
//						axiom.getObjectPropertiesInSignature()) {
//
//
//					if(op.getIRI().equals(hasTopping.getIRI())) {
//
//
//						for(OWLClass classInAxiom:
//								axiom.getClassesInSignature()) {
//
//							if(containsSuperClass(
//									classInAxiom.getSuperClasses(ontology),
//									dataFactory.getOWLClass(
//											IRI.create(ontologyIRIStr + "Pizza")))) {
//
//								contains = false;
//
//								Pizza p = new Pizza();
//								p.setName(getClassFriendlyName(classInAxiom));
//								p.setId(classInAxiom.getIRI().toQuotedString());
//
//								p.setTopping(getAllToppings(classInAxiom
//										, hasTopping));
//
//								result.add(p);
//							}
//
//						}
//					}
//
//				}
//
//			}
//
//		}
//
//		return result;
//	}




}
