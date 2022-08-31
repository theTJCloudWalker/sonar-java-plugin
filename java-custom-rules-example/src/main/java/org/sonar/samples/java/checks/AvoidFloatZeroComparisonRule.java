package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;

@Rule(key="AvoidFloatZeroComparison")
public class AvoidFloatZeroComparisonRule extends BaseTreeVisitor implements JavaFileScanner{
  private JavaFileScannerContext context;
  private  IssuableSubscriptionVisitor vis;
  @Override
  public void scanFile(JavaFileScannerContext context){
    this.context=context;
    scan(context.getTree());
  }

  @Override
  public void visitBinaryExpression(BinaryExpressionTree tree){
    if(tree.is(Tree.Kind.EQUAL_TO)){
      //context.reportIssue(this,tree,"comment here");
      BinaryExpressionTree copy = tree;
      //BinaryExpressionTreeImpl expressionTree=(BinaryExpressionTreeImpl) copy;
//      context.reportIssue(this,tree,"comment here");
//      return;
      if((tree.leftOperand().symbolType().is("float")||tree.rightOperand().symbolType().is("float"))){
        context.reportIssue(this, tree, "avoid direct comparison");
        //context.getFileLines();
        //vis.reportIssue(tree,"avoid comparison between 0 and float");
        return;
      }
    }
    super.visitBinaryExpression(tree);
  }
}
