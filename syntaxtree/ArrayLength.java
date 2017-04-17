/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Array length expression: expr.length
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class ArrayLength extends Exp
{
    public Exp e;

    public ArrayLength(Exp e)
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
