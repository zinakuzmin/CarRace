package zrace.client.models;

import javafx.scene.Node;
import main.runner.RunParameters;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

/**
 * The Class ModelLoader - test method.
 */
public class ModelLoader {

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ModelImporter tdsImporter = new TdsModelImporter();
		
		tdsImporter.read(RunParameters.MODEL_PLANE);
		Node[] tdsMesh = (Node[]) tdsImporter.getImport();
		tdsImporter.close();
		
		
	}
}
