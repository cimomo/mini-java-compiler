/*
 * @begin[license]
 * Copyright (C) Kai Chen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * @author: Kai Chen
 * @end[license]
 *
 */

package visitor;

import syntaxtree.*;
import typesystem.*;
import symboltable.*;


public class TypeCheckVisitor extends TypeDepthFirstVisitor
{
    private MJSymbolTable symbolTable;
    
    public TypeCheckVisitor(MJSymbolTable symbolTable)
    {
        this.symbolTable = symbolTable;
    }
    
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
        MJClass cls = symbolTable.getClass(n.i.s);
        
        assert(cls != null);
        
        symbolTable.enterScope(cls);
        
        for (int i = 0; i < n.ml.size(); i ++) {
            n.ml.elementAt(i).accept(this);
        }
        
        symbolTable.exitScope();
    
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
        MJClass cls = symbolTable.getClass(n.i1.s);
        
        assert(cls != null);
        
        symbolTable.enterScope(cls);
        
        for (int i = 0; i < n.ml.size(); i ++) {
            n.ml.elementAt(i).accept(this);
        }
        
        symbolTable.exitScope();
        
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
        MJMethod method = symbolTable.getMethod(n.i.s);
        
        assert(method != null);
        
        symbolTable.enterScope(method);
        
        int i;
        for (i = 0; i < n.mb.size(); i ++) {
            if (n.mb.elementAt(i) instanceof Statement) {
                break;
            }
        }
        
        for (; i < n.mb.size(); i ++) {
            n.mb.elementAt(i).accept(this);
        }
        
        MJType returnType = n.e.accept(this);
        
        symbolTable.exitScope();
        
        MJType methodType = method.getType();
        
        MJTypeSystem.checkType(methodType, returnType);
        
        return null;
    }
    

    //
    // Type t;
    // Identifier i;
    //
    public MJType visit(Formal n) 
    {
        assert(false);
        return null;
    }

    
    public MJType visit(IntArrayType n) 
    {
        assert(false);
        return null;
    }

    
    public MJType visit(BooleanType n) 
    {
        assert(false);
        return null;
    }

    
    public MJType visit(IntegerType n) 
    {
        assert(false);
        return null;
    }

    
    //
    // String s;
    //
    public MJType visit(IdentifierType n) 
    {
        return MJTypeSystem.getType(n.s);
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
        MJType eType = n.e.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.BOOL, eType);
        
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
        MJType eType = n.e.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.BOOL, eType);
        
        n.s.accept(this);
        return null;
    }
    
    //
    // Exp e;
    //
    public MJType visit(Print n) 
    {
        MJType type = n.e.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.INT, type);
        
        return null;
    }
  
    
    //
    // Identifier i;
    // Exp e;
    //
    public MJType visit(Assign n) 
    {
        MJType lType = n.i.accept(this);
        MJType rType = n.e.accept(this);
        
        MJTypeSystem.checkType(lType, rType);
        
        return null;
    }

    
    //
    // Identifier i;
    // Exp e1,e2;
    //
    public MJType visit(ArrayAssign n) 
    {
        MJType iType = n.i.accept(this);
        MJType e1Type = n.e1.accept(this);
        MJType e2Type = n.e2.accept(this);
        
        if (iType == MJTypeSystem.INTARRAY) {
            MJTypeSystem.checkType(MJTypeSystem.INT, e2Type);
        } else if (iType instanceof MJArrayType) {
            MJTypeSystem.checkType(((MJArrayType)iType).getType(), e2Type);
        } else {
            MJTypeSystem.error("Error: " + n.i.s + " is not array");
        }
        
        MJTypeSystem.checkType(MJTypeSystem.INT, e1Type);
        
        return null;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(And n) 
    {
        MJType e1Type = n.e1.accept(this);
        MJType e2Type = n.e2.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.BOOL, e1Type);
        MJTypeSystem.checkType(MJTypeSystem.BOOL, e2Type);
        
        return MJTypeSystem.BOOL;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(LessThan n) 
    {
        MJType e1Type = n.e1.accept(this);
        MJType e2Type = n.e2.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.INT, e1Type);
        MJTypeSystem.checkType(MJTypeSystem.INT, e2Type);
        
        return MJTypeSystem.BOOL;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(Plus n) 
    {
        MJType e1Type = n.e1.accept(this);
        MJType e2Type = n.e2.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.INT, e1Type);
        MJTypeSystem.checkType(MJTypeSystem.INT, e2Type);
        
        return MJTypeSystem.INT;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(Minus n) 
    {
        MJType e1Type = n.e1.accept(this);
        MJType e2Type = n.e2.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.INT, e1Type);
        MJTypeSystem.checkType(MJTypeSystem.INT, e2Type);
        
        return MJTypeSystem.INT;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(Times n) 
    {
        MJType e1Type = n.e1.accept(this);
        MJType e2Type = n.e2.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.INT, e1Type);
        MJTypeSystem.checkType(MJTypeSystem.INT, e2Type);
        
        return MJTypeSystem.INT;
    }

    
    //
    // Exp e1,e2;
    //
    public MJType visit(ArrayLookup n) 
    {
        MJType e1Type = n.e1.accept(this);
        MJType e2Type = n.e2.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.INT, e2Type);
        
        if (e1Type == MJTypeSystem.INTARRAY) {
            return MJTypeSystem.INT;
        } else if (e1Type instanceof MJArrayType) {
            return ((MJArrayType)e1Type).getType();
        }
        
        MJTypeSystem.error("Error: lookup performed on non-array");
        
        return MJTypeSystem.UNKNOWN;
    }

    
    //
    // Exp e;
    //
    public MJType visit(ArrayLength n) 
    {
        MJType type = n.e.accept(this);
        
        if (type == MJTypeSystem.INTARRAY || type instanceof MJArrayType) {
            return MJTypeSystem.INT;
        }
        
        MJTypeSystem.error("Error: length performed on non-array");
        
        return MJTypeSystem.UNKNOWN;
    }

    
    //
    // Exp e;
    // Identifier i;
    // ExpList el;
    //
    public MJType visit(Call n) 
    {
        MJType eType = n.e.accept(this);
        
        if (!(eType instanceof MJClassType)) {
            MJTypeSystem.error("Error: " + eType + " is not a class");
            return MJTypeSystem.UNKNOWN;
        }
        
        String className = eType.toString();
        
        MJMethod method = symbolTable.getMethod(className, n.i.s);
        
        if (method == null) {
            return MJTypeSystem.UNKNOWN;
        }
        
        if (n.el.size() != method.numParameters()) {
            MJTypeSystem.error("Calling " + 
                               n.i + 
                               " with wrong number of arguments. Found: " + 
                               n.el.size() + 
                               "; Required: " + 
                               method.numParameters());
            return MJTypeSystem.UNKNOWN;
        }
    
        for (int i = 0; i < n.el.size(); i ++) {
            MJType required = method.parameterAt(i).getType();
            MJType found = n.el.elementAt(i).accept(this);
            
            MJTypeSystem.checkType(required, found);
        }
        
        n.mtd = method;
        
        return method.getType();
    }

    //
    // Identifier i;
    // ExpList el;
    //
    public MJType visit(LocalCall n) 
    {   
        String className = symbolTable.getThis().getName();
        
        MJMethod method = symbolTable.getMethod(className, n.i.s);
        
        if (method == null) {
            return MJTypeSystem.UNKNOWN;
        }
        
        if (n.el.size() != method.numParameters()) {
            MJTypeSystem.error("Calling " + 
                               n.i + 
                               " with wrong number of arguments. Found: " + 
                               n.el.size() + 
                               "; Required: " + 
                               method.numParameters());
            return MJTypeSystem.UNKNOWN;
        }
    
        for (int i = 0; i < n.el.size(); i ++) {
            MJType required = method.parameterAt(i).getType();
            MJType found = n.el.elementAt(i).accept(this);
            
            MJTypeSystem.checkType(required, found);
        }
        
        n.mtd = method;
        
        return method.getType();
    }
    
    //
    // String s;
    //
    public MJType visit(IdentifierExp n) 
    {
        MJVariable var = symbolTable.getVariable(n.s);
        
        if (var == null) {
            return MJTypeSystem.UNKNOWN;
        }
        
        n.var = var;
        
        return var.getType();
    }

    
    public MJType visit(This n) 
    {
        return symbolTable.getThis().getType();
    }
    
    
    public MJType visit(Super n)
    {
        MJClass clsSuper = symbolTable.getSuper();
        MJType type = null;
        
        if (clsSuper != null) {
            type = clsSuper.getType();
        }
        
        return type;
    }

    
    //
    // Exp e;
    //
    public MJType visit(NewArray n) 
    {
        MJType eType = n.e.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.INT, eType);
        
        return MJTypeSystem.INTARRAY;
    }
    
    
    //
    // Identifier i;
    // Exp e;
    //
    public MJType visit(NewObjArray n)
    {
        MJClass cls = symbolTable.getClass(n.i.s);
        if (cls == null) {
            return MJTypeSystem.UNKNOWN;
        }
        
        MJType eType = n.e.accept(this);
        MJTypeSystem.checkType(MJTypeSystem.INT, eType);
        
        return new MJArrayType(cls.getType());
    }

    
    //
    // Identifier i;
    //
    public MJType visit(NewObject n) 
    {
        MJClass cls = symbolTable.getClass(n.i.s);
        
        if (cls == null) {
            return MJTypeSystem.UNKNOWN;
        }
        
        n.cls = cls;
        
        return cls.getType();
    }

    
    //
    // Exp e;
    //
    public MJType visit(Not n) 
    {
        MJType eType = n.e.accept(this);
        
        MJTypeSystem.checkType(MJTypeSystem.BOOL, eType);
        
        return MJTypeSystem.BOOL;
    }
    
    
    //
    // Exp e;
    // IdentifierType t;
    //
    public MJType visit(InstanceOf n)
    {
        MJType eType = n.e.accept(this);
        
        if (!(eType instanceof MJClassType)) {
            MJTypeSystem.error("Error: " + eType + " is not a class");
            return MJTypeSystem.UNKNOWN;
        }
        
        MJType tType = n.t.accept(this);
        
        if (!(tType instanceof MJClassType)) {
            MJTypeSystem.error("Error: " + tType + " is not a class");
            return MJTypeSystem.UNKNOWN;
        }
        
        if (!(eType.compatibleWith(tType) || tType.compatibleWith(eType))) {
            MJTypeSystem.error("Error: types " + eType + " and " + tType + " are not compatible");
            return MJTypeSystem.UNKNOWN;
        }
        
        return MJTypeSystem.BOOL;
    }

    
    //
    // IdentifierType t;
    // Exp e;
    //
    public MJType visit(Cast n)
    {
        MJType tType = n.t.accept(this);
        
        if (!(tType instanceof MJClassType)) {
            MJTypeSystem.error("Error: " + tType + " is not a class");
            return MJTypeSystem.UNKNOWN;
        }
        
        MJType eType = n.e.accept(this);
        
        if (!(eType instanceof MJClassType)) {
            MJTypeSystem.error("Error: " + eType + " is not a class");
            return MJTypeSystem.UNKNOWN;
        }
        
        if (!(eType.compatibleWith(tType) || tType.compatibleWith(eType))) {
            MJTypeSystem.error("Error: types " + eType + " and " + tType + " are not compatible");
            return MJTypeSystem.UNKNOWN;
        }
        
        return tType;
    }
    
    
    //
    // String s;
    //
    public MJType visit(Identifier n) 
    {
        MJVariable var = symbolTable.getVariable(n.s);
        
        if (var == null) {
            return MJTypeSystem.UNKNOWN;
        }
        
        n.var = var;
        
        return var.getType();
    }
}
