package zrace.client.app.world.cars.objs.pixels;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

public class Pixel {
	
	public static Box getPixelBox(Color color, double w, double h, double d) {
		return getPixelBox(color, w, h, d, new Translate(0, 0, 0));
	}
	
	public static Box getPixelBox(Color color, double w, double h, double d, Translate tr) {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(color);
		
		Box box = new Box(w, h, d);
		box.setMaterial(material);
		
		box.setTranslateX(tr.getX());
		box.setTranslateY(tr.getY());
		box.setTranslateZ(tr.getZ());
		
		return box;
	}
}
