import java.io.InputStreamReader;
%%

%public
%class MeuLexico
%integer
%unicode
%line

%{
/**
 * Método main para teste, tirado de exemplos anteriores de analisador lexico
 */
public int getLine() { return yyline; }
public String getText() { return yytext(); }

public static void main(String argv[]) {
    MeuLexico scanner;
    if (argv.length == 0) {
        try {
            scanner = new MeuLexico(new InputStreamReader(System.in));
            while (true) {
                int token = scanner.yylex();
                if (token == 0) break;
                System.out.println("token: " + token + "\t<" + scanner.yytext() + ">");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        for (int i = 0; i < argv.length; i++) {
            try {
                scanner = new MeuLexico(new java.io.FileReader(argv[i]));
                while (true) {
                    int token = scanner.yylex();
                    if (token == 0) break;
                    System.out.println("token: " + token + "\t<" + scanner.yytext() + ">");
                }
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
"if"        { return Parser.IF; }
"else"      { return Parser.ELSE; }
"public"    { return Parser.PUBLIC; }
"static"    { return Parser.STATIC; }
"void"      { return Parser.VOID; }
"class"     { return Parser.CLASS; }
"extends"   { return Parser.EXTENDS; }
"return"    { return Parser.RETURN; }
"int"       { return Parser.INT; }
"boolean"   { return Parser.BOOLEAN; }
"while"     { return Parser.WHILE; }
"true"      { return Parser.TRUE; }
"false"     { return Parser.FALSE; }
"this"      { return Parser.THIS; }
"new"       { return Parser.NEW; }
"String"    { return Parser.STRING; }
"main"      { return Parser.MAIN; }

// Caso especial
"System.out.println" { return Parser.PRINT; }

// Operadores
"&&"       { return Parser.AND; }
"<"        { return Parser.LT; }
"+"        { return Parser.PLUS; }
"-"        { return Parser.MINUS; }
"*"        { return Parser.TIMES; }
"="        { return Parser.ASSIGN; }
"!"        { return Parser.NOT; }
"."        { return Parser.DOT; }

// Delimitadores
"{"        { return Parser.LBRACE; }
"}"        { return Parser.RBRACE; }
"("        { return Parser.LPAREN; }
")"        { return Parser.RPAREN; }
"["        { return Parser.LBRACKET; }
"]"        { return Parser.RBRACKET; }
";"        { return Parser.SEMICOLON; }
","        { return Parser.COMMA; }

// Literais e identificadores
{DIGIT}+                     { return Parser.INTEGER_LITERAL; }
{ID_START}{ID_PART}* { return Parser.IDENTIFIER; }
// Espaços e quebras
{WHITESPACE}+      { }
{LineTerminator}   { }


// Erro léxico
. { System.out.println((yyline+1) + ": caracter invalido: " + yytext()); }