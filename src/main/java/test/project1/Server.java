package test.project1;

import java.awt.TextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.vertx.core.*;
import io.vertx.core.http.*;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.*;
import io.vertx.ext.web.handler.BodyHandler;

public class Server extends AbstractVerticle {
	private Map<String, Integer> allwords = new LinkedHashMap<>();
	private List<Character> charactervalue =Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');

	
	private Router router;

	@Override
	public void start(Future<Void> fut) throws Exception {
		router = Router.router(vertx);
		router.get("/analyze").handler(routingContext -> {
				HttpServerResponse response = routingContext
					.response();
				response.putHeader("content-type", "text/html")
						.end("<h1>Your are lauching the vertx program</h1><p>Please test using the command curl on the terminal for begining to see result</p>");
				});
		
		
		
		
		router.route("/analyze").handler(BodyHandler.create());
		router.post("/analyze").handler(this::addOne);
            
		vertx.createHttpServer().requestHandler(router::accept)
			.listen(
				config().getInteger("http.port", 8080),
				result -> {
					if (result.succeeded()) {
						fut.complete();
					} else {
						fut.fail(result.cause());
					}
				});
	}

private void addOne(RoutingContext routingContext) {   
	int closevalue=0;
	String closelexical="";
	JsonObject requestJson = routingContext.getBodyAsJson(); 
	String param=requestJson.getString("text");	
	int paramvalue=gettotalvalue(param);
	
	if(allwords.size()!=0)
	{
	 closevalue=getclosevalue(paramvalue);
	 closelexical=getcloselexical(param);
	 if(closelexical=="")
		 closelexical=getcloselexicalperlength(param);
	}
      allwords.put(param,paramvalue);
	  routingContext.response()
	      .setStatusCode(201)
	      .putHeader("content-type", "application/json; charset=utf-8");
	  if(closelexical!="")
	  {
		  routingContext.response().end("The field value: "+closevalue+"\nThe field lexical: "+closelexical+"\nAll data:  "+Json.encodePrettily(allwords.toString()));
	  }
	  else
		  routingContext.response().end("That the first request the two field are not exist");
	    //  .end("The field value: "+closevalue+"\nAll data:  "+Json.encodePrettily(allwords.toString()));
	}

private int gettotalvalue(String param)
{
	int total=0;
	for (int i=0;i<param.length();i++)
	{
		total=charactervalue.indexOf(param.charAt(i))+1+total;
	}
	return total;
}
private int getclosevalue(int paramvalue)
{
	int diff=0;
	int closevalue=	allwords.values().iterator().next();
	int diffclosevalue=paramvalue-closevalue;
	if(diffclosevalue<0)
		diffclosevalue=diffclosevalue*-1;
	
	  for(Map.Entry<String, Integer> entry : allwords.entrySet()){
		int tmp=entry.getValue();
		if(tmp>=paramvalue)
			diff=tmp-paramvalue;
		else
			diff=paramvalue-tmp;
		if(diff<diffclosevalue)
		{
			diffclosevalue=diff;
			closevalue=tmp;
		}
			
			
	}
	return closevalue;
}
private String getcloselexical(String param)
{
	String closelexical="";
	int sum=0;
	int max=0;
	for(Map.Entry<String, Integer> entry : allwords.entrySet()){
		String tmp=entry.getKey();
		for (int i=0;i<param.length() && i<tmp.length();i++)
		{
			if(tmp.charAt(i)==param.charAt(i))
                  sum+=1;
          }
		if(sum>max)
		{
			max=sum;
			closelexical=tmp;
			
		}
	}
	
	return closelexical;

}
private String getcloselexicalperlength(String param)
{
	int diff=0;
	String closevalue=	allwords.keySet().iterator().next();
	int diffclosevalue=param.length()-closevalue.length();
	if(diffclosevalue<0)
		diffclosevalue=diffclosevalue*-1;
	  for(Map.Entry<String, Integer> entry : allwords.entrySet()){
			String tmp=entry.getKey();
			if(tmp.length()>=param.length())
				diff=tmp.length()-param.length();
			else
				diff=param.length()-tmp.length();
			if(diff<diffclosevalue)
			{
				diffclosevalue=diff;
				closevalue=tmp;
			}
				
				
		}
		return closevalue;
	
	
}
}
