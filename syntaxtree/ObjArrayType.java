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

public class ObjArrayType extends Type
{
    public String s;
    
    public ObjArrayType(String s)
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
