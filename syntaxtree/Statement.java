/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Statement.
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public abstract class Statement extends MethodBlockItem
{
    public abstract void accept(Visitor v);
    public abstract MJType accept(TypeVisitor v);
}
