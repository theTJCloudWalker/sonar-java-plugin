package org.sonar.samples.java.checks;

import org.junit.Test;
//import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.java.checks.verifier.CheckVerifier;

public class CommentStandardCheckTest {

  @Test
  void verify(){
    CheckVerifier.newVerifier()
      .onFile("src/test/files/CommentStandardCheck.java")
      .withCheck(new CommentStandardCheck())
      .verifyIssues();
  }
}
