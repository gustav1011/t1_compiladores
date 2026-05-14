
%{
  import java.io.*;
%}

%token CLASS PUBLIC STATIC VOID MAIN STRING EXTENDS RETURN INT BOOLEAN IF ELSE WHILE PRINT
%token AND LT PLUS MINUS TIMES ASSIGN NOT DOT
%token LBRACE RBRACE LPAREN RPAREN LBRACKET RBRACKET SEMICOLON COMMA
%token IDENTIFIER INTEGER_LITERAL TRUE FALSE THIS NEW

%left AND
%left LT
%left PLUS MINUS
%left TIMES
%right NOT NEW
%left LBRACKET DOT

%%

Goal : MainClass ClassDeclarations
     ;

MainClass : CLASS IDENTIFIER LBRACE PUBLIC STATIC VOID MAIN LPAREN STRING LBRACKET RBRACKET IDENTIFIER RPAREN LBRACE Statement RBRACE RBRACE
          ;

ClassDeclarations : ClassDeclarations ClassDeclaration
                  | /* empty */
                  ;

ClassDeclaration : CLASS IDENTIFIER ExtendsOpt LBRACE VarDeclarations MethodDeclarations RBRACE
                 ;

ExtendsOpt : EXTENDS IDENTIFIER
           | /* empty */
           ;

VarDeclarations : VarDeclarations VarDeclaration
                | /* empty */
                ;

VarDeclaration : Type IDENTIFIER SEMICOLON
               ;

MethodDeclarations : MethodDeclarations MethodDeclaration
                   | /* empty */
                   ;

MethodDeclaration : PUBLIC Type IDENTIFIER LPAREN FormalList RPAREN LBRACE VarDeclarations Statements RETURN Expression SEMICOLON RBRACE
                  ;

FormalList : FormalRest
           | /* empty */
           ;

FormalRest : Type IDENTIFIER
           | FormalRest COMMA Type IDENTIFIER
           ;

Statements : Statements Statement
           | /* empty */
           ;

Type : INT LBRACKET RBRACKET
     | BOOLEAN
     | INT
     | IDENTIFIER
     ;

Statement : LBRACE Statements RBRACE
          | IF LPAREN Expression RPAREN Statement ELSE Statement
          | WHILE LPAREN Expression RPAREN Statement
          | PRINT LPAREN Expression RPAREN SEMICOLON
          | IDENTIFIER ASSIGN Expression SEMICOLON
          | IDENTIFIER LBRACKET Expression RBRACKET ASSIGN Expression SEMICOLON
          ;

Expression : Expression AND Expression
           | Expression LT Expression
           | Expression PLUS Expression
           | Expression MINUS Expression
           | Expression TIMES Expression
           | Expression LBRACKET Expression RBRACKET
           | Expression DOT IDENTIFIER
           | Expression DOT IDENTIFIER LPAREN ExpList RPAREN
           | INTEGER_LITERAL
           | TRUE
           | FALSE
           | IDENTIFIER
           | THIS
           | NEW INT LBRACKET Expression RBRACKET
           | NEW IDENTIFIER LPAREN RPAREN
           | NOT Expression
           | LPAREN Expression RPAREN
           ;

ExpList : ExpRest
        | /* empty */
        ;

ExpRest : Expression
        | ExpRest COMMA Expression
        ;

%%

  private MeuLexico lexer;

  private int yylex () {
    int yyl = -1;
    try {
      yyl = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e.getMessage());
    }
    return yyl;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
    System.err.println ("Line: " + (lexer.getLine() + 1));
    System.err.println ("Text: " + lexer.getText());
  }


  public Parser(Reader r) {
    lexer = new MeuLexico(r);
  }


  public static void main(String args[]) throws IOException {
    Parser yyparser = new Parser(new FileReader(args[0]));
    if (yyparser.yyparse() == 0) {
        System.out.println("Análise sintática concluida com sucesso!");
    } else {
        System.out.println("Falhou!!!");
    }
  }
