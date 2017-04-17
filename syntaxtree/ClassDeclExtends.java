/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Subclass declaration: class id extends id { varDecls methodDecls }
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;
import symboltable.*;

public class ClassDeclExtends extends ClassDecl
{
    public Identifier i1;
    public Identifier i2;
    public VarDeclList vl;
    public MethodDeclList ml;
    public MJClass cls;

    public ClassDeclExtends(Identifier i1, Identifier i2, VarDeclList vl, MethodDeclList ml)
    {
        this.i1 = i1;
        this.i2 = i2;
        this.vl = vl;
        this.ml = ml;
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
