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
				.equals("N0 O3 I0 O5 N1 S4 O2 S4 S1 S4 N2 O1 S4 N3 S2 I1 O5 N4 S4 O2 N5 O3 I0 O3 I1 O3 C2 O5 N6 O3 I0 O3 F13 S1 I0 S2 S4 O1 S4 N7 O3 C1 O3 F5 S1 N8 S2 O1 S4 N9 "));
	}
}
//"2 X1 2 ^ * 3 2 + X2 2 ^ * - -333.1415 X1 * X2 * e 2 ^ * X1 * sin X1 1F * + 0.17E-27 PI * lg 10 1F * + 12E13 +"
//"N0 I0 N1 O5 O3 N2"
