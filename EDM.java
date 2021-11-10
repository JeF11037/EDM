import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class EDM {
    private static Scanner scanner;
    private final static int delay = 1;
    private final static int delay_pause = 1;
    private final static int[] blank_title = new int[] {3, 2};
    private final static int[] blank_heading = new int[] {2, 1}; 

    private static Participant[] testParticipants = new Participant[]{
        new Participant("Ave", Race.Elf),
        new Participant("Elegast", Race.Elf),
        new Participant("Taeriel", Race.Elf),
        new Participant("Saeya", Race.Elf),
        new Participant("Loralf", Race.Elf),
        new Participant("Duthila", Race.Dwarf),
        new Participant("Mondull", Race.Dwarf),
        new Participant("Boltor", Race.Dwarf),
        new Participant("Drummer", Race.Dwarf),
    };

    private static Encounter[] testEncounters = new Encounter[]{
        new Encounter(testParticipants[0], new Swing(1, SwingType.R), testParticipants[5], new Swing(4, SwingType.R)),
        new Encounter(testParticipants[1], new Swing(1, SwingType.R), testParticipants[5], new Swing(1, SwingType.R)),
        new Encounter(testParticipants[2], new Swing(1, SwingType.L), testParticipants[5], new Swing(3, SwingType.L)),
        new Encounter(testParticipants[3], new Swing(2, SwingType.R), testParticipants[5], new Swing(1, SwingType.L)),
        new Encounter(testParticipants[4], new Swing(1, SwingType.R), testParticipants[5], new Swing(2, SwingType.R)),
        new Encounter(testParticipants[2], new Swing(1, SwingType.R), testParticipants[1], new Swing(3, SwingType.R)),
        new Encounter(testParticipants[1], new Swing(1, SwingType.R), testParticipants[1], new Swing(5, SwingType.R)),
        new Encounter(testParticipants[2], new Swing(1, SwingType.R), testParticipants[2], new Swing(1, SwingType.R)),
        new Encounter(testParticipants[4], new Swing(1, SwingType.R), testParticipants[2], new Swing(1, SwingType.R)),
        new Encounter(testParticipants[4], new Swing(1, SwingType.R), testParticipants[3], new Swing(1, SwingType.R)),
        new Encounter(testParticipants[4], new Swing(2, SwingType.R), testParticipants[1], new Swing(2, SwingType.R)),
        new Encounter(testParticipants[1], new Swing(1, SwingType.R), testParticipants[2], new Swing(10, SwingType.R)),
        new Encounter(testParticipants[3], new Swing(1, SwingType.L), testParticipants[1], new Swing(1, SwingType.L)),
        new Encounter(testParticipants[3], new Swing(1, SwingType.R), testParticipants[2], new Swing(1, SwingType.L)),
    };

    public EDM(){
        scanner = new Scanner(System.in);
        animatedOutputWithBlanks("--- Welcome to Elves & Dwarves Misfits ---", blank_title[0], blank_title[1]);
    }

    public int setup(){
        animatedOutput("Choose between automated control (1) or manual control (2) ", true);
        animatedOutput("Control type : ", false);
        String input = scanner.nextLine();
        if (input.equals("1") || input.equals("2"))
            return Integer.parseInt(input);
        return 0;
    }

    public void start(int contolType){
        if (contolType == 1){
            outputParticipants(Arrays.asList(testParticipants));
            outputEncounters(Arrays.asList(testEncounters));
        } else if (contolType == 2) {

        } else {
            animatedOutputWithBlanks("Something went wrong ! Please start from the beginning. ", blank_heading[0], blank_heading[1]);
            start(setup());
        }
    }


    public void end(){
        scanner.close();
    }

    public void outputParticipants(List<Participant> participants){
        animatedOutputWithBlanks("- Participants -", blank_heading[0], blank_heading[1]);
            String elves = "";
            String dwarves = "";
            for (Participant participant : participants) {
                if (participant.getRace().equals(Race.Elf)) {
                    elves += (!elves.isEmpty()) ? ", " + participant.getName() : participant.getName();
                }
                if (participant.getRace().equals(Race.Dwarf)){
                    dwarves += (!dwarves.isEmpty()) ? ", " + participant.getName() : participant.getName();
                }
            }
            animatedOutput("Elves: "+elves, true);
            animatedOutput("Dwarves: "+dwarves, true);
    }

    public void outputEncounters(List<Encounter> encounters){
        animatedOutputWithBlanks("- Encounters -", blank_heading[0], blank_heading[1]);
            for (Encounter encounter : encounters) {
                Participant elf = encounter.getElf();
                Swing elvenSwing = encounter.getElfinSwing();
                Participant dwarf = encounter.getDwarf();
                Swing dwarvenSwing = encounter.getDwarvenSwing();
                String output = elf.getName() + 
                                " : " + 
                                dwarf.getName() + 
                                " = " + 
                                elvenSwing.getSwingCount() + 
                                elvenSwing.getSwingType() + 
                                " x " + 
                                dwarvenSwing.getSwingCount() + 
                                dwarvenSwing.getSwingType();
                animatedOutput(output, true);
            }
    }

    public void animatedOutput(String output, boolean end){
        char[] outputChars = output.toCharArray();
        for (char c : outputChars) {
            System.out.print(c);
            try {
                if (c == ',' || c == '.'){
                    Thread.sleep(delay_pause);
                } else {
                    Thread.sleep(delay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (end)
            System.out.println();
    }

    public void animatedOutputWithBlanks(String output, int beforeBlankCount, int afterBlankCount){
        for (int i = 0; i < beforeBlankCount; i++) {
            System.out.println();
        }
        char[] outputChars = output.toCharArray();
        for (char c : outputChars) {
            System.out.print(c);
            try {
                if (c == ',' || c == '.'){
                    Thread.sleep(delay_pause);
                } else {
                    Thread.sleep(delay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < afterBlankCount; i++) {
            System.out.println();
        }
    }
}
