/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * New array expression: new int[expr]
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class NewArray extends Exp
{
    public Exp e;

    public NewArray(Exp e)
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
