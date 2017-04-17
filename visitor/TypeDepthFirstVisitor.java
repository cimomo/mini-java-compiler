package visitor;

import syntaxtree.*;
import typesystem.*;

public class TypeDepthFirstVisitor implements TypeVisitor 
{
    //
    // MainClass m;
    // ClassDeclList cl;
    //
    public MJType visit(Program n) 
    {
        n.m.accept(this);
    
        for (int i = 0; i < n.cl.size(); i ++) {
            n.cl.elementAt(i).accept(this);
        }
        
        return null;
    }
    
    
    //
    // Identifier i1,i2;
    // Statement s;
    //
    public MJType visit(MainClass n) 
    {
        n.i1.accept(this);
        n.i2.accept(this);
        n.s.accept(this);
        return null;
    }
  
    
    //
    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    //
    public MJType visit(ClassDeclSimple n) 
    {
        n.i.accept(this);
        
        for (int i = 0; i < n.vl.size(); i ++) {
            n.vl.elementAt(i).accept(this);
        }
        
        for (int i = 0; i < n.ml.size(); i ++) {
            n.ml.elementAt(i).accept(this);
        }
    
        return null;
    }
    
 
    //
    // Identifier i1;
    // Identifier i2;
    // VarDeclList vl;
    // MethodDeclList ml;
    //
    public MJType visit(ClassDeclExtends n) 
    {
        n.i1.accept(this);
        n.i2.accept(this);
    
        for (int i = 0; i < n.vl.size(); i ++) {
            n.vl.elementAt(i).accept(this);
        }
        
        for (int i = 0; i < n.ml.size(); i ++) {
            n.ml.elementAt(i).accept(this);
        }
        
        return null;
    }

    
    //
    // Type t;
    // Identifier i;
    //
    public MJType visit(VarDecl n) 
    {
        n.t.accept(this);
        n.i.accept(this);
        return null;
    }

    
    //
    // Type t;
    // Identifier i;
    // FormalList fl;
    // MethodBlock mb;
    // Exp e;
    //
    public MJType visit(MethodDecl n) 
    {
        n.t.accept(this);
        n.i.accept(this);
    
        for (int i = 0; i < n.fl.size(); i ++) {
            n.fl.elementAt(i).accept(this);
        }
        
        for (int i = 0; i < n.mb.size(); i ++) {
            n.mb.elementAt(i).accept(this);
        }
        
        n.e.accept(this);
        return null;
    }
    

    //
    // Type t;
    // Identifier i;
    //
    public MJType visit(Formal n) 
    {
        n.t.accept(this);
        n.i.accept(this);
        return null;
    }

    
    public MJType visit(IntArrayType n) 
    {
        return null;
    }
    
    
    public MJType visit(ObjArrayType n)
    {
        return null;
    }

    
    public MJType visit(BooleanType n) 
    {
        return null;
    }

    
    public MJType visit(IntegerType n) 
    {
        return null;
    }

    
    //
    // String s;
    //
    public MJType visit(IdentifierType n) 
    {
        return null;
    }

    
    //
    // StatementList sl;
    //
    public MJType visit(Block n) 
    {
        for (int i = 0; i < n.sl.size(); i ++) {
            n.sl.elementAt(i).accept(this);
        }
        
        return null;
    }

    
    //
    // Exp e;
    // Statement s1,s2;
    //
    public MJType visit(If n) 
    {
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
        return null;
    }

    
    //
    // Exp e;
    // Statement s;
    //
    public MJType visit(While n) 
    {
        n.e.accept(this);
        n.s.accept(this);
        return null;
    }

    
    //
    // Exp e;
    //
    public MJType visit(Print n) 
    {
        n.e.accept(this);
        return null;
    }
  
    
    //
    // Identifier i;
    // Exp e;
    //
    public MJType visit(Assign n) 
    {
        n.i.accept(this);
        n.e.accept(this);
        return null;
    }

    
    //
    // Identifier i;
    // Exp e1,e2;
    //
    public MJType visit(ArrayAssign n) 
    {
        n.i.accept(this);
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(And n) 
    {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(LessThan n) 
    {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(Plus n) 
    {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(Minus n) 
    {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(Times n) 
    {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(ArrayLookup n) 
    {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    
    //
    // Exp e;
    //
    public MJType visit(ArrayLength n) 
    {
        n.e.accept(this);
        return null;
    }

    
    //
    // Exp e;
    // Identifier i;
    // ExpList el;
    //
    public MJType visit(Call n) 
    {
        n.e.accept(this);
        n.i.accept(this);
    
        for (int i = 0; i < n.el.size(); i ++) {
            n.el.elementAt(i).accept(this);
        }
        
        return null;
    }
    
    
    //
    // Identifier i;
    // ExpList el;
    //
    public MJType visit(LocalCall n)
    {
        n.i.accept(this);
        
        for (int i = 0; i < n.el.size(); i ++) {
            n.el.elementAt(i).accept(this);
        }
        
        return null;
    }

    
    //
    // int i;
    //
    public MJType visit(IntegerLiteral n) 
    {
        return MJTypeSystem.INT;
    }

    
    public MJType visit(True n) 
    {
        return MJTypeSystem.BOOL;
    }

    
    public MJType visit(False n) 
    {
        return MJTypeSystem.BOOL;
    }

    
    //
    // String s;
    //
    public MJType visit(IdentifierExp n) 
    {
        return null;
    }

    
    public MJType visit(This n) 
    {
        return null;
    }
    
    
    public MJType visit(Super n)
    {
        return null;
    }

    
    //
    // Exp e;
    //
    public MJType visit(NewArray n) 
    {
        n.e.accept(this);
        return null;
    }
    
    
    //
    // Identifier i;
    // Exp e;
    //
    public MJType visit(NewObjArray n)
    {
        n.i.accept(this);
        n.e.accept(this);
        
        return null;
    }

    
    //
    // Identifier i;
    //
    public MJType visit(NewObject n) 
    {
        return null;
    }

    
    //
    // Exp e;
    //
    public MJType visit(Not n) 
    {
        n.e.accept(this);
        return null;
    }
    
    
    //
    // Exp e;
    // IdentifierType t;
    //
    public MJType visit(InstanceOf n)
    {
        n.e.accept(this);
        n.t.accept(this);
        
        return null;
    }
    
    
    //
    // IdentifierType t;
    // Exp e;
    //
    public MJType visit(Cast n)
    {
        n.t.accept(this);
        n.e.accept(this);
        
        return null;
    }

    
    //
    // String s;
    //
    public MJType visit(Identifier n) 
    {
        return null;
    }
  
    
    public MJType visit(ErrorStmt n)
    {
        return null;
    }
}
