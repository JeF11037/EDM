public class Relationship {
    private Participant participant;
    private Swing swing;

    public Relationship(Participant participant, Swing swing){
        this.participant = participant;
        this.swing = swing;
    }

    public Participant getParticipant(){
        return this.participant;
    }

    public Swing getSwing(){
        return this.swing;
    }
}
