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

public interface Visitor
{
    public void visit(Program n);
    public void visit(MainClass n);
    public void visit(ClassDeclSimple n);
    public void visit(ClassDeclExtends n);
    public void visit(VarDecl n);
    public void visit(MethodDecl n);
    public void visit(Formal n);
    public void visit(IntArrayType n);
    public void visit(ObjArrayType n);
    public void visit(BooleanType n);
    public void visit(IntegerType n);
    public void visit(IdentifierType n);
    public void visit(Block n);
    public void visit(If n);
    public void visit(While n);
    public void visit(Print n);
    public void visit(Assign n);
    public void visit(ArrayAssign n);
    public void visit(And n);
    public void visit(LessThan n);
    public void visit(Plus n);
    public void visit(Minus n);
    public void visit(Times n);
    public void visit(ArrayLookup n);
    public void visit(ArrayLength n);
    public void visit(Call n);
    public void visit(LocalCall n);
    public void visit(IntegerLiteral n);
    public void visit(True n);
    public void visit(False n);
    public void visit(IdentifierExp n);
    public void visit(This n);
    public void visit(Super n);
    public void visit(NewArray n);
    public void visit(NewObjArray n);
    public void visit(NewObject n);
    public void visit(Not n);
    public void visit(InstanceOf n);
    public void visit(Cast n);
    public void visit(Identifier n);
    public void visit(ErrorStmt n);
}
