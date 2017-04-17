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
public class MJSymbolTable 
{
    private MJClass currentClass;
    private MJMethod currentMethod;
    
    private Map<String, MJClass> classes;
    private Map<String, MJClass> unknownClasses;
    private Vector<MJClass> classList;
    private int classIndex;
    
    public MJSymbolTable()
    {
        this.currentClass = null;
        this.currentMethod = null;
        
        this.classes = new HashMap<String, MJClass>();
        this.unknownClasses = new HashMap<String, MJClass>();
        this.classList = new Vector<MJClass>();
    }
    
    public void assignMemberAddresses()
    {
        Iterator iterator = classes.values().iterator();
        
        while (iterator.hasNext()) {
            MJClass cls = (MJClass) iterator.next();
            cls.assignMemberAddresses();
        }
    }
    
    public MJClass getFirstClass()
    {
        classIndex = 0;
        return getNextClass();
    }
    
    public MJClass getNextClass()
    {
        MJClass cls = null;
        
        if (classList.size() > classIndex) {
            cls = classList.elementAt(classIndex++);
        }
        
        return cls;
    }
    
    
    public void enterScope(MJClass cls)
    {
        assert(currentClass == null);
        
        currentClass = cls;
    }
    
    
    public void enterScope(MJMethod method)
    {
        assert(currentMethod == null);
        
        currentMethod = method;
    }
    
    
    public void exitScope()
    {
        if (currentMethod != null) {
            currentMethod = null;
        
        } else {
            assert(currentClass != null);
            currentClass = null;
        }
    }
    
    
    public MJClass putClass(String id, MJType type)
    {
        MJClass cls = classes.get(id);
        
        if (cls != null) {
            MJTypeSystem.error("Error: Class " + id + " already declared");
            return cls;
        }
        
        cls = unknownClasses.remove(id);
        
        if (cls == null) {        
            cls = new MJClass(id, type);
        }
        
        classes.put(id, cls);
        classList.add(cls);
        
        return cls;
    }
    
    
    public MJClass putClass(String id, MJType type, String idSuper, MJType typeSuper)
    {
        MJClass clsSuper = referenceClass(idSuper, typeSuper);
        MJClass cls = putClass(id, type);
        
        cls.setSuper(clsSuper);
        
        return cls;
    }
        
    
    private MJClass referenceClass(String id, MJType type)
    {
        MJClass cls = classes.get(id);
        
        if (cls != null) {
            assert(cls.getType() == type);
            return cls;
        }
        
        cls = unknownClasses.get(id);
        
        if (cls != null) {
            assert(cls.getType() == type);
            return cls;
        }
        
        cls = new MJClass(id, type);
        
        unknownClasses.put(id, cls);
        
        return cls;
    }
    
    
    public MJClass getClass(String id)
    {
        MJClass cls = classes.get(id);
        
        if (cls == null) {
            MJTypeSystem.error("Error: Class " + id + " not found");
        }
        
        return cls;
    }
    
    
    public MJClass getThis()
    {
        assert(currentClass != null);
        
        return currentClass;
    }
    
    
    public MJClass getSuper()
    {
        assert(currentClass != null);
        
        MJClass superClass = currentClass.getSuper();
        
        if (superClass == null) {
            MJTypeSystem.error("Error: Class " + currentClass.getName() + " has no super class.");
        }
        
        return superClass;
    }
    
    
    public MJMethod putMethod(String id, MJType type)
    {
        assert(currentClass != null);
        
        return currentClass.putMethod(id, type);
    }
    
    
    public MJMethod getMethod(String id)
    {
        assert(currentClass != null);
        
        MJMethod method = currentClass.getMethod(id);
        
        if (method == null) {
            MJTypeSystem.error("Error: method " + id + " not found in class " + currentClass.id);
        }
        
        return method;
    }
    
    
    public MJMethod getMethod(String className, String id)
    {
        MJClass cls = getClass(className);
        
        MJMethod method = cls.getMethod(id);
        
        if (method == null) {
            MJTypeSystem.error("Error: method " + id + " not found in class " + className);
        }
        
        return method;
    }
    
    
    public MJVariable putVariable(String id, MJType type)
    {
        MJVariable variable;
        
        if (currentMethod != null) {
            variable = currentMethod.putLocal(id, type);
        
        } else {
            assert(currentClass != null);
            variable = currentClass.putMember(id, type);
        }
        
        return variable;
    }
    
    
    public MJVariable getVariable(String id)
    {
        MJVariable variable = null;
        
        if (currentMethod != null) {
            variable = currentMethod.getLocal(id);
            
            if (variable == null) {
                assert(currentClass != null);
                variable = currentClass.getMember(id);
            }
            
        } else {
            assert(currentClass != null);
            variable = currentClass.getMember(id);
        }
        
        if (variable == null) {
            MJTypeSystem.error("Error: variable " + id + " not found");
        }
        
        return variable;
    }
    
    
    public MJVariable addParameter(String id, MJType type)
    {
        assert(currentClass != null);
        assert(currentMethod != null);
        
        return currentMethod.addParameter(id, type);
    }
    
    
    public void checkUnknownClasses()
    {
        if (unknownClasses.size() > 0) {
            String description = "Unknown classes: \n";
            Iterator iterator = unknownClasses.values().iterator();
            
            while (iterator.hasNext()) {
                description += iterator.next();
                description += "\n";
            }
            
            MJTypeSystem.error(description);
        }
        
        //
        // It is illegal to add new unknown classes from this point on.
        //
        unknownClasses = null;
    }
    
    
    public String toString()
    {
        String description = "Program:\n";
        
        Iterator iterator = classes.values().iterator();
        
        while (iterator.hasNext()) {
            description += iterator.next();
            description += "\n";
        }
        
        return description;
    }
    
    public static String getClassLabel(String clsName)
    {
        return clsName + "$$";
    }
    
    public static String getMethodLabel(String clsName, String mtdName)
    {
        return clsName + "$" + mtdName;
    }
}




