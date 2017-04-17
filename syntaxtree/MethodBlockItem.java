/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Method block item can be either VarDecl or Statement.
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public abstract class MethodBlockItem
{
    public abstract void accept(Visitor v);
    public abstract MJType accept(TypeVisitor v);
}
