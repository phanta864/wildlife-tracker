
import org.sql2o.*;

import java.util.List;

public class RegisterAnimal extends Animal {
    private static final String ANIMAL_SPECIES = "safe";

    public RegisterAnimal(String name, String age, String health, String species){
        if (name.equals("")){
            throw new IllegalArgumentException("Please enter name");
        }
        if (age.equals("")){
            throw new IllegalArgumentException("Please enter age");
        }
        if (health.equals("")){
            throw new IllegalArgumentException("Please enter the age of the animal");
        }

        this.name= name;
        this.age = age;
        this.health = health;
        this.species = ANIMAL_SPECIES;
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO animals (name, age, health, species) VALUES (:name, :age, :health, :species);";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("age", this.age)
                    .addParameter("health", this.health)
                    .addParameter("species", this.species)
                    .executeUpdate()
                    .getKey();
        }
    }

    @Override
    public boolean equals(Object otherAnimal){
        if(!(otherAnimal instanceof Animal)){
            return false;
        }
        else {
            Animal newAnimal = (Animal) otherAnimal;
            return  this.getName().equals(newAnimal.getName())&&
                    this.getSpecies().equals(newAnimal.getSpecies())&&
                    this.getId()==(newAnimal.getId());
        }
    }

    public static List<RegisterAnimal> all(){
        String sql = "SELECT * FROM animals WHERE species = 'safe'";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(RegisterAnimal.class);
        }
    }
}
