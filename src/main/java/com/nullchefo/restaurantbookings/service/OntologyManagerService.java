
package com.nullchefo.restaurantbookings.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
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
import com.nullchefo.restaurantbookings.entity.Ingredient;

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

	// get all dishes

	public ArrayList<Dish> getAllDishes() {
		ArrayList<Dish> result = new ArrayList<>();

		OWLClass appetizerClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Appetizer"));
		OWLClass dessertClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Dessert"));
		OWLClass mainClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Main"));
		OWLClass uncategorizedDishClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "UncategorizedDish"));

		OWLObjectProperty hasDairy = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasDairy"));
		OWLObjectProperty hasGrain = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasGrain"));
		OWLObjectProperty hasMeat = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasMeat"));
		OWLObjectProperty hasNuts = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasNuts"));
		OWLObjectProperty hasOther = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasOther"));
		OWLObjectProperty hasSpice = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasSpice"));
		OWLObjectProperty hasVegetable = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasVegetable"));

		for (OWLClass owlClass : ontology.getClassesInSignature()) {
			if (isDirectSubclass(owlClass, appetizerClass) || isDirectSubclass(owlClass, dessertClass) || isDirectSubclass(owlClass, mainClass) || isDirectSubclass(owlClass, uncategorizedDishClass)) {
				Dish p = new Dish();
				p.setName(getClassFriendlyName(owlClass));
				p.setId(owlClass.getIRI().toQuotedString());

				Ingredient ingredient = Ingredient.builder()
												  .dairy(getAllIngredients(owlClass, hasDairy))
												  .grain(getAllIngredients(owlClass, hasGrain))
												  .meat(getAllIngredients(owlClass, hasMeat))
												  .nuts(getAllIngredients(owlClass, hasNuts))
												  .others(getAllIngredients(owlClass, hasOther))
												  .spices(getAllIngredients(owlClass, hasSpice))
												  .vegetables(getAllIngredients(owlClass, hasVegetable))
												  .build();

				p.setIngredient(ingredient);
				result.add(p);
			}
		}

		return result;
	}

	private boolean isDirectSubclass(OWLClass subclass, OWLClass superclass) {
		for (OWLClassExpression superClassExpr : subclass.getSuperClasses(ontology)) {
			if (superClassExpr instanceof OWLClass) {
				OWLClass superClass = (OWLClass) superClassExpr;
				if (superClass.equals(superclass)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean containsSuperClass(Collection<OWLClassExpression> superClasses, OWLClass superClass) {
		for (OWLClassExpression classExpression : superClasses) {
			if (classExpression.asOWLClass().equals(superClass)) {
				return true;
			}
		}
		return false;
	}

	private String ingredientsToString(final List<String> ingredients) {
		StringBuilder result = new StringBuilder();
		for (String ingredient : ingredients) {
			result.append(ingredient).append(", ");
		}
		return result.toString();
	}
	private List<String> stringToIngredients(final String ingredients) {
		List<String> result = new ArrayList<>();
		String[] split = ingredients.split(", ");
		Collections.addAll(result, split);
		return result;
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
				IRI.create(ontologyIRIStr + "hasVegetable")
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
				IRI.create(ontologyIRIStr + "hasVegetable")
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

	public void renameDish(String oldName, String newName) {

		OWLEntityRenamer renames = new OWLEntityRenamer(
				ontologyManager,
				ontology.getImportsClosure());

		IRI newIRI = IRI.create(ontologyIRIStr + newName);
		IRI oldIRI = IRI.create(ontologyIRIStr + oldName);

		ontologyManager.applyChanges(renames.changeIRI(oldIRI, newIRI));

		saveOntology();

	}

	public ArrayList<Dish> getDishByVegetable(String vegetable) {
		ArrayList<Dish> result = new ArrayList<>();

		OWLClass appetizerClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Appetizer"));
		OWLClass dessertClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Dessert"));
		OWLClass mainClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Main"));
		OWLClass uncategorizedDishClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "UncategorizedDish"));

		OWLObjectProperty hasVegetable = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasVegetable"));
		OWLNamedIndividual vegetableIndividual = dataFactory.getOWLNamedIndividual(IRI.create(ontologyIRIStr + vegetable));




		for (OWLClass owlClass : ontology.getClassesInSignature()) {
			if (isDirectSubclass(owlClass, appetizerClass) || isDirectSubclass(owlClass, dessertClass) || isDirectSubclass(owlClass, mainClass) || isDirectSubclass(owlClass, uncategorizedDishClass)) {
				Set<OWLNamedIndividual> vegetablesInDish = getAllIngredientsV2(owlClass, hasVegetable);
				if (vegetablesInDish.contains(vegetableIndividual)) {

					List<String> vegetablesInDishList = new ArrayList<>();
					for (OWLNamedIndividual ingredient : vegetablesInDish) {
						vegetablesInDishList.add(ingredient.getIRI().toString());
					}

					Dish p = new Dish();
					p.setName(getClassFriendlyName(owlClass));
					p.setId(owlClass.getIRI().toQuotedString());

					Ingredient ingredient = Ingredient.builder()
													  .dairy(getAllIngredientsAsStrings(owlClass, dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasDairy"))))
													  .grain(getAllIngredientsAsStrings(owlClass, dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasGrain"))))
													  .meat(getAllIngredientsAsStrings(owlClass, dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasMeat"))))
													  .nuts(getAllIngredientsAsStrings(owlClass, dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasNuts"))))
													  .others(getAllIngredientsAsStrings(owlClass, dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasOther"))))
													  .spices(getAllIngredientsAsStrings(owlClass, dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasSpice"))))
													  .vegetables(vegetablesInDishList)
													  .build();

					p.setIngredient(ingredient);
					result.add(p);
				}
			}
		}

		return result;
	}

	private boolean isDirectSubclassV2(OWLClass subclass, OWLClass superclass) {
		for (OWLClassExpression superClassExpr : subclass.getSuperClasses(ontology)) {
			if (superClassExpr instanceof OWLClass) {
				OWLClass superClass = (OWLClass) superClassExpr;
				if (superClass.equals(superclass)) {
					return true;
				}
			}
		}
		return false;
	}

	private Set<OWLNamedIndividual> getAllIngredientsV2(OWLClass dishClass, OWLObjectProperty property) {
		Set<OWLNamedIndividual> ingredients = new HashSet<>();
		for (OWLIndividual individual : dishClass.getIndividualsInSignature()) {
			for (OWLAxiom axiom : ontology.getAxioms(individual)) {
				if (axiom instanceof OWLObjectPropertyAssertionAxiom) {
					OWLObjectPropertyAssertionAxiom propertyAxiom = (OWLObjectPropertyAssertionAxiom) axiom;
					if (propertyAxiom.getProperty().equals(property)) {
						ingredients.add(propertyAxiom.getObject().asOWLNamedIndividual());
					}
				}
			}
		}
		return ingredients;
	}


	private List<String> getAllIngredientsAsStrings(OWLClass dishClass, OWLObjectProperty property) {
		Set<OWLNamedIndividual> ingredients = new HashSet<>();
		for (OWLIndividual individual : dishClass.getIndividualsInSignature()) {
			for (OWLAxiom axiom : ontology.getAxioms(individual)) {
				if (axiom instanceof OWLObjectPropertyAssertionAxiom) {
					OWLObjectPropertyAssertionAxiom propertyAxiom = (OWLObjectPropertyAssertionAxiom) axiom;
					if (propertyAxiom.getProperty().equals(property)) {
						ingredients.add(propertyAxiom.getObject().asOWLNamedIndividual());
					}
				}
			}
		}
		List<String> ingredientNames = new ArrayList<>();
		for (OWLNamedIndividual ingredient : ingredients) {
			ingredientNames.add(ingredient.getIRI().toString());
		}
		return ingredientNames;
	}


	// delete Dish
	public void deleteDish(String name) {
		OWLClass classToRemove = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + name));

		OWLEntityRemover remover = new OWLEntityRemover(
				ontologyManager,
				ontology.getImportsClosure());

		classToRemove.accept(remover);

		ontologyManager.applyChanges(remover.getChanges());
		saveOntology();

	}

	public void editCreateDish(final Dish dish) {

		if (dish.getId() != null) {
			editDish(dish);
		} else {
			createDish(dish);
		}

	}

	public void createDish(final Dish dish) {

		// creates the dish
		createDishType(dish.getName());

		// adds ingredients
		// todo
//		for (String ingredient : stringToIngredients(dish.getIngredients())) {
//			addIngredientToDish(dish.getName(), );
//		}
		saveOntology();
	}

	//edit
	public void editDish(final Dish dish) {
		// get dish by id
		OWLClass dishClass = dataFactory.getOWLClass(IRI.create(dish.getId()));

		// gets all ingredients from the dishClass
		OWLObjectProperty hasIngredient = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasVegetable"));
		List<String> ingredients = getAllIngredients(dishClass, hasIngredient);

		System.out.printf("Dish: %s Ingredients: %s%n", dish.getName(), ingredients);





	}


}
