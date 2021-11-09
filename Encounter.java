public class Encounter {

    private Participant elf;
    private Swing elfinSwing;
    private Participant dwarf;
    private Swing dwarvenSwing;

    public Encounter(Participant elf, Swing elfinSwing, Participant dwarf, Swing dwarveSwing){
        this.elf = elf;
        this.elfinSwing = elfinSwing;
        this.dwarf = dwarf;
        this.dwarvenSwing = dwarveSwing;
    }

    public Participant getElf(){
        return elf;
    }

    public Swing getElfinSwing(){
        return elfinSwing;
    }

    public Participant getDwarf(){
        return dwarf;
    }

    public Swing getDwarvenSwing(){
        return dwarvenSwing;
    }
}
