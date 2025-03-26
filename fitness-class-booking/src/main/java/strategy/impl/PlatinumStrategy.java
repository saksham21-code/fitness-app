package main.java.strategy.impl;

import main.java.strategy.PackageStrategy;

public class PlatinumStrategy implements PackageStrategy {
    public int getMaxBookingLimit() {
        return 10;
    }
}