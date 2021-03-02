import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class HorseTrackProblem {
    public static void main(String[] args){
        HorseTrackProblem horseTrackProblem = new HorseTrackProblem();
        Payout payout = new Payout();
        Map<Integer, HorseBo> horseMap = horseTrackProblem.initHorseMap();
        List<BillBo> billList = horseTrackProblem.initBillList();
        Scanner scanner = new Scanner(System.in);
        Boolean run = Boolean.TRUE;
        Integer lastWinner = 1;
        try {
            while (run){
                horseTrackProblem.printInventory(billList);
                horseTrackProblem.printHorse(horseMap);
                String input = scanner.nextLine();
                if (input.isEmpty()) continue;
                String errMsg = horseTrackProblem.checkValidInput(input, horseMap);
                if (errMsg != null) {
                    System.out.println(errMsg);
                    continue;
                }
                String perm1 = input.toUpperCase().split(" ")[0]; //Q, R, W or horse number
                Integer perm2 = input.split(" ").length > 1 ? Integer.parseInt(input.split(" ")[1]) : null; //horse number or bet
                switch(perm1){
                    case "Q": 
                        run = Boolean.FALSE;
                        break;
                    case "R":
                        billList = horseTrackProblem.initBillList();
                        break;
                    case "W": 
                        horseTrackProblem.changeWinner(horseMap, lastWinner, perm2);
                        lastWinner = perm2;
                        break;
                    default: //place the bet
                        if (lastWinner.equals(Integer.parseInt(perm1))){
                            //payout the bet
                            payout.doPayout(perm2, horseMap.get(lastWinner), billList);
                        } else {
                            Integer horse = Integer.parseInt(perm1);
                            System.out.println("No Payout: " + horseMap.get(horse).getName());
                        }
                        break;
                }
            }

        } catch (Exception e){
            System.out.println("unexpected error occur in reading stream" + e);
        }
    }

    private Map<Integer, HorseBo> initHorseMap(){
        Map<Integer, HorseBo> horseMap = new HashMap();
        horseMap.put(1, new HorseBo(1, "That Darn Gray Cat", 5, Boolean.TRUE));
        horseMap.put(2, new HorseBo(2, "Fort Utopia", 10, Boolean.FALSE));
        horseMap.put(3, new HorseBo(3, "Count Sheep", 9, Boolean.FALSE));
        horseMap.put(4, new HorseBo(4, "Ms Traitour", 4, Boolean.FALSE));
        horseMap.put(5, new HorseBo(5, "Real Princess", 3, Boolean.FALSE));
        horseMap.put(6, new HorseBo(6, "Pa Kettle", 5, Boolean.FALSE));
        horseMap.put(7, new HorseBo(7, "Gin Stinger", 6, Boolean.FALSE));
        return horseMap;
    }

    private List<BillBo> initBillList(){
        List<BillBo> billList = new ArrayList<BillBo>();
        billList.add(new BillBo(100));
        billList.add(new BillBo(20));
        billList.add(new BillBo(10));
        billList.add(new BillBo(5));
        billList.add(new BillBo(1));
        return billList;
    }

    private void printInventory(List<BillBo> bills){
        System.out.println("Inventory:");
        bills.stream()
            .sorted(Comparator.comparing(BillBo::getFaceValue))
            .map(BillBo::toString)
            .forEach(System.out::println);
    }

    private void printHorse(Map<Integer, HorseBo> horseMap){
        System.out.println("Horses:");
        horseMap.values().stream().map(HorseBo::toString).forEach(System.out::println);
    }

    /**
     * return error message if error occur
     * return null if input valid
     */
    private String checkValidInput(String input, Map<Integer, HorseBo> horseMap){
        final String errInvalidCommand = "Invalid Command: ";
        final String errInvalidHorse = "Invalid Horse Number: ";
        final String errInvalidBet = "Invalid Bet: ";
        String winRegex = "\\bW \\d+\\b";
        Pattern winPattern = Pattern.compile(winRegex);
        String billRegex = "\\b\\d+ ";
        Pattern billPattern = Pattern.compile(billRegex);
        if (input == null) return errInvalidCommand + "null";
        if (input.toUpperCase().equals("R") || input.toUpperCase().equals("Q")) return null;
        Matcher winMatcher = winPattern.matcher(input.toUpperCase());
        if (winMatcher.matches()){
            String [] inputArr = winMatcher.group(0).split(" ");
            Integer horseNumber = Integer.parseInt(inputArr[1]);
            if (!horseMap.containsKey(horseNumber)) return errInvalidHorse + inputArr[1];
            else return null;
        }

        Matcher billMatcher = billPattern.matcher(input);
        if (billMatcher.find()){
            String [] inputArr = input.split(" ");
            if (inputArr.length == 2){
                Integer horseNumber = Integer.parseInt(inputArr[0]);
                if (!horseMap.containsKey(horseNumber)) return errInvalidHorse + inputArr[0];
                try {
                    Integer.parseInt(inputArr[1]);
                } catch (NumberFormatException e) {
                    try {
                        return errInvalidBet + Double.parseDouble(inputArr[1]);
                    } catch (NumberFormatException e1){
                        return errInvalidCommand + input;
                    }
                }
                return null;
            }
        }
        //all possible input exhausted
        return errInvalidCommand + input;
    }

    private void changeWinner(Map<Integer, HorseBo> horseMap, Integer lastWinner, Integer curWinner){
        horseMap.get(lastWinner).setIsWin(Boolean.FALSE);
        horseMap.get(curWinner).setIsWin(Boolean.TRUE);
    }
}