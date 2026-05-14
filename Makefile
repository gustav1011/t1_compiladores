# Makefile para o compilador Mini-Java

JFLEX = jflex
YACC = yacc
JAVAC = javac

# Arquivos de especificação
LEXER_SPEC = asdr_lex.flex
PARSER_SPEC = Parser.y

# Arquivos gerados
LEXER_GEN = MeuLexico.java
PARSER_GEN = Parser.java ParserVal.java

# Arquivos de teste
VALID_TESTS = test1_valid.txt test2_valid.txt sample.txt
INVALID_TESTS = test1_invalid.txt test2_invalid.txt

TESTS_PATH = tests_folder/

all: compile

compile: $(LEXER_GEN) $(PARSER_GEN)
	$(JAVAC) Parser.java ParserVal.java MeuLexico.java

$(PARSER_GEN): $(PARSER_SPEC)
	$(YACC) -J $(PARSER_SPEC)

$(LEXER_GEN): $(LEXER_SPEC)
	$(JFLEX) $(LEXER_SPEC)

test: all
	@echo "---------------------------------------"
	@echo "Executando testes validos..."
	@for file in $(VALID_TESTS); do \
		echo "Testando $$file:"; \
		java Parser $(TESTS_PATH)$$file; \
		echo "---------------------------------------"; \
	done
	@echo "Executando testes invalidos (devem falhar)..."
	@for file in $(INVALID_TESTS); do \
		echo "Testando $$file:"; \
		java Parser $(TESTS_PATH)$$file || true; \
		echo "---------------------------------------"; \
	done

clean:
	rm -f *.class $(LEXER_GEN) $(PARSER_GEN) MeuLexico.java~

.PHONY: all compile test clean
