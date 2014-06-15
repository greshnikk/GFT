package translator;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexicalAnalysisTest {

	@Test
	public void testConvert() throws Exception {
		LexicalAnalysis tester = new LexicalAnalysis();
		assertTrue(tester
				.convert(
						"2X1^2 - ( 3+ 2)X2^2 -333.1415X1X2e^2X1sin(X1) + 0.17E-27*PI*lg(10)+ 12E13")
				.equals("N0 O2 I0 O4 N1 S3 O1 S3 S0 S3 N2 O0 S3 N3 S1 I1 O4 N4 S3 O1 N5 O2 I0 O2 I1 O2 C1 O4 N6 O2 I0 O2 F12 S0 I0 S1 S3 O0 S3 N7 O2 C0 O2 F4 S0 N8 S1 O0 S3 N9 "));
	}

}
