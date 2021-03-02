public class HorseBo {
    private Integer id;
    private String name;
    private Integer odds;
    private Boolean isWin;

    public HorseBo(Integer id, String name, Integer odds, Boolean isWin){
        this.id = id;
        this.name = name;
        this.odds = odds;
        this.isWin = isWin;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOdds() {
        return this.odds;
    }

    public void setOdds(Integer odds) {
        this.odds = odds;
    }

    public Boolean isIsWin() {
        return this.isWin;
    }

    public Boolean getIsWin() {
        return this.isWin;
    }

    public void setIsWin(Boolean isWin) {
        this.isWin = isWin;
    }
    
    public String toString(){
        return this.id + "," + this.name + "," + this.odds + "," + (this.isWin ? "won" : "lost");
    }
}