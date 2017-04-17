/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Variable declaration.
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;
import symboltable.*;

public class VarDecl extends MethodBlockItem
{
    public Type t;
    public Identifier i;
    public MJVariable var;

    public VarDecl(Type t, Identifier i)
    {
        this.t = t;
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
