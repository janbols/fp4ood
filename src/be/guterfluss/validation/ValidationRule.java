package be.guterfluss.validation;

import fj.data.Validation;

public interface ValidationRule<U,V> {
    Validation<String, V> validate(U unvalidated);
}
