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

package typesystem;

/**
 *
 * @author kaichen
 */
public class MJArrayType extends MJType 
{
    private MJType type;
    
    public MJArrayType(MJType type)
    {
        this.type = type;
    }
    
    public MJType getType()
    {
        return type;
    }
    
    public boolean compatibleWith(MJType that)
    {
        if (!(that instanceof MJArrayType)) {
            return false;
        }
        
        return (this.getType() == ((MJArrayType)that).getType());
    }
    
    public String toString()
    {   
        return type + "[]";
    }
}
