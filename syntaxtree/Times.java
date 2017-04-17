/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Times expression: expr * expr
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class Times extends Exp
{
    public Exp e1;
    public Exp e2;

    public Times(Exp e1, Exp e2)
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
