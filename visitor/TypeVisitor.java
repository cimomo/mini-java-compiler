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
