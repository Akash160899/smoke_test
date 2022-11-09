package runner;

import org.testng.TestNG;

public class TestRunner {

	public static void main(String[] args) {
		TestNG test = new TestNG();

		test.setTestClasses(new Class[] { runner.Runer.class });
		test.run();

	}

}
