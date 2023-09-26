import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    private Scanner scanner;
    private final String testSamplesPath = "Test_Samples";
    private final String testPath = "test";
    private final String outputPath = "output";
    private final String allowableCharsRegex = "[:=,]*";
    private final String regexForName = "[a-zA-Z]*";
    private final String regex = "[a-zA-Z0-9]*";
    private final String lineLook = "Line should look like \"elf : dwarf = #elfinSwing SwingType x #dwarvenSwing SwingType\"";
    private List<Participant> participants;
    private List<Encounter> encounters;
    private SortingTree sortingTree;
    private boolean passed = false;

    public Test(String... tests){
        for (String test : tests) {
            if (testExists(test)){
                try {
                    File file = new File(this.testSamplesPath+"/"+test+"/"+this.testPath);
                    this.scanner = new Scanner(file);
                    this.participants = fitParticipants(test);
                    this.encounters = fitEncounters(test);
                    this.scanner.close();
                    this.sortingTree = new SortingTree(this.participants, this.encounters);
                    this.passed = compareWithOutput(test, sortingTree.getCouples()) ? true : false;
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }

    public List<Participant> getParticipants(){
        return this.participants;
    }

    public List<Encounter> getEncounters(){
        return this.encounters;
    }

    public SortingTree getSortingTree(){
        return this.sortingTree;
    }

    private boolean compareWithOutput(String test, List<Participant[]> couples){
        File file = new File(this.testSamplesPath+"/"+test+"/"+this.outputPath);
        try {
            this.scanner = new Scanner(file);
            int idx = 0;
            while (this.scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] data = line.split(" ");
                if (!(couples.size() > idx)){
                    if (!couples.get(idx)[0].getName().equals(data[0]) && !couples.get(idx)[1].getName().equals(data[2])){
                        throw new java.lang.Error("Couples do not match. ");
                    }
                    throw new java.lang.Error("Participants amount is not correct.");
                }
            }
            return true;
        } catch (Exception e) {
            throw new java.lang.Error(e.getMessage());
        }
    }

    private List<Encounter> fitEncounters(String path){
        List<Encounter> encounters = new ArrayList<Encounter>();
        int lineCount = 3;
        while (this.scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] data = line.split(" ");
            if (data.length != 7){
                throw new java.lang.Error("Wrong amount of data. "+this.lineLook);
            }
            Participant elf = null;
            Participant dwarf = null;
            Swing elfinSwing = null;
            Swing dwarvenSwing = null;
            for (int idx = 0; idx < data.length; idx++){
                switch (idx){
                    case 0:
                        if (!nameDuplicateExists(participants, data[idx]))
                            throw new java.lang.Error("Unknown elf participant at the line "+lineCount+". "+this.lineLook);
                        break;
                    case 1:
                        if (!data[idx].equals(":"))
                            throw new java.lang.Error("Missed \":\" at the line "+lineCount+". "+this.lineLook);
                        break;
                    case 2:
                        if (!nameDuplicateExists(participants, data[idx]))
                            throw new java.lang.Error("Unknown dwarf participant at the line "+lineCount+". "+this.lineLook);
                        break;
                    case 3:
                        if (!data[idx].equals("="))
                            throw new java.lang.Error("Missed \"=\" at the line "+lineCount+". "+this.lineLook);
                        break;
                    case 4:
                        if (!data[idx].matches(regex))
                            throw new java.lang.Error("ElfinSwing does not meet expectations at the line "+lineCount+". "+this.lineLook);
                        break;
                    case 5:
                        if (!data[idx].toLowerCase().equals("x"))
                            throw new java.lang.Error("Missed \"x\". "+this.lineLook);
                        break;
                    case 6:
                        if (!data[idx].matches(regex))
                            throw new java.lang.Error("DwarvenSwing does not meet expectations at the line "+lineCount+". "+this.lineLook);
                        break;        
                }
                elf = matchParticipant(new Participant(data[0], Race.Elf));
                dwarf = matchParticipant(new Participant(data[2], Race.Dwarf));
                if (elf.equals(null)){
                    throw new java.lang.Error("Unknown elf participant at the line "+lineCount+". ");
                }
                if (dwarf.equals(null)){
                    throw new java.lang.Error("Unknown dwarf participant at the line "+lineCount+". ");
                }
                elfinSwing = null;
                dwarvenSwing = null;
                if (data[4].toLowerCase().charAt(data[4].length()-1) == 'r' || data[4].toLowerCase().charAt(data[4].length()-1) == 'l'){
                    String count = data[4].substring(0, data[4].length() - 1);
                    try {
                        if (data[4].toLowerCase().charAt(data[4].length()-1) == 'r'){
                            elfinSwing = new Swing(Integer.parseInt(count), SwingType.R);
                        } else {
                            elfinSwing = new Swing(Integer.parseInt(count), SwingType.L);
                        }
                    } catch (Exception e) {
                        throw new java.lang.Error(e.getMessage()+" at the line "+lineCount);
                    }
                } else{
                    throw new java.lang.Error("ElfinSwing does not meet expectations at the line "+lineCount+". "+this.lineLook);
                }
                if (data[6].toLowerCase().charAt(data[6].length()-1) == 'r' || data[6].toLowerCase().charAt(data[6].length()-1) == 'l'){
                    String count = data[6].substring(0, data[6].length() - 1);
                    try {
                        if (data[6].toLowerCase().charAt(data[6].length()-1) == 'r'){
                            dwarvenSwing = new Swing(Integer.parseInt(count), SwingType.R);
                        } else {
                            dwarvenSwing = new Swing(Integer.parseInt(count), SwingType.L);
                        }
                    } catch (Exception e) {
                        throw new java.lang.Error(e.getMessage()+" at the line "+lineCount);
                    }
                } else{
                    throw new java.lang.Error("DwarvenSwing does not meet expectations at the line "+lineCount+". "+this.lineLook);
                }
                if (elfinSwing.equals(null)){
                    throw new java.lang.Error("ElfinSwing does not meet expectations at the line "+lineCount+". "+this.lineLook);
                }
                if (dwarvenSwing.equals(null)){
                    throw new java.lang.Error("DwarvenSwing does not meet expectations at the line "+lineCount+". "+this.lineLook);
                }
            }
            if (checkEncounterRepeats(elf, elfinSwing, dwarf, dwarvenSwing, encounters)){
                throw new java.lang.Error("Encounter repeats at the line "+lineCount+". ");
            }
            encounters.add(new Encounter(elf, elfinSwing, dwarf, dwarvenSwing));
            lineCount++;
        }
        return encounters;
    }

    private boolean checkEncounterRepeats(Participant elf, Swing elfinSwing, Participant dwarf, Swing dwarvenSwing, List<Encounter> encounters){
        for (Encounter encounter : encounters) {
            if (encounter.getElf().getName().equals(elf.getName()) && 
                encounter.getElfinSwing().getSwingCount() == elfinSwing.getSwingCount() &&  encounter.getElfinSwing().getSwingType() == elfinSwing.getSwingType() &&
                encounter.getDwarf().getName().equals(dwarf.getName()) && 
                encounter.getDwarvenSwing().getSwingCount() == dwarvenSwing.getSwingCount() &&  encounter.getDwarvenSwing().getSwingType() == dwarvenSwing.getSwingType())
                return true;
        }
        return false;
    }

    private List<Participant> fitParticipants(String path){
        List<Participant> participants = new ArrayList<Participant>();
        int elvesCount = 0;
        int dwarvesCount = 0;
        for (int lineCount = 1; lineCount < 3; lineCount++){
            String line = scanner.nextLine();
            String[] data = line.split(" ");
            for (int idx = 0; idx < data.length; idx++){
                String name = data[idx].replaceAll(this.allowableCharsRegex, "");
                if (idx == 0){
                    String race = lineCount == 1 ? "Elves:" : "Dwarves:";
                    if (!data[idx].toLowerCase().equals(race.toLowerCase())){
                        throw new java.lang.Error("First line should start like \""+race+" \"");
                    }
                } else {
                    data[idx] = name;
                    if (!name.matches(this.regexForName)){
                        throw new java.lang.Error("Special characters, numbers or not Latin characters are not allowed !");
                    }
                    if (lineCount == 1){
                        participants.add(new Participant(name, Race.Elf));
                        elvesCount++;
                    }
                    if (lineCount == 2){
                        participants.add(new Participant(name, Race.Dwarf));
                        dwarvesCount++;
                    }
                }
            }
            if (lineCount == 1 || lineCount == 2){
                if (dublicateExists(data)){
                    throw new java.lang.Error("Names must be unique !");
                }
            }
        }
        if (!(dwarvesCount <= elvesCount && elvesCount <= 500)){
            throw new java.lang.Error("Participants does not meet expectations. DwarvesCount <= ElvesCount <= 500");
        }
        return participants;
    }

    private Participant matchParticipant(Participant who){
        for (Participant participant : this.participants) {
            if (participant.getName().equals(who.getName()) && participant.getRace().equals(who.getRace())){
                return participant;
            }
        }
        return null;
    }

    private boolean nameDuplicateExists(List<Participant> participants, String name){
        for (Participant participant : participants) {
            if (participant.getName().equals(name))
                return true;
        }
        return false;
    }

    private boolean dublicateExists(String[] array){
        for(int i = 0; i < array.length; i++) {  
            for(int j = i + 1; j < array.length; j++) {  
                if(array[i].toLowerCase().equals(array[j].toLowerCase()))  
                    return true;  
            }  
        }
        return false;
    }

    private boolean testExists(String folderName){
        Path folderPath = Paths.get(this.testSamplesPath, folderName);
        Path testPath = Paths.get(folderPath.toString(), this.testPath);
        Path outputPath = Paths.get(folderPath.toString(), this.outputPath);
        return  Files.exists(folderPath, LinkOption.NOFOLLOW_LINKS) && 
                Files.exists(testPath, LinkOption.NOFOLLOW_LINKS) && 
                Files.exists(outputPath, LinkOption.NOFOLLOW_LINKS) ? true : false;
    }

    public boolean status(){
        return this.passed;
    }
}
