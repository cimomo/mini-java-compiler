/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * MiniJava program: mainClass classDecls
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;

public class Program
{
    public MainClass m;
    public ClassDeclList cl;

    public Program(MainClass m, ClassDeclList cl)
    {
        this.m = m;
        this.cl = cl;
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
