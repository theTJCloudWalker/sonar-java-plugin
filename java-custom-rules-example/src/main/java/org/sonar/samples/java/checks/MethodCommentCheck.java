package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key="MethodCommentCheck")
public class MethodCommentCheck extends BaseTreeVisitor implements JavaFileScanner {
  private JavaFileScannerContext context;

  private static final String MSG_NO_COMMENT = "There is no comment under class";
  private static final String MSG_NO_AUTHOR = "There is no @author inside comment";

  private static final String MSG_LACK_PARAM = "There is missed param inside comment";

  private static final String MSG_NO_RETURN = "There is no @return inside comment";

  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }

  @Override
  public void visitMethod(MethodTree tree){
    String comment = ((SyntaxTrivia) tree).comment();
    if(comment==null){
      context.reportIssue(this,tree, MSG_NO_COMMENT);
    }
    if (!comment.contains("@author")) {
      context.reportIssue(this,tree, MSG_LACK_PARAM);
    }

    if (!comment.contains("@param")) {
      context.reportIssue(this,tree, MSG_LACK_PARAM);
    }
    if (!comment.contains("@return")) {
      context.reportIssue(this,tree, MSG_NO_RETURN);
    }
  }
}
