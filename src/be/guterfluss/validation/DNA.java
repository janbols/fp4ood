package be.guterfluss.validation;

import java.util.Random;

public class DNA {
    public DNA(String unvalidated) {

    }

    public static boolean valid(String unvalidated) {
        return new Random().nextBoolean();
    }
}
