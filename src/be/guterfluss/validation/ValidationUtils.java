package be.guterfluss.validation;

import fj.data.Validation;

import java.util.function.Function;

public class ValidationUtils {

    static Validation<String, DNA> dnaValidation(String unvalidated) {
        if (DNA.valid(unvalidated)) {
            return Validation.success(new DNA(unvalidated));
        } else {
            return Validation.fail(
                    "DNA is not valid for field ???");
        }
    }

    static Validation<UnresolvedError, DNA> dnaValidation(
            String unvalidated) {
        if (DNA.valid(unvalidated)) {
            return Validation.success(new DNA(unvalidated));
        } else {
            return Validation.fail(
                    (fieldName) -> "DNA is not valid for field " + fieldName);
        }
    }

    static Function<String, Validation<String, DNA>> dnaValidationRule =
            (unvalidated) -> {
                if (DNA.valid(unvalidated)) {
                    return Validation.success(new DNA(unvalidated));
                } else {
                    return Validation.fail(
                            "DNA is not valid for field ???");
                }
            };

}
