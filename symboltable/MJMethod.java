/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
