/**
 *
 * Copyright (c) Kai Chen (kchen@cs.washington.edu)
 *
 * Unittest for MiniJava Lexer/Parser.
 *
 */

package main;
        
import java_cup.runtime.*;
import parser.*;
import syntaxtree.*;
import visitor.*;
import typesystem.*;
import symboltable.*;
import java.io.*;

public class MiniJavaTest
{
    public static void main(String args[]) throws Exception
    {
        if (args.length == 0) {
            System.out.println("Missing file name.");
            System.exit(1);
        }
        
        String inFileName = args[0];
        String outFileName = getOutputFileName(inFileName);
        
        if (outFileName == null) {
            System.out.println(inFileName + " is not a valid java sournce file");
            System.exit(1);
        }
        
        MiniJavaLexer scanner = new MiniJavaLexer(new BufferedReader(new FileReader(new File(inFileName))));
        MiniJavaParser parser = new MiniJavaParser(scanner);
        MJSymbolTable symbolTable = new MJSymbolTable();

        Symbol result = parser.parse();
        
        if (ErrorStmt.numErrors > 0) {
            System.out.println("Number of syntax errors: " + ErrorStmt.numErrors);
            System.exit(1);
        }
        
        Program root = (Program) result.value;
        
        // root.accept(new PrettyPrintVisitor());
        
        SymbolTableVisitor stv = new SymbolTableVisitor(symbolTable);
        root.accept(stv);
        
        MJTypeSystem.checkUnknownTypes();
        symbolTable.checkUnknownClasses();
        
        TypeCheckVisitor tcv = new TypeCheckVisitor(symbolTable);
        root.accept(tcv);
        
        int nErrors = MJTypeSystem.getNumErrors();
        if (nErrors > 0) {
            System.out.println("Number of errors: " + nErrors);
            System.exit(1);
        }
        
        CodeGenVisitor cgv = new CodeGenVisitor(symbolTable, outFileName);
        root.accept(cgv);
    }
    
    private static String getOutputFileName(String inputFileName)
    {
        int iDot = inputFileName.indexOf(".java");
        
        if (iDot == -1) {
            return null;
        }
        
        return inputFileName.substring(0, iDot) + ".asm";
    }

    public static void printSymbol(Symbol symbol)
    {
        String Name;

        switch (symbol.sym) {
            case sym.PUBLIC:
                Name = "public";
                break;
            case sym.STATIC:
                Name = "static";
                break;
            case sym.VOID:
                Name = "void";
                break;
            case sym.MAIN:
                Name = "main";
                break;
            case sym.CLASS:
                Name = "class";
                break;
            case sym.EXTENDS:
                Name = "extends";
                break;
            case sym.THIS:
                Name = "this";
                break;
            case sym.RETURN:
                Name = "return";
                break;
            case sym.NEW:
                Name = "new";
                break;
            case sym.IF:
                Name = "if";
                break;
            case sym.ELSE:
                Name = "else";
                break;
            case sym.WHILE:
                Name = "while";
                break;
            case sym.BOOLEAN:
                Name = "boolean";
                break;
            case sym.INT:
                Name = "int";
                break;
            case sym.TRUE:
                Name = "true";
                break;
            case sym.FALSE:
                Name = "false";
                break;
            case sym.LENGTH:
                Name = "length";
                break;
            case sym.STRING:
                Name = "String";
                break;
            case sym.PRINTLN:
                Name = "System.out.println";
                break;
            case sym.BANG:
                Name = "!";
                break;
            case sym.EQ:
                Name = "=";
                break;
            case sym.LT:
                Name = "<";
                break;
            case sym.AND:
                Name = "&&";
                break;
            case sym.PLUS:
                Name = "+";
                break;
            case sym.MINUS:
                Name = "-";
                break;
            case sym.STAR:
                Name = "*";
                break;
            case sym.LPAREN:
                Name = "(";
                break;
            case sym.RPAREN:
                Name = ")";
                break;
            case sym.LBRACK:
                Name = "[";
                break;
            case sym.RBRACK:
                Name = "]";
                break;
            case sym.LBRACE:
                Name = "{";
                break;
            case sym.RBRACE:
                Name = "}";
                break;
            case sym.DOT:
                Name = ".";
                break;
            case sym.COMMA:
                Name = ",";
                break;
            case sym.SEMI:
                Name = ";";
                break;
            case sym.INTEGER:
                Name = "INT:"+symbol.value.toString();
                break;
            case sym.ID:
                Name = "ID:"+symbol.value.toString();
                break;
            default:
                Name = "ERROR";
                break;
        }

        System.out.print(Name+' ');
    }
}

