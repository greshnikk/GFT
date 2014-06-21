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
				.equals("N0 O3 I0 O5 N1 O2 S1 N2 O1 N3 S2 O3 I1 O5 N4 O2 N5 O3 I0 O3 I1 O3 C2 O5 N6 O3 I0 O3 F13 S1 I0 S2 O1 N7 O3 C1 O3 F5 S1 N8 S2 O1 N9 "));
	}
}