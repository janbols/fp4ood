package be.guterfluss.calculate;

public interface ProjectRepository {
    Project findBy(ProjectId projectId);
}
