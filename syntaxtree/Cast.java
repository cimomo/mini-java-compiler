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

public class Cast extends Exp
{
    public IdentifierType t;
    public Exp e;

    public Cast(IdentifierType t, Exp e)
    {
        this.t = t;
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

