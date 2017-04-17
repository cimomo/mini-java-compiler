/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Print statement: System.out.println(expr);
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class Print extends Statement
{
    public Exp e;

    public Print(Exp e)
    {
        this.e = e;
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
