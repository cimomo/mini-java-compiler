/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Identifier expression.
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;
import symboltable.*;

public class IdentifierExp extends Exp
{
    public String s;
    public MJVariable var;

    public IdentifierExp(String s)
    {
        this.s = s;
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
