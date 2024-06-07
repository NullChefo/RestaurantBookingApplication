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

import com.nullchefo.restaurantbookings.entity.Dish;

@Service
public class OntologyManagerService {

	private final OWLOntologyManager ontologyManager;
	private final OWLDataFactory dataFactory;
	private final String ontologyIRIStr;
	private OWLOntology ontology;
	private OWLReasoner reasoner;
	private boolean contains = false;

	public OntologyManagerService() {
		ontologyManager = OWLManager.createOWLOntologyManager();
		dataFactory = ontologyManager.getOWLDataFactory();
		loadOntologyFromFile("src/main/resources/ontology/food/new-food-ontology.owl");

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

		for (OWLClassExpression clsExps : setOfClasses) {

			for (OWLClass cls : clsExps.getClassesInSignature()) {
				if (cls.getIRI().equals(superClass.getIRI())) {
					contains = true;
				} else {
					if (!cls.getSubClasses(ontology).isEmpty()) {
						containsSuperClass(
								cls.getSuperClasses(ontology),
								superClass);
					}
				}
			}

		}
		return contains;
	}

	private List<String> getAllIngredients(OWLClass dishClass, OWLObjectProperty hasIngredient) {

		List<String> ingredients = new ArrayList<>();

		for (OWLAxiom axiom :
				dishClass.getReferencingAxioms(ontology)) {

			if (axiom.getAxiomType() == AxiomType.SUBCLASS_OF) {

				for (OWLObjectProperty op :
						axiom.getObjectPropertiesInSignature()) {

					if (op.getIRI().equals(hasIngredient.getIRI())) {

						for (OWLClass classInAxiom :
								axiom.getClassesInSignature()) {

							if (!classInAxiom.getIRI().equals(dishClass.getIRI())) {
								ingredients.add(getClassFriendlyName(classInAxiom));
							}

						}
					}
				}
			}
		}

		return ingredients;
	}

	private void saveOntology() {
		try {
			ontologyManager.saveOntology(ontology);

		} catch (OWLOntologyStorageException e) {
			System.out.println("Cannot edit the file at that moment");
			e.printStackTrace();
		}
	}

	public void createDishType(String dishName) {
		//Класът който ще създаваме
		OWLClass dishClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + dishName));

		//Родителят където искаме да го поставим
		OWLClass parentClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + "UncategorizedDish"));

		//Създаваме аксиома на sublcass която ще е връзка между двете
		OWLSubClassOfAxiom subClassOf = dataFactory.getOWLSubClassOfAxiom(
				dishClass, parentClass);

		//Това ще ни позволи да добавим към класа новата информация
		AddAxiom axiom = new AddAxiom(ontology, subClassOf);

		//Това придизвиква извършване на действието
		ontologyManager.applyChange(axiom);

		//Предизвиква запазване на състоянито
		saveOntology();
	}

	public void addIngredientToDish(String dishName, String ingredientName) {

		//Класа на ястието към която ще добавяме съставката
		OWLClass dishClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + dishName));

		//Съставката класа който ще сложим към ястието
		OWLClass ingredientClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + ingredientName));

		System.out.println("Dish: " + dishName + " Ingredient: " + ingredientName);

		//Взимаме инстанция на hasIngredient property
		OWLObjectProperty hasIngredient = dataFactory.getOWLObjectProperty(
				IRI.create(ontologyIRIStr + "hasIngredient")
																		  );

		OWLClassExpression classExpression = dataFactory.getOWLObjectSomeValuesFrom(
				hasIngredient, ingredientClass);

		OWLSubClassOfAxiom axiom = dataFactory.getOWLSubClassOfAxiom(
				dishClass, classExpression);

		AddAxiom addAxiom = new AddAxiom(ontology, axiom);

		ontologyManager.applyChange(addAxiom);

		saveOntology();

	}

	public void deleteIngredient(String name) {
		OWLClass classToRemove = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + name));

		OWLEntityRemover remover = new OWLEntityRemover(
				ontologyManager,
				ontology.getImportsClosure());

		classToRemove.accept(remover);

		ontologyManager.applyChanges(remover.getChanges());
		saveOntology();

	}

	public void removeIngredientFromDish(String dishName, String ingredientName) {

		//Класа на ястието към която ще добавяме съставката
		OWLClass dishClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + dishName));

		//Съставки класа който ще сложим към ястието
		OWLClass ingredientClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + ingredientName));

		System.out.println("Dish: " + dishName + " Ingredient: " + ingredientName);

		//Взимаме инстанция на hasIngredient property
		OWLObjectProperty hasIngredient = dataFactory.getOWLObjectProperty(
				IRI.create(ontologyIRIStr + "hasIngredient")
																		  );

		OWLClassExpression classExpression = dataFactory.getOWLObjectSomeValuesFrom(
				hasIngredient, ingredientClass);

		OWLSubClassOfAxiom axiom = dataFactory.getOWLSubClassOfAxiom(
				dishClass, classExpression);

		RemoveAxiom removeAxiom = new RemoveAxiom(ontology, axiom);

		ontologyManager.applyChange(removeAxiom);

		saveOntology();

	}

	public void renameIngredient(String oldName, String newName) {

		OWLEntityRenamer renames = new OWLEntityRenamer(
				ontologyManager,
				ontology.getImportsClosure());

		IRI newIRI = IRI.create(ontologyIRIStr + newName);
		IRI oldIRI = IRI.create(ontologyIRIStr + oldName);

		ontologyManager.applyChanges(renames.changeIRI(oldIRI, newIRI));

		saveOntology();

	}

	public ArrayList<Dish> getDishByIngredient(String ingredient) {

		ArrayList<Dish> result = new ArrayList<>();

		OWLObjectProperty hasIngredient = dataFactory
				.getOWLObjectProperty(
						IRI.create(ontologyIRIStr + "hasIngredient"));

		OWLClass ingredientClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + ingredient));

		for (OWLAxiom axiom :
				ingredientClass.getReferencingAxioms(ontology)) {

			if (axiom.getAxiomType() == AxiomType.SUBCLASS_OF) {

				for (OWLObjectProperty op :
						axiom.getObjectPropertiesInSignature()) {

					if (op.getIRI().equals(hasIngredient.getIRI())) {

						for (OWLClass classInAxiom :
								axiom.getClassesInSignature()) {

							if (containsSuperClass(
									classInAxiom.getSuperClasses(ontology),
									dataFactory.getOWLClass(
											IRI.create(ontologyIRIStr + "Dish")))) {

								contains = false;

								Dish p = new Dish();
								p.setName(getClassFriendlyName(classInAxiom));
								p.setId(classInAxiom.getIRI().toQuotedString());

								p.setIngredients(getAllIngredients(classInAxiom
										, hasIngredient));

								result.add(p);
							}

						}
					}

				}

			}

		}

		return result;
	}

}
