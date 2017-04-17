/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Method call expression: expr.id(exprs)
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;
import symboltable.*;

public class LocalCall extends Exp
{
    public Identifier i;
    public ExpList el;
    public MJMethod mtd;

    public LocalCall(Identifier i, ExpList el)
    {
        this.i = i;
        this.el = el;
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
