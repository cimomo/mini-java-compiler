/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Class declaration.
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public abstract class ClassDecl
{
    public abstract void accept(Visitor v);
    public abstract MJType accept(TypeVisitor v);
}
