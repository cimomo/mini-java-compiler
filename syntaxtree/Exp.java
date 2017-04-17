/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Expression.
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public abstract class Exp
{
    public abstract void accept(Visitor v);
    public abstract MJType accept(TypeVisitor v);
}
