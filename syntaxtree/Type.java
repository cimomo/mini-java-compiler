/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Type.
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public abstract class Type
{
    public abstract void accept(Visitor v);
    public abstract MJType accept(TypeVisitor v);
}
