import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class EDM {
    private static Scanner scanner;
    private int delay = 10;
    private final static int delay_pause = 5;
    private final static int[] blank_title = new int[] {3, 2};
    private final static int[] blank_heading = new int[] {2, 1}; 

    public EDM(){
        scanner = new Scanner(System.in);
        animatedOutputWithBlanks("--- Welcome to Elves & Dwarves Misfits ---", blank_title[0], blank_title[1]);
    }

    public void setup(){
        animatedOutput("Write the duration of the delay between chars (If You press enter default delay will be "+this.delay+") ", true);
        animatedOutput("Delay duration : ", false);
        String input = scanner.nextLine();
        try {
            if (!input.isEmpty())
                this.delay = Integer.parseInt(input);
        } catch (Exception e) {
            setup();
        }
    }

    public void start(){
        File[] files = new File("Test_Samples").listFiles();
        animatedOutput("\n\nWhich of the provided files would You like to check? Files 1 to "+files.length+" available (Write number)", true);
        animatedOutput("Test : ", false);
        String inputTestNumber = scanner.nextLine();
        int testNumber = 0;
        try {
            testNumber = Integer.parseInt(inputTestNumber);
            if (testNumber > files.length)
                start();
        } catch (Exception e) {
            start();
        }
        Test test = new Test(new String[]{"Test"+testNumber});
        outputParticipants(test.getParticipants());
        outputEncounters(test.getEncounters());
        outputResult(test.getSortingTree());
        animatedOutput("\n\nShould we continue ? (Type \"Y\") : ", false);
        String inputContinue = scanner.nextLine();
        if (inputContinue.toLowerCase().equals("y"))
            start();
        end();
    }


    public void end(){
        System.out.println("\n\n\n");
        scanner.close();
    }

    public void outputResult(SortingTree sortingTree){
        animatedOutputWithBlanks("- Output -", blank_heading[0], blank_heading[1]);
        for (Participant[] couple : sortingTree.getCouples()) {
            animatedOutput(couple[0].getName()+" : "+couple[1].getName(), true);
        }
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
            Swing elfinSwing = encounter.getElfinSwing();
            Participant dwarf = encounter.getDwarf();
            Swing dwarvenSwing = encounter.getDwarvenSwing();
            String output = elf.getName() + 
                            " : " + 
                            dwarf.getName() + 
                            " = " + 
                            elfinSwing.getSwingCount() + 
                            elfinSwing.getSwingType() + 
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
