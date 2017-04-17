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
public class MJClass 
{
    protected String id;
    protected MJType type;
    protected MJClass superClass;
    
    private Map<String, MJVariable> members;
    private Map<String, MJMethod> methods;
    private Vector<MJMethod> methodList;
    private int methodIndex;
    
    public MJClass(String id, MJType type, MJClass superClass)
    {
        this.id = id;
        this.type = type;
        this.superClass = superClass;
        
        members = new HashMap<String, MJVariable>();
        methods = new HashMap<String, MJMethod>();
        methodList = new Vector<MJMethod>();
    }
    
    public MJClass(String id, MJType type)
    {
        this(id, type, null);
    }
    
    public void assignMemberAddresses()
    {
        int offset = 4;
        
        if (superClass != null) {
            offset = superClass.getSize();
        }
        
        Iterator iterator = members.values().iterator();
        
        while(iterator.hasNext()) {
            MJVariable var = (MJVariable) iterator.next();
            var.setAddress("ecx+" + offset);
            offset += 4;
        }
    }
    
    public String getName()
    {
        return id;
    }
    
    public int getSize()
    {
        int size = 4;
        
        if (superClass != null) {
            size = superClass.getSize();
        }
        
        size += (members.size() * 4);
        
        return size;
    }
    
    public MJMethod getFirstMethod()
    {
        MJMethod method = null;
        
        if (superClass != null) {
            method = superClass.getFirstMethod();
            
            if (method != null) {
                return method;
            }
        }
        
        if (method == null) {
            methodIndex = 0;
            method = getNextMethod();
        }
        
        return method;
    }
    
    public MJMethod getNextMethod()
    {
        MJMethod method = null;
        
        if (superClass != null) {
            method = superClass.getNextMethod();
        }
        
        if (method == null && methodList.size() > methodIndex) {
            method = methodList.elementAt(methodIndex++);
        }
        
        return method;
    }
    
    public MJVariable putMember(String id, MJType type)
    {
        MJVariable member = members.get(id);
        
        if (member != null) {
            MJTypeSystem.error("Error: Member " + id + " already declared in class " + this.id);
            return member;
        }
        
        member = new MJVariable(id, type);
        
        members.put(id, member);
        
        return member;
    }
    
    
    public MJVariable getMember(String id)
    {
        MJVariable member = members.get(id);
        
        if (member == null && superClass != null) {
            member = superClass.getMember(id);
        }
        
        return member;
    }
    
    
    public MJMethod putMethod(String id, MJType type)
    {
        MJMethod method = methods.get(id);
        
        if (method != null) {
            MJTypeSystem.error("Error Method " + id + " already declared in class " + this.id);
            return method;
        }
        
        method = new MJMethod(id, type);
        method.setLabel(MJSymbolTable.getMethodLabel(this.id, id));
        
        methods.put(id, method);
        methodList.add(method);
        
        return method;
    }
    
    
    public MJMethod getMethod(String id)
    {
        MJMethod method = methods.get(id);
        
        if (method == null && superClass != null) {
            method = superClass.getMethod(id);
        }
        
        return method;
    }
    
    
    public MJType getType()
    {
        return type;
    }
    
    
    public void setSuper(MJClass superClass)
    {
        this.superClass = superClass;
    }
    
    
    public MJClass getSuper()
    {
        return superClass;
    }
    
    
    public String toString()
    {
        Iterator iterator;
        String description = "Class " + id + "\n";
        
        description += "Member variables:\n";
        
        iterator = members.values().iterator();
        
        while (iterator.hasNext()) {
            description += iterator.next();
            description += "\n";
        }
        
        description += "Methods:\n";
        
        iterator = methods.values().iterator();
        
        while (iterator.hasNext()) {
            description += iterator.next();
            description += "\n";
        }
        
        return description;
    }
}
