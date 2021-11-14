import java.util.List;
import java.util.ArrayList;

public class SortingTree {
    private List<Participant> participants = new ArrayList<Participant>();
    private List<Encounter> encounters = new ArrayList<Encounter>();
    private List<Participant> avoided = new ArrayList<Participant>();
    private List<Participant[]> couples = new ArrayList<Participant[]>();

    public SortingTree(List<Participant> participants, List<Encounter> encounters){
        this.participants = participants;
        this.encounters = encounters;
        fitRelationship();
        for (Participant participant : this.participants) {
            participant.setRelationship(relationshipSort(participant));
        }
        while (this.avoided.size() != getDwarvesCount(participants)*2){
            for(int i = 0; i < 10; i++){
                for (Participant participant : this.participants) {
                    stabilizeOptions(participant, this.participants);
                }
            }
            for (Participant participant : this.participants) {
                Participant mate = matchSuitablePartner(participant);
                if (!mate.equals(participant)){
                    if (!this.avoided.contains(participant) && !this.avoided.contains(mate)){
                        if (participant.getRace().equals(Race.Elf)){
                            this.avoided.add(participant);
                            this.avoided.add(mate);
                        }
                        if (participant.getRace().equals(Race.Dwarf)){
                            this.avoided.add(mate);
                            this.avoided.add(participant);
                        }
                    }
                }
            }
        }
        for (int idx = 0; idx < this.avoided.size(); idx+=2){
            this.couples.add(new Participant[]{
                this.avoided.get(idx),
                this.avoided.get(idx+1)
            });
        }
    }

    private int getDwarvesCount(List<Participant> participants){
        int count = 0;
        for (Participant participant : participants) {
            if (participant.getRace().equals(Race.Dwarf))
                count++;
        }
        return count;
    }

    private int getSumOfSwingCount(Participant participant){
        int count = 0;
        for (Relationship relationship : participant.getRelationship()) {
            count += relationship.getSwing().getSwingCount();
        }
        return count;
    }

    private Participant matchSuitablePartner(Participant participant){
        double maxPercent = 0.0;
        if (participant.getRelationship().size() >= 1){
            Participant mate = participant.getRelationship().get(0).getParticipant();
            for (Relationship m : mate.getRelationship()) {
                for (Relationship o : m.getParticipant().getRelationship()) {
                    if (o.getParticipant().equals(mate)){
                        double p = Double.valueOf(o.getSwing().getSwingCount()) / Double.valueOf(o.getParticipant().getSumOfSwingCount())+
                        Double.valueOf(m.getSwing().getSwingCount()) / Double.valueOf(m.getParticipant().getSumOfSwingCount());
                        if (maxPercent < p){
                            maxPercent = p ;
                        }
                    }
                }
            }
            for (Relationship m : mate.getRelationship()) {
                if (m.getParticipant().equals(participant)){
                    double p = Double.valueOf(m.getSwing().getSwingCount()) / Double.valueOf(m.getParticipant().getSumOfSwingCount())+
                    Double.valueOf(participant.getRelationship().get(0).getSwing().getSwingCount()) / Double.valueOf(participant.getSumOfSwingCount());
                    if (p >= maxPercent)
                        return mate;
                }
            }
        }
        return participant;
    }

    private void stabilizeOptions(Participant participant, List<Participant> participants){
        List<Relationship> relationship = participant.getRelationship();
        for (int idx = 0; idx < relationship.size(); idx++){
            if (!this.avoided.contains(participant)){
                if (this.avoided.contains(relationship.get(idx).getParticipant()))
                    relationship.remove(idx);
            } else {
                if (participant.getRace().equals(Race.Elf)){
                    if (!this.avoided.get(this.avoided.indexOf(participant)+1).equals(relationship.get(idx).getParticipant()))
                        relationship.remove(idx);
                }
                if (participant.getRace().equals(Race.Dwarf)){
                    if (!this.avoided.get(this.avoided.indexOf(participant)-1).equals(relationship.get(idx).getParticipant()))
                        relationship.remove(idx);
                }
            }
        }
        if (relationship.size() >= 1){
            Participant possiblePartner = relationship.get(0).getParticipant();
            List<Relationship> possiblePartnerRelationship = possiblePartner.getRelationship();
            if (possiblePartnerRelationship.get(0).getParticipant().equals(participant)){
                if (!this.avoided.contains(participant) && !this.avoided.contains(possiblePartner)){
                    if (participant.getRace().equals(Race.Elf)){
                        this.avoided.add(participant);
                        this.avoided.add(possiblePartner);
                    }
                    if (participant.getRace().equals(Race.Dwarf)){
                        this.avoided.add(possiblePartner);
                        this.avoided.add(participant);
                    }
                }
                    
            }
        }
    }

    private List<Relationship> relationshipSort(Participant participant){
        boolean sorted = false;
        List<Relationship> relationship = participant.getRelationship();
        if (relationship.size() == 1)
            sorted = true;
        while (!sorted){
            for(int idx = 0; idx < relationship.size(); idx++) {  
                if (idx+1 < relationship.size()){
                    Swing fS = relationship.get(idx).getSwing();
                    Swing sS = relationship.get(idx+1).getSwing();
                    int fR = fS.getSwingType().equals(SwingType.R) ? fS.getSwingCount() : fS.getSwingCount() * -1;
                    int sR = sS.getSwingType().equals(SwingType.R) ? sS.getSwingCount() : sS.getSwingCount() * -1;
                    if (fR < sR){
                        Relationship fRel = relationship.get(idx);
                        Relationship sRel = relationship.get(idx+1);
                        relationship.remove(idx+1);
                        relationship.remove(idx);
                        relationship.add(idx, sRel);
                        relationship.add(idx+1, fRel);
                    }
                }
            }
            for(int idx = 0; idx < relationship.size(); idx++) {  
                if (idx+1 < relationship.size()){
                    Swing fS = relationship.get(idx).getSwing();
                    Swing sS = relationship.get(idx+1).getSwing();
                    int fR = fS.getSwingType().equals(SwingType.R) ? fS.getSwingCount() : fS.getSwingCount() * -1;
                    int sR = sS.getSwingType().equals(SwingType.R) ? sS.getSwingCount() : sS.getSwingCount() * -1;
                    if (fR < sR){
                        break;
                    }
                    if (idx == relationship.size()-2)
                        sorted = true;

                }
            }
        }
        return relationship;
    }

    private void fitRelationship(){
        for (Participant participant : this.participants) {
            for (Encounter encounter : this.encounters) {
                if (encounter.getElf().equals(participant)){
                    participant.addRelationship(new Relationship(encounter.getDwarf(), encounter.getElfinSwing()));
                }
                if (encounter.getDwarf().equals(participant)){
                    participant.addRelationship(new Relationship(encounter.getElf(), encounter.getDwarvenSwing()));
                }
            }
        }
        for (Participant participant : this.participants) {
            participant.setSumOfSwingCount(getSumOfSwingCount(participant));
        }
    }

    public List<Participant[]> getCouples(){
        return this.couples;
    }
}