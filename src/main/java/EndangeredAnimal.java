import org.sql2o.*;

import java.util.List;


public class EndangeredAnimal extends Animal{
    private static final String ANIMAL_SPECIES = "endangered";

    public EndangeredAnimal(String name, String age, String health, String species){
        if(name.equals("")){
            throw new IllegalArgumentException("Please enter name");
        }
        if(age.equals("")){
            throw new IllegalArgumentException("Please enter age");
        }
        if(health.equals("")){
            throw new IllegalArgumentException("Please enter the health of the animal");
        }
        if(species.equals("")){
            throw new IllegalArgumentException("Please enter the species of the animal");
        }

        this.name = name;
        this.age = age;
        this.health = health;
        this.species = species;
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

    public static List<EndangeredAnimal> all() {
        String sql = "SELECT * FROM animals WHERE species = 'endangered'";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(EndangeredAnimal.class);
        }

    }

}


