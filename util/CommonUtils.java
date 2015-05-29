package com.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.business.service.SendMailInQueue;



public class CommonUtils 
{
	private static final Logger	log	= Logger.getLogger( CommonUtils.class.getName() );
	
	public static HashMap<String, String> getBrodwerDetails(String browserDetails)
	{
		//String userAgent = request.getHeader("User-Agent");
		String 				userAgent 		= 	browserDetails;
		String 				user 			= 	userAgent.toLowerCase();
		String 				os 				= 	"";
		String 				browser 		= 	"";
		HashMap<String, String> userAgentMap=	null;
		try
		{
			log.info("User Agent for the request is===>"+browserDetails);
			userAgentMap	=	new HashMap<String, String>();
			//=================OS=======================
		     if (userAgent.toLowerCase().indexOf("windows") >= 0 )
		     {
		    	 os = "Windows";
		     }
		     else if(userAgent.toLowerCase().indexOf("mac") >= 0)
		     {
		    	 os = "Mac";
		     }
		     else if(userAgent.toLowerCase().indexOf("x11") >= 0)
		     {
		    	 os = "Unix";
		     }
		     else if(userAgent.toLowerCase().indexOf("android") >= 0)
		     {
		    	 os = "Android";
		     }
		     else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
		     {
		    	 os = "IPhone";
		     }else
		     {
		    	 os = "UnKnown,	More-Info: "+userAgent;
		     }
		     //===============Browser===========================
		    if (user.contains("msie"))
		    {
		    	String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
		    	browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
		    }
		    else if (user.contains("safari") && user.contains("version"))
		    {
		    	browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		    }
		    else if ( user.contains("opr") || user.contains("opera"))
		    {
		    	if(user.contains("opera"))
		    		browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		    	else if(user.contains("opr"))
		    		browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
		    }
		    else if (user.contains("chrome"))
		    {
		    	browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		    }
		    else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
		    {
		    	//browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
		    	browser = "Netscape-?";
		    		  
		    }
		    else if (user.contains("firefox"))
		    {
		    	browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		    }
		    else
		    {
		    	browser = "UnKnown,	More-Info: "+userAgent;
		    }
		    log.info("Operating System======>"+os);
		    log.info("Browser Name==========>"+browser);
		    userAgentMap.put("os", os);
		    userAgentMap.put("browser", browser);
		}
		catch (StringIndexOutOfBoundsException ste) 
		{
			log.log(Level.SEVERE,"Exception while getting browser details"+ste.getMessage(),ste);
			browser = "UnKnown,	More-Info:: "+userAgent;	
			userAgentMap.put("os", os);
		    userAgentMap.put("browser", browser);
		}
		catch (ArrayIndexOutOfBoundsException ae) 
		{
			log.log(Level.SEVERE,"Exception while getting browser details"+ae.getMessage(),ae);
			browser = "UnKnown,	More-Info:: "+userAgent;	
			userAgentMap.put("os", os);
		    userAgentMap.put("browser", browser);
		}
		catch (Exception e) 
		{
			log.log(Level.SEVERE,"Exception while getting browser details"+e.getMessage(),e);						
		}
		return  userAgentMap;    
	}
	public static HashMap<String,String> getAddWord(String cookies)
	{
		HashMap<String,String>	addWord		=	null;
		try
		{
			if(cookies !=null && !cookies.isEmpty())
			{
				addWord		=	new HashMap<String,String>();
				addWord.put("utmz_campaign", getRequiredCookieInformation(cookies,"utmccn"));
				addWord.put("utmz_AdClickID", getRequiredCookieInformation(cookies,"utmgclid"));
				addWord.put("utmz_medium", getRequiredCookieInformation(cookies,"utmcmd"));
				addWord.put("utmz_source", getRequiredCookieInformation(cookies,"utmcsr"));
				addWord.put("utmz_term", getRequiredCookieInformation(cookies,"utmctr"));
			}
		}
		catch (Exception e) {
			log.severe("Excpetion inside getAddSenseDetails"+e.getMessage());
		}
		return addWord;
	}
	public static String getRequiredCookieInformation(String utmCookie,String value)
	{
		String utmVal = "";
		String utmValue = "";
		String [] utmValues = null;
		try
		{
			log.info("Inside getMediumValue method utmCookie : " + utmCookie+" required value : "+value);
			if (utmCookie != null && utmCookie != "")
			{
				utmVal = utmCookie.replace("|", ",").replace(".", ",").replace(";", ",");
				log.info("cookie : " + utmVal);
				utmValues = utmVal.split(",");
				for (int i = 0; i < utmValues.length; i++)
				{
					log.info("Cookie value : " + utmValues[i]);
					if (utmValues[i].contains(value))
					{
						utmValue = utmValues[i].split("=")[1];
						log.info("utm required value : " + utmValue);
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			log.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
		}
		return utmValue;
	}
	public static void writeCookies(HttpServletRequest request,HttpServletResponse response)
	{
		String	mode	=	"";
		log.info("******DomainName**************"+request.getServerName());
		String gclickID	=	"";
		Cookie cookies 	= 	null;
		try
		{
			 mode		=	new ModeUtil().getMode();
			 //gclickID	=	CommonUtils.getRequiredCookieInformation((String)request.getHeader("cookie"), "utmgclid");
			 gclickID 	=	CommonUtils.getGclidFromURL(request.getQueryString());
			 log.info("========The GclickID form server is ================"+gclickID);
			 if(!gclickID.isEmpty())
			 {
				 cookies = new Cookie("gclickID", gclickID);
				 if("LIVE".equalsIgnoreCase(mode))
				 {
				 	cookies.setDomain("answerconnect.com");
				 }
				 else
				 {
					 cookies.setDomain(request.getServerName());
				 }
				 cookies.setMaxAge(2*365*24*60*60);
				 cookies.setComment(""+new Date().toString());
				 cookies.setPath("/");
				 response.addCookie(cookies);
				 log.info("========This cookie is to update the gclickID with latest ad cookies================"+cookies.getValue());
				 log.info("========This cookie is to update the gclickID with latest ad cookies(comment)================"+cookies.getComment());
			 }
		}
		catch (Exception e) {
			log.log(Level.SEVERE,"Exception while writing the cookies"+e.getMessage(),e);
		}
	}
	
	public static String getGclidFromURL(String queryString){
		String gclid	=	"";
		log.info("Inside getGclidFromURL :Query :"+queryString);
		try
		{
			log.info("inside getGclidFromURL");
			if(queryString.contains("gclid"))
			{
				gclid	=	(queryString.substring(queryString.indexOf("gclid")).split("&")[0]).split("=")[1];
			}
			log.info("Gclid is===>"+gclid);
		}
		catch (NullPointerException e) 
		{
			log.info("no gclid");
		}
		catch (Exception e) 
		{
			log.log(Level.SEVERE,"Excpetion getGclidFromURL"+e.getMessage(),e);
		}
		return gclid;
	}
	
	public static String readCookies(HttpServletRequest request,String cookieName,String option)
	{
		String 		gclickID		=	"";
		Cookie[]	arrayOfCookies	= 	null;
		try
		{
			log.info("inside read cookies method CookiesName::"+cookieName);
			arrayOfCookies	=	request.getCookies();
			for (Cookie localCookie : arrayOfCookies) 
			{
				if(cookieName.equalsIgnoreCase(localCookie.getName()))
				{
					if("value".equalsIgnoreCase(option))
					{
						gclickID=localCookie.getValue();
						log.info("===========comment========"+localCookie.getComment());
						log.info("===========maxAge========"+localCookie.getMaxAge());
						break;
					}
					else if("comment".equalsIgnoreCase(option))
					{
						gclickID=localCookie.getComment();
						break;
					}
				}
				log.info("GclickID from cookies is::"+gclickID);
			}
		}
		catch (Exception e) 
		{
			log.log(Level.SEVERE,"Exception while reading the cookies"+e.getMessage(),e);
		}
		return gclickID;
	}
	public static void sendGeneralPorposeMail(String subject, String messagebody,String Option)
	{
		log.info("Inside sendMailToDevelopers method" + "subject:" + subject+ "messagebody :" + messagebody);

		String 	from 	= null;
		String 	to 		= null;
		String 	cc	 	= null;
		String 	bcc	 	= null;
		String 	mode 	= null;
		String fromName	= null;
		String toName	= null;
		String sSubject 		= "AnswerConnect Registration Failure !";
		try
		{
			from 			= 	ResourceBundle.getBundle("ApplicationResources").getString("autogenerated-email.registration-confirmation.from-address");
			mode 			= 	new ModeUtil().getMode();
			fromName		=	"Exception-AnswerConnect";
			if (!"LIVE".equalsIgnoreCase( mode ) )
			{	
				log.info("the mode is this :: "+mode);
				subject = "Staging :" + subject;
				
			}
			log.info("the mode is this :: "+mode);
			
			if (Option != null && "ar".equalsIgnoreCase(Option))
			{
				fromName		=	"Exception-AnswerConnect";
				toName			=	"Active Response Team";	
				to 				= 	"dev.webchat@a-cti.com";
				cc 				= 	ResourceBundle.getBundle("ApplicationResources").getString("ar.emailCCAddress");
				bcc 			= 	ResourceBundle.getBundle("ApplicationResources").getString("developer_email");
				SendMailInQueue.sendGeneralPorposeMail(from,fromName , to, toName,cc, bcc, subject, messagebody);
			}
			else if (Option != null && "DemoNumber".equalsIgnoreCase(Option))
			{
				fromName		=	"Exception-AnswerConnect";
				toName			=	"Distributedsource Team";	
				to 				= 	"development@distributedsource.com";
				cc 				= 	ResourceBundle.getBundle("ApplicationResources").getString("developer_email");
				bcc 			= 	null;
				SendMailInQueue.sendGeneralPorposeMail(from,fromName , to, toName,cc, bcc, subject, messagebody);
			}
			else
			{
				fromName			=	"Exception-AnswerConnect";
				toName				=	"CWA team";
				to 					= 	"dev.clientwebaccess@a-cti.com";
				cc 					= 	null;
				bcc 				= 	null;
				SendMailInQueue.sendGeneralPorposeMail(from,fromName , to, toName,cc, bcc, subject, messagebody);
			}
			log.info("from :" + from + "to:" + to +"mode"+mode +"bcc"+bcc);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
		}
	}
	public static String getSignUpURL(String utmgclid,String UniquePin)
	{
		
		String 		signUpUrl		=	"";
		String 		urlContent	=	"";
		try 
		{
			if(utmgclid.trim().length() >0)
			{
				urlContent   = "&agentpeopleid=f0244895-e6ff-4347-8e99-cc9302a9882a"+"&"+utmgclid;
			}
			else
			{
				urlContent   = "&agentpeopleid=f0244895-e6ff-4347-8e99-cc9302a9882a";
			}
			if("LIVE".equalsIgnoreCase(new ModeUtil().getMode()))
			{
				signUpUrl = ResourceBundle.getBundle("ApplicationResources").getString("live_signup_url")+UniquePin+urlContent;
				log.info("Signup url is :"+signUpUrl);
				
			}
			else 
			{
				signUpUrl = ResourceBundle.getBundle("ApplicationResources").getString("staging_signup_url")+UniquePin+urlContent+"&testcreditcard=true";
				log.info("Signup url is :"+signUpUrl);	
			}
		} 
		catch (Exception e) 
		{
			log.log(Level.SEVERE,e.getMessage(),e);
		}
		return signUpUrl;
	}
	
	public static HashMap<String, Object> getLocationDetails(HttpServletRequest request)
	{
		HashMap<String, Object> locationMap	=	null; 
		String country	=	"",city="",region="",latitude="",longitude="",temp="",state="",title="",address="",zip="";
		boolean	primary	=	true;
		try
		{
			log.info("inside getLocation");
			locationMap	= new HashMap<String, Object>();
			country		=	request.getHeader("X-AppEngine-Country"); 
			region		=	request.getHeader("X-AppEngine-Region");
			city		=	request.getHeader("X-AppEngine-City");
			temp		=	request.getHeader("X-AppEngine-CityLatLong");
			log.info("country>>"+country+"region>>"+region+"city>>"+city+"temp>>"+temp);
			if(temp !=null || temp !="")
			{
				latitude	=	(temp.split(","))[0];
				longitude	=	(temp.split(","))[1];
			}
			city 		= 	(city != null ? city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase() : city);
			region		=	(region != null ? region.toUpperCase() : region);
			log.info("country>>"+country+"region>>"+region+"city>>"+city+"temp>>"+temp+"latitude>>"+latitude+"longitude>>"+longitude);
			locationMap.put("title",title);
			locationMap.put("address", address);
			locationMap.put("city", city);
			locationMap.put("state", region);
			locationMap.put("country", country);
			locationMap.put("zip", zip);
			locationMap.put("primary", primary);
			locationMap.put("latitude", latitude);
			locationMap.put("longitude", longitude);
			log.info("locationMap==>"+locationMap);
		}
		catch (NullPointerException e) 
		{
			locationMap	= new HashMap<String, Object>();
			log.log(Level.SEVERE, "Nullpointer Exception while getting location details"+e.getMessage(),e);
		}
		catch (Exception e) 
		{
			log.log(Level.SEVERE, "Exception while getting location details"+e.getMessage(),e);
		}
		return locationMap;
	}
}
