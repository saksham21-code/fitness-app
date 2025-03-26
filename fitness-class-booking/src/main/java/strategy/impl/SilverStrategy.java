package main.java.strategy.impl;

import main.java.strategy.PackageStrategy;

public class SilverStrategy implements PackageStrategy {
    public int getMaxBookingLimit() {
        return 3;
    }
}
