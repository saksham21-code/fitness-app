package main.java.strategy.impl;

import main.java.strategy.PackageStrategy;

public class GoldStrategy implements PackageStrategy {
    public int getMaxBookingLimit() {
        return 5;
    }
}