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

import java.util.*;

/**
 *
 * @author kaichen
 */
public class MJTypeSystem 
{
    public static final MJBaseType INT = new MJBaseType();
    public static final MJBaseType BOOL = new MJBaseType();
    public static final MJBaseType INTARRAY = new MJBaseType();
    public static final MJBaseType UNKNOWN = new MJBaseType();
    
    private static int numErrors = 0;
    
    private static Map<String, MJClassType> classTypes = new HashMap<String, MJClassType>();
    private static Map<String, MJClassType> unknownTypes = new HashMap<String, MJClassType>();
    
    public static MJClassType declareType(String id)
    {
        MJClassType type = classTypes.get(id);;
        
        if (type != null) {
            error("Error: Class " + id + " already declared.");
            return type;
        }
    
        if (unknownTypes.containsKey(id)) {
            type = unknownTypes.remove(id);
        
        } else {
            type = new MJClassType(id);
        }
        
        classTypes.put(id, type);
        
        return type;
    }
    
    
    public static MJClassType declareType(String id, String idSuper)
    {
        MJClassType type = declareType(id);
        MJClassType typeSuper = referenceType(idSuper);
        
        type.setSuper(typeSuper);
        
        return type;
    }   
    
    
    public static MJClassType referenceType(String id)
    {
        MJClassType type = null;
        
        type = classTypes.get(id);
        
        if (type != null) {
            return type;
        }
        
        type = unknownTypes.get(id);
        
        if (type != null) {
            return type;
        }
        
        type = new MJClassType(id);
        
        unknownTypes.put(id, type);
        
        return type;
    }
    
    
    public static MJClassType getType(String id)
    {
        MJClassType type = null;
        
        type = classTypes.get(id);
        
        if (type == null) {
            error("Error: Class type " + type + " does not exist");
        }
        
        return type;
    }
    
    
    public static void checkUnknownTypes()
    {
        if (unknownTypes.size() > 0) {
            System.out.println("Error: Found unknown types");
            String description = "Unknown types: \n";
            Iterator iterator = unknownTypes.values().iterator();
            
            while (iterator.hasNext()) {
                description += iterator.next();
                description += "\n";
            }
            
            MJTypeSystem.error(description);
        }
        
        //
        // It is illegal to add new unknown types from this point on.
        //
        unknownTypes = null;
    }
    
    
    /**
     * Checks if type2 is compatible with type 1. That is, type2 is identical
     * to type1 or is a subtype of type 1.
     */
    public static void checkType(MJType type1, MJType type2)
    {
        if (!type2.compatibleWith(type1)){
            error("Error: Incompatible types. Found: " + type2 + " Required: " + type1);
        }
    }
    
    public static void error(String msg)
    {
        System.out.println(msg);
        numErrors ++;
    }
    
    public static int getNumErrors()
    {
        return numErrors;
    }
 
}
