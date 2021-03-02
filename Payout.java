import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class Payout {
    public void doPayout(int amount, HorseBo horseBo, List<BillBo> bills){
        List<BillBo> dispenses = initDispenses();
        int totalPayout = amount * horseBo.getOdds();
        System.out.println("Payout: " + horseBo.getName() + ",$" + totalPayout);
        dispenses = calulateDispensing(totalPayout, bills, dispenses);
        if (dispenses != null) {
            System.out.println("Dispensing:");
            dispenses.stream().sorted(Comparator.comparing(BillBo::getFaceValue)).forEach(System.out::println);
            for (int i = 0; i < bills.size(); i++){
                bills.get(i).setStock(bills.get(i).getStock() - dispenses.get(i).getStock());
            }
        } else System.out.println("Insufficient Funds: " + totalPayout);
    }

    private List<BillBo> initDispenses(){
        List<BillBo> dispenses = new ArrayList<BillBo>();
        dispenses.add(new BillBo(100, 0));
        dispenses.add(new BillBo(20, 0));
        dispenses.add(new BillBo(10, 0));
        dispenses.add(new BillBo(5, 0));
        dispenses.add(new BillBo(1, 0));
        return dispenses;
    }

    private List<BillBo> calulateDispensing(int totalPayout, List<BillBo> bills, List<BillBo> dispenses){
        if (totalPayout == 0) return dispenses;
        for (int i = 0; i < bills.size(); i++){
            if (bills.get(i).getFaceValue() <= totalPayout
                && bills.get(i).getStock() - dispenses.get(i).getStock() > 0){
                    dispenses.get(i).setStock(dispenses.get(i).getStock() + 1);
                return calulateDispensing(totalPayout - bills.get(i).getFaceValue(), bills, dispenses);
            }
        }
        return null;//exhaust the bill list
    }
}
