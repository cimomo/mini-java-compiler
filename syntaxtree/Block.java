/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Block statement: {statements}
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class Block extends Statement
{
    public StatementList sl;

    public Block(StatementList sl)
    {
        this.sl = sl;
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

