/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Integer array type: int[]
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class IntArrayType extends Type
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
