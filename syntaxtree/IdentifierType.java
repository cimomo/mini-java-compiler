/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Identifier type: id
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class IdentifierType extends Type
{
    public String s;

    public IdentifierType(String s)
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
