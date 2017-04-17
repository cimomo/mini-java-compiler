/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Not expression: !expr
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class Not extends Exp
{
    public Exp e;

    public Not(Exp e)
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
