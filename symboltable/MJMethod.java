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

package symboltable;

import java.util.*;
import typesystem.*;

/**
 *
 * @author kaichen
 */
public class MJMethod 
{
    protected String id;
    protected MJType type;
    protected int offset;
    protected String label;
    
    private Vector<MJVariable> parameters;
    private Map<String, MJVariable> locals;
    
    public MJMethod(String id, MJType type)
    {
        this.id = id;
        this.type = type;
        this.offset = 0;
        
        this.parameters = new Vector<MJVariable>();
        this.locals = new HashMap<String, MJVariable>();
    }
    
    
    public void setOffset(int offset)
    {
        this.offset = offset;
    }
    
    
    public int getOffset()
    {
        return offset;
    }
    
    
    public void setLabel(String label)
    {
        this.label = label;
    }
    
    
    public String getLabel()
    {
        return label;
    }
    
    
    public MJVariable addParameter(String id, MJType type)
    {
        MJVariable parameter = new MJVariable(id, type);
        
        //
        // Verify the parameter does not already exist.
        //
        for (int i = 0; i < parameters.size(); i ++) {
            if (id.equals(parameters.elementAt(i).id)) {
                MJTypeSystem.error("Error: Parameter " + id + " already exists in method " + this.id);
            }
        }
        
        parameters.addElement(parameter);
        
        //
        // Also add to the locals hash table for quick look up.
        //
        locals.put(id, parameter);
        
        return parameter;
    }
    
    
    public MJVariable parameterAt(int i)
    {
        return parameters.elementAt(i);
    }
    
    
    public int numParameters()
    {
        return parameters.size();
    }
    
    
    public MJVariable putLocal(String id, MJType type)
    {
        MJVariable local = locals.get(id);
        
        if (local != null) {
            MJTypeSystem.error("Error: Local " + id + " already declared in method " + this.id);
            return local;
        }
        
        local = new MJVariable(id, type);
        
        locals.put(id, local);
        
        return local;
    }
    
    
    public MJVariable getLocal(String id)
    {
        MJVariable local = locals.get(id);
        
        return local;
    }
    
    
    public MJType getType()
    {
        return type;
    }
    
    
    public String getName()
    {
        return id;
    }
    
    
    public String toString()
    {
        String description = "Method (" + type + " " + id + ")\n";
        
        description += "Parameters:\n";
        
        for (int i = 0; i < parameters.size(); i ++) {
            description += parameters.elementAt(i);
            description += "\n";
        }
        
        description += "Local variables:\n";
        
        Iterator iterator = locals.values().iterator();
        
        while (iterator.hasNext()) {
            description += iterator.next();
            description += "\n";
        }
        
        return description;
    }
}
