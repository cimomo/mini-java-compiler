/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Identifier: id
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;
import symboltable.*;

public class Identifier
{
    public String s;
    public MJVariable var;

    public Identifier(String s)
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


    public String toString()
    {
        return s;
    }
}
