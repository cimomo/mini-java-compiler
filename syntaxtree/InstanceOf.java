/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Assignment statement: id = expr;
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class InstanceOf extends Exp
{
    public Exp e;
    public IdentifierType t;

    public InstanceOf(Exp e, IdentifierType t)
    {
        this.e = e;
        this.t = t;
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

