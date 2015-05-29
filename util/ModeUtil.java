package com.util;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;


public class ModeUtil {
	
	private static final Logger log = Logger.getLogger( ModeUtil.class.getName() );
	private static Logger mLogger = Logger.getLogger( ModeUtil.class.getPackage().getName() );
	
	
	public String getMode()
	{
		String mode = "";
		String applicationId = SystemProperty.applicationId.get();

		try
		{
			// Live Domain is arsubmitter.appspot.com
			if(applicationId.equals("anct-adaptavant")||applicationId.equals("anct-adaptavant-hrd"))
	        {
	        	mode = "LIVE";
	        }
			// Live Domain is staging-arsubmitter.appspot.com
			else if(applicationId.equals("testing-anct")||applicationId.equals("testing-anct-hrd"))
	        {
	        	mode = "STAGING";
	        }
	        else
	        {
	        	mode = "LOCAL";
	        }
			log.info("AppId is "+applicationId+",Mode is-"+mode);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.log(java.util.logging.Level.SEVERE,"Exception in the method getMode ::"+e.getMessage());
		}
		return mode;
	}
	
	public String getCMSURL()
	{
		String sMode = this.getMode();
		
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		String cmsurl = rb.getString("answerconnect.datastore.environment").trim();
		try
		{
			if(sMode.equalsIgnoreCase("LIVE"))
			{
				cmsurl = rb.getString("answerconnect.datastore.environment").trim();
				mLogger.info("Mode is Live cmsurl = "+cmsurl);
			}
			else if(sMode.equalsIgnoreCase("STAGING"))
			{
				cmsurl = rb.getString("answerconnect.datastore.staging.environment").trim();
				mLogger.info("Mode is Staging cmsurl "+cmsurl);
			}
			else
			{
				cmsurl = rb.getString("answerconnect.datastore.staging.environment").trim();
				mLogger.info("Mode is Non-Live cmsurl "+cmsurl);
			}
	}
		catch(Exception e)
		{
			e.printStackTrace();
			log.info("Exception in the method getCMSURL ::"+e.getMessage());
		}
		return cmsurl;
	}
	public String getPreferredURL()
	{
		String sMode = this.getMode();
		String url = null;
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		try
		{
			if(sMode.equalsIgnoreCase("LIVE"))
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("live.preferredUrl.url");
			}
			else if(sMode.equalsIgnoreCase("STAGING"))
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("staging.preferredUrl.url");
			}
			else
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("staging.preferredUrl.url");
			}
			log.info("url : " + url);
		}
		catch(Exception e)
		{
			log.log(java.util.logging.Level.SEVERE,e.getMessage(),e);
		}
		return url;
		
	}
	public String getTemplateURL()
	{
		String sMode = this.getMode();
		String url = null;
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		try
		{
			if(sMode.equalsIgnoreCase("LIVE"))
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("live.template.url");
			}
			else if(sMode.equalsIgnoreCase("STAGING"))
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("staging.template.url");
			}
			else
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("staging.template.url");
			}
			log.info("url : " + url);
		}
		catch(Exception e)
		{
			log.log(java.util.logging.Level.SEVERE,e.getMessage(),e);
		}
		return url;
		
	}
	public String getRegistrationURL()
	{
		log.info("getContactManagerURL ");
		String url = null;
		String sMode = this.getMode();
		
		log.info("The Mode is  : " + sMode);
		
		try
		{
			if(sMode.equalsIgnoreCase("LIVE"))
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("live.register.url");
			}
			else if(sMode.equalsIgnoreCase("STAGING"))
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("staging.register.url");
			}
			else
			{
				url =  ResourceBundle.getBundle("ApplicationResources").getString("staging.register.url");
			}
			log.info("url : " + url);
		}
		catch(Exception e)
		{
			log.log(java.util.logging.Level.SEVERE,e.getMessage(),e);
		}
		return url;
	}
	
	public String getActiveResponseURL() 
	{
		String arUrl					=	null;
		String mode						=	null;
		try
		{
			mode							=	getMode();
			log.info("The mode got  : " + mode);
			if("LIVE".equalsIgnoreCase(mode))
			{
				arUrl			=	ResourceBundle.getBundle("ApplicationResources").getString("live.new.ar.url").trim();
			}
			else if(("STAGING".equalsIgnoreCase(mode))||("lexreception-dev").equalsIgnoreCase(mode))
			{
				arUrl			=	ResourceBundle.getBundle("ApplicationResources").getString("staging.new.ar.url").trim();
			}
			else
			{
				arUrl			=	ResourceBundle.getBundle("ApplicationResources").getString("staging.new.ar.url").trim();
			}
			
			log.info("The cmsUrl url is  : " +arUrl);
		}
		catch(Exception e)
		{
			log.log( java.util.logging.Level.INFO , e.getMessage() , e );
		}
		return arUrl;
	}
	public String getActiveResponseID() 
	{
		String arID						=	null;
		String mode						=	null;
		try
		{
			mode							=	getMode();
			log.info("The mode got  : " + mode);
			
			if("LIVE".equalsIgnoreCase(mode))
			{
				arID			=	ResourceBundle.getBundle("ApplicationResources").getString("live.answerconnect.arpopup.id").trim();;
			}
			else if(("STAGING".equalsIgnoreCase(mode))||("lexreception-dev").equalsIgnoreCase(mode))
			{
				arID			=	ResourceBundle.getBundle("ApplicationResources").getString("staging.answerconnect.arpopup.id").trim();
			}
			else
			{
				arID			=	ResourceBundle.getBundle("ApplicationResources").getString("staging.answerconnect.arpopup.id").trim();
			}
			
			log.info("The cmsUrl url is  : " +arID);
		}
		catch(Exception e)
		{
			log.log( java.util.logging.Level.INFO , e.getMessage() , e );
		}
		return arID;
	}
	
	public String getUpdateAnswerPhraseUrl()
	{
		log.info("Inside getUpdateAnswerPhraseUrl method");

		String url = null;
		String mode = null;
		try
		{
			mode = new ModeUtil().getMode();

			if ("local".equalsIgnoreCase(mode) || "staging".equalsIgnoreCase(mode))
			{
				url = ResourceBundle.getBundle("ApplicationResources").getString("staging.update.answerphrase.url");
			}
			else
			{
				url = ResourceBundle.getBundle("ApplicationResources").getString("live.update.answerphrase.url");
			}
			log.info("The url is  : " + url);

		}
		catch (Exception e)
		{
			log.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
		}
		return url;

	}
	public String getSbClearCacheURl()
	{
		log.info("Inside getSbClearCacheURl method");
		String url = null;
		try
		{
			ResourceBundle rsb = ResourceBundle.getBundle("ApplicationResources");
			String mode = getMode();
			if("staging".equalsIgnoreCase(mode))
			{
				log.info(rsb.getKeys().toString());
				url = rsb.getString("staging.sbclearcache");
			}
			else
			{
				url = rsb.getString("live.sbclearcache");
			}
			log.info("The url is  : " + url);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		return url;
	}
	public String getAnswerPhraseUpdateCacheURL()
	{
		log.info("Inside getAnswerPhraseUpdateCacheURL method");
		ResourceBundle rsb = ResourceBundle.getBundle("ApplicationResources");
		String url = null;
		String mode = null;
		try
		{
			mode = new ModeUtil().getMode();

			if ("local".equalsIgnoreCase(mode) || "staging".equalsIgnoreCase(mode))
			{
				url = rsb.getString("staging.update.answerphrasecache.url");
			}
			else
			{
				url = rsb.getString("live.update.answerphrasecache.url");
			}
			log.info("The url is  : " + url);

		}
		catch (Exception e)
		{
			log.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
		}
		return url;

	}
}
