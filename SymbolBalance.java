import java.io.*;
import java.util.*;
  
public class SymbolBalance extends MyStack implements SymbolBalanceInterface, BalanceError {

    File testFile;

    // sets file path to be used in checkFile()
    public void setFile(String filename){
        testFile = new File(filename);
    }

    // reads file line by line to check the validity of the opening and closing symbols
    public BalanceError checkFile(){
        ArrayList<Character> openSymbols = new ArrayList<>(Arrays.asList('[','{','(', '\"', '\'', '*', '/'));
        ArrayList<Character> closeSymbols = new ArrayList<>(Arrays.asList(']','}',')', '\"', '\'', '*', '/'));
        Stack<Character> s = new Stack<Character>();
        int lineNum = 0;
        boolean inQuote = false;
        boolean inComment = false;
        boolean inlineComment = false;
            
        try{
            Scanner fileReader = new Scanner(testFile);
            while(fileReader.hasNextLine()){
                String str = fileReader.nextLine();
                lineNum++;
                if(inlineComment) // resets to not in an inline comment
                    inlineComment = false;
                
                for(int i = 0; i < str.length(); i++){
                    if(inlineComment) // skips the loop for the rest of the line
                        continue;
                    
                    char c = str.charAt(i);
                    
                    // checks the opening symbol when not inside a quote or a comment
                    if(openSymbols.contains(c) && inComment == false && inQuote == false){
                        if((c == '\"' || c == '\'')){ // pushes the corresponding quote symbol to the stack, saves that the upcoming values are in quote
                            inQuote = true;
                            int n = openSymbols.indexOf(c);
                            s.push(closeSymbols.get(n));
                        }
                        else if(c == '/' && i != str.length()-1){ //checks what kind of quote it is
                            if(str.charAt(i+1) == '*'){ // pushes * for multi-line comment, saves that the upcoming values are in a comment
                                inComment = true; 
                                int n = openSymbols.indexOf(str.charAt(i+1));
                                s.push(closeSymbols.get(n));
                            }
                            if(str.charAt(i+1) == '/'){ // saves that the current line is a comment
                                inlineComment = true;
                            }
                        }
                        else{
                            int n = openSymbols.indexOf(c); // pushes corresponding symbol (not a quote or comment) to stack
                            s.push(closeSymbols.get(n));
                            
                        }
                    }
                    // checks closing symbols
                    else if(closeSymbols.contains(c)){
                        if(s.isEmpty()){ // returns an error if a closing symbol is found with no corresponding opening symbol on stack
                            return new EmptyStackError(lineNum);
                        }
                        
                        else if(inQuote == false && inComment == true && c == '/' && i != 0){ //checks for comment end
                            if(str.charAt(i-1) == '*' && s.peek() == str.charAt(i-1)){ // pops * from stack and sets not in comment
                                s.pop();
                                inComment = false;
                            }
                        }
                        else if(inComment == false && inQuote == true && (c == '\"' || c == '\'')){ //checks for quote end
                            if(s.peek() == c){ // pops corresponding quote from stack and sets not in quote
                                s.pop();
                                inQuote = false;
                            }
                        }
                        else if (inQuote == false && inComment == false && s.peek() == c){ // pops symbol if not in quote or comment
                            s.pop();
                        }
                        else if (inQuote == false && inComment == false && s.peek() != c){ // returns an error if the closing symbol doesn't match the symbol on the stack
                            return new MismatchError(lineNum, c, s.peek());
                        }
                    }

        
                    
                }
            }
            if(!(s.isEmpty())) { // returns an error if there are too many symbols left at the end on the stack
                return new NonEmptyStackError(s.peek(), s.size());
            }
            
            // returns null when everything is balanced
            return null;
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
}


