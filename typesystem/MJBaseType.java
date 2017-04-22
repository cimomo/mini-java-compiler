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
public class MJBaseType extends MJType 
{
    public boolean compatibleWith(MJType that)
    {
        if (!(that instanceof MJBaseType)) {
            return false;
        }
        
        return (this == that);
    }
    
    public String toString()
    {
        String typeName;
        
        if (this == MJTypeSystem.INT) {
            typeName = "int";
        
        } else if (this == MJTypeSystem.BOOL) {
            typeName = "boolean";
            
        } else if (this == MJTypeSystem.INTARRAY) {
            typeName = "int[]";
        
        } else {
            typeName = "unknown";
        }
        
        return typeName;
    }
}
