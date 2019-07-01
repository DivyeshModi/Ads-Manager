# Ads Manager

allprojects {

		repositories {
			
			...
			
			maven { url 'https://jitpack.io' }
			
			maven { url "http://dl.appnext.com/" }
		}
	}
	
	
dependencies {

	        implementation 'com.github.DivyeshModi:Ads-Manager:v1.0.0'
	
	}
	
Android Manifest 

	<application
	
		...
		
 		android:networkSecurityConfig="@xml/network_security_config">

		<meta-data
            		android:name="com.google.android.gms.ads.APPLICATION_ID"
            		android:value="YOUR_APP_ID" />
	    
	</application>
	
	
