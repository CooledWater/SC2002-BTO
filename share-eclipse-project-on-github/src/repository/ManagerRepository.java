package repository;

import java.util.List;
import user.Manager;

public class ManagerRepository implements Repository {
	private List<Manager> managers;
	
	public ManagerRepository() {
		this.importFromFile("filePath");
	}
	public void saveToFile(String filePath) {
		;
	};
	public void importFromFile(String filePath) {
		;
	};
}
