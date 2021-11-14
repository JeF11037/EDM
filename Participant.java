import java.util.ArrayList;
import java.util.List;

enum Race {
    Elf,
    Dwarf
}

public class Participant {
    private String name;
    private Race race;
    private List<Relationship> relationship = new ArrayList<Relationship>();
    private int sumOfSwingCount;


    public Participant(String name, Race race) {
        this.name = name;
        this.race = race;
    }

    public String getName(){
        return this.name;
    }

    public Race getRace(){
        return this.race;
    }

    public void addRelationship(Relationship relationship){
        this.relationship.add(relationship);
    }

    public void addRelationship(int idx, Relationship relationship){
        this.relationship.add(idx, relationship);
    }

    public void removeRelationship(Relationship relationship){
        this.relationship.remove(relationship);
    }

    public void removeRelationship(int idx){
        this.relationship.remove(idx);
    }

    public void setRelationship(List<Relationship> relationship){
        this.relationship = relationship;
    }

    public List<Relationship> getRelationship(){
        return this.relationship;
    }

    public void setSumOfSwingCount(int value){
        this.sumOfSwingCount = value;
    }

    public int getSumOfSwingCount(){
        return this.sumOfSwingCount;
    }
}
