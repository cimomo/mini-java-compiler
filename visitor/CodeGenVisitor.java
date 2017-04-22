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
import symboltable.*;
import java.io.*;
import java.util.*;

public class CodeGenVisitor implements Visitor
{
    private PrintWriter asmWriter;
    private MJSymbolTable symbolTable;
    private static int labelSN = 0;
    private String outputFileName;
    
    public CodeGenVisitor(MJSymbolTable symbolTable, String outputFileName)
    {
        this.symbolTable = symbolTable;
        this.outputFileName = outputFileName;
    }
    
    //
    // MainClass m;
    // ClassDeclList cl;
    //
    public void visit(Program n)
    {
        openFile();
        
        symbolTable.assignMemberAddresses();
        genVTables();
        
        n.m.accept(this);

        for (int i = 0; i < n.cl.size(); i ++) {
            n.cl.elementAt(i).accept(this);
        }
        
        closeFile();
    }


    //
    // Identifier i1, i2;
    // Statement s;
    //
    public void visit(MainClass n)
    {
        genLabel("asm_main");
        genProlog(0);
        n.s.accept(this);
        genEpilog(0);
    }

    //
    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    //
    public void visit(ClassDeclSimple n)
    {   
        for (int i = 0; i < n.ml.size(); i ++) {
            n.ml.elementAt(i).accept(this);
        }
    }

    //
    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    //
    public void visit(ClassDeclExtends n)
    {   
        for (int i = 0; i < n.ml.size(); i ++) {
            n.ml.elementAt(i).accept(this);
        }
    }

    //
    // Type t;
    // Identifier i;
    //
    public void visit(VarDecl n)
    {
        // Nothing to do.
    }

    //
    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    //
    public void visit(MethodDecl n)
    {
        int i = 0;
        int nLocals = 0;
        String mtdLabel = n.mtd.getLabel();
        
        genLabel(mtdLabel);
        
        for (i = 0; i < n.mb.size(); i ++) {
            MethodBlockItem mbi = n.mb.elementAt(i);
            
            if (mbi instanceof Statement) {
                break;
            }
            
            mbi.accept(this);
        }
        
        nLocals = i;
        genProlog(nLocals);
        
        for (; i < n.mb.size(); i ++) {
            MethodBlockItem mbi = n.mb.elementAt(i);
            mbi.accept(this);
        }
        
        n.e.accept(this);
        
        //
        // Return value is already in eax.
        //
        
        genEpilog(nLocals);
    }

    //
    // Type t;
    // Identifier i;
    //
    public void visit(Formal n)
    {
        throw new Error("Not implemented");
    }

    public void visit(IntArrayType n)
    {
        throw new Error("Not implemented");
    }
    
    public void visit(ObjArrayType n)
    {
        throw new Error("Not implemented");
    }

    public void visit(BooleanType n)
    {
        throw new Error("Not implemented");
    }

    public void visit(IntegerType n)
    {
        throw new Error("Not implemented");
    }

    //
    // String s;
    //
    public void visit(IdentifierType n)
    {
        String clsLabel = MJSymbolTable.getClassLabel(n.s);
        gen("lea eax," + clsLabel);
    }

    //
    // StatementList sl;
    //
    public void visit(Block n)
    {
        for (int i = 0; i < n.sl.size(); i ++) {
            n.sl.elementAt(i).accept(this);
        }
    }

    //
    // Exp e;
    // Statement s1,s2;
    //
    public void visit(If n)
    {
        String lElse = getLabel("Else");
        String lDone = getLabel("Done");
        
        n.e.accept(this);
        
        gen("cmp eax,0");
        gen("je " + lElse);
        n.s1.accept(this);
        gen("jmp " + lDone);
        genLabel(lElse);
        n.s2.accept(this);
        genLabel(lDone);
    }

    //
    // Exp e;
    // Statement s;
    public void visit(While n)
    {
        String lWhile = getLabel("While");
        String lDone = getLabel("Done");
        
        genLabel(lWhile);
        n.e.accept(this);
        gen("cmp eax,0");
        gen("je " + lDone);
        n.s.accept(this);
        gen("jmp " + lWhile);
        genLabel(lDone);
    }

    //
    // Exp e;
    //
    public void visit(Print n)
    {
        n.e.accept(this);
        
        gen("push eax");
        gen("call put");
        gen("add esp,4");
    }

    //
    // Identifier i;
    // Exp e;
    //
    public void visit(Assign n)
    {
        String addr = n.i.var.getAddress();
        n.e.accept(this);
        
        //
        // Restore ecx in case we need offset from object.
        //
        gen("mov ecx,[ebp]");
        gen("mov [" + addr + "],eax");
    }

    //
    // Identifier i;
    // Exp e1,e2;
    //
    public void visit(ArrayAssign n)
    {
        String addr = n.i.var.getAddress();
        
        n.e1.accept(this);
        gen("push eax");
        
        n.e2.accept(this);
        
        gen("pop ebx");
        gen("mov edx,[" + addr + "]");
        
        gen("mov [edx+ebx*4],eax");
    }

    //
    // Exp e1,e2;
    //
    public void visit(And n)
    {
        String lFalse = getLabel("False");
        String lDone = getLabel("Done");
        
        n.e1.accept(this);
        gen("cmp eax,0");
        gen("je " + lFalse);
        n.e2.accept(this);
        gen("cmp eax,0");
        gen("je " + lFalse);
        gen("mov eax,-1");
        gen("jmp " + lDone);
        genLabel(lFalse);
        gen("mov eax,0");
        genLabel(lDone);
    }

    //
    // Exp e1,e2;
    //
    public void visit(LessThan n)
    {
        String lFalse = getLabel("False");
        String lDone = getLabel("Done");
        
        n.e1.accept(this);
        gen("push eax");
        
        n.e2.accept(this);
        gen("pop edx");
        
        gen("cmp edx,eax");
        gen("jge " + lFalse);
        gen("mov eax,-1");
        gen("jmp " + lDone);
        genLabel(lFalse);
        gen("mov eax,0");
        genLabel(lDone);
    }

    //
    // Exp e1,e2;
    //
    public void visit(Plus n)
    {
        n.e1.accept(this);
        gen("push eax");
        
        n.e2.accept(this);
        
        gen("pop edx");
        
        gen("add eax,edx");
    }

    //
    // Exp e1,e2;
    //
    public void visit(Minus n)
    {
        n.e2.accept(this);
        gen("push eax");
        
        n.e1.accept(this);
        
        gen("pop edx");
        
        gen("sub eax,edx");
    }

    //
    // Exp e1,e2;
    //
    public void visit(Times n)
    {
        n.e1.accept(this);
        gen("push eax");
        
        n.e2.accept(this);
        
        gen("pop edx");
        
        gen("imul eax,edx");
    }

    //
    // Exp e1,e2;
    //
    public void visit(ArrayLookup n)
    {
        n.e1.accept(this);
        gen("push eax");
        
        n.e2.accept(this);
        gen("pop edx");
        
        gen("mov eax,[edx+eax*4]");
    }

    //
    // Exp e;
    //
    public void visit(ArrayLength n)
    {
        n.e.accept(this);
        
        gen("mov eax,[eax-4]");
    }

    //
    // Exp e;
    // Identifier i;
    // ExpList el;
    //
    public void visit(Call n)
    {
        int nArgs = n.el.size();
        int offset = n.mtd.getOffset();
        
        for (int i = nArgs - 1; i >= 0; i --) {
            n.el.elementAt(i).accept(this);
            gen("push eax");
        }
        
        //
        // Get pointer to object.
        //
        n.e.accept(this);
        
        gen("mov ecx,eax");
        gen("mov eax,[ecx]");
        gen("call dword ptr [eax+" + offset + "]");
        
        gen("add esp," + nArgs * 4);
    }
    
        //
    // Exp e;
    // Identifier i;
    // ExpList el;
    //
    public void visit(LocalCall n)
    {
        int nArgs = n.el.size();
        int offset = n.mtd.getOffset();
        
        for (int i = nArgs - 1; i >= 0; i --) {
            n.el.elementAt(i).accept(this);
            gen("push eax");
        }
        
        //
        // Get pointer to this.
        //
        gen("mov ecx,[ebp]");
        gen("mov eax,[ecx]");
        gen("call dword ptr [eax+" + offset + "]");
        
        gen("add esp," + nArgs * 4);
    }

    //
    // int i;
    //
    public void visit(IntegerLiteral n)
    {
        gen("mov eax," + n.i);
    }

    //
    // true
    //
    public void visit(True n)
    {
        gen("mov eax,-1");
    }

    //
    // false
    //
    public void visit(False n)
    {
        gen("mov eax,0");
    }

    //
    // String s;
    //
    public void visit(IdentifierExp n)
    {
        String addr = n.var.getAddress();
        
        //
        // Restore ecx in case variable is on heap.
        //
        gen("mov ecx,[ebp]");
        gen("mov eax,[" + addr + "]");
    }

    //
    // this
    //
    public void visit(This n)
    {
        //
        // We stored the this pointer on the stack where ebp points to.
        //
        gen("mov eax,[ebp]");
    }
    
    //
    // super
    //
    public void visit(Super n)
    {
        gen("mov eax,[ebp]");
        gen("mov eax,[eax]");
    }

    //
    // Exp e;
    //
    public void visit(NewArray n)
    {
        n.e.accept(this);
        
        //
        // Allocate 4 extra bytes for size.
        //
        gen("push eax");
        gen("inc eax");
        gen("imul eax,4");
        gen("push eax");
        gen("call mjmalloc");
        gen("add esp,4");
        
        //
        // Make pointer point to the beginning of the array (past header).
        //
        gen("pop edx");
        gen("mov [eax],edx");
        gen("add eax,4");
    }
    
    //
    // Identifier i;
    // Exp e;
    //
    public void visit(NewObjArray n)
    {
        n.e.accept(this);
        
        //
        // Allocate 4 extra bytes for size.
        //
        gen("push eax");
        gen("inc eax");
        gen("imul eax,4");
        gen("push eax");
        gen("call mjmalloc");
        gen("add esp,4");
        
        //
        // Make pointer point to the beginning of the array (past header).
        //
        gen("pop edx");
        gen("mov [eax],edx");
        gen("add eax,4");
    }

    //
    // Identifier i;
    //
    public void visit(NewObject n)
    {
        String clsLabel = MJSymbolTable.getClassLabel(n.i.s);
        int size = n.cls.getSize();
        
        gen("push " + size);
        gen("call mjmalloc");
        gen("add esp,4");
        gen("lea edx," + clsLabel);
        gen("mov [eax],edx");
        
        //
        // eax still has pointer to object.
        //
    }

    //
    // Exp e;
    //
    public void visit(Not n)
    {
        n.e.accept(this);
        gen("not eax");
    }
    
    //
    // Exp e;
    // IdentifierType t;
    //
    public void visit(InstanceOf n)
    {
        n.t.accept(this);
        gen("push eax");
        
        n.e.accept(this);
        gen("push eax");
        
        gen("call instanceof");
        gen("add esp,8");
        
        //
        // eax already contains boolean result.
        //
    }
    
    //
    // IdentifierType t;
    // Exp e;
    //
    public void visit(Cast n)
    {
        n.t.accept(this);
        gen("push eax");
        
        n.e.accept(this);
        gen("push eax");
        
        gen("call cast");
        gen("add esp,8");
        
        //
        // eax already contains pointer to casted object
        //
    }

    //
    // String s;
    //
    public void visit(Identifier n)
    {
        throw new Error("Not implemented");
    }

    //
    // Error statement;
    //
    public void visit(ErrorStmt n)
    {
        throw new Error("Not implemented");
    }

    private void openFile()
    {
        try {
            asmWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
        } catch(Exception e) {
            throw new Error("Open file failed.");
        }
        asmWriter.println(".386");
        asmWriter.println(".model flat,c");
        asmWriter.println("public asm_main");
        asmWriter.println("extern put:near,get:near,mjmalloc:near,instanceof:near,cast:near");
    }
    
    private void closeFile()
    {
        asmWriter.println("end");
        
        asmWriter.close();
    }
    
    private void gen(String s)
    {
        asmWriter.println("    " + s);
    }
    
    private void genProlog(int nLocals)
    {
        gen("push ebp");
        gen("push ecx");
        gen("mov ebp,esp");
        gen("sub esp," + 4 * nLocals);
    }
    
    private void genEpilog(int nLocals)
    {
        gen("mov esp,ebp");
        gen("pop ecx");
        gen("pop ebp");
        gen("ret");
    }
    
    private void genLabel(String label)
    {
        asmWriter.println(label + ":");
    }
    
    private String getLabel(String prefix)
    {
        labelSN ++;
        return (prefix + labelSN);
    }
    
    private void genVTables()
    {
        asmWriter.println(".data");
        
        MJClass cls = symbolTable.getFirstClass();
        while (cls != null) {
            String clsLabel = MJSymbolTable.getClassLabel(cls.getName());
            
            MJClass clsSuper = cls.getSuper();
            if (clsSuper != null) {
                String clsSuperLabel = MJSymbolTable.getClassLabel(clsSuper.getName());
                asmWriter.println(clsLabel + " dd " + clsSuperLabel);
            } else {
                asmWriter.println(clsLabel + " dd 0");
            }
            
            Vector<MJMethod> methods = new Vector<MJMethod>();
            
            MJMethod mtd = cls.getFirstMethod();
            while (mtd != null) {
                int i = 0;
                for (; i < methods.size(); i ++) {
                    if (mtd.getName().equals(methods.elementAt(i).getName())) {
                        methods.set(i, mtd);
                        break;
                    } 
                }
                
                if (i >= methods.size()) {
                    methods.add(mtd);
                }
                
                mtd = cls.getNextMethod();
            }
            
            for (int i = 0; i < methods.size(); i ++) {
                mtd = methods.elementAt(i);
                mtd.setOffset((i+1)*4);
                
                String mtdLabel = mtd.getLabel();
                gen("dd " + mtdLabel);
            }
            
            cls = symbolTable.getNextClass();
        }
        
        //
        // swtich to code section.
        //
        asmWriter.println(".code");
    }
}
