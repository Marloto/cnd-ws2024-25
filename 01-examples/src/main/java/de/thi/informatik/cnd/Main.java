package de.thi.informatik.cnd;

import java.util.*;

class Tier {
    protected String name;

    public Tier(String name) {
        this.name = name;
    }

    public Tier vermehren() {
        return new Tier(this.name + " Junior");
    }

    @Override
    public String toString() {
        return "Tier: " + name;
    }
}

class Hund extends Tier {
    public Hund(String name) {
        super(name);
    }

    @Override
    public Hund vermehren() {  // Kovarianter Rückgabetyp
        return new Hund(this.name + " Junior");
    }

    @Override
    public String toString() {
        return "Hund: " + name;
    }
}

public class Main {
    public static void main(String[] args) {
        Tier allgemein = new Tier("Tier");
        Hund bello = new Hund("Bello");

        Tier tierNachwuchs = allgemein.vermehren();
        Hund hundNachwuchs = bello.vermehren();  // Beachten Sie den Rückgabetyp

        System.out.println(tierNachwuchs);  // Ausgabe: Tier: Tier Junior
        System.out.println(hundNachwuchs);  // Ausgabe: Hund: Bello Junior

        // Polymorphismus
        Tier polymorphTier = new Hund("Rex");
        Tier polymorphNachwuchs = polymorphTier.vermehren();
        System.out.println(polymorphNachwuchs);  // Ausgabe: Hund: Rex Junior
    }
}
