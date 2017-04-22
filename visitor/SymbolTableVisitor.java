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

public class SymbolTableVisitor extends TypeDepthFirstVisitor 
{   
    private MJSymbolTable symbolTable;
    
    public SymbolTableVisitor(MJSymbolTable symbolTable)
    {
        this.symbolTable = symbolTable;
    }
    
    //
    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    //
    public MJType visit(ClassDeclSimple n) 
    {
        MJType type = MJTypeSystem.declareType(n.i.s);
        
        MJClass cls = symbolTable.putClass(n.i.s, type);
        
        symbolTable.enterScope(cls);
        
        for (int i = 0; i < n.vl.size(); i ++) {
            VarDecl vd = n.vl.elementAt(i);
            vd.accept(this);
        }
        
        for (int i = 0; i < n.ml.size(); i ++) {
            MethodDecl md = n.ml.elementAt(i);
            md.accept(this);
        }
        
        symbolTable.exitScope();
        
        n.cls = cls;
    
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
        MJClassType type = MJTypeSystem.declareType(n.i1.s, n.i2.s);
        
        MJClass cls = symbolTable.putClass(n.i1.s, type, n.i2.s, type.getSuper());
        
        symbolTable.enterScope(cls);
    
        for (int i = 0; i < n.vl.size(); i ++) {
            VarDecl vd = n.vl.elementAt(i);
            vd.accept(this);
        }
        
        for (int i = 0; i < n.ml.size(); i ++) {
            MethodDecl md = n.ml.elementAt(i);
            md.accept(this);
        }
        
        symbolTable.exitScope();
        
        n.cls = cls;
        
        return null;
    }

    
    public MJType visit(IntArrayType n) 
    {
        return MJTypeSystem.INTARRAY;
    }
    
    
    //
    // String s
    //
    public MJType visit(ObjArrayType n)
    {
        MJType type = MJTypeSystem.referenceType(n.s);
        return new MJArrayType(type);
    }

    
    public MJType visit(BooleanType n) 
    {
        return MJTypeSystem.BOOL;
    }

    
    public MJType visit(IntegerType n) 
    {
        return MJTypeSystem.INT;
    }

    
    //
    // String s;
    //
    public MJType visit(IdentifierType n) 
    {
        return MJTypeSystem.referenceType(n.s);
    }
    
    
    //
    // Type t;
    // Identifier i;
    //
    public MJType visit(VarDecl n) 
    {
        MJType type = n.t.accept(this);
        
        MJVariable var = symbolTable.putVariable(n.i.s, type);
        n.var = var;
        
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
        boolean endVarDecl = false;
        
        MJType type = n.t.accept(this);
        
        MJMethod method = symbolTable.putMethod(n.i.s, type);
        
        symbolTable.enterScope(method);
    
        for (int i = 0; i < n.fl.size(); i ++) {
            Formal f = n.fl.elementAt(i);
            f.accept(this);
            f.var.setAddress("ebp+" + (i+3)*4);
        }
        
        for (int i = 0; i < n.mb.size(); i ++) {
            MethodBlockItem mbi = n.mb.elementAt(i);
            
            //
            // Verify that VarDecls come before Statements.
            //
            if (mbi instanceof VarDecl) {
                if (endVarDecl) {
                    MJTypeSystem.error("Error: variable declaration appears after statement");
                    continue;
                }
                VarDecl vd = (VarDecl) mbi;
                vd.accept(this);
                vd.var.setAddress("ebp-" + (i+1)*4);
            
            } else {
                endVarDecl = true;
            }
        }
        
        symbolTable.exitScope();
        
        n.mtd = method;
        
        return null;
    }
    

    //
    // Type t;
    // Identifier i;
    //
    public MJType visit(Formal n) 
    {
        MJType type = n.t.accept(this);
        
        MJVariable var = symbolTable.addParameter(n.i.s, type);
        n.var = var;
        
        return null;
    }
}
