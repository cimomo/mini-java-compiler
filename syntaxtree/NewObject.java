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

public class NewObject extends Exp
{
    public Identifier i;
    public MJClass cls;

    public NewObject(Identifier i)
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
