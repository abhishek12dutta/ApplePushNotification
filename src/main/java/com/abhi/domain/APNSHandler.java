package com.abhi.domain;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.abhi.model.PostRequest;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsDelegate;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.DeliveryError;
import com.notnoop.apns.PayloadBuilder;

public class APNSHandler implements ApnsDelegate {

	public static final int APNSHANDLER_OK = 0;
	public static final int APNSHANDLER_NO_SSH_KEY_FOUND = 1;

	private static APNSHandler instance = new APNSHandler();

	private String certificateStorePath;

	private HashMap<String, ApnsService> appList = new HashMap<String, ApnsService>();

	public void setCertificateStorePath(String certificateStorePath) {
		this.certificateStorePath = certificateStorePath;
	}

	private APNSHandler() {
	};

	public static APNSHandler getInstance() {
		if (null == instance) {
			instance = new APNSHandler();
		}
		return instance;
	}

	public int addApp(String appName, String certificateFileName,
			String certificatePassword, boolean production, int nrThreads)
			throws Exception {
		String keyPath = certificateStorePath + "/" + certificateFileName;
		URL fullPath = this.getClass().getResource(keyPath);
		System.out.println("keyPath = " + keyPath);
		System.out.println("fullPath = " + fullPath);
		if (fullPath == null) {
			System.out
					.println("No SSH Key found for "
							+ appName
							+ ", not adding app! Make sure the key is present in the correct folder and restart the server");
			return APNSHandler.APNSHANDLER_NO_SSH_KEY_FOUND;
		}

		String fullKeyPath = fullPath.getPath();
		System.out.println("fullKeyPath = " + fullKeyPath);

		ApnsServiceBuilder sb;
		sb = APNS.newService().withCert(fullKeyPath, certificatePassword)
				.withDelegate(this);

		if (production) {
			System.out.println("production APNS");
			sb.withProductionDestination();
		} else {
			System.out.println("sandbox APNS");
			sb.withSandboxDestination();
		}

		ApnsService service = sb.build();
		appList.put(appName, service);

		return APNSHandler.APNSHANDLER_OK;
	}

	public void sendMessage(PostRequest postRequest) throws Exception {
		ApnsService service = appList.get(postRequest.getAppName());
		System.out.println("got service - " + service);
		System.out.println("devices - " + postRequest.getDevices());
		if (service != null) {
			PayloadBuilder payloadBuilder = APNS.newPayload();
			if (postRequest.isContentAvailable())
				payloadBuilder.forNewsstand();
			if (postRequest.getAlert() != null
					&& !postRequest.getAlert().equals(""))
				payloadBuilder.alertBody(postRequest.getAlert());
			if (postRequest.getSound() != null
					&& !postRequest.getSound().equals(""))
				payloadBuilder.sound(postRequest.getSound());

			// Support for Safari notifications
			if (postRequest.getTitle() != null
					&& !postRequest.getTitle().equals(""))
				payloadBuilder.alertTitle(postRequest.getTitle());
			if (postRequest.getAction() != null
					&& !postRequest.getAction().equals(""))
				payloadBuilder.alertAction(postRequest.getAction());
			if (postRequest.getUrlArgs() != null)
				payloadBuilder.urlArgs(postRequest.getUrlArgs());

			if (postRequest.getMetadata() != null
					&& postRequest.getMetadata().length() > 0)
				payloadBuilder
						.customField("appData", postRequest.getMetadata());

			// System.out.println( "Payload - [" + payload + "]" );

			for (String item : postRequest.getDevices()) {
				if (postRequest.isShowBadge()
						&& postRequest.getBadgeCount() != -1)
					payloadBuilder.badge(postRequest.getBadgeCount());

				String payload = payloadBuilder.build();
				System.out.println("Payload - [" + payload + "]");

				System.out.println("Sending to token [" + item + "]");
				service.push(item, payload);
			}
			System.out.println("added messages to queue");
		}
	}

	public Map<String, Date> callFeedbackService(String appName)
			throws Exception {
		System.out.println("Calling feedback service");
		ApnsService service = appList.get(appName);
		Map<String, Date> inactiveDevices = service.getInactiveDevices();

		if (inactiveDevices.size() == 0) {
			System.out.println("No invalid tokens found");
		} else {
			System.out.println("Invalid tokens:");
		}
		return inactiveDevices;
	}

	public void messageSent(ApnsNotification apnsNotification, boolean resent) {
		System.out.println();

	}

	public void messageSendFailed(ApnsNotification apnsNotification, Throwable e) {
		System.out.println("Failed to send notification-->  "
				+ apnsNotification.getDeviceToken());

	}

	public void connectionClosed(DeliveryError e, int messageIdentifier) {
		System.out.println();

	}

	public void cacheLengthExceeded(int newCacheLength) {
		System.out.println();

	}

	public void notificationsResent(int resendCount) {
		System.out.println();

	}

}
