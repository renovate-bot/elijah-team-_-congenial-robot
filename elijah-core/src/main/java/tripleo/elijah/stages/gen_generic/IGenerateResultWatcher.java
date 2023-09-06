package tripleo.elijah.stages.gen_generic;

public interface IGenerateResultWatcher {
	public void complete();

	public void item(GenerateResultItem item);
}