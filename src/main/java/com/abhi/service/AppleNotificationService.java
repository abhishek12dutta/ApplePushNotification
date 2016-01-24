package com.abhi.service;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.abhi.domain.APNSHandler;
import com.abhi.model.FeedbackResponse;
import com.abhi.model.PostRequest;
import com.abhi.model.PostResponse;

@Service("PostService")
@Path("/services")
public class AppleNotificationService {

	public static final String RESPONSE_STATUS_OK = "OK";
	public static final String RESPONSE_STATUS_ERROR = "ERROR";

	public static final String PUSH_TYPE_IOS = "ios";

	@POST
	@Path("/postMessage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostResponse postMessage(PostRequest postRequest) {
		PostResponse postResponse = new PostResponse();
		try {
			sendMessage(postRequest);
			postResponse.setStatus(RESPONSE_STATUS_OK);
		} catch (Exception e) {
			postResponse.setStatus(RESPONSE_STATUS_ERROR);
			postResponse.setErrorReason(e.getLocalizedMessage());
		}
		return postResponse;
	}

	@GET
	@Path("/feedback/{appname}")
	@Produces(MediaType.APPLICATION_JSON)
	public FeedbackResponse handleFeedback(@PathParam("appname") String appName) {

		FeedbackResponse response = new FeedbackResponse();
		try {
			Map<String, Date> failedFeedbackTokens = callFeedbackService(appName);
			if (failedFeedbackTokens != null) {
				response.setInvalidTokens(failedFeedbackTokens);
			}
			response.setStatus(RESPONSE_STATUS_OK);
		} catch (Exception e) {
			System.out.println("Service:HandleFeedback, App:" + appName
					+ ", Status:ERROR,ErrorMessage:" + e.getLocalizedMessage());
			response.setStatus(RESPONSE_STATUS_ERROR);
			response.setErrorReason(e.getLocalizedMessage());
		}
		return response;
	}

	private void sendMessage(PostRequest postRequest) throws Exception {
		APNSHandler.getInstance().sendMessage(postRequest);

	}

	// Extracted for Junit
	private Map<String, Date> callFeedbackService(String appName)
			throws Exception {
		return APNSHandler.getInstance().callFeedbackService(appName);
	}

}
