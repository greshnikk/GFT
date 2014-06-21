package translator;

import static org.junit.Assert.*;

import org.junit.Test;

public class SemanticAnalysisTest {

	@Test
	public void test() throws Exception {
		//SemanticAnalysis tester = new SemanticAnalysis();
		assertTrue(SemanticAnalysis
				.convert(
						"N0 O3 I0 O5 N1 O2 S1 N2 O1 N3 S2 O3 I1 O5 N4 O2 N5 O3 I0 O3 I1 O3 C2 O5 N6 O3 I0 O3 F13 S1 I0 S2 O1 N7 O3 C1 O3 F5 S1 N8 S2 O1 N9")
				.equals("N0 I0 N1 O5 O3 N2 N3 O1 I1 N4 O5 O3 O2 N5 I0 O3 I1 O3 C2 N6 O5 O3 I0 O3 F13 I0 Fn2 O3 O2 N7 C1 O3 F5 N8 Fn2 O3 O1 N9 O1"));
	}
}
//"2 X1 2 ^ * 3 2 + X2 2 ^ * - -333.1415 X1 * X2 * e 2 ^ * X1 * sin X1 1F * + 0.17E-27 PI * lg 10 1F * + 12E13 +"
//"N0 I0 N1 O5 O3 N2 N3 O1 I1 N4 O5 O3 O2 N5 I0 O3 I1 O3 C2 N6 O5 O3 I0 O3 F13 I0 !1F! O3 O1 N7 C1 O3 F5 N8 !1F! O3 O1 N9 O1"
