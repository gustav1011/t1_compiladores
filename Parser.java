//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 3 "Parser.y"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short CLASS=257;
public final static short PUBLIC=258;
public final static short STATIC=259;
public final static short VOID=260;
public final static short MAIN=261;
public final static short STRING=262;
public final static short EXTENDS=263;
public final static short RETURN=264;
public final static short INT=265;
public final static short BOOLEAN=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short WHILE=269;
public final static short PRINT=270;
public final static short AND=271;
public final static short LT=272;
public final static short PLUS=273;
public final static short MINUS=274;
public final static short TIMES=275;
public final static short ASSIGN=276;
public final static short NOT=277;
public final static short DOT=278;
public final static short LBRACE=279;
public final static short RBRACE=280;
public final static short LPAREN=281;
public final static short RPAREN=282;
public final static short LBRACKET=283;
public final static short RBRACKET=284;
public final static short SEMICOLON=285;
public final static short COMMA=286;
public final static short IDENTIFIER=287;
public final static short INTEGER_LITERAL=288;
public final static short TRUE=289;
public final static short FALSE=290;
public final static short THIS=291;
public final static short NEW=292;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    4,    5,    5,    6,    6,    8,
    7,    7,   10,   11,   11,   14,   14,   12,   12,    9,
    9,    9,    9,    3,    3,    3,    3,    3,    3,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   15,   15,   16,   16,
};
final static short yylen[] = {                            2,
    2,   17,    2,    0,    7,    2,    0,    2,    0,    3,
    2,    0,   13,    1,    0,    2,    4,    2,    0,    3,
    1,    1,    1,    3,    7,    5,    5,    4,    7,    3,
    3,    3,    3,    3,    4,    3,    6,    1,    1,    1,
    1,    1,    5,    4,    2,    3,    1,    0,    1,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    4,    0,    0,    0,    0,    3,    0,    0,
    0,    0,    0,    0,    6,    9,    0,    0,    0,    0,
   21,   23,    0,    8,    0,    0,    0,    0,    5,   11,
    0,    0,   20,    0,   10,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   16,    0,    0,    0,    0,    0,
   19,    0,    0,    9,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   17,    0,    0,   41,   38,   39,   40,
   42,    0,    0,    0,    0,   24,   18,    0,    0,    2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   28,    0,    0,   46,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   26,
   27,    0,    0,    0,   44,    0,    0,   35,    0,    0,
   43,    0,    0,    0,   25,   29,   13,   37,    0,    0,
};
final static short yydgoto[] = {                          2,
    3,    5,   77,    8,   13,   18,   23,   24,   25,   30,
   42,   59,   73,   43,  123,  124,
};
final static short yysindex[] = {                      -233,
 -273,    0,    0, -252, -203, -218, -215,    0, -193, -183,
 -159, -184, -162, -136,    0,    0, -147, -258, -127, -146,
    0,    0, -257,    0, -141, -138, -137, -258,    0,    0,
 -135, -133,    0, -134,    0, -125, -132, -119, -258, -113,
 -122, -115, -118, -223,    0, -109, -258, -104, -103, -101,
    0, -221,  -98,    0, -106, -169, -169, -169, -228, -169,
 -169,  -97, -258,    0, -169, -169,    0,    0,    0,    0,
    0, -261,  -26,  -13,    1,    0,    0, -114,  -69,    0,
 -251, -225,   14,  -91,  -96, -169, -169, -169, -169, -169,
  -94, -223, -169, -223,  -90,    0,  -80, -169,    0, -169,
  -85,   34, -253, -266, -266, -225,  -83,  -68,  -54,    0,
    0, -169,  -99,  -40,    0, -169, -223,    0,  -84,  -73,
    0,   27,  -74,  -76,    0,    0,    0,    0, -169,   27,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  211,    0,    0,    0,    0,  -67,
    0,    0,    0,    0,    0,    0,    0, -243,    0,  -71,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -60,    0,
    0,    0,  -59,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -131,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -180,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -130, -201, -158, -142, -175, -196,    0,    0,    0,
    0,    0,    0,    0,    0,  -57,    0,    0,    0,    0,
    0, -219,    0,  -56,    0,    0,    0,    0,    0, -217,
};
final static short yygindex[] = {                         0,
    0,    0,  -44,    0,    0,  159,    0,    0,   21,    0,
    0,  164,  -55,    0,    0,    0,
};
final static int YYTABLESIZE=317;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   28,   74,   75,   84,   78,   79,   20,   21,   90,   82,
   83,   91,   98,    4,   12,   48,   93,   49,   50,   88,
   89,   90,   29,    1,   91,   85,    6,   51,   22,   93,
  102,  103,  104,  105,  106,   52,   12,  109,   48,    9,
   49,   50,  113,   48,  114,   49,   50,  108,   34,  110,
   51,   76,   91,    7,   60,   51,  119,   93,   52,   41,
  122,   61,   49,   52,   50,   11,   49,   55,   50,   31,
   31,   10,  125,  130,   36,   36,   36,   36,   36,   12,
   31,   36,   31,   31,   31,   36,   36,   36,   36,   36,
   45,   45,   45,   45,   45,   34,   34,   34,   34,   34,
   14,   45,   15,   45,   45,   45,   34,   65,   34,   34,
   34,   66,   32,   32,   32,   32,   16,   67,   68,   69,
   70,   71,   72,   32,   17,   32,   32,   32,   33,   33,
   33,   33,   19,   19,   26,   19,   27,   19,   19,   33,
   30,   33,   33,   33,   32,   31,   33,   19,   39,   35,
   36,   30,   37,   30,   30,   30,   86,   87,   88,   89,
   90,   38,   40,   91,   45,   44,   46,   47,   93,   54,
   96,   86,   87,   88,   89,   90,   56,   57,   91,   58,
   64,   62,   80,   93,  101,  120,   86,   87,   88,   89,
   90,  100,  107,   91,  111,  112,  115,  116,   93,  117,
  126,   86,   87,   88,   89,   90,  127,  128,   91,  129,
    1,    7,   63,   93,   97,   22,   86,   87,   88,   89,
   90,   15,   14,   91,   48,   47,   81,    0,   93,  118,
   86,   87,   88,   89,   90,    0,    0,   91,    0,    0,
    0,    0,   93,  121,   86,   87,   88,   89,   90,    0,
    0,   91,    0,    0,    0,   92,   93,   86,   87,   88,
   89,   90,    0,    0,   91,    0,    0,    0,   94,   93,
    0,   86,   87,   88,   89,   90,    0,    0,   91,    0,
    0,    0,   95,   93,   86,   87,   88,   89,   90,    0,
    0,   91,    0,    0,    0,   99,   93,   86,   87,   88,
   89,   90,    0,    0,   91,   87,   88,   89,   90,   93,
    0,   91,    0,    0,    0,    0,   93,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
  258,   57,   58,  265,   60,   61,  265,  266,  275,   65,
   66,  278,  264,  287,  258,  267,  283,  269,  270,  273,
  274,  275,  280,  257,  278,  287,  279,  279,  287,  283,
   86,   87,   88,   89,   90,  287,  280,   93,  267,  258,
  269,  270,   98,  267,  100,  269,  270,   92,   28,   94,
  279,  280,  278,  257,  276,  279,  112,  283,  287,   39,
  116,  283,  282,  287,  282,  259,  286,   47,  286,  271,
  272,  287,  117,  129,  271,  272,  273,  274,  275,  263,
  282,  278,  284,  285,  286,  282,  283,  284,  285,  286,
  271,  272,  273,  274,  275,  271,  272,  273,  274,  275,
  260,  282,  287,  284,  285,  286,  282,  277,  284,  285,
  286,  281,  271,  272,  273,  274,  279,  287,  288,  289,
  290,  291,  292,  282,  261,  284,  285,  286,  271,  272,
  273,  274,  264,  281,  262,  267,  283,  269,  270,  282,
  271,  284,  285,  286,  283,  287,  284,  279,  281,  285,
  284,  282,  287,  284,  285,  286,  271,  272,  273,  274,
  275,  287,  282,  278,  287,  279,  282,  286,  283,  279,
  285,  271,  272,  273,  274,  275,  281,  281,  278,  281,
  287,  280,  280,  283,  281,  285,  271,  272,  273,  274,
  275,  283,  287,  278,  285,  276,  282,  281,  283,  268,
  285,  271,  272,  273,  274,  275,  280,  282,  278,  286,
    0,  279,   54,  283,  284,  287,  271,  272,  273,  274,
  275,  282,  282,  278,  282,  282,   63,   -1,  283,  284,
  271,  272,  273,  274,  275,   -1,   -1,  278,   -1,   -1,
   -1,   -1,  283,  284,  271,  272,  273,  274,  275,   -1,
   -1,  278,   -1,   -1,   -1,  282,  283,  271,  272,  273,
  274,  275,   -1,   -1,  278,   -1,   -1,   -1,  282,  283,
   -1,  271,  272,  273,  274,  275,   -1,   -1,  278,   -1,
   -1,   -1,  282,  283,  271,  272,  273,  274,  275,   -1,
   -1,  278,   -1,   -1,   -1,  282,  283,  271,  272,  273,
  274,  275,   -1,   -1,  278,  272,  273,  274,  275,  283,
   -1,  278,   -1,   -1,   -1,   -1,  283,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=292;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"CLASS","PUBLIC","STATIC","VOID","MAIN","STRING","EXTENDS",
"RETURN","INT","BOOLEAN","IF","ELSE","WHILE","PRINT","AND","LT","PLUS","MINUS",
"TIMES","ASSIGN","NOT","DOT","LBRACE","RBRACE","LPAREN","RPAREN","LBRACKET",
"RBRACKET","SEMICOLON","COMMA","IDENTIFIER","INTEGER_LITERAL","TRUE","FALSE",
"THIS","NEW",
};
final static String yyrule[] = {
"$accept : Goal",
"Goal : MainClass ClassDeclarations",
"MainClass : CLASS IDENTIFIER LBRACE PUBLIC STATIC VOID MAIN LPAREN STRING LBRACKET RBRACKET IDENTIFIER RPAREN LBRACE Statement RBRACE RBRACE",
"ClassDeclarations : ClassDeclarations ClassDeclaration",
"ClassDeclarations :",
"ClassDeclaration : CLASS IDENTIFIER ExtendsOpt LBRACE VarDeclarations MethodDeclarations RBRACE",
"ExtendsOpt : EXTENDS IDENTIFIER",
"ExtendsOpt :",
"VarDeclarations : VarDeclarations VarDeclaration",
"VarDeclarations :",
"VarDeclaration : Type IDENTIFIER SEMICOLON",
"MethodDeclarations : MethodDeclarations MethodDeclaration",
"MethodDeclarations :",
"MethodDeclaration : PUBLIC Type IDENTIFIER LPAREN FormalList RPAREN LBRACE VarDeclarations Statements RETURN Expression SEMICOLON RBRACE",
"FormalList : FormalRest",
"FormalList :",
"FormalRest : Type IDENTIFIER",
"FormalRest : FormalRest COMMA Type IDENTIFIER",
"Statements : Statements Statement",
"Statements :",
"Type : INT LBRACKET RBRACKET",
"Type : BOOLEAN",
"Type : INT",
"Type : IDENTIFIER",
"Statement : LBRACE Statements RBRACE",
"Statement : IF LPAREN Expression RPAREN Statement ELSE Statement",
"Statement : WHILE LPAREN Expression RPAREN Statement",
"Statement : PRINT LPAREN Expression RPAREN SEMICOLON",
"Statement : IDENTIFIER ASSIGN Expression SEMICOLON",
"Statement : IDENTIFIER LBRACKET Expression RBRACKET ASSIGN Expression SEMICOLON",
"Expression : Expression AND Expression",
"Expression : Expression LT Expression",
"Expression : Expression PLUS Expression",
"Expression : Expression MINUS Expression",
"Expression : Expression TIMES Expression",
"Expression : Expression LBRACKET Expression RBRACKET",
"Expression : Expression DOT IDENTIFIER",
"Expression : Expression DOT IDENTIFIER LPAREN ExpList RPAREN",
"Expression : INTEGER_LITERAL",
"Expression : TRUE",
"Expression : FALSE",
"Expression : IDENTIFIER",
"Expression : THIS",
"Expression : NEW INT LBRACKET Expression RBRACKET",
"Expression : NEW IDENTIFIER LPAREN RPAREN",
"Expression : NOT Expression",
"Expression : LPAREN Expression RPAREN",
"ExpList : ExpRest",
"ExpList :",
"ExpRest : Expression",
"ExpRest : ExpRest COMMA Expression",
};

//#line 105 "Parser.y"

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
//#line 384 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
