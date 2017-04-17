/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * While loop statement: while (expr) stmt
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class While extends Statement
{
    public Exp e;
    public Statement s;

    public While(Exp e, Statement s)
    {
        this.e = e;
        this.s = s;
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

