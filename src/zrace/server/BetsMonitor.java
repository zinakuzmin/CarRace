package zrace.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import dbModels.Race;
import javafx.scene.media.Media;

public class BetsMonitor implements Runnable{
	private ServerController controller;
	private boolean shouldRun = true;
	
	public BetsMonitor(ServerController controller) {
		this.setController(controller);
		
	}

	@Override
	public void run() {
		ArrayList<Race> activeRaces = controller.getActiveRaces();
		
		while(shouldRun){
//			 for (Race race : activeRaces) {
//				if (race.ge)
//			}
//			
			
			try {
				
				
				
				
				
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

	public ServerController getController() {
		return controller;
	}

	public void setController(ServerController controller) {
		this.controller = controller;
	}

	public boolean isShouldStopRun() {
		return shouldRun;
	}

	public void setShouldStopRun(boolean shouldStopRun) {
		this.shouldRun = shouldStopRun;
	}

}
