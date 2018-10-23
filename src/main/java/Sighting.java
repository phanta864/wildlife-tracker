
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.List;

public class Sighting implements DataBase {
    private String name;
    private String location;
    private int animalId;
    private Timestamp timestamp;
    private int id;

    public Sighting (String name, String location, int animalId){
        if (name.equals("")){
            throw new IllegalArgumentException("Please enter name");
        }
        if (location.equals("")){
            throw new IllegalArgumentException("Please enter location");
        }
        this.name = name;
        this.location = location;
        this.animalId = animalId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getAnimalId() {
        return animalId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sighting(name, location, animalId, timestamp) VALUES (:name, :location, :animalId, now());";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("location", this.location)
                    .addParameter("animalId", this.animalId)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Sighting> all(){
        String sql = "SELECT * FROM sighting;";
        try(Connection con = DB.sql2o.open()){
            return con.createQuery(sql).executeAndFetch(Sighting.class);
        }
    }

    public static Sighting find(int id){
        String sql = "SELECT * FROM sighting WHERE id = :id";
        try(Connection con = DB.sql2o.open()){
            Sighting sighting = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sighting.class);
            return sighting;
        }
    }

    public Animal getAnimal(){
        String sql = "SELECT * FROM animals WHERE id = :id";
        try(Connection con = DB.sql2o.open()){
            Animal animal = con.createQuery(sql)
                    .addParameter("id", this.animalId)
                    .executeAndFetchFirst(Animal.class);
            return animal;
        }
    }

    @Override
    public void delete() {
        try (Connection con = DB.sql2o.open()){
            String sql ="DELETE FROM sighting WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public boolean equals(Object otherSighting){
        if(!(otherSighting instanceof Sighting)){
            return false;
        }
        else {
            Sighting newSighting = (Sighting) otherSighting;
            return  this.getName().equals(newSighting.getName())&&
                    this.getLocation().equals(newSighting.getLocation())&&
                    this.getId()==newSighting.getId();
        }
    }
}
