enum Race {
    Elf,
    Dwarf
}

public class Participant {
    private String name;
    private Race race;

    public Participant(String name, Race race) {
        this.name = name;
        this.race = race;
    }

    public String getName(){
        return name;
    }

    public Race getRace(){
        return race;
    }
}
