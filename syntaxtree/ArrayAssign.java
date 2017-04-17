/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Array assignment statement: id[expr] = expr;
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class ArrayAssign extends Statement
{
    public Identifier i;
    public Exp e1;
    public Exp e2;

    public ArrayAssign(Identifier i, Exp e1, Exp e2)
    {
        this.i = i;
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

