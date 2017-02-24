package zrace.client.models;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.Node;
import javafx.scene.shape.Shape3D;
import main.runner.RunParameters;

public class ModelLoader {

	
	public static void main(String[] args) {
		ModelImporter tdsImporter = new TdsModelImporter();
		
		tdsImporter.read(RunParameters.MODEL_PLANE);
		Node[] tdsMesh = (Node[]) tdsImporter.getImport();
		tdsImporter.close();
		
		
	}
}
