/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Integer literal
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class IntegerLiteral extends Exp
{
    public int i;

    public IntegerLiteral(int i)
    {
        this.i = i;
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
