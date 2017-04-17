/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Less than expression: expr < expr
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class LessThan extends Exp
{
    public Exp e1;
    public Exp e2;

    public LessThan(Exp e1, Exp e2)
    {
        this.e1 = e1;
        this.e2 = e2;
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
