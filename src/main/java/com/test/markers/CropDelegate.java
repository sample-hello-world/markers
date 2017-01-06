package com.test.markers;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CropDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.print("Called the Crop delgate method");
		String temp = (String) execution.getVariable("temperature");
	    String markerName = (String) execution.getVariable("markerName");

		System.out.println ( "temperture: " + temp + " - " + "Marker Name: " + markerName);
		


	}

}
