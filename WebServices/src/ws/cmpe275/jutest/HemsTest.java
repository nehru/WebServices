package ws.cmpe275.jutest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class HemsTest {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ws.cmpe275.jutest");
		//$JUnit-BEGIN$
		suite.addTestSuite(MapDataTest.class);
		//$JUnit-END$
		return suite;
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}
