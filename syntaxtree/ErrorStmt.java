/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * This class corresponds to chunks of symbols with syntax error skipped
 * by the compiler.
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class ErrorStmt extends Statement
{
    public static int numErrors = 0;
    
    public ErrorStmt()
    {
        numErrors ++;
    }
    
    public void accept(Visitor v)
    {
        v.visit(this);
    }


    public MJType accept(TypeVisitor v)
    {
        return v.visit(this);
    }
}

