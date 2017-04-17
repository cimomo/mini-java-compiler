/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Simple class declaraion: class id { varDecls methodDecls }
 *
 */

package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import typesystem.*;
import symboltable.*;

public class ClassDeclSimple extends ClassDecl
{
    public Identifier i;
    public VarDeclList vl;
    public MethodDeclList ml;
    public MJClass cls;

    public ClassDeclSimple(Identifier i, VarDeclList vl, MethodDeclList ml)
    {
        this.i = i;
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
