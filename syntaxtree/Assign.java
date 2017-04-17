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

public class Assign extends Statement
{
    public Identifier i;
    public Exp e;

    public Assign(Identifier i, Exp e)
    {
        this.i = i;
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

