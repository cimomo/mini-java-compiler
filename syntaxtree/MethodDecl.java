/*
 * Method declaration: public type id(optAgrList) { mothodBlock return expr; }
 *
 * ------------------------------------------------------------------
 *
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
