package Go;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;



public class Main extends GoParserBaseListener{

    public static ArrayList<String> ans = new ArrayList<String>();
    public static ArrayList<String> block = new ArrayList<String>();
    public static Stack<String> sta = new Stack<String>();
    public static Boolean IsFor = false;
    public static Boolean InIf = false;
    public static int InBlock = 0;
    public static int now = 0;
    @Override
    public void exitPackageClause(GoParser.PackageClauseContext ctx){
//        System.out.println(ctx.packageName.getText());
    }
    @Override
    public void exitShortVarDecl(GoParser.ShortVarDeclContext ctx){
        //ans.add(ctx.getText());
        System.out.print("(");
        System.out.print(++now);
        System.out.print(") ");
        System.out.println(ctx.getText());
        //System.out.println(ctx.DECLARE_ASSIGN());
    }

    @Override
    public void enterForStmt(GoParser.ForStmtContext ctx){
        IsFor = true;
    }

    @Override
    public void exitForStmt(GoParser.ForStmtContext ctx){
        IsFor = false;
    }

    @Override
    public void enterForClause(GoParser.ForClauseContext ctx){

    }

    @Override
    public void exitForClause(GoParser.ForClauseContext ctx){

    }

    @Override
    public void enterExpression(GoParser.ExpressionContext ctx){
        //System.out.println(ctx.getChildCount());
        if(IsFor==true&&ctx.getChildCount()==3){
            System.out.print("(");
            System.out.print(++now);
            System.out.print(") ");
            System.out.println("if "+ctx.getText()+" goto ("+(now+2)+")");
            System.out.print("(");
            System.out.print(++now);
            System.out.print(") ");
            System.out.println("goto (End)");
        }
    }

    @Override
    public void exitExpression(GoParser.ExpressionContext ctx){
        //System.out.println(ctx.getText());
    }

    @Override
    public void enterIncDecStmt(GoParser.IncDecStmtContext ctx){
        //System.out.println(ctx.getText());
        /*System.out.print("(");
        System.out.print(++now);
        System.out.print(") ");*/
        if(IsFor==true) {
            if (ctx.getText().substring(ctx.getText().length() - 2).equals("++"))
                sta.push(ctx.getText().substring(0, ctx.getText().length() - 2) + "=" + ctx.getText().substring(0, ctx.getText().length() - 2) + "+1");
        }
    }

    @Override
    public void enterBlock(GoParser.BlockContext ctx) {
        InBlock++;
    }

    @Override
    public void exitBlock(GoParser.BlockContext ctx) {
        InBlock--;
    }

    public static void main(String args[]) throws Exception{
        String inputFile = "/Users/wjcwjc/Desktop/大学/大三下/项目制实践2/3/example/easy.go";
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);

        GoLexer lexer = new GoLexer(new ANTLRInputStream(is));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GoParser parser = new GoParser(tokens);
        parser.setBuildParseTree(true); // tell ANTLR to build a parse tree
        ParseTree tree = parser.sourceFile();


        ParseTreeWalker walker = new ParseTreeWalker();
        Main main = new Main();
        walker.walk(main, tree);

        /*for(int i = 0;i < ans.size();i++){
            System.out.println(ans.get(i));
        }*/
    }
}
