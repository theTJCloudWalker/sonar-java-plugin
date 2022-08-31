package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;


public class FloatUsageCheckTest {

  void verify(){
    CheckVerifier.newVerifier()
      .onFile("src/test/files/FloatUsageCheck.java")
      .withCheck(new FloatUsageCheck())
      .verifyIssues();
  }
}
