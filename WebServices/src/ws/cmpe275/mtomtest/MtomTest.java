package ws.cmpe275.mtomtest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MtomTest {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ws.cmpe275.mtomtest");
		//$JUnit-BEGIN$
		suite.addTestSuite(MtomDataTest.class);
		//$JUnit-END$
		return suite;
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}
