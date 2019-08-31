# syndinnovate-hni
Developing automated system for Identification &amp; Triggering alert when a High Net Worth customer visits the Branch and/or using our Digital Platform like Internet/Mobile Banking etc.

[Synd-Innovate Hackathon](https://www.hackerearth.com/challenges/hackathon/synd-innovate/)


# Download the following libraries and place it in WEB-INF/lib

derby-10.14.2.0.jar
json-20170516.jar
sendgrid-java-latest.jar
twilio-7.41.2-jar-with-dependencies.jar

You need to signup in sendgrid and twilio for mail and WhatsApp integrations and update the Constants.java 


# Idea Description 

The aim is to develop a system to identify HNI and top 100 customers when they enter the branch premises. For this, the power of Artificial Intelligence (AI) is harnessed. As per govt norms, since each branch holds KYC records which includes the photo of the customer, a neural network powered facial recognition system is used. This AI powered facial recognition system (which works for even low resolution photos of 5KB onwards) will be capable of identifying a person given his/her photo. Most of the banks today are CCTV powered. The entrance CCTV camera footage stream will be fed to the server which processes the video using various video processing techniques. From the video stream , the photo of the person entering is captured which is fed to the face recognition system. The facial recognition system identifies the person and verifies if the person is a HNI customer/top 100 customer against the list of HNI customers/top 100 customers. If they turn out to be either of them, an alert is triggered to the designated person at the branch. This whole pipeline is done in real time.


# Tech used

OpenCV, Derby, AI, Face Recognition, WebSockets, WhatsApp Twilio, SendGrid Mail


# Model usage

The models used here are available on the Internet. We have converted the model to work with our OpenCV pipeline.


# Demo 

You can find the demo [here](https://www.youtube.com/watch?v=HFVtKIxehH0)

# Authors
	
[Balachandar S](https://github.com/balachandarsv)

[Sujatha S Iyer](https://github.com/SujathaSIyer)





