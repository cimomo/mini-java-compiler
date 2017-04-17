/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Boolean type: boolean
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class BooleanType extends Type
{
    public void accept(Visitor v)
    {
        v.visit(this);
    }


    public MJType accept(TypeVisitor v)
    {
        return v.visit(this);
    }
}
