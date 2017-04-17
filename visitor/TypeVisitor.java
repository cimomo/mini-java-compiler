/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * typed visitor interface.
 *
 */

package visitor;
import syntaxtree.*;
import typesystem.*;

public interface TypeVisitor
{
    public MJType visit(Program n);
    public MJType visit(MainClass n);
    public MJType visit(ClassDeclSimple n);
    public MJType visit(ClassDeclExtends n);
    public MJType visit(VarDecl n);
    public MJType visit(MethodDecl n);
    public MJType visit(Formal n);
    public MJType visit(IntArrayType n);
    public MJType visit(ObjArrayType n);
    public MJType visit(BooleanType n);
    public MJType visit(IntegerType n);
    public MJType visit(IdentifierType n);
    public MJType visit(Block n);
    public MJType visit(If n);
    public MJType visit(While n);
    public MJType visit(Print n);
    public MJType visit(Assign n);
    public MJType visit(ArrayAssign n);
    public MJType visit(And n);
    public MJType visit(LessThan n);
    public MJType visit(Plus n);
    public MJType visit(Minus n);
    public MJType visit(Times n);
    public MJType visit(ArrayLookup n);
    public MJType visit(ArrayLength n);
    public MJType visit(Call n);
    public MJType visit(LocalCall n);
    public MJType visit(IntegerLiteral n);
    public MJType visit(True n);
    public MJType visit(False n);
    public MJType visit(IdentifierExp n);
    public MJType visit(This n);
    public MJType visit(Super n);
    public MJType visit(NewArray n);
    public MJType visit(NewObjArray n);
    public MJType visit(NewObject n);
    public MJType visit(Not n);
    public MJType visit(InstanceOf n);
    public MJType visit(Cast n);
    public MJType visit(Identifier n);
    public MJType visit(ErrorStmt n);
}
