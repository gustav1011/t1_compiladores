import java.io.InputStreamReader;
%%

%public
%class MeuLexico
%integer
%unicode
%line

%{

// Identificadores e números
public static int IDENTIFIER        = 257;
public static int INTEGER_LITERAL   = 258;

// Palavras reservadas
public static int IF        = 259;
public static int ELSE      = 260;
public static int PUBLIC    = 261;
public static int PRIVATE   = 262;
public static int STATIC    = 263;
public static int VOID      = 264;
public static int CLASS     = 265;
public static int EXTENDS   = 266;
public static int RETURN    = 267;
public static int INT       = 268;
public static int BOOLEAN   = 269;
public static int WHILE     = 270;
public static int TRUE      = 271;
public static int FALSE     = 272;
public static int THIS      = 273;
public static int NEW       = 274;
public static int STRING    = 275;
public static int MAIN      = 276;
public static int PRINT     = 277; // System.out.println

// Operadores
public static int AND       = 278; // &&
public static int LT        = 279; // <
public static int PLUS      = 280; // +
public static int MINUS     = 281; // -
public static int TIMES     = 282; // *
public static int ASSIGN    = 283; // =
public static int NOT       = 284; // !
public static int DOT       = 285; // .

// Delimitadores
public static int LBRACE    = 286; // {
public static int RBRACE    = 287; // }
public static int LPAREN    = 288; // (
public static int RPAREN    = 289; // )
public static int LBRACKET  = 290; // [
public static int RBRACKET  = 291; // ]
public static int SEMICOLON = 292; // ;
public static int COMMA     = 293; // ,

/**
 * Método main para teste, tirado de exemplos anteriores de analisador lexico
 */
public static void main(String argv[]) {
    MeuLexico scanner;
    if (argv.length == 0) {
        try {
            scanner = new MeuLexico(new InputStreamReader(System.in));
            while (!scanner.zzAtEOF)
                System.out.println("token: " + scanner.yylex() + "\t<" + scanner.yytext() + ">");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        for (int i = 0; i < argv.length; i++) {
            try {
                scanner = new MeuLexico(new java.io.FileReader(argv[i]));
                while (!scanner.zzAtEOF)
                    System.out.println("token: " + scanner.yylex() + "\t<" + scanner.yytext() + ">");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

%}

DIGIT = [0-9]
ID_START = [a-zA-Z_]
ID_PART  = [a-zA-Z0-9_]
WHITESPACE = [ \t]
LineTerminator = \r|\n|\r\n

%%

// Palavras reservadas
"if"        { return IF; }
"else"      { return ELSE; }
"public"    { return PUBLIC; }
"private"   { return PRIVATE; }
"static"    { return STATIC; }
"void"      { return VOID; }
"class"     { return CLASS; }
"extends"   { return EXTENDS; }
"return"    { return RETURN; }
"int"       { return INT; }
"boolean"   { return BOOLEAN; }
"while"     { return WHILE; }
"true"      { return TRUE; }
"false"     { return FALSE; }
"this"      { return THIS; }
"new"       { return NEW; }
"String"    { return STRING; }
"main"      { return MAIN; }

// Caso especial
"System.out.println" { return PRINT; }

// Operadores
"&&"       { return AND; }
"<"        { return LT; }
"+"        { return PLUS; }
"-"        { return MINUS; }
"*"        { return TIMES; }
"="        { return ASSIGN; }
"!"        { return NOT; }
"."        { return DOT; }

// Delimitadores
"{"        { return LBRACE; }
"}"        { return RBRACE; }
"("        { return LPAREN; }
")"        { return RPAREN; }
"["        { return LBRACKET; }
"]"        { return RBRACKET; }
";"        { return SEMICOLON; }
","        { return COMMA; }

// Literais e identificadores
{DIGIT}+                     { return INTEGER_LITERAL; }
{ID_START}{ID_PART}* { return IDENTIFIER; }
// Espaços e quebras
{WHITESPACE}+      { }
{LineTerminator}   { }


// Erro léxico
. { System.out.println((yyline+1) + ": caracter invalido: " + yytext()); }