public class BillBo {
   private Integer faceValue;
   private Integer stock;

    public BillBo(Integer faceValue, Integer stock) {
        this.faceValue = faceValue;
        this.stock = stock;
    }

    public BillBo(Integer faceValue) {
        this.faceValue = faceValue;
        this.stock = 10;
    }

    public Integer getFaceValue() {
        return this.faceValue;
    }

    public void setFaceValue(Integer faceValue) {
        this.faceValue = faceValue;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public String toString(){
        return "$" + this.faceValue + "," + this.stock;
    }
}
