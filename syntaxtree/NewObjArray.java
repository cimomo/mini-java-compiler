/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * New object expression: new id()
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;
import symboltable.*;

public class NewObjArray extends Exp
{
    public Identifier i;
    public Exp e;

    public NewObjArray(Identifier i, Exp e)
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
