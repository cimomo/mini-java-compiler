/*
 * Lexical definitions for MiniJava compiler.
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

package parser;
import java_cup.runtime.*;

%%

%public
%class MiniJavaLexer
%unicode
%cup
%line
%column

%{
    private Symbol symbol(int type)
    {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value)
    {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

/**
 * comments
 */
Comment = {MultiLineComment} | {SingleLineComment}
MultiLineComment = "/*" ([^*]* | ("*"+[^/*]))* "*"+ "/"
SingleLineComment = "//" {InputCharacter}* {LineTerminator}

/**
 * Identifiers
 */
Identifier = {IdPrefix} {IdSuffix}*
IdPrefix = [_A-Za-z]
IdSuffix = [_A-Za-z0-9]

/**
 * Literals
 */
DecIntegerLiteral = 0 | [1-9][0-9]*

%%

<YYINITIAL> {
    /**
     * Comments & white spaces
     */
    {Comment} { /* ignore */ }
    {WhiteSpace} { /* ignore */ }

    /**
     * keywords
     */
    "public" { return symbol(sym.PUBLIC); }
    "static" { return symbol(sym.STATIC); }
    "void" { return symbol(sym.VOID); }
    "main" { return symbol(sym.MAIN); }
    "class" { return symbol(sym.CLASS); }
    "extends" { return symbol(sym.EXTENDS); }
    "this" { return symbol(sym.THIS); }
    "super" { return symbol(sym.SUPER); }
    "return" {return symbol(sym.RETURN); }
    "new" { return symbol(sym.NEW); }
    "if" { return symbol(sym.IF); }
    "else" { return symbol(sym.ELSE); }
    "while" { return symbol(sym.WHILE); }
    "boolean" { return symbol(sym.BOOLEAN); }
    "int" { return symbol(sym.INT); }
    "true" { return symbol(sym.TRUE); }
    "false" { return symbol(sym.FALSE); }
    "length" { return symbol(sym.LENGTH); }
    "String" { return symbol(sym.STRING); }
    "System.out.println" { return symbol(sym.PRINTLN); }
    "instanceof" { return symbol(sym.INSTANCEOF); }

    /**
     * Operators & special symbols
     */
    "!" { return symbol(sym.BANG); }
    "=" { return symbol(sym.EQ); }
    "<" { return symbol(sym.LT); }
    ">" { return symbol(sym.GT); }
    "&&" { return symbol(sym.AND); }
    "+" { return symbol(sym.PLUS); }
    "-" { return symbol(sym.MINUS); }
    "*" { return symbol(sym.STAR); }
    "(" { return symbol(sym.LPAREN); }
    ")" { return symbol(sym.RPAREN); }
    "[" { return symbol(sym.LBRACK); }
    "]" { return symbol(sym.RBRACK); }
    "{" { return symbol(sym.LBRACE); }
    "}" { return symbol(sym.RBRACE); }
    "." { return symbol(sym.DOT); }
    "," { return symbol(sym.COMMA); }
    ";" { return symbol(sym.SEMI); }

    /**
     * Literals
     */
    {DecIntegerLiteral} { return symbol(sym.INTEGER, new Integer(yytext())); }

    /**
     * Identifiers
     */
    {Identifier} { return symbol(sym.ID, yytext()); }
}

/**
 * On error, print a warning and move on
 */
.|\n {
    System.out.println();
    System.out.println("Warning: Illegal character <"+
                       yytext()+
                       "> at line: "+
                       (yyline+1)+
                       ", column: "+
                       (yycolumn+1));
}


