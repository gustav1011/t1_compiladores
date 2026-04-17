import java.io.*;

public class meuASDR {

    private MeuLexico lexer;
    private static int laToken;
    private boolean debug;

    public meuASDR(Reader r) {
        lexer = new MeuLexico(r);
    }

        // LÉXICO
     private int yylex() {
       int retVal = -1;
       try {
           retVal = lexer.yylex(); //le a entrada do arquivo e retorna um token
       } catch (IOException e) {
           System.err.println("IO Error:" + e);
          }
       return retVal; //retorna o token para o Parser 
    }    

    // MATCH
    private void verifica(int expected) {
        if (laToken == expected) {
            laToken = yylex();
        } else {
            yyerror("Esperado: " + expected + " encontrado: " + lexer.yytext());
        }
    }

    // error
    public void yyerror (String error) {
        System.err.println("Erro: " + error);
        System.err.println("Entrada rejeitada");
        System.out.println("\n\nFalhou!!!");
        System.exit(1);
        
    }

    // PONTO DE ENTRADA - Goal
    public void Goal() {
        laToken = yylex();
        MainClass();
        while (laToken == MeuLexico.CLASS) {
            ClassDeclaration();
        }
        if (laToken != MeuLexico.YYEOF) {
            yyerror("Esperado EOF ao final do programa");
        }
    }

    // MainClass
    private void MainClass() {
        verifica(MeuLexico.CLASS);
        verifica(MeuLexico.IDENTIFIER);
        verifica(MeuLexico.LBRACE);

        verifica(MeuLexico.PUBLIC);
        verifica(MeuLexico.STATIC);
        verifica(MeuLexico.VOID);
        verifica(MeuLexico.MAIN);

        verifica(MeuLexico.LPAREN);
        verifica(MeuLexico.STRING);
        verifica(MeuLexico.LBRACKET);
        verifica(MeuLexico.RBRACKET);
        verifica(MeuLexico.IDENTIFIER);
        verifica(MeuLexico.RPAREN);

        verifica(MeuLexico.LBRACE);
        Statement();
        verifica(MeuLexico.RBRACE);
        verifica(MeuLexico.RBRACE);
    }

    // ClassDeclaration
    private void ClassDeclaration() {
        verifica(MeuLexico.CLASS);
        verifica(MeuLexico.IDENTIFIER);
        
        // Verifica "extends" opcional
        if (laToken == MeuLexico.EXTENDS) {
            verifica(MeuLexico.EXTENDS);
            verifica(MeuLexico.IDENTIFIER);
        }
        
        verifica(MeuLexico.LBRACE);
        
        // VarDeclarations
        while (laToken == MeuLexico.INT || laToken == MeuLexico.BOOLEAN || 
               laToken == MeuLexico.IDENTIFIER) {
            // Lookahead para diferenciar VarDeclaration de MethodDeclaration
            if (laToken == MeuLexico.PUBLIC) {
                break;
            }
            VarDeclaration();
        }
        
        // MethodDeclarations
        while (laToken == MeuLexico.PUBLIC) {
            MethodDeclaration();
        }
        
        verifica(MeuLexico.RBRACE);
    }

    // MethodDeclaration
    private void MethodDeclaration() {
        verifica(MeuLexico.PUBLIC);
        Type();
        verifica(MeuLexico.IDENTIFIER);
        verifica(MeuLexico.LPAREN);
        
        // Parâmetros (zero ou mais)
        if (laToken == MeuLexico.INT || laToken == MeuLexico.BOOLEAN || 
            laToken == MeuLexico.IDENTIFIER) {
            Type();
            verifica(MeuLexico.IDENTIFIER);
            
            while (laToken == MeuLexico.COMMA) {
                verifica(MeuLexico.COMMA);
                Type();
                verifica(MeuLexico.IDENTIFIER);
            }
        }
        
        verifica(MeuLexico.RPAREN);
        verifica(MeuLexico.LBRACE);
        
        // VarDeclarations
        while (laToken == MeuLexico.INT || laToken == MeuLexico.BOOLEAN || 
               laToken == MeuLexico.IDENTIFIER) {
            VarDeclaration();
        }
        
        // Statements
        while (laToken == MeuLexico.LBRACE || laToken == MeuLexico.IF || laToken == MeuLexico.WHILE || 
               laToken == MeuLexico.PRINT || laToken == MeuLexico.IDENTIFIER) {
            Statement();
        }
        
        verifica(MeuLexico.RETURN);
        Expression();
        verifica(MeuLexico.SEMICOLON);
        verifica(MeuLexico.RBRACE);
    }

    private void VarDeclaration() {
        Type();
        verifica(MeuLexico.IDENTIFIER);
        verifica(MeuLexico.SEMICOLON);
    }

    private void IdentifierMetodo(){
        verifica(MeuLexico.IDENTIFIER);
    }

    private void Type() {
        if (laToken == MeuLexico.INT) {
            verifica(MeuLexico.INT);

            if (laToken == MeuLexico.LBRACKET) {
                verifica(MeuLexico.LBRACKET);
                verifica(MeuLexico.RBRACKET);
            }

        } else if (laToken == MeuLexico.BOOLEAN) {
            verifica(MeuLexico.BOOLEAN);

        } else if (laToken == MeuLexico.IDENTIFIER) {
            verifica(MeuLexico.IDENTIFIER);

        } else {
            yyerror("Erro em Type");
        }
    }

    // Statement
    private void Statement() {
        if (laToken == MeuLexico.LBRACE) {
            // Bloco de statements
            verifica(MeuLexico.LBRACE);
            while (laToken == MeuLexico.LBRACE || laToken == MeuLexico.IF || laToken == MeuLexico.WHILE || 
                   laToken == MeuLexico.PRINT || laToken == MeuLexico.IDENTIFIER) {
                Statement();
            }
            verifica(MeuLexico.RBRACE);
        } else if (laToken == MeuLexico.IF) {
            // if (Expression) Statement else Statement
            verifica(MeuLexico.IF);
            verifica(MeuLexico.LPAREN);
            Expression();
            verifica(MeuLexico.RPAREN);
            Statement();
            verifica(MeuLexico.ELSE);
            Statement();
        } else if (laToken == MeuLexico.WHILE) {
            // while (Expression) Statement
            verifica(MeuLexico.WHILE);
            verifica(MeuLexico.LPAREN);
            Expression();
            verifica(MeuLexico.RPAREN);
            Statement();
        } else if (laToken == MeuLexico.PRINT) {
            // System.out.println (Expression) ;
            verifica(MeuLexico.PRINT);
            verifica(MeuLexico.LPAREN);
            Expression();
            verifica(MeuLexico.RPAREN);
            verifica(MeuLexico.SEMICOLON);
        } else if (laToken == MeuLexico.IDENTIFIER) {
            // Identifier = Expression; ou Identifier [ Expression ] = Expression;
            verifica(MeuLexico.IDENTIFIER);
            
            if (laToken == MeuLexico.LBRACKET) {
                // Array assignment
                verifica(MeuLexico.LBRACKET);
                Expression();
                verifica(MeuLexico.RBRACKET);
                verifica(MeuLexico.ASSIGN);
                Expression();
                verifica(MeuLexico.SEMICOLON);
            } else {
                // Simple assignment
                verifica(MeuLexico.ASSIGN);
                Expression();
                verifica(MeuLexico.SEMICOLON);
            }
        } else {
            yyerror("Erro em Statement: token inesperado " + laToken);
        }
    }

    // Expression com precedência de operadores
    // Precedência (da mais alta para mais baixa):
    // 1. Primary (literals, identifiers, parênteses)
    // 2. Unária (!, new, .)
    // 3. Multiplicação (*, /)
    // 4. Adição (+, -)
    // 5. Relacional (<)
    // 6. Lógica (&&)
    
    private void Expression() {
        ExpressionLogicAnd();
    }

    // Expression AND (&& tem menor precedência)
    private void ExpressionLogicAnd() {
        ExpressionRelacional();
        while (laToken == MeuLexico.AND) {
            verifica(MeuLexico.AND);
            ExpressionRelacional();
        }
    }

    // Expression Relacional (< tem precedência maior que &&)
    private void ExpressionRelacional() {
        ExpressionAditiva();
        while (laToken == MeuLexico.LT) {
            verifica(MeuLexico.LT);
            ExpressionAditiva();
        }
    }

    // Expression Aditiva (+ e - têm precedência maior que <)
    private void ExpressionAditiva() {
        ExpressionMultiplicativa();
        while (laToken == MeuLexico.PLUS || laToken == MeuLexico.MINUS) {
            if (laToken == MeuLexico.PLUS) {
                verifica(MeuLexico.PLUS);
            } else {
                verifica(MeuLexico.MINUS);
            }
            ExpressionMultiplicativa();
        }
    }

    // Expression Multiplicativa (* tem precedência maior)
    private void ExpressionMultiplicativa() {
        ExpressionPostfixouUnaria();
        while (laToken == MeuLexico.TIMES) {
            verifica(MeuLexico.TIMES);
            ExpressionPostfixouUnaria();
        }
    }

    // Expressões Postfix ([...], .length, .method(...))
    // e Unárias (!, new)
    private void ExpressionPostfixouUnaria() {
        ExpressionUnaria();
        
        // Postfix: [ ... ] ou . length ou . method(...)
        while (laToken == MeuLexico.LBRACKET || (laToken == MeuLexico.DOT)) {
            if (laToken == MeuLexico.LBRACKET) {
                // Array index
                verifica(MeuLexico.LBRACKET);
                Expression();
                verifica(MeuLexico.RBRACKET);
            } else if (laToken == MeuLexico.DOT) {
                verifica(MeuLexico.DOT);
                if (laToken == MeuLexico.IDENTIFIER) {
                    verifica(MeuLexico.IDENTIFIER);
                    // Verifica se é method call
                    if (laToken == MeuLexico.LPAREN) {
                        verifica(MeuLexico.LPAREN);
                        // Argumentos da chamada (zero ou mais)
                        if (laToken != MeuLexico.RPAREN) {
                            Expression();
                            while (laToken == MeuLexico.COMMA) {
                                verifica(MeuLexico.COMMA);
                                Expression();
                            }
                        }
                        verifica(MeuLexico.RPAREN);
                    }
                } else {
                    yyerror("Esperado identificador ou 'length' após '.'");
                }
            }
        }
    }

    // Expressões Unárias (!, new)
    private void ExpressionUnaria() {
        if (laToken == MeuLexico.NOT) {
            verifica(MeuLexico.NOT);
            ExpressionUnaria();
        } else if (laToken == MeuLexico.NEW) {
            verifica(MeuLexico.NEW);
            if (laToken == MeuLexico.INT) {
                verifica(MeuLexico.INT);
                verifica(MeuLexico.LBRACKET);
                Expression();
                verifica(MeuLexico.RBRACKET);
            } else if (laToken == MeuLexico.IDENTIFIER) {
                verifica(MeuLexico.IDENTIFIER);
                verifica(MeuLexico.LPAREN);
                verifica(MeuLexico.RPAREN);
            } else {
                yyerror("Erro em expressão 'new'");
            }
        } else {
            Primary();
        }
    }

    // Expressões Primárias
    private void Primary() {
        if (laToken == MeuLexico.INTEGER_LITERAL) {
            verifica(MeuLexico.INTEGER_LITERAL);
        } else if (laToken == MeuLexico.TRUE) {
            verifica(MeuLexico.TRUE);
        } else if (laToken == MeuLexico.FALSE) {
            verifica(MeuLexico.FALSE);
        } else if (laToken == MeuLexico.IDENTIFIER) {
            verifica(MeuLexico.IDENTIFIER);
        } else if (laToken == MeuLexico.THIS) {
            verifica(MeuLexico.THIS);
        } else if (laToken == MeuLexico.LPAREN) {
            verifica(MeuLexico.LPAREN);
            Expression();
            verifica(MeuLexico.RPAREN);
        } else {
            yyerror("Esperado expressão primária, encontrado: " + laToken);
        }
    }

    // Método main para testar o parser
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java meuASDR <arquivo.java>");
            System.exit(1);
        }

        try {
            Reader r = new FileReader(args[0]);
            meuASDR parser = new meuASDR(r);
            parser.Goal();
            System.out.println("Análise sintática concluida");
        } catch (IOException e) {
            System.err.println("Erro ao abrir arquivo: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            System.exit(1);
        }
    }
}