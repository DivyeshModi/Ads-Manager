# Ads Manager

		repositories {
			
			...
			
			maven { url 'https://jitpack.io' }
			
			maven { url "http://dl.appnext.com/" }
		}
	
	
dependencies {

	        implementation 'com.github.DivyeshModi:Ads-Manager:6bb779b88a'
    	implementation 'com.facebook.android:audience-network-sdk:5.4.0'   //adapter
	
	}
	
Android Manifest 

 	 <uses-permission android:name="android.permission.INTERNET" />

	<application
	
		...
		
 		android:networkSecurityConfig="@xml/network_security_config">

		<meta-data
            		android:name="com.google.android.gms.ads.APPLICATION_ID"
            		android:value="YOUR_APP_ID" />
	    
	</application>
	
	
