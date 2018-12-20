import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class keywordcounter 
{

	public static void main(String[] args) throws Exception 
	{
		MaxFH myFH=new MaxFH();
		
		String input="";
		String data = "";
        long wordCount = 0;
        long topK = 0;
		String[] arrs = null;
        
		//Initializing objects for File IO
        FileReader inputFile=new FileReader(args[0]);
		BufferedReader myBr=new BufferedReader(inputFile);
		
		File outputFile=new File("output_file.txt");
		FileOutputStream myWr=new FileOutputStream(outputFile);
		OutputStreamWriter myWriter=new OutputStreamWriter(myWr,"UTF-8");
		
		while(true)
        {
            input=myBr.readLine();
            if(input.equals("STOP") || input.equals("stop"))
            {
                break;
            }
            else if(input.contains("$"))
            {
                arrs=input.split(" ");
                data=arrs[0].substring(1);
                wordCount=Integer.parseInt(arrs[1]);
                myFH.processInput(data,wordCount);
            }
            else 
            {
                topK=Integer.parseInt(input);
                List<String> answers=myFH.findTopK(topK);
                for(int i=0;i<answers.size();i++)
                {
                	myWriter.append(answers.get(i));
                	if(i!=answers.size()-1)
                	{
                		myWriter.append(",");
                	}
                	
                }
                myWriter.append("\n");
            }
        }

		//Close all variables related to File IO.
        myWriter.close();
        myWr.close();
        myBr.close();
	}
}
