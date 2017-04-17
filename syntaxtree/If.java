/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * If statement: if (expr) stmt else stmt
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class If extends Statement
{
    public Exp e;
    public Statement s1;
    public Statement s2;

    public If(Exp e, Statement s1, Statement s2)
    {
        this.e = e;
        this.s1 = s1;
        this.s2 = s2;
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

