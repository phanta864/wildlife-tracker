import org.sql2o.*;

import java.util.List;

public class Animal {
    protected String name;
    protected String age;
    protected String species;
    protected String health;
    protected int id;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getSpecies() {
        return species;
    }

    public String getHealth() {
        return health;
    }

    public int getId() {
        return id;
    }

//    public static List<Animal> all(){
//        String sql = "SELECT * FROM animals";
//        try (Connection con = DB.sql2o.open()){
//            return con.createQuery(sql).executeAndFetch(Animal.class);
//        }
//    }

    public static Animal find(int id){
        try(Connection con = DB.sql2o.open()){
            String sql ="SELECT * FROM animals where id=:id";
            Animal animal = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animal.class);
            return animal;
        }
    }

    public void save(){
        try(Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO animals(name, age, health, species) VALUES(:name, :age, :health, :species)";
            this.id=(int) con.createQuery(sql, true)
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
                    this.getAge().equals(newAnimal.getAge())&&
                    this.getHealth().equals(newAnimal.getHealth())&&
                    this.getId()==(newAnimal.getId());
        }
    }
}

