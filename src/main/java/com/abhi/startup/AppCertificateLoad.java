package com.abhi.startup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.abhi.domain.APNSHandler;
import com.abhi.model.AppDetails;

public class AppCertificateLoad extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
    *
    */
	public void init(ServletConfig config) throws ServletException {

		try {

			APNSHandler handler = APNSHandler.getInstance();
			handler.setCertificateStorePath("/pns-provider");
			try {
				loadConfigFromDatabase();
			} catch (Exception e) {
				System.out.println("Caught exception " + e);
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Caught exception " + e);
			e.printStackTrace();
		}
	}

	public void loadConfigFromDatabase() throws Exception {
		// DBAccess db = new DBAccess();
		// MobileApplicationDAO mobileApplicationDAO = new
		// MobileApplicationDAO();
		try {
			// List<AppDetails> appDetails =
			// mobileApplicationDAO.getAppDetails();
			// TODO: call db to load all the application details
			/*
			 * ------------------------------------------------------------------
			 * APP_DETAILS_TABLE
			 * ------------------------------------- 
			 * Appname |certificateFileName 			| path 					|keyPassword | isProduction 
			 * ==============================================================================================
			 * Testapp |testapp_sandbox_cert.p12 	    |  /weblogic/testaapp/ 	|mypass 	 |		false
			 */
			List<AppDetails> appDetails = new ArrayList<AppDetails>();
			
			//appDetails = dao call here
			APNSHandler handler = APNSHandler.getInstance();
			System.out.println("Adding apps");

			int nrThreads = 1;
			for (AppDetails item : appDetails) {
				System.out.println("Adding app - " + item);
				try {
					handler.addApp(item.getAppName(), item.getSslKeyFilename(),
							item.getSslKeypassword(), item.isProduction(),
							nrThreads);
				} catch (Exception e) {
					System.out.println("Caught exception " + e);
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			System.out.println("Caught exception " + e);
			e.printStackTrace();
		}
	}

}
