/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Method declaration: public type id(optAgrList) { mothodBlock return expr; }
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;
import symboltable.*;

public class MethodDecl
{
    public Type t;
    public Identifier i;
    public FormalList fl;
    public MethodBlock mb;
    public Exp e;
    public MJMethod mtd;

    public MethodDecl(Type t, Identifier i, FormalList fl, MethodBlock mb, Exp e)
    {
        this.t = t;
        this.i = i;
        this.fl = fl;
        this.mb = mb;
        this.e = e;
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
