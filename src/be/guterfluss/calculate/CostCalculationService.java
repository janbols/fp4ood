package be.guterfluss.calculate;

import java.util.function.Function;

public class CostCalculationService {
    ProjectRepository projectRepository;

    LabourCalculator labourCalculator;
    OpenInvoicesCalculator openInvoicesCalculator;
    NetMarginCalculator netMarginCalculator;
    GrossMarginCalculator grossMarginCalculator;

    Cost calculateTotalCost(ProjectId projectId) {
        Project project = projectRepository.findBy(projectId);

        return labourCalculator.calculate(project)
                .plus(openInvoicesCalculator.calculate(project))
                .plus(netMarginCalculator.calculate(project))
                .plus(grossMarginCalculator.calculate(project));
    }



    Function<Project, Cost> labour = p -> new Cost(p.labourActuals / (p.labourActuals + p.labourPrognosis));
    Function<Project, Cost> openInvoices = p -> new Cost(p.invoicesAmount - p.invoicesPaidAmount);
    Function<Project, Cost> netMargin = p -> null;
    Function<Project, Cost> grossMargin = p -> null;

    Cost calculatesTotalCost(ProjectId projectId) {
        Project project = projectRepository.findBy(projectId);

        return labour.apply(project)
                .plus(openInvoices.apply(project))
                .plus(netMargin.apply(project))
                .plus(grossMargin.apply(project));
    }
}
