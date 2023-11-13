package Structures.PdfHelpers;

public class SaleReportTableItem {
    private String name;
    private int amount;
    private Double buyNetto;
    private Double buyBrutto;
    private Double sellNetto;
    private Double sellBrutto;
    private Double income;

    public SaleReportTableItem(String name, int amount, Double buyNetto, Double buyBrutto, Double sellNetto, Double sellBrutto, Double income) {
        this.name = name;
        this.amount = amount;
        this.buyNetto = buyNetto;
        this.buyBrutto = buyBrutto;
        this.sellNetto = sellNetto;
        this.sellBrutto = sellBrutto;
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public Double getBuyNetto() {
        return buyNetto;
    }

    public Double getBuyBrutto() {
        return buyBrutto;
    }

    public Double getSellNetto() {
        return sellNetto;
    }

    public Double getSellBrutto() {
        return sellBrutto;
    }

    public Double getIncome() {
        return income;
    }

    @Override
    public String toString(){
        return this. getName();
    }
}
