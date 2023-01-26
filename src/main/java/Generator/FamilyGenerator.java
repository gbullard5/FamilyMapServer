package Generator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.Person;

public class FamilyGenerator {
  private Random random = new Random();
  private ArrayList<Person> tree;
  private EventGen eventGen;


  private String username;

  public GenData genGen(int numGen, Person user){
    username = user.getPersonID();
    eventGen = new EventGen(username);
    beginTree(user, numGen);
    return new GenData(tree, eventGen.getAll());
  }

  private void beginTree(Person person, int gen){
    tree = new ArrayList<Person>();
    tree.add(person);
    int year = 1990;
    eventGen.birthEvent(person, year);
    generateParents(person, gen-1, year);
  }

  private void generateParents(Person person, int gen, int year){
    int gap = 30;
    year = year - gap - random.nextInt(10);
    Person mother = new Person();
    Person father = genFather(person);
    mother = genMother(mother, father);
    father.setSpouseID(mother.getPersonID());
    person.setFatherID(father.getPersonID());
    person.setMotherID(mother.getPersonID());
    mother.setAssociatedUsername(person.getAssociatedUsername());
    father.setAssociatedUsername(person.getAssociatedUsername());
    father.setSpouseID(mother.getPersonID());
    genEvents(mother, father, year);
    if(gen > 0){
      generateParents(father, gen-1, year);
      generateParents(mother,gen-1,year);
    }
    tree.add(mother);
    tree.add(father);
  }

  private Person genMother(Person mother, Person husband){
    mother.setFirstName(nameGenFemale());
    mother.setGender("f");
    mother.setSpouseID(husband.getPersonID());
    mother.setLastName(nameGenLast());
    mother.setPersonID(UUID.randomUUID().toString());
    return mother;
  }

  private Person genFather(Person child){
    Person father = new Person();
    father.setFirstName(nameGenMale());
    father.setGender("m");
    father.setPersonID(UUID.randomUUID().toString());
    if(child.getGender() == "m"){
      father.setLastName(child.getLastName());
    }else{
      father.setLastName(nameGenLast());
    }
    return father;
  }

  private void genEvents(Person mother, Person father, int year){
    eventGen.birthEvent(father, year);
    eventGen.birthEvent(mother,year);
    eventGen.marriageEvent(father,mother,year);
    eventGen.deathEvent (father,year);
    eventGen.deathEvent(mother,year);

  }

  private String nameGenMale(){
    Random rand = new Random();
    try{
      FileReader fileReader = new FileReader("json/mnames.json");
      JsonParser jsonParser = new JsonParser();
      JsonObject obj = (JsonObject) jsonParser.parse(fileReader);
      JsonArray array = (JsonArray) obj.get("data");
      int pos = rand.nextInt(array.size());
      String name = array.get(pos).toString();
      name =name.substring(1,name.length()-1);
      return name;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return "error";
  }
  private String nameGenFemale(){
    Random rand = new Random();
    try{
      FileReader fileReader = new FileReader("json/fnames.json");
      JsonParser jsonParser = new JsonParser();
      JsonObject obj = (JsonObject) jsonParser.parse(fileReader);
      JsonArray array = (JsonArray) obj.get("data");
      int pos = rand.nextInt(array.size());
      String name = array.get(pos).toString();
      name =name.substring(1,name.length()-1);
      return name;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return "error";
  }
  private String nameGenLast(){
    Random rand = new Random();
    try{
      FileReader fileReader = new FileReader("json/snames.json");
      JsonParser jsonParser = new JsonParser();
      JsonObject obj = (JsonObject) jsonParser.parse(fileReader);
      JsonArray array = (JsonArray) obj.get("data");
      int pos = rand.nextInt(array.size());
      String name = array.get(pos).toString();
      name =name.substring(1,name.length()-1);
      return name;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return "error";
  }
}
