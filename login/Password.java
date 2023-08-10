package websites;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Password extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		boolean flag = true;
		String submit = request.getParameter("submit");
		FileReader fr = new FileReader("/home/harshithams/eclipse-workspace/websites/passwords");
		BufferedReader br = new BufferedReader(fr);
		response.setContentType("text/html");
		if(submit!=null)
		{
			String pass = request.getParameter("pass");
			while(br.ready())
			{
				if(pass.equals(br.readLine()))
				{
					out.println("password already exixts");
					flag = false;  
					break;
				}
			}
			if(flag)
				out.println("password accpeted");
			
		}
		else
		{
			out.println("Enter passowrd");
		}
	}
}
