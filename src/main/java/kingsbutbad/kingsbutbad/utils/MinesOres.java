package kingsbutbad.kingsbutbad.utils;

public enum MinesOres {
    COAL_ORE(50.0),
    IRON_ORE(100.0),
    GOLD_ORE(250.0);
    private double moneyOnMine;
    MinesOres(double moneyOnMine){
        this.moneyOnMine = moneyOnMine;
    }

    public double getMoneyOnMine() {
        return moneyOnMine;
    }
}
