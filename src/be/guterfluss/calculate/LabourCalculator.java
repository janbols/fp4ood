package be.guterfluss.calculate;

public class LabourCalculator implements CostCalculator {
    @Override
    public Cost calculate(Project p) {
        return new Cost(
                p.labourActuals /
                        (p.labourActuals + p.labourPrognosis)
        );
    }
}
