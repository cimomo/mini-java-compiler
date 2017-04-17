/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Main class: class id { public static void main (String[] id) { statement }}
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class MainClass
{
    public Identifier i1;
    public Identifier i2;
    public Statement s;

    public MainClass(Identifier i1, Identifier i2, Statement s)
    {
        this.i1 = i1;
        this.i2 = i2;
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

