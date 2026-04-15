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

    // MainClass
    private void MainClass() {
        verifica(MeuLexico.CLASS);
        verifica(MeuLexico.IDENTIFIER);
        verifica('{');

        verifica(MeuLexico.PUBLIC);
        verifica(MeuLexico.STATIC);
        verifica(MeuLexico.VOID);
        verifica(MeuLexico.MAIN);

        verifica('(');
        verifica(MeuLexico.STRING);
        verifica('[');
        verifica(']');
        verifica(MeuLexico.IDENTIFIER);
        verifica(')');

        verifica('{');

        //Statement(); n implementei ainda esse

        verifica('}');
        verifica('}');
    }

      private void VarDeclaration() {
        Type();
        verifica(MeuLexico.IDENTIFIER);
        verifica(';');
    }

    private void IdentifierMetodo(){
        verifica(MeuLexico.IDENTIFIER);
    }

    private void Type() {
        if (laToken == MeuLexico.INT) {
            verifica(MeuLexico.INT);

            if (laToken == '[') {
                verifica('[');
                verifica(']');
            }

        } else if (laToken == MeuLexico.BOOLEAN) {
            verifica(MeuLexico.BOOLEAN);

        } else if (laToken == MeuLexico.IDENTIFIER) {
            verifica(MeuLexico.IDENTIFIER);

        } else {
            yyerror("Erro em Type");
        }
    }
}